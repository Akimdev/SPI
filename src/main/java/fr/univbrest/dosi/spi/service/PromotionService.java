package fr.univbrest.dosi.spi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;
import fr.univbrest.dosi.spi.dao.PromotionRepository;
import fr.univbrest.dosi.spi.exception.SPIException;

@Service
public class PromotionService {

	@Autowired
	private PromotionRepository promotionRepository;

	public final void addPromotion(final Promotion promotion) {
		
		if(promotionRepository.exists(promotion.getPromotionPK())){
			throw new SPIException("cette Promotion que vous souhaitez ajouter exsite déja :D");
		} 
		promotionRepository.save(promotion);
	}
	
	public final Promotion update(final Promotion promotion){
		if (promotionRepository.exists(promotion.getPromotionPK())) {
			return promotionRepository.save(promotion);
		} else {
			throw new SPIException("la promotion que vous souhaitez modifier n'exsite pas ");
		}
	}

	public final void deletePromotion(final PromotionPK promotionPK) {
		if (promotionRepository.exists(promotionPK) )
			promotionRepository.delete(promotionPK);
		else
			throw new SPIException("promotion non trouvée");
	}

	public final Boolean existPromotion(final PromotionPK promotionPK) {
		return promotionRepository.exists(promotionPK);
	}

	public final Promotion getPromotion(final PromotionPK promotionPK) {
		return promotionRepository.findOne(promotionPK);
	}

	public final List<Promotion> getPromotionByEnseignant(final Integer noEnseignant) {
		return promotionRepository.findByNoEnseignant(noEnseignant);
	}

	/**
	 * 
	 * @return all promotion
	 * @author ASSABBAR
	 */

	public final Iterable<Promotion> getPromotionALL() {
		return promotionRepository.findAll();
	}
}
