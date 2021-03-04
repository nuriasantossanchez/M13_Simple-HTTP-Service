package springBootSimpleHTTPService.controller.exception;

/**
 * Clase de la capa Controller, dentro del paquete Exception
 *
 * Extiende RuntimeException, de tipo unchecked.
 *
 * La exception es lanzada en la ejecucion de ciertos metodos, en los casos en los que
 * la peticion de recuperar un objeto de tipo Role pueda no devolver ningun resultado
 */
public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(Long id) {
        super("Could not find role " + id + "\nRole must be between 1 and 11");
    }

    public RoleNotFoundException(String title) {
        super("Could not find role " + title );
    }
}
