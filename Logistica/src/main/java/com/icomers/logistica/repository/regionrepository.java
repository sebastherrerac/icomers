package com.icomers.logistica.repository;

import com.icomers.logistica.model.region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface regionrepository extends JpaRepository<region, Integer> {
}
