package cl.duoc.barriodigital.requests.dto;

public record TramiteEstadoRequest(
		String estado,
		String funcionarioAsignado,
		String observaciones
) {
}
