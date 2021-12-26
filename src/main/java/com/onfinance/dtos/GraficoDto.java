
package com.onfinance.dtos;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class GraficoDto {
    
    private String titulo;
    private String tipo;
    private String[] colunas;
    private Object[][] dados;

    public Object[][] getDados() {
        return dados;
    }

    public void setDados(Object[][] dados) {
        this.dados = dados;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void setColunas(String[] colunas) {
        this.colunas = colunas;
    }   
    
}