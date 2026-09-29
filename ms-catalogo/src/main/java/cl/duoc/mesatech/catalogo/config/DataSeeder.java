package cl.duoc.mesatech.catalogo.config;

import cl.duoc.mesatech.catalogo.model.Categoria;
import cl.duoc.mesatech.catalogo.model.Prioridad;
import cl.duoc.mesatech.catalogo.repository.CategoriaRepository;
import cl.duoc.mesatech.catalogo.repository.PrioridadRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/** Carga datos iniciales solo si las tablas están vacías (útil para la demo). */
@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner cargarCatalogoInicial(CategoriaRepository categorias, PrioridadRepository prioridades) {
        return args -> {
            if (categorias.count() == 0) {
                categorias.saveAll(List.of(
                        new Categoria(null, "Hardware", "Fallas de equipos, periféricos e impresoras"),
                        new Categoria(null, "Software", "Instalación, licencias y errores de aplicaciones"),
                        new Categoria(null, "Redes", "Conectividad, VPN y Wi-Fi"),
                        new Categoria(null, "Accesos", "Cuentas, contraseñas y permisos")
                ));
            }
            if (prioridades.count() == 0) {
                prioridades.saveAll(List.of(
                        new Prioridad(null, "Baja", 1),
                        new Prioridad(null, "Media", 2),
                        new Prioridad(null, "Alta", 3),
                        new Prioridad(null, "Crítica", 4)
                ));
            }
        };
    }
}
