package xavier.ricardo.myfuzzy.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import xavier.ricardo.myfuzzy.exemplos.Gorjeta;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
import xavier.ricardo.myfuzzy.tipos.Variavel;
import xavier.ricardo.myfuzzy.utils.DbHelper;

public class ProblemaDao {

    public static void insert(SQLiteDatabase db, Problema problema) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME", problema.getNome());
        db.insert("PROBLEMAS", null, contentValues);

    }

    public static List<String> getAll(SQLiteDatabase db) {

        List<String> problemas = new ArrayList<>();

        Cursor res = db.query("PROBLEMAS",
                new String[] { "NOME" },
                null,
                null,
                null,
                null,
                "NOME");
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String nome = res.getString(res.getColumnIndex("NOME"));
            problemas.add(nome);
            res.moveToNext();
        }

        res.close();
        return problemas;

    }

    public static Problema get(SQLiteDatabase db, String nome) {

        List<Variavel> variaveis = VariavelDao.getAll(db, nome);
        List<Regra> regras = RegraDao.getAll(db, nome, variaveis);
        Problema problema = new Problema(nome, variaveis, regras);
        return problema;

    }

}
