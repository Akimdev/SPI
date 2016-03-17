package fr.univbrest.dosi.spi.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.ElementConstitutif;
import fr.univbrest.dosi.spi.bean.UniteEnseignement;
import fr.univbrest.dosi.spi.bean.UniteEnseignementPK;
import fr.univbrest.dosi.spi.dao.UniteEnseignementRepository;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 * @author DOSI
=======
import fr.univbrest.dosi.spi.bean.ElementConstitutif;
import fr.univbrest.dosi.spi.bean.UniteEnseignement;
import fr.univbrest.dosi.spi.bean.UniteEnseignementPK;
import fr.univbrest.dosi.spi.dao.UniteEnseignementRepository;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 * @author Othman
>>>>>>> ad516cacb3c6f3ca1d08b029a0d4ed977a573f34
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

	public List<ElementConstitutif> getECByUE(final UniteEnseignementPK uniteEnseignementPK){
		UniteEnseignement ue = uniteEnseignementRepository.findOne(uniteEnseignementPK);
		List<ElementConstitutif> listeECs = (List<ElementConstitutif>) ue.getElementConstitutifCollection();
		Collections.sort(listeECs, new Comparator<ElementConstitutif>() {
	        public int compare(final ElementConstitutif ec1, final ElementConstitutif ec2) {
	            return (ec1.getElementConstitutifPK().getCodeEc()).compareTo(ec2.getElementConstitutifPK().getCodeEc());
	        }
	       });
		return listeECs;
	}
}