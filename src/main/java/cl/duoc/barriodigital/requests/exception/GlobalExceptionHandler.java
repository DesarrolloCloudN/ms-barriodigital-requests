package cl.duoc.barriodigital.requests.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// Esta clase centraliza el manejo de errores de toda la aplicación.
// @RestControllerAdvice hace que Spring la use automáticamente cada vez
// que un controller lanza una de estas excepciones, sin tener que poner
// try/catch en cada método del controller.
@RestControllerAdvice
public class GlobalExceptionHandler {

	// Si se intenta un cambio de estado inválido, respondemos 409 Conflict
	// con un mensaje simple explicando el error.
	@ExceptionHandler(EstadoInvalidoException.class)
	public ResponseEntity<Map<String, String>> handleEstadoInvalido(EstadoInvalidoException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
	}

	// Si se busca un trámite que no existe, respondemos 404 Not Found.
	@ExceptionHandler(TramiteNoEncontradoException.class)
	public ResponseEntity<Map<String, String>> handleTramiteNoEncontrado(TramiteNoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
	}

}
