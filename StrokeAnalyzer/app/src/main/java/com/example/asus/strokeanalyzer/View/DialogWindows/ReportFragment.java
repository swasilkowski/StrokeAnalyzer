package com.example.asus.strokeanalyzer.View.DialogWindows;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.example.asus.strokeanalyzer.R;

/**
 * Klasa będąca rozszerzeniem klasy {@link DialogFragment}. Wykorzystywana jest w celu potwierdzenia przez
 * użytkownika chęci wygenerowania raportu z wynikami pacjenta.
 * Do stworzenia instancji tego okna dialogowego należy wykorzystać metodę {@link ReportFragment#newInstance}.
 *
 * @author Marta Marciszewicz
 */
public class ReportFragment extends DialogFragment {

    /**
     * Zmienna przechowująca klucz, który pozwala na zapisanie i pobranie danych z obiektu klasy
     * {@link Bundle} wykorzystywanego do przekazania parametrów fragmentu.
     */
    private static final String ARG_PATIENT_ID = "patient_id";

    /**
     * Id pacjenta, dla którego raport chcemy wygenerować.
     */
    private Integer patientID;

    /**
     * Interfejs akcji przycisków w oknie dialogowym.
     */
    public interface GenerateReportDialogListener {
        void onDialogReportPositiveClick(android.support.v4.app.DialogFragment dialog, int patientID);
        void onDialogReportNegativeClick(android.support.v4.app.DialogFragment dialog);
    }


    /**
     * Obiekt odpowiadający za kontrolowanie akcji przycisków okna dialogowego.
     */
    private GenerateReportDialogListener _listener;


    /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów.
     *
     * @param patientID id pacjenta, dla którego ma zostać wygenerowany raport
     * @param listener obiekt klasy {@link GenerateReportDialogListener}, wykorzystywany w oknie dialogowym
     *                do monitorowania akcji przycisków okna
     * @return nowa instancja fragmentu ReportFragment
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
     * Metoda pozwala na stowrzenie własnego kontenera na okno dialogowe. Wstrzykuje widok oraz
     * odpowiada za wywoływanie operacji listenera.
     *
     * @param savedInstanceState poprzedni zapisany stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     * @return nowa instancja okna dialogowego, która ma być wyświetlona we fragmencie
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.report_generation)
                .setView(R.layout.fragment_report)
                .setPositiveButton(R.string.ok_bt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        _listener.onDialogReportPositiveClick(ReportFragment.this, patientID);

                    }
                })
                .setNegativeButton(R.string.cancel_bt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        _listener.onDialogReportNegativeClick(ReportFragment.this);
                    }
                });
        return builder.create();
    }

}
