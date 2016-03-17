package fr.univbrest.dosi.spi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import fr.univbrest.dosi.spi.bean.Rubrique;
import fr.univbrest.dosi.spi.service.RubriqueService;

/**
 * 
 * @author ASSABBAR
 */
 
@RestController
public class RubriqueController {

	@Autowired
	RubriqueService rubriqueService;

	/**
	 * l'ajout d'une rubrique
	 *
	 * @param rubrique
	 * @return la rubrique ajoutée
	 */
	@RequestMapping(value = "/addRubrique", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public final Rubrique ajouterRubrique(@RequestBody final Rubrique rubrique) {
		return rubriqueService.addRubrique(rubrique);
	}

	/**
	 * La modification d'une rubrique
	 *
	 * @param rubrique
	 * @return une rubrique
	 */
	@RequestMapping(value = "/updateRubrique", method = RequestMethod.POST, headers = "Accept=application/json")
	public final Rubrique editRubrique(@RequestBody final Rubrique rubrique) {
		return rubriqueService.updateRubrique(rubrique);
	}

	/**
	 * @author Kenza ABOUAKIL permet de retourner la valeur de MaxIdRubrique pour généré un nouveau ID à la Rubrique
	 * @return l'IdRubrique maximal pour tous les Rubriques
	 */
	@RequestMapping(value = "/getMaxIdRubrique")
	public Integer getMaxIdRubrique() {
		return rubriqueService.getMaxIdRubrique();
	}

	/**
	 * la recuperation de la liste des rubriques
	 *
	 * @return la liste des rubriques
	 */
	@RequestMapping(produces = "application/json", value = "/rubriques")
	public Iterable<Rubrique> listerRubrique() {
		return rubriqueService.listRubrique();
	}

	/**
	 * La suppression d'une rubrique par son ID
	 *
	 * @param idRubrique
	 */
	@RequestMapping(value = "/deleteRubrique/{idRubrique}", headers = "Accept=application/json", method = RequestMethod.GET)
	public final void removeRubrique(@PathVariable("idRubrique") final Long idRubrique) {
		rubriqueService.deleteRubrique(idRubrique);
	}

	/**
	 * La recuperation d'une rubrique par son ID
	 *
	 * @param idRubrique
	 * @return
	 */
	@RequestMapping(value = "/rubrique/{idRubrique}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public final Rubrique rubrique(@PathVariable(value = "idRubrique") final Long idRubrique) {
		return rubriqueService.getRubrique(idRubrique);
	}

	/**
	 * La recuperation du nombre des rubriques
	 * 
	 * @param idRubrique
	 * @return
	 */
	@RequestMapping(value="/nombreRubriques")
	public int nombreRubriques(){
		return rubriqueService.nombreRubriques();
	}
}
