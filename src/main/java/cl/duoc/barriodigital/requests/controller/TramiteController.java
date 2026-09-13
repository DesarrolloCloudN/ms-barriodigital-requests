package cl.duoc.barriodigital.requests.controller;

import cl.duoc.barriodigital.requests.dto.TramiteCrearRequest;
import cl.duoc.barriodigital.requests.dto.TramiteEstadoRequest;
import cl.duoc.barriodigital.requests.dto.TramiteResponse;
import cl.duoc.barriodigital.requests.service.TramiteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints internos (sin seguridad propia, ver sección 3 del contrato) de gestión de trámites.
 * Consumidos únicamente por ms-barriodigital-bff.
 */
// Controller: es la puerta de entrada HTTP. Recibe las peticiones y le pasa
// el trabajo al TramiteService, que tiene la lógica de verdad.
@RestController
@RequestMapping("/api/requests")
public class TramiteController {

	private final TramiteService tramiteService;

	// Spring inyecta automáticamente el service por el constructor.
	public TramiteController(TramiteService tramiteService) {
		this.tramiteService = tramiteService;
	}

	// GET /api/requests -> lista todos los trámites, o solo los de un vecino
	// si viene el parámetro vecinoId en la URL.
	@GetMapping
	public List<TramiteResponse> listar(@RequestParam(required = false) String vecinoId) {
		return tramiteService.listar(vecinoId).stream()
				.map(TramiteResponse::from)
				.toList();
	}

	// GET /api/requests/{id} -> devuelve un solo trámite buscado por su id.
	@GetMapping("/{id}")
	public TramiteResponse obtener(@PathVariable Long id) {
		return TramiteResponse.from(tramiteService.obtener(id));
	}

	// POST /api/requests -> crea un trámite nuevo (queda en estado INGRESADO).
	// Devuelve HTTP 201 (CREATED) cuando sale bien.
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TramiteResponse crear(@RequestBody TramiteCrearRequest request) {
		return TramiteResponse.from(tramiteService.crear(request));
	}

	// PUT /api/requests/{id}/estado -> cambia el estado de un trámite (por
	// ejemplo de INGRESADO a ADMITIDO). El service valida que el cambio sea
	// válido según la máquina de estados.
	@PutMapping("/{id}/estado")
	public TramiteResponse cambiarEstado(@PathVariable Long id, @RequestBody TramiteEstadoRequest request) {
		return TramiteResponse.from(tramiteService.cambiarEstado(id, request));
	}

}
