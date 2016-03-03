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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "REPONSE_EVALUATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReponseEvaluation.findAll", query = "SELECT r FROM ReponseEvaluation r"),
    @NamedQuery(name = "ReponseEvaluation.findByIdReponseEvaluation", query = "SELECT r FROM ReponseEvaluation r WHERE r.idReponseEvaluation = :idReponseEvaluation"),
    @NamedQuery(name = "ReponseEvaluation.findByCommentaire", query = "SELECT r FROM ReponseEvaluation r WHERE r.commentaire = :commentaire"),
    @NamedQuery(name = "ReponseEvaluation.findByNom", query = "SELECT r FROM ReponseEvaluation r WHERE r.nom = :nom"),
    @NamedQuery(name = "ReponseEvaluation.findByPrenom", query = "SELECT r FROM ReponseEvaluation r WHERE r.prenom = :prenom")})
public class ReponseEvaluation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_REPONSE_EVALUATION")
    private Long idReponseEvaluation;
    @Size(max = 512)
    @Column(name = "COMMENTAIRE")
    private String commentaire;
    @Size(max = 32)
    @Column(name = "NOM")
    private String nom;
    @Size(max = 32)
    @Column(name = "PRENOM")
    private String prenom;
    @JsonIgnore
    @JoinColumn(name = "NO_ETUDIANT", referencedColumnName = "NO_ETUDIANT")
    @ManyToOne
    private Etudiant noEtudiant;
    @JsonIgnore
    @JoinColumn(name = "ID_EVALUATION", referencedColumnName = "ID_EVALUATION")
    @ManyToOne(optional = false)
    private Evaluation idEvaluation;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reponseEvaluation")
    private Collection<ReponseQuestion> reponseQuestionCollection;

    public ReponseEvaluation() {
    }

    public ReponseEvaluation(Long idReponseEvaluation) {
        this.idReponseEvaluation = idReponseEvaluation;
    }

    public Long getIdReponseEvaluation() {
        return idReponseEvaluation;
    }

    public void setIdReponseEvaluation(Long idReponseEvaluation) {
        this.idReponseEvaluation = idReponseEvaluation;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Etudiant getNoEtudiant() {
        return noEtudiant;
    }

    public void setNoEtudiant(Etudiant noEtudiant) {
        this.noEtudiant = noEtudiant;
    }

    public Evaluation getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(Evaluation idEvaluation) {
        this.idEvaluation = idEvaluation;
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
        hash += (idReponseEvaluation != null ? idReponseEvaluation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReponseEvaluation)) {
            return false;
        }
        ReponseEvaluation other = (ReponseEvaluation) object;
        if ((this.idReponseEvaluation == null && other.idReponseEvaluation != null) || (this.idReponseEvaluation != null && !this.idReponseEvaluation.equals(other.idReponseEvaluation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.ReponseEvaluation[ idReponseEvaluation=" + idReponseEvaluation + " ]";
    }
    
}
