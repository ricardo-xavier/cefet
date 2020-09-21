package xavier.ricardo.myfuzzy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

import xavier.ricardo.myfuzzy.tipos.Problema;

public class ProblemasFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_problemas, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Problema problema = Gorjeta.carrega();
        List<String> problemas = new ArrayList<>();
        problemas.add(problema.getNome());

        ListView lvProblemas = view.findViewById(R.id.lvProblemas);
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, problemas);
        lvProblemas.setAdapter(adapter);
        lvProblemas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("problema", problemas.get(position));
                NavHostFragment.findNavController(ProblemasFragment.this)
                        .navigate(R.id.ProblemasParaProblema, bundle);
            }
        });
    }
}