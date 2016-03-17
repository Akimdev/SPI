package fr.univbrest.dosi.spi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpParams;
import org.junit.Assert;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.univbrest.dosi.spi.bean.Qualificatif;

/**
 * 
 * @author hakim
 * test intégration de qualificatif
 *
 */
public class QualificatifControllerTest {

	@Test
	public void addFormationTest() throws ClientProtocolException, IOException {
		

		Qualificatif qualif = new Qualificatif();
		
		qualif.setIdQualificatif(new Long(557));
		qualif.setMaximal("max");
		qualif.setMinimal("min");
		
		// Création du client et  d'une requete POST
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/ajouterQualificatif");
		// création de l'objet mapper afin de convertir l'objet en jsonInSTring
		final ObjectMapper mapper = new ObjectMapper();
		com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String jsonInString = ow.writeValueAsString(qualif);
		// établition  de la requette (header+body)
		mockRequestPost.addHeader("content-type", "application/json");
		mockRequestPost.setEntity(new StringEntity(jsonInString));
		// création de la réponse .
		final HttpResponse mockResponse = client.execute(mockRequestPost);
		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
		
	}
	
	@Test
	public void listerQualificatifTest() throws ClientProtocolException,IOException{
		
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet mockRequest = new HttpGet("http://localhost:8090/listerQualificatif");
		final HttpResponse mockResponse = client.execute(mockRequest);
		
		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
		
		BufferedReader rd ;
		rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
		final ObjectMapper mapper = new ObjectMapper();
		List<Qualificatif> qualif;
		qualif = mapper.readValue(rd, ArrayList.class);		
		Assert.assertEquals(20,qualif.size());	
	}
	
	@Test
	public void modifierQualificatifTest() throws ClientProtocolException,IOException{
		
		Qualificatif qualif = new Qualificatif();
		qualif.setIdQualificatif((long) 2);
		qualif.setMinimal("modi-min");
		qualif.setMaximal("modif-max");
		
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockRequest = new HttpPost("http://localhost:8090/modifierQualificatif");
		final HttpResponse mockResponse ;
			
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String jsonInString = ow.writeValueAsString(qualif);
		
		mockRequest.addHeader("content-type", "application/json");
		mockRequest.setEntity(new StringEntity(jsonInString));
		
		mockResponse=client.execute(mockRequest);
		
	}
	
	@Test
	public void supprimerQualificatifTest() throws ClientProtocolException,IOException{
		long id = 25;
		HttpClient client = HttpClientBuilder.create().build();
		HttpDelete requestDelete = new HttpDelete("http://localhost:8090/supprimerQualificatifAvecId-"+id);
		HttpResponse response = client.execute(requestDelete);

	}
}
