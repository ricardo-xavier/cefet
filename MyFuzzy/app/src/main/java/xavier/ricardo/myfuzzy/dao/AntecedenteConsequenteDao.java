package xavier.ricardo.myfuzzy.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import xavier.ricardo.myfuzzy.tipos.AntecedenteConsequente;
import xavier.ricardo.myfuzzy.tipos.Operador;

public class AntecedenteConsequenteDao {

    public static void insert(SQLiteDatabase db, String problema, String tipo, int seq,
                              AntecedenteConsequente ac, Operador op) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("PROBLEMA", problema);
        contentValues.put("TIPO", tipo);
        contentValues.put("SEQ", seq);
        contentValues.put("VARIAVEL", ac.getVariavel().getNome());
        contentValues.put("TERMO", ac.getTermo().getNome());
        if (op != null) {
            contentValues.put("OPERADOR", op == Operador.AND ? "&" : "|");
        }
        db.insert("ANTECEDENTES_CONSEQUENTES", null, contentValues);

    }

}
