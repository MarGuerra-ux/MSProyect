package cl.duoc.mesatech.solicitudes.exception;

/** Se lanza cuando se viola una regla de negocio (responde HTTP 400). */
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
