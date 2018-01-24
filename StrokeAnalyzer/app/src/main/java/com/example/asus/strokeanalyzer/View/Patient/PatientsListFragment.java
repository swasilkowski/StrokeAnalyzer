package com.example.asus.strokeanalyzer.View.Patient;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.DialogWindows.PatientsListActionFragment;
import com.example.asus.strokeanalyzer.View.Helpers.LineDecoration;
import com.example.asus.strokeanalyzer.View.Helpers.ClickListener;
import com.example.asus.strokeanalyzer.View.Helpers.RecyclerClickListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa będąca podklasą {@link Fragment}. Pozwala na wyświetlenie listy pacjentów, których
 * profile przechowywane są w bazie danych aplikacji.
 */
public class PatientsListFragment extends Fragment  {

    private RecyclerView recyclerView;
    private PatientAdapter pAdapter;
    private List<Patient> patients = new ArrayList<>();
    PatientService patientService;

    /**
     * Metoda wołana w celu zainicjowania tworzenia fragmentu.
     *
     * @param savedInstanceState poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Metoda pozwalająca na zainicjowanie interfejsu użytkownika dla fragmentu. Funkcja oprócz wstrzyknięcia widoku
     * fragmentu inicjalizuje obiekt klasy RecyclerView odpowiedzialny za prezentację listy pacjentów przy wykorzystaniu
     * klasy {@link PatientAdapter}
     *
     * @param inflater obiekt umożliwiający wstrzyknięcie widoku do fragmentu
     * @param container widok-rodzic, do którego powinien być podpięty UI fragmentu
     * @param savedInstanceState poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     * @return (View) widok interfejsu użytkownika fragmentu (może przyjąć wartość null)
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_patients_list, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.colorBackground, null));

        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity!=null)
        {
            ActionBar bar =  activity.getSupportActionBar();
            if(bar!=null)
            {
                bar.show();
                bar.setTitle("Wybierz pacjenta");
            }
        }

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            patientService = new PatientService(getContext());

            //get patients list from database
            patients = patientService.GetPatientsList();

            pAdapter = new PatientAdapter(patients,context);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(pAdapter);
            recyclerView.addItemDecoration(new LineDecoration(this.getContext()));

            recyclerView.addOnItemTouchListener(new RecyclerClickListener( getActivity().getApplicationContext(), new ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    // Storing data into bundle
                    final Patient patient = patients.get(position);

                    //print dialog with actions for patient
                    PatientsListActionFragment.DeleteListener listener = new PatientsListActionFragment.DeleteListener() {
                        @Override
                        public void patientDeleted(final int patientID) {

                            Patient tmp = new Patient();
                            tmp.Id = patientID;
                            patients.remove(patients.indexOf(tmp));
                            pAdapter.notifyDataSetChanged();
                        }
                    };
                    DialogFragment dialog =PatientsListActionFragment.newInstance( patient.Id, listener);
                    dialog.show(getActivity().getSupportFragmentManager(), "PatientsListActionFragment");
                }
            }));
        }

        return recyclerView;
    }

    /**
     * Metoda wywoływana w momencie, gdy fragment odłączany jest od aktywności. Aplikacja wykorzystuje tę metodę
     * do kontrolowania elementu ActionBar.
     */
    @Override
    public void onDetach() {
        super.onDetach();

        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        if(activity!=null)
        {
            ActionBar bar = activity.getSupportActionBar();
            if(bar!=null)
                bar.hide();
        }
    }

}
