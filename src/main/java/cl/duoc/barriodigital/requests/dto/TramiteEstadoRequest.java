package cl.duoc.barriodigital.requests.dto;

/**
 * Body de {@code PUT /api/requests/{id}/estado}.
 */
// DTO que llega cuando alguien quiere cambiar el estado de un trámite.
// "estado" es el nuevo estado en texto (por ejemplo "ADMITIDO").
public record TramiteEstadoRequest(
		String estado,
		String funcionarioAsignado,
		String observaciones
) {
}
