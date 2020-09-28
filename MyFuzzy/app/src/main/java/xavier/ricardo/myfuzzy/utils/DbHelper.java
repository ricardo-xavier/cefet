package xavier.ricardo.myfuzzy.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {


    public DbHelper(@Nullable Context context) {
        super(context, "myfuzzy", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table PROBLEMAS ("
                + "NOME char(20) not null, "
                + "primary key (NOME))");

        sqLiteDatabase.execSQL("create table VARIAVEIS ("
                + "PROBLEMA char(20) not null, "
                + "NOME char(20) not null, "
                + "TIPO char(1) not null, "
                + "INICIO integer not null, "
                + "FIM integer not null, "
                + "primary key (PROBLEMA, NOME))");

        sqLiteDatabase.execSQL("create table TERMOS ("
                + "PROBLEMA char(20) not null, "
                + "VARIAVEL char(20) not null, "
                + "NOME char(20) not null, "
                + "A integer, "
                + "B integer, "
                + "C integer, "
                + "D integer, "
                + "primary key (PROBLEMA, VARIAVEL, NOME))");

        sqLiteDatabase.execSQL("create table ANTECEDENTES_CONSEQUENTES ("
                + "PROBLEMA char(20) not null, "
                + "TIPO char(1) not null, "
                + "SEQ integer not null, "
                + "VARIAVEL char(20) not null, "
                + "TERMO char(20) not null, "
                + "OPERADOR char(1) not null, "
                + "primary key (PROBLEMA, TIPO, SEQ))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
