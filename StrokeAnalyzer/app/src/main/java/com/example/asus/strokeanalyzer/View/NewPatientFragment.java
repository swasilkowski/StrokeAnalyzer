package com.example.asus.strokeanalyzer.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.DialogWindows.NumberAlertFragment;
import com.example.asus.strokeanalyzer.View.Patient.PatientsListFragment;
import static android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

/**
 * Klasa będąca rozszerzeniem klasy {@link Fragment}. Pozwala na utworzenie nowego profilu pacjenta w aplikacji i
 * zapisanie go w bazie danych.
 * Do stworzenia instancji tego fragmentu należy wykorzystać metodę {@link FormListFragment#newInstance}.
 *
 * @author Marta Marciszewicz
 */
public class NewPatientFragment extends Fragment {

    /**
     * Kontrolka pobierająca imię pacjenta.
     */
    private EditText name;
    /**
     * Kontrolka pobierająca nazwisko pacjenta.
     */
    private EditText surname;
    /**
     * Kontrolka pobierająca indywidualny numer pacjenta.
     */
    private EditText number;
    /**
     * Obiekt umożliwiający wykonywanie operacji na bazie danych aplikacji.
     */
    private PatientService patientService;

    /**
     * Publiczny konstruktor bezparametrowy - jest wymagany, ale nie jest wykorzystywany.
     */
    public NewPatientFragment() {
        // Required empty public constructor
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_patient, container, false);

        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        if(activity!=null)
        {
            ActionBar bar = activity.getSupportActionBar();
            if(bar!=null)
            {
                bar.show();
                bar.setTitle("Tworzenie profilu pacjenta");
            }

        }


        Context context = getContext();
        if(context!=null)
            view.setBackgroundColor(ContextCompat.getColor(context,R.color.colorBackground));
        view.setClickable(true);
        //mListener.setTitleName(getString(R.string.title_fragmet_change_name));
        name = view.findViewById(R.id.name);
        surname =  view.findViewById(R.id.surname);
        number =  view.findViewById(R.id.patientNumber);

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
        setHasOptionsMenu(true);
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
        inflater.inflate(R.menu.form, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    /**
     * Metoda wywoływana w momencie wyboru przez użytkownika jednej z opcji w menu fragmentu.
     * Funkcja jest odpowiedzialna za stworzenie nowego profilu pacjenta na podstawie
     * wprowadzonych przez użytkownika danych i zapisanie go w bazie danych.
     *
     * @param item elementu menu, który został wybrany przez użytkownika
     * @return false - jeżeli element menu ma być przetworzony standardowo;
     *          true - jeżeli element menu został obsłużony wewnątrz funkcji
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.done) {

            //creating new patient profile
            if(!createPatient())
                Toast.makeText(getContext(), getString(R.string.toast_new_patient), Toast.LENGTH_LONG).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
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

    /**
     * Metoda tworząca profil nowego pacjenta w bazie danych.
     *
     * Uwaga: Metoda sprawdza dodatkowo istnienie pacjent o podanym numerze w bazie danych i
     * prosi o potwierdzenie przez uzytkownika chęci utowrzenia profilu pacjenta o tym samym numerze, w przypadku zaistnienia
     * takiej sytuacji.
     *
     * @return true - jeżeli stowrzenie profilu nowego pacjenta zakończyło się sukcesem;
     *          false - jeżeli nie udało się utworzyć profilu nowego pacjenta
     */
    private boolean createPatient()
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


                //dialog.setArguments(bundel);
                AppCompatActivity activity = (AppCompatActivity)getActivity();
                if(activity!=null)
                {
                    //print dialog with actions for patient
                    DialogFragment dialog = NumberAlertFragment.newInstance(listener);
                    dialog.show(activity.getSupportFragmentManager(), "NumberAlertFragment");
                }

                return true;
            }

            addPatient(name,surname,patientNumber);
        }
        catch(NumberFormatException exception)
        {
            Toast.makeText(getContext(),"Numer pacjenta jest za długi. Wprwadź inny numer", Toast.LENGTH_LONG).show();
        }

        return true;
    }

    /**
     * Metoda pomocnicza dodająca nowego pacjenta do listy wszystkich pacjentów w bazie danych.
     * Dodatkowo metoda wywołuje przejście do fragmentu z profilem nowo utworzonego pacjenta.
     *
     * @param name imię nowego pacjenta
     * @param surname nazwisko nowego pacjenta
     * @param number numer nowego pacjenta
     */
    private void addPatient(String name, String surname, long number)
    {
        //add patient to database - create profile
        Patient newPatient = new Patient();
        newPatient.Name = name;
        newPatient.Surname = surname;
        newPatient.PatientNumber = number;

        PatientService patientService = new PatientService(getContext());
        long patientID = patientService.AddPatient(newPatient);

        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        if(activity!=null)
        {
            android.support.v4.app.FragmentManager _fmanager = activity.getSupportFragmentManager();
            if(_fmanager!=null)
            {
                _fmanager.popBackStack(getString(R.string.new_patient_tag), POP_BACK_STACK_INCLUSIVE);

                PatientsListFragment listFragment= new PatientsListFragment();
                _fmanager.beginTransaction()
                        .replace(R.id.fragmentFrame, listFragment, null)
                        .addToBackStack(null)
                        .commit();

                PatientProfileFragment setFragment = PatientProfileFragment.newInstance((int)patientID);
                _fmanager.beginTransaction()
                        .replace(R.id.fragmentFrame, setFragment, null)
                        .addToBackStack(null)
                        .commit();

            }

        }
    }
}
