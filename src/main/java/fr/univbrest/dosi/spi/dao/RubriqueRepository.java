package fr.univbrest.dosi.spi.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.univbrest.dosi.spi.bean.Rubrique;

/**
 *
 * @author ASSABBAR
 *
 *         Cette interface est la repository de la creation ,consultation et la suppression des Rubriques standards
 */

@RepositoryRestResource(collectionResourceRel = "rubrique", path = "rubrique")
public interface RubriqueRepository extends PagingAndSortingRepository<Rubrique, Long> {

	public Integer getMaxIdRubrique();

}
