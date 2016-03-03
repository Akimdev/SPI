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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 *
 * @author DOSI
 */
@Entity
@Table(name = "QUALIFICATIF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Qualificatif.findAll", query = "SELECT q FROM Qualificatif q"),
    @NamedQuery(name = "Qualificatif.findByIdQualificatif", query = "SELECT q FROM Qualificatif q WHERE q.idQualificatif = :idQualificatif"),
    @NamedQuery(name = "Qualificatif.findByMaximal", query = "SELECT q FROM Qualificatif q WHERE q.maximal = :maximal"),
    @NamedQuery(name = "Qualificatif.findByMinimal", query = "SELECT q FROM Qualificatif q WHERE q.minimal = :minimal")})
public class Qualificatif implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_QUALIFICATIF")
    private Long idQualificatif;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "MAXIMAL")
    private String maximal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "MINIMAL")
    private String minimal;
    @JsonBackReference(value="question-Qualificatif")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idQualificatif")
    private Collection<Question> questionCollection;
    @JsonBackReference(value="questionEvaluation-Qualificatif")
    @OneToMany(mappedBy = "idQualificatif")
    private Collection<QuestionEvaluation> questionEvaluationCollection;

    public Qualificatif() {
    }

    public Qualificatif(Long idQualificatif) {
        this.idQualificatif = idQualificatif;
    }

    public Qualificatif(Long idQualificatif, String maximal, String minimal) {
        this.idQualificatif = idQualificatif;
        this.maximal = maximal;
        this.minimal = minimal;
    }

    public Long getIdQualificatif() {
        return idQualificatif;
    }

    public void setIdQualificatif(Long idQualificatif) {
        this.idQualificatif = idQualificatif;
    }

    public String getMaximal() {
        return maximal;
    }

    public void setMaximal(String maximal) {
        this.maximal = maximal;
    }

    public String getMinimal() {
        return minimal;
    }

    public void setMinimal(String minimal) {
        this.minimal = minimal;
    }

    @XmlTransient
    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    @XmlTransient
    public Collection<QuestionEvaluation> getQuestionEvaluationCollection() {
        return questionEvaluationCollection;
    }

    public void setQuestionEvaluationCollection(Collection<QuestionEvaluation> questionEvaluationCollection) {
        this.questionEvaluationCollection = questionEvaluationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idQualificatif != null ? idQualificatif.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Qualificatif)) {
            return false;
        }
        Qualificatif other = (Qualificatif) object;
        if ((this.idQualificatif == null && other.idQualificatif != null) || (this.idQualificatif != null && !this.idQualificatif.equals(other.idQualificatif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.Qualificatif[ idQualificatif=" + idQualificatif + " ]";
    }
    
}
