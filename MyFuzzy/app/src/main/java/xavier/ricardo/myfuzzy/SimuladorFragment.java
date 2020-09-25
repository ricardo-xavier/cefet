package xavier.ricardo.myfuzzy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import xavier.ricardo.myfuzzy.exemplos.Gorjeta;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Variavel;
import xavier.ricardo.myfuzzy.utils.Simulador;

public class SimuladorFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simulador, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);

        String nomeProblema = getArguments().getString("problema");

        Problema problema = Gorjeta.carrega();

        TextView tvProblema = view.findViewById(R.id.tvProblema);
        tvProblema.setText(tvProblema.getText().toString().replace("$problema", nomeProblema));

        LinearLayout llEntrada = view.findViewById(R.id.llEntrada);

        for (int v=0; v<problema.getVariaveis().size()-1; v++) {
            Variavel variavel = problema.getVariaveis().get(v);

            LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tvVariavel = new TextView(getActivity());
            tvVariavel.setLayoutParams(paramsText);
            tvVariavel.setText(variavel.getNome());
            llEntrada.addView(tvVariavel);

            LinearLayout.LayoutParams paramsEdit = new LinearLayout.LayoutParams(
                    100,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            EditText etValor = new EditText(getActivity());
            etValor.setLayoutParams(paramsEdit);
            etValor.setEnabled(false);
            etValor.setText("0");
            llEntrada.addView(etValor);

            LinearLayout.LayoutParams paramsSeek = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            SeekBar sbValor = new SeekBar(getActivity());
            sbValor.setLayoutParams(paramsSeek);
            sbValor.setMax(variavel.getFim());
            llEntrada.addView(sbValor);

            sbValor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                public void onStopTrackingTouch(SeekBar arg0) {
                }

                public void onStartTrackingTouch(SeekBar arg0) {
                }

                public void onProgressChanged(SeekBar sb, int position, boolean arg2) {
                    etValor.setText(String.valueOf(position));
                    variavel.setCrisp(position);
                }
            });

        }

        LinearLayout llSaida = view.findViewById(R.id.llSaida);

        Variavel variavel = problema.getVariaveis().get(problema.getVariaveis().size()-1);

        LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tvVariavel = new TextView(getActivity());
        tvVariavel.setLayoutParams(paramsText);
        tvVariavel.setText(variavel.getNome());
        llSaida.addView(tvVariavel);

        LinearLayout.LayoutParams paramsEdit = new LinearLayout.LayoutParams(
                100,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        EditText etValor = new EditText(getActivity());
        etValor.setLayoutParams(paramsEdit);
        etValor.setEnabled(false);
        etValor.setText("0");
        llSaida.addView(etValor);

        LinearLayout.LayoutParams paramsSeek = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        SeekBar sbValor = new SeekBar(getActivity());
        sbValor.setLayoutParams(paramsSeek);
        sbValor.setMax(variavel.getFim());
        sbValor.setEnabled(false);
        llSaida.addView(sbValor);

        Button btnSimular = view.findViewById(R.id.btnSimular);
        btnSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double crisp = Simulador.sumula(problema);
                sbValor.setProgress((int) crisp);
                etValor.setText(String.valueOf((int) crisp));
            }
        });

    }
}