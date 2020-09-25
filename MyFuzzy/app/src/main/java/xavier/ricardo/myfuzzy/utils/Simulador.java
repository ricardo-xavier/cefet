package xavier.ricardo.myfuzzy.utils;

import android.util.Log;

import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class Simulador {

    public static int sumula(Problema problema) {

        for (int v=0; v<problema.getVariaveis().size()-1; v++) {

            Variavel variavel = problema.getVariaveis().get(v);

            int crisp = variavel.getCrisp();
            Log.i("simulador", variavel.getNome() + " " + crisp);

            for (Termo termo : variavel.getTermos()) {
                Log.i("simulador", termo.toString());

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
                }

                Log.i("simulador", "pertence:" + termo.getNome());
            }

        }

        return 0;
    }
}
