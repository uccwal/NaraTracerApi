package com.uccwal.naratracerapi.NaraTracerApiController;


import com.uccwal.naratracerapi.NaraTracerApiEntity.TracerApiEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

@RestController
public class TracerApiController {

    private static final Logger logger = LoggerFactory.getLogger(TracerApiController.class);
    private final MongoTemplate mongoTemplate;


    @Autowired
    public TracerApiController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @GetMapping("/tracerData")
    public List<TracerApiEntity> getTracerData() {
        // MongoDB에서 데이터 조회

        //List<TracerApiEntity> tracerData = mongoTemplate.findAll(TracerApiEntity.class);
        //return tracerData;
        return mongoTemplate.findAll(TracerApiEntity.class);
    }


    @PostMapping("/searchTracerData")
    public List<TracerApiEntity> searchTracerData(@RequestBody TracerApiEntity tracerApiEntity) {
        Query query = new Query();

        List<Criteria> orCriterias = new ArrayList<>();

        // Category 검색 조건 추가
        if (tracerApiEntity.getCategory() != null) {
            orCriterias.add(Criteria.where("category").is(tracerApiEntity.getCategory()));
        }

        // Bidder (기존 DemandAgency) 검색 조건 추가
        if (tracerApiEntity.getBidder() != null && tracerApiEntity.getBidder().length > 0) {
            List<String> bidders = Arrays.asList(tracerApiEntity.getBidder());
            orCriterias.add(Criteria.where("bidder").regex(".*" + String.join("|", bidders) + ".*", "i"));
        }

        // TitleLinkText (기존 AnnouncementName) 검색 조건 추가
        if (tracerApiEntity.getTitleLinkText() != null && tracerApiEntity.getTitleLinkText().length > 0) {
            List<String> titleLinkTexts = Arrays.asList(tracerApiEntity.getTitleLinkText());
            orCriterias.add(Criteria.where("titleLinkText").regex(".*" + String.join("|", titleLinkTexts) + ".*", "i"));
        }

        // 기존의 bidStart 및 endDate 검색 조건 추가
        if (tracerApiEntity.getStartDate() != null && tracerApiEntity.getEndDate() != null) {
            orCriterias.add(Criteria.where("bidStart").gte(tracerApiEntity.getStartDate()).lte(tracerApiEntity.getEndDate()));
        }

        // 다른 검색 조건들 추가...

        if (!orCriterias.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(orCriterias.toArray(new Criteria[0])));
        }

        logger.info(String.valueOf(query));

        //List<TracerApiEntity> searchResult = mongoTemplate.find(query, TracerApiEntity.class);
        //return searchResult;
        return mongoTemplate.find(query, TracerApiEntity.class);
    }
}

