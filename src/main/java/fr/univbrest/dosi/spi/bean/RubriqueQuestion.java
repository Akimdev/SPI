/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univbrest.dosi.spi.bean;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 *
 * @author DOSI
 */
@Entity
@Table(name = "RUBRIQUE_QUESTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RubriqueQuestion.findAll", query = "SELECT r FROM RubriqueQuestion r"),
    @NamedQuery(name = "RubriqueQuestion.findByIdRubrique", query = "SELECT r FROM RubriqueQuestion r WHERE r.rubriqueQuestionPK.idRubrique = :idRubrique"),
    @NamedQuery(name = "RubriqueQuestion.findByIdQuestion", query = "SELECT r FROM RubriqueQuestion r WHERE r.rubriqueQuestionPK.idQuestion = :idQuestion"),
    @NamedQuery(name = "RubriqueQuestion.findByOrdre", query = "SELECT r FROM RubriqueQuestion r WHERE r.ordre = :ordre")})
public class RubriqueQuestion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RubriqueQuestionPK rubriqueQuestionPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDRE")
    private BigInteger ordre;
    @JsonIgnore
    @JoinColumn(name = "ID_QUESTION", referencedColumnName = "ID_QUESTION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Question question;
    @JsonIgnore
    @JoinColumn(name = "ID_RUBRIQUE", referencedColumnName = "ID_RUBRIQUE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rubrique rubrique;

    public RubriqueQuestion() {
    }

    public RubriqueQuestion(RubriqueQuestionPK rubriqueQuestionPK) {
        this.rubriqueQuestionPK = rubriqueQuestionPK;
    }

    public RubriqueQuestion(RubriqueQuestionPK rubriqueQuestionPK, BigInteger ordre) {
        this.rubriqueQuestionPK = rubriqueQuestionPK;
        this.ordre = ordre;
    }

    public RubriqueQuestion(long idRubrique, long idQuestion) {
        this.rubriqueQuestionPK = new RubriqueQuestionPK(idRubrique, idQuestion);
    }

    public RubriqueQuestionPK getRubriqueQuestionPK() {
        return rubriqueQuestionPK;
    }

    public void setRubriqueQuestionPK(RubriqueQuestionPK rubriqueQuestionPK) {
        this.rubriqueQuestionPK = rubriqueQuestionPK;
    }

    public BigInteger getOrdre() {
        return ordre;
    }

    public void setOrdre(BigInteger ordre) {
        this.ordre = ordre;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Rubrique getRubrique() {
        return rubrique;
    }

    public void setRubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rubriqueQuestionPK != null ? rubriqueQuestionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RubriqueQuestion)) {
            return false;
        }
        RubriqueQuestion other = (RubriqueQuestion) object;
        if ((this.rubriqueQuestionPK == null && other.rubriqueQuestionPK != null) || (this.rubriqueQuestionPK != null && !this.rubriqueQuestionPK.equals(other.rubriqueQuestionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.RubriqueQuestion[ rubriqueQuestionPK=" + rubriqueQuestionPK + " ]";
    }
    
}
