package com.deviget.minesweeper.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.deviget.minesweeper.model.Game;

public interface GameRepository extends MongoRepository<Game,Integer>{

}
