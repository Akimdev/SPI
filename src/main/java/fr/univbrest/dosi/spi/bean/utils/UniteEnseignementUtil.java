package fr.univbrest.dosi.spi.bean.utils;

import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.UniteEnseignement;

public class UniteEnseignementUtil {

	private UniteEnseignement uniteEnseignement;
	private Enseignant enseignant;

	public UniteEnseignement getUniteEnseignement() {
		return uniteEnseignement;
	}

	public void setUniteEnseignement(UniteEnseignement uniteEnseignement) {
		this.uniteEnseignement = uniteEnseignement;
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public UniteEnseignementUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UniteEnseignementUtil(UniteEnseignement uniteEnseignement,
			Enseignant enseignant) {
		super();
		this.uniteEnseignement = uniteEnseignement;
		this.enseignant = enseignant;
	}

}
