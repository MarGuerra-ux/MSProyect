package cl.duoc.mesatech.solicitudes.service;

import cl.duoc.mesatech.solicitudes.dto.SolicitudResponse;
import cl.duoc.mesatech.solicitudes.exception.ReglaNegocioException;
import cl.duoc.mesatech.solicitudes.model.EstadoSolicitud;
import cl.duoc.mesatech.solicitudes.model.Solicitud;
import cl.duoc.mesatech.solicitudes.repository.SolicitudRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock
    private SolicitudRepository repository;

    @InjectMocks
    private SolicitudService service;

    private Solicitud solicitudEn(EstadoSolicitud estado) {
        Solicitud s = new Solicitud();
        s.setId(1L);
        s.setTitulo("No funciona la VPN");
        s.setDescripcion("Error al conectar desde casa");
        s.setUsuarioId("oid-123");
        s.setCategoriaId(1L);
        s.setPrioridadId(2L);
        s.setEstado(estado);
        s.setFechaCreacion(LocalDateTime.now());
        return s;
    }

    @ParameterizedTest
    @EnumSource(value = EstadoSolicitud.class, names = {"CREADA", "ASIGNADA", "RESUELTA", "CERRADA", "CANCELADA"})
    void noPermitePasarAResueltaSiNoEstaEnProceso(EstadoSolicitud estadoActual) {
        when(repository.findById(1L)).thenReturn(Optional.of(solicitudEn(estadoActual)));

        ReglaNegocioException ex = assertThrows(ReglaNegocioException.class,
                () -> service.cambiarEstado(1L, EstadoSolicitud.RESUELTA));

        assertEquals("Una solicitud solo puede pasar a RESUELTA si se encuentra EN_PROCESO", ex.getMessage());
        verify(repository, never()).save(any(Solicitud.class));
    }

    @Test
    void permitePasarAResueltaDesdeEnProceso() {
        when(repository.findById(1L)).thenReturn(Optional.of(solicitudEn(EstadoSolicitud.EN_PROCESO)));
        when(repository.save(any(Solicitud.class))).thenAnswer(inv -> inv.getArgument(0));

        SolicitudResponse respuesta = service.cambiarEstado(1L, EstadoSolicitud.RESUELTA);

        assertEquals(EstadoSolicitud.RESUELTA, respuesta.estado());
    }

    @Test
    void permiteCancelarUnaSolicitudCreada() {
        when(repository.findById(1L)).thenReturn(Optional.of(solicitudEn(EstadoSolicitud.CREADA)));
        when(repository.save(any(Solicitud.class))).thenAnswer(inv -> inv.getArgument(0));

        SolicitudResponse respuesta = service.cambiarEstado(1L, EstadoSolicitud.CANCELADA);

        assertEquals(EstadoSolicitud.CANCELADA, respuesta.estado());
    }

    @Test
    void noPermiteSaltarseEstadosDelFlujo() {
        when(repository.findById(1L)).thenReturn(Optional.of(solicitudEn(EstadoSolicitud.CREADA)));

        assertThrows(ReglaNegocioException.class,
                () -> service.cambiarEstado(1L, EstadoSolicitud.EN_PROCESO));
    }

    @Test
    void noPermiteReabrirUnaSolicitudCerrada() {
        when(repository.findById(1L)).thenReturn(Optional.of(solicitudEn(EstadoSolicitud.CERRADA)));

        assertThrows(ReglaNegocioException.class,
                () -> service.cambiarEstado(1L, EstadoSolicitud.CREADA));
    }
}
