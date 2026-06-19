package cl.duoc.proveedores_service.exception;

public class NombreProveedorExistenteException extends RuntimeException {
    public NombreProveedorExistenteException(String message) {
        super(message);
    }
}
