package springBootSimpleHTTPService.controller.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(Long id) {
        super("Could not find role " + id + "\nRole must be between 1 and 11");
    }

    public RoleNotFoundException(String title) {
        super("Could not find role " + title );
    }
}
