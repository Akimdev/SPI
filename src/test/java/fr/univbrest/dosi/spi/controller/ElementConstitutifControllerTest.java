package fr.univbrest.dosi.spi.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.ElementConstitutif;
import fr.univbrest.dosi.spi.bean.ElementConstitutifPK;
import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.utils.EcUtil;


public class ElementConstitutifControllerTest {
	
	@Test
	public void addEC() throws JsonProcessingException, Exception{
		
		Enseignant enseignant = new Enseignant(2);
		ElementConstitutifPK ecPK = new ElementConstitutifPK("M2DOSI","J2EE","newEc");
		ElementConstitutif ec = new ElementConstitutif(ecPK);
		ec.setDescription("nn");
		ec.setDesignation("nn");
		EcUtil ecUtil = new EcUtil(enseignant, ec);
		
		// Création du client et  d'une requete POST
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/updateEC");
		// création de l'objet mapper afin de convertir l'objet en jsonInSTring
		ObjectMapper mapper = new ObjectMapper();
		com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String jsonInString = ow.writeValueAsString(ecUtil);
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
