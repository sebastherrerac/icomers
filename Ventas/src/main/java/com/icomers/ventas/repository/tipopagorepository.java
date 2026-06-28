package com.icomers.ventas.repository;

import com.icomers.ventas.model.tipopago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tipopagorepository extends JpaRepository<tipopago, Integer> {
}
