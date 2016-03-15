package fr.univbrest.dosi.spi.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 * 
 * @author ASSABBAR
 * 
 *         cette classe permet de tester les metodes CRUD du service QualificatifService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class QualificatifServiceTest {
	@Autowired
	private QualificatifService qualificatifService;
	private long idQualificatif;
	private String maximal;
	private String minimal;

	/**
	 * cette methode permet de tester l'ajout d'un Qualificatif si l'IdQualificatif existe deja le qualificatif ne va pas etre ajouté
	 */
	@Test
	public final void addQualificatif() {
		final Qualificatif qualificatif = new Qualificatif();
		final Long id = (long) 20;
		qualificatif.setIdQualificatif(id);
		qualificatif.setMaximal("TPauvre");
		qualificatif.setMinimal("TRiche");
		try {
			qualificatifService.addQualificatif(qualificatif);
		} catch (final SPIException ex) {
			Assert.assertEquals("le qualifiatif existe déja ", ex.getMessage());
		}
	}

	/**
	 * cette metodes permet de tester la modification d'un qualificatif.
	 */
	@Test
	public final void modifyQualificatif() {
		final Qualificatif qualificatif = new Qualificatif();
		qualificatif.setIdQualificatif((long) 21);
		qualificatif.setMaximal("Tres");
		qualificatif.setMinimal("TresRiche");
		try {
			qualificatifService.modifyQualificatif(qualificatif);
			Qualificatif newQualificatif = qualificatifService.getQualificatif((long) 21);
			Assert.assertNotNull(newQualificatif);
			Assert.assertEquals(qualificatif.getMaximal(), newQualificatif.getMaximal());
			Assert.assertEquals(qualificatif.getMinimal(), newQualificatif.getMinimal());
		} catch (final SPIException ex) {
			Assert.assertEquals("le qualifiatif que vous souhaitez modifier n'exsite pas ", ex.getMessage());
		}
	}

	/**
	 * cette methode permet de supprimer un qualificatif par son ID
	 */
	@Test
	public final void deleteQualificatifById() {
		final Long id = (long) 21;
		try {
			qualificatifService.deleteQualificatifById(id);

		} catch (final SPIException ex) {
			Assert.assertEquals("le qualificatif ne peut pas etre supprimé", ex.getMessage());
		}
	}

	/**
	 * cette methode permet de recuperer un Qualificatif
	 */
	@Test
	public final void getQualificatif() {
		this.idQualificatif = 14;
		this.maximal = "Pauvre";
		this.minimal = "Riche";
		final Qualificatif qualificatif = qualificatifService.getQualificatif(this.idQualificatif);
		Assert.assertNotNull(qualificatif);
		Assert.assertEquals(this.maximal, qualificatif.getMaximal());
		Assert.assertEquals(this.minimal, qualificatif.getMinimal());
	}
}
