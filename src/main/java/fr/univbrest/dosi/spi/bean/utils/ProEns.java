package fr.univbrest.dosi.spi.bean.utils;

import java.io.Serializable;

import javax.persistence.Entity;

import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Formation;
import fr.univbrest.dosi.spi.bean.Promotion;

public class ProEns {

	private Enseignant enseignant;
	public Promotion promotion;
	private Formation formation;

	public ProEns(Enseignant enseignant, String test, Promotion promotion, Formation formation) {
		super();
		this.enseignant = enseignant;

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

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public ProEns() {

	}
}