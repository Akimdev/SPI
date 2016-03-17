package fr.univbrest.dosi.spi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.exception.SPIException;
import fr.univbrest.dosi.spi.service.QualificatifService;

/**
 *
 * @author othman cet classe permet de gérer le CRUD d'un qualificatif
 */
 
@RestController
public class QualificatifController {

	@Autowired
	QualificatifService qualifServ;

	@RequestMapping(value = "/ajouterQualificatif", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void ajoutQualificatif(@RequestBody final Qualificatif qualif) {
		if (qualifServ.getQualificatif(qualif.getIdQualificatif()) == null)
			qualifServ.addQualificatif(qualif);
		else
			throw new SPIException("Impossible de créer le qualificatif, l'IdQualificatif existe déjà!");
	}

	/**
	 * @author Kenza ABOUAKIL permet de retourner la valeur de MaxIdQualificatif pour généré un nouveau ID au qualificatif
	 * @return l'IdQualificatif maximal pour tous les qualificatifs
	 */
	 
	@RequestMapping(value = "/getMaxIdQualificatif")
	public Integer getMaxIdQualificatif() {
		return qualifServ.getMaxIdQualificatif();
	}

	@RequestMapping(value = "/qualificatif/{idQualificatif}", headers = "Accept=application/json")
	public Qualificatif getQualificatifById(@PathVariable("idQualificatif") Long idQualificatif) {
		return qualifServ.getQualificatif(idQualificatif);
	}

	@RequestMapping(value = "/listerQualificatif")
	public List<Qualificatif> listerQualificatif() {
		return qualifServ.listeQualificatif();
	}

	@RequestMapping(value = "/updateQualificatif", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void modifyQualificatif(@RequestBody final Qualificatif qualif) {
		if (qualifServ.getQualificatif(qualif.getIdQualificatif()) != null)
			qualifServ.modifyQualificatif(qualif);
		else
			throw new SPIException("Impossible de créer le qualificatif, l'IdQualificatif existe déjà!");
	}

	@RequestMapping(value = "/supprimerQualificatif", method = RequestMethod.DELETE)
	public void suppressionQualificatif(Qualificatif qualif) {
		qualifServ.deleteQualificatif(qualif);
	}

	@RequestMapping(value = "/supprimerQualificatif", headers = "Accept=application/json")
	public void suppressionQualificatifById(@RequestParam("idQualificatif") Long idQualificatif) {
		qualifServ.deleteQualificatifById(idQualificatif);
	}

	@RequestMapping(value = "/supprimerQualificatifBis", headers = "Accept=application/json")
	public void suppressionQualificatifByIdBiss(@RequestParam("idQualificatif") Long idQualificatif) {
		qualifServ.deleteQualificatifById(idQualificatif);
	}
	
	/**
	 * @author Zouhair
	 * La recuperation du nombre des promotions
	 * 
	 * @param idRubrique
	 * @return
	 */
	@RequestMapping(value="/nombreQualificatifs")
	public int nombreQualificatifs(){
		return qualifServ.nombreQualificatifs();
	}
}