package br.com.joao.campoMinado;

import br.com.joao.campoMinado.modelo.Tabuleiro;
import br.com.joao.campoMinado.visao.TabuleiroConsole;

public class App {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(6, 6,6);
        new TabuleiroConsole(tabuleiro);
    }
}
