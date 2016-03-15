package fr.univbrest.dosi.spi.dao;


import java.util.List;



import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;



/**
 * @author DOSI
 *
 */
@RepositoryRestResource(collectionResourceRel = "etudiant", path = "etudiant")
public interface EtudiantRepository extends PagingAndSortingRepository<Etudiant, String> {

	
	/**
	 * soukaina
	 * @param promotionPK
	 * @return
	 */
	
	List<Etudiant> findByPromotion(@Param("codeFormation") String codeFormation, @Param("anneeUniversitaire") String anneeUniversitaire);


}
