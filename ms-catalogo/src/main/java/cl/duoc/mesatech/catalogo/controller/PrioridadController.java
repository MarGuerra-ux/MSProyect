package cl.duoc.mesatech.catalogo.controller;

import cl.duoc.mesatech.catalogo.dto.PrioridadRequest;
import cl.duoc.mesatech.catalogo.model.Prioridad;
import cl.duoc.mesatech.catalogo.service.CatalogoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prioridades")
@RequiredArgsConstructor
public class PrioridadController {

    private final CatalogoService service;

    @GetMapping
    public List<Prioridad> listar() {
        return service.listarPrioridades();
    }

    @GetMapping("/{id}")
    public Prioridad obtener(@PathVariable("id") Long id) {
        return service.obtenerPrioridad(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prioridad crear(@Valid @RequestBody PrioridadRequest request) {
        return service.crearPrioridad(request);
    }

    @PutMapping("/{id}")
    public Prioridad actualizar(@PathVariable("id") Long id, @Valid @RequestBody PrioridadRequest request) {
        return service.actualizarPrioridad(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") Long id) {
        service.eliminarPrioridad(id);
    }
}
