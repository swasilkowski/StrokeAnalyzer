package com.example.asus.strokeanalyzer.View.DialogWindows;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.PatientProfileFragment;

/**
 * Klasa będąca podklasą {@link DialogFragment}. Pozwala na wybór akcji, którą użytkownik chce wykonać
 * dla wybranego z listy pacjenta.
 * Do stworzenia instancji tego okna dialogowego należy wykorzystać metodę {@link PatientsListActionFragment#newInstance}.
 */
public class PatientsListActionFragment extends DialogFragment {

    private static final String ARG_PATIENT_ID = "patient_id";

    private Integer patientID;
    private PatientService patientService;
    private DeleteListener dListener;

    /**
     * Interfejs nasłuchujący chęci usunięcia pacjenta. Umożliwia aktualizację listy pacjentów.
     */
    public interface DeleteListener {
        void patientDeleted(int patientID);
    }

    /**
     * Publiczny konstruktor bezparametrowy - jest wymagany, ale nie jest wykorzystywany
     */
    public PatientsListActionFragment() {
        // Required empty public constructor
    }


    public static PatientsListActionFragment newInstance(int patientID, DeleteListener _dListener) {
        PatientsListActionFragment fragment = new PatientsListActionFragment();
        fragment.dListener = _dListener;

        Bundle args = new Bundle();
        args.putInt(ARG_PATIENT_ID, patientID);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Metoda wołana w celu zainicjowania tworzenia fragmentu. Metoda ustawia wartość pól klasy przekazane
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
     * Metoda pozwalająca na zainicjowanie interfejsu użytkownika dla okna dialogowego. Funkcja oprócz wstrzyknięcia widoku
     * fragmentu ustawia akcje dla poszczególnych przycisków w oknie dialogowym.
     *
     * @param inflater obiekt umożliwiający wstrzyknięcie widoku do fragmentu
     * @param container widok-rodzic, do którego powinien być podpięty UI fragmentu
     * @param savedInstanceState poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     * @return (View) widok interfejsu użytkownika fragmentu (może przyjąć wartość null)
     */
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patients_list_action, container, false);
        patientService = new PatientService(view.getContext());

        final Button profileBt= view.findViewById(R.id.profileBt);
        profileBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setPatientProfile();
            }
        });

        final Button deleteBt= view.findViewById(R.id.deleteBt);
        deleteBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDeleteDialog();
            }
        });

        return view;
    }

    /**
     * Metoda wywoływana w momencie, gdy użytkownik zdecyduje się na usunięcie profilu pacjenta.
     * Powoduje pojawienie się okna dialogowego {@link DeleteDialogFragment}
     *
     */
    private void openDeleteDialog()
    {
        DeleteDialogFragment.DeletePatientDialogListener listener = new DeleteDialogFragment.DeletePatientDialogListener() {
            @Override
            public void onDialogDeletePositiveClick(DialogFragment dialog, int patientID) {
                deletePatient();
                dialog.dismiss();
            }

            @Override
            public void onDialogDeleteNegativeClick(DialogFragment dialog) {
                dialog.dismiss();
            }
        };

        AppCompatActivity activity = (AppCompatActivity)getActivity();
        if(activity!=null)
        {
            //print dialog with actions for patient
            DialogFragment dialog = DeleteDialogFragment.newInstance(patientID,listener);
            dialog.show(activity.getSupportFragmentManager(), "DeleteDialogFragment");
        }

    }

    /**
     * Metoda służaca do usunięcia pacjenta. Powoduje usunięcie danych pacjenta z bazy danych aplikacji.
     *
     */
    private void deletePatient()
    {
        //remove patient from database
        patientService.DeletePatient(patientID);
        dListener.patientDeleted(patientID);
        //get back to list view
        dismiss();

    }

    /**
     * Metoda służąca do przejścia do okna profilu pacjenta
     */
    private void setPatientProfile()
    {
        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        if(activity!=null)
        {

            FragmentManager _fmanager = activity.getSupportFragmentManager();
            if(_fmanager!=null)
            {
                //move to patient profile view
                PatientProfileFragment setFragment= PatientProfileFragment.newInstance(patientID);
                _fmanager.beginTransaction()
                        .replace(R.id.fragmentFrame, setFragment, null)
                        .addToBackStack(null)
                        .commit();
            }

        }


        //close dialog
        dismiss();

    }

}
