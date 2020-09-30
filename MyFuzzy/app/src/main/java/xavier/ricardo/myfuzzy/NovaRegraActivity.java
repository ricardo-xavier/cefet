package xavier.ricardo.myfuzzy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import xavier.ricardo.myfuzzy.dao.AntecedenteConsequenteDao;
import xavier.ricardo.myfuzzy.dao.RegraDao;
import xavier.ricardo.myfuzzy.tipos.AntecedenteConsequente;
import xavier.ricardo.myfuzzy.tipos.Operador;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.TipoVariavel;
import xavier.ricardo.myfuzzy.tipos.Variavel;
import xavier.ricardo.myfuzzy.utils.DbHelper;

public class NovaRegraActivity extends AppCompatActivity {

    private Button btnSe;
    private Button btnEh;
    private Button btnOu;
    private Button btnE;
    private Button btnEntao;
    private Button btnVariavel;
    private Button btnTermo;
    private EditText edtExpressao;

    private List<String> nomesAntecedentes;
    private String nomeConsequente;
    private List<String> termos;
    private String nomeVariavel;
    private Problema problema;

    private Regra regra;
    private List<AntecedenteConsequente> antecedentes;
    private AntecedenteConsequente antecedente;
    private AntecedenteConsequente consequente;
    private List<Operador> operadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_regra);

        problema = ProblemasFragment.getProblema();
        nomesAntecedentes = new ArrayList<>();
        for (Variavel variavel : problema.getVariaveis()) {
            if (variavel.getTipo() == TipoVariavel.ANTECEDENTE) {
                nomesAntecedentes.add(variavel.getNome());
            } else {
                nomeConsequente = variavel.getNome();
            }
        }

        btnSe = findViewById(R.id.btnSe);
        btnEh = findViewById(R.id.btnEh);
        btnOu = findViewById(R.id.btnOu);
        btnE = findViewById(R.id.btnE);
        btnEntao = findViewById(R.id.btnEntao);
        btnVariavel = findViewById(R.id.btnVariavel);
        btnTermo = findViewById(R.id.btnTermo);
        edtExpressao = findViewById(R.id.edtExpressao);
        Button btnConfirma = findViewById(R.id.btnConfirma);
        btnConfirma.setVisibility(View.INVISIBLE);

        desativa();
        ativa(btnSe);

        antecedentes = new ArrayList<>();
        consequente = new AntecedenteConsequente();
        operadores = new ArrayList<>();
        regra = new Regra(antecedentes, operadores, consequente);
    }

    private void desativa() {
        btnSe.setEnabled(false);
        btnSe.setBackgroundColor(Color.RED);
        btnEh.setEnabled(false);
        btnEh.setBackgroundColor(Color.RED);
        btnOu.setEnabled(false);
        btnOu.setBackgroundColor(Color.RED);
        btnE.setEnabled(false);
        btnE.setBackgroundColor(Color.RED);
        btnEntao.setEnabled(false);
        btnEntao.setBackgroundColor(Color.RED);
        btnVariavel.setEnabled(false);
        btnVariavel.setBackgroundColor(Color.RED);
        btnTermo.setEnabled(false);
        btnTermo.setBackgroundColor(Color.RED);
    }

    private void ativa(Button btn) {
        btn.setEnabled(true);
        btn.setBackgroundColor(Color.GREEN);
    }

    private void adiciona(String s) {
        String texto = edtExpressao.getText().toString();
        texto += s + " ";
        edtExpressao.setText(texto);
    }

    public void se(View v) {
        adiciona("SE");
        desativa();
        ativa(btnVariavel);
        Spinner spVariavel = findViewById(R.id.spVariavel);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nomesAntecedentes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVariavel.setAdapter(dataAdapter);
        antecedente = new AntecedenteConsequente();
    }

    public void eh(View v) {
        adiciona("É");
        desativa();
        ativa(btnTermo);
        for (Variavel variavel : problema.getVariaveis()) {
            if (variavel.getNome().equals(nomeVariavel)) {
                termos = new ArrayList<>();
                for (Termo termo : variavel.getTermos()) {
                    termos.add(termo.getNome());
                }
                Spinner spTermo = findViewById(R.id.spTermo);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, termos);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spTermo.setAdapter(dataAdapter);
                break;
            }
        }
    }

    public void e(View v) {
        antecedentes.add(antecedente);
        operadores.add(Operador.AND);
        adiciona("E");
        desativa();
        ativa(btnVariavel);
    }

    public void ou(View v) {
        antecedentes.add(antecedente);
        operadores.add(Operador.OR);
        adiciona("OU");
        desativa();
        ativa(btnVariavel);
    }

    public void entao(View v) {
        antecedentes.add(antecedente);
        operadores.add(null);
        adiciona("ENTÃO");
        desativa();
        ativa(btnVariavel);
        Spinner spVariavel = findViewById(R.id.spVariavel);
        List<String> consequentes = new ArrayList<>();
        consequentes.add(nomeConsequente);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, consequentes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVariavel.setAdapter(dataAdapter);
    }

    public void adVar(View v) {
        Spinner spVariavel = findViewById(R.id.spVariavel);
        int pos = spVariavel.getSelectedItemPosition();
        if (pos < 0) {
            return;
        }
        if (edtExpressao.getText().toString().contains("ENTÃO"))  {
            nomeVariavel = nomeConsequente;
        } else {
            nomeVariavel = nomesAntecedentes.get(pos);
        }
        Variavel variavel = null;
        for (Variavel var : problema.getVariaveis()) {
            if (var.getNome().equals(nomeVariavel)) {
                variavel = var;
                break;
            }
        }
        if (edtExpressao.getText().toString().contains("ENTÃO"))  {
            consequente.setVariavel(variavel);
        } else {
            antecedente.setVariavel(variavel);
        }
        adiciona(nomeVariavel);
        desativa();
        ativa(btnEh);
    }

    public void adTermo(View v) {
        Spinner spTermo = findViewById(R.id.spTermo);
        int pos = spTermo.getSelectedItemPosition();
        if (pos < 0) {
            return;
        }
        String nomeTermo = termos.get(pos);
        AntecedenteConsequente ac = null;
        if (edtExpressao.getText().toString().contains("ENTÃO"))  {
            ac = consequente;
        } else {
            ac = antecedente;
        }
        Variavel variavel = ac.getVariavel();
        Termo termo = null;
        for (Termo t : variavel.getTermos()) {
            if (t.getNome().equals(nomeTermo)) {
                termo = t;
                break;
            }
        }
        ac.setTermo(termo);
        adiciona(nomeTermo);
        desativa();
        if (edtExpressao.getText().toString().contains("ENTÃO")) {
            Button btnConfirma = findViewById(R.id.btnConfirma);
            btnConfirma.setVisibility(View.VISIBLE);
        } else {
            ativa(btnE);
            ativa(btnOu);
            ativa(btnEntao);
        }
    }

    public void cancela(View v) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void confirma(View v) {

        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int r = problema.getRegras().size() + 1;
        for (int a=0; a<regra.getAntecedentes().size(); a++) {
            AntecedenteConsequenteDao.insert(db, problema.getNome(), r, "A", a+1,
                    regra.getAntecedentes().get(a), regra.getOperadores().get(a));
        }
        AntecedenteConsequenteDao.insert(db, problema.getNome(), r, "C", 1,
                regra.getConsequente(), null);

        setResult(Activity.RESULT_OK);
        finish();
    }

}