package xavier.ricardo.myfuzzy.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import xavier.ricardo.myfuzzy.tipos.Problema;
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
        contentValues.put("FIM", variavel.getInicio());
        db.insert("VARIAVEIS", null, contentValues);

    }

}
