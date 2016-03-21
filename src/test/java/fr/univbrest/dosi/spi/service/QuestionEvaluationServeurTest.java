/**
 * 
 */
package fr.univbrest.dosi.spi.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.bean.Question;
import fr.univbrest.dosi.spi.bean.QuestionEvaluation;
import fr.univbrest.dosi.spi.bean.RubriqueEvaluation;
import fr.univbrest.dosi.spi.bean.utils.QuestionEvaluationUtil;

/**
 * @author BAQLOUL Soukaina
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class QuestionEvaluationServeurTest {
	
	@Autowired
	QuestionEvaluationService quesEvaServ;

	@Test
	public void addQuestionEvaluationTest(){
		
		QuestionEvaluation quesEva= new QuestionEvaluation();
		Question ques=new Question(1L);
		Qualificatif qualif= ques.getIdQualificatif();
		RubriqueEvaluation rubEva= new RubriqueEvaluation(22L);
		quesEva.setIntitule("brahim");;
		quesEva.setOrdre((short)8);
		quesEva.setIdQuestionEvaluation(30L);
		QuestionEvaluationUtil quesEvaUtil = new QuestionEvaluationUtil();
		quesEvaUtil.setQuestionEvaluation(quesEva);
		quesEvaUtil.setQualificatif(null);
		quesEvaUtil.setQuestion(ques);
		quesEvaUtil.setRubriqueEvaluation(rubEva);
		List<QuestionEvaluation> listeQuesEva = quesEvaServ.getAllQuestionsEvaluation();
		Assert.assertEquals(25, listeQuesEva.size());
	}
	
	@Test
	public void deleteQuestionEvaluaionService(){
		quesEvaServ.deleteQuestionEvaluation(53L);
		List<QuestionEvaluation> listeQuesEva = quesEvaServ.getAllQuestionsEvaluation();
		Assert.assertEquals(25, listeQuesEva.size());
	}
	
    @Test
    public void updateQuestionEvaluationTset(){
    	
    	QuestionEvaluation quesEva= quesEvaServ.getQuestionEvaluation(52L);
		quesEva.setIntitule("baqloul");
		quesEvaServ.updateQuestionEvaluation(quesEva);
		QuestionEvaluation questionEva = quesEvaServ.getQuestionEvaluation(52L);
		Assert.assertEquals(questionEva.getIntitule(),"baqloul");
    }

}
