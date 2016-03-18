package fr.univbrest.dosi.spi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import fr.univbrest.dosi.spi.bean.ElementConstitutif;
import fr.univbrest.dosi.spi.bean.Formation;
import fr.univbrest.dosi.spi.bean.UniteEnseignement;
import fr.univbrest.dosi.spi.dao.FormationRepository;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 * @author Kenza ABOUAKIL Service Formation
 *
 */
@Service
public class FormationService {

	@Autowired
	FormationRepository formationRepository;

	/**
	 * @author Kenza ABOUAKIL
	 *
	 *         Création d'une nouvelle formation
	 * @param formation
	 *            : la nouvelle formation à créer
	 */
	public void addFormation(Formation formation) {
		if (!formationRepository.exists(formation.getCodeFormation()))
			formationRepository.save(formation);
		else
			throw new SPIException("Le Code Formation existe déjà dans la BD !");
	}
	/**
	 * @author Othman
	 * 
	 * 		Modification d'une formation existante
	 * @param formation
	 * 			: la formation à modifier
	 */
	public void updateFormation(Formation formation) {
		if (!formationRepository.exists(formation.getCodeFormation()))
			throw new SPIException("Le Code Formation n'existe pas dans la BD !");
		else{
		formationRepository.save(formation);
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
	
	/**
	 * @author Kenza ABOUAKIL Suppression d'une formation
	 * @return
	 */
	public void deleteFormation(@RequestParam("codeFormation") String codeFormation) {
		if (formationRepository.exists(codeFormation))
			formationRepository.delete(codeFormation);
		else
			throw new SPIException("La formation n'existe pas dans la BD !");
	}

	/**
	 * @author Kenza ABOUAKIL
	 *
	 *         Retourne la lite de toutes les formations
	 * @return toute les formations
	 */
	public List<Formation> getAllFormation() {
		return formationRepository.findAll();
	}

	public Formation getFormation(String codeFormation) {
		return formationRepository.findByCodeFormation(codeFormation);
	}

	/**
	 * @Author Kenza ABOUAKIL
	 *
	 * @param codeFormation
	 * @return Le nom de la formation fournise en parametre
	 */
	public String getNomFormation(String codeFormation) {
		return formationRepository.findByCodeFormation(codeFormation).getNomFormation();
	}

	/**
	 * @Author Kenza ABOUAKIL
	 *
	 * @param codeFormations
	 * @return une liste de nom des formations fournises en parametre
	 */
	public List<String> getNomFormations(List<String> codeFormations) {
		List<String> nomFormations = new ArrayList<String>();
		for (String code : codeFormations) {
			nomFormations.add(formationRepository.findOne(code).getNomFormation());
		}
		return nomFormations;
	}

	/**
	 * @author Kenza ABOUAKIL
	 *
	 * @return le nombre de formations
	 */
	public long nombreFormations() {
		return formationRepository.count();
	}
	
	/**
	 * @author Othman
	 * @param codeFormation
	 * @return Cette méthode retourne une liste d'unités d'enseignement en lui donnant comme paramètre
	 * un code de formation
	 */
	public List<UniteEnseignement> getUEsByCodeFormation(String codeFormation){
		Formation formation = formationRepository.findOne(codeFormation);
		List<UniteEnseignement> listeUEs = (List<UniteEnseignement>) formation.getUniteEnseignementCollection();
		Collections.sort(listeUEs, new Comparator<UniteEnseignement>() {
	        public int compare(final UniteEnseignement ue1, final UniteEnseignement ue2) {
	            return (ue1.getUniteEnseignementPK().getCodeUe()).compareTo(ue2.getUniteEnseignementPK().getCodeUe());
	        }
	       });
		return listeUEs;
	}
}
