package fr.univbrest.dosi.spi.bean;

import java.io.Serializable;

import javax.persistence.Entity;


public class ProEns{
	
	private Enseignant enseignant;
	private String test;
	public  Promotion promotion;
	private Formation formation;
	
	
	public ProEns(Enseignant enseignant, String test, Promotion promotion, Formation formation) {
		super();
		this.enseignant = enseignant;
		this.test = test;
		this.promotion = promotion;
		this.formation = formation;
	}
	
	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public ProEns() {
		
	}

}
