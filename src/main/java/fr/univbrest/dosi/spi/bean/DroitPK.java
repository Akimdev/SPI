/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univbrest.dosi.spi.bean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author DOSI
 */
@Embeddable
public class DroitPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2484597371886049955L;
	@Basic(optional = false)
    @NotNull
    @Column(name = "ID_EVALUATION")
    private long idEvaluation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NO_ENSEIGNANT")
    private int noEnseignant;

    public DroitPK() {
    }

    public DroitPK(long idEvaluation, int noEnseignant) {
        this.idEvaluation = idEvaluation;
        this.noEnseignant = noEnseignant;
    }

    public long getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(long idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public int getNoEnseignant() {
        return noEnseignant;
    }

    public void setNoEnseignant(int noEnseignant) {
        this.noEnseignant = noEnseignant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEvaluation;
        hash += (int) noEnseignant;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DroitPK)) {
            return false;
        }
        DroitPK other = (DroitPK) object;
        if (this.idEvaluation != other.idEvaluation) {
            return false;
        }
        if (this.noEnseignant != other.noEnseignant) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.DroitPK[ idEvaluation=" + idEvaluation + ", noEnseignant=" + noEnseignant + " ]";
    }
    
}
