package fr.univbrest.dosi.spi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;
import fr.univbrest.dosi.spi.bean.utils.EtudiantPromotionUtil;
import fr.univbrest.dosi.spi.exception.SPIException;

public class EtudiantControllerTest {
/**
	 * author: hakim ait errami
	 * 
	 * test intégration de la méthode : getAllEtudiants
	 * 
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void etudiantService() throws IllegalStateException, IOException {
		/**
		 * ce bloc est pour récupèrer la réponse de la méthode
		 */
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet mockRequest = new HttpGet("http://localhost:8090/etudiants");
		HttpResponse mockResponse;
		try {
			mockResponse = client.execute(mockRequest);
		} catch (ClientProtocolException e) {
			throw new SPIException("Error Protocol", e);
		} catch (IOException e) {
			throw new SPIException("Error Protocol", e);
		}
		
		/**
		 * ici , je test le status Http de la réponse si c'est 200 !
		 */
		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
		/**
		 * ici je vérifie le contenue de la réponse
		 */
		BufferedReader rd;
		try {
			rd = new BufferedReader(new InputStreamReader(mockResponse.getEntity().getContent()));
		} catch (UnsupportedOperationException e) {
			throw new SPIException("Error buffer ", e);
		}
		
		final ObjectMapper mapper = new ObjectMapper();
		List<Etudiant> etudiants;
		try{
			etudiants = mapper.readValue(rd,ArrayList.class);
		} catch (JsonParseException e) {
			throw new SPIException("Error Parser", e);
		} catch (JsonMappingException e) {
			throw new SPIException("Error Parser", e);
		} catch (IOException e) {
			throw new SPIException("Error Parser", e);
		}
		
		Assert.assertEquals(10,etudiants.size());
		Assert.assertNotNull(etudiants);	
	}
	
	@Test
	public void addEtudiantTest() throws ClientProtocolException, IOException{
		Etudiant etudiant = new Etudiant();
		etudiant.setNoEtudiant("2140836");
		etudiant.setNom("BELHAJ");
		etudiant.setPrenom("Othman");
		etudiant.setSexe("M");
		etudiant.setDateNaissance(new Date("21/08/1993"));
		etudiant.setLieuNaissance("Taza");
		etudiant.setNationalite("Marocaine");
		etudiant.setEmail("belhaj.othman@gmail.com");
		etudiant.setAdresse("2, rue des archives");
		etudiant.setVille("Brest");
		etudiant.setPaysOrigine("MA");
		etudiant.setUniversiteOrigine("UAE");
		PromotionPK promotionPK = new PromotionPK("M2DOSI","2013-2014");
		Promotion promotion = new Promotion(promotionPK);
		EtudiantPromotionUtil etudiantPromoUtil = new EtudiantPromotionUtil();
		etudiantPromoUtil.setEtudiant(etudiant);
		etudiantPromoUtil.setPromotion(promotion);
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockPost = new HttpPost("http://localhost:8090/etudiants/addEtudiant");
		ObjectMapper mapper = new ObjectMapper();
		com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String jsonInString = ow.writeValueAsString(etudiantPromoUtil);
		// établition de la requette (header+body)
		mockPost.addHeader("content-type", "application/json");
		mockPost.setEntity(new StringEntity(jsonInString));
		HttpResponse mockResponse = client.execute(mockPost);

		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());	
	
	}
	
	@Test
	public void updateEtudiantTest() throws ClientProtocolException, IOException{
		Etudiant etudiant = new Etudiant();
		etudiant.setNoEtudiant("2140836");
		etudiant.setNom("BELHAJ");
		etudiant.setPrenom("Othmane");
		etudiant.setSexe("M");
		etudiant.setDateNaissance(new Date("21/08/1993"));
		etudiant.setLieuNaissance("Taza");
		etudiant.setNationalite("Marocaine");
		etudiant.setEmail("belhaj.othmane@gmail.com");
		etudiant.setAdresse("2, rue des archives");
		etudiant.setVille("Brest");
		etudiant.setPaysOrigine("FR");
		etudiant.setUniversiteOrigine("UAE");
		PromotionPK promotionPK = new PromotionPK("M2DOSI","2013-2014");
		Promotion promotion = new Promotion(promotionPK);
		EtudiantPromotionUtil etudiantPromoUtil = new EtudiantPromotionUtil();
		etudiantPromoUtil.setEtudiant(etudiant);
		etudiantPromoUtil.setPromotion(promotion);
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost mockPost = new HttpPost("http://localhost:8090/etudiants/updateEtudiant");
		ObjectMapper mapper = new ObjectMapper();
		com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String jsonInString = ow.writeValueAsString(etudiantPromoUtil);
		// établition de la requette (header+body)
		mockPost.addHeader("content-type", "application/json");
		mockPost.setEntity(new StringEntity(jsonInString));
		HttpResponse mockResponse = client.execute(mockPost);

		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());	
	
	}
	
	@Test
	public void deleteEtudiantTest() throws ClientProtocolException, IOException{
		String noEtudiant = "2140836";
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet mockRequest = new HttpGet("http://localhost:8090/etudiants/deleteEtudiant-"+noEtudiant);
		final HttpResponse mockResponse = client.execute(mockRequest);
		
		Assert.assertEquals(200, mockResponse.getStatusLine().getStatusCode());
	}
}
