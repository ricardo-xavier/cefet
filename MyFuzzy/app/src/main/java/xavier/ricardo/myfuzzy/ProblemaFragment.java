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

import xavier.ricardo.myfuzzy.tipos.Problema;

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

        String nomeProblema = getArguments().getString("problema");

        TextView tvProblema = view.findViewById(R.id.tvProblema);
        tvProblema.setText(tvProblema.getText().toString().replace("$problema", nomeProblema));

        Button btnVariaveis = view.findViewById(R.id.btnVariaveis);
        btnVariaveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("problema", nomeProblema);
                NavHostFragment.findNavController(ProblemaFragment.this)
                        .navigate(R.id.ProblemaParaVariaveis, bundle);
            }
        });
    }
}