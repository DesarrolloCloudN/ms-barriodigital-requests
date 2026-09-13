package cl.duoc.barriodigital.requests.entity;

import java.util.EnumSet;
import java.util.Set;

/**
 * Máquina de estados de un {@link Tramite}, según el contrato (sección 5 de CONTRATO_BARRIODIGITAL.md):
 *
 * <pre>
 * INGRESADO   -&gt; ADMITIDO | RECHAZADO
 * ADMITIDO    -&gt; EN_GESTION | RECHAZADO
 * EN_GESTION  -&gt; EN_TERRENO | RECHAZADO
 * EN_TERRENO  -&gt; RESUELTO | RECHAZADO
 * RESUELTO, RECHAZADO -&gt; (estados finales, no se puede cambiar)
 * </pre>
 */
// Este enum es la máquina de estados del trámite. Cada estado sabe a qué
// otros estados puede pasar (su método siguientesValidos()). Así evitamos
// que un trámite salte de un estado a otro que no tiene sentido, por
// ejemplo pasar de INGRESADO directo a RESUELTO sin pasar por el resto.
public enum EstadoTramite {

	// INGRESADO: el vecino recién creó el trámite. Desde acá se puede
	// admitir (ADMITIDO) o rechazar (RECHAZADO).
	INGRESADO {
		@Override
		public Set<EstadoTramite> siguientesValidos() {
			return EnumSet.of(ADMITIDO, RECHAZADO);
		}
	},
	// ADMITIDO: un funcionario aceptó el trámite. Desde acá pasa a gestión
	// (EN_GESTION) o se puede rechazar.
	ADMITIDO {
		@Override
		public Set<EstadoTramite> siguientesValidos() {
			return EnumSet.of(EN_GESTION, RECHAZADO);
		}
	},
	// EN_GESTION: el funcionario está trabajando el trámite en la oficina.
	// Desde acá puede pasar a terreno (EN_TERRENO) o rechazarse.
	EN_GESTION {
		@Override
		public Set<EstadoTramite> siguientesValidos() {
			return EnumSet.of(EN_TERRENO, RECHAZADO);
		}
	},
	// EN_TERRENO: se necesita una visita o verificación fuera de la oficina.
	// Desde acá se puede resolver (RESUELTO) o rechazar.
	EN_TERRENO {
		@Override
		public Set<EstadoTramite> siguientesValidos() {
			return EnumSet.of(RESUELTO, RECHAZADO);
		}
	},
	// RESUELTO: estado final, el trámite terminó bien. No se puede cambiar
	// a ningún otro estado (por eso devuelve un set vacío).
	RESUELTO {
		@Override
		public Set<EstadoTramite> siguientesValidos() {
			return EnumSet.noneOf(EstadoTramite.class);
		}
	},
	// RECHAZADO: estado final, el trámite no procedió. Tampoco se puede
	// cambiar a otro estado desde acá.
	RECHAZADO {
		@Override
		public Set<EstadoTramite> siguientesValidos() {
			return EnumSet.noneOf(EstadoTramite.class);
		}
	};

	/**
	 * @return el conjunto de estados a los que se puede transicionar directamente desde este estado.
	 */
	public abstract Set<EstadoTramite> siguientesValidos();

	/**
	 * @param nuevoEstado el estado destino propuesto.
	 * @return true si la transición de este estado a {@code nuevoEstado} es válida.
	 */
	// Pregunta simple: "¿puedo pasar de mi estado actual a nuevoEstado?".
	// Se usa antes de guardar cualquier cambio de estado.
	public boolean puedeTransicionarA(EstadoTramite nuevoEstado) {
		return siguientesValidos().contains(nuevoEstado);
	}

}
