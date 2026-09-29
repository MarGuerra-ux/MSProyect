package cl.duoc.mesatech.catalogo.service;

import cl.duoc.mesatech.catalogo.dto.CategoriaRequest;
import cl.duoc.mesatech.catalogo.dto.PrioridadRequest;
import cl.duoc.mesatech.catalogo.exception.RecursoNoEncontradoException;
import cl.duoc.mesatech.catalogo.model.Categoria;
import cl.duoc.mesatech.catalogo.model.Prioridad;
import cl.duoc.mesatech.catalogo.repository.CategoriaRepository;
import cl.duoc.mesatech.catalogo.repository.PrioridadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final CategoriaRepository categoriaRepository;
    private final PrioridadRepository prioridadRepository;

    // ------------------------- Categorías -------------------------

    @Transactional(readOnly = true)
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll(Sort.by("nombre"));
    }

    @Transactional(readOnly = true)
    public Categoria obtenerCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría " + id + " no encontrada"));
    }

    @Transactional
    public Categoria crearCategoria(CategoriaRequest request) {
        Categoria categoria = new Categoria(null, request.nombre().trim(), request.descripcion());
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria actualizarCategoria(Long id, CategoriaRequest request) {
        Categoria categoria = obtenerCategoria(id);
        categoria.setNombre(request.nombre().trim());
        categoria.setDescripcion(request.descripcion());
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void eliminarCategoria(Long id) {
        categoriaRepository.delete(obtenerCategoria(id));
    }

    // ------------------------- Prioridades -------------------------

    @Transactional(readOnly = true)
    public List<Prioridad> listarPrioridades() {
        return prioridadRepository.findAll(Sort.by("nivel"));
    }

    @Transactional(readOnly = true)
    public Prioridad obtenerPrioridad(Long id) {
        return prioridadRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Prioridad " + id + " no encontrada"));
    }

    @Transactional
    public Prioridad crearPrioridad(PrioridadRequest request) {
        Prioridad prioridad = new Prioridad(null, request.nombre().trim(), request.nivel());
        return prioridadRepository.save(prioridad);
    }

    @Transactional
    public Prioridad actualizarPrioridad(Long id, PrioridadRequest request) {
        Prioridad prioridad = obtenerPrioridad(id);
        prioridad.setNombre(request.nombre().trim());
        prioridad.setNivel(request.nivel());
        return prioridadRepository.save(prioridad);
    }

    @Transactional
    public void eliminarPrioridad(Long id) {
        prioridadRepository.delete(obtenerPrioridad(id));
    }
}
