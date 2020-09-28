package xavier.ricardo.myfuzzy.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class ProblemaDao {

    public static void insert(SQLiteDatabase db, Problema problema) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME", problema.getNome());
        db.insert("PROBLEMAS", null, contentValues);

    }

    public static List<String> getAll() {

        List<String> problemas = new ArrayList<>();
        //TODO
        return problemas;

    }

}
