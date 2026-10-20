// dto/VentaRequest.java
package com.ventas.demo.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record VentaRequest(
        @NotEmpty(message = "La venta debe tener al menos un producto")
        List<@Valid ItemRequest> items) { }