package xavier.ricardo.myfuzzy.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import xavier.ricardo.myfuzzy.tipos.AntecedenteConsequente;
import xavier.ricardo.myfuzzy.tipos.Operador;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class RegraDao {

    public static List<Regra> getAll(SQLiteDatabase db, String problema, List<Variavel> variaveis) {

        List<Regra> regras = new ArrayList<>();

        Cursor res = db.query("ANTECEDENTES_CONSEQUENTES",
                new String[] { "REGRA", "TIPO", "SEQ", "VARIAVEL", "TERMO", "OPERADOR" },
                "PROBLEMA = ?",
                new String[] { problema },
                null,
                null,
                "REGRA, TIPO, SEQ");
        res.moveToFirst();

        int rAnterior = -1;
        Regra regra = null;
        List<AntecedenteConsequente> antecedentes = null;
        List<Operador > operadores = null;
        AntecedenteConsequente consequente = null;
        while (!res.isAfterLast()) {
            int r = res.getInt(res.getColumnIndex("REGRA"));
            Log.i("BANCO", "select regra:" + r);
            if (r != rAnterior) {
                rAnterior = r;
                antecedentes = new ArrayList<>();
                operadores = new ArrayList<>();
                operadores = new ArrayList<>();
                consequente = new AntecedenteConsequente();
                regra = new Regra(antecedentes, operadores, consequente);
                regra.setId(r);
                regras.add(regra);
            }
            String tipo = res.getString(res.getColumnIndex("TIPO"));

            String nomeVariavel  = res.getString(res.getColumnIndex("VARIAVEL"));
            Variavel variavel = null;
            for (Variavel v : variaveis) {
                if (v.getNome().equals(nomeVariavel)) {
                    variavel = v;
                    break;
                }
            }

            String nomeTermo  = res.getString(res.getColumnIndex("TERMO"));
            Termo termo = null;
            if (variavel != null) {
                for (Termo t : variavel.getTermos()) {
                    if (t.getNome().equals(nomeTermo)) {
                        termo = t;
                        break;
                    }
                }
            }

            Operador operador = null;
            String op  = res.getString(res.getColumnIndex("OPERADOR"));
            if (op != null) {
                operador = op.equals("&") ? Operador.AND : Operador.OR;
            }

            if (tipo.equals("A")) {
                AntecedenteConsequente antecedente = new AntecedenteConsequente();
                antecedente.setVariavel(variavel);
                antecedente.setTermo(termo);
                antecedentes.add(antecedente);
                operadores.add(operador);
            } else {
                consequente.setVariavel(variavel);
                consequente.setTermo(termo);
            }
            res.moveToNext();
        }

        res.close();

        return regras;
    }


    public static void delete(SQLiteDatabase db, String problema, int regra) {

        db.delete("ANTECEDENTES_CONSEQUENTES", "PROBLEMA = ? and REGRA = ?",
                new String[] { problema, String.valueOf(regra) } );

    }

}
