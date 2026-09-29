package cl.duoc.mesatech.solicitudes.dto;

import cl.duoc.mesatech.solicitudes.model.EstadoSolicitud;
import cl.duoc.mesatech.solicitudes.model.Solicitud;

import java.time.LocalDateTime;

public record SolicitudResponse(
        Long id,
        String titulo,
        String descripcion,
        String usuarioId,
        EstadoSolicitud estado,
        Long categoriaId,
        Long prioridadId,
        LocalDateTime fechaCreacion
) {

    public static SolicitudResponse from(Solicitud s) {
        return new SolicitudResponse(
                s.getId(),
                s.getTitulo(),
                s.getDescripcion(),
                s.getUsuarioId(),
                s.getEstado(),
                s.getCategoriaId(),
                s.getPrioridadId(),
                s.getFechaCreacion()
        );
    }
}
