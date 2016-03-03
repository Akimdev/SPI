package fr.univbrest.dosi.spi.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.univbrest.dosi.spi.bean.Question;


/**
 * 
 * @author Othman
 *
 *Cette interface est la repository de la gestion de CRUD des questions standards
 */
@RepositoryRestResource(collectionResourceRel = "question", path = "question")
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long>{

}
