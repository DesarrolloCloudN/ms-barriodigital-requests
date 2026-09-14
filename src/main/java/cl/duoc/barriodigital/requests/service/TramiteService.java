package cl.duoc.barriodigital.requests.service;

import cl.duoc.barriodigital.requests.dto.TramiteCrearRequest;
import cl.duoc.barriodigital.requests.dto.TramiteEstadoRequest;
import cl.duoc.barriodigital.requests.entity.EstadoTramite;
import cl.duoc.barriodigital.requests.entity.Tramite;
import cl.duoc.barriodigital.requests.exception.EstadoInvalidoException;
import cl.duoc.barriodigital.requests.exception.TramiteNoEncontradoException;
import cl.duoc.barriodigital.requests.repository.TramiteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// Lógica de negocio de los trámites; el controller solo delega aquí.
@Service
public class TramiteService {

	private final TramiteRepository tramiteRepository;

	public TramiteService(TramiteRepository tramiteRepository) {
		this.tramiteRepository = tramiteRepository;
	}

	// Si viene vecinoId filtra por ese vecino; si no, devuelve todos.
	public List<Tramite> listar(String vecinoId) {
		if (vecinoId != null && !vecinoId.isBlank()) {
			return tramiteRepository.findByVecinoId(vecinoId);
		}
		return tramiteRepository.findAll();
	}

	public Tramite obtener(Long id) {
		return tramiteRepository.findById(id)
				.orElseThrow(() -> new TramiteNoEncontradoException("No existe un trámite con id " + id));
	}

	public Tramite crear(TramiteCrearRequest request) {
		Tramite tramite = new Tramite(
				request.tipoTramiteId(),
				request.vecinoId(),
				request.vecinoNombre(),
				request.descripcion()
		);
		return tramiteRepository.save(tramite);
	}

	// Valida que la transición sea permitida antes de guardar el cambio.
	public Tramite cambiarEstado(Long id, TramiteEstadoRequest request) {
		Tramite tramite = obtener(id);
		EstadoTramite nuevoEstado = parsearEstado(request.estado());

		if (!tramite.getEstado().puedeTransicionarA(nuevoEstado)) {
			throw new EstadoInvalidoException(
					"No se puede cambiar el trámite %d de %s a %s".formatted(id, tramite.getEstado(), nuevoEstado));
		}

		tramite.setEstado(nuevoEstado);
		// El responsable y las observaciones son opcionales: solo se actualizan si vienen.
		if (request.responsableAsignado() != null) {
			tramite.setResponsableAsignado(request.responsableAsignado());
		}
		if (request.observaciones() != null) {
			tramite.setObservaciones(request.observaciones());
		}
		tramite.setFechaActualizacion(LocalDateTime.now());

		return tramiteRepository.save(tramite);
	}

	// Convierte el texto a EstadoTramite; si es inválido lanza la excepción.
	private EstadoTramite parsearEstado(String estado) {
		if (estado == null || estado.isBlank()) {
			throw new EstadoInvalidoException("El campo 'estado' es obligatorio");
		}
		try {
			return EstadoTramite.valueOf(estado);
		} catch (IllegalArgumentException ex) {
			throw new EstadoInvalidoException("Estado desconocido: " + estado);
		}
	}

}
