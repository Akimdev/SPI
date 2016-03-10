package fr.univbrest.dosi.spi.service;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Rubrique;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 * 
 * @author ASSABBAR
 *
 *         cette classe permet de tester les metodes CRUD du service RubriqueService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RubriqueServiceTest {
	@Autowired
	RubriqueService rubriqueService;

	/**
	 * 
	 * la methode permet de tester l'ajout d'une Rubrique
	 */
	@Test
	public final void testAddRubrique() {
		BigInteger ord = new BigInteger("12");
		final Rubrique rbq = new Rubrique();
		rbq.setIdRubrique(12l);
		rbq.setType("RBS");
		rbq.setDesignation("TESTU");
		rbq.setOrdre(ord);
		try {
			rubriqueService.addRubrique(rbq);
		} catch (final SPIException ex) {
			Assert.assertEquals("la rubrique existe déja ", ex.getMessage());
		}
	}

	/**
	 * cette metodes permet de tester la modification d'une rubrique
	 */
	@Test
	public final void testupdateRubrique() {
		final Rubrique rbq = new Rubrique();
		BigInteger ord = new BigInteger("12");
		rbq.setIdRubrique(12l);
		rbq.setType("RBS");
		rbq.setDesignation("UTEST");
		rbq.setOrdre(ord);
		try {
			rubriqueService.updateRubrique(rbq);
			Rubrique rubrique = rubriqueService.getRubrique((long) 9);
			Assert.assertNotNull(rubrique);
			Assert.assertEquals(rubrique.getType(), rubrique.getType());
			Assert.assertEquals(rubrique.getDesignation(), rubrique.getDesignation());
		} catch (final SPIException ex) {
			Assert.assertEquals("la rubrique que vous souhaitez modifier n'exsite pas ", ex.getMessage());
		}
	}

	/**
	 * cette methode permet de supprimer une rubrique
	 */
	@Test
	public final void testdeleteRubrique() {
		final long idRubrique = 12l;
		try {
			rubriqueService.deleteRubrique(idRubrique);
		} catch (final SPIException ex) {
			Assert.assertEquals("la rubrique ne peut pas etre supprimee", ex.getMessage());
		}

	}

	/**
	 * cette methode permet de recuperer une Rubrique
	 */
	@Test
	public final void testgetRubrique() {
		final long idRubrique = 11l;
		final Rubrique rubrique = rubriqueService.getRubrique(idRubrique);
		Assert.assertNotNull(rubrique);
	}

}
