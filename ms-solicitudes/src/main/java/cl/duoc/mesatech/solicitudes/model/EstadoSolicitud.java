package cl.duoc.mesatech.solicitudes.model;

import java.util.EnumSet;
import java.util.Set;

/**
 * Flujo de estados de una solicitud:
 *
 * CREADA -> ASIGNADA -> EN_PROCESO -> RESUELTA -> CERRADA
 *
 * Una solicitud puede pasar a CANCELADA mientras no haya sido RESUELTA.
 * CERRADA y CANCELADA son estados finales.
 */
public enum EstadoSolicitud {
    CREADA,
    ASIGNADA,
    EN_PROCESO,
    RESUELTA,
    CERRADA,
    CANCELADA;

    public Set<EstadoSolicitud> siguientesPermitidos() {
        return switch (this) {
            case CREADA -> EnumSet.of(ASIGNADA, CANCELADA);
            case ASIGNADA -> EnumSet.of(EN_PROCESO, CANCELADA);
            case EN_PROCESO -> EnumSet.of(RESUELTA, CANCELADA);
            case RESUELTA -> EnumSet.of(CERRADA);
            case CERRADA, CANCELADA -> EnumSet.noneOf(EstadoSolicitud.class);
        };
    }

    public boolean puedePasarA(EstadoSolicitud destino) {
        return siguientesPermitidos().contains(destino);
    }
}
