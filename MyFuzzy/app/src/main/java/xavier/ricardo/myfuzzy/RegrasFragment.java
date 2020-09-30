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
import android.widget.ArrayAdapter;
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

import xavier.ricardo.myfuzzy.dao.RegraDao;
import xavier.ricardo.myfuzzy.dao.TermoDao;
import xavier.ricardo.myfuzzy.exemplos.Gorjeta;
import xavier.ricardo.myfuzzy.tipos.AntecedenteConsequente;
import xavier.ricardo.myfuzzy.tipos.Operador;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.TipoVariavel;
import xavier.ricardo.myfuzzy.tipos.Variavel;
import xavier.ricardo.myfuzzy.utils.DbHelper;

public class RegrasFragment extends Fragment {

    private List<Map<String, String>> regras;

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

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NovaRegraActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        Problema problema = ProblemasFragment.getProblema();

        List<Map<String, String>> regras = new ArrayList<>();
        for (Regra r : problema.getRegras()) {
            Map<String, String> colunas = new HashMap<>();
            colunas.put("condicoes", r.getExprAntecedente());
            colunas.put("resultado", r.getExprConsequente());
            regras.add(colunas);
        }

        ListView lvRegras = view.findViewById(R.id.lvRegras);
        lvRegras.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Map<String, String> aux = regras.get(position);
                String condicoes = aux.get("condicoes");
                int r = 0;
                for (Regra regra : problema.getRegras()) {
                    if (regra.getExprAntecedente().equals(condicoes)) {
                        r = regra.getId();
                        break;
                    }
                }
                final int idRegra = r;
                new AlertDialog.Builder(getActivity())
                        .setTitle("r" + idRegra)
                        .setMessage(getResources().getString(R.string.excluir_termo))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                Problema problema = ProblemasFragment.getProblema();
                                DbHelper mDbHelper = new DbHelper(getActivity());
                                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                                RegraDao.delete(db, problema.getNome(), idRegra);
                                db.close();

                                Snackbar.make(getView(), getResources().getString(R.string.regra_excluida), Snackbar.LENGTH_LONG)
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
        List<Regra> regrasBd;
        if (banco) {
            DbHelper mDbHelper = new DbHelper(getActivity());
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            regrasBd = RegraDao.getAll(db, problema.getNome(), problema.getVariaveis());
            db.close();
            problema.setRegras(regrasBd);
        } else {
            regrasBd = problema.getRegras();
        }

        regras = new ArrayList<>();
        for (Regra regra : regrasBd) {
            Map<String, String> colunas = new HashMap<>();
            colunas.put("condicoes", regra.getExprAntecedente());
            colunas.put("resultado", regra.getExprConsequente());
            regras.add(colunas);
        }

        ListView lvRegras = getView().findViewById(R.id.lvRegras);
        SimpleAdapter adapter = new SimpleAdapter(getContext(),
                regras,
                android.R.layout.simple_list_item_2,
                new String[] { "condicoes", "resultado" },
                new int[] { android.R.id.text1, android.R.id.text2 });
        lvRegras.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        Snackbar.make(getView(), getResources().getString(R.string.regra_adicionada), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        carrega(true);
    }

}