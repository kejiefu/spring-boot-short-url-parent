package com.mountain.url.thread;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mountain.url.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * @Auther kejiefu
 * @Date 2019/4/26 0026
 */
@Slf4j
public class CounterThread extends Thread {


    private ArrayBlockingQueue<Integer> arrayBlockingQueue;

    private static final String ID_KEY = "_id";

    private static final String NUMBER_FIELD = "n";

    private static final String COUNTER_NAME = "count";

    private Integer prefetchCount = 10;

    private MongoCollection<Document> collection;

    public CounterThread() {
        arrayBlockingQueue = new ArrayBlockingQueue<Integer>(prefetchCount);
        MongoTemplate mongoTemplate = SpringContextUtils.getBean(MongoTemplate.class);
        Boolean collectionExists = mongoTemplate.collectionExists(COUNTER_NAME);
        if (collectionExists) {
            collection = mongoTemplate.getCollection(COUNTER_NAME);
        } else {
            collection = mongoTemplate.createCollection(COUNTER_NAME);
        }
    }


    @Override
    public void run() {
        while (true) {
            try {
                Integer half = prefetchCount / 2;
                if (arrayBlockingQueue.size() < half) {
                    Integer id = getNextSequence();
                    for (Integer i = (id - prefetchCount + 1); i <= id; i++) {
                        arrayBlockingQueue.put(i);
                    }
                }
            } catch (Exception ex) {
                log.error("CounterThread", ex);
            }
        }
    }

    public Integer getNumber() throws InterruptedException {
        return arrayBlockingQueue.take();
    }

    private Integer getNextSequence() throws TimeoutException {
        Integer id = 1;
        Bson filter = Filters.and(Filters.eq(ID_KEY, id));
        Document doc = collection.findOneAndUpdate(filter, Updates.inc(NUMBER_FIELD, prefetchCount));
        if (doc != null) {
            return doc.getInteger(NUMBER_FIELD) + prefetchCount;
        }
        try {
            Document document = new Document();
            document.append(ID_KEY, id);
            document.append(NUMBER_FIELD, prefetchCount);
            collection.insertOne(document);
            return prefetchCount;
        } catch (Exception e) {
            throw new TimeoutException("获取序列号异常");
        }
    }
}
