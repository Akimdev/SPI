package fr.univbrest.dosi.spi.controller;

import java.util.List;

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
import fr.univbrest.dosi.spi.dao.ElementConstitutifRepository;
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
	
	@RequestMapping(value = "/elementConstitutif/findByUE", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<ElementConstitutif> findByUE(@RequestBody UniteEnseignementPK ue) {
		return elementConstitutifService.getEcByUe(ue);
	}

	@RequestMapping(value="/ECs")
	public List<ElementConstitutif> findAll(){
		return elementConstitutifService.findAll();
	}
	/**
	 * @author abdelhakim Ait Errami
	 * @param ecUtil
	 * cet méthod permet d'ajouter un elementConstituf
	 */
	@RequestMapping(value="/addEC",headers="Accept=application/json",method=RequestMethod.POST)
	public void add(@RequestBody EcUtil ecUtil){
		String codeUe = ecUtil.getElementConstitutif().getElementConstitutifPK().getCodeUe();
		String codeFormation = ecUtil.getElementConstitutif().getElementConstitutifPK().getCodeFormation();
		Enseignant enseignant = ecUtil.getEnseignant();
		UniteEnseignement ue = uniteEnseignementService.uniteEnseignement(new UniteEnseignementPK(codeFormation, codeUe));
		ElementConstitutif ec = ecUtil.getElementConstitutif();
		ec.setNoEnseignant(enseignant);
		ec.setUniteEnseignement(ue);
		elementConstitutifService.addElementConstitutif(ec);
	}
	
	/**
	 * @author abdelhakim Ait Errami
	 * @param ecUtil
	 * cet méthod permet de modifier un elementConstituf
	 */
	@RequestMapping(value="/updateEC",headers="Accept=application/json",method=RequestMethod.POST)
	public ElementConstitutif update(@RequestBody EcUtil ecUtil){
		String codeUe = ecUtil.getElementConstitutif().getElementConstitutifPK().getCodeUe();
		String codeFormation = ecUtil.getElementConstitutif().getElementConstitutifPK().getCodeFormation();
		Enseignant enseignant = ecUtil.getEnseignant();
		UniteEnseignement ue = uniteEnseignementService.uniteEnseignement(new UniteEnseignementPK(codeFormation, codeUe));
		ElementConstitutif ec = ecUtil.getElementConstitutif();
		ec.setNoEnseignant(enseignant);
		ec.setUniteEnseignement(ue);
		elementConstitutifService.addElementConstitutif(ec);
		return ec;
	}
	
	/**
	 * @author abdelhakim Ait Errami
	 * @param ecUtil
	 * cet méthod permet de supprimer un elementConstituf
	 */
	@RequestMapping(value="/deleteEC",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void delete(@RequestBody ElementConstitutifPK elementConstitufPK){
		elementConstitutifService.deleteElementConstitutif(elementConstitufPK);
	}
	
	/**
	 * @author abdelhakim Ait Errami
	 * @param ecUtil
	 * cet méthod permet de récuperer les EC correspndant a un UE
	 */
	@RequestMapping(value="/EcByUe/{codeUe}")
	public List<ElementConstitutif> getByUe(@PathVariable("codeUe") String codeUe){
		return elementConstitutifService.getByUe(codeUe);
	}
	
	/**
	 * @author abdelhakim Ait Errami
	 * @return nombres d'elements constitutifs
	 */
	@RequestMapping(value="/nombreEC")
	public Integer nombreEc(){
		return elementConstitutifService.nombreEc();
	}
	
	/**
	 * return un EC 
	 */
	@RequestMapping(value="/getEC",method=RequestMethod.POST,headers="Accept=application/json")
	public ElementConstitutif getOne(@RequestBody ElementConstitutifPK ecPK){
		return elementConstitutifService.getElementConstitutif(ecPK);
	}
}