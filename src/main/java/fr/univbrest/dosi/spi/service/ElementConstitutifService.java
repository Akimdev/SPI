package fr.univbrest.dosi.spi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.ElementConstitutif;
import fr.univbrest.dosi.spi.bean.ElementConstitutifPK;
import fr.univbrest.dosi.spi.bean.UniteEnseignementPK;
import fr.univbrest.dosi.spi.dao.ElementConstitutifRepository;

/**
 * @author DOSI
 *
 */
@Service
public class ElementConstitutifService {

	@Autowired
	private ElementConstitutifRepository elementConstitutifRepository;
	
	public List<ElementConstitutif> getByUe(String codeUe){
		return elementConstitutifRepository.findByCodeUe(codeUe);
	}
	
	public final void addElementConstitutif(final ElementConstitutif elementConstitutif) {
		elementConstitutifRepository.save(elementConstitutif);
	}

	public final void deleteElementConstitutif(final ElementConstitutifPK elementConstitutifPK) {
		elementConstitutifRepository.delete(elementConstitutifPK);
	}

	public final Boolean existElementCostitutif(final ElementConstitutifPK elementConstitutifPK) {
		return elementConstitutifRepository.exists(elementConstitutifPK);
	}

	/**
	 * @author Kenza ABOUAKIL
	 * @param codeFormation
	 * @return
	 */
	public List<ElementConstitutif> findByCodeFormation(final String codeFormation) {
		return elementConstitutifRepository.findByCodeFormation(codeFormation);
	}

	public final ElementConstitutif getElementConstitutif(final ElementConstitutifPK elementConstitutifPK) {
		return elementConstitutifRepository.findOne(elementConstitutifPK);
	}
	
	public List<ElementConstitutif> findAll(){
		return (List<ElementConstitutif>) elementConstitutifRepository.findAll();
	}
	
	public List<ElementConstitutif> getEcByUe(UniteEnseignementPK codeUe){
		return elementConstitutifRepository.findEcByCodeUe(codeUe.getCodeUe(), codeUe.getCodeFormation());
	}

}

