package fr.univbrest.dosi.spi.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.dao.QualificatifRepository;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 *
 * @author Othman
 *
 *         Cette classe représente la partie service de la gestion de CRUD des qualificatifs
 */

@Service
public class QualificatifService {

	@Autowired
	QualificatifRepository qualifRepo;

	public void addQualificatif(Qualificatif qualif) {
		if (!(qualifRepo.exists(qualif.getIdQualificatif()))) {
			qualifRepo.save(qualif);
		} else {
			throw new SPIException("Echec de l'ajout d'un nouveau qualificatif, Qualificatif existant!");
		}
	}

	public void deleteQualificatif(Qualificatif qualif) {
		qualifRepo.delete(qualif);
	}

	public void deleteQualificatifById(Long idQualificatif) {
		qualifRepo.delete(idQualificatif);

	}

	/**
	 * @author Kenza ABOUAKIL
	 * @return le max des IdQualificatifs
	 */
	public Integer getMaxIdQualificatif() {
		return qualifRepo.getMaxIdQualificatif();
	}

	public final Qualificatif getQualificatif(Long idQualificatif) {
		return qualifRepo.findOne(idQualificatif);
	}

	public List<Qualificatif> listeQualificatif() {
		List<Qualificatif> qualifList = new ArrayList<Qualificatif>();
		qualifList = (List<Qualificatif>) qualifRepo.findAll();
		return qualifList;
	}

	public void modifyQualificatif(Qualificatif qualif) {
		qualifRepo.save(qualif);
	}
	
	/** 
	 * @author Zouhair
	 * Cette méthode retourne le nombre des qualificatifs
	 * @return
	 */
	public int nombreQualificatifs(){
		List<Qualificatif> listQualificatifs = (List<Qualificatif>) qualifRepo.findAll();
		return listQualificatifs.size();
	}
}