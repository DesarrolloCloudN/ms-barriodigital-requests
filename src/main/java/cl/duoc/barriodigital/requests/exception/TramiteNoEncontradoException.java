package cl.duoc.barriodigital.requests.exception;

// No existe un trámite con el id buscado; el handler responde HTTP 404.
public class TramiteNoEncontradoException extends RuntimeException {

	public TramiteNoEncontradoException(String message) {
		super(message);
	}

}
