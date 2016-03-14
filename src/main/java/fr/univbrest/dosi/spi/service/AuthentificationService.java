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
	
	public Authentification logIn(String login, String motPasse){
		Authentification auth = authentificationRepository.findByLoginAndPwd(login, motPasse);
		if(auth != null)
			return auth;
		else
			return null;
	}
	
}
