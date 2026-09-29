package cl.duoc.mesatech.catalogo.controller;

import cl.duoc.mesatech.catalogo.dto.CategoriaRequest;
import cl.duoc.mesatech.catalogo.model.Categoria;
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
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CatalogoService service;

    @GetMapping
    public List<Categoria> listar() {
        return service.listarCategorias();
    }

    @GetMapping("/{id}")
    public Categoria obtener(@PathVariable("id") Long id) {
        return service.obtenerCategoria(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria crear(@Valid @RequestBody CategoriaRequest request) {
        return service.crearCategoria(request);
    }

    @PutMapping("/{id}")
    public Categoria actualizar(@PathVariable("id") Long id, @Valid @RequestBody CategoriaRequest request) {
        return service.actualizarCategoria(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") Long id) {
        service.eliminarCategoria(id);
    }
}
