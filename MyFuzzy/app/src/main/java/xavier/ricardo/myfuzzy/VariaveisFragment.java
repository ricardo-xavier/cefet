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
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        String problema = getArguments().getString("problema");
        super.onViewCreated(view, savedInstanceState);

        ListView lvVariaveis = view.findViewById(R.id.lvVariaveis);
        if (lvVariaveis == null) {
            //TODO
        }

        List<Map<String, String>> variaveis = new ArrayList<>();

        Map<String, String> colunas = new HashMap<>();
        colunas.put("variavel", "comida");
        colunas.put("detalhes", "0 a 10 - 3 valores");
        variaveis.add(colunas);

        colunas = new HashMap<>();
        colunas.put("variavel", "servico");
        colunas.put("detalhes", "0 a 10 - 3 valores");
        variaveis.add(colunas);

        colunas = new HashMap<>();
        colunas.put("variavel", "gorjeta");
        colunas.put("detalhes", "0 a 24 - 3 valores");
        variaveis.add(colunas);

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