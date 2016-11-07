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
@Table(name = "RUBRIQUE_EVALUATION", catalog = "", schema = "SPI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RubriqueEvaluation.findAll", query = "SELECT r FROM RubriqueEvaluation r"),
    @NamedQuery(name = "RubriqueEvaluation.findByIdRubriqueEvaluation", query = "SELECT r FROM RubriqueEvaluation r WHERE r.idRubriqueEvaluation = :idRubriqueEvaluation"),
    @NamedQuery(name = "RubriqueEvaluation.findByOrdre", query = "SELECT r FROM RubriqueEvaluation r WHERE r.ordre = :ordre"),
    @NamedQuery(name = "RubriqueEvaluation.findByIdEvaluation", query = "SELECT r FROM RubriqueEvaluation r WHERE r.idEvaluation = :idEvaluation"),
    @NamedQuery(name = "RubriqueEvaluation.findByDesignation", query = "SELECT r FROM RubriqueEvaluation r WHERE r.designation = :designation")})
public class RubriqueEvaluation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="REV_SEQ",strategy=GenerationType.AUTO)
	@SequenceGenerator(name="REV_SEQ",sequenceName="REV_SEQ", allocationSize=1)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_RUBRIQUE_EVALUATION")
    private Long idRubriqueEvaluation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDRE")
    private short ordre;
    @Size(max = 64)
    @Column(name = "DESIGNATION")
    private String designation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRubriqueEvaluation")
    private Collection<QuestionEvaluation> questionEvaluationCollection;
    @JsonIgnore
    @JoinColumn(name = "ID_EVALUATION", referencedColumnName = "ID_EVALUATION")
    @ManyToOne(optional = false)
    private Evaluation idEvaluation;
    @JoinColumn(name = "ID_RUBRIQUE", referencedColumnName = "ID_RUBRIQUE")
    @ManyToOne
    private Rubrique idRubrique;

    public RubriqueEvaluation() {
    }

    public RubriqueEvaluation(Long idRubriqueEvaluation) {
        this.idRubriqueEvaluation = idRubriqueEvaluation;
    }

    public RubriqueEvaluation(Long idRubriqueEvaluation, short ordre) {
        this.idRubriqueEvaluation = idRubriqueEvaluation;
        this.ordre = ordre;
    }

    public Long getIdRubriqueEvaluation() {
        return idRubriqueEvaluation;
    }

    public void setIdRubriqueEvaluation(Long idRubriqueEvaluation) {
        this.idRubriqueEvaluation = idRubriqueEvaluation;
    }

    public short getOrdre() {
        return ordre;
    }

    public void setOrdre(short ordre) {
        this.ordre = ordre;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @XmlTransient
    public Collection<QuestionEvaluation> getQuestionEvaluationCollection() {
        return questionEvaluationCollection;
    }

    public void setQuestionEvaluationCollection(Collection<QuestionEvaluation> questionEvaluationCollection) {
        this.questionEvaluationCollection = questionEvaluationCollection;
    }

    public Evaluation getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(Evaluation idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public Rubrique getIdRubrique() {
        return idRubrique;
    }

    public void setIdRubrique(Rubrique idRubrique) {
        this.idRubrique = idRubrique;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRubriqueEvaluation != null ? idRubriqueEvaluation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RubriqueEvaluation)) {
            return false;
        }
        RubriqueEvaluation other = (RubriqueEvaluation) object;
        if ((this.idRubriqueEvaluation == null && other.idRubriqueEvaluation != null) || (this.idRubriqueEvaluation != null && !this.idRubriqueEvaluation.equals(other.idRubriqueEvaluation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.RubriqueEvaluation[ idRubriqueEvaluation=" + idRubriqueEvaluation + " ]";
    }
    
}
