package fr.univbrest.dosi.spi.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.ElementConstitutif;
import fr.univbrest.dosi.spi.bean.ElementConstitutifPK;
import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.UniteEnseignement;
import fr.univbrest.dosi.spi.bean.UniteEnseignementPK;
import fr.univbrest.dosi.spi.bean.utils.EcUtil;
import fr.univbrest.dosi.spi.service.ElementConstitutifService;
import fr.univbrest.dosi.spi.service.UniteEnseignementService;

/**
 * @author Kenza ABOUAKIL
 *
 */
@RestController
public class ElementConstitutifController {

	@Autowired
	private ElementConstitutifService elementConstitutifService;
	@Autowired
	private UniteEnseignementService uniteEnseignementService;

	@RequestMapping(value = "/elementConstitutif/findByCodeFormation/{codeFormation}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<ElementConstitutif> findByCodeFormation(@PathVariable("codeFormation") String codeFormation) {
		return elementConstitutifService.findByCodeFormation(codeFormation);
	}
	
	@RequestMapping(value="/elementConstitufs")
	public List<ElementConstitutif> findAll(){
		return elementConstitutifService.findAll();
	}
	/**
	 * @author abdelhakim Ait Errami
	 * @param ecUtil
	 * cet méthod permet d'ajouter un elementConstituf
	 */
	@RequestMapping(value="/addElementConstituf",headers="Accept=application/json",method=RequestMethod.POST)
	public void add(@RequestBody EcUtil ecUtil){
		/** on récupère les codes nécessaires**/
		String codeUe = ecUtil.getElementConstitutif().getElementConstitutifPK().getCodeUe();
		String codeFormation = ecUtil.getElementConstitutif().getElementConstitutifPK().getCodeFormation();
		/** récupere les objets du util**/
		Enseignant enseignant = ecUtil.getEnseignant();
		UniteEnseignement ue = uniteEnseignementService.uniteEnseignement(new UniteEnseignementPK(codeFormation, codeUe));
		ElementConstitutif ec = ecUtil.getElementConstitutif();
		/** affecttation des objets a ElementConstituf**/
		ec.setNoEnseignant(enseignant);
		ec.setUniteEnseignement(ue);
		elementConstitutifService.addElementConstitutif(ec);
	}
	/**
	 * @author abdelhakim Ait Errami
	 * @param ecUtil
	 * cet méthod permet de supprimer un elementConstituf
	 */
	@RequestMapping(value="/deleteElementConstituf",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void delete(@RequestBody ElementConstitutifPK elementConstitufPK){
		elementConstitutifService.deleteElementConstitutif(elementConstitufPK);
	}
	/**
	 * @author abdelhakim Ait Errami
	 * @param ecUtil
	 * cet méthod permet de récuperer les EC correspndant a un UE
	 */
	@RequestMapping(value="/elementConstitufsByUe/{codeUe}")
	public List<ElementConstitutif> getByUe(@PathVariable("codeUe") String codeUe){
		return elementConstitutifService.getByUe(codeUe);
	}
	
}

