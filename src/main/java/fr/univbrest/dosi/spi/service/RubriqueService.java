package fr.univbrest.dosi.spi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.Rubrique;
import fr.univbrest.dosi.spi.dao.RubriqueRepository;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 *
 * @author ASSABBAR
 *
 */
@Service
public class RubriqueService {
	@Autowired
	private RubriqueRepository rubriqueRepository;

	/**
	 *
	 * @param rubrique
	 * @return la rubrique ajoutée
	 */
	public final Rubrique addRubrique(final Rubrique rubrique) {
		if ((rubriqueRepository).exists(rubrique.getIdRubrique())) {
			throw new SPIException("le rubrique que vous souhaitez ajouter exsite déja ");
		}
		return rubriqueRepository.save(rubrique);
	}

	/**
	 *
	 * @param idRubrique
	 */
	public final void deleteRubrique(final long idRubrique) {
		if (rubriqueRepository.exists(idRubrique)) {
			rubriqueRepository.delete(idRubrique);
		} else {
			throw new SPIException("Cant delete Rubrique");
		}
	}

	/**
	 * @author Kenza ABOUAKIL
	 * @return le max des IdRubriques
	 */
	public Integer getMaxIdRubrique() {
		return rubriqueRepository.getMaxIdRubrique();
	}

	public final Rubrique getRubrique(final Long idRubrique) {
		return rubriqueRepository.findOne(idRubrique);
	}

	/**
	 *
	 * @return la liste des rubrique
	 */
	public final Iterable<Rubrique> listRubrique() {
		return rubriqueRepository.findAll();
	}

	public final Rubrique updateRubrique(final Rubrique rubrique) {
		if (rubriqueRepository.exists(rubrique.getIdRubrique())) {
			return rubriqueRepository.save(rubrique);
		} else {
			throw new SPIException("la rubrique que vous souhaitez modifier n'exsite pas ");
		}

	}
	
	/** 
	 * @author Zouhair
	 * Cette méthode retourne le nombre des rubriques
	 * @return
	 */
	public int nombreRubriques(){
		List<Rubrique> listRubrique = (List<Rubrique>) rubriqueRepository.findAll();
		return listRubrique.size();
	}
}
