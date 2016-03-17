package fr.univbrest.dosi.spi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.service.EtudiantService;

@RestController
public class EtudiantController {

	@Autowired
	EtudiantService etudiantService;

	/**
	 * 
	 * @return une liste de formation
	 */
	@RequestMapping(value = "/etudiants", produces = "application/json")
	public final Iterable<Etudiant> etudiant() {
		Iterable<Etudiant> liste = etudiantService.getAllEtudiant();
		for (Etudiant etd : liste) {
			System.out.println(etd.getNoEtudiant());
		}
		return etudiantService.getAllEtudiant();
	}

	/**
	 * 
	 * @param noEtudiant
	 * @return retourne un etudiant particulier
	 */
	@RequestMapping(value = "/etudiants/{noEtudiant}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public final Etudiant etudiant(@PathVariable(value = "noEtudiant") final String noEtudiant) {
		return etudiantService.getEtudiant(noEtudiant);
	}
	
	@RequestMapping(value="/nombreEtudiants", produces = { MediaType.APPLICATION_JSON_VALUE })
	public int nombreEtudiants(){
		return etudiantService.getNombreEtudiants();
	}
}