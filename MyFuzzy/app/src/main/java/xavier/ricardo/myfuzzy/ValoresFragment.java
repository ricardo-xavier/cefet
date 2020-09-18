package xavier.ricardo.myfuzzy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        String variavel = getArguments().getString("variavel");
        super.onViewCreated(view, savedInstanceState);

        ListView lvValores = view.findViewById(R.id.lvValores);
        if (lvValores == null) {
            //TODO
        }

        List<Map<String, String>> valores = new ArrayList<>();

        Map<String, String> colunas = new HashMap<>();

        switch (variavel) {

            case "comida":
                colunas.put("valor", "pessima");
                colunas.put("detalhes", "triangular: 0 0 5");
                valores.add(colunas);

                colunas = new HashMap<>();
                colunas.put("valor", "comivel");
                colunas.put("detalhes", "triangular: 0 5 10");
                valores.add(colunas);

                colunas = new HashMap<>();
                colunas.put("valor", "deliciosa");
                colunas.put("detalhes", "triangular: 5 10 10");
                valores.add(colunas);
                break;

            case "servico":
                colunas.put("valor", "ruim");
                colunas.put("detalhes", "triangular: 0 0 5");
                valores.add(colunas);

                colunas = new HashMap<>();
                colunas.put("valor", "aceitavel");
                colunas.put("detalhes", "triangular: 0 5 10");
                valores.add(colunas);

                colunas = new HashMap<>();
                colunas.put("valor", "excelente");
                colunas.put("detalhes", "triangular: 5 10 10");
                valores.add(colunas);
                break;

            case "gorjeta":
                colunas.put("valor", "baixa");
                colunas.put("detalhes", "triangular: 0 0 12");
                valores.add(colunas);

                colunas = new HashMap<>();
                colunas.put("valor", "media");
                colunas.put("detalhes", "triangular: 0 12 24");
                valores.add(colunas);

                colunas = new HashMap<>();
                colunas.put("valor", "alta");
                colunas.put("detalhes", "triangular: 12 24 24");
                valores.add(colunas);
                break;

        }

        SimpleAdapter adapter = new SimpleAdapter(getContext(),
                valores,
                android.R.layout.simple_list_item_2,
                new String[] { "valor", "detalhes" },
                new int[] { android.R.id.text1, android.R.id.text2 });

        lvValores.setAdapter(adapter);
        lvValores.setOnItemClickListener((parent, v, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putString("valor", valores.get(position).get("valor"));
            NavHostFragment.findNavController(ValoresFragment.this)
                    .navigate(R.id.ValoresParaProblemas, bundle);
        });

    }
}