package fr.univbrest.dosi.spi.dao;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestBody;
import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.UniteEnseignement;

/**
 * @author DOSI
 *
 */
@RepositoryRestResource(collectionResourceRel = "enseignant", path = "enseignant")
public interface EnseignantRepository extends PagingAndSortingRepository<Enseignant, Integer> {
	/**
	 * 
	 * @param nom
	 *            de l'enseignant
	 * @return liste des enseignant
	 */
	List<Enseignant> findByNom(@Param("nom") String nom);

	@Override
	Enseignant save(@RequestBody Enseignant ens);
}