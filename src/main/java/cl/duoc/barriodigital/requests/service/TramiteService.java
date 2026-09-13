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

// Service: acá vive la lógica de negocio de los trámites (crear, listar,
// cambiar de estado). El controller solo delega el trabajo a esta clase.
@Service
public class TramiteService {

	private final TramiteRepository tramiteRepository;

	public TramiteService(TramiteRepository tramiteRepository) {
		this.tramiteRepository = tramiteRepository;
	}

	// Si viene un vecinoId, filtra los trámites de ese vecino. Si no viene
	// (o viene vacío), devuelve todos los trámites.
	public List<Tramite> listar(String vecinoId) {
		if (vecinoId != null && !vecinoId.isBlank()) {
			return tramiteRepository.findByVecinoId(vecinoId);
		}
		return tramiteRepository.findAll();
	}

	// Busca un trámite por id. Si no existe, lanza una excepción que más
	// adelante el GlobalExceptionHandler convierte en un HTTP 404.
	public Tramite obtener(Long id) {
		return tramiteRepository.findById(id)
				.orElseThrow(() -> new TramiteNoEncontradoException("No existe un trámite con id " + id));
	}

	// Crea un trámite nuevo a partir de los datos que llegaron en el
	// request. El constructor de Tramite ya se encarga de dejarlo en
	// estado INGRESADO.
	public Tramite crear(TramiteCrearRequest request) {
		Tramite tramite = new Tramite(
				request.tipoTramiteId(),
				request.vecinoId(),
				request.vecinoNombre(),
				request.descripcion()
		);
		return tramiteRepository.save(tramite);
	}

	// Cambia el estado de un trámite existente. Antes de guardar el cambio,
	// revisamos que la transición sea válida (por ejemplo, no se puede
	// pasar de INGRESADO directo a RESUELTO). Si no es válida, se lanza
	// EstadoInvalidoException y no se guarda nada.
	public Tramite cambiarEstado(Long id, TramiteEstadoRequest request) {
		Tramite tramite = obtener(id);
		EstadoTramite nuevoEstado = parsearEstado(request.estado());

		if (!tramite.getEstado().puedeTransicionarA(nuevoEstado)) {
			throw new EstadoInvalidoException(
					"No se puede cambiar el trámite %d de %s a %s".formatted(id, tramite.getEstado(), nuevoEstado));
		}

		tramite.setEstado(nuevoEstado);
		// El funcionario y las observaciones son opcionales: solo se
		// actualizan si vinieron en el request.
		if (request.funcionarioAsignado() != null) {
			tramite.setFuncionarioAsignado(request.funcionarioAsignado());
		}
		if (request.observaciones() != null) {
			tramite.setObservaciones(request.observaciones());
		}
		tramite.setFechaActualizacion(LocalDateTime.now());

		return tramiteRepository.save(tramite);
	}

	// Convierte el texto del estado (por ejemplo "ADMITIDO") en el valor
	// del enum EstadoTramite. Si viene vacío o no coincide con ningún
	// estado válido, se lanza EstadoInvalidoException.
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
