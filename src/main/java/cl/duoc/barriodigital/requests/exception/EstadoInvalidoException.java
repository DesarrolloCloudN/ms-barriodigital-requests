package cl.duoc.barriodigital.requests.exception;

/**
 * Se lanza cuando se solicita una transición de estado de un {@link cl.duoc.barriodigital.requests.entity.Tramite}
 * que no es válida según la máquina de estados. El {@code @RestControllerAdvice} la traduce a HTTP 409.
 */
// Excepción propia (custom) para avisar que alguien pidió un cambio de
// estado que no está permitido. El GlobalExceptionHandler la atrapa y
// responde con un HTTP 409 (Conflict).
public class EstadoInvalidoException extends RuntimeException {

	public EstadoInvalidoException(String message) {
		super(message);
	}

}
