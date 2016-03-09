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
		evaluation.setIdEvaluation(5l);
		Enseignant ens = ensServ.getEnseignant(1);
		System.out.println(ens);
		evaluation.setNoEnseignant(ens);
		evaluation.setNoEvaluation((short)2);
		evaluation.setEtat("ELA");
		evaluation.setDesignation("evaluation deux");
		PromotionPK promotionPK = new PromotionPK("M2DOSI","2014-2015");
		Promotion promo = promoServ.getPromotion(promotionPK);
		System.out.println(promo);
		evaluation.setPromotion(promo);
		evaluation.setDebutReponse(new Date("12/03/2015"));
		evaluation.setFinReponse(new Date("18/03/2015"));
		evaServ.addEvaluation(evaluation);
		List<Evaluation> listeEva = evaServ.getAllEvaluations();
		Assert.assertEquals(3, listeEva.size());
	}
	
	@Test 
	public void getAllEvaluationsTest(){
		List<Evaluation> listeEvaluation = evaServ.getAllEvaluations();
		Assert.assertNotNull(listeEvaluation);
		Assert.assertTrue(listeEvaluation.size()>0);
	}
	
	@Test
	public void deleteEvaluationTest(){
		evaServ.deleteEvaluation(3l);
		List<Evaluation> listeEvaluation = evaServ.getAllEvaluations();
		Assert.assertEquals(2, listeEvaluation.size());
	}
}
