package fr.univbrest.dosi.spi.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.univbrest.dosi.spi.bean.ElementConstitutif;
import fr.univbrest.dosi.spi.bean.ElementConstitutifPK;
import fr.univbrest.dosi.spi.bean.UniteEnseignementPK;

/**
 * @author kenza ABOUAKIL
 *
 */
@RepositoryRestResource(collectionResourceRel = "elementConstitutif", path = "elementConstitutif")
public interface ElementConstitutifRepository extends PagingAndSortingRepository<ElementConstitutif, ElementConstitutifPK> {

	public List<ElementConstitutif> findByCodeFormation(@Param("codeFormation") String codeFormation);
	public List<ElementConstitutif>  findByCodeUe(@Param("codeUe") String codeUe);
	public List<ElementConstitutif>  findEcByCodeUe(@Param("codeUe") String codeUe, @Param("codeFormation") String codeFormation);
}