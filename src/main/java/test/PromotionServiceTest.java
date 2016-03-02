/**
 * 
 */
package test;

import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.service.PromotionService;

/**
 * @author DOSI
 *
 */
public class PromotionServiceTest {

	private static PromotionService PromotionService= new PromotionService();
	private static Promotion promotion=new Promotion();
	
	public static PromotionService getPromotionService() {
		return PromotionService;
	}
	public static void setPromotionService(PromotionService promotionService) {
		PromotionService = promotionService;
	}
	public static Promotion getPromotion() {
		return promotion;
	}
	public static void setPromotion(Promotion promotion) {
		PromotionServiceTest.promotion = promotion;
	}
	
	
}
