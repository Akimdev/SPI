
package fr.univbrest.dosi.spi.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Iterables;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;

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
	
 
	@Test
	public final void getPromotion(){
		
		PromotionPK promotionPK= new PromotionPK("M2DOSI","2014-2015");
		final Promotion promotion= promotionService.getPromotion(promotionPK);
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
}