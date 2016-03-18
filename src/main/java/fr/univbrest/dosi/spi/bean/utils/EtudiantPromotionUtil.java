package fr.univbrest.dosi.spi.bean.utils;

import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.bean.Promotion;

public class EtudiantPromotionUtil {

	private Etudiant etudiant;
	private Promotion promotion;
	public EtudiantPromotionUtil() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EtudiantPromotionUtil(Etudiant etudiant, Promotion promotion) {
		super();
		this.etudiant = etudiant;
		this.promotion = promotion;
	}
	public Etudiant getEtudiant() {
		return etudiant;
	}
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	public Promotion getPromotion() {
		return promotion;
	}
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
	
	
}
