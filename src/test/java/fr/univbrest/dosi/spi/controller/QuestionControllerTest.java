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

import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.bean.Question;
import fr.univbrest.dosi.spi.bean.Rubrique;
import fr.univbrest.dosi.spi.bean.utils.QuesQual;

/**
 * 
 * @author hakim
 *
 */
public class QuestionControllerTest {

	@Test
	public void addQuestionTest() throws ClientProtocolException, IOException {

		Long idq = 2L;
		Question ques = new Question(800L, "QUS", "hh");
		Qualificatif qua = new Qualificatif(1L, "Pauvre", "Riche");
		QuesQual quesQual = new QuesQual(qua, ques);

		// Création du client et d'une requete POST
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/addQuestion");
		// création de l'objet mapper afin de convertir l'objet en jsonInSTring
		ObjectMapper mapper = new ObjectMapper();
		com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String jsonInString = ow.writeValueAsString(quesQual);
		// établition de la requette (header+body)
		mockRequestPost.addHeader("content-type", "application/json");
		mockRequestPost.setEntity(new StringEntity(jsonInString));
		System.out.println(jsonInString);

		// création de la réponse
		try {
			HttpResponse mockResponse = client.execute(mockRequestPost);

			Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	@Test
	public void deleteQuestionTest() throws ClientProtocolException, IOException {
		
				Long idq = 23L;
				// Création du client et éxécution d'une requete GET
				final HttpClient client = HttpClientBuilder.create().build();
				final HttpGet mockRequest = new HttpGet("http://localhost:8090/deleteQuestionById-"+idq);
				final HttpResponse mockResponse = client.execute(mockRequest);
				// Le code retour HTTP doit être un succès (200)
				Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
				final ObjectMapper mapper = new ObjectMapper();
				
		
	}
	
	@Test
	public void getAllQuestionsTest() throws ClientProtocolException, IOException {
		
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet mockRequest = new HttpGet("http://localhost:8090/questions");
		final HttpResponse mockResponse = client.execute(mockRequest);
		
		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
		
		BufferedReader rd ;
		rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
		final ObjectMapper mapper = new ObjectMapper();
		List<Question> question;
		question = mapper.readValue(rd, ArrayList.class);
	}
}
