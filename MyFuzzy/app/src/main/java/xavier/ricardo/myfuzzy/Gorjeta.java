package xavier.ricardo.myfuzzy;

import java.util.ArrayList;
import java.util.List;

import xavier.ricardo.myfuzzy.tipos.AntecedenteConsequente;
import xavier.ricardo.myfuzzy.tipos.Operador;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
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

        List<Regra> regras = new ArrayList<>();

        List<AntecedenteConsequente> antecedentes1 = new ArrayList<>();
        List<Operador> operadores1 = new ArrayList<>();
        AntecedenteConsequente antecedente11 = new AntecedenteConsequente(servico, servico.getValores().get(2));
        antecedentes1.add(antecedente11);
        operadores1.add(Operador.OR);
        AntecedenteConsequente antecedente12 = new AntecedenteConsequente(comida, comida.getValores().get(2));
        antecedentes1.add(antecedente12);
        operadores1.add(null);
        AntecedenteConsequente consequente1 = new AntecedenteConsequente(gorjeta, gorjeta.getValores().get(2));
        Regra r1 = new Regra(antecedentes1, operadores1, consequente1);
        regras.add(r1);

        List<AntecedenteConsequente> antecedentes2 = new ArrayList<>();
        List<Operador> operadores2 = new ArrayList<>();
        AntecedenteConsequente antecedente21 = new AntecedenteConsequente(servico, servico.getValores().get(1));
        antecedentes2.add(antecedente21);
        operadores2.add(null);
        AntecedenteConsequente consequente2 = new AntecedenteConsequente(gorjeta, gorjeta.getValores().get(1));
        Regra r2 = new Regra(antecedentes2, operadores2, consequente2);
        regras.add(r2);

        List<AntecedenteConsequente> antecedentes3 = new ArrayList<>();
        List<Operador> operadores3 = new ArrayList<>();
        AntecedenteConsequente antecedente31 = new AntecedenteConsequente(servico, servico.getValores().get(0));
        antecedentes3.add(antecedente31);
        operadores3.add(Operador.AND);
        AntecedenteConsequente antecedente32 = new AntecedenteConsequente(comida, comida.getValores().get(0));
        antecedentes3.add(antecedente32);
        operadores3.add(null);
        AntecedenteConsequente consequente3 = new AntecedenteConsequente(gorjeta, gorjeta.getValores().get(0));
        Regra r3 = new Regra(antecedentes3, operadores3, consequente3);
        regras.add(r3);

        Problema problema = new Problema("gorjeta", variaveis, regras);
        return problema;

    }

}
