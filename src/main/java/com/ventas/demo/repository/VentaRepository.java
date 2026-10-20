package com.ventas.demo.repository;

import com.ventas.demo.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> { }