package com.example.asus.strokeanalyzer.View.Nihss;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.NihssExamination;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.Helpers.LineDecoration;
import com.example.asus.strokeanalyzer.View.Form.FormFragment;
import com.example.asus.strokeanalyzer.View.Helpers.ClickListener;
import com.example.asus.strokeanalyzer.View.Helpers.RecyclerClickListener;
import java.util.Collections;
import java.util.List;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

/**
 * Klasa będąca rozszerzeniem klasy {@link Fragment}. Pozwala na wyświetlenie listy badań w skali NIHSS pacjenta.
 * Do stworzenia instancji tego fragmentu należy wykorzystać metodę {@link NihssExaminationFragment#newInstance}.
 *
 * @author Marta Marciszewicz
 */
public class NihssExaminationFragment extends Fragment {

    /**
     * Zmienna przechowująca klucz, który pozwala na zapisanie i pobranie danych z obiektu klasy
     * {@link Bundle} wykorzystywanego do przekazania parametrów fragmentu.
     */
    private static final String ARG_PATIENT_ID = "patient_id";

    /**
     * Obiekt stanowiący kontener do wyświetlania informacji o przeprowadzonych badaniach w skali NIHSS.
     */
    private RecyclerView recyclerView;
    /**
     * Id pacjenta, którego badania w skali NIHSS mają zostać wyświetlone.
     */
    private int patientID;
    /**
     * Aktywność związana z fragmentem.
     */
    private FragmentActivity activity;

    /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów.
     *
     * @param patientID id pacjenta, którego badania w skali NIHSS mają zostać wyświetlone
     * @return nowa instancja fragmentu NihssExaminationFragment
     */
    public static NihssExaminationFragment newInstance(long patientID) {
        NihssExaminationFragment fragment = new NihssExaminationFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PATIENT_ID, (int)patientID);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Metoda wołana w celu zainicjowania tworzenia fragmentu. Metoda ustawia wartości pól klasy przekazane
     * jako argumenty poprzez {@link Bundle}. Dodatkowo zapisuje obiekt aktywności fragmentu.
     *
     * @param savedInstanceState poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            patientID = getArguments().getInt(ARG_PATIENT_ID);
        }
        activity = getActivity();
    }

    /**
     * Metoda wywoływana w momencie, gdy fragment jest wyświetlany użytkownikowi. Aplikacja wykorzystuje tę metodę
     * do kontrolowania elementu ActionBar.
     */
    @Override
    public void onResume()
    {
        super.onResume();
        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        if(activity!=null)
        {
            ActionBar bar = activity.getSupportActionBar();
            if(bar!=null)
                bar.show();
        }
    }

    /**
     * Metoda pozwalająca na zainicjowanie interfejsu użytkownika dla fragmentu. Funkcja oprócz wstrzyknięcia widoku
     * fragmentu inicjalizuje obiekt klasy RecyclerView odpowiedzialny za prezentację listy badań skali NIHSS przy wykorzystaniu
     * klasy {@link NihssAdapter}.
     *
     * @param inflater obiekt umożliwiający wstrzyknięcie widoku do fragmentu
     * @param container widok-rodzic, do którego powinien być podpięty UI fragmentu
     * @param savedInstanceState poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     * @return widok interfejsu użytkownika fragmentu (może przyjąć wartość null)
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nihss_examination, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.colorBackground, null));

        //set action bar
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity!=null)
        {
            ActionBar bar =  activity.getSupportActionBar();
            if(bar!=null)
            {
                bar.setTitle("Badania NIHSS");
            }
        }

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            PatientService patientService = new PatientService(getContext());
            final List<NihssExamination> examinations = patientService.GetPatientById(patientID).getNihssHistory();
            Collections.reverse(examinations);

            NihssAdapter nAdapter = new NihssAdapter(examinations, context);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(nAdapter);
            recyclerView.addItemDecoration(new LineDecoration(this.getContext()));
            recyclerView.addOnItemTouchListener(new RecyclerClickListener( getActivity().getApplicationContext(), new ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    FormFragment setFragment;
                    if(position==0)
                        setFragment= FormFragment.newInstance(Form.NIHSS, patientID,  false,true, examinations.get(position));
                    else
                        setFragment= FormFragment.newInstance(Form.NIHSS, patientID,  false,false, examinations.get(position));

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentFrame, setFragment, null)
                            .addToBackStack(null)
                            .commit();

                }
            }));
        }

        return recyclerView;
    }

    /**
     * Metoda umożliwiająca zainicjowanie standardowego menu aktywności.
     *
     * @param menu obiekt klasy Menu, w którym powinna znajdować się definicja menu dla
     *             tego fragmentu
     * @param inflater obiekt klasy MenuInflater pozwalający na pozyskanie menu z zasobów aplikacji
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.examination, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    /**
     * Metoda wywoływana w momencie wyboru przez użytkownika jednej z opcji w menu fragmentu.
     * Funkcja jest odpowiedzialna za przejście do fragmentu z formularzem skali NIHSS.
     *
     * @param item elementu menu, który został wybrany przez użytkownika
     * @return false - jeżeli element menu ma być przetworzony standardowo;
     *          true - jeżeli element menu został obsłużony wewnątrz funkcji
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {

            if(activity!=null)
            {
                //move to proper form
                FormFragment setFragment = FormFragment.newInstance(Form.NIHSS,patientID, true, true);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentFrame, setFragment, null)
                        .addToBackStack(null)
                        .commit();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Metoda wywoływana w momencie, gdy fragment przestaje być wykorzystywany. Aplikacja wykorzystuje tę metodę
     * do usunięcia dodatkowego fragmentu ze stosu fragmentów.
     */
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        FragmentManager manager = getFragmentManager();
        if(manager!=null)
        {
            manager.popBackStack("forms_list", POP_BACK_STACK_INCLUSIVE);
        }

    }

}
