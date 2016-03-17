package fr.univbrest.dosi.spi.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.univbrest.dosi.spi.bean.CgRefCodes;
import fr.univbrest.dosi.spi.dao.DomainesRepository;

/**
 * @author LAKRAA Classe permettant de récuperer les valeurs des domaines dynamiques à partir de la table CG-REF-CODES
 */

@Service
public class DomainesSevices {
	
	@Autowired
	DomainesRepository domainesRepository;

	public final CgRefCodes getDomainById(final BigDecimal idCgrc) {
		return domainesRepository.findByIdCgrc(idCgrc);
	}

	/**
	 * @author LAKRAA cette méthode permet de recuperer la liste des valeur du domaine : DIPLOME
	 */

	public final List<CgRefCodes> getDomainByRvDomain(final String rvDomain) {
		return domainesRepository.findByRvDomain(rvDomain);
	}
	
	/**
	 * @author Youssef cette méthode permet de récuperer le pays correspondant à une abrévation
	 */
	public CgRefCodes getPaysDomain(final String lowValue){
		return domainesRepository.findPaysDomain(lowValue);
	}
	
	/**
	 * @author Baqloul 
	 */
	public final CgRefCodes getRvMainingByRvAbbreviation(final String rvAbbreviation) {
		return domainesRepository.findByRvAbbreviation(rvAbbreviation);
	}
}