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
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.utils.DbHelper;

public class NovaVariavelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_variavel);

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

        //TODO
        /*
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
        */
        finish();
    }

}