// service/VentaService.java
package com.ventas.demo.service;

import com.ventas.demo.dto.*;
import com.ventas.demo.entity.*;
import com.ventas.demo.exception.*;
import com.ventas.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class VentaService {

    private final ProductoRepository productoRepo;
    private final VentaRepository ventaRepo;

    public VentaService(ProductoRepository productoRepo, VentaRepository ventaRepo) {
        this.productoRepo = productoRepo;
        this.ventaRepo = ventaRepo;
    }

    @Transactional // si algo falla, se revierte TODO (stock incluido)
    public Venta registrarVenta(VentaRequest request) {
        Venta venta = new Venta();
        BigDecimal total = BigDecimal.ZERO;

        for (ItemRequest item : request.items()) {
            // 1. Validar que el producto exista
            Producto p = productoRepo.findById(item.productoId())
                    .orElseThrow(() -> new ProductoNoEncontradoException(item.productoId()));

            // 2. Validar stock
            if (p.getStock() < item.cantidad()) {
                throw new StockInsuficienteException(p.getNombre(), p.getStock(), item.cantidad());
            }

            // 3. Descontar stock
            p.setStock(p.getStock() - item.cantidad());
            productoRepo.save(p);

            // 4. Calcular subtotal y sumar al total
            BigDecimal subtotal = p.getPrecio().multiply(BigDecimal.valueOf(item.cantidad()));
            total = total.add(subtotal);

            venta.getDetalles().add(new DetalleVenta(
                    p.getId(), p.getNombre(), item.cantidad(), p.getPrecio(), subtotal));
        }

        venta.setTotal(total);
        return ventaRepo.save(venta);
    }
}