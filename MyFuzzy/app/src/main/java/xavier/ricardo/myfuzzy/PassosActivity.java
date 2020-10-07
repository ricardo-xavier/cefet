package xavier.ricardo.myfuzzy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import xavier.ricardo.myfuzzy.exemplos.Gorjeta;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.TipoVariavel;
import xavier.ricardo.myfuzzy.tipos.Variavel;
import xavier.ricardo.myfuzzy.utils.Centroid;

public class PassosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passos);

        Problema problema = ProblemasFragment.getProblema();

        StringBuilder html = new StringBuilder();

        html.append("<html>");
        html.append("<body>");

        html.append("<h5>" + MainActivity.getStringResources().getString(R.string.pertinencia) + "</h5>");
        for (Variavel variavel : problema.getVariaveis()) {

            if (variavel.getTipo() == TipoVariavel.CONSEQUENTE) {
                continue;
            }

            for (Termo termo : variavel.getTermos()) {
                html.append(String.format("&mu;<sub>%s</sub>(%d) = %.2f<br/>",
                        termo.getNome(), variavel.getCrisp(), termo.getPertinencia()));
            }
        }

        html.append("<h5>" + MainActivity.getStringResources().getString(R.string.regras_label) + "</h5>");

        int r = 1;
        for (Regra regra : problema.getRegras()) {
            html.append(String.format("w<sub>%d</sub> = %.2f<br/>",
                    r++, regra.getConsequente().getTermo().getPertinencia()));
        }

        html.append("<h5>Centroid:</h5>");
        html.append(Centroid.getTabela());

        html.append("</body>");
        html.append("</html>");

        WebView wvPassos = (WebView)this.findViewById(R.id.wvPassos);
        wvPassos.getSettings().setJavaScriptEnabled(false);
        wvPassos.loadData(html.toString(), "text/html; charset=utf-8", "UTF-8");
    }
}