package fr.univbrest.dosi.spi.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestBody;

import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;

/**
 * @author Kenza ABOUAKIL
 *
 */
@RepositoryRestResource(collectionResourceRel = "promotion", path = "promotion")
public interface PromotionRepository extends PagingAndSortingRepository<Promotion, PromotionPK> {

	@Override
	List<Promotion> findAll();

	public List<Promotion> findByCodeFormation(@Param("codeFormation") String codeFormation);

	/**
	 *
	 * @param noEnseignant
	 *            l'id de l'enseignant
	 * @return liste des promotions
	 */
	List<Promotion> findByNoEnseignant(@Param("noEnseignant") Integer noEnseignant);

	@Override
	Promotion save(@RequestBody Promotion promotion);

}
