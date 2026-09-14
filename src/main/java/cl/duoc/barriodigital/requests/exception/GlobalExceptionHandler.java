package cl.duoc.barriodigital.requests.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// Centraliza el manejo de errores de todos los controllers.
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EstadoInvalidoException.class)
	public ResponseEntity<Map<String, String>> handleEstadoInvalido(EstadoInvalidoException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
	}

	@ExceptionHandler(TramiteNoEncontradoException.class)
	public ResponseEntity<Map<String, String>> handleTramiteNoEncontrado(TramiteNoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
	}

}
