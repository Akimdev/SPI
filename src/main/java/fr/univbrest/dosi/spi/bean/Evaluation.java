package fr.univbrest.dosi.spi.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
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
@Table(name = "EVALUATION", catalog = "", schema = "DOSI", uniqueConstraints = { @UniqueConstraint(columnNames = { "ANNEE_UNIVERSITAIRE", "NO_ENSEIGNANT", "NO_EVALUATION", "CODE_FORMATION", "CODE_UE" }) })
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evaluation.findAll", query = "SELECT e FROM Evaluation e"),
    @NamedQuery(name = "Evaluation.findByFormationAndAnnee", query = "SELECT e FROM Evaluation e WHERE e.annee = :annee AND e.code_formation = :code_formation"),
    @NamedQuery(name = "Evaluation.findByIdEvaluation", query = "SELECT e FROM Evaluation e WHERE e.idEvaluation = :idEvaluation"),
    @NamedQuery(name = "Evaluation.findByNoEvaluation", query = "SELECT e FROM Evaluation e WHERE e.noEvaluation = :noEvaluation"),
    @NamedQuery(name = "Evaluation.findByNoEnseignant", query = "SELECT e FROM Evaluation e WHERE e.noEnseignant = :noEnseignant"),
    @NamedQuery(name = "Evaluation.findByDesignation", query = "SELECT e FROM Evaluation e WHERE e.designation = :designation"),
    @NamedQuery(name = "Evaluation.findByEtat", query = "SELECT e FROM Evaluation e WHERE e.etat = :etat"),
    @NamedQuery(name = "Evaluation.findByPeriode", query = "SELECT e FROM Evaluation e WHERE e.periode = :periode"),
    @NamedQuery(name = "Evaluation.findByDebutReponse", query = "SELECT e FROM Evaluation e WHERE e.debutReponse = :debutReponse"),
    @NamedQuery(name = "Evaluation.findByFinReponse", query = "SELECT e FROM Evaluation e WHERE e.finReponse = :finReponse")})
public class Evaluation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "ANNEE_UNIVERSITAIRE", nullable = false)
	private String annee;
	@Column(name = "CODE_EC")
	private String code_ec;
	@Column(name = "CODE_FORMATION", nullable = false)
	private String code_formation;
	@Column(name = "CODE_UE", nullable = false)
	private String code_ue;
	@Basic(optional = false)
	@NotNull
	@Column(name = "DEBUT_REPONSE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date debutReponse;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 16)
	@Column(name = "DESIGNATION", nullable = false, length = 16)
	private String designation;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 3)
	@Column(name = "ETAT", nullable = false, length = 3)
	private String etat;
	@Basic(optional = false)
	@NotNull
	@Column(name = "FIN_REPONSE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date finReponse;
	@Id
	@GeneratedValue(generator = "EVE_SEQ", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "EVE_SEQ", sequenceName = "EVE_SEQ", allocationSize = 1)
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID_EVALUATION", nullable = false)
	private Integer idEvaluation;
	@JoinColumn(name = "NO_ENSEIGNANT", referencedColumnName = "NO_ENSEIGNANT", nullable = false )
	@ManyToOne
	private Enseignant noEnseignant;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluation")
	private Collection<Droit> droitCollection;
	@Basic(optional = false)
	@NotNull
	@Column(name = "NO_EVALUATION", nullable = false)
	private short noEvaluation;
	@Size(max = 64)
	@Column(name = "PERIODE", length = 64)
	private String periode;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvaluation", fetch = FetchType.LAZY)
	private Collection<ReponseEvaluation> reponseEvaluationCollection;
	//@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvaluation")
	private Collection<RubriqueEvaluation> rubriqueEvaluationCollection;

	public Evaluation() {
	}

	public Evaluation(final Integer idEvaluation) {
		this.idEvaluation = idEvaluation;
	}

	public Evaluation(final Integer idEvaluation, final short noEvaluation, final String designation, final String etat, final Date debutReponse, final Date finReponse) {
		this.idEvaluation = idEvaluation;
		this.noEvaluation = noEvaluation;
		this.designation = designation;
		this.etat = etat;
		this.debutReponse = debutReponse;
		this.finReponse = finReponse;
	}

	@Override
	public boolean equals(final Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Evaluation)) {
			return false;
		}
		final Evaluation other = (Evaluation) object;
		if (this.idEvaluation == null && other.idEvaluation != null || this.idEvaluation != null && !this.idEvaluation.equals(other.idEvaluation)) {
			return false;
		}
		return true;
	}

	public String getAnnee() {
		return this.annee;
	}

	public String getCode_ec() {
		return this.code_ec;
	}

	public String getCode_formation() {
		return this.code_formation;
	}

	public String getCode_ue() {
		return this.code_ue;
	}

	public Date getDebutReponse() {
		return this.debutReponse;
	}

	public String getDesignation() {
		return this.designation;
	}

	public String getEtat() {
		return this.etat;
	}

	public Date getFinReponse() {
		return this.finReponse;
	}

	public Integer getIdEvaluation() {
		return this.idEvaluation;
	}

	public Enseignant getNoEnseignant() {
		return this.noEnseignant;
	}

	public short getNoEvaluation() {
		return this.noEvaluation;
	}

	public String getPeriode() {
		return this.periode;
	}

	public Collection<ReponseEvaluation> getReponseEvaluationCollection() {
		return this.reponseEvaluationCollection;
	}

	@XmlTransient
	public Collection<RubriqueEvaluation> getRubriqueEvaluationCollection() {
		return this.rubriqueEvaluationCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += this.idEvaluation != null ? this.idEvaluation.hashCode() : 0;
		return hash;
	}

	public void setAnnee(final String annee) {
		this.annee = annee;
	}

	public void setCode_ec(final String code_ec) {
		this.code_ec = code_ec;
	}

	public void setCode_formation(final String code_formation) {
		this.code_formation = code_formation;
	}

	public void setCode_ue(final String code_ue) {
		this.code_ue = code_ue;
	}

	public void setDebutReponse(final Date debutReponse) {
		this.debutReponse = debutReponse;
	}

	public void setDesignation(final String designation) {
		this.designation = designation;
	}

	public void setEtat(final String etat) {
		this.etat = etat;
	}

	public void setFinReponse(final Date finReponse) {
		this.finReponse = finReponse;
	}

	public void setIdEvaluation(final Integer idEvaluation) {
		this.idEvaluation = idEvaluation;
	}

	public void setNoEnseignant(final Enseignant noEnseignant) {
		this.noEnseignant = noEnseignant;
	}

	public void setNoEvaluation(final short noEvaluation) {
		this.noEvaluation = noEvaluation;
	}

	public void setPeriode(final String periode) {
		this.periode = periode;
	}

	public void setReponseEvaluationCollection(final Collection<ReponseEvaluation> reponseEvaluationCollection) {
		this.reponseEvaluationCollection = reponseEvaluationCollection;
	}

	public void setRubriqueEvaluationCollection(final Collection<RubriqueEvaluation> rubriqueEvaluationCollection) {
		this.rubriqueEvaluationCollection = rubriqueEvaluationCollection;
	}

	@Override
	public String toString() {
		return "rest.Evaluation[ idEvaluation=" + this.idEvaluation + " ]";
	}
}