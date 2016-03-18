package fr.univbrest.dosi.spi.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.UniteEnseignement;
import fr.univbrest.dosi.spi.dao.EnseignantRepository;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 * @author DOSI
 *
 */
@Service
public class EnseignantService {
	/**
	 *
	 */
	@Autowired
	private EnseignantRepository enseignantRepository;

	/**
	 *
	 * @param enseignant
	 *            l'entité
	 * @return l'enseignant ajouter
	 */
	public final Enseignant addEnseignant(final Enseignant enseignant) {
		return enseignantRepository.save(enseignant);
	}

	/**
	 *
	 * @param noEnseignant
	 *            l'id de l'enseignant
	 */
	public final void deleteEnseignant(final Integer noEnseignant) {
		// enseignantRepository.delete(noEnseignant);
		// return enseignantRepository.findAll();
		if (enseignantRepository.exists(noEnseignant)) {
			enseignantRepository.delete(noEnseignant);
		} else {
			throw new SPIException("Cant delete Enseignant");
		}
		/*
		 * try { enseignantRepository.delete(noEnseignant); } catch (EmptyResultDataAccessException e1) { throw new SPIException("Il y a aucun enseignant avec ce numero", e1); } catch (Exception e) {
		 * throw new SPIException("Cant delete Enseignant", e); }
		 */
	}

	/**
	 *
	 * @param noEnseignant
	 *            l'id de l'enseignant
	 * @return test de existe
	 */
	public final Boolean existEnseignant(final Integer noEnseignant) {
		final Boolean exist = enseignantRepository.exists(noEnseignant);
		if (exist) {
			return exist;
		} else {
			throw new SPIException("Il y a aucun enseignant avec ce numero");
		}
	}

	/*
	 * public Enseignant addEnseignant(Enseignant enseignant) { Boolean exist = enseignantRepository.exists(enseignant .getNoEnseignant()); if (exist) { throw new SPIException(
	 * "l'enseignant que vous souhaitez ajouter exsite déja ");
	 * 
	 * } else { return enseignantRepository.save(enseignant); } }
	 */
	/**
	 *
	 * @param id
	 *            l'id de l'enseignant
	 * @return l'enseignant
	 */
	public final Enseignant getEnseignant(final Integer noEnseignant) {
		return enseignantRepository.findOne(noEnseignant);
	}

	/**
	 *
	 * @param nom
	 *            de l'enseignant
	 * @return la liste des enseignants
	 */
	public final List<Enseignant> getEnseignantByNom(final String nom) {
		return enseignantRepository.findByNom(nom);
	}

	/**
	 *
	 * @return getter
	 */
	public final EnseignantRepository getEnseignantRepository() {
		return enseignantRepository;
	}

	/**
	 *
	 * @return liste des enseignant
	 */
	public final Iterable<Enseignant> listens() {
		List<Enseignant> list = (List<Enseignant>) enseignantRepository.findAll();
		Collections.sort(list, new Comparator<Enseignant>() {
			@Override
			public int compare(Enseignant e1, Enseignant e2) {
				return e1.getNom().compareTo(e2.getNom());
			}
		});
		return list;
	}

	/**
	 *
	 * @param enseignantRepository
	 *            setter
	 */
	public final void setEnseignantRepository(final EnseignantRepository enseignantRepository) {
		this.enseignantRepository = enseignantRepository;
	}

	/**
	 *
	 * @param enseignant
	 *            l'entité enseignant
	 * @return enseignat modifier
	 */
	public final Enseignant updateEnseignant(final Enseignant enseignant) {
		if (enseignantRepository.exists(enseignant.getNoEnseignant())) {
			return enseignantRepository.save(enseignant);
		} else {
			throw new SPIException("l'enseignant que vous souhaitez modifier n'exsite pas ");
		}
	}

	/**
	 * Cette méthode retourne le nombre d'enseignants
	 * 
	 * @return nombre d'enseignants
	 */
	public int nombreEnseignants() {
		List<Enseignant> listeEnseignants = (List<Enseignant>) enseignantRepository.findAll();
		return listeEnseignants.size();
	}

	/**
	 * @author Othman
	 * @param noEnseignant
	 * @return
	 * 
	 *         Cette méthode retourne une liste triée d'unités d'enseignement
	 */
	public List<UniteEnseignement> getUEByNoEnseignant(Integer noEnseignant) {
		Enseignant ens = enseignantRepository.findOne(noEnseignant);
		List<UniteEnseignement> listeUEs = (List<UniteEnseignement>) ens.getUniteEnseignementCollection();
		Collections.sort(listeUEs, new Comparator<UniteEnseignement>() {
			@Override
			public int compare(final UniteEnseignement ue1, final UniteEnseignement ue2) {
				return (ue1.getUniteEnseignementPK().getCodeUe()).compareTo(ue2.getUniteEnseignementPK().getCodeUe());
			}
		});
		return listeUEs;
	}

}
