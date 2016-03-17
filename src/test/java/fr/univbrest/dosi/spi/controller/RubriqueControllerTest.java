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
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.univbrest.dosi.spi.bean.Rubrique;


/**
 * @author BAQLOUL Soukaina
 *
 */
public class RubriqueControllerTest {
	
	@Test
	public final void RubriqueTest() throws ClientProtocolException, IOException {
		
		Rubrique rub= new Rubrique(8L, "RBS", "desig");
		
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockPost = new HttpPost("http://localhost:8090/addRubrique");
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		
		String json = ow.writeValueAsString(rub);
		mockPost.addHeader("content-type","application/json");
		mockPost.setEntity(new StringEntity(json));
		HttpResponse response = client.execute(mockPost);
		
		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		
		final HttpDelete mockRequest = new HttpDelete("http://localhost:8090/deleteRubrique/"+new Long(9));
		final HttpResponse mockResponse = client.execute(mockRequest);
		
		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());		
	} 
	
	@Test
	public void getAllRubriqueTest() throws ClientProtocolException, IOException {
		
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet mockRequest = new HttpGet("http://localhost:8090/rubriques");
		final HttpResponse mockResponse = client.execute(mockRequest);
		
		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
		
		BufferedReader rd ;
		rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
		final ObjectMapper mapper = new ObjectMapper();
		List<Rubrique> rubrique;
		rubrique = mapper.readValue(rd, ArrayList.class);		
	}
	
	@Test
	public final void updateRubriqueTest() throws ClientProtocolException, IOException {
		
		Rubrique rub= new Rubrique(7L, "RBP", "desig");
		
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockPost = new HttpPost("http://localhost:8090/updateRubrique");
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		
		String json = ow.writeValueAsString(rub);
		mockPost.addHeader("content-type","application/json");
		mockPost.setEntity(new StringEntity(json));
		HttpResponse response = client.execute(mockPost);
		
		Assert.assertEquals(200, response.getStatusLine().getStatusCode());	
	} 
}