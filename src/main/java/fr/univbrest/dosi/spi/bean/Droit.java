/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univbrest.dosi.spi.bean;

import java.io.Serializable;

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
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 *
 * @author DOSI
 */
@Entity
@Table(name = "DROIT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Droit.findAll", query = "SELECT d FROM Droit d"),
    @NamedQuery(name = "Droit.findByIdEvaluation", query = "SELECT d FROM Droit d WHERE d.droitPK.idEvaluation = :idEvaluation"),
    @NamedQuery(name = "Droit.findByNoEnseignant", query = "SELECT d FROM Droit d WHERE d.droitPK.noEnseignant = :noEnseignant"),
    @NamedQuery(name = "Droit.findByConsultation", query = "SELECT d FROM Droit d WHERE d.consultation = :consultation"),
    @NamedQuery(name = "Droit.findByDuplication", query = "SELECT d FROM Droit d WHERE d.duplication = :duplication")})
public class Droit implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DroitPK droitPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONSULTATION")
    private Character consultation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DUPLICATION")
    private Character duplication;
    @JsonIgnore
    @JoinColumn(name = "NO_ENSEIGNANT", referencedColumnName = "NO_ENSEIGNANT", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Enseignant enseignant;
    @JsonIgnore
    @JoinColumn(name = "ID_EVALUATION", referencedColumnName = "ID_EVALUATION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Evaluation evaluation;

    public Droit() {
    }

    public Droit(DroitPK droitPK) {
        this.droitPK = droitPK;
    }

    public Droit(DroitPK droitPK, Character consultation, Character duplication) {
        this.droitPK = droitPK;
        this.consultation = consultation;
        this.duplication = duplication;
    }

    public Droit(long idEvaluation, int noEnseignant) {
        this.droitPK = new DroitPK(idEvaluation, noEnseignant);
    }

    public DroitPK getDroitPK() {
        return droitPK;
    }

    public void setDroitPK(DroitPK droitPK) {
        this.droitPK = droitPK;
    }

    public Character getConsultation() {
        return consultation;
    }

    public void setConsultation(Character consultation) {
        this.consultation = consultation;
    }

    public Character getDuplication() {
        return duplication;
    }

    public void setDuplication(Character duplication) {
        this.duplication = duplication;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (droitPK != null ? droitPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Droit)) {
            return false;
        }
        Droit other = (Droit) object;
        if ((this.droitPK == null && other.droitPK != null) || (this.droitPK != null && !this.droitPK.equals(other.droitPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.Droit[ droitPK=" + droitPK + " ]";
    }
    
}
