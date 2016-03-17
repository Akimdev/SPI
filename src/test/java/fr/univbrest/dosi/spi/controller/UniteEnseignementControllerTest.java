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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.UniteEnseignement;
import fr.univbrest.dosi.spi.bean.UniteEnseignementPK;
import fr.univbrest.dosi.spi.bean.utils.UniteEnseignementUtil;
import fr.univbrest.dosi.spi.exception.SPIException;


public class UniteEnseignementControllerTest {

	
	@Test
	public void addUETest() throws ClientProtocolException, IOException{
		
		Enseignant ens = new Enseignant(2);
		UniteEnseignementPK uePK= new UniteEnseignementPK("M2DOSI", "VV");
		UniteEnseignement ue = new UniteEnseignement(uePK);
		ue.setDesignation("Preparation a la vie Prof");
		ue.setSemestre("10");
		UniteEnseignementUtil ueUtil = new UniteEnseignementUtil(ue, ens);
		
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/addUE");
		// création de l'objet mapper afin de convertir l'objet en jsonInSTring
		ObjectMapper mapper = new ObjectMapper();
		com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String jsonInString = ow.writeValueAsString(ueUtil);
		// établition de la requette (header+body)
		mockRequestPost.addHeader("content-type", "application/json");
		mockRequestPost.setEntity(new StringEntity(jsonInString));
		
		System.out.println(jsonInString);

		// création de la réponse
		try {
			HttpResponse mockResponse = client.execute(mockRequestPost);

			Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void deleteUE() throws ClientProtocolException, IOException{
		
		UniteEnseignementPK uePK= new UniteEnseignementPK("M2DOSI", "VV");
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/deleteUE");
		// création de l'objet mapper afin de convertir l'objet en jsonInSTring
		ObjectMapper mapper = new ObjectMapper();
		com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String jsonInString = ow.writeValueAsString(uePK);
		// établition de la requette (header+body)
		mockRequestPost.addHeader("content-type", "application/json");
		mockRequestPost.setEntity(new StringEntity(jsonInString));
		
		System.out.println(jsonInString);

		// création de la réponse
		try {
			HttpResponse mockResponse = client.execute(mockRequestPost);

			Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void getAllUEs() throws ClientProtocolException, IOException{
		try {
			final HttpClient client = HttpClientBuilder.create().build();
			final HttpGet mockRequest = new HttpGet("http://localhost:8090/UEs");
			final HttpResponse mockResponse = client.execute(mockRequest);
			
			Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
			
			BufferedReader rd ;
			rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
			final ObjectMapper mapper = new ObjectMapper();
			List<UniteEnseignement> ueList;
			ueList = mapper.readValue(rd, ArrayList.class);
	}
		catch(final SPIException se){
			Assert.fail("Echec de chargement des unites d'enseignement");
		}
		}
}
