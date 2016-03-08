package fr.univbrest.dosi.spi.controller;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Formation;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.bean.Question;
import fr.univbrest.dosi.spi.bean.utils.ProEns;
import fr.univbrest.dosi.spi.bean.utils.QuesQual;

/**
 * 
 * @author hakim
 * cet classe permet de tester l'intégration de promotion 
 *
 */
public class PromotionControllerTest {
	@Test
	public void ajoutProTest() throws ClientProtocolException, IOException {
		
		Enseignant ens = new Enseignant(1);
		Formation form = new Formation("M2DOSI");
		Promotion pro = new Promotion(form.getCodeFormation(), "888");
		ProEns proens = new ProEns(ens, null, pro, form);
		
				// Création du client et  d'une requete POST
				final HttpClient client = HttpClientBuilder.create().build();
				final HttpPost mockRequestPost = new HttpPost("http://localhost:8090/addPromotion");
				// création de l'objet mapper afin de convertir l'objet en jsonInSTring
				ObjectMapper mapper = new ObjectMapper();
				com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
				String jsonInString = ow.writeValueAsString(proens);
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
