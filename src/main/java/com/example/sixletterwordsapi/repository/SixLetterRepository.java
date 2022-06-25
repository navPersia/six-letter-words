package com.example.sixletterwordsapi.repository;

import com.example.sixletterwordsapi.model.FileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SixLetterRepository extends MongoRepository<FileInfo, String> {
}
