package fr.univbrest.dosi.spi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import fr.univbrest.dosi.spi.bean.Authentification;
import fr.univbrest.dosi.spi.bean.User;
import fr.univbrest.dosi.spi.exception.SPIException;

public class AuthControllerTest {
	
	 @Test
	 public void Authentifier() throws ClientProtocolException, IOException {

	 List<String> roles = new ArrayList<String>();
	 roles.add("ADM");
	 User user = new User("adm","dosi",roles);
	 
	 // Créaton du client et éxécution d'une requete POST
	 final HttpClient client = HttpClientBuilder.create().build();
	 final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/auth");
	 
	 final ObjectMapper mapper = new ObjectMapper();
	 final ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	 final String jsonInString = ow.writeValueAsString(user);
	 
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
	 
	 @Test
	 public void badAuthentifierEtudiantPromo() throws ClientProtocolException, IOException {

	 Authentification auth = new Authentification(11L, "ETU", "belhaj.othman@gmail.com");
	 
	 // Créaton du client et éxécution d'une requete POST
	 final HttpClient client = HttpClientBuilder.create().build();
	 final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/auth");
	 
	 final ObjectMapper mapper = new ObjectMapper();
	 final ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	 final String jsonInString = ow.writeValueAsString(auth);
	 
	 mockRequestPost.addHeader("content-type", "application/json");
	 mockRequestPost.addHeader("Accept", "application/json");
	 mockRequestPost.setEntity(new StringEntity(jsonInString));
	
	 try {
	 final HttpResponse mockResponse = client.execute(mockRequestPost);
	 Assert.assertEquals(500, mockResponse.getStatusLine().getStatusCode());
	 } catch (ClientProtocolException e) {
	 throw new SPIException("Error Protocol", e);
	 } catch (IOException e) {
	 throw new SPIException("Error Protocol", e);
	 }
  }
}
