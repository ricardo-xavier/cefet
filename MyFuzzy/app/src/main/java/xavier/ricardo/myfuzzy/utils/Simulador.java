package xavier.ricardo.myfuzzy.utils;

import android.util.Log;

import xavier.ricardo.myfuzzy.tipos.AntecedenteConsequente;
import xavier.ricardo.myfuzzy.tipos.Operador;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class Simulador {

    public static double sumula(Problema problema) {

        for (int v=0; v<problema.getVariaveis().size()-1; v++) {

            Variavel variavel = problema.getVariaveis().get(v);

            int crisp = variavel.getCrisp();
            Log.i("simulador", variavel.getNome() + " " + crisp);

            for (Termo termo : variavel.getTermos()) {

                Log.i("simulador", termo.toString());
                termo.setPertinencia(0);

                if (crisp < termo.getA()) {
                    continue;
                }

                if (termo.getD() != null) {
                    if (crisp > termo.getD()) {
                        continue;
                    }
                } else {
                    if (crisp > termo.getC()) {
                        continue;
                    }
                    termo.setPertinencia(Triangulo.pertinencia(termo, crisp));
                }

            }

        }

        Double ativacaoFinal = null;

        for (Regra regra : problema.getRegras()) {

            Log.i("simulador", "regra: " + regra.getExprAntecedente());
            Log.i("simulador", "regra: " + regra.getExprConsequente());

            Double ativacao = null;
            Operador operador = null;

            for (int a=0; a<regra.getAntecedentes().size(); a++) {

                AntecedenteConsequente antecedente = regra.getAntecedentes().get(a);
                for (Termo termo : antecedente.getVariavel().getTermos()) {
                    if (termo == antecedente.getTermo()) {
                        double pertinencia = termo.getPertinencia();
                        if (ativacao == null) {
                            ativacao = pertinencia;
                        }
                        if (operador == null) {
                            ativacao = pertinencia;
                        } else if (operador == Operador.AND) {
                            if (pertinencia < ativacao) {
                                ativacao = pertinencia;
                            }
                        } else if (operador == Operador.OR) {
                            if (pertinencia > ativacao) {
                                ativacao = pertinencia;
                            }
                        }
                        Log.i("simulador", "termo: " + termo.getNome() + " " + pertinencia + " " + ativacao);
                        break;
                    }
                }
                operador = regra.getOperadores().get(a);
            }

        }

        return 0.;
    }
}
