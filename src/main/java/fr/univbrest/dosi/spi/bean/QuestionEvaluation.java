/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univbrest.dosi.spi.bean;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author DOSI
 */
 
@Entity
@Table(name = "QUESTION_EVALUATION", catalog = "", schema = "DOSI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuestionEvaluation.findAll", query = "SELECT q FROM QuestionEvaluation q"),
    @NamedQuery(name = "QuestionEvaluation.findByIdQuestionEvaluation", query = "SELECT q FROM QuestionEvaluation q WHERE q.idQuestionEvaluation = :idQuestionEvaluation"),
    @NamedQuery(name = "QuestionEvaluation.findByOrdre", query = "SELECT q FROM QuestionEvaluation q WHERE q.ordre = :ordre"),
    @NamedQuery(name = "QuestionEvaluation.findByIntitule", query = "SELECT q FROM QuestionEvaluation q WHERE q.intitule = :intitule")})
public class QuestionEvaluation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="QEV_SEQ",strategy=GenerationType.AUTO)
	@SequenceGenerator(name="QEV_SEQ",sequenceName="QEV_SEQ", allocationSize=1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_QUESTION_EVALUATION")
    private Long idQuestionEvaluation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDRE")
    private short ordre;
    @Size(max = 64)
    @Column(name = "INTITULE")
    private String intitule;
    @JsonIgnore
    @JoinColumn(name = "ID_QUALIFICATIF", referencedColumnName = "ID_QUALIFICATIF")
    @ManyToOne
    private Qualificatif idQualificatif;
    @JoinColumn(name = "ID_QUESTION", referencedColumnName = "ID_QUESTION")
    @ManyToOne
    private Question idQuestion;
    @JsonIgnore
    @JoinColumn(name = "ID_RUBRIQUE_EVALUATION", referencedColumnName = "ID_RUBRIQUE_EVALUATION", nullable = false)
    @ManyToOne(optional = false)
    private RubriqueEvaluation idRubriqueEvaluation;
    @JsonIgnore
    //@JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionEvaluation")
    private Collection<ReponseQuestion> reponseQuestionCollection;

    public QuestionEvaluation() {
    }

    public QuestionEvaluation(Long idQuestionEvaluation) {
        this.idQuestionEvaluation = idQuestionEvaluation;
    }

    public QuestionEvaluation(Long idQuestionEvaluation, short ordre) {
        this.idQuestionEvaluation = idQuestionEvaluation;
        this.ordre = ordre;
    }

    public Long getIdQuestionEvaluation() {
        return idQuestionEvaluation;
    }

    public void setIdQuestionEvaluation(Long idQuestionEvaluation) {
        this.idQuestionEvaluation = idQuestionEvaluation;
    }

    public short getOrdre() {
        return ordre;
    }

    public void setOrdre(short ordre) {
        this.ordre = ordre;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Qualificatif getIdQualificatif() {
        return idQualificatif;
    }

    public void setIdQualificatif(Qualificatif idQualificatif) {
        this.idQualificatif = idQualificatif;
    }

    public Question getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Question idQuestion) {
        this.idQuestion = idQuestion;
    }

    public RubriqueEvaluation getIdRubriqueEvaluation() {
        return idRubriqueEvaluation;
    }

    public void setIdRubriqueEvaluation(RubriqueEvaluation idRubriqueEvaluation) {
        this.idRubriqueEvaluation = idRubriqueEvaluation;
    }

    @XmlTransient
    public Collection<ReponseQuestion> getReponseQuestionCollection() {
        return reponseQuestionCollection;
    }

    public void setReponseQuestionCollection(Collection<ReponseQuestion> reponseQuestionCollection) {
        this.reponseQuestionCollection = reponseQuestionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idQuestionEvaluation != null ? idQuestionEvaluation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionEvaluation)) {
            return false;
        }
        QuestionEvaluation other = (QuestionEvaluation) object;
        if ((this.idQuestionEvaluation == null && other.idQuestionEvaluation != null) || (this.idQuestionEvaluation != null && !this.idQuestionEvaluation.equals(other.idQuestionEvaluation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.QuestionEvaluation[ idQuestionEvaluation=" + idQuestionEvaluation + " ]";
    } 
}