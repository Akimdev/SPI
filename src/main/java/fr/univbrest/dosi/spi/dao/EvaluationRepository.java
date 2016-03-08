package fr.univbrest.dosi.spi.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.univbrest.dosi.spi.bean.Evaluation;
/**
 * 
 * @author Othman
 *
 */

@RepositoryRestResource(collectionResourceRel = "evaluation", path = "evaluation")
public interface EvaluationRepository extends PagingAndSortingRepository<Evaluation, Long>{
   Evaluation findByIdEvaluation(@Param("idEvaluation") Long idEvaluation);
}
