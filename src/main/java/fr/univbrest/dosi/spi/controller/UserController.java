package fr.univbrest.dosi.spi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.User;
import fr.univbrest.dosi.spi.exception.SPIException;
import fr.univbrest.dosi.spi.service.AuthentificationService;

/**
 * Authentification Controller
 * @author Youssef
 */
@RestController
public class UserController {
	
	@Autowired
	AuthentificationService authentificationService;

	/**
	 * vérifie si l'utilisateur existe pour s'authentifier sinon l'authentification est refusée 
	 * @param request
	 * @param user
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.POST, headers = "Accept=application/json")
	public void authentifier(final HttpServletRequest request, @RequestBody final User user) {
		final User users = authentificationService.logIn(user.getUsername(), user.getPwd());
		user.setPwd(null);
		if (users != null) {
			request.getSession().setAttribute("user", users);
		} else {
			request.getSession().removeAttribute("user");
			throw new SPIException("impossible de s'authentifier");
		}

	}

	/**
	 * @param request
	 * @param response
	 * @return 
	 * Retourne l'utilisateur connecté
	 */
	@RequestMapping(value = "/user")
	public User users(final HttpServletRequest request, final HttpServletResponse response) {
		final User user = (User) request.getSession().getAttribute("user");
		return user;

	}
	
	/**
	 * @param request
	 * Pour se déconnecter, on supprime l'objet user de la session ouverte
	 */
	@RequestMapping(value = "/deconnexion", method = RequestMethod.GET)
	public void authentifier(final HttpServletRequest request) {
		request.getSession().removeAttribute("user");
	}

}
