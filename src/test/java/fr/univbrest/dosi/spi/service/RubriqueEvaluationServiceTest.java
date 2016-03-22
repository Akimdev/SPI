package fr.univbrest.dosi.spi.service;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Evaluation;
import fr.univbrest.dosi.spi.bean.Rubrique;
import fr.univbrest.dosi.spi.bean.RubriqueEvaluation;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RubriqueEvaluationServiceTest {

	@Autowired
	RubriqueEvaluationService rubriqueEvaluationService;
	
	@Autowired
	EvaluationService evaluationService;
	
	@Autowired
	RubriqueService rubriqueService;
	
	@Test
	public void addRubriqueEvaluationTest(){
		RubriqueEvaluation rubriqueEvaluation =new RubriqueEvaluation();
		final long idrubEva=6;
		final long idrub=1;
		Evaluation eva =evaluationService.getEvaluation(1);
		Rubrique rub= rubriqueService.getRubrique(1L);
		rubriqueEvaluation.setDesignation("designation");
		rubriqueEvaluation.setIdRubrique(rub);
		rubriqueEvaluation.setIdEvaluation(eva);
		rubriqueEvaluation.setIdRubriqueEvaluation(idrubEva);
		rubriqueEvaluation.setOrdre((short)8);
		rubriqueEvaluationService.addRubriqueEvaluation(rubriqueEvaluation);
		List<RubriqueEvaluation> listeRubEva = rubriqueEvaluationService.getAllRubriquesEvaluation();
		Assert.assertEquals(6, listeRubEva.size());
	}
	
	@Test
	public void deleteRubriqueEvaluationTest(){
		rubriqueEvaluationService.deleteRubriqueEvaluation(1L);
		List<RubriqueEvaluation> listeRubEva = rubriqueEvaluationService.getAllRubriquesEvaluation();
		Assert.assertEquals(5, listeRubEva.size());
	}
	
	@Test
	public void updateRubriqueEvaluationTest(){
		final Long id=3L;
		RubriqueEvaluation rubriqueEvaluation=rubriqueEvaluationService.getRubriqueEvaluation(id);
		System.out.println(rubriqueEvaluation.getDesignation());
		rubriqueEvaluation.setDesignation("soukaina");
		rubriqueEvaluationService.updateRubriqueEvaluation(rubriqueEvaluation);
		//List<RubriqueEvaluation> listeRubEva = rubriqueEvaluationService.getAllRubriquesEvaluation();
		RubriqueEvaluation rubriqueEva = rubriqueEvaluationService.getRubriqueEvaluation(3L);
		Assert.assertEquals(rubriqueEva.getDesignation(),"soukaina");

	}
	

	@Test
	public void getRubriqueEvaluationByIdTest(){
		final Integer idEvaluation = 1;
		List<RubriqueEvaluation> listeRubEva = rubriqueEvaluationService.getRubriqueEvaluationsByEvaluation(idEvaluation);
		Assert.assertNotNull(listeRubEva);
	}
}
