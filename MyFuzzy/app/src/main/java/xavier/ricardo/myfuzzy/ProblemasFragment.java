package xavier.ricardo.myfuzzy;

import android.database.sqlite.SQLiteDatabase;
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

import xavier.ricardo.myfuzzy.dao.ProblemaDao;
import xavier.ricardo.myfuzzy.exemplos.Gorjeta;
import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.utils.DbHelper;

public class ProblemasFragment extends Fragment {

    private static Problema problema;

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

        DbHelper mDbHelper = new DbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<String> problemas = ProblemaDao.getAll(db);
        db.close();

        ListView lvProblemas = view.findViewById(R.id.lvProblemas);
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, problemas);
        lvProblemas.setAdapter(adapter);
        lvProblemas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String nomeProblema = problemas.get(position);
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                problema = ProblemaDao.get(db, nomeProblema);
                db.close();
                NavHostFragment.findNavController(ProblemasFragment.this)
                        .navigate(R.id.ProblemasParaProblema);
            }
        });
    }

    public static Problema getProblema() {
        return problema;
    }

    public static void setProblema(Problema problema) {
        ProblemasFragment.problema = problema;
    }
}