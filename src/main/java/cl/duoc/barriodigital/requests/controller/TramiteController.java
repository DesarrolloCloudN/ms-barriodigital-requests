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

/** Endpoints internos de gestión de trámites, consumidos por ms-barriodigital-bff. */
@RestController
@RequestMapping("/api/requests")
public class TramiteController {

	private final TramiteService tramiteService;

	public TramiteController(TramiteService tramiteService) {
		this.tramiteService = tramiteService;
	}

	@GetMapping
	public List<TramiteResponse> listar(@RequestParam(required = false) String vecinoId) {
		return tramiteService.listar(vecinoId).stream()
				.map(TramiteResponse::from)
				.toList();
	}

	@GetMapping("/{id}")
	public TramiteResponse obtener(@PathVariable Long id) {
		return TramiteResponse.from(tramiteService.obtener(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TramiteResponse crear(@RequestBody TramiteCrearRequest request) {
		return TramiteResponse.from(tramiteService.crear(request));
	}

	// La validación de la transición de estado la hace el service.
	@PutMapping("/{id}/estado")
	public TramiteResponse cambiarEstado(@PathVariable Long id, @RequestBody TramiteEstadoRequest request) {
		return TramiteResponse.from(tramiteService.cambiarEstado(id, request));
	}

}
