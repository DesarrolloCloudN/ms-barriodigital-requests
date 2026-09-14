package cl.duoc.barriodigital.requests.dto;

import cl.duoc.barriodigital.requests.entity.Tramite;

import java.time.LocalDateTime;

// DTO de respuesta hacia el bff; evita exponer la entidad Tramite directamente.
public record TramiteResponse(
		Long id,
		Long tipoTramiteId,
		String vecinoId,
		String vecinoNombre,
		String descripcion,
		String estado,
		String responsableAsignado,
		String observaciones,
		LocalDateTime fechaIngreso,
		LocalDateTime fechaActualizacion
) {

	public static TramiteResponse from(Tramite tramite) {
		return new TramiteResponse(
				tramite.getId(),
				tramite.getTipoTramiteId(),
				tramite.getVecinoId(),
				tramite.getVecinoNombre(),
				tramite.getDescripcion(),
				tramite.getEstado().name(),
				tramite.getResponsableAsignado(),
				tramite.getObservaciones(),
				tramite.getFechaIngreso(),
				tramite.getFechaActualizacion()
		);
	}

}
