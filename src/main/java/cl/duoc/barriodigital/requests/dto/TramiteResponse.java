package cl.duoc.barriodigital.requests.dto;

import cl.duoc.barriodigital.requests.entity.Tramite;

import java.time.LocalDateTime;

// DTO que se devuelve al cliente (el bff) cuando se consulta o se modifica
// un trámite. Se usa en vez de exponer la entidad Tramite directamente.
public record TramiteResponse(
		Long id,
		Long tipoTramiteId,
		String vecinoId,
		String vecinoNombre,
		String descripcion,
		String estado,
		String funcionarioAsignado,
		String observaciones,
		LocalDateTime fechaIngreso,
		LocalDateTime fechaActualizacion
) {

	// Convierte una entidad Tramite (la que se guarda en la base de datos)
	// en un TramiteResponse (lo que se manda como respuesta HTTP).
	public static TramiteResponse from(Tramite tramite) {
		return new TramiteResponse(
				tramite.getId(),
				tramite.getTipoTramiteId(),
				tramite.getVecinoId(),
				tramite.getVecinoNombre(),
				tramite.getDescripcion(),
				tramite.getEstado().name(),
				tramite.getFuncionarioAsignado(),
				tramite.getObservaciones(),
				tramite.getFechaIngreso(),
				tramite.getFechaActualizacion()
		);
	}

}
