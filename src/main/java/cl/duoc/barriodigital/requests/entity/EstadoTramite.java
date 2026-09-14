package cl.duoc.barriodigital.requests.entity;

import java.util.EnumSet;
import java.util.Set;

/** Máquina de estados de un {@link Tramite} (ver sección 5 del contrato). */
// Cada estado define a qué otros estados puede pasar (siguientesValidos()).
public enum EstadoTramite {

	// Recién creado: puede admitirse o rechazarse.
	INGRESADO {
		@Override
		public Set<EstadoTramite> siguientesValidos() {
			return EnumSet.of(ADMITIDO, RECHAZADO);
		}
	},
	// Aceptado por un administrador: pasa a gestión o se rechaza.
	ADMITIDO {
		@Override
		public Set<EstadoTramite> siguientesValidos() {
			return EnumSet.of(EN_GESTION, RECHAZADO);
		}
	},
	// Se está trabajando en oficina: pasa a terreno o se rechaza.
	EN_GESTION {
		@Override
		public Set<EstadoTramite> siguientesValidos() {
			return EnumSet.of(EN_TERRENO, RECHAZADO);
		}
	},
	// Requiere visita o verificación fuera de oficina: se resuelve o rechaza.
	EN_TERRENO {
		@Override
		public Set<EstadoTramite> siguientesValidos() {
			return EnumSet.of(RESUELTO, RECHAZADO);
		}
	},
	// Estado final: el trámite terminó bien, ya no cambia.
	RESUELTO {
		@Override
		public Set<EstadoTramite> siguientesValidos() {
			return EnumSet.noneOf(EstadoTramite.class);
		}
	},
	// Estado final: el trámite no procedió, ya no cambia.
	RECHAZADO {
		@Override
		public Set<EstadoTramite> siguientesValidos() {
			return EnumSet.noneOf(EstadoTramite.class);
		}
	};

	/** Estados a los que se puede pasar directamente desde este. */
	public abstract Set<EstadoTramite> siguientesValidos();

	// Indica si se puede pasar del estado actual a nuevoEstado.
	public boolean puedeTransicionarA(EstadoTramite nuevoEstado) {
		return siguientesValidos().contains(nuevoEstado);
	}

}
