package com.ventas.demo.controller;

import com.ventas.demo.entity.Producto;
import com.ventas.demo.exception.ProductoNoEncontradoException;
import com.ventas.demo.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository repo;

    public ProductoController(ProductoRepository repo) {
        this.repo = repo;
    }

    // READ: listar todos
    @GetMapping
    public List<Producto> listar() {
        return repo.findAll();
    }

    // READ: buscar uno por id
    @GetMapping("/{id}")
    public Producto buscar(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto p) {
        p.setId(null); // evita que un id enviado en el JSON sobrescriba un producto existente
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(p));
    }

    // UPDATE
    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @Valid @RequestBody Producto datos) {
        Producto existente = repo.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        existente.setCodigo(datos.getCodigo());
        existente.setNombre(datos.getNombre());
        existente.setPrecio(datos.getPrecio());
        existente.setStock(datos.getStock());

        return repo.save(existente);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ProductoNoEncontradoException(id);
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}