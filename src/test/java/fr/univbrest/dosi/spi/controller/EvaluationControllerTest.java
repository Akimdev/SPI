package fr.univbrest.dosi.spi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import fr.univbrest.dosi.spi.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EvaluationControllerTest {
	@Test
	public void getAllEvaluationsTest() throws ClientProtocolException, IOException {

			final HttpClient client = HttpClientBuilder.create().build();

			final HttpGet mockRequest = new HttpGet("http://localhost:8090/evaluations");

			final HttpResponse mockResponse = client.execute(mockRequest);

			Assert.assertEquals(200, mockResponse.getStatusLine()
					.getStatusCode());
	}
	
	@Test
	public void deleteEvaluationTest() throws ClientProtocolException, IOException {

			final HttpClient client = HttpClientBuilder.create().build();
			final Long idEvaluation = 3l;
			final HttpGet mockRequest = new HttpGet("http://localhost:8090/deleteEvaluation-"+idEvaluation);

			final HttpResponse mockResponse = client.execute(mockRequest);

			Assert.assertEquals(200, mockResponse.getStatusLine()
					.getStatusCode());
	}
}
