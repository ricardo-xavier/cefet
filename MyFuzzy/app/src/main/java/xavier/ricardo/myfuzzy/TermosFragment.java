package xavier.ricardo.myfuzzy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xavier.ricardo.myfuzzy.dao.TermoDao;
import xavier.ricardo.myfuzzy.dao.VariavelDao;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.Variavel;
import xavier.ricardo.myfuzzy.utils.DbHelper;

public class TermosFragment extends Fragment {

    private List<Map<String, String>> termos;
    private  Variavel variavel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_termos, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Problema problema = ProblemasFragment.getProblema();
        String nomeVariavel = getArguments().getString("variavel");

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NovoTermoActivity.class);
                intent.putExtra("problema", problema.getNome());
                intent.putExtra("variavel", nomeVariavel);
                startActivityForResult(intent, 1);
            }
        });

        TextView tvVariavel = view.findViewById(R.id.tvVariavel);
        tvVariavel.setText(tvVariavel.getText().toString().replace("$variavel", nomeVariavel));

        variavel = null;
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

        ListView lvTermos = view.findViewById(R.id.lvTermos);
        lvTermos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Map<String, String> aux = termos.get(position);
                String nomeTermo = aux.get("termo");
                new AlertDialog.Builder(getActivity())
                        .setTitle(nomeVariavel)
                        .setMessage(getResources().getString(R.string.excluir_termo))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                Problema problema = ProblemasFragment.getProblema();
                                DbHelper mDbHelper = new DbHelper(getActivity());
                                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                                TermoDao.delete(db, problema.getNome(), variavel.getNome(), nomeTermo);
                                db.close();

                                Snackbar.make(getView(), getResources().getString(R.string.termo_excluido), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                carrega(true);
                            }
                        }).setNegativeButton(android.R.string.no, null).show();
                return true;
            }
        });

        Button btnGrafico = view.findViewById(R.id.btnGrafico);
        btnGrafico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GraficoActivity.class);
                intent.putExtra("variavel", nomeVariavel);
                startActivity(intent);
            }
        });

        carrega(false);
    }

    public void carrega(boolean banco) {

        Problema problema = ProblemasFragment.getProblema();
        List<Termo> termosBd;
        if (banco) {
            DbHelper mDbHelper = new DbHelper(getActivity());
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            termosBd = TermoDao.getAll(db, problema.getNome(), variavel.getNome());
            db.close();
            variavel.setTermos(termosBd);
        } else {
            termosBd = variavel.getTermos();
        }

        termos = new ArrayList<>();
        for (Termo termo : termosBd) {
            Map<String, String> colunas = new HashMap<>();
            colunas.put("termo", termo.getNome());
            colunas.put("detalhes", "triangular: " + termo.getA() + " "
                    + termo.getB() + " " + termo.getC());
            termos.add(colunas);
        }

        ListView lvTermos = getView().findViewById(R.id.lvTermos);
        SimpleAdapter adapter = new SimpleAdapter(getContext(),
                termos,
                android.R.layout.simple_list_item_2,
                new String[] { "termo", "detalhes" },
                new int[] { android.R.id.text1, android.R.id.text2 });
        lvTermos.setAdapter(adapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        Snackbar.make(getView(), getResources().getString(R.string.variavel_adicionada), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        carrega(true);
    }
}
