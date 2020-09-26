package xavier.ricardo.myfuzzy.utils;

import android.util.Log;

import xavier.ricardo.myfuzzy.tipos.AntecedenteConsequente;
import xavier.ricardo.myfuzzy.tipos.Operador;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.TipoVariavel;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class Simulador {

    public static double simula(Problema problema) {

        double resultado = 0;

        // inicializa pertinencias de antecedentes e consequentes
        for (int v=0; v<problema.getVariaveis().size(); v++) {
            Variavel variavel = problema.getVariaveis().get(v);
            for (Termo termo : variavel.getTermos()) {
                termo.setPertinencia(0);
            }
        }

        // avalia antecedentes
        for (int v=0; v<problema.getVariaveis().size(); v++) {

            Variavel variavel = problema.getVariaveis().get(v);
            if (variavel.getTipo() != TipoVariavel.ANTECEDENTE) {
                continue;
            }

            int crisp = variavel.getCrisp();
            Log.i("simulador", variavel.getNome() + " " + crisp);

            // seta a pertinencia dos termos
            for (Termo termo : variavel.getTermos()) {

                Log.i("simulador", termo.toString());
                if (crisp < termo.getA()) {
                    continue;
                }

                if (termo.getD() != null) {
                    if (crisp > termo.getD()) {
                        continue;
                    }
                    //TODO trapezoidal
                } else {
                    if (crisp > termo.getC()) {
                        continue;
                    }
                    termo.setPertinencia(Triangulo.getY(termo, crisp));
                }

            }

        }

        // avalia regras
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

            regra.getConsequente().getTermo().setPertinencia(ativacao);

        }

        // avalia o consequente
        for (Variavel variavel : problema.getVariaveis()) {

            if (variavel.getTipo() != TipoVariavel.CONSEQUENTE) {
                continue;
            }

            int n = variavel.getFim() - variavel.getInicio() + 1;
            double[] x = new double[n];
            double[] y = new double[n];

            int j = 0;
            for (int i=variavel.getInicio(); i<=variavel.getFim(); i++) {
                x[j] = i;
                y[j] = 0;
                for (Termo termo : variavel.getTermos()) {
                    if ((i < termo.getA()) || (i > termo.getC())) { //TODO trapezio
                        continue;
                    }
                    double pertinenciaTermo = termo.getPertinencia();
                    if (pertinenciaTermo <= y[j]) {
                        continue;
                    }
                    double pertinenciaX = Triangulo.getY(termo, i);
                    if (pertinenciaX > pertinenciaTermo) {
                        pertinenciaX = pertinenciaTermo;
                    }
                    if (pertinenciaX > y[j]) {
                        y[j] = pertinenciaX;
                    }
                }
                j++;
            }

            resultado = Centroid.centroid(x, y);
            break;
        }

        return resultado;
    }
}
