package fr.univbrest.dosi.spi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;

import junit.framework.Assert;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
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
		
		Rubrique rub= new Rubrique();
		rub.setDesignation("kk");
		rub.setIdRubrique(new Long(8));
		rub.setType("kk");
		
		
		
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockPost = new HttpPost("http://localhost:8090/addRubrique");
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		
		String json = ow.writeValueAsString(rub);
		mockPost.addHeader("content-type","application/json");
		mockPost.setEntity(new StringEntity(json));
		HttpResponse response = client.execute(mockPost);
		
		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		

		final HttpDelete mockRequest = new HttpDelete("http://localhost:8090/deleteRubrique/"+new Long(10));
		final HttpResponse mockResponse = client.execute(mockRequest);
		
		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());	

		
	}

   
}
