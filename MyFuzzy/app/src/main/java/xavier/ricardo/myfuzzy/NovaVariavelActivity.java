package xavier.ricardo.myfuzzy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.google.android.material.snackbar.Snackbar;

import xavier.ricardo.myfuzzy.dao.ProblemaDao;
import xavier.ricardo.myfuzzy.dao.VariavelDao;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.TipoVariavel;
import xavier.ricardo.myfuzzy.tipos.Variavel;
import xavier.ricardo.myfuzzy.utils.DbHelper;

public class NovaVariavelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_variavel);

        Problema problema = ProblemasFragment.getProblema();
        for (Variavel variavel : problema.getVariaveis()) {
            if (variavel.getTipo() == TipoVariavel.CONSEQUENTE) {
                RadioButton rbConsequente = findViewById(R.id.rbConsequente);
                rbConsequente.setEnabled(false);
                break;
            }
        }

        EditText edtInicio = findViewById(R.id.edtInicio);
        SeekBar sbInicio = findViewById(R.id.sbInicio);
        sbInicio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar arg0) {
            }

            public void onStartTrackingTouch(SeekBar arg0) {
            }

            public void onProgressChanged(SeekBar sb, int position, boolean arg2) {
                edtInicio.setText(String.valueOf(position));
            }
        });

        EditText edtFim = findViewById(R.id.edtFim);
        SeekBar sbFim = findViewById(R.id.sbFim);
        sbFim.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar arg0) {
            }

            public void onStartTrackingTouch(SeekBar arg0) {
            }

            public void onProgressChanged(SeekBar sb, int position, boolean arg2) {
                edtFim.setText(String.valueOf(position));
            }
        });

    }

    public void cancela(View v) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void confirma(View v) {

        EditText edtVariavel = findViewById(R.id.edtVariavel);
        String nome = edtVariavel.getText().toString();
        if (nome.isEmpty()) {
            Snackbar.make(edtVariavel, getResources().getString(R.string.variavel_nao_informada), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            edtVariavel.requestFocus();
            return;
        }

        SeekBar sbInicio = findViewById(R.id.sbInicio);
        SeekBar sbFim = findViewById(R.id.sbFim);
        if (sbFim.getProgress() <= sbInicio.getProgress()) {
            Snackbar.make(edtVariavel, getResources().getString(R.string.fim_maior_inicio), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            sbFim.requestFocus();
            return;
        }

        RadioButton rbConsequente = findViewById(R.id.rbConsequente);
        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Variavel variavel = new Variavel(nome, sbInicio.getProgress(), sbFim.getProgress(),
                rbConsequente.isChecked() ? TipoVariavel.CONSEQUENTE : TipoVariavel.ANTECEDENTE);
        Problema problema = ProblemasFragment.getProblema();
        long registro = VariavelDao.insert(db, problema.getNome(), variavel);
        db.close();

        if (registro != -1) {
            setResult(Activity.RESULT_OK);
        } else {
            setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }

}