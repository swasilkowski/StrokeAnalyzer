package com.example.asus.strokeanalyzer.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.View.Form.FormFragment;
import com.example.asus.strokeanalyzer.View.Nihss.NihssExaminationFragment;


/**
 * Klasa będąca rozszerzeniem klasy {@link Fragment}. Pozwala na wybór formularza, którego modyfikacji chce dokonać użytkownik.
 * Do stworzenia instancji tego fragmentu należy wykorzystać metodę {@link FormListFragment#newInstance}.
 *
 * @author Marta Marciszewicz
 */
public class FormListFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT_ID = "patient_id";

    //Id of patient whose data are going to be changed
    private Integer patientID;

    /**
     * Publiczny konstruktor bezparametrowy - jest wymagany, ale nie jest wykorzystywany.
     */
    public FormListFragment() {
        // Required empty public constructor
    }

    /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów.
     *
     * @param id id pacjenta, w którego profilu otworzony został widok fragmentu (dane pacjenta o tym id będą edytowane
     *           w formularzach)
     * @return nowa instancja fragmentu FormListFragment
     */
    public static FormListFragment newInstance(Integer id) {
        FormListFragment fragment = new FormListFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PATIENT_ID, id);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Metoda wołana w celu zainicjowania tworzenia fragmentu. Metoda ustawia wartości pól klasy przekazane
     * jako argumenty poprzez {@link Bundle}
     *
     * @param savedInstanceState poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            patientID = getArguments().getInt(ARG_PATIENT_ID);
        }
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
     * Metoda pozwalająca na zainicjowanie interfejsu użytkownika dla fragmentu.
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

        View view = inflater.inflate(R.layout.fragment_form_list, container, false);

        view.setBackgroundColor(getResources().getColor(R.color.buttonBackgroundColor, null));
        view.setClickable(true);

        final Button nihssBt= view.findViewById(R.id.nihssBt);
        nihssBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //move to proper form

                AppCompatActivity activity = ((AppCompatActivity)getActivity());
                if(activity!=null)
                {

                    FragmentManager _fmanager = activity.getSupportFragmentManager();
                    if(_fmanager!=null)
                    {
                        NihssExaminationFragment setFragment = NihssExaminationFragment.newInstance(patientID);
                        _fmanager.beginTransaction()
                                .replace(R.id.fragmentFrame, setFragment, null)
                                .addToBackStack(null)
                                .commit();
                    }


                }
            }
        });

        final Button treatmentBt= view.findViewById(R.id.treatmentBt);
        treatmentBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                printForm(Form.ThrombolyticTreatment);
            }
        });
        final Button iscoreBt= view.findViewById(R.id.iscoreBt);
        iscoreBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                printForm(Form.iScore);
            }
        });
        final Button demoandclinicBt= view.findViewById(R.id.dandcBt);
        demoandclinicBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                printForm(Form.DemographicAndClinic);
            }
        });

        return view;
    }

    /**
     * Metoda dokonująca przejścia do kolejnego fragmentu zawierającego formularz konkretnej skali wskazanej w parametrze.
     *
     * @param form formularz wybrany przez użytkownika
     */
    private void printForm(Form form)
    {
        //move to proper form
        FormFragment setFragment = FormFragment.newInstance(form, patientID, false, true);
        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        if(activity!=null)
        {
            FragmentManager _fmanager = activity.getSupportFragmentManager();
            if(_fmanager!=null)
                _fmanager.beginTransaction()
                        .replace(R.id.fragmentFrame, setFragment, null)
                        .addToBackStack(null)
                        .commit();
        }
    }

}
