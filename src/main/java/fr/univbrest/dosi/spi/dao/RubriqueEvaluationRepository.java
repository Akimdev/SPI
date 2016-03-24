package fr.univbrest.dosi.spi.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.univbrest.dosi.spi.bean.Evaluation;
import fr.univbrest.dosi.spi.bean.Rubrique;
import fr.univbrest.dosi.spi.bean.RubriqueEvaluation;

/**
 * 
 * @author LAKRAA
 * interface repository pour rubrique evaluation 
 *
 */

@RepositoryRestResource(collectionResourceRel = "rubriqueEvaluation", path = "rubriqueEvaluation")
public interface RubriqueEvaluationRepository extends PagingAndSortingRepository<RubriqueEvaluation, Long>{

	List<RubriqueEvaluation> findByIdEvaluation(@Param("idEvaluation")Evaluation idEvaluation);
	
}
