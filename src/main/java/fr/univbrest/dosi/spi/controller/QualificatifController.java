package fr.univbrest.dosi.spi.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.service.QualificatifService;

@RestController
public class QualificatifController {

	@Autowired
	QualificatifService qualifServ;
	
	@RequestMapping(value = "/ajouterQualificatif", method = RequestMethod.POST, produces =  { MediaType.APPLICATION_JSON_VALUE})
	public void ajoutQualificatif(@RequestBody final Qualificatif qualif){
		qualifServ.addQualificatif(qualif);
	}
	
	@RequestMapping(value="/modifierQualificatif", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public void modifyQualificatif(@RequestBody final Qualificatif qualif){
		qualifServ.modifyQualificatif(qualif);
	}
	
	@RequestMapping(value="/supprimerQualificatif")
	public void suppressionQualificatif(Qualificatif qualif){
		qualifServ.deleteQualificatif(qualif);
	}
	@RequestMapping(value="/supprimerQualificatif", headers="Accept=application/json")
	public void suppressionQualificatifById(@RequestParam("idQualificatif") Long idQualificatif){
		qualifServ.deleteQualificatifById(idQualificatif);
	}
	
	@RequestMapping(value="/listerQualificatif")
	public List<Qualificatif> listerQualificatif(){
		return qualifServ.listeQualificatif();
	}
}
