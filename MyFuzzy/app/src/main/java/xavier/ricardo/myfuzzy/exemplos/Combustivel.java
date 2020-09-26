package xavier.ricardo.myfuzzy.exemplos;

import java.util.ArrayList;
import java.util.List;

import xavier.ricardo.myfuzzy.tipos.AntecedenteConsequente;
import xavier.ricardo.myfuzzy.tipos.Operador;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.TipoVariavel;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class Combustivel {

    public static Problema carrega() {

        List<Variavel> variaveis = new ArrayList<>();

        Variavel velocidade = new Variavel("velocidade", 0, 200, TipoVariavel.ANTECEDENTE);
        velocidade.getTermos().add(new Termo("baixa", 0, 0, 40, 80));
        velocidade.getTermos().add(new Termo("media",  60, 80, 110));
        velocidade.getTermos().add(new Termo("alta", 90, 140, 200, 200));
        variaveis.add(velocidade);

        Variavel consumo = new Variavel("consumo", 0, 10, TipoVariavel.CONSEQUENTE);
        consumo.getTermos().add(new Termo("baixo", 12, 20, 25, 25));
        consumo.getTermos().add(new Termo("medio", 0, 12, 14));
        consumo.getTermos().add(new Termo("alto", 0, 0, 5, 10));
        variaveis.add(consumo);

        List<Regra> regras = new ArrayList<>();

        List<AntecedenteConsequente> antecedentes1 = new ArrayList<>();
        List<Operador> operadores1 = new ArrayList<>();
        AntecedenteConsequente antecedente11 = new AntecedenteConsequente(velocidade, velocidade.getTermos().get(0));
        antecedentes1.add(antecedente11);
        operadores1.add(null);
        AntecedenteConsequente consequente1 = new AntecedenteConsequente(consumo, consumo.getTermos().get(2));
        Regra r1 = new Regra(antecedentes1, operadores1, consequente1);
        regras.add(r1);

        List<AntecedenteConsequente> antecedentes2 = new ArrayList<>();
        List<Operador> operadores2 = new ArrayList<>();
        AntecedenteConsequente antecedente21 = new AntecedenteConsequente(velocidade, velocidade.getTermos().get(1));
        antecedentes2.add(antecedente21);
        operadores2.add(null);
        AntecedenteConsequente consequente2 = new AntecedenteConsequente(consumo, consumo.getTermos().get(0));
        Regra r2 = new Regra(antecedentes2, operadores2, consequente2);
        regras.add(r2);

        List<AntecedenteConsequente> antecedentes3 = new ArrayList<>();
        List<Operador> operadores3 = new ArrayList<>();
        AntecedenteConsequente antecedente31 = new AntecedenteConsequente(velocidade, velocidade.getTermos().get(2));
        antecedentes3.add(antecedente31);
        operadores3.add(null);
        AntecedenteConsequente consequente3 = new AntecedenteConsequente(consumo, consumo.getTermos().get(1));
        Regra r3 = new Regra(antecedentes3, operadores3, consequente3);
        regras.add(r3);

        Problema problema = new Problema("combustivel", variaveis, regras);
        return problema;

    }

}
