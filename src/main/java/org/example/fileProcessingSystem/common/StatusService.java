package org.example.fileProcessingSystem.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void updateStatus(String fileName, String status) {
        Query query = new Query(Criteria.where("fileName").is(fileName));
        Update update = new Update().set("status", status);
        mongoTemplate.upsert(query, update, FileStatus.class);
    }
}
