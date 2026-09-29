package cl.duoc.mesatech.solicitudes.service;

import cl.duoc.mesatech.solicitudes.dto.SolicitudRequest;
import cl.duoc.mesatech.solicitudes.dto.SolicitudResponse;
import cl.duoc.mesatech.solicitudes.exception.RecursoNoEncontradoException;
import cl.duoc.mesatech.solicitudes.exception.ReglaNegocioException;
import cl.duoc.mesatech.solicitudes.model.EstadoSolicitud;
import cl.duoc.mesatech.solicitudes.model.Solicitud;
import cl.duoc.mesatech.solicitudes.repository.SolicitudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudService {

    static final String MSG_RESUELTA =
            "Una solicitud solo puede pasar a RESUELTA si se encuentra EN_PROCESO";

    private final SolicitudRepository repository;

    @Transactional
    public SolicitudResponse crear(SolicitudRequest request) {
        Solicitud solicitud = new Solicitud();
        solicitud.setTitulo(request.titulo().trim());
        solicitud.setDescripcion(request.descripcion().trim());
        solicitud.setUsuarioId(request.usuarioId());
        solicitud.setCategoriaId(request.categoriaId());
        solicitud.setPrioridadId(request.prioridadId());
        solicitud.setEstado(EstadoSolicitud.CREADA);
        return SolicitudResponse.from(repository.save(solicitud));
    }

    @Transactional(readOnly = true)
    public List<SolicitudResponse> listar(String usuarioId) {
        List<Solicitud> solicitudes = (usuarioId == null || usuarioId.isBlank())
                ? repository.findAllByOrderByFechaCreacionDesc()
                : repository.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId);
        return solicitudes.stream().map(SolicitudResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public SolicitudResponse obtener(Long id) {
        return SolicitudResponse.from(buscar(id));
    }

    /**
     * Cambia el estado aplicando las reglas del flujo de negocio.
     *
     * Regla obligatoria: una solicitud solo puede pasar a RESUELTA si su estado
     * actual es EN_PROCESO (responde HTTP 400 en caso contrario).
     */
    @Transactional
    public SolicitudResponse cambiarEstado(Long id, EstadoSolicitud nuevoEstado) {
        Solicitud solicitud = buscar(id);
        EstadoSolicitud actual = solicitud.getEstado();

        if (nuevoEstado == EstadoSolicitud.RESUELTA && actual != EstadoSolicitud.EN_PROCESO) {
            throw new ReglaNegocioException(MSG_RESUELTA);
        }

        if (!actual.puedePasarA(nuevoEstado)) {
            throw new ReglaNegocioException(
                    "Transición de estado no permitida: " + actual + " -> " + nuevoEstado);
        }

        solicitud.setEstado(nuevoEstado);
        return SolicitudResponse.from(repository.save(solicitud));
    }

    private Solicitud buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitud " + id + " no encontrada"));
    }
}
