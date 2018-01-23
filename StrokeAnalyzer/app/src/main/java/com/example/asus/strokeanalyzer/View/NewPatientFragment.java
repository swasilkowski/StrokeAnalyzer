package com.example.asus.strokeanalyzer.View;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.DialogWindows.NumberAlertFragment;
import com.example.asus.strokeanalyzer.View.Patient.PatientsListFragment;
import static android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

/**
 * Klasa będąca podklasą {@link Fragment}. Pozwala na utworzenie nowego profilu pacjenta w aplikacji i
 * zapisanie go w bazie danych.
 * Do stworzenia instancji tego fragmentu należy wykorzystać metodę {@link FormListFragment#newInstance}.
 *
 * @author Marta Marciszewicz
 */
public class NewPatientFragment extends Fragment {

    private EditText name;
    private EditText surname;
    private EditText number;
    private PatientService patientService;

    /**
     * Publiczny konstruktor bezparametrowy - jest wymagany, ale nie jest wykorzystywany
     */
    public NewPatientFragment() {
        // Required empty public constructor
    }

    /**
     * Metoda pozwalająca na zainicjowanie interfejsu użytkownika dla fragmentu. Funkcja oprócz wstrzyknięcia widoku
     * fragmentu pobiera poszczególne jego elementy i zapisuje w obiekcie.
     *
     *
     * @param inflater obiekt umożliwiający wstrzyknięcie widoku do fragmentu
     * @param container widok-rodzic, do którego powinien być podpięty UI fragmentu
     * @param savedInstanceState poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     * @return (View) widok interfejsu użytkownika fragmentu (może przyjąć wartość null)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_patient, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tworzenie profilu pacjenta");

        view.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        view.setClickable(true);
        //mListener.setTitleName(getString(R.string.title_fragmet_change_name));
        name = (EditText) view.findViewById(R.id.name);
        surname = (EditText) view.findViewById(R.id.surname);
        number = (EditText) view.findViewById(R.id.patientNumber);

        final Button nextBt= (Button) view.findViewById(R.id.nextBt);
        nextBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!createPatient(v))
                    Toast.makeText(v.getContext(), getString(R.string.toast_new_patient), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    /**
     * Metoda wołana w celu zainicjowania tworzenia fragmentu.
     *
     * @param savedInstanceState  poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patientService = new PatientService(getContext());
    }

    /**
     * Metoda wywoływana w momencie, gdy fragment jest wyświetlany użytkownikowi. Aplikacja wykorzystuje tę metodę
     * do kontrolowania elementu ActionBar.
     */
    @Override
    public void onResume()
    {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    /**
     * Metoda wywoływana w momencie, gdy fragment odłączany jest od aktywności. Aplikacja wykorzystuje tę metodę
     * do kontrolowania elementu ActionBar.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    /**
     * Metoda tworząca profil nowego pacjenta w bazie danych.
     * Funkcja pobiera z widoku fragmentu dane pacjenta wprowadzone przez użytkownika. Następnie wywołuje metodę
     * tworzącą profil nowego pacjenta. Metoda sprawdza dodatkowo istnienie pacjent o podanym numerze w bazie i
     * prosi o potwierdzenie przez uzytkownika chęci utowrzenia profilu pacjenta o tym samym numerze., w przypadku zaistnienia
     * takiej sytuacji.
     *
     * @param v obiekt klasy View pozwalający na pozyskanie kontekstu aplikacji
     * @return (boolean) true - jeżeli stowrzenie profilu nowego pacjenta zakończyło się sukcesem;
     *          false - jeżeli nie udało się utworzyć profilu nowego pacjenta
     */
    public boolean createPatient(View v)
    {
        final String name = this.name.getText().toString();
        final String surname = this.surname.getText().toString();
        final String number = this.number.getText().toString();

        if(name.isEmpty() || surname.isEmpty() || number.isEmpty())
            return false;

        try
        {
            final long patientNumber = Long.parseLong(number);
            //sprawdz czy istnieje pacjent o takim numerze
            if(patientService.isPatientNumberTaken(patientNumber))
            {
                NumberAlertFragment.NumberAlertDialogListener listener = new NumberAlertFragment.NumberAlertDialogListener() {
                    @Override
                    public void onDialogNumberPositiveClick(DialogFragment dialog) {
                        addPatient(name,surname,patientNumber);
                        dialog.dismiss();
                    }

                    @Override
                    public void onDialogNumberNegativeClick(DialogFragment dialog) {
                        dialog.dismiss();
                    }
                };

                //print dialog with actions for patient
                DialogFragment dialog = NumberAlertFragment.newInstance(listener);
                //dialog.setArguments(bundel);
                dialog.show(getActivity().getSupportFragmentManager(), "NumberAlertFragment");
                return true;
            }

            addPatient(name,surname,patientNumber);
        }
        catch(NumberFormatException exception)
        {
            Toast.makeText(v.getContext(),"Numer pacjenta jest za długi. Wprwadź inny numer", Toast.LENGTH_LONG).show();
        }

        return true;
    }

    /**
     * Metoda dodająca nowego pacjenta do listy wszystkich pacjentów.
     * Funkcja tworzy obiekt klasy Patient na podstawie danych podanych jako argumenty, a następnie
     * wywołuje metodę dodającą nowego pacjenta do bazy. Dodatkowo metoda wywołuje przejście do kolejnego fragmentu.
     *
     * @param name imię nowego pacjenta
     * @param surname nazwisko nowego pacjenta
     * @param number numer nowego pacjenta
     */
    public void addPatient(String name, String surname, long number)
    {
        //add patient to database - create profile
        Patient newPatient = new Patient();
        newPatient.Name = name;
        newPatient.Surname = surname;
        newPatient.PatientNumber = number;

        PatientService patientService = new PatientService(getContext());
        long patientID = patientService.AddPatient(newPatient);


        getFragmentManager().popBackStack(getString(R.string.new_patient_tag), POP_BACK_STACK_INCLUSIVE);

        PatientsListFragment listFragment= new PatientsListFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame, listFragment, null)
                .addToBackStack(null)
                .commit();

        PatientProfileFragment setFragment = PatientProfileFragment.newInstance((int)patientID);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame, setFragment, null)
                .addToBackStack(null)
                .commit();
    }
}
