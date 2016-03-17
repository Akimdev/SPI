package fr.univbrest.dosi.spi.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Formation;
import fr.univbrest.dosi.spi.bean.UniteEnseignement;
import fr.univbrest.dosi.spi.bean.UniteEnseignementPK;

/**
 * 
 * @author Othman
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UniteEnseignementServiceTest {

	@Autowired
	UniteEnseignementService ueServ;
	
	@Autowired
	EnseignantService ensServ;
	
	@Autowired
	FormationService formServ;
	
	@Test
	public void addUETest(){
		Enseignant enseignant = ensServ.getEnseignant(2);
		UniteEnseignement ue = new UniteEnseignement();
		UniteEnseignementPK uePK = new UniteEnseignementPK("M2DOSI","PVP");
		ue.setUniteEnseignementPK(uePK);
		ue.setNoEnseignant(enseignant);
		Formation formation = formServ.getFormation(uePK.getCodeFormation());
		ue.setFormation(formation);
		ue.setDesignation("Preparation a la vie Prof");
		ue.setSemestre("10");
		ueServ.addUE(ue);
		List<UniteEnseignement> ueList = ueServ.getAllUEs();
		Assert.assertNotNull(ueList);
		Assert.assertEquals(9, ueList.size());
 	}
	
	@Test
	public void updateUETest(){
		UniteEnseignementPK uePK = new UniteEnseignementPK("M2DOSI","CO");
		UniteEnseignement ue = ueServ.getUE(uePK);
		ue.setSemestre("9");
		ueServ.updateUE(ue);
		UniteEnseignement ueBDD = ueServ.getUE(uePK);
		Assert.assertNotNull(ueBDD);
		Assert.assertEquals("9  ", ueBDD.getSemestre());
	}
	
	@Test
	public void deleteUETest(){
		UniteEnseignementPK uePK = new UniteEnseignementPK("M2DOSI","PVP");
		ueServ.deleteUE(uePK);
		List<UniteEnseignement> ueList = ueServ.getAllUEs();
		Assert.assertNotNull(ueList);
		Assert.assertEquals(8, ueList.size());
	}
	
	@Test
	public void getAllUEsTest(){
		List<UniteEnseignement> ueList = ueServ.getAllUEs();
		Assert.assertNotNull(ueList);
		Assert.assertEquals(8, ueList.size());
	}
}
