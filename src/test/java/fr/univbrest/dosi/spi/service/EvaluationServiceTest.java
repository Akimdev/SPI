package fr.univbrest.dosi.spi.service;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Evaluation;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EvaluationServiceTest {

	@Autowired
	EvaluationService evaServ;
	@Autowired
	EnseignantService ensServ;
	@Autowired
	PromotionService promoServ;
	
	@Test
	public void addEvaluationTest(){
		Evaluation evaluation = new Evaluation();
		evaluation.setIdEvaluation(5);
		Enseignant ens = ensServ.getEnseignant(1);
		System.out.println(ens);
		evaluation.setNoEnseignant(ens);
		evaluation.setNoEvaluation((short)2);
		evaluation.setEtat("ELA");
		evaluation.setDesignation("evaluation deux");
		evaluation.setAnnee("2013-2014");
		//evaluation.setCode_ec("ERP");
		evaluation.setCode_formation("M2DOSI");
		evaluation.setCode_ue("IDL");
		evaluation.setDebutReponse(new Date("12/03/2015"));
		evaluation.setFinReponse(new Date("18/03/2015"));
		evaServ.addEvaluation(evaluation);
		List<Evaluation> listeEva = evaServ.getAllEvaluations();
		//Assert.assertEquals(4, listeEva.size());
	}
	
	@Test
	public void updateEvaluationTest(){
		Evaluation evaluation = evaServ.getEvaluation(22);
		evaluation.setPeriode("between this and this");
		evaServ.updateEvaluation(evaluation);
		List<Evaluation> listeEva = evaServ.getAllEvaluations();
		Assert.assertNotNull(evaluation);
		Assert.assertEquals("between this and this", listeEva.get(2).getPeriode());
	}
	
	@Test 
	public void getAllEvaluationsTest(){
		List<Evaluation> listeEvaluation = evaServ.getAllEvaluations();
		Assert.assertNotNull(listeEvaluation);
		Assert.assertTrue(listeEvaluation.size()>0);
	}
	
	@Test
	public void getEvaluationsPromoTest(){
		List<Evaluation> listeEvaluationPromo = evaServ.getEvaluationsPromo("M2DOSI", "2014-2015");
		Assert.assertNotNull(listeEvaluationPromo);
		Assert.assertTrue(listeEvaluationPromo.size()>0);
	}
	
	@Test
	public void deleteEvaluationTest(){
		evaServ.deleteEvaluation(36);
		List<Evaluation> listeEvaluation = evaServ.getAllEvaluations();
		Assert.assertEquals(3, listeEvaluation.size());
	}
}
