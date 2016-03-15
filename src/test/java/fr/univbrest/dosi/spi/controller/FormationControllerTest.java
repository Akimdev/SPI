package fr.univbrest.dosi.spi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univbrest.dosi.spi.bean.Formation;

public class FormationControllerTest {

	@Test
	public void addFormationTest() throws ClientProtocolException, IOException {

		Formation formation = new Formation();
		formation.setCodeFormation("M2DOSIII");
		formation.setDiplome("M");
		formation.setDoubleDiplome('O');
		formation.setN0Annee((short) 2);
		formation.setNomFormation("2eme annee Science de l'information...");
		formation.setDebutAccreditation(new java.util.Date("11/11/2011"));
		formation.setFinAccreditation(new java.util.Date("11/11/2019"));

		// Création du client et d'une requete POST
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/formation/addFormation");
		// création de l'objet mapper afin de convertir l'objet en jsonInSTring
		final ObjectMapper mapper = new ObjectMapper();
		com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String jsonInString = ow.writeValueAsString(formation);
		System.err.println(jsonInString);
		// établition de la requette (header+body)
		mockRequestPost.addHeader("content-type", "application/json");
		mockRequestPost.setEntity(new StringEntity(jsonInString));
		// création de la réponse .
		final HttpResponse mockResponse = client.execute(mockRequestPost);
		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
	}

	@Test
	public void getFormationTest() throws ClientProtocolException, IOException {
		String codeFormation = "M2DOSI";
		HttpClient client = HttpClientBuilder.create().build();
		HttpDelete request = new HttpDelete("http://localhost:8090/formation/getFormation/" + codeFormation);
		HttpResponse response = client.execute(request);
		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		BufferedReader rd;
		rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		final ObjectMapper mapper = new ObjectMapper();
		Formation formation = new Formation();
		formation = mapper.readValue(rd, Formation.class);
		Assert.assertNotNull(formation);
		// System.err.println("formation:" + formation);

	}

	@Test
	public void listFormationTest() throws ClientProtocolException, IOException {

		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet mockRequest = new HttpGet("http://localhost:8090/formations");
		final HttpResponse mockResponse = client.execute(mockRequest);

		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());

		BufferedReader rd;
		rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
		final ObjectMapper mapper = new ObjectMapper();
		List<Formation> formations;
		formations = mapper.readValue(rd, ArrayList.class);
		System.err.println(formations);
		Assert.assertEquals(5, formations.size());
	}

	@Test
	public void supprimerFormationTest() throws ClientProtocolException, IOException {
		String codeFormation = "M2DOSIII";
		HttpClient client = HttpClientBuilder.create().build();
		HttpDelete requestDelete = new HttpDelete("http://localhost:8090/formation/deleteFormation/" + codeFormation);
		HttpResponse response = client.execute(requestDelete);
		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
	}
}
