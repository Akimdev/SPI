package fr.univbrest.dosi.spi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;
import fr.univbrest.dosi.spi.dao.EtudiantRepository;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 * @author DOSI
 *
 */
@Service
public class EtudiantService {

	@Autowired
	private EtudiantRepository etudiantRepository;

	public final void addEtudiant(final Etudiant etudiant) {
		if(!(etudiantRepository.exists(etudiant.getNoEtudiant()))){
		etudiantRepository.save(etudiant);
		}
		else{
			throw new SPIException("Cet étudiant existe déjà en base de données");
		}
	}
	
	public final void updateEtudiant(final Etudiant etudiant) {
		if(etudiantRepository.exists(etudiant.getNoEtudiant())){
		etudiantRepository.save(etudiant);
		}
		else{
			throw new SPIException("Cet étudiant n'existe pas en base de données");	
		}
	}

	public final void deleteEtudiant(final String noEtudiant) {
		etudiantRepository.delete(noEtudiant);
	}

	public final Boolean existEtudiant(final String noEtudiant) {
		return etudiantRepository.exists(noEtudiant);
	}

	public final Etudiant getEtudiant(final String noEtudiant) {
		return etudiantRepository.findOne(noEtudiant);
	}
	/**
	 * @author Othman 
	 * Fonction pour récupérer le nombre d'étudiants
	 * @return retourne le nombre un entier qui définit le nombre d'étudiants existants dans la BD
	 */
	public int getNombreEtudiants(){
		List<Etudiant> listeEtudiants = (List<Etudiant>) etudiantRepository.findAll();
		return listeEtudiants.size();
	}
	/**
	 * soukaina
	 * @param promotionPK
	 * @return
	 */
	public final List<Etudiant> getEtudiantByPromotion(final PromotionPK promotionPk) {
		return etudiantRepository.findByPromotion(promotionPk.getCodeFormation(),promotionPk.getAnneeUniversitaire());
	}
	public final Iterable<Etudiant> getAllEtudiant(){
		return etudiantRepository.findAll();

	}
	
}
