package fr.univbrest.dosi.spi.controller;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Test;

import fr.univbrest.dosi.spi.bean.Authentification;
import fr.univbrest.dosi.spi.exception.SPIException;


/**
 *
 * @author BAQLOUL Soukaina Test d'integration spécifique au contrôlleur de l'authentification
 */
public class AuthControllerTest {
	
	 @Test
	 public void Authentifier() throws ClientProtocolException, IOException {
	
	 Authentification authentification = new Authentification();
	 
	 // Créaton du client et éxécution d'une requete POST
	 final HttpClient client = HttpClientBuilder.create().build();
	 final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/auth");
	 final ObjectMapper mapper = new ObjectMapper();
	 final ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	 final String jsonInString = ow.writeValueAsString(authentification);
	 mockRequestPost.addHeader("content-type", "application/json");
	 mockRequestPost.addHeader("Accept", "application/json");
	 mockRequestPost.setEntity(new StringEntity(jsonInString));
	
	 try {
	 final HttpResponse mockResponse = client.execute(mockRequestPost);
	 Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
	 } catch (ClientProtocolException e) {
	 throw new SPIException("Error Protocol", e);
	 } catch (IOException e) {
	 throw new SPIException("Error Protocol", e);
	 }
	 }
}
