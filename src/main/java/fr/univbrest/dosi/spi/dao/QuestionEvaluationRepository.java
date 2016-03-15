package fr.univbrest.dosi.spi.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.univbrest.dosi.spi.bean.QuestionEvaluation;


@RepositoryRestResource(collectionResourceRel = "questionEvaluation", path = "questionEvaluation")
public interface QuestionEvaluationRepository extends PagingAndSortingRepository<QuestionEvaluation, Long>{

}
