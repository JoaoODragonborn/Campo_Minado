package br.com.joao.campoMinado.modelo;

import br.com.joao.campoMinado.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo
{
    private final int linha;
    private final int coluna;

    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhanca = new ArrayList<>();

    public Campo(int linha, int coluna)
    {
        if(linha < 0 || coluna < 0)
            throw new IllegalArgumentException("Argumento Invalido");

        this.linha = linha;
        this.coluna = coluna;
    }

    boolean abrir()
    {
        if(aberto || marcado)
            return false;

        aberto = true;

        if(minado)
            throw new ExplosaoException();

        if(this.encontraMinasVizinhanca() == 0)
            vizinhanca.forEach(Campo::abrir);

        return true;
    }

    long encontraMinasVizinhanca()
    {
        return vizinhanca.stream()
                .filter( v -> v.minado )
                .count();
    }

    boolean adicionarVizinho(Campo vizinho)
    {
        //

        boolean linhaDiferente = this.linha != vizinho.linha;
        boolean colunaDiferente = this.coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(this.linha - vizinho.linha);
        int deltaColuna = Math.abs(this.coluna - vizinho.coluna);
        int deltaGeral = deltaLinha + deltaColuna;

        if(deltaGeral == 1 || diagonal && deltaGeral == 2)
        {
            if(vizinhanca.size() == 8)
                throw new ArrayIndexOutOfBoundsException("Limite de vizinhos excedido");
            vizinhanca.add(vizinho);
            return true;
        }
        return false;
    }

    public String toString()
    {
        if(isMarcado()) return "X";
        if(!isAberto()) return "?";
        if(minado) return "*";
        if(encontraMinasVizinhanca() == 0) return " ";
        return Long.toString(encontraMinasVizinhanca());
    }

    void reiniciar()
    {
        marcado = false;
        minado = false;
        aberto = false;
    }

    void alternarMarcacao()
    {
        if(!isAberto())
            marcado = !marcado;
    }

    boolean objetivoAlcancado()
    {
        return (isMinado() && isMarcado()) || (isAberto() && !isMinado());
    }


    void setVizinhanca(List<Campo> vizinhanca)
    {
        this.vizinhanca = vizinhanca;
    }

    void setAberto()
    {
        this.aberto = true;
    }

    void setMina()
    {
        this.minado = true;
    }

    boolean isMarcado()
    {
        return marcado;
    }

    boolean isMinado()
    {
        return minado;
    }

    boolean isAberto()
    {
        return aberto;
    }

    public int getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }
}
