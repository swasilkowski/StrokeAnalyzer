package com.example.asus.strokeanalyzer.View.Form;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.asus.strokeanalyzer.R;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Klasa będąca rozszerzeniem klasy {@link RecyclerView.Adapter<BulletedAnswerAdapter.ViewHolder>}.
 * Odpowiedzialna jest za zarządzanie widokiem listy możliwych odpowiedzi na pytanie zamknięte.
 * Zarządza obiektami przechowującymi widoki dla pojedynczego elementu listy oraz odpowiada za
 * uzupełnianie ich danymi aktualnie wyświetlanego elementu.
 *
 * @author Marta Marciszewicz
 */

public class BulletedAnswerAdapter  extends RecyclerView.Adapter<BulletedAnswerAdapter.ViewHolder>  {

    private List<BulletedAnswer> answers;
    private final Context context;
    private Map<Integer, TextView> buttons;
    private Integer answerID;

    /**
     * Klasa zarządzająca elementami związanymi z widokiem pojedynczego elementu listy przechowywanej w adaptorze.
     * Pozwala na wyświetlenie w widoku danych zawartych w obiekcie z listy.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView answer;

        /**
         * Kontruktor pobierający kontrolki z widoku pojedynczego elementu listy.
         *
         * @param view widok pojedynczego elementy listy
         */
        public ViewHolder(View view) {
            super(view);

            answer = (TextView) view.findViewById(R.id.answerTextB);
        }
    }

    /**
     * Kontruktor ustawiający listę pacjentów wykorzystywaną przez adapter do wyświetlenia we fragmencie
     * oraz Id odpowiedzi, która została uprzednio wybrana przez użytkownika.
     *
     * @param answers lista możliwych odpowiedzi, która powinna zostać wyświetlona pod pytaniem
     * @param pickedAnswer Id odpowiedzi, którą wybrał uprzednio użytkownik
     * @param context kontekst aplikacji
     */
    public BulletedAnswerAdapter(final List<BulletedAnswer> answers, int pickedAnswer, Context context) {
        this.answers = answers;
        this.context = context;
        this.buttons = new Hashtable<>();
        answerID=pickedAnswer;
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
                .inflate(R.layout.answer_row, parent, false);

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
    public void onBindViewHolder(final BulletedAnswerAdapter.ViewHolder holder, int position) {

        final BulletedAnswer answer = answers.get(position);
        holder.answer.setText(answer.getText());
        color(holder.answer,answer.getId());
        buttons.put(answer.getId(),holder.answer);
    }

    /**
     * Metoda ustawiająca Id wybranej przez użytkwonika odpowiedz
     *
     * @param id id wybranej odpowiedzi
     */
    public void SetAnswerID(int id)
    {
        answerID = id;
    }

    /**
     * Metoda ustawiająca kolor danego elementu listy.
     *
     * @param view widok, któremu ustawiane jest tło
     * @param id Id aktualnie sprawdzanego elementu, którego widok przekazany jest jako pierwszy parametr
     */
    public void color(View view,int id)
    {
        if(id==answerID)
        {
            view.setBackgroundColor(context.getColor(R.color.colorAccent));
        }
        else
        {
            view.setBackgroundColor((context.getColor(R.color.colorPrimary)));
        }
    }

    /**
     * Metoda przerysowująca wszystkie elmenty w celu zmiany ich koloru
     */
    public void clearColors()
    {
        for(Integer ans: new ArrayList<Integer>(buttons.keySet()))
        {
            color(buttons.get(ans),ans);
        }
    }

    /**
     * Metoda pobierająca liczbę elentów listy do wyświetlenia
     *
     * @return (int) liczba pacjentów wyświetlanych we fragmencie
     */
    @Override
    public int getItemCount() {
        return answers.size();
    }



    //--------TODO---------usun
   /*



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.answer_row, viewGroup, false);
        }


    }




*//*    public class ViewHolder extends RecyclerView.ViewHolder {
        public Button answer;

        public ViewHolder(View view) {
            super(view);

            answer = (Button) view.findViewById(R.id.answerTextB);
        }
    }*//*





*//*    @Override
    public void onBindViewHolder(final BulletedAnswerAdapter.ViewHolder holder, int position) {

        final BulletedAnswer answer = answers.get(position);

        holder.answer.setText(answer.getText());
        holder.answer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chooseAnswer(holder.answer, answer.getId());
            }
        });
    }*//*

    public void chooseAnswer(View view, int id)
    {

        view.setBackgroundColor(view.getResources().getColor(R.color.colorAccent));
            //kolorowanie elementu
           // ListPoint elem = productsList.get(position);
           // elem.setisBought(val);
           // notifyItemChanged(position);
    }*/






}
