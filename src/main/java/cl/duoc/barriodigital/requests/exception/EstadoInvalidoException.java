package cl.duoc.barriodigital.requests.exception;

// Transición de estado inválida; el GlobalExceptionHandler responde HTTP 409.
public class EstadoInvalidoException extends RuntimeException {

	public EstadoInvalidoException(String message) {
		super(message);
	}

}
