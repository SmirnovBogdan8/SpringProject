package org.example.fileProcessingSystem.statusCheckService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.example.fileProcessingSystem.common.FileStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class StatusCheckService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/status")
    public ResponseEntity<String> getStatus(@RequestParam("fileName") String fileName) {
        Query query = new Query(Criteria.where("fileName").is(fileName));
        FileStatus fileStatus = mongoTemplate.findOne(query, FileStatus.class);

        if (fileStatus == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(fileStatus.getStatus());
    }
}

