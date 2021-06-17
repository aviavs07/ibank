package com.jbl.ibank.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jbl.ibank.rest.api.model.RequestQueue;

@Repository
public interface RequestQueueRepository extends JpaRepository<RequestQueue, Long> {

}
