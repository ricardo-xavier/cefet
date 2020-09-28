package xavier.ricardo.myfuzzy.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.Variavel;

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
}
