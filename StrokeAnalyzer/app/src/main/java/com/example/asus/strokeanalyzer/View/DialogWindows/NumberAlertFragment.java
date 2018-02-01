package com.example.asus.strokeanalyzer.View.DialogWindows;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import com.example.asus.strokeanalyzer.R;

/**
 * Klasa będąca rozszerzeniem klasy {@link DialogFragment}. Wykorzystywana jest w celu poinformowania użytkownika,
 * iż próbuje on dodać pacjenta o takim samym numerze, jak już istniejący pacjent w aplikacji.
 * Do stworzenia instancji tego okna dialogowego należy wykorzystać metodę {@link NumberAlertFragment#newInstance}.
 */
public class NumberAlertFragment extends DialogFragment {

    /**
     * Interfejs akcji przycisków w oknie dialogowym.
     */
    public interface NumberAlertDialogListener {
        void onDialogNumberPositiveClick(android.support.v4.app.DialogFragment dialog);
        void onDialogNumberNegativeClick(android.support.v4.app.DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    private NumberAlertDialogListener _listener;

    /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów.
     *
     * @param listener obiekt klasy {@link NumberAlertDialogListener}, wykorzystywany w oknie dialogowym
     *                do monitorowania akcji przycisków okna
     * @return nowa instancja fragmentu NumberAlertFragment
     */
    public static NumberAlertFragment newInstance(NumberAlertDialogListener listener) {
        NumberAlertFragment fragment = new NumberAlertFragment();
        fragment._listener = listener;
        return fragment;
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

        builder.setTitle(R.string.number_alert_title)
                .setView(R.layout.fragment_number_alert)
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
