package xavier.ricardo.myfuzzy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;

import com.google.android.material.snackbar.Snackbar;

import xavier.ricardo.myfuzzy.dao.TermoDao;
import xavier.ricardo.myfuzzy.dao.VariavelDao;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.TipoVariavel;
import xavier.ricardo.myfuzzy.tipos.Variavel;
import xavier.ricardo.myfuzzy.utils.DbHelper;

public class NovoTermoActivity extends AppCompatActivity {

    private String nomeProblema;
    private String nomeVariavel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_termo);

        Intent intent = getIntent();
        nomeProblema = intent.getStringExtra("problema");
        nomeVariavel = intent.getStringExtra("variavel");
        int fim = intent.getIntExtra("fim", 0);

        EditText edtA = findViewById(R.id.edtA);
        SeekBar sbA = findViewById(R.id.sbA);
        sbA.setMax(fim);
        sbA.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar arg0) {
            }

            public void onStartTrackingTouch(SeekBar arg0) {
            }

            public void onProgressChanged(SeekBar sb, int position, boolean arg2) {
                edtA.setText(String.valueOf(position));
            }
        });

        EditText edtB = findViewById(R.id.edtB);
        SeekBar sbB = findViewById(R.id.sbB);
        sbB.setMax(fim);
        sbB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar arg0) {
            }

            public void onStartTrackingTouch(SeekBar arg0) {
            }

            public void onProgressChanged(SeekBar sb, int position, boolean arg2) {
                edtB.setText(String.valueOf(position));
            }
        });

        EditText edtC = findViewById(R.id.edtC);
        SeekBar sbC = findViewById(R.id.sbC);
        sbC.setMax(fim);
        sbC.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar arg0) {
            }

            public void onStartTrackingTouch(SeekBar arg0) {
            }

            public void onProgressChanged(SeekBar sb, int position, boolean arg2) {
                edtC.setText(String.valueOf(position));
            }
        });

    }

    public void cancela(View v) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void confirma(View v) {

        EditText edtTermo = findViewById(R.id.edtTermo);
        String nome = edtTermo.getText().toString();
        if (nome.isEmpty()) {
            Snackbar.make(edtTermo, getResources().getString(R.string.termo_nao_informado), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            edtTermo.requestFocus();
            return;
        }

        SeekBar sbA = findViewById(R.id.sbA);
        SeekBar sbB = findViewById(R.id.sbB);
        SeekBar sbC = findViewById(R.id.sbC);
        if (sbA.getProgress() > sbB.getProgress() || sbB.getProgress() > sbC.getProgress()) {
            Snackbar.make(edtTermo, getResources().getString(R.string.abc), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            sbA.requestFocus();
            return;
        }

        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Termo termo = new Termo(nome, sbA.getProgress(), sbB.getProgress(), sbC.getProgress());
        long registro = TermoDao.insert(db, nomeProblema, nomeVariavel, termo);
        db.close();

        if (registro != -1) {
            setResult(Activity.RESULT_OK);
        } else {
            setResult(Activity.RESULT_CANCELED);
        }
        
        finish();
    }

}