package br.com.joao.campoMinado.modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TabuleiroTest {

    private Tabuleiro tabuleiro;
    private int linhas;
    private int colunas;
    private int minas;


    @BeforeEach
    void inicializaTabuleiro()
    {
        Random rand = new Random();
        linhas = rand.nextInt(16);
        colunas = rand.nextInt(30);
        linhas++;
        colunas++;
        minas = linhas*colunas/2;
        tabuleiro = new Tabuleiro(linhas, colunas, minas);
    }

    @Test
    void testeGerarTabuleiroArgumentosInvalidos()
    {
        Assertions.assertThrowsExactly(
                IllegalArgumentException.class,
                () -> new Tabuleiro(0, 0, 0));
    }

    @Test
    void testeGerarTabuleiroQtdMinasInvalida()
    {
        Tabuleiro tabuleiroNovo = new Tabuleiro(2, 3, 7);
        Assertions.assertEquals(tabuleiroNovo.getQtdMinas(), 6);
    }

    @Test
    void testeGerarTabuleiroValido()
    {
        Tabuleiro tabuleiroNovo = new Tabuleiro(2, 3, 5);
        Assertions.assertAll(
                () -> Assertions.assertEquals(tabuleiroNovo.getQtdMinas(), 5),
                () -> Assertions.assertEquals(tabuleiroNovo.getQtdColunas(), 3),
                () -> Assertions.assertEquals(tabuleiroNovo.getQtdLinhas(), 2)
        );
    }

    @Test
    void testeGerarCampos()
    {
        Assertions.assertEquals(linhas*colunas, tabuleiro.getCampos().size());
    }

}
