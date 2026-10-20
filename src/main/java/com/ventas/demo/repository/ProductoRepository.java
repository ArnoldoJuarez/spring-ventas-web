package com.ventas.demo.repository;

import com.ventas.demo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends 
JpaRepository<Producto, Long> { }