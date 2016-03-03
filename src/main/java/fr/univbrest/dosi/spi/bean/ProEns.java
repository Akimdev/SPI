package fr.univbrest.dosi.spi.bean;

import java.io.Serializable;

import javax.persistence.Entity;


public class ProEns implements Serializable{
	
	private static final long serialVersionUID = 100L;
	
	public String nom;
	public String prenom;
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ProEns(String nom, String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
	}
	
	public ProEns() {
		
	}
	
	@Override
	public String toString() {
		return "ProEns [nom=" + nom + ", prenom=" + prenom + ", getNom()="
				+ getNom() + ", getPrenom()=" + getPrenom() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
		

}
