package com.icomers.logistica.repository;

import com.icomers.logistica.model.comuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface comunarepository extends JpaRepository<comuna, Integer> {
}
