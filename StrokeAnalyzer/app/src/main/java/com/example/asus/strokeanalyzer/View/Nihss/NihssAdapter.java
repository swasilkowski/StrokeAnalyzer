package com.example.asus.strokeanalyzer.View.Nihss;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.asus.strokeanalyzer.Model.Analyzers.NihssAnalyzer;
import com.example.asus.strokeanalyzer.Model.NihssExamination;
import com.example.asus.strokeanalyzer.R;
import java.text.DateFormat;
import java.util.List;

/**
 * Klasa będąca rozszerzeniem klasy {@link RecyclerView.Adapter<NihssAdapter.ViewHolder>}.
 * Odpowiedzialna jest za zarządzanie widokiem listy badań skali NIHSS. Zarządza obiektami przechowującymi widoki
 * dla pojedynczego elementu listy oraz odpowiada za uzupełnianie ich danymi aktualnie wyświetlanego elementu.
 *
 * @author Marta Marciszewicz
 */

public class NihssAdapter extends RecyclerView.Adapter<NihssAdapter.ViewHolder>  {

    /**
     * Lista badań w skali NIHSS prezentowanych w widoku.
     */
    final private List<NihssExamination> examinations;

    /**
     *  Klasa zarządzająca elementami związanymi z widokiem pojedynczego elementu listy przechowywanej w adapterze.
     *  Pozwala na wyświetlenie w widoku danych zawartych w obiekcie z listy.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        final public TextView date;
        final public TextView time;
        final public TextView sum;
        public final View mView;

        /**
         * Kontruktor pobierający kontrolki z widoku pojedynczego elementu listy.
         *
         * @param view widok pojedynczego elementu listy
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;

            date =  view.findViewById(R.id.examinationDate);
            time =  view.findViewById(R.id.examinationTime);
            sum =  view.findViewById(R.id.pointsSum);
        }
    }

    /**
     * Kontruktor ustawiający listę badań skali NIHSS wykorzystywaną przez adapter do wyświetlenia we fragmencie.
     *
     * @param examinations lista badań w skali NIHSS, która powinna zostać wyświetlona we fragmencie
     * @param context kontekst aplikacji
     */
    public NihssAdapter(final List<NihssExamination> examinations, Context context) {
        this.examinations = examinations;
    }

    /**
     * Metoda pobierająca widok pojedynczego elementu RecyclerView i generująca nowy obiekt klasy {@link ViewHolder}
     * do przechowywania danych elementu listy adaptera.
     *
     * @param parent grupa, do której dodany zostanie widok po jego utworzeniu
     * @param viewType rodzaj widoku
     * @return nowoutworzony ViewHolder, który przechowuje widok podanego typu
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nihss_row, parent, false);

        return new ViewHolder(itemView);
    }

    /**
     * Metoda wywoływana w celu wyświetlenia danych konkretnego elementu listy. Powoduje modyfikację
     * elementów przetrzymywanych w ViewHolderze zgodnie z danymi przechowywanymi na podanej jako parametr pozycji.
     *
     * @param holder obiekt klasy ViewHolder, który powinien zostać zaktualizowany, tak by przechowywał dane zawarte w
     *               obiekcie znajdującym się na podanej pozycji w liście wszystkich elementów zarządzanych przez adapter
     * @param position pozycja obiektu na liście wszystkich elementów zarządzanych przez adapter
     */
    @Override
    public void onBindViewHolder(NihssAdapter.ViewHolder holder, int position) {

        final NihssExamination examination = examinations.get(position);

        holder.date.setText(DateFormat.getDateInstance().format(examination.Date));
        holder.time.setText(DateFormat.getTimeInstance().format(examination.Date));
        holder.sum.setText(String.valueOf(NihssAnalyzer.CountNihssSum(examination)));

    }

    /**
     * Metoda pobierająca liczbę elementów listy do wyświetlenia.
     *
     * @return liczba badań wyświetlanych we fragmencie
     */
    @Override
    public int getItemCount() {
        return examinations.size();
    }
}
