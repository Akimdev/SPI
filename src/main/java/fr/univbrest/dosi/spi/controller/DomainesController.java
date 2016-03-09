package fr.univbrest.dosi.spi.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.CgRefCodes;
import fr.univbrest.dosi.spi.service.DomainesSevices;

/**
 * @author LAKRAA Classe permettant de récuperer les valeurs des domaines dynamiques à partir de la table CG-REF-CODES
 */
@RestController
public class DomainesController {

	@Autowired
	DomainesSevices domainesService;

	@RequestMapping(value = "/domaine/{idCgrc}", produces = { MediaType.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public CgRefCodes getDomain(@PathVariable(value = "idCgrc") BigDecimal idCgrc) {
		return domainesService.getDomainById(idCgrc);
	}

	/**
	 * @author LAKRAA Méthode qui permet de recuperer un domaine dynamique
	 */
	@RequestMapping(value = "/getDomaine/{rvDomain}")
	public List<CgRefCodes> getDomainByRvDomain(@PathVariable(value = "rvDomain") final String rvDomain) {
		return domainesService.getDomainByRvDomain(rvDomain);
	}
}
