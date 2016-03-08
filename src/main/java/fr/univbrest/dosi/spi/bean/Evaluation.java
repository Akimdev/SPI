/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univbrest.dosi.spi.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;



/**
 *
 * @author DOSI
 */

@Entity
@Table(name = "EVALUATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evaluation.findAll", query = "SELECT e FROM Evaluation e"),
    @NamedQuery(name = "Evaluation.findByIdEvaluation", query = "SELECT e FROM Evaluation e WHERE e.idEvaluation = :idEvaluation"),
    @NamedQuery(name = "Evaluation.findByNoEvaluation", query = "SELECT e FROM Evaluation e WHERE e.noEvaluation = :noEvaluation"),
    @NamedQuery(name = "Evaluation.findByDesignation", query = "SELECT e FROM Evaluation e WHERE e.designation = :designation"),
    @NamedQuery(name = "Evaluation.findByEtat", query = "SELECT e FROM Evaluation e WHERE e.etat = :etat"),
    @NamedQuery(name = "Evaluation.findByPeriode", query = "SELECT e FROM Evaluation e WHERE e.periode = :periode"),
    @NamedQuery(name = "Evaluation.findByDebutReponse", query = "SELECT e FROM Evaluation e WHERE e.debutReponse = :debutReponse"),
    @NamedQuery(name = "Evaluation.findByFinReponse", query = "SELECT e FROM Evaluation e WHERE e.finReponse = :finReponse")})
public class Evaluation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_EVALUATION")
    private Long idEvaluation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NO_EVALUATION")
    private short noEvaluation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "DESIGNATION")
    private String designation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "ETAT")
    private String etat;
    @Size(max = 64)
    @Column(name = "PERIODE")
    private String periode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DEBUT_REPONSE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date debutReponse;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FIN_REPONSE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finReponse;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluation")
    private Collection<Droit> droitCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvaluation")
    private Collection<ReponseEvaluation> reponseEvaluationCollection;
    @JoinColumns({
        @JoinColumn(name = "CODE_FORMATION", referencedColumnName = "CODE_FORMATION", insertable = false, updatable = false),
        @JoinColumn(name = "CODE_UE", referencedColumnName = "CODE_UE", insertable = false, updatable = false),
        @JoinColumn(name = "CODE_EC", referencedColumnName = "CODE_EC", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private ElementConstitutif elementConstitutif;
    @JoinColumn(name = "NO_ENSEIGNANT", referencedColumnName = "NO_ENSEIGNANT")
    @ManyToOne(optional = false)
    private Enseignant noEnseignant;
    @JoinColumns({
        @JoinColumn(name = "ANNEE_UNIVERSITAIRE", referencedColumnName = "ANNEE_UNIVERSITAIRE", insertable = false, updatable = false),
        @JoinColumn(name = "CODE_FORMATION", referencedColumnName = "CODE_FORMATION", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Promotion promotion;
    @JoinColumns({
        @JoinColumn(name = "CODE_FORMATION", referencedColumnName = "CODE_FORMATION", insertable = false, updatable = false),
        @JoinColumn(name = "CODE_UE", referencedColumnName = "CODE_UE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private UniteEnseignement uniteEnseignement;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvaluation")
    private Collection<RubriqueEvaluation> rubriqueEvaluationCollection;

    public Evaluation() {
    }

    public Evaluation(Long idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public Evaluation(Long idEvaluation, short noEvaluation, String designation, String etat, Date debutReponse, Date finReponse) {
        this.idEvaluation = idEvaluation;
        this.noEvaluation = noEvaluation;
        this.designation = designation;
        this.etat = etat;
        this.debutReponse = debutReponse;
        this.finReponse = finReponse;
    }

    public Long getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(Long idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public short getNoEvaluation() {
        return noEvaluation;
    }

    public void setNoEvaluation(short noEvaluation) {
        this.noEvaluation = noEvaluation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public Date getDebutReponse() {
        return debutReponse;
    }

    public void setDebutReponse(Date debutReponse) {
        this.debutReponse = debutReponse;
    }

    public Date getFinReponse() {
        return finReponse;
    }

    public void setFinReponse(Date finReponse) {
        this.finReponse = finReponse;
    }

    @XmlTransient
    public Collection<Droit> getDroitCollection() {
        return droitCollection;
    }

    public void setDroitCollection(Collection<Droit> droitCollection) {
        this.droitCollection = droitCollection;
    }

    @XmlTransient
    public Collection<ReponseEvaluation> getReponseEvaluationCollection() {
        return reponseEvaluationCollection;
    }

    public void setReponseEvaluationCollection(Collection<ReponseEvaluation> reponseEvaluationCollection) {
        this.reponseEvaluationCollection = reponseEvaluationCollection;
    }

    public ElementConstitutif getElementConstitutif() {
        return elementConstitutif;
    }

    public void setElementConstitutif(ElementConstitutif elementConstitutif) {
        this.elementConstitutif = elementConstitutif;
    }

    public Enseignant getNoEnseignant() {
        return noEnseignant;
    }

    public void setNoEnseignant(Enseignant noEnseignant) {
        this.noEnseignant = noEnseignant;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public UniteEnseignement getUniteEnseignement() {
        return uniteEnseignement;
    }

    public void setUniteEnseignement(UniteEnseignement uniteEnseignement) {
        this.uniteEnseignement = uniteEnseignement;
    }

    @XmlTransient
    public Collection<RubriqueEvaluation> getRubriqueEvaluationCollection() {
        return rubriqueEvaluationCollection;
    }

    public void setRubriqueEvaluationCollection(Collection<RubriqueEvaluation> rubriqueEvaluationCollection) {
        this.rubriqueEvaluationCollection = rubriqueEvaluationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvaluation != null ? idEvaluation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evaluation)) {
            return false;
        }
        Evaluation other = (Evaluation) object;
        if ((this.idEvaluation == null && other.idEvaluation != null) || (this.idEvaluation != null && !this.idEvaluation.equals(other.idEvaluation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.Evaluation[ idEvaluation=" + idEvaluation + " ]";
    }
    
}
