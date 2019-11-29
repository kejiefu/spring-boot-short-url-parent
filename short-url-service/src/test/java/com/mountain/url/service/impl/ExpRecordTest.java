package com.mountain.url.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mountain.url.Application;
import com.mountain.url.ExpRecord;
import com.mountain.url.ExpRecordCopy1;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Auther kejiefu
 * @Date 2019/6/30 0030
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ExpRecordTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void getExp() throws Exception {
        Query query = new Query(Criteria.where("a").is("用户签到增加的经验值"));
        List<ExpRecord> list = mongoTemplate.find(query, ExpRecord.class);
        List<Long> distinctIdList = list.stream().map(item -> item.getUid()).distinct().collect(Collectors.toList());
        System.err.println("distinctIdList.size:" + distinctIdList.size());
        for (Long aLong : distinctIdList) {
            Query query1 = new Query(Criteria.where("a").is("用户签到增加的经验值").and("uid").is(aLong));
            List<ExpRecord> list1 = mongoTemplate.find(query1, ExpRecord.class);
            list1.sort(Comparator.comparing(ExpRecord::getDate));
            if (list1.size() >= 7) {
                System.err.println("list1:" + JSONObject.toJSONString(list1));
                Integer startDay = 0;
                Integer continueDay = 0;
                boolean con1 = false;
                boolean con2 = false;
                boolean con3 = false;
                for (Integer i = 0; i < list1.size(); i++) {
                    ExpRecord expRecord = list1.get(i);
                    if (i == 0) {
                        continueDay++;
                    } else {
                        if (startDay.toString().substring(0, 6).equals(expRecord.getDate().toString().substring(0, 6))) {
                            if ((expRecord.getDate() - startDay) > 1) {
                                continueDay = 0;
                            } else {
                                continueDay++;
                            }
                        } else {
                            Integer time = Integer.valueOf(String.valueOf(Integer.valueOf(expRecord.getDate().toString().substring(0, 6)) - 1) + String.valueOf(Integer.valueOf(expRecord.getDate().toString().substring(6, 8))));
                            if ((expRecord.getDate() - time) > 1) {
                                continueDay = 0;
                            } else {
                                continueDay++;
                            }

                        }

                        if (continueDay >= 7 && continueDay < 15) {
                            if (!con1) {
                                ExpRecordCopy1 expRecordCopy1 = new ExpRecordCopy1();
                                expRecordCopy1.setId(UUID.randomUUID().toString());
                                expRecordCopy1.setUid(expRecord.getUid());
                                expRecordCopy1.setCount(continueDay);
                                mongoTemplate.save(expRecordCopy1);
                            }
                            con1 = true;
                        }
                        if (continueDay >= 15 && continueDay < 30) {
                            if (!con2) {
                                ExpRecordCopy1 expRecordCopy1 = new ExpRecordCopy1();
                                expRecordCopy1.setId(UUID.randomUUID().toString());
                                expRecordCopy1.setUid(expRecord.getUid());
                                expRecordCopy1.setCount(continueDay);
                                mongoTemplate.save(expRecordCopy1);
                            }
                            con2 = true;
                        }
                        if (continueDay >= 30) {
                            if (!con3) {
                                ExpRecordCopy1 expRecordCopy1 = new ExpRecordCopy1();
                                expRecordCopy1.setId(UUID.randomUUID().toString());
                                expRecordCopy1.setUid(expRecord.getUid());
                                expRecordCopy1.setCount(continueDay);
                                mongoTemplate.save(expRecordCopy1);
                            }
                            con3 = true;
                        }
                    }
                    startDay = expRecord.getDate();
                }

            }
        }
    }

    @Test
    public void ttt() throws Exception {
        ExpRecord expRecord = new ExpRecord();
        expRecord.setDate(20190612);
        Integer time = Integer.valueOf(String.valueOf(Integer.valueOf(expRecord.getDate().toString().substring(0, 6)) - 1) + String.valueOf(Integer.valueOf(expRecord.getDate().toString().substring(6, 8))));
        System.err.println(time);
    }

}
