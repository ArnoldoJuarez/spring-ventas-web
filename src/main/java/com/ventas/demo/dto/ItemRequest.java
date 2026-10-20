// dto/ItemRequest.java
package com.ventas.demo.dto;

import jakarta.validation.constraints.*;

public record ItemRequest(
        @NotNull(message = "El productoId es obligatorio") Long productoId,
        @NotNull @Min(value = 1, message = "La cantidad debe ser mayor a 0") Integer cantidad) { }