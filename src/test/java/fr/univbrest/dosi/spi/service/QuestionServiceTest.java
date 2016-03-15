package fr.univbrest.dosi.spi.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.bean.Question;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class QuestionServiceTest {

	@Autowired
	QualificatifService qualifServ;
	@Autowired
	QuestionService questServ;
	
	@Test
	public void ajoutQuestionTest(){
		Qualificatif qualif = qualifServ.getQualificatif(1l);
		Question quest = new Question(26l, "QUS","nouvelle question");
		quest.setIdQualificatif(qualif);
		questServ.addQuestion(quest);
		List<Question> listeQuestions = questServ.getAllQuestions();
		Assert.assertNotNull(listeQuestions);
		Assert.assertEquals(23, listeQuestions.size());
	}
	
	@Test
	public void modificationQuestionTest(){
		Question quest = questServ.getQuestion(26l);
		quest.setIntitule("question modifié");
		questServ.updateQuestion(quest);
		List<Question> listeQuestions = questServ.getAllQuestions();
		Assert.assertNotNull(listeQuestions);
		Assert.assertEquals(listeQuestions.get(22).getIntitule(), "question modifié");
	}
	
	@Test
	public void supprimerQuestionTest(){
		questServ.deleteQuestionById(26l);;
		List<Question> listeQuestions = questServ.getAllQuestions();
		Assert.assertEquals(22, listeQuestions.size());
	}
	
}
