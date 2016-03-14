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
public class RubriqueQuestionPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2192321867252921815L;
	
	@Basic(optional = false)
    @NotNull
    @Column(name = "ID_RUBRIQUE")
    private long idRubrique;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_QUESTION")
    private long idQuestion;

    public RubriqueQuestionPK() {
    }

    public RubriqueQuestionPK(long idRubrique, long idQuestion) {
        this.idRubrique = idRubrique;
        this.idQuestion = idQuestion;
    }

    public long getIdRubrique() {
        return idRubrique;
    }

    public void setIdRubrique(long idRubrique) {
        this.idRubrique = idRubrique;
    }

    public long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(long idQuestion) {
        this.idQuestion = idQuestion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idRubrique;
        hash += (int) idQuestion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RubriqueQuestionPK)) {
            return false;
        }
        RubriqueQuestionPK other = (RubriqueQuestionPK) object;
        if (this.idRubrique != other.idRubrique) {
            return false;
        }
        if (this.idQuestion != other.idQuestion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.RubriqueQuestionPK[ idRubrique=" + idRubrique + ", idQuestion=" + idQuestion + " ]";
    }
    
}
