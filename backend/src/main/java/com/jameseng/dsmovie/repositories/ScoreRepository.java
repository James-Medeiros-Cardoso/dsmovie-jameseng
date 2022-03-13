package com.jameseng.dsmovie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jameseng.dsmovie.entities.Score;
import com.jameseng.dsmovie.entities.ScorePK;

public interface ScoreRepository extends JpaRepository<Score, ScorePK> {

}
