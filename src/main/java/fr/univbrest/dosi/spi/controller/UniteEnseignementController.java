package fr.univbrest.dosi.spi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.service.UniteEnseignementService;

@RestController
public class UniteEnseignementController {

	@Autowired
	UniteEnseignementService ueServ;
	
	@RequestMapping(value="/nombreUEs", headers = "Accept=application/json")
	public int nombreUEs(){
		return ueServ.nombreUEs();
	}
}
