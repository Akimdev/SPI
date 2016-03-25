package fr.univbrest.dosi.spi.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Evaluation;

/**
 * 
 * @author Othman
 *
 */

@RepositoryRestResource(collectionResourceRel = "evaluation", path = "evaluation")
public interface EvaluationRepository extends PagingAndSortingRepository<Evaluation, Integer>{

   Evaluation findByIdEvaluation(@Param("idEvaluation") Integer idEvaluation);
   List<Evaluation> findByNoEnseignant(@Param("noEnseignant")Enseignant noEnseignant);
   List<Evaluation> findByFormationAndAnnee(@Param("code_formation")String code_formation, @Param("annee")String anneeUniversitaire);
}