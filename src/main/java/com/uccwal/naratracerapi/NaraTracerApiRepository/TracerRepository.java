package com.uccwal.naratracerapi.NaraTracerApiRepository;


import com.uccwal.naratracerapi.NaraTracerApiEntity.TracerApiEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TracerRepository extends MongoRepository<TracerApiEntity, String> {
}
