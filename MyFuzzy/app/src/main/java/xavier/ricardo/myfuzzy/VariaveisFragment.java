package xavier.ricardo.myfuzzy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xavier.ricardo.myfuzzy.exemplos.Gorjeta;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class VariaveisFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_variaveis, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NovaVariavelActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        List<Map<String, String>> variaveis = new ArrayList<>();
        Problema problema = ProblemasFragment.getProblema();
        for (Variavel variavel : problema.getVariaveis()) {
            Map<String, String> colunas = new HashMap<>();
            colunas.put("variavel", variavel.getNome());
            colunas.put("detalhes", "universo: " + variavel.getInicio() + " - " + variavel.getFim());
            variaveis.add(colunas);
        }

        ListView lvVariaveis = view.findViewById(R.id.lvVariaveis);
        SimpleAdapter adapter = new SimpleAdapter(getContext(),
                variaveis,
                android.R.layout.simple_list_item_2,
                new String[] { "variavel", "detalhes" },
                new int[] { android.R.id.text1, android.R.id.text2 });

        lvVariaveis.setAdapter(adapter);
        lvVariaveis.setOnItemClickListener((parent, v, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putString("variavel", variaveis.get(position).get("variavel"));
            NavHostFragment.findNavController(VariaveisFragment.this)
                    .navigate(R.id.VariaveisParaValores, bundle);
        });

    }
}