package fr.univbrest.dosi.spi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.bean.PromotionPK;
import fr.univbrest.dosi.spi.dao.EtudiantRepository;

/**
 * @author DOSI
 *
 */
@Service
public class EtudiantService {

	@Autowired
	private EtudiantRepository etudiantRepository;

	public final void addEtudiant(final Etudiant etudiant) {
		etudiantRepository.save(etudiant);
	}

	public final void deletEtudiant(final String noEtudiant) {
		etudiantRepository.delete(noEtudiant);
	}

	public final Boolean existEtudiant(final String noEtudiant) {
		return etudiantRepository.exists(noEtudiant);
	}

	public final Etudiant getEtudiant(final String noEtudiant) {
		return etudiantRepository.findOne(noEtudiant);
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
