package fr.univbrest.dosi.spi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return (List<Formation>) formationRepository.findAll();
	}

	public Formation findByCodeFormation(String codeFormation) {
		return formationRepository.findByCodeFormation(codeFormation);
	}

	public List<Formation> findByNomFormation(String nomFormation) {
		return formationRepository.findByNomFormation(nomFormation);
	}

	public String getNomFormation(String codeFormation) {
		return formationRepository.findByCodeFormation(codeFormation).getNomFormation();
	}

	public List<String> getNomFormations(List<String> codeFormations) {
		List<String> nomFormations = new ArrayList<String>();
		for (String code : codeFormations) {
			nomFormations.add(formationRepository.findOne(code).getNomFormation());
		}
		return nomFormations;
	/**
	 * @author Othman
	 * méthode retourne le nombre de formations
	 * @return
	 */
	public int nombreFormations(){
	List<Formation> listeFormations = (List<Formation>) formationRepository.findAll();
	return listeFormations.size();
	}
}
