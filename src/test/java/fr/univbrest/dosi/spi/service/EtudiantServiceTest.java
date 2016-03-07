package fr.univbrest.dosi.spi.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.bean.PromotionPK;
import fr.univbrest.dosi.spi.service.EtudiantService;

/**
 * @author Zouhair
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EtudiantServiceTest {
	/**
	 *
	 */
	@Autowired
	private EtudiantService etudiantService;
	/**
	 * Méthode qui teste l'existance d'un étudiant selon son identifiant en vérifiant son nom
	 */
	@Test
	public final void getEtudiantTest() {
		final String noEtudiant = "21406961";
		final Etudiant etudiant = etudiantService.getEtudiant(noEtudiant);
		Assert.assertNotNull(etudiant);
		Assert.assertEquals("AFKIR", etudiant.getNom());
	}
	
	@Test
	public final void getEtudiantByPromotion(){
		PromotionPK promotionPK= new PromotionPK("M2DOSI","2014-2015");
		final List<Etudiant> etudiant= etudiantService.getEtudiantByPromotion(promotionPK);
		Assert.assertNotNull(etudiant);
	}
}
