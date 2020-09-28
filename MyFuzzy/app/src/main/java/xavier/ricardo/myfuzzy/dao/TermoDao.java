package xavier.ricardo.myfuzzy.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import xavier.ricardo.myfuzzy.tipos.Termo;

public class TermoDao {

    public static void insert(SQLiteDatabase db, String problema, String variavel, Termo termo) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("PROBLEMA", problema);
        contentValues.put("VARIAVEL", variavel);
        contentValues.put("NOME", termo.getNome());
        contentValues.put("A", termo.getA());
        contentValues.put("B", termo.getB());
        contentValues.put("C", termo.getC());
        contentValues.put("D", termo.getD());
        db.insert("TERMOS", null, contentValues);

    }

    public static List<Termo> getAll(SQLiteDatabase db, String problema, String variavel) {

        List<Termo> termos = new ArrayList<>();

        Cursor res = db.query("TERMOS",
                new String[] { "NOME", "A", "B", "C", "D" },
                "PROBLEMA = ? and VARIAVEL = ?",
                new String[] { problema, variavel },
                null,
                null,
                "NOME");
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String nome = res.getString(res.getColumnIndex("NOME"));
            int a = res.getInt(res.getColumnIndex("A"));
            int b = res.getInt(res.getColumnIndex("B"));
            int c = res.getInt(res.getColumnIndex("C"));
            Integer d = res.getInt(res.getColumnIndex("D"));
            if (res.isNull(res.getColumnIndex("D"))) {
                d = null;
            }
            Termo termo = new Termo(nome, a, b, c, d);
            termos.add(termo);
            res.moveToNext();
        }

        res.close();
        return termos;

    }

}
