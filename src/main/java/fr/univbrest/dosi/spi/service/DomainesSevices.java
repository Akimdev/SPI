package fr.univbrest.dosi.spi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.CgRefCodes;
import fr.univbrest.dosi.spi.dao.DomainesRepository;

/**
 * @author LAKRAA Classe permettant de récuperer les valeurs des domaines
 *         dynamiques à partir de la table CG-REF-CODES
 */

@Service
public class DomainesSevices {
	@Autowired
	DomainesRepository domainesRepository;

	/**
	 * @author LAKRAA cette méthode permet de recuperer la liste des valeur du
	 *         domaine : DIPLOME
	 */

	public final List<String> getDomainDiplome(final String rvDomain) {
		return domainesRepository.findByRvDomain(rvDomain);
	}
	
	public final CgRefCodes getDomainById(final BigDecimal idCgrc) {
		return domainesRepository.findByIdCgrc(idCgrc);
	}

}
