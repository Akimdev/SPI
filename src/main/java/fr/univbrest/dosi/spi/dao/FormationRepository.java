package fr.univbrest.dosi.spi.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.univbrest.dosi.spi.bean.Formation;

/**
 * @author 
 *
 */
@RepositoryRestResource(collectionResourceRel = "formation", path = "formation")
public interface FormationRepository extends PagingAndSortingRepository<Formation, String> {

	@Override
	public List<Formation> findAll();

	public Formation findByCodeFormation(@Param("codeFormation") String codeFormation);
}