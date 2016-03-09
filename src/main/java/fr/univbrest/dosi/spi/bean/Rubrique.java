/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univbrest.dosi.spi.bean;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "RUBRIQUE")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Rubrique.findAll", query = "SELECT r FROM Rubrique r"),
		@NamedQuery(name = "Rubrique.findByIdRubrique", query = "SELECT r FROM Rubrique r WHERE r.idRubrique = :idRubrique"),
		@NamedQuery(name = "Rubrique.findByType", query = "SELECT r FROM Rubrique r WHERE r.type = :type"),
		@NamedQuery(name = "Rubrique.findByDesignation", query = "SELECT r FROM Rubrique r WHERE r.designation = :designation"),
		@NamedQuery(name = "Rubrique.findByOrdre", query = "SELECT r FROM Rubrique r WHERE r.ordre = :ordre"),
		@NamedQuery(name = "Rubrique.getMaxIdRubrique", query = "SELECT MAX(idRubrique) FROM Rubrique") })
public class Rubrique implements Serializable {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 32)
	@Column(name = "DESIGNATION")
	private String designation;
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID_RUBRIQUE")
	private Long idRubrique;
	@JsonIgnore
	@JoinColumn(name = "NO_ENSEIGNANT", referencedColumnName = "NO_ENSEIGNANT")
	@ManyToOne
	private Enseignant noEnseignant;
	@Column(name = "ORDRE")
	private BigInteger ordre;
	@JsonIgnore
	@OneToMany(mappedBy = "idRubrique")
	private Collection<RubriqueEvaluation> rubriqueEvaluationCollection;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "rubrique")
	private Collection<RubriqueQuestion> rubriqueQuestionCollection;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 10)
	@Column(name = "TYPE")
	private String type;

	public Rubrique() {
	}

	public Rubrique(Long idRubrique) {
		this.idRubrique = idRubrique;
	}

	public Rubrique(Long idRubrique, String type, String designation) {
		this.idRubrique = idRubrique;
		this.type = type;
		this.designation = designation;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Rubrique)) {
			return false;
		}
		Rubrique other = (Rubrique) object;
		if ((this.idRubrique == null && other.idRubrique != null) || (this.idRubrique != null && !this.idRubrique.equals(other.idRubrique))) {
			return false;
		}
		return true;
	}

	public String getDesignation() {
		return designation;
	}

	public Long getIdRubrique() {
		return idRubrique;
	}

	public Enseignant getNoEnseignant() {
		return noEnseignant;
	}

	public BigInteger getOrdre() {
		return ordre;
	}

	@XmlTransient
	public Collection<RubriqueEvaluation> getRubriqueEvaluationCollection() {
		return rubriqueEvaluationCollection;
	}

	@XmlTransient
	public Collection<RubriqueQuestion> getRubriqueQuestionCollection() {
		return rubriqueQuestionCollection;
	}

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idRubrique != null ? idRubrique.hashCode() : 0);
		return hash;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setIdRubrique(Long idRubrique) {
		this.idRubrique = idRubrique;
	}

	public void setNoEnseignant(Enseignant noEnseignant) {
		this.noEnseignant = noEnseignant;
	}

	public void setOrdre(BigInteger ordre) {
		this.ordre = ordre;
	}

	public void setRubriqueEvaluationCollection(Collection<RubriqueEvaluation> rubriqueEvaluationCollection) {
		this.rubriqueEvaluationCollection = rubriqueEvaluationCollection;
	}

	public void setRubriqueQuestionCollection(Collection<RubriqueQuestion> rubriqueQuestionCollection) {
		this.rubriqueQuestionCollection = rubriqueQuestionCollection;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "com.example.beans.Rubrique[ idRubrique=" + idRubrique + " ]";
	}

}
