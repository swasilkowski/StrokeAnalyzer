package com.example.asus.strokeanalyzer.View.DialogWindows;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.example.asus.strokeanalyzer.R;

/**
 * Klasa będąca podklasą {@link DialogFragment}. Wykorzystywana jest w celu potwierdzenia przez
 * użytkownika chęci wygenerowania raportu z wynikami pacjenta.
 * Do stworzenia instancji tego okna dialogowego należy wykorzystać metodę {@link ReportFragment#newInstance}.
 */
public class ReportFragment extends DialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT_ID = "patient_id";

    //Patient patient;
    Integer patientID;

    /**
     * Interfejs akcji przycisków w oknie dialogowym
     */
    public interface GenerateReportDialogListener {
        void onDialogReportPositiveClick(android.support.v4.app.DialogFragment dialog, int patientID);
        void onDialogReportNegativeClick(android.support.v4.app.DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    GenerateReportDialogListener _listener;

     /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów.
     *
     * @param patientID Id pacjenta, dla którego ma zostać wygenerowany raport
     * @return (ReportFragment) nowa instancja fragmentu ReportFragment
     */
    public static ReportFragment newInstance(int patientID, GenerateReportDialogListener listener) {
        ReportFragment fragment = new ReportFragment();
        fragment._listener = listener;

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
     * Metoda pozwala na stowrzenie własnego kontenera na okno dialogowe. Wstrzykuje widok oraz
     * odpowiada za wywoływanie operacji listnera.
     *
     * @param savedInstanceState poprzedni zapisany stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     * @return (Dialog) nowa instancja okna dialogowego, która ma być wyświetlona we fragmencie
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fragment_report, null);

        builder.setTitle(R.string.report_generation)
                .setView(dialogView)
                .setPositiveButton(R.string.ok_bt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //generate report
                        _listener.onDialogReportPositiveClick(ReportFragment.this, patientID);

                    }
                })
                .setNegativeButton(R.string.cancel_bt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // User cancelled the dialog
                        // Send the negative button event back to the host activity
                        _listener.onDialogReportNegativeClick(ReportFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
