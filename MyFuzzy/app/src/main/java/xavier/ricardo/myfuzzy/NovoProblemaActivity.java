package xavier.ricardo.myfuzzy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import xavier.ricardo.myfuzzy.dao.ProblemaDao;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.utils.DbHelper;

public class NovoProblemaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_problema);
    }

    public void cancela(View v) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void confirma(View v) {

        EditText edtProblema = findViewById(R.id.edtProblema);
        String nome = edtProblema.getText().toString();
        if (nome.isEmpty()) {
            Snackbar.make(edtProblema, getResources().getString(R.string.problema_nao_informado), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            edtProblema.requestFocus();
            return;
        }

        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Problema problema = new Problema(nome, null, null);
        long registro = ProblemaDao.insert(db, problema);
        db.close();

        if (registro != -1) {
            setResult(Activity.RESULT_OK);
        } else {
            setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }

 }