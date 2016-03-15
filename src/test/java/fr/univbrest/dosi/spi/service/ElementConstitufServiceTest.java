package fr.univbrest.dosi.spi.service;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import fr.univbrest.dosi.spi.Application;
import fr.univbrest.dosi.spi.bean.ElementConstitutif;
import fr.univbrest.dosi.spi.bean.ElementConstitutifPK;
import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Formation;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ElementConstitufServiceTest {
	
	@Autowired
	ElementConstitutifService elementConstitutifService;
	@Autowired
	EnseignantService ensServ;
	@Autowired
	FormationService formServ;
	
	@Test
	public void addEC() throws IOException{
		Enseignant enseignant = ensServ.getEnseignant(1);
		Formation formation = formServ.getFormation("M2DOSI");
		ElementConstitutifPK ecPK = new ElementConstitutifPK("M2DOSI","J2EE","BABA");
		ElementConstitutif ec = new ElementConstitutif(ecPK);
		ec.setDescription("descri");
		ec.setDesignation("desig");
		ec.setNoEnseignant(enseignant);
		elementConstitutifService.addElementConstitutif(ec);
		
		ElementConstitutif ecNew = elementConstitutifService.getElementConstitutif(
					new ElementConstitutifPK("M2DOSI", "J2EE", "BABA"));
		Assert.assertNotNull(ecNew);
		Assert.assertEquals("desig",ecNew.getDesignation());
	}
	@Test
	public void deleteEC(){
		elementConstitutifService.deleteElementConstitutif(new ElementConstitutifPK("M2DOSI", "J2EE", "BABA"));
		try{
			ElementConstitutif ecOld = elementConstitutifService.getElementConstitutif(
										new ElementConstitutifPK("M2DOSI", "J2EE", "BABA"));
			Assert.assertNull(ecOld);
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
	

