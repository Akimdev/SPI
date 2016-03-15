package fr.univbrest.dosi.spi.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.univbrest.dosi.spi.bean.Authentification;


/**
 * 
 * @author Youssef
 * Authentification repository (DAO)
 */
@RepositoryRestResource(collectionResourceRel = "authentification", path = "authentification")
public interface AuthentificationRepository extends PagingAndSortingRepository<Authentification, Long> {
	

	/**
	 * Vérifier la connexion avec un couple Pseudo et Mot de passe
	 * @author Youssef
	 * @param pseudoConnection
	 * @param motPasse
	 * @return
	 */
	public Authentification findByLoginAndPwd(@Param("pseudoConnection") String pseudoConnection, @Param("motPasse") String motPasse);


}