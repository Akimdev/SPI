package fr.univbrest.dosi.spi.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Authentification;
import fr.univbrest.dosi.spi.bean.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AuthentificationServiceTest {

	@Autowired
	AuthentificationService authentificationService;
	
	@Test
	public void loginSuccessPseudo(){
		Authentification user = authentificationService.logIn("psaliou", "dosi");
		Assert.assertNotNull(user);
	}
	
	@Test
	public void loginSuccessLoginConnection(){
		Authentification user = authentificationService.logIn("philippe.saliou@univ-brest.fr", "dosi");
		Assert.assertNotNull(user);
	}
	
	@Test
	public void loginWrongPwd(){
		Authentification user = authentificationService.logIn("psaliou", "fail");
		Assert.assertNull(user);
	}
	
	@Test
	public void loginWrongPseudo(){
		Authentification user = authentificationService.logIn("pseudo", "dosi");
		Assert.assertNull(user);
	}
	
	@Test
	public void loginWrongLoginConnection(){
		Authentification user = authentificationService.logIn("philip.saliou@univ-brest.fr", "dosi");
		Assert.assertNull(user);
	}
	
	@Test
	public void loginWrongCredentials(){
		Authentification user = authentificationService.logIn("pseudo", "pwd");
		Assert.assertNull(user);
	}
	
	@Test
	public void etudiantMauvaisePromo(){
		Authentification user = authentificationService.logIn("belhaj.othman@gmail.com", "dosi");
		Assert.assertNotNull(user);
		Assert.assertEquals("2015-2016", user.getNoEtudiant().getPromotion().getPromotionPK().getAnneeUniversitaire());
	}
}