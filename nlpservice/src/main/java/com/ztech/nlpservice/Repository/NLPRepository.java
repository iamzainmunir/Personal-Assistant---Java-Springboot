package com.ztech.nlpservice.Repository;

import com.ztech.nlpservice.Entity.NLP;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NLPRepository extends MongoRepository<NLP, ObjectId> {
}
