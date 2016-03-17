/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univbrest.dosi.spi.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DOSI
 */
@Entity
@Table(name = "CG_REF_CODES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CgRefCodes.findAll", query = "SELECT c FROM CgRefCodes c"),
    @NamedQuery(name = "CgRefCodes.findByIdCgrc", query = "SELECT c FROM CgRefCodes c WHERE c.idCgrc = :idCgrc"),
    @NamedQuery(name = "CgRefCodes.findByRvDomain", query = "SELECT c FROM CgRefCodes c WHERE c.rvDomain = :rvDomain"),
    @NamedQuery(name = "CgRefCodes.findPaysDomain", query = "SELECT c FROM CgRefCodes c WHERE c.rvDomain = 'PAYS' AND c.rvLowValue = :rvLowValue"),
    @NamedQuery(name = "CgRefCodes.findByRvLowValue", query = "SELECT c FROM CgRefCodes c WHERE c.rvLowValue = :rvLowValue"),
    @NamedQuery(name = "CgRefCodes.findByRvHighValue", query = "SELECT c FROM CgRefCodes c WHERE c.rvHighValue = :rvHighValue"),
    @NamedQuery(name = "CgRefCodes.findByRvAbbreviation", query = "SELECT c FROM CgRefCodes c WHERE c.rvAbbreviation = :rvAbbreviation"),
    @NamedQuery(name = "CgRefCodes.findByRvMeaning", query = "SELECT c FROM CgRefCodes c WHERE c.rvMeaning = :rvMeaning")})
public class CgRefCodes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CGRC")
    private BigDecimal idCgrc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "RV_DOMAIN")
    private String rvDomain;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 240)
    @Column(name = "RV_LOW_VALUE")
    private String rvLowValue;
    @Size(max = 240)
    @Column(name = "RV_HIGH_VALUE")
    private String rvHighValue;
    @Size(max = 240)
    @Column(name = "RV_ABBREVIATION")
    private String rvAbbreviation;
    @Size(max = 240)
    @Column(name = "RV_MEANING")
    private String rvMeaning;

    public CgRefCodes() {
    }

    public CgRefCodes(BigDecimal idCgrc) {
        this.idCgrc = idCgrc;
    }

    public CgRefCodes(BigDecimal idCgrc, String rvDomain, String rvLowValue) {
        this.idCgrc = idCgrc;
        this.rvDomain = rvDomain;
        this.rvLowValue = rvLowValue;
    }

    public BigDecimal getIdCgrc() {
        return idCgrc;
    }

    public void setIdCgrc(BigDecimal idCgrc) {
        this.idCgrc = idCgrc;
    }

    public String getRvDomain() {
        return rvDomain;
    }

    public void setRvDomain(String rvDomain) {
        this.rvDomain = rvDomain;
    }

    public String getRvLowValue() {
        return rvLowValue;
    }

    public void setRvLowValue(String rvLowValue) {
        this.rvLowValue = rvLowValue;
    }

    public String getRvHighValue() {
        return rvHighValue;
    }

    public void setRvHighValue(String rvHighValue) {
        this.rvHighValue = rvHighValue;
    }

    public String getRvAbbreviation() {
        return rvAbbreviation;
    }

    public void setRvAbbreviation(String rvAbbreviation) {
        this.rvAbbreviation = rvAbbreviation;
    }

    public String getRvMeaning() {
        return rvMeaning;
    }

    public void setRvMeaning(String rvMeaning) {
        this.rvMeaning = rvMeaning;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCgrc != null ? idCgrc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CgRefCodes)) {
            return false;
        }
        CgRefCodes other = (CgRefCodes) object;
        if ((this.idCgrc == null && other.idCgrc != null) || (this.idCgrc != null && !this.idCgrc.equals(other.idCgrc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.CgRefCodes[ idCgrc=" + idCgrc + " ]";
    }  
}