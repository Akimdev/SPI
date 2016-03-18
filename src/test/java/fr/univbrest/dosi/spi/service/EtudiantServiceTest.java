package fr.univbrest.dosi.spi.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;

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
	
	@Autowired
	private PromotionService promotionService;
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
	
	@Test
	public void addEtudiantTest(){
		Etudiant etudiant = new Etudiant();
		etudiant.setNoEtudiant("2140836");
		etudiant.setNom("BELHAJ");
		etudiant.setPrenom("Othman");
		etudiant.setSexe("M");
		etudiant.setDateNaissance(new Date("21/08/1993"));
		etudiant.setLieuNaissance("Taza");
		etudiant.setNationalite("Marocaine");
		etudiant.setEmail("belhaj.othman@gmail.com");
		etudiant.setAdresse("2, rue des archives");
		etudiant.setVille("Brest");
		etudiant.setPaysOrigine("MA");
		etudiant.setUniversiteOrigine("UAE");
		PromotionPK promotionPK = new PromotionPK("M2DOSI","2013-2014");
		Promotion promotion = promotionService.getPromotion(promotionPK);
		etudiant.setPromotion(promotion);
		etudiantService.addEtudiant(etudiant);
		List<Etudiant> listeEtudiants = (List<Etudiant>) etudiantService.getAllEtudiant();
		Assert.assertNotNull(listeEtudiants);
		Assert.assertEquals(11, listeEtudiants.size());
	}
	
	@Test
	public void updateEtudiantTest(){
		Etudiant etudiant = etudiantService.getEtudiant("2140836");
		etudiant.setEmail("belhaj.othmane@gmail.com");
		etudiant.setPrenom("othmane");
		etudiantService.updateEtudiant(etudiant);
		Etudiant etudiantBDD = etudiantService.getEtudiant("2140836");
		Assert.assertNotNull(etudiantBDD);
		Assert.assertEquals("othmane", etudiantBDD.getPrenom());
	}
	
	@Test
	public void deleteEtudiantTest(){
		etudiantService.deleteEtudiant("2140836");
		List<Etudiant> listeEtudiants = (List<Etudiant>) etudiantService.getAllEtudiant();
		Assert.assertNotNull(listeEtudiants);
		Assert.assertEquals(10, listeEtudiants.size());
	}
}
