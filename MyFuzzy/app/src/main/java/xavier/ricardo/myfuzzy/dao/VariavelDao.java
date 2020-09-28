package xavier.ricardo.myfuzzy.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.TipoVariavel;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class VariavelDao {

    public static void insert(SQLiteDatabase db, String problema, Variavel variavel) {

        ContentValues contentValues = new ContentValues();
        contentValues = new ContentValues();
        contentValues.put("PROBLEMA", problema);
        contentValues.put("NOME", variavel.getNome());
        contentValues.put("TIPO", variavel.getTipo() == TipoVariavel.ANTECEDENTE ? "A" : "C");
        contentValues.put("INICIO", variavel.getInicio());
        contentValues.put("FIM", variavel.getFim());
        db.insert("VARIAVEIS", null, contentValues);

    }

    public static List<Variavel> getAll(SQLiteDatabase db, String problema) {

        List<Variavel> variaveis = new ArrayList<>();

        Cursor res = db.query("VARIAVEIS",
                new String[] { "NOME", "INICIO", "FIM", "TIPO" },
                "PROBLEMA = ?",
                new String[] { problema },
                null,
                null,
                "TIPO, NOME");
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String nome = res.getString(res.getColumnIndex("NOME"));
            int inicio = res.getInt(res.getColumnIndex("INICIO"));
            int fim = res.getInt(res.getColumnIndex("FIM"));
            String tipo = res.getString(res.getColumnIndex("TIPO"));
            Variavel variavel = new Variavel(nome, inicio, fim, tipo.equals("A") ? TipoVariavel.ANTECEDENTE : TipoVariavel.CONSEQUENTE);
            List<Termo> termos = TermoDao.getAll(db, problema, nome);
            variavel.setTermos(termos);
            variaveis.add(variavel);
            res.moveToNext();
        }

        res.close();
        return variaveis;
    }
}
