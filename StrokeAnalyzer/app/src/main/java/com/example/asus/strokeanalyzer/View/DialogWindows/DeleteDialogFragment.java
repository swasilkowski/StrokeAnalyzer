package com.example.asus.strokeanalyzer.View.DialogWindows;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import com.example.asus.strokeanalyzer.R;

/**
 * Klasa będąca rozszerzeniem klasy {@link DialogFragment}. Wykorzystywana jest w celu otrzymania potwierdzenia od
 * użytkownika chęci usunięcia profilu pacjenta z aplikacji.
 * Do stworzenia instancji tego okna dialogowego należy wykorzystać metodę {@link DeleteDialogFragment#newInstance}.
 */
public class DeleteDialogFragment extends DialogFragment {

    private static final String ARG_PATIENT_ID = "patient_id";

    //Patient patient;
    private Integer patientID;


    /**
     * Interfejs akcji przycisków w oknie dialogowym.
     */
    public interface DeletePatientDialogListener {
        void onDialogDeletePositiveClick(android.support.v4.app.DialogFragment dialog, int patientID);
        void onDialogDeleteNegativeClick(android.support.v4.app.DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    private DeletePatientDialogListener _listener;


    /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów.
     *
     * @param id id pacjenta, którego profil ma zostać usunięty
     * @param listener obiekt klasy {@link DeletePatientDialogListener}, wykorzystywany w oknie dialogowym
     *                do monitorowania akcji przycisków okna
     * @return nowa instancja fragmentu DeleteDialogFragment
     */
    public static DeleteDialogFragment newInstance(int id, DeletePatientDialogListener listener) {
        DeleteDialogFragment fragment = new DeleteDialogFragment();
        fragment._listener = listener;
        Bundle args = new Bundle();
        args.putInt(ARG_PATIENT_ID, id);
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
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.delete_patient)
                .setView(R.layout.fragment_delete_dialog)
                .setPositiveButton(R.string.ok_bt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //generate report
                        _listener.onDialogDeletePositiveClick(DeleteDialogFragment.this, patientID);

                    }
                })
                .setNegativeButton(R.string.cancel_bt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // User cancelled the dialog
                        // Send the negative button event back to the host activity
                        _listener.onDialogDeleteNegativeClick(DeleteDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
