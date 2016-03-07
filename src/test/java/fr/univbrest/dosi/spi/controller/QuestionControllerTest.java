package fr.univbrest.dosi.spi.controller;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univbrest.dosi.spi.bean.Question;
import fr.univbrest.dosi.spi.exception.SPIException;
import fr.univbrest.dosi.spi.service.QualificatifService;

public class QuestionControllerTest {
	
	@Autowired
	QualificatifService qualificatifService;
	
	@Test
	public void addQuestionTest() throws ClientProtocolException, IOException {

		Question question = new Question(99L, "QUS", "Some question");
		question.setIdQualificatif(qualificatifService.getQualificatif(1L));
		
		// Création du client et éxécution d'une requete GET
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/ajouterQuestion");
		final ObjectMapper mapper = new ObjectMapper();
		final com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		final String jsonInString = ow.writeValueAsString(question);
		mockRequestPost.addHeader("content-type", "application/json");
		mockRequestPost.addHeader("Accept", "application/json");
		mockRequestPost.setEntity(new StringEntity(jsonInString));
		try {
			final HttpResponse mockResponse = client.execute(mockRequestPost);
			//Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
		}
		catch (ClientProtocolException e){
			throw new SPIException("Error Protocol", e);
		}
		catch(IOException e){
			throw new SPIException("Erreur d'ajout de nouvelle question", e);
		}

	}
}
