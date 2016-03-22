package fr.univbrest.dosi.spi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;



import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univbrest.dosi.spi.bean.Evaluation;
import fr.univbrest.dosi.spi.bean.Rubrique;
import fr.univbrest.dosi.spi.bean.RubriqueEvaluation;
import fr.univbrest.dosi.spi.bean.utils.RubriqueEvaluationUtil;

public class RubriqueEvaluationControllerTest {

	

	@Test
	public final void addRubriqueEvaluationTest() throws ClientProtocolException, IOException {
		
		Evaluation eva= new Evaluation(1);
		Rubrique rub= new Rubrique(1L);
		RubriqueEvaluation rubEva= new RubriqueEvaluation();
		rubEva.setDesignation("soukaina");
		rubEva.setOrdre((short)8);
		RubriqueEvaluationUtil rubEvaUtil = new RubriqueEvaluationUtil();
		rubEvaUtil.setEvaluation(eva);
		rubEvaUtil.setRubrique(rub);
		rubEvaUtil.setRubriqueEvaluation(rubEva);
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockPost = new HttpPost("http://localhost:8090/addRubriqueEvaluation");
		ObjectMapper mapper = new ObjectMapper();
		com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String jsonInString = ow.writeValueAsString(rubEvaUtil);
		// établition de la requette (header+body)
		mockPost.addHeader("content-type", "application/json");
		mockPost.setEntity(new StringEntity(jsonInString));
		HttpResponse mockResponse = client.execute(mockPost);

		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());	
	
	} 
	
	@Test
	public final void deleteRubriqueEvaluationTest() throws ClientProtocolException, IOException{
		
		Long id = 21L;
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet mockRequest = new HttpGet("http://localhost:8090/deleteRubriqueEvaluation-"+id);
		final HttpResponse mockResponse = client.execute(mockRequest);
		
		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
		
			
	}
	
	@Test
	public final void getAllRubriqueEvaluationTest() throws ClientProtocolException, IOException{
		
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet mockRequest = new HttpGet("http://localhost:8090/getAllRubriquesEvaluation");
		final HttpResponse mockResponse = client.execute(mockRequest);

		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());

		BufferedReader rd;
		rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
		final ObjectMapper mapper = new ObjectMapper();
		List<RubriqueEvaluation> rubriqueEvaluation;
		rubriqueEvaluation = mapper.readValue(rd, ArrayList.class);
	
		
	}
	
	@Test
	public final void getRubriqueEvaluationByIdTest()throws ClientProtocolException, IOException{
		
		final Integer idEvaluation = 1;
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet mockRequest = new HttpGet("http://localhost:8090/getRubriqueEvaluationByEvaluation-"+idEvaluation);
		final HttpResponse mockResponse = client.execute(mockRequest);

		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());

		BufferedReader rd;
		rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
		final ObjectMapper mapper = new ObjectMapper();
		List<RubriqueEvaluation> rubriqueEvaluation;
		rubriqueEvaluation = mapper.readValue(rd, ArrayList.class);
	}
}
