package com.jameseng.dsmovie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jameseng.dsmovie.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
