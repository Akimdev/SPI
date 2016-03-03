/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univbrest.dosi.spi.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 *
 * @author DOSI
 */
@Entity
@Table(name = "AUTHENTIFICATION")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Authentification.findByPseudoAndPwd", query = "SELECT a FROM Authentification a WHERE a.pseudoConnection = :pseudoConnection AND a.motPasse = :motPasse"),
    @NamedQuery(name = "Authentification.findAll", query = "SELECT a FROM Authentification a"),
    @NamedQuery(name = "Authentification.findByIdConnection", query = "SELECT a FROM Authentification a WHERE a.idConnection = :idConnection"),
    @NamedQuery(name = "Authentification.findByRole", query = "SELECT a FROM Authentification a WHERE a.role = :role"),
    @NamedQuery(name = "Authentification.findByMailConnection", query = "SELECT a FROM Authentification a WHERE a.mailConnection = :mailConnection"),
    @NamedQuery(name = "Authentification.findByPseudoConnection", query = "SELECT a FROM Authentification a WHERE a.pseudoConnection = :pseudoConnection"),
    @NamedQuery(name = "Authentification.findByMotPasse", query = "SELECT a FROM Authentification a WHERE a.motPasse = :motPasse")})
public class Authentification implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CONNECTION")
    private Long idConnection;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ROLE")
    private String role;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "MAIL_CONNECTION")
    private String mailConnection;
    @Size(max = 240)
    @Column(name = "PSEUDO_CONNECTION")
    private String pseudoConnection;
    @Size(max = 32)
    @Column(name = "MOT_PASSE")
    private String motPasse;
    @JsonManagedReference(value="auth-Enseignant")
    @JoinColumn(name = "NO_ENSEIGNANT", referencedColumnName = "NO_ENSEIGNANT")
    @ManyToOne
    private Enseignant noEnseignant;
    @JsonManagedReference(value="auth-Etudiant")
    @JoinColumn(name = "NO_ETUDIANT", referencedColumnName = "NO_ETUDIANT")
    @ManyToOne
    private Etudiant noEtudiant;

    public Authentification() {
    }

    public Authentification(Long idConnection) {
        this.idConnection = idConnection;
    }

    public Authentification(Long idConnection, String role, String mailConnection) {
        this.idConnection = idConnection;
        this.role = role;
        this.mailConnection = mailConnection;
    }

    public Long getIdConnection() {
        return idConnection;
    }

    public void setIdConnection(Long idConnection) {
        this.idConnection = idConnection;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMailConnection() {
        return mailConnection;
    }

    public void setMailConnection(String mailConnection) {
        this.mailConnection = mailConnection;
    }

    public String getPseudoConnection() {
        return pseudoConnection;
    }

    public void setPseudoConnection(String pseudoConnection) {
        this.pseudoConnection = pseudoConnection;
    }

    public String getMotPasse() {
        return motPasse;
    }

    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }

    public Enseignant getNoEnseignant() {
        return noEnseignant;
    }

    public void setNoEnseignant(Enseignant noEnseignant) {
        this.noEnseignant = noEnseignant;
    }

    public Etudiant getNoEtudiant() {
        return noEtudiant;
    }

    public void setNoEtudiant(Etudiant noEtudiant) {
        this.noEtudiant = noEtudiant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConnection != null ? idConnection.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authentification)) {
            return false;
        }
        Authentification other = (Authentification) object;
        if ((this.idConnection == null && other.idConnection != null) || (this.idConnection != null && !this.idConnection.equals(other.idConnection))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.beans.Authentification[ idConnection=" + idConnection + " ]";
    }
    
}
