package fr.univbrest.dosi.spi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.univbrest.dosi.spi.bean.Formation;
import fr.univbrest.dosi.spi.dao.FormationRepository;

/**
 * @author Kenza ABOUAKIL
 *
 */
@Service
public class FormationService {

	@Autowired
	FormationRepository formationRepository;

	public void createFormation(Formation formation) {
		formationRepository.save(formation);
	}

	public void deleteFormation(String codeformation) {
		try {
			formationRepository.delete(codeformation);
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
	}

	public List<Formation> findAll() {
		List<Formation> list = (List<Formation>) formationRepository.findAll();
		Collections.sort(list, new Comparator<Formation>() {
			@Override
			public int compare(Formation f1, Formation f2) {
				return f1.getCodeFormation().compareTo(f2.getCodeFormation()) * (-1);
			}
		});
		return list;
	}

	public List<Formation> findByNomFormation(String nomFormation) {
		return formationRepository.findByNomFormation(nomFormation);
	}

	public Formation getFormation(String codeFormation) {
		return formationRepository.findByCodeFormation(codeFormation);
	}

	public String getNomFormation(String codeFormation) {
		return formationRepository.findByCodeFormation(codeFormation)
				.getNomFormation();
	}

	public List<String> getNomFormations(List<String> codeFormations) {
		List<String> nomFormations = new ArrayList<String>();
		for (String code : codeFormations) {
			nomFormations.add(formationRepository.findOne(code)
					.getNomFormation());
		}
		return nomFormations;
	}

	/**
	 * @author LAKRAA
	 *  méthode pour la suppression d'une formation
	 * @return
	 */
	@RequestMapping(value = "/formation/delete", headers = "Accept=application/json")
	public void removeFormation(
			@RequestParam("codeFormation") String codeFormation) {
		formationRepository.delete(codeFormation);
	}
	
	public Formation traitement(String codeFormation) {
		return formationRepository.findOne(codeFormation);
	}
	
	/**
	 * @author Othman méthode retourne le nombre de formations
	 * @return
	 */
	public long nombreFormations() {
		return formationRepository.count();
		// List<Formation> listeFormations = (List<Formation>) formationRepository.findAll();
		// return listeFormations.size();
	}
}
