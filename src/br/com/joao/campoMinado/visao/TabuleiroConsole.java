package br.com.joao.campoMinado.visao;

import br.com.joao.campoMinado.excecao.ExplosaoException;
import br.com.joao.campoMinado.excecao.SairException;
import br.com.joao.campoMinado.modelo.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroConsole {

    Tabuleiro tabuleiro;
    private final Scanner entrada = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        executarJogo();
    }

    private void executarJogo() {
        try
        {
            boolean continuar = true;

            while(continuar)
            {
                cicloDojogo();
                System.out.println("Deseja jogar mais uma Partida?(S/n)");
                String resposta = entrada.nextLine().trim();
                if("n".equalsIgnoreCase(resposta))
                    continuar = false;
                else
                    tabuleiro.reiniciar();
            }
        }
        catch(SairException e)
        {
            System.out.println("Adeus!");
        }
        finally {
            entrada.close();
        }
    }

    private void cicloDojogo() {
        try
        {
            while (!tabuleiro.objetivoAlcancado())
            {
                System.out.println(tabuleiro);

                String digitado = capturarValor("Digite (x, y): ");

                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .iterator();

                digitado = capturarValor("1 - Abrir ou 2 - Marcar");

                if(digitado.equals("1"))
                    tabuleiro.abrirCampo(xy.next(), xy.next());
                else if(digitado.equals("2"))
                    tabuleiro.alterarMarcacaoCampo(xy.next(), xy.next());
                else
                    System.out.println("Nada foi feito");
            }
            System.out.println("Você venceu!!!");
        }
        catch(ExplosaoException e)
        {
            System.out.println("Você perdeu!");
        }
    }

    private String capturarValor(String texto)
    {
        System.out.println(texto);
        String digitado = entrada.nextLine().trim();
        if(digitado.equalsIgnoreCase("sair"))
            throw new SairException();
        return digitado;
    }
}
