package cl.duoc.barriodigital.requests.dto;

public record TramiteCrearRequest(
		Long tipoTramiteId,
		String vecinoId,
		String vecinoNombre,
		String descripcion
) {
}
