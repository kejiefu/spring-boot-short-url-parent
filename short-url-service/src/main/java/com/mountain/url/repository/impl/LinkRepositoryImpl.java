package com.mountain.url.repository.impl;

import com.mongodb.client.result.UpdateResult;
import com.mountain.url.repository.LinkRepository;
import com.mountain.url.repository.model.LinkPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * @Auther kejiefu
 * @Date 2019/4/30 0030
 */
@Repository
public class LinkRepositoryImpl implements LinkRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public LinkPo getLinkById(Integer id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, LinkPo.class);
    }

    public LinkPo getLinkByUrl(String url) {
        return null;
    }

    public LinkPo saveLink(LinkPo linkPo) {
        return mongoTemplate.save(linkPo);
    }

    public long updateLink(LinkPo linkPo) {
        Query query = new Query(Criteria.where("id").is(linkPo.getId()));
        Update update = new Update().set("updateTime", linkPo.getUpdateTime()).set("expireTime", linkPo.getExpireTime());
        //更新查询返回结果集的第一条
        UpdateResult result = mongoTemplate.updateFirst(query, update, LinkPo.class);
        return result.getMatchedCount();
    }

    public void deleteUserById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, LinkPo.class);
    }


}
