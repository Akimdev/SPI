package fr.univbrest.dosi.spi.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.univbrest.dosi.spi.bean.CgRefCodes;
import fr.univbrest.dosi.spi.service.DomainesSevices;

/**
 * @author LAKRAA Classe permettant de récuperer les valeurs des domaines
 *         dynamiques à partir de la table CG-REF-CODES
 */ 
public class DomainesController {

	@Autowired
	DomainesSevices domainesService;
	
	
	/**
	 * @author LAKRAA
	 * Méthode qui permet de recuperer les diplomes à partir du domaine dynamique
	 */
	
	@RequestMapping(value = "/domaines/{rvDomain}", produces = { MediaType.APPLICATION_JSON_VALUE }, headers="Accept: application/hal+json")
	public @ResponseBody List<String> getDomainByRvDomain(
			@PathVariable(value = "rvDomain") String rvDomain) {
		return domainesService.getDomainDipolme(rvDomain);
	}
	
	@RequestMapping(value = "/domaines/{idCgrc}", produces = { MediaType.APPLICATION_JSON_VALUE }, headers="Accept=application/json")
	public CgRefCodes getDomain(
			@PathVariable(value = "rvDomain") BigDecimal idCgrc) {
		return domainesService.getDomainById(idCgrc);
	}

}
