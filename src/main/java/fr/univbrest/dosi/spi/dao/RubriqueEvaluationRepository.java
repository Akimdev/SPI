package fr.univbrest.dosi.spi.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.univbrest.dosi.spi.bean.RubriqueEvaluation;

/**
 * 
 * @author LAKRAA
 * interface repository pour rubrique evaluation 
 *
 */

@RepositoryRestResource(collectionResourceRel = "rubriqueEvaluation", path = "rubriqueEvaluation")
public interface RubriqueEvaluationRepository extends PagingAndSortingRepository<RubriqueEvaluation, Long>{

}
