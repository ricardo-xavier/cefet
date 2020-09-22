package xavier.ricardo.myfuzzy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Valor;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class ValoresFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_valores, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        String nomeVariavel = getArguments().getString("variavel");

        TextView tvVariavel = view.findViewById(R.id.tvVariavel);
        tvVariavel.setText(tvVariavel.getText().toString().replace("$variavel", nomeVariavel));

        Problema problema = Gorjeta.carrega();
        Variavel variavel = null;
        for (Variavel v : problema.getVariaveis()) {
            if (v.getNome().equals(nomeVariavel)) {
                variavel = v;
                break;
            }
        }

        EditText edtInicio = view.findViewById(R.id.edtInicio);
        edtInicio.setText(String.valueOf(variavel.getInicio()));

        EditText edtFim = view.findViewById(R.id.edtFim);
        edtFim.setText(String.valueOf(variavel.getFim()));

        ListView lvValores = view.findViewById(R.id.lvValores);
        Button btnGrafico = view.findViewById(R.id.btnGrafico);

        List<Map<String, String>> valores = new ArrayList<>();

        for (Valor valor : variavel.getValores()) {
            Map<String, String> colunas = new HashMap<>();
            colunas.put("valor", valor.getNome());
            colunas.put("detalhes", "triangular: " + valor.getInicio() + " "
                    + valor.getMaximo() + " " + valor.getFim());
            valores.add(colunas);
        }

        SimpleAdapter adapter = new SimpleAdapter(getContext(),
                valores,
                android.R.layout.simple_list_item_2,
                new String[] { "valor", "detalhes" },
                new int[] { android.R.id.text1, android.R.id.text2 });

        lvValores.setAdapter(adapter);

        btnGrafico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GraficoActivity.class);
                intent.putExtra("variavel", nomeVariavel);
                startActivity(intent);
            }
        });

    }
}