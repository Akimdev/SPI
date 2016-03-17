package fr.univbrest.dosi.spi.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Iterables;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Formation;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class FormationServiceTest {

	@Autowired
	FormationService formationService;

	@Test
	public void addFormationTest() {

		Formation formation = new Formation();
		formation.setCodeFormation("M2DOSIII");
		formation.setDiplome("M");
		formation.setDoubleDiplome('O');
		formation.setN0Annee((short) 2);
		formation.setNomFormation("2eme annee Science de l'information...");
		formation.setDebutAccreditation(new java.util.Date("11/11/2011"));
		formation.setFinAccreditation(new java.util.Date("11/11/2019"));
		formationService.addFormation(formation);
		Formation f = formationService.getFormation("M2DOSIII");
		Assert.assertNotNull(f);
		Assert.assertEquals(formation, f);
	}

	@Test
	public void deleteFormationTest() {
		int size = Iterables.size(formationService.getAllFormation());
		formationService.deleteFormation("M2DOSII");
		Assert.assertEquals(size - 1, Iterables.size(formationService.getAllFormation()));
	}

	@Test
	public void getAllFormationTest() {
		Iterable<Formation> listeFor = formationService.getAllFormation();
		Assert.assertNotNull(listeFor);
		// Assert.assertEquals(5, Iterables.size(listeFor));
	}

	@Test
	public void getFormationTest() {
		Formation formation = formationService.getFormation("M2DOSI");
		Assert.assertNotNull(formation);
		Assert.assertEquals("Master Développement à l'Offshore des Systèmes d'Information", formation.getNomFormation());
	}
}