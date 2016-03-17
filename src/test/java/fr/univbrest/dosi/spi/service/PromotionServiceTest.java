package fr.univbrest.dosi.spi.service;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 * @author Soukaina BAQLOUL
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PromotionServiceTest {

	@Autowired
	private PromotionService promotionService;
	private final String siglePromotion="DOSI5";
	
    final String noEtudiant="1";
	
	private EnseignantService enseignantService;
	
	private FormationService formationService;
	private EtudiantService etudiantService;
 

	@Test
	public final void getPromotion() {

		PromotionPK promotionPK = new PromotionPK("M2DOSI", "2014-2015");
		final Promotion promotion = promotionService.getPromotion(promotionPK);
		Assert.assertNotNull(promotion);
		Assert.assertEquals(this.siglePromotion, promotion.getSiglePromotion());
		
	}
	
	@Test
	public final void deletePromotion(){
		
		PromotionPK promotionPK= new PromotionPK("M2DOSI","10-2014");
		promotionService.deletePromotion(promotionPK);
		List<Promotion> listePromos = (List<Promotion>) promotionService.getPromotionALL();
		Assert.assertEquals(18, listePromos.size());
}
	
	@Test
	public final void getPromotionAll(){
		final Iterable<Promotion> promotions = promotionService.getPromotionALL();
		Assert.assertNotNull(promotions);
	}
	
	@Test
	public final void addPromotion(){
		try {
	//final Integer noEnseignant=1;
	//final String code="M2DOSI";
    Promotion promotion=new Promotion();
	PromotionPK promotionPK= new PromotionPK("M2DOSI","10-2014"); 
	promotion.setPromotionPK(promotionPK);
	//promotion.setNoEnseignant(enseignantService.getEnseignant(noEnseignant));
	promotion.setSiglePromotion("MDOSI");
	promotion.setProcessusStage("dosi");
	promotion.setDateRentree(new java.util.Date(13,5,4));
	promotion.setDateReponseLalp(new java.util.Date(13,5,4));
	promotion.setDateReponseLp(new java.util.Date(13,5,4));
	promotion.setLieuRentree("LC117B");
	promotion.setNbMaxEtudiant((short) 24);
	//promotion.setFormation(formationService.getFormation(code));
	promotion.setCommentaire("commentaire");
	//promotion.setEtudiantCollection(etudiantService.getEtudiantByPromotion(promotionPK));
	
	promotionService.addPromotion(promotion);
	List<Promotion> listePromos = (List<Promotion>) promotionService.getPromotionALL();
	Assert.assertEquals(19, listePromos.size());
		//Assert.fail();
	} catch (final SPIException ex) {
		Assert.assertEquals("la promotion que vous souhaitez ajouter exsite déja ", ex.getMessage());
	}
	}
	
	@Test
	public final void updatePromotion(){
		try {
			//final Integer noEnseignant=1;
			//final String code="M2DOSI";
		    Promotion promotion=new Promotion();
			PromotionPK promotionPK= new PromotionPK("M2DOSI","10-2014"); 
			promotion.setPromotionPK(promotionPK);
			//promotion.setNoEnseignant(enseignantService.getEnseignant(noEnseignant));
			promotion.setSiglePromotion("MDOSI");
			promotion.setProcessusStage("dosi");
			promotion.setDateRentree(new java.util.Date(13,5,4));
			promotion.setDateReponseLalp(new java.util.Date(13,5,4));
			promotion.setDateReponseLp(new java.util.Date(13,5,4));
			promotion.setLieuRentree("LC117B");
			promotion.setNbMaxEtudiant((short) 24);
			//promotion.setFormation(formationService.getFormation(code));
			promotion.setCommentaire("soukaina");
			//promotion.setEtudiantCollection(etudiantService.getEtudiantByPromotion(promotionPK));
			
			promotionService.update(promotion);
			List<Promotion> listePromos = (List<Promotion>) promotionService.getPromotionALL();
			Assert.assertEquals(19, listePromos.size());
				//Assert.fail();
			} catch (final SPIException ex) {
				Assert.assertEquals("la promotion que vous souhaitez modifier exsite déja ", ex.getMessage());
			}
	}
}
