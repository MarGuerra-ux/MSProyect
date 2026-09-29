package cl.duoc.mesatech.solicitudes.controller;

import cl.duoc.mesatech.solicitudes.dto.CambioEstadoRequest;
import cl.duoc.mesatech.solicitudes.dto.SolicitudRequest;
import cl.duoc.mesatech.solicitudes.dto.SolicitudResponse;
import cl.duoc.mesatech.solicitudes.service.SolicitudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {

    private final SolicitudService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudResponse crear(@Valid @RequestBody SolicitudRequest request) {
        return service.crear(request);
    }

    /** Sin parámetros lista todas; con usuarioId filtra las de ese usuario. */
    @GetMapping
    public List<SolicitudResponse> listar(
            @RequestParam(name = "usuarioId", required = false) String usuarioId) {
        return service.listar(usuarioId);
    }

    @GetMapping("/{id}")
    public SolicitudResponse obtener(@PathVariable("id") Long id) {
        return service.obtener(id);
    }

    @PatchMapping("/{id}/estado")
    public SolicitudResponse cambiarEstado(
            @PathVariable("id") Long id,
            @Valid @RequestBody CambioEstadoRequest request) {
        return service.cambiarEstado(id, request.estado());
    }
}
