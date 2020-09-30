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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import xavier.ricardo.myfuzzy.dao.ProblemaDao;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.utils.DbHelper;

public class ProblemasFragment extends Fragment {

    private static Problema problema;
    private List<String> problemas;

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

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NovoProblemaActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        ListView lvProblemas = view.findViewById(R.id.lvProblemas);
        carrega();
        lvProblemas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                DbHelper mDbHelper = new DbHelper(getActivity());
                String nomeProblema = problemas.get(position);
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                problema = ProblemaDao.get(db, nomeProblema);
                db.close();

                NavHostFragment.findNavController(ProblemasFragment.this)
                        .navigate(R.id.ProblemasParaProblema);
            }
        });

        lvProblemas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                String nomeProblema = problemas.get(position);
                new AlertDialog.Builder(getActivity())
                        .setTitle(nomeProblema)
                        .setMessage(getResources().getString(R.string.excluir_problema))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                DbHelper mDbHelper = new DbHelper(getActivity());
                                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                                ProblemaDao.delete(db, nomeProblema);
                                db.close();

                                Snackbar.make(getView(), getResources().getString(R.string.problema_excluido), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                carrega();
                            }
                        }).setNegativeButton(android.R.string.no, null).show();

                return true;
            }
        });
    }

    public void carrega() {

        DbHelper mDbHelper = new DbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        problemas = ProblemaDao.getAll(db);
        db.close();

        ListView lvProblemas = getView().findViewById(R.id.lvProblemas);
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, problemas);
        lvProblemas.setAdapter(adapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
           return;
        }
        Snackbar.make(getView(), getResources().getString(R.string.problema_adicionado), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        carrega();;
    }

    public static Problema getProblema() {
        return problema;
    }

    public static void setProblema(Problema problema) {
        ProblemasFragment.problema = problema;
    }
}