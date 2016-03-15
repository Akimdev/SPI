package fr.univbrest.dosi.spi.bean.utils;

import fr.univbrest.dosi.spi.bean.ElementConstitutif;
import fr.univbrest.dosi.spi.bean.Enseignant;

public class EcUtil {

	private Enseignant enseignant;
	private ElementConstitutif elementConstitutif;

	public EcUtil(Enseignant enseignant, ElementConstitutif elementConstitutif) {
		super();
		this.enseignant = enseignant;
		this.elementConstitutif = elementConstitutif;
	}

	public ElementConstitutif getElementConstitutif() {
		return elementConstitutif;
	}

	public void setElementConstitutif(ElementConstitutif elementConstitutif) {
		this.elementConstitutif = elementConstitutif;
	}

	public EcUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}
	
	
}
