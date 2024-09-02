package br.com.joao.campoMinado.modelo;

import java.util.List;
import java.util.ArrayList;

public class Tabuleiro {

    private int qtdLinhas;
    private int qtdColunas;
    private int qtdMinas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int qtdLinhas, int qtdColunas, int qtdMinas) throws IllegalArgumentException{
        if(qtdLinhas < 1 || qtdColunas < 1)
            throw new IllegalArgumentException("Quantidade de linhas ou clounas inválida");

        this.qtdLinhas = qtdLinhas;
        this.qtdColunas = qtdColunas;
        this.qtdMinas = Math.min(qtdMinas, qtdColunas * qtdLinhas);

        gerarCampos();
        associarVizinhos();
        sortearMinas();

    }

    private void sortearMinas() {
        long minasArmadas;
        do{

            int random = (int) (Math.random() * campos.size());
            campos.get(random).setMina();
            minasArmadas = campos.stream()
                    .filter(Campo::isMinado)
                    .count();
        }
        while(minasArmadas < qtdMinas);
    }

    private void associarVizinhos()
    {
        for(Campo c: campos)
            for (Campo v: campos) {
                c.adicionarVizinho(v);
            }
    }

    public void gerarCampos()
    {
        for(int i = 0; i < qtdLinhas; i++)
            for (int j = 0; j < qtdColunas; j++)
            {
                campos.add(new Campo(i, j));
            }
    }

    public boolean objetivoAlcancado()
    {
        return campos.stream().allMatch(Campo::objetivoAlcancado);
    }

    void reiniciar()
    {
        campos.forEach(Campo::reiniciar);
        sortearMinas();
    }

    public int getQtdLinhas() {
        return qtdLinhas;
    }

    public int getQtdColunas() {
        return qtdColunas;
    }

    public int getQtdMinas() {
        return qtdMinas;
    }

    public List<Campo> getCampos() {
        return campos;
    }

    public String toString()
    {
        return "";
    }
    // Faz o mesmo que o algoritmo acima, mas com complexidade O(n)
    // A leitura e compreensão são horríveis, mas a complexidade é menor!
    // Só funciona em tabuleiros de no mínimo 2*2.
    private void associarVizinhosOn()
    {
        int i = 0;
        // A1
        campos.get(i).adicionarVizinho(campos.get(i+1));
        campos.get(i).adicionarVizinho(campos.get(i + qtdColunas));
        campos.get(i).adicionarVizinho(campos.get(i + qtdColunas+1));
        i++;
        for(int j = 0; j < qtdColunas-2; j++)
        {
            // A2
            campos.get(i).adicionarVizinho(campos.get(i-1));
            campos.get(i).adicionarVizinho(campos.get(i+1));
            campos.get(i).adicionarVizinho(campos.get(i + qtdColunas-1));
            campos.get(i).adicionarVizinho(campos.get(i + qtdColunas));
            campos.get(i).adicionarVizinho(campos.get(i + qtdColunas+1));
            i++;
        }
        // A4
        campos.get(i).adicionarVizinho(campos.get(i-1));
        campos.get(i).adicionarVizinho(campos.get(i + qtdColunas - 1));
        campos.get(i).adicionarVizinho(campos.get(i + qtdColunas));
        i++;
        for(int j = 0; j < qtdLinhas - 2; j++)
        {
            // A3
            campos.get(i).adicionarVizinho(campos.get(i - qtdColunas));
            campos.get(i).adicionarVizinho(campos.get(i - qtdColunas + 1));
            campos.get(i).adicionarVizinho(campos.get(i+1));
            campos.get(i).adicionarVizinho(campos.get(i + qtdColunas));
            campos.get(i).adicionarVizinho(campos.get(i + qtdColunas + 1));
            i++;
            for(int l = 0; l < qtdColunas - 2; l++)
            {
                // G
                campos.get(i).adicionarVizinho(campos.get(i - qtdColunas - 1));
                campos.get(i).adicionarVizinho(campos.get(i - qtdColunas));
                campos.get(i).adicionarVizinho(campos.get(i - qtdColunas + 1));
                campos.get(i).adicionarVizinho(campos.get(i-1));
                campos.get(i).adicionarVizinho(campos.get(i+1));
                campos.get(i).adicionarVizinho(campos.get(i + qtdColunas - 1));
                campos.get(i).adicionarVizinho(campos.get(i + qtdColunas));
                campos.get(i).adicionarVizinho(campos.get(i + qtdColunas + 1));
                i++;
            }
            // A5
            campos.get(i).adicionarVizinho(campos.get(i - qtdColunas - 1));
            campos.get(i).adicionarVizinho(campos.get(i - qtdColunas));
            campos.get(i).adicionarVizinho(campos.get(i - 1));
            campos.get(i).adicionarVizinho(campos.get(i + qtdColunas - 1));
            campos.get(i).adicionarVizinho(campos.get(i + qtdColunas));
            i++;
        }
        // A6
        campos.get(i).adicionarVizinho(campos.get(i - qtdColunas));
        campos.get(i).adicionarVizinho(campos.get(i - qtdColunas + 1));
        campos.get(i).adicionarVizinho(campos.get(i + 1));
        i++;
        for(int j = 0; j < qtdColunas-2; j++)
        {
            // A8
            campos.get(i).adicionarVizinho(campos.get(i - qtdColunas-1));
            campos.get(i).adicionarVizinho(campos.get(i - qtdColunas));
            campos.get(i).adicionarVizinho(campos.get(i - qtdColunas+1));
            campos.get(i).adicionarVizinho(campos.get(i-1));
            campos.get(i).adicionarVizinho(campos.get(i+1));
            i++;
        }
        // A7
        campos.get(i).adicionarVizinho(campos.get(i - qtdColunas - 1));
        campos.get(i).adicionarVizinho(campos.get(i - qtdColunas));
        campos.get(i).adicionarVizinho(campos.get(i - 1));
    }


}
