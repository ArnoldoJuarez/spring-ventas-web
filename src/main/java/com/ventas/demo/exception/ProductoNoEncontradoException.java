// exception/ProductoNoEncontradoException.java
package com.ventas.demo.exception;

public class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException(Long id) {
        super("El producto con id " + id + " no existe");
    }
}