/**
 * 
 */
package fr.univbrest.dosi.spi.controller;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.bean.Question;
import fr.univbrest.dosi.spi.bean.QuestionEvaluation;
import fr.univbrest.dosi.spi.bean.RubriqueEvaluation;
import fr.univbrest.dosi.spi.bean.utils.QuestionEvaluationUtil;
import fr.univbrest.dosi.spi.service.QualificatifService;
import fr.univbrest.dosi.spi.service.QuestionEvaluationService;
import fr.univbrest.dosi.spi.service.QuestionService;
import fr.univbrest.dosi.spi.service.RubriqueEvaluationService;

/**
 * @author DOSI
 *
 */
public class QuestionEvaluationControllerTest {

	@Autowired
	QuestionService questionServ;
	
	@Autowired
	QualificatifService qualificatifServ;
	
	@Autowired
	RubriqueEvaluationService rubriqueEvaluationSer;
	
	@Autowired
	QuestionEvaluationService questionEvaServ;
	
	@Test
	public final void addQuestionEvaluationTest() throws ClientProtocolException, IOException {
		
		QuestionEvaluation quesEva= new QuestionEvaluation();
		Question ques=new Question(1L);
		Qualificatif qualif= ques.getIdQualificatif();
		RubriqueEvaluation rubEva= new RubriqueEvaluation(22L);
		quesEva.setIntitule("Madame Bagloul");;
		quesEva.setOrdre((short)8);
		quesEva.setIdQuestionEvaluation(30L);
		QuestionEvaluationUtil quesEvaUtil = new QuestionEvaluationUtil();
		quesEvaUtil.setQuestionEvaluation(quesEva);
		quesEvaUtil.setQualificatif(null);
		quesEvaUtil.setQuestion(ques);
		quesEvaUtil.setRubriqueEvaluation(rubEva);
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockPost = new HttpPost("http://localhost:8090/addQuestionEvaluation");
		ObjectMapper mapper = new ObjectMapper();
		com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String jsonInString = ow.writeValueAsString(quesEvaUtil);
		mockPost.addHeader("content-type", "application/json");
		mockPost.setEntity(new StringEntity(jsonInString));
		HttpResponse mockResponse = client.execute(mockPost);

		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());	
	
	} 
	
	@Test
	public void updateQuestionEvaluationTest() throws ClientProtocolException, IOException{
		
		QuestionEvaluation quesEva= new QuestionEvaluation(53L);
		Question ques=new Question(1L);
		Qualificatif qualif= ques.getIdQualificatif();
		RubriqueEvaluation rubEva= new RubriqueEvaluation(22L);
		quesEva.setIntitule("sousou");;
		quesEva.setOrdre((short)8);
		quesEva.setIdQuestionEvaluation(30L);
		QuestionEvaluationUtil quesEvaUtil = new QuestionEvaluationUtil();
		quesEvaUtil.setQuestionEvaluation(quesEva);
		quesEvaUtil.setQualificatif(null);
		quesEvaUtil.setQuestion(ques);
		quesEvaUtil.setRubriqueEvaluation(rubEva);
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockPost = new HttpPost("http://localhost:8090/updateQuestionEvaluation");
		ObjectMapper mapper = new ObjectMapper();
		com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String jsonInString = ow.writeValueAsString(quesEvaUtil);
		mockPost.addHeader("content-type", "application/json");
		mockPost.setEntity(new StringEntity(jsonInString));
		HttpResponse mockResponse = client.execute(mockPost);

		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
	}
	
	@Test
	public void deleteQuestionEvaluationTest() throws ClientProtocolException, IOException{
		
		Long id = 54L;
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet mockRequest = new HttpGet("http://localhost:8090/deleteQuestionEvaluation-"+id);
		final HttpResponse mockResponse = client.execute(mockRequest);
		
		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
	}

}
