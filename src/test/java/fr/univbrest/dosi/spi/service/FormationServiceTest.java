package fr.univbrest.dosi.spi.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Iterables;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Formation;

/**
 * Classe de test d'int�gration permettant de tester le service
 *
 * @author Kenza ABOUAKIL
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class FormationServiceTest {

	/**
	 * M�thode testant le cas nominal du load images main revolver
	 *
	 */
	@Autowired
	FormationService formationService;

	@Test
	public void getFormationByCodeFormationTest() {

		Formation formation = formationService.findByCodeFormation("M2DOSI");
		Assert.assertNotNull(formation);
		Assert.assertEquals("Master Développement à l'Offshore des Systèmes d'Information", formation.getNomFormation());
	}

	/**
	 * Initialisation de param�tres avant chaque test
	 */
	@Before
	public void init() {

	}

	@Test
	public void insertFormationTest() {

		Formation formation = new Formation();
		formation.setCodeFormation("M2SII");
		formation.setDiplome("M");
		formation.setDoubleDiplome('O');
		formation.setN0Annee((short) 2);
		formation.setNomFormation("2eme annee Science de l'information...");
		formation.setDebutAccreditation(new java.util.Date("11/11/2011"));
		formation.setFinAccreditation(new java.util.Date("11/11/2019"));
		formationService.createFormation(formation);

		String codeF = "M2SII";
		Formation f = formationService.findByCodeFormation(codeF);

		Assert.assertEquals(formation, f);
	}

	@Test
	public void testEns() {
		Iterable<Formation> listeFor = formationService.findAll();
		Assert.assertNotNull(listeFor);
		Assert.assertEquals(5, Iterables.size(listeFor));
	}
}
