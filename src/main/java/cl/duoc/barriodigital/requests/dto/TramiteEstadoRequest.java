package cl.duoc.barriodigital.requests.dto;

public record TramiteEstadoRequest(
		String estado,
		String responsableAsignado,
		String observaciones
) {
}
