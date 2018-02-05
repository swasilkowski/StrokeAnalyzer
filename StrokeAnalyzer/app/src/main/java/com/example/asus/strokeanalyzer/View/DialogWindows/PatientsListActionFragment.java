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
 * Klasa będąca rozszerzeniem klasy {@link DialogFragment}. Pozwala na wybór akcji, którą użytkownik chce wykonać
 * dla wybranego z listy pacjenta.
 * Do stworzenia instancji tego okna dialogowego należy wykorzystać metodę {@link PatientsListActionFragment#newInstance}.
 */
public class PatientsListActionFragment extends DialogFragment {

    /**
     * Zmienna przechowująca klucz, który pozwala na zapisanie i pobranie danych z obiektu klasy
     * {@link Bundle} wykorzystywanego do przekazania parametrów fragmentu.
     */
    private static final String ARG_PATIENT_ID = "patient_id";

    /**
     * Id pacjenta, dla którego ma zostać wykonana wskazana przez użytkownika akcja.
     */
    private Integer patientID;
    /**
     * Obiekt umożliwiający wykonywanie operacji na bazie danych aplikacji.
     */
    private PatientService patientService;
    /**
     * Obiekt informujący o chęci usunięcia pacjenta. Pozwala na aktualizację listy pacjentów.
     */
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


    /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów.
     *
     * @param patientID id pacjenta, dla którego będzie wykonywana wybrana akcja
     * @param _dListener obiekt klasy {@link DeleteListener}, wykorzystywany w oknie dialogowym
     *                do monitorowania akcji przycisku do usunięcia pacjenta
     * @return nowa instancja fragmentu PatientsListActionFragment
     */
    public static PatientsListActionFragment newInstance(int patientID, DeleteListener _dListener) {
        PatientsListActionFragment fragment = new PatientsListActionFragment();
        fragment.dListener = _dListener;

        Bundle args = new Bundle();
        args.putInt(ARG_PATIENT_ID, patientID);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Metoda wołana w celu zainicjowania tworzenia fragmentu. Metoda ustawia wartości pól klasy przekazane
     * jako argumenty poprzez {@link Bundle}.
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
     * Metoda pozwalająca na zainicjowanie interfejsu użytkownika dla okna dialogowego.
     *
     * @param inflater obiekt umożliwiający wstrzyknięcie widoku do fragmentu
     * @param container widok-rodzic, do którego powinien być podpięty UI fragmentu
     * @param savedInstanceState poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     * @return widok interfejsu użytkownika fragmentu (może przyjąć wartość null)
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
     * Metoda wywoływana w momencie, gdy użytkownik wybierze opcję usunięcia profilu pacjenta.
     * Powoduje pojawienie się okna dialogowego {@link DeleteDialogFragment}.
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
     * Metoda służąca do usunięcia pacjenta z listy pacjentów przechowywanej w aplikacji.
     * Powoduje usunięcie danych pacjenta z bazy danych aplikacji.
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
     * Metoda służąca do przejścia do widoku profilu wybranego pacjenta.
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
