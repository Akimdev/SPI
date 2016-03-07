package fr.univbrest.dosi.spi.service;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;

/**
 * @author DOSI
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PromotionServiceTest {

	public PromotionService getPromotionService() {
		return promotionService;
	}

	public void setPromotionService(PromotionService promotionService) {
		this.promotionService = promotionService;
	}

	public String getSiglePromotion() {
		return siglePromotion;
	}

	public void setSiglePromotion(String siglePromotion) {
		this.siglePromotion = siglePromotion;
	}

	@Autowired
	private PromotionService promotionService;

	private String siglePromotion = "DOSI5";

	public PromotionServiceTest() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public final void getPromotion() {

		PromotionPK promotionPK = new PromotionPK("M2DOSI", "2014-2015");
		final Promotion promotion = promotionService.getPromotion(promotionPK);
		Assert.assertNotNull(promotion);
		Assert.assertEquals(this.getSiglePromotion(), promotion.getSiglePromotion());

	}
}
