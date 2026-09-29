package cl.duoc.mesatech.catalogo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PrioridadRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 80, message = "El nombre no puede superar los 80 caracteres")
        String nombre,

        @NotNull(message = "El nivel es obligatorio")
        @Min(value = 1, message = "El nivel mínimo es 1")
        Integer nivel
) {
}
