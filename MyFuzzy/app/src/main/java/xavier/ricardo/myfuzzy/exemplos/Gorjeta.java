package xavier.ricardo.myfuzzy.exemplos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import xavier.ricardo.myfuzzy.MainActivity;
import xavier.ricardo.myfuzzy.R;
import xavier.ricardo.myfuzzy.dao.AntecedenteConsequenteDao;
import xavier.ricardo.myfuzzy.dao.ProblemaDao;
import xavier.ricardo.myfuzzy.dao.TermoDao;
import xavier.ricardo.myfuzzy.dao.VariavelDao;
import xavier.ricardo.myfuzzy.tipos.AntecedenteConsequente;
import xavier.ricardo.myfuzzy.tipos.Operador;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.TipoVariavel;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class Gorjeta {

    public static void popula(SQLiteDatabase db, Problema problema) {

        ProblemaDao.insert(db, problema);

        for (Variavel variavel : problema.getVariaveis()) {

            VariavelDao.insert(db, problema.getNome(), variavel);

            for (Termo termo : variavel.getTermos()) {
                TermoDao.insert(db, problema.getNome(), variavel.getNome(), termo);
            }

        }

        for (Regra regra : problema.getRegras()) {
            for (int a=0; a<regra.getAntecedentes().size(); a++) {
                AntecedenteConsequenteDao.insert(db, problema.getNome(), regra.getId(), "A", a+1,
                        regra.getAntecedentes().get(a), regra.getOperadores().get(a));
            }
            AntecedenteConsequenteDao.insert(db, problema.getNome(), regra.getId(), "C", 1,
                    regra.getConsequente(), null);
        }

    }

    public static Problema inicializa() {

        String _gorjeta = MainActivity.getStringResources().getString(R.string.gorjeta);
        String _servico = MainActivity.getStringResources().getString(R.string.servico);
        String _comida = MainActivity.getStringResources().getString(R.string.comida);
        String _ruim = MainActivity.getStringResources().getString(R.string.ruim);
        String _aceitavel = MainActivity.getStringResources().getString(R.string.aceitavel);
        String _excelente = MainActivity.getStringResources().getString(R.string.excelente);
        String _pessima = MainActivity.getStringResources().getString(R.string.pessima);
        String _comivel = MainActivity.getStringResources().getString(R.string.comivel);
        String _deliciosa = MainActivity.getStringResources().getString(R.string.deliciosa);
        String _baixa = MainActivity.getStringResources().getString(R.string.baixa);
        String _media = MainActivity.getStringResources().getString(R.string.media);
        String _alta = MainActivity.getStringResources().getString(R.string.alta);

        List<Variavel> variaveis = new ArrayList<>();

        Variavel comida = new Variavel(_comida, 0, 10, TipoVariavel.ANTECEDENTE);
        comida.getTermos().add(new Termo(_pessima, 0, 0, 5));
        comida.getTermos().add(new Termo(_comivel, 0, 5, 10));
        comida.getTermos().add(new Termo(_deliciosa, 5, 10, 10));
        variaveis.add(comida);

        Variavel servico = new Variavel(_servico, 0, 10, TipoVariavel.ANTECEDENTE);
        servico.getTermos().add(new Termo(_ruim, 0, 0, 5));
        servico.getTermos().add(new Termo(_aceitavel, 0, 5, 10));
        servico.getTermos().add(new Termo(_excelente, 5, 10, 10));
        variaveis.add(servico);

        Variavel gorjeta = new Variavel(_gorjeta, 0, 24, TipoVariavel.CONSEQUENTE);
        gorjeta.getTermos().add(new Termo(_baixa, 0, 0, 12));
        gorjeta.getTermos().add(new Termo(_media, 0, 12, 24));
        gorjeta.getTermos().add(new Termo(_alta, 12, 24, 24));
        variaveis.add(gorjeta);

        List<Regra> regras = new ArrayList<>();

        List<AntecedenteConsequente> antecedentes1 = new ArrayList<>();
        List<Operador> operadores1 = new ArrayList<>();
        AntecedenteConsequente antecedente11 = new AntecedenteConsequente(servico, servico.getTermos().get(2));
        antecedentes1.add(antecedente11);
        operadores1.add(Operador.OR);
        AntecedenteConsequente antecedente12 = new AntecedenteConsequente(comida, comida.getTermos().get(2));
        antecedentes1.add(antecedente12);
        operadores1.add(null);
        AntecedenteConsequente consequente1 = new AntecedenteConsequente(gorjeta, gorjeta.getTermos().get(2));
        Regra r1 = new Regra(antecedentes1, operadores1, consequente1);
        r1.setId(1);
        regras.add(r1);

        List<AntecedenteConsequente> antecedentes2 = new ArrayList<>();
        List<Operador> operadores2 = new ArrayList<>();
        AntecedenteConsequente antecedente21 = new AntecedenteConsequente(servico, servico.getTermos().get(1));
        antecedentes2.add(antecedente21);
        operadores2.add(null);
        AntecedenteConsequente consequente2 = new AntecedenteConsequente(gorjeta, gorjeta.getTermos().get(1));
        Regra r2 = new Regra(antecedentes2, operadores2, consequente2);
        r2.setId(2);
        regras.add(r2);

        List<AntecedenteConsequente> antecedentes3 = new ArrayList<>();
        List<Operador> operadores3 = new ArrayList<>();
        AntecedenteConsequente antecedente31 = new AntecedenteConsequente(servico, servico.getTermos().get(0));
        antecedentes3.add(antecedente31);
        operadores3.add(Operador.AND);
        AntecedenteConsequente antecedente32 = new AntecedenteConsequente(comida, comida.getTermos().get(0));
        antecedentes3.add(antecedente32);
        operadores3.add(null);
        AntecedenteConsequente consequente3 = new AntecedenteConsequente(gorjeta, gorjeta.getTermos().get(0));
        Regra r3 = new Regra(antecedentes3, operadores3, consequente3);
        r3.setId(3);
        regras.add(r3);

        Problema problema = new Problema(_gorjeta, variaveis, regras);
        return problema;

    }

}
