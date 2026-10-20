// controller/VentaController.java
package com.ventas.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ventas.demo.dto.VentaRequest;
import com.ventas.demo.entity.Venta;
import com.ventas.demo.repository.VentaRepository;
import com.ventas.demo.service.VentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService service;
    private final VentaRepository repo;

    public VentaController(VentaService service, VentaRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Venta> vender(@Valid @RequestBody VentaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarVenta(request));
    }

    @GetMapping
    public List<Venta> listar() { return repo.findAll(); }
}