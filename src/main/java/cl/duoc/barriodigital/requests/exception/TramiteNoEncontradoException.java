package cl.duoc.barriodigital.requests.exception;

/**
 * Se lanza cuando se busca un {@link cl.duoc.barriodigital.requests.entity.Tramite} por id y no existe.
 * El {@code @RestControllerAdvice} la traduce a HTTP 404.
 */
// Excepción propia para cuando buscamos un trámite por id y no lo
// encontramos en la base de datos.
public class TramiteNoEncontradoException extends RuntimeException {

	public TramiteNoEncontradoException(String message) {
		super(message);
	}

}
