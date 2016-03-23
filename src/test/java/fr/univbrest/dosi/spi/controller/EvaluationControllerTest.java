package fr.univbrest.dosi.spi.controller;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Evaluation;
import fr.univbrest.dosi.spi.bean.PromotionPK;
import fr.univbrest.dosi.spi.service.EnseignantService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EvaluationControllerTest {
	@Autowired
	EnseignantService ensServ;
	
	@Test
	public void getAllEvaluationsTest() throws ClientProtocolException, IOException {

			final HttpClient client = HttpClientBuilder.create().build();

			final HttpGet mockRequest = new HttpGet("http://localhost:8090/evaluations");

			final HttpResponse mockResponse = client.execute(mockRequest);

			Assert.assertEquals(200, mockResponse.getStatusLine()
					.getStatusCode());
	}
	
	@Test
	public void getEvaluationsPromoTest() throws ClientProtocolException, IOException {

			PromotionPK promotion = new PromotionPK("M2DOSI","2014-2015");
		
			final HttpClient client = HttpClientBuilder.create().build();

			final HttpPost mockRequest = new HttpPost("http://localhost:8090/getEvaluationsPromo");

			final ObjectMapper mapper = new ObjectMapper();
			final com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
			final String jsonInString = ow.writeValueAsString(promotion);
			mockRequest.addHeader("content-type", "application/json");
			mockRequest.setEntity(new StringEntity(jsonInString));

			final HttpResponse mockResponse = client.execute(mockRequest);

			// Le code retour HTTP doit être un succès (200)
			Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
	}
	
	@Test
	public void deleteEvaluationTest() throws ClientProtocolException, IOException {

			final HttpClient client = HttpClientBuilder.create().build();
			final Long idEvaluation = 3l;
			final HttpGet mockRequest = new HttpGet("http://localhost:8090/deleteEvaluation-"+idEvaluation);

			final HttpResponse mockResponse = client.execute(mockRequest);

			Assert.assertEquals(200, mockResponse.getStatusLine()
					.getStatusCode());
	}
	
	@Test
	public void addEvaluationTest() throws ClientProtocolException, IOException{
		Evaluation evaluation = new Evaluation();
		//evaluation.setIdEvaluation(5);
		Enseignant ens = ensServ.getEnseignant(1);
		evaluation.setNoEnseignant(ens);
		evaluation.setNoEvaluation((short)3);
		evaluation.setEtat("ELA");
		evaluation.setDesignation("evaluation troix");
		evaluation.setAnnee("2014-2015");
		evaluation.setCode_ec("ERP");
		evaluation.setCode_formation("M2DOSI");
		evaluation.setCode_ue("PCO");
		evaluation.setDebutReponse(new Date("18/03/2015"));
		evaluation.setFinReponse(new Date("22/03/2015"));
		
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/addEvaluation");
		final ObjectMapper mapper = new ObjectMapper();
		final com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		final String jsonInString = ow.writeValueAsString(evaluation);
		mockRequestPost.addHeader("content-type", "application/json");
		mockRequestPost.setEntity(new StringEntity(jsonInString));

		final HttpResponse mockResponse = client.execute(mockRequestPost);

		// Le code retour HTTP doit être un succès (200)
		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
	}
}
