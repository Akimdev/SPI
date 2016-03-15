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
public class ReponseQuestionPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8011859230601666672L;
	@Basic(optional = false)
    @NotNull
    @Column(name = "ID_REPONSE_EVALUATION")
    private long idReponseEvaluation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_QUESTION_EVALUATION")
    private long idQuestionEvaluation;

    public ReponseQuestionPK() {
    }

    public ReponseQuestionPK(long idReponseEvaluation, long idQuestionEvaluation) {
        this.idReponseEvaluation = idReponseEvaluation;
        this.idQuestionEvaluation = idQuestionEvaluation;
    }

    public long getIdReponseEvaluation() {
        return idReponseEvaluation;
    }

    public void setIdReponseEvaluation(long idReponseEvaluation) {
        this.idReponseEvaluation = idReponseEvaluation;
    }

    public long getIdQuestionEvaluation() {
        return idQuestionEvaluation;
    }

    public void setIdQuestionEvaluation(long idQuestionEvaluation) {
        this.idQuestionEvaluation = idQuestionEvaluation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idReponseEvaluation;
        hash += (int) idQuestionEvaluation;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReponseQuestionPK)) {
            return false;
        }
        ReponseQuestionPK other = (ReponseQuestionPK) object;
        if (this.idReponseEvaluation != other.idReponseEvaluation) {
            return false;
        }
        if (this.idQuestionEvaluation != other.idQuestionEvaluation) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.ReponseQuestionPK[ idReponseEvaluation=" + idReponseEvaluation + ", idQuestionEvaluation=" + idQuestionEvaluation + " ]";
    }
    
}
