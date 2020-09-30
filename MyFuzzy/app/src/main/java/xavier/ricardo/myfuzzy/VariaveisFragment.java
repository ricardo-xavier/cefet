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
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xavier.ricardo.myfuzzy.dao.ProblemaDao;
import xavier.ricardo.myfuzzy.dao.VariavelDao;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Variavel;
import xavier.ricardo.myfuzzy.utils.DbHelper;

public class VariaveisFragment extends Fragment {

    private List<Map<String, String>> variaveis;

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

        ListView lvVariaveis = view.findViewById(R.id.lvVariaveis);
        lvVariaveis.setOnItemClickListener((parent, v, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putString("variavel", variaveis.get(position).get("variavel"));
            NavHostFragment.findNavController(VariaveisFragment.this)
                    .navigate(R.id.VariaveisParaValores, bundle);
        });

        lvVariaveis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Map<String, String> aux = variaveis.get(position);
                String nomeVariavel = aux.get("variavel");
                new AlertDialog.Builder(getActivity())
                        .setTitle(nomeVariavel)
                        .setMessage(getResources().getString(R.string.excluir_variavel))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                Problema problema = ProblemasFragment.getProblema();
                                DbHelper mDbHelper = new DbHelper(getActivity());
                                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                                VariavelDao.delete(db, problema.getNome(), nomeVariavel);
                                db.close();

                                Snackbar.make(getView(), getResources().getString(R.string.variavel_excluida), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                carrega(true);
                            }
                        }).setNegativeButton(android.R.string.no, null).show();
                return true;
            }
        });

        carrega(false);
    }

    public void carrega(boolean banco) {

        Problema problema = ProblemasFragment.getProblema();
        List<Variavel> variaveisBd;
        if (banco) {
            DbHelper mDbHelper = new DbHelper(getActivity());
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            variaveisBd = VariavelDao.getAll(db, problema.getNome());
            db.close();
            problema.setVariaveis(variaveisBd);
        } else {
            variaveisBd = problema.getVariaveis();
        }

        variaveis = new ArrayList<>();
        for (Variavel variavel : variaveisBd) {
            Map<String, String> colunas = new HashMap<>();
            colunas.put("variavel", variavel.getNome());
            colunas.put("detalhes", "universo: " + variavel.getInicio() + " - " + variavel.getFim());
            variaveis.add(colunas);
        }

        ListView lvVariaveis = getView().findViewById(R.id.lvVariaveis);
        SimpleAdapter adapter = new SimpleAdapter(getContext(),
                variaveis,
                android.R.layout.simple_list_item_2,
                new String[] { "variavel", "detalhes" },
                new int[] { android.R.id.text1, android.R.id.text2 });
        lvVariaveis.setAdapter(adapter);

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