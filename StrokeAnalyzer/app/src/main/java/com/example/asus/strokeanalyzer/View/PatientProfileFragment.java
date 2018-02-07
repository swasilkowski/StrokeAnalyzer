package com.example.asus.strokeanalyzer.View;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.Model.Report;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.DialogWindows.ReportFragment;

import java.io.File;

/**
 * Klasa będąca rozszerzeniem klasy {@link Fragment}. Odpowiada za wyświetlenie profilu pacjenta wybranego przez użytkownika
 * i pozwala na wykonanie dla niego różnych czynności, np. sprawdzenie jego wyników, czy
 * wygenerowanie raportu o stanie zdrowia.
 * Do stworzenia instancji tego fragmentu należy wykorzystać metodę {@link FormListFragment#newInstance}.
 *
 * @author Marta Marciszewicz
 */
public class PatientProfileFragment extends Fragment {

    /**
     * Zmienna przechowująca klucz, który pozwala na zapisanie i pobranie danych z obiektu klasy
     * {@link Bundle} wykorzystywanego do przekazania parametrów fragmentu.
     */
    private static final String ARG_PATIENT_ID = "patient_id";

    /**
     * Id pacjenta, którego profil ma zostać wyświetlony.
     */
    private Integer patientID;
    /**
     * Obiekt klasy {@link Patient} reprezentujący pacjenta, którego profil ma zostać wyświetlony.
     */
    private Patient patient;
    /**
     * Aktywność związana z fragmentem.
     */
    private FragmentActivity activity;

    /**
     * Publiczny konstruktor bezparametrowy - jest wymagany, ale nie jest wykorzystywany.
     */
    public PatientProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów.
     *
     * @param patientID id pacjenta, którego wyniki mają zostać wyświetlone we fragmencie
     * @return nowa instancja fragmentu PatientProfileFragment
     */
    public static PatientProfileFragment newInstance(Integer patientID) {
        PatientProfileFragment fragment = new PatientProfileFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PATIENT_ID, patientID);
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
        if (getArguments() != null) {
            patientID = getArguments().getInt(ARG_PATIENT_ID);
        }
        activity= getActivity();
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
        View view = inflater.inflate(R.layout.fragment_patient_profile, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.colorBackground, null));

        PatientService patientService = new PatientService(view.getContext());
        patient = patientService.GetPatientById(patientID);

        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity!=null)
        {
            ActionBar bar =  activity.getSupportActionBar();
            if(bar!=null)
            {
                bar.setTitle("Profil: "+patient.Name+" "+patient.Surname);
            }
        }

        //set patient data
        TextView name = view.findViewById(R.id.patientNameShow);
        TextView number = view.findViewById(R.id.patientNumberShow);
        String nameText = patient.Name +" "+ patient.Surname;
        name.setText(nameText);
        number.setText(String.valueOf(patient.PatientNumber));

        final Button resultBt=  view.findViewById(R.id.resultBt);
        resultBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showResults();
            }
        });
        final Button reportBt=  view.findViewById(R.id.reportBt);
        reportBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                generateReport();
            }
        });
        final Button formsBt= view.findViewById(R.id.formsBt);
        formsBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chooseForm();
            }
        });

        return view;
    }

    /**
     * Metoda wyświetlająca okno dialogowe do potwierdzenia przez użytkownika chęci wygenerowania raportu.
     */
    private void generateReport()
    {
        if(activity!=null)
        {
            ReportFragment.GenerateReportDialogListener listener = new ReportFragment.GenerateReportDialogListener() {
                @Override
                public void onDialogReportPositiveClick(DialogFragment dialog, int patientID) {
                    //generate report about the patient
                    PatientService patientService = new PatientService(dialog.getContext());
                    String fileName = Report.GenerateReport( patientService.GetPatientById(patientID), dialog.getContext());

                    if(fileName!=null)
                        share(fileName, patientService.GetPatientById(patientID).PatientNumber);
                }

                @Override
                public void onDialogReportNegativeClick(DialogFragment dialog) {
                    dialog.dismiss();
                }
            };

            DialogFragment dialog = ReportFragment.newInstance(patient.Id, listener);
            dialog.show(activity.getSupportFragmentManager(), "ReportFragment");
        }

    }

    /**
     * Metoda dpowiedzialna za udostępnianie wygenerowanego pliku raportu innym aplikacjom na urządzeniu
     * mobilnym.
     *
     * @param fileName nazwa pliku, w którym zapisany został wygenerowany raport
     * @param patientNumber numer identyfikacyjny pacjenta, którego raportu ma zostać udostępniony
     */
    private void share(String fileName, long patientNumber) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        File file = new File(fileName);

        if(file.exists()) {
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.report_share_msg_title));
            intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.report_share_msg_text)+ String.valueOf(patientNumber));
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+fileName));
            startActivity(Intent.createChooser(intent, getString(R.string.report_share_title)));
        }
    }

    /**
     * Metoda dokonująca przejścia do fragmentu zawierającego wyniki analizy stanu pacjenta.
     */
    private void showResults()
    {
        if(activity!=null)
        {
            ResultsFragment setFragment= ResultsFragment.newInstance(patient.Id);
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentFrame, setFragment, null)
                    .addToBackStack(null)
                    .commit();
        }

    }

    /**
     * Metoda dokonująca przejścia do fragmentu pozwalającego wybrać formularz do uzupełnienia.
     */
    private void chooseForm()
    {
        if(activity!=null)
        {
            FormListFragment setFragment= FormListFragment.newInstance(patient.Id);
            activity.getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentFrame, setFragment, null)
                    .addToBackStack("forms_list")
                    .commit();
        }

    }

}
