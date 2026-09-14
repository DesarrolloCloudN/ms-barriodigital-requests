package cl.duoc.barriodigital.requests.repository;

import cl.duoc.barriodigital.requests.entity.Tramite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TramiteRepository extends JpaRepository<Tramite, Long> {

	// Spring Data genera la consulta a partir del nombre del método.
	List<Tramite> findByVecinoId(String vecinoId);

}
