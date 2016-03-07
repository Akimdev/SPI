package fr.univbrest.dosi.spi.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.Authentification;
import fr.univbrest.dosi.spi.bean.User;
import fr.univbrest.dosi.spi.dao.AuthentificationRepository;


/**
 * 
 * @author Youssef
 * Service d'authentification
 */
@Service
public class AuthentificationService {

	@Autowired
	AuthentificationRepository authentificationRepository;
	
	public Authentification getConnection(Long idConnection){
		return authentificationRepository.findOne(idConnection);
	}
	
	public User logIn(String pseudoConnection, String motPasse){
		Authentification auth = authentificationRepository.findByPseudoAndPwd(pseudoConnection, motPasse);
		User user = null;
		if(auth != null){
			user = new User(auth.getPseudoConnection(), auth.getMotPasse(), Arrays.asList(auth.getRole()));
		}
		return user;
	}
	
}
