package com.example.asus.strokeanalyzer.View.Patient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;
import java.util.List;

/**
 * Klasa będąca rozszerzeniem klasy {@link RecyclerView.Adapter<PatientAdapter.ViewHolder>}.
 * Odpowiedzialna jest za zarządzanie widokiem listy pacjentów. Zarządza obiektami przechowującymi widoki
 * dla pojedynczego elementu listy oraz odpowiada za uzupełnianie ich danymi aktualnie wyświetlanego elementu.
 *
 * @author Marta Marciszewicz
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {

    private List<Patient> patients;

    /**
     *  Klasa zarządzająca elementami związanymi z widokiem pojedynczego elementu listy przechowywanej w adaptorze.
     *  Pozwala na wyświetlenie w widoku danych zawartych w obiekcie z listy.
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView number;
        public final View mView;

        /**
         * Kontruktor pobierający kontrolki z widoku pojedynczego elementu listy.
         *
         * @param view widok pojedynczego elementu listy
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;

            name =  view.findViewById(R.id.patientNameSurname);
            number =  view.findViewById(R.id.patientID);
        }
    }

    /**
     * Kontruktor ustawiający listę pacjentów wykorzystywaną przez adapter do wyświetlenia we fragmencie.
     *
     * @param patients lista pacjentów, która powinna zostać wyświetlona we fragmencie
     * @param context kontekst aplikacji
     */
    public PatientAdapter(final List<Patient> patients, Context context) {
        this.patients = patients;
    }

    /**
     * Metoda pobierająca widok pojedynczego elementu RecyclerView i generująca nowy obiekt klasy {@link ViewHolder}
     * do przechowywania danych elementu listy adaptera.
     *
     * @param parent grupa, do której dodany zostanie widok po jego utworzeniu
     * @param viewType rodzaj widoku
     * @return (ViewHolder) nowoutworzony ViewHolder, który przechowuje widok podanego typu
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_row, parent, false);

        return new ViewHolder(itemView);
    }

    /**
     * Metoda wywoływana w celu wyświetlanie danych konkretnego elementu listy. Powoduje modyfikację
     * elementów przetrzymywanych w ViewHolderze zgodnie z danymi przechowywanymi na podanej jako parametr pozycji.
     *
     * @param holder obiekt klasy ViewHolder, który powinien zostać zaktualizowany, tak by przechowywał dane zawarte w
     *               obiekcie znajdującym się na podanej pozycji w liście wszystkich elementów zarządzanych przez adapter
     * @param position pozycja obiektu na liście wszystkich elementów zarządzanych przez adapter
     */
    @Override
    public void onBindViewHolder(PatientAdapter.ViewHolder holder, int position) {

        final Patient patient = patients.get(position);

        String name = patient.Name + " " + patient.Surname;
        holder.name.setText(name);
        holder.number.setText(String.valueOf(patient.PatientNumber));

    }


    /**
     * Metoda pobierająca liczbę elentów listy do wyświetlenia
     *
     * @return (int) liczba pacjentów wyświetlanych we fragmencie
     */
    @Override
    public int getItemCount() {
        return patients.size();
    }

}
