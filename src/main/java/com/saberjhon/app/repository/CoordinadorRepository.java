package com.saberjhon.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.saberjhon.app.entity.Coordinador;

public interface CoordinadorRepository extends MongoRepository<Coordinador, String> {

}