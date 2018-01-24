package com.example.asus.strokeanalyzer.View.DialogWindows;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import com.example.asus.strokeanalyzer.R;

/**
 * Klasa będąca podklasą {@link DialogFragment}. Wykorzystywana jest w celu poinformowania użytkownika,
 * iż próbuje on dodać pacjenta o takim samym numerze, jak już istniejący pacjent w aplikacji.
 * Do stworzenia instancji tego okna dialogowego należy wykorzystać metodę {@link NumberAlertFragment#newInstance}.
 */
public class NumberAlertFragment extends DialogFragment {

    /**
     * Interfejs akcji przycisków w oknie dialogowym
     */
    public interface NumberAlertDialogListener {
        void onDialogNumberPositiveClick(android.support.v4.app.DialogFragment dialog);
        void onDialogNumberNegativeClick(android.support.v4.app.DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NumberAlertDialogListener _listener;

    /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów. Inicjalizuje ona listener.
     *
     * @param listener obiekt klasy {@link NumberAlertDialogListener}, wykorzystywany w oknie dialogowym
     *                do monitorowania akcji przycisków okna
     * @return (NumberAlertFragment) nowa instancja fragmentu NumberAlertFragment
     */
    public static NumberAlertFragment newInstance(NumberAlertDialogListener listener) {
        NumberAlertFragment fragment = new NumberAlertFragment();
        fragment._listener = listener;
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
        final View dialogView = inflater.inflate(R.layout.fragment_number_alert, null);

        builder.setTitle(R.string.number_alert_title)
                .setView(dialogView)
                .setPositiveButton(R.string.ok_bt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //generate report
                        _listener.onDialogNumberPositiveClick(NumberAlertFragment.this);

                    }
                })
                .setNegativeButton(R.string.cancel_bt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // User cancelled the dialog
                        // Send the negative button event back to the host activity
                        _listener.onDialogNumberNegativeClick(NumberAlertFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
