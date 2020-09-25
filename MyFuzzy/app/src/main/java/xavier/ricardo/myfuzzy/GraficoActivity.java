package xavier.ricardo.myfuzzy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import xavier.ricardo.myfuzzy.exemplos.Gorjeta;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class GraficoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);

        Intent intent = getIntent();
        String nomeVariavel = intent.getStringExtra("variavel");
        Problema problema = Gorjeta.carrega();
        Variavel variavel = null;
        for (Variavel v : problema.getVariaveis()) {
            if (v.getNome().equals(nomeVariavel)) {
                variavel = v;
                break;
            }
        }

        DrawView vwGrafico = findViewById(R.id.vwGrafico);
        vwGrafico.setVariavel(variavel);
    }
}