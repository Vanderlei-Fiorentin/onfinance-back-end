package com.onfinance.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Entity
@Table(name = "bancos")
public class BancoEntity implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banco")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private EmpresaEntity empresa;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "codigo")
    private int codigo;
    
    @Column(name = "logo")
    private String logo;

    public BancoEntity() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public EmpresaEntity getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaEntity empresa) {
        this.empresa = empresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BancoEntity other = (BancoEntity) obj;
        return this.id == other.id;
    }
    
    
    
}