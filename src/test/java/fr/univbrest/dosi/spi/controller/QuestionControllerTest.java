package fr.univbrest.dosi.spi.controller;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
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
import com.google.gson.Gson;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.bean.Question;
import fr.univbrest.dosi.spi.bean.utils.QuesQual;
import fr.univbrest.dosi.spi.exception.SPIException;
import fr.univbrest.dosi.spi.service.QualificatifService;

public class QuestionControllerTest {
	
	@Autowired
	QualificatifService qualificatifService;
	
	@Test
	public void ajoutQuestionTest() throws ClientProtocolException, IOException {
		
		Long idq = 2L;
		
		Question ques = new Question(800L,"QUS","hh");
 		Qualificatif qua = new Qualificatif(1L,"Pauvre","Riche");
 		
		QuesQual quesQual = new QuesQual(qua, ques);
		
		// Création du client et  d'une requete POST
				final HttpClient client = HttpClientBuilder.create().build();
				final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/ajouterQuestion");
				// création de l'objet mapper afin de convertir l'objet en jsonInSTring
				ObjectMapper mapper = new ObjectMapper();
				com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
				String jsonInString = ow.writeValueAsString(quesQual);
				// établition  de la requette (header+body)
				mockRequestPost.addHeader("content-type", "application/json");
				mockRequestPost.setEntity(new StringEntity(jsonInString));
				System.out.println(jsonInString);
				
				// création de la réponse 
				try {
				HttpResponse	mockResponse = client.execute(mockRequestPost);
				
				Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
				} catch (ClientProtocolException e) {
					System.out.println(e);
				} catch (IOException e) {
					System.out.println(e);
				}
				
		
	}
}
