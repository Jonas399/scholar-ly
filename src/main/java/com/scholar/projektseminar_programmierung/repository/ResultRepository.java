package com.scholar.projektseminar_programmierung.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholar.projektseminar_programmierung.model.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

}
