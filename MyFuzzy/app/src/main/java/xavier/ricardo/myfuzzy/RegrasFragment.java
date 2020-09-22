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

import xavier.ricardo.myfuzzy.tipos.AntecedenteConsequente;
import xavier.ricardo.myfuzzy.tipos.Operador;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;

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

        Problema problema = Gorjeta.carrega();
        List<String> regras = new ArrayList<>();
        for (Regra r : problema.getRegras()) {
            StringBuilder expr = new StringBuilder();
            for (int a=0; a<r.getAntecedentes().size(); a++) {
                AntecedenteConsequente antecedente = r.getAntecedentes().get(a);
                expr.append("SE " + antecedente.getVariavel().getNome() + " é " + antecedente.getValor().getNome());
                Operador operador = r.getOperadores().get(a);
                if (operador != null) {
                    expr.append(operador == Operador.OR ? " OU " : " E ");
                }
            }
            expr.append(" ENTÃO " + r.getConsequente().getVariavel().getNome() + " é " + r.getConsequente().getValor().getNome());
            regras.add(expr.toString());
        }

        ListView lvRegras = view.findViewById(R.id.lvRegras);
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, regras);
        lvRegras.setAdapter(adapter);
    }
}