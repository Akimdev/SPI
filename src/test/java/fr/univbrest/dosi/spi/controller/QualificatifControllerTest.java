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

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 * 
 * @author Othman
 *
 */


public class QualificatifControllerTest {

	
	@Test
	public void addserviceTest() throws ClientProtocolException, IOException {

		Qualificatif qualif = new Qualificatif((long) 18, "fort", "faible");
		// Création du client et éxécution d'une requete GET
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/ajouterQualificatif");
		final ObjectMapper mapper = new ObjectMapper();
		final com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		final String jsonInString = ow.writeValueAsString(qualif);
		mockRequestPost.addHeader("content-type", "application/json");
		mockRequestPost.addHeader("Accept", "application/json");
		mockRequestPost.setEntity(new StringEntity(jsonInString));

		try {
		final HttpResponse mockResponse = client.execute(mockRequestPost);
		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
		}
		catch (ClientProtocolException e){
			throw new SPIException("Error Protocol", e);
		}
		catch(IOException e){
			throw new SPIException("Error Protocol", e);
		}
		// Le code retour HTTP doit être un succès (200)
		

		/*
		 * final BufferedReader rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
		 * 
		 * Iterable<Enseignant> ens = mapper.readValue(rd, Iterable.class);
		 * 
		 * Assert.assertNotNull(ens);
		 */

	}
}
