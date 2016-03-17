package fr.univbrest.dosi.spi.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.bean.Question;

/**
 *
 * @author Othman
 *
 *         Cette interface est la repository de la gestion de CRUD des questions standards
 */
@RepositoryRestResource(collectionResourceRel = "question", path = "question")
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
	/**
	 * @author Youssef Recherche le qualificatif correspondant à une question
	 * @param idQuestion
	 * @return
	 */
	public Qualificatif findQualificatif(@Param("idQuestion") Long idQuestion);

	/**
	 * @author Kenza ABOUAKIL
	 * @return
	 */
	public Integer getMaxIdQuestion();
}
