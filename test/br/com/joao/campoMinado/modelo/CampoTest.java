package br.com.joao.campoMinado.modelo;
import br.com.joao.campoMinado.excecao.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

class CampoTest {

    private static Campo campo;
    private static List<Campo> vizinhanca;

    @BeforeEach
    void inicializaCampo()
    {
        campo = new Campo(3, 3);
    }

    static void inicializaVizinhanca()
    {
        vizinhanca = Arrays.asList(
            new Campo(3,2), new Campo(3, 4),
            new Campo(2,2), new Campo(4, 4),
            new Campo(4,2), new Campo(2, 4),
            new Campo(2,3), new Campo(4,3));
    }

    @Test
    void testeInstanciarCampoLinhaInvalida()
    {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Campo(-1, 2));
    }

    @Test
    void testeInstanciarCampoColunaInvalida()
    {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> new Campo(1, -2));
    }

    @Test
    void testeNaoAdicionarVizinhosEmExcesso()
    {
        inicializaVizinhanca();
        campo.setVizinhanca(vizinhanca);
        Assertions.assertThrowsExactly(ArrayIndexOutOfBoundsException.class,
                () -> campo.adicionarVizinho(new Campo(4,2)));
    }

    @Test
    void testeNaoAdicionarVizinhoADireita()
    {
        Campo vizinho = new Campo(3, 5);
        boolean resultado = campo.adicionarVizinho(vizinho);
        Assertions.assertFalse(resultado);
    }

    @Test
    void testeNaoAdicionarVizinhoSuperiorEsquerdo()
    {
        Campo vizinho = new Campo(2, 1);
        boolean resultado = campo.adicionarVizinho(vizinho);
        Assertions.assertFalse(resultado);
    }

    @Test
    void testeAdicionarVizinhoAEsquerda()
    {
        Campo vizinho = new Campo(3, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        Assertions.assertTrue(resultado);
    }

    @Test
    void testeAdicionarVizinhoABaixo()
    {
        Campo vizinho = new Campo(4, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        Assertions.assertTrue(resultado);
    }

    @Test
    void testeAdicionarSiPropriComoVizinho()
    {
        Campo vizinho = new Campo(3, 3);
        Assertions.assertFalse(campo.adicionarVizinho(vizinho));
    }

    @Test
    void testeEncontrarZeroVizinhosMinados()
    {
        inicializaVizinhanca();
        campo.setVizinhanca(vizinhanca);
        long qtdVizinhosMinados = campo.encontraMinasVizinhanca();
        Assertions.assertEquals( qtdVizinhosMinados, 0 );
    }

    @Test
    void testeEncontrarQuatroVizinhosMinados()
    {
        inicializaVizinhanca();

        vizinhanca.stream()
                .filter(c -> ( c.getColuna() + c.getLinha() ) % 2 == 0)
                .forEach(Campo::setMina);

        campo.setVizinhanca( vizinhanca);
        long qtdVizinhosMinados = campo.encontraMinasVizinhanca();
        Assertions.assertEquals( qtdVizinhosMinados, 4 );
    }

    @Test
    void testeAbrirCampo()
    {
        Assertions.assertTrue( campo.abrir() );
    }

    @Test
    void testeAbrirCampoMarcado()
    {
        campo.alternarMarcacao();
        Assertions.assertFalse( campo.abrir() );
    }

    @Test
    void testeAbrirCampoAberto()
    {
        campo.abrir();
        Assertions.assertFalse( campo.abrir() );
    }

    @Test
    void testeAbrirCampoAbertoMarcado()
    {
        campo.abrir();
        campo.alternarMarcacao();
        Assertions.assertFalse( campo.abrir() );
    }

    @Test
    void testeAbrirCampoMinadoMarcada()
    {
        campo.setMina();
        campo.alternarMarcacao();
        Assertions.assertFalse( campo.abrir() );
    }

    @Test
    void testeAbrirCampoMinado()
    {
        campo.setMina();
        Assertions.assertThrowsExactly( ExplosaoException.class, () -> campo.abrir() );
    }

    @Test
    void testeAbrirCampoSeguro()
    {
        inicializaVizinhanca();
        campo.setVizinhanca(vizinhanca);
        Assertions.assertTrue(campo.abrir());
    }

    @Test
    void testeAbrirCampoSeguroComVizinhoInseguro()
    {
        Campo vizinhoInseguro =  new Campo(3,1);
        vizinhoInseguro.setMina();

        inicializaVizinhanca();
        vizinhanca.getFirst().adicionarVizinho(vizinhoInseguro);

        campo.setVizinhanca(vizinhanca);
        campo.abrir();

        Assertions.assertTrue(
                vizinhanca.getFirst().isAberto() && vizinhanca.getLast().isAberto()
        );
    }

    @Test
    void testeEstaMarcado()
    {
        Assertions.assertFalse( campo.isMarcado() );
    }

    @Test
    void testeNaoEstaMarcado()
    {
        campo.alternarMarcacao();
        Assertions.assertTrue( campo.isMarcado() );
    }

    @Test
    void testeToStringFechado()
    {
        Assertions.assertEquals("?", campo.toString());
    }

    @Test
    void testeToStringAbertoMinado()
    {
        campo.setMina();
        Assertions.assertThrowsExactly(ExplosaoException.class, () -> campo.abrir());
    }

    @Test
    void testeToStringMarcadoFechadoMinado()
    {
        campo.alternarMarcacao();
        campo.setMina();
        Assertions.assertEquals("X", campo.toString());
    }
    @Test
    void testeToStringSeguro()
    {
        inicializaVizinhanca();
        campo.setVizinhanca(vizinhanca);
        campo.abrir();
        Assertions.assertEquals(" ", campo.toString());
    }

    @Test
    void testeToStringAbertoInseguro()
    {
        List<String> numeros = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        Random rand = new Random();
        inicializaVizinhanca();
        // Escolhe aleatoriamente quais vizinhos estarão minados
        vizinhanca.forEach((v) ->
        {
             if(rand.nextInt(2) % 2 == 0)
                 v.setMina();
        });
        // Garante que pelo menos um vizinho será minado
        vizinhanca.getFirst().setMina();

        campo.setVizinhanca(vizinhanca);
        campo.abrir();

        Assertions.assertTrue(numeros.contains(campo.toString()));
    }
}