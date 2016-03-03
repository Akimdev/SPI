/**
 * 
 */
package fr.univbrest.dosi.spi.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.univbrest.dosi.spi.Application;

/**
 * @author DOSI
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PromotionServiceTest {
	
	private PromotionService promotionService;
	
	private String codeFormation;
	private String anneeUniversitaire;

	/**
	 * 
	 */
	public PromotionServiceTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public final void getPromotion() {
		
		
		final Promotion promotion= promotionService.getPromotion("M2DOSI","2014-2015");
	}
	
	

}
