package fr.univbrest.dosi.spi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.UniteEnseignement;
import fr.univbrest.dosi.spi.bean.UniteEnseignementPK;
import fr.univbrest.dosi.spi.dao.UniteEnseignementRepository;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 * @author Othman
 *
 */
@Service
public class UniteEnseignementService {

	@Autowired
	UniteEnseignementRepository uniteEnseignementRepository;

	public void addUE(final UniteEnseignement uniteEnseignement) {
		if(!uniteEnseignementRepository.exists(uniteEnseignement.getUniteEnseignementPK()))
			uniteEnseignementRepository.save(uniteEnseignement);
		else
			throw new SPIException("L'unité d'enseignement existe déjà !");
	}
	
	public void updateUE(final UniteEnseignement uniteEnseignement) {
		uniteEnseignementRepository.save(uniteEnseignement);
	}

	public final void deleteUE(final UniteEnseignementPK uniteEnseignementPK) {
		uniteEnseignementRepository.delete(uniteEnseignementPK);
	}

	public Boolean existUnitEnseignement(final UniteEnseignementPK uniteEnseignementPK) {
		return uniteEnseignementRepository.exists(uniteEnseignementPK);
	}

	public UniteEnseignement getUE(final UniteEnseignementPK uniteEnseignementPK){
		return uniteEnseignementRepository.findOne(uniteEnseignementPK);
	}
	/**
	 * @author Kenza ABOUAKIL
	 * @param codeFormation
	 * @return
	 */
	public List<UniteEnseignement> findByCodeFormation(String codeFormation) {
		return uniteEnseignementRepository.findByCodeFormation(codeFormation);
	}

	/**
	 * @author Othman cette méthode renvoie la liste de tous les UEs
	 * @return une liste des UEs non ordonnées
	 */
	public List<UniteEnseignement> getAllUEs() {
		return (List<UniteEnseignement>) uniteEnseignementRepository.findAll();
	}

	public final List<UniteEnseignement> getUEByEnseignant(final Integer noEnseignant) {

		return uniteEnseignementRepository.findByNoEnseignant(noEnseignant);

	}

	/**
	 * cette méthode retourne le nombre d'unité d'enseignements présentes dans la BDD
	 *
	 * @return nombre d'UEs
	 */
	public int nombreUEs() {
		List<UniteEnseignement> listeUEs = (List<UniteEnseignement>) uniteEnseignementRepository.findAll();
		return listeUEs.size();
	}

	public final UniteEnseignement uniteEnseignement(final UniteEnseignementPK uniteEnseignementPK) {
		return uniteEnseignementRepository.findOne(uniteEnseignementPK);
	}
}
