package xavier.ricardo.myfuzzy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.TipoVariavel;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class ProblemaFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_problema, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);

        Problema problema = ProblemasFragment.getProblema();

        TextView tvProblema = view.findViewById(R.id.tvProblema);
        tvProblema.setText(tvProblema.getText().toString().replace("$problema", problema.getNome()));

        Button btnVariaveis = view.findViewById(R.id.btnVariaveis);
        btnVariaveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProblemaFragment.this)
                        .navigate(R.id.ProblemaParaVariaveis);
            }
        });

        Button btnRegras = view.findViewById(R.id.btnRegras);
        btnRegras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean antecedente = false;
                boolean consequente = false;
                for (Variavel variavel : problema.getVariaveis()) {
                    if (variavel.getTipo() == TipoVariavel.ANTECEDENTE) {
                        antecedente = true;
                    } else {
                        consequente = true;
                    }
                }
                if (!antecedente || !consequente) {
                    Snackbar.make(getView(), getResources().getString(R.string.defina), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                NavHostFragment.findNavController(ProblemaFragment.this)
                        .navigate(R.id.ProblemaParaRegras);
            }
        });

        Button btnSimulador = view.findViewById(R.id.btnSimulador);
        btnSimulador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (problema.getRegras().size() == 0) {
                    Snackbar.make(getView(), getResources().getString(R.string.nenhuma_regra), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                NavHostFragment.findNavController(ProblemaFragment.this)
                        .navigate(R.id.ProblemaParaSimulador);
            }
        });

    }
}