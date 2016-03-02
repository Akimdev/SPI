package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univbrest.dosi.spi.bean.Etudiant;
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
		List<Etudiant> etudiants = new ArrayList<Etudiant>();
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
		
		
		
	}
}
