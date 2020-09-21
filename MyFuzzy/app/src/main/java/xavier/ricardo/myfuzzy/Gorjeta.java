package xavier.ricardo.myfuzzy;

import java.util.ArrayList;
import java.util.List;

import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Valor;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class Gorjeta {

    public static Problema carrega() {

        List<Variavel> variaveis = new ArrayList<>();

        Variavel comida = new Variavel("comida", 0, 10);
        comida.getValores().add(new Valor("pessima", 0, 0, 5));
        comida.getValores().add(new Valor("comivel", 0, 5, 10));
        comida.getValores().add(new Valor("deliciosa", 5, 10, 10));
        variaveis.add(comida);

        Variavel servico = new Variavel("servico", 0, 10);
        servico.getValores().add(new Valor("ruim", 0, 0, 5));
        servico.getValores().add(new Valor("aceitavel", 0, 5, 10));
        servico.getValores().add(new Valor("excelente", 5, 10, 10));
        variaveis.add(servico);

        Variavel gorjeta = new Variavel("gorjeta", 0, 24);
        gorjeta.getValores().add(new Valor("baixa", 0, 0, 12));
        gorjeta.getValores().add(new Valor("media", 0, 12, 24));
        gorjeta.getValores().add(new Valor("alta", 12, 24, 24));
        variaveis.add(gorjeta);

        Problema problema = new Problema("gorjeta", variaveis);
        return problema;

    }

}
