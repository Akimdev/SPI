package fr.univbrest.dosi.spi.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AuthentificationServiceTest {

	@Autowired
	AuthentificationService authentificationService;
	
	@Test
	public void loginSuccess(){
		User user = authentificationService.logIn("psaliou", "dosi");
		Assert.assertNotNull(user);
	}
	
	@Test
	public void loginWrongPwd(){
		User user = authentificationService.logIn("psaliou", "fail");
		Assert.assertNull(user);
	}
	
	@Test
	public void loginWrongPseudo(){
		User user = authentificationService.logIn("pseudo", "dosi");
		Assert.assertNull(user);
	}
	
	@Test
	public void loginWrongCredentials(){
		User user = authentificationService.logIn("pseudo", "pwd");
		Assert.assertNull(user);
	}
}
