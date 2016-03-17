/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univbrest.dosi.spi.bean;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 *
 * @author DOSI
 */
 
@Entity
@Table(name = "REPONSE_QUESTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReponseQuestion.findAll", query = "SELECT r FROM ReponseQuestion r"),
    @NamedQuery(name = "ReponseQuestion.findByIdReponseEvaluation", query = "SELECT r FROM ReponseQuestion r WHERE r.reponseQuestionPK.idReponseEvaluation = :idReponseEvaluation"),
    @NamedQuery(name = "ReponseQuestion.findByIdQuestionEvaluation", query = "SELECT r FROM ReponseQuestion r WHERE r.reponseQuestionPK.idQuestionEvaluation = :idQuestionEvaluation"),
    @NamedQuery(name = "ReponseQuestion.findByPositionnement", query = "SELECT r FROM ReponseQuestion r WHERE r.positionnement = :positionnement")})
public class ReponseQuestion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReponseQuestionPK reponseQuestionPK;
    @Column(name = "POSITIONNEMENT")
    private BigInteger positionnement;
    @JsonManagedReference
    @JoinColumn(name = "ID_QUESTION_EVALUATION", referencedColumnName = "ID_QUESTION_EVALUATION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private QuestionEvaluation questionEvaluation;
    @JsonIgnore
    @JoinColumn(name = "ID_REPONSE_EVALUATION", referencedColumnName = "ID_REPONSE_EVALUATION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ReponseEvaluation reponseEvaluation;

    public ReponseQuestion() {
    }

    public ReponseQuestion(ReponseQuestionPK reponseQuestionPK) {
        this.reponseQuestionPK = reponseQuestionPK;
    }

    public ReponseQuestion(long idReponseEvaluation, long idQuestionEvaluation) {
        this.reponseQuestionPK = new ReponseQuestionPK(idReponseEvaluation, idQuestionEvaluation);
    }

    public ReponseQuestionPK getReponseQuestionPK() {
        return reponseQuestionPK;
    }

    public void setReponseQuestionPK(ReponseQuestionPK reponseQuestionPK) {
        this.reponseQuestionPK = reponseQuestionPK;
    }

    public BigInteger getPositionnement() {
        return positionnement;
    }

    public void setPositionnement(BigInteger positionnement) {
        this.positionnement = positionnement;
    }

    public QuestionEvaluation getQuestionEvaluation() {
        return questionEvaluation;
    }

    public void setQuestionEvaluation(QuestionEvaluation questionEvaluation) {
        this.questionEvaluation = questionEvaluation;
    }

    public ReponseEvaluation getReponseEvaluation() {
        return reponseEvaluation;
    }

    public void setReponseEvaluation(ReponseEvaluation reponseEvaluation) {
        this.reponseEvaluation = reponseEvaluation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reponseQuestionPK != null ? reponseQuestionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReponseQuestion)) {
            return false;
        }
        ReponseQuestion other = (ReponseQuestion) object;
        if ((this.reponseQuestionPK == null && other.reponseQuestionPK != null) || (this.reponseQuestionPK != null && !this.reponseQuestionPK.equals(other.reponseQuestionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.ReponseQuestion[ reponseQuestionPK=" + reponseQuestionPK + " ]";
    }  
}