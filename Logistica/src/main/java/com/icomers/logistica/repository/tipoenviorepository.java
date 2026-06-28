package com.icomers.logistica.repository;

import com.icomers.logistica.model.tipoenvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tipoenviorepository extends JpaRepository<tipoenvio, Integer> {
}
