package xavier.ricardo.myfuzzy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xavier.ricardo.myfuzzy.exemplos.Gorjeta;
import xavier.ricardo.myfuzzy.tipos.AntecedenteConsequente;
import xavier.ricardo.myfuzzy.tipos.Operador;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class RegrasFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_regras, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        List<Map<String, String>> regras = new ArrayList<>();
        Problema problema = Gorjeta.carrega();
        for (Regra r : problema.getRegras()) {
            Map<String, String> colunas = new HashMap<>();

            StringBuilder expr = new StringBuilder();
            for (int a=0; a<r.getAntecedentes().size(); a++) {
                AntecedenteConsequente antecedente = r.getAntecedentes().get(a);
                expr.append("SE " + antecedente.getVariavel().getNome() + " é " + antecedente.getValor().getNome());
                Operador operador = r.getOperadores().get(a);
                if (operador != null) {
                    expr.append(operador == Operador.OR ? " OU " : " E ");
                }
            }
            colunas.put("condicoes", expr.toString());

            expr = new StringBuilder();
            expr.append(" ENTÃO " + r.getConsequente().getVariavel().getNome() + " é " + r.getConsequente().getValor().getNome());
            colunas.put("resultado", expr.toString());
            regras.add(colunas);
        }

        ListView lvRegras = view.findViewById(R.id.lvRegras);
        SimpleAdapter adapter = new SimpleAdapter(getContext(),
                regras,
                android.R.layout.simple_list_item_2,
                new String[] { "condicoes", "resultado" },
                new int[] { android.R.id.text1, android.R.id.text2 });
        lvRegras.setAdapter(adapter);
    }
}