package cl.duoc.barriodigital.requests.repository;

import cl.duoc.barriodigital.requests.entity.Tramite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository: es la capa que habla con la base de datos. Al extender de
// JpaRepository ya vienen gratis los métodos típicos (guardar, buscar por
// id, buscar todos, eliminar, etc.), sin tener que escribir SQL a mano.
public interface TramiteRepository extends JpaRepository<Tramite, Long> {

	// Spring Data genera automáticamente la consulta a partir del nombre
	// del método: busca todos los trámites de un vecino en particular.
	List<Tramite> findByVecinoId(String vecinoId);

}
