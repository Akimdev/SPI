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
@Table(name = "QUESTION")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Question.findQualificatif", query = "SELECT q.idQualificatif FROM Question q WHERE q.idQuestion = :idQuestion"),
	@NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
	@NamedQuery(name = "Question.findByIdQuestion", query = "SELECT q FROM Question q WHERE q.idQuestion = :idQuestion"),
	@NamedQuery(name = "Question.findByType", query = "SELECT q FROM Question q WHERE q.type = :type"),
	@NamedQuery(name = "Question.findByIntitul\u00e9", query = "SELECT q FROM Question q WHERE q.intitul\u00e9 = :intitul\u00e9"),
	@NamedQuery(name = "Question.getMaxIdQuestion", query = "SELECT MAX(idQuestion) FROM Question") })
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	// @JsonManagedReference(value="question-Qualificatif")
	@JoinColumn(name = "ID_QUALIFICATIF", referencedColumnName = "ID_QUALIFICATIF")
	@ManyToOne(optional = false)
	private Qualificatif idQualificatif;
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID_QUESTION")
	private Long idQuestion;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 64)
	@Column(name = "INTITUL\u00c9")
	private String intitulé;
	@JsonIgnore
	@JoinColumn(name = "NO_ENSEIGNANT", referencedColumnName = "NO_ENSEIGNANT")
	@ManyToOne
	private Enseignant noEnseignant;
	@JsonIgnore
	@OneToMany(mappedBy = "idQuestion")
	private Collection<QuestionEvaluation> questionEvaluationCollection;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
	private Collection<RubriqueQuestion> rubriqueQuestionCollection;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 10)
	@Column(name = "TYPE")
	private String type;

	public Question() {
	}

	public Question(Long idQuestion) {
		this.idQuestion = idQuestion;
	}

	public Question(Long idQuestion, String type, String intitulé) {
		this.idQuestion = idQuestion;
		this.type = type;
		this.intitulé = intitulé;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Question)) {
			return false;
		}
		Question other = (Question) object;
		if ((this.idQuestion == null && other.idQuestion != null) || (this.idQuestion != null && !this.idQuestion.equals(other.idQuestion))) {
			return false;
		}
		return true;
	}

	public Qualificatif getIdQualificatif() {
		return idQualificatif;
	}

	public Long getIdQuestion() {
		return idQuestion;
	}

	public String getIntitule() {
		return intitulé;
	}

	public String getIntitulé() {
		return intitulé;
	}

	public Enseignant getNoEnseignant() {
		return noEnseignant;
	}

	@XmlTransient
	public Collection<QuestionEvaluation> getQuestionEvaluationCollection() {
		return questionEvaluationCollection;
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
		hash += (idQuestion != null ? idQuestion.hashCode() : 0);
		return hash;
	}

	public void setIdQualificatif(Qualificatif idQualificatif) {
		this.idQualificatif = idQualificatif;
	}

	public void setIdQuestion(Long idQuestion) {
		this.idQuestion = idQuestion;
	}

	public void setIntitule(String intitule) {
		this.intitulé = intitule;
	}

	public void setIntitulé(String intitulé) {
		this.intitulé = intitulé;
	}

	public void setNoEnseignant(Enseignant noEnseignant) {
		this.noEnseignant = noEnseignant;
	}

	public void setQuestionEvaluationCollection(Collection<QuestionEvaluation> questionEvaluationCollection) {
		this.questionEvaluationCollection = questionEvaluationCollection;
	}

	public void setRubriqueQuestionCollection(Collection<RubriqueQuestion> rubriqueQuestionCollection) {
		this.rubriqueQuestionCollection = rubriqueQuestionCollection;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "com.example.beans.Question[ idQuestion=" + idQuestion + " ]";
	}

}
