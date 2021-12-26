
package com.onfinance.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "produtos")
public class ProdutoEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "id_unidade")
    private UnidadeMedidaEntity unidade;
    
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaProdutoEntity categoria;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "descricao")
    private String descricao;

    public ProdutoEntity() {
    
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UnidadeMedidaEntity getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeMedidaEntity unidade) {
        this.unidade = unidade;
    }

    public CategoriaProdutoEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProdutoEntity categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final ProdutoEntity other = (ProdutoEntity) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
