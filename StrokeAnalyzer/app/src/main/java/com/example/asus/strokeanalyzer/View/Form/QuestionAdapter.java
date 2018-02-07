package com.example.asus.strokeanalyzer.View.Form;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TextAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TrueFalseAnswer;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.View.Helpers.ClickListener;
import com.example.asus.strokeanalyzer.View.Helpers.RecyclerClickListener;
import java.util.ArrayList;
import java.util.List;

//----------TODO----------usun
//sorce:https://medium.com/@ruut_j/a-recyclerview-with-multiple-item-types-bce7fbd1d30e

/**
 * Klasa będąca rozszerzeniem klasy {@link RecyclerView.Adapter<QuestionAdapter.ViewHolder>}.
 * Odpowiedzialna jest za zarządzanie widokiem listy pytań formularza. Zarządza obiektami
 * przechowującymi widoki dla pojedynczego elementu listy oraz odpowiada za
 * uzupełnianie ich danymi aktualnie wyświetlanego elementu.
 *
 * @author Marta Marciszewicz
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    /**
     * Lista wszystkich pytań wyświetlanych w formularzu.
     */
    final private List<Question> questions;
    /**
     * Kontekst aplikacji.
     */
    Context context;
    /**
     * Zmienna informująca o tym, czy odpowiedzi użytkownika mają być edytowalne czy jedynie wyświetlane.
     */
    private boolean editable;

    /**
     * Klasa bazowa dla klas zarządzających elementami związanymi z widokiem pojedynczego pytania
     * przechowywanego w adapterze. Pozwala na wyświetlenie w widoku danych zawartych w obiekcie z listy pytań.
     */
    public abstract class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Kontruktor wywołujący metodę superklasy.
         *
         * @param view widok pojedynczego elementu listy
         */
        ViewHolder(View view) {
            super(view);
        }

        /**
         * Metoda abstrakcyjna, która w klasach dziedziczących powoduje modyfikację elementów przetrzymywanych w ViewHolderze zgodnie z danymi
         * przechowywanymi w podanym jako parametr pytaniu.
         *
         * @param question pytanie, które ma zostać wyświetlone przy pomocy elementów ViewHoldera
         */
        public abstract void bindType(Question question);
    }

    /**
     * Klasa będąca rozszerzeniem klasy {@link ViewHolder}. Zarządza elementami związanymi z widokiem pytania
     * typu {@link DescriptiveQ}.
     *
     */
    public class ViewHolderDescriptiveQ extends ViewHolder {
        private final TextView question;
        private final EditText answer;
        private Question questionObject;

        /**
         * Kontruktor pobierający kontrolki z widoku pojedynczego elementu listy.
         *
         * @param view widok pojedynczego elementu listy
         */
        ViewHolderDescriptiveQ(View view) {
            super(view);

            question =  view.findViewById(R.id.questionTextD);
            answer =  view.findViewById(R.id.answerText);
        }

        /**
         * Metoda wywoływana w celu wyświetlenia danych konkretnego elementu listy. Powoduje modyfikację
         * elementów przetrzymywanych w ViewHolderze zgodnie z danymi przechowywanymi w podanym jako parametr pytaniu.
         *
         * @param question pytanie, które ma zostać wyświetlone przy pomocy elmenetów ViewHoldera
         */
        @Override
        public void bindType(Question question) {
            questionObject = question;
            this.question.setText(((DescriptiveQ)question).getText());
            this.question.setTextColor(ContextCompat.getColor( context,textColor(questionObject)));
            this.answer.setText(((DescriptiveQ)questionObject).getAnswer());
            answer.setImeOptions(EditorInfo.IME_ACTION_DONE);

            if(editable)
            {
                answer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        ((DescriptiveQ)questionObject).setAnswer(answer.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
            else
                answer.setEnabled(false);

        }
    }

    /**
     * Klasa będąca rozszerzeniem klasy {@link ViewHolder}. Zarządza elementami związanymi z widokiem pytania
     * typu {@link NumericQ}.
     *
     */
    public class ViewHolderNumericQ extends ViewHolder {
        private final TextView question;
        private final EditText answer;
        private Question questionObject;
        final private Toast toast;

        /**
         * Kontruktor pobierający kontrolki z widoku pojedynczego elementu listy.
         *
         * @param view widok pojedynczego elementu listy
         */
        @SuppressLint("ShowToast")
        ViewHolderNumericQ(View view) {
            super(view);

            question =  view.findViewById(R.id.questionTextN);
            answer =  view.findViewById(R.id.answerData);
            toast = Toast.makeText(view.getContext(),"Podana liczba jest niepoprawna",Toast.LENGTH_SHORT);
        }

        /**
         * Metoda wywoływana w celu wyświetlenia danych konkretnego elementu listy. Powoduje modyfikację
         * elementów przetrzymywanych w ViewHolderze zgodnie z danymi przechowywanymi w podanym jako parametr pytaniu.
         *
         * @param question pytanie, które ma zostać wyświetlone przy pomocy elmenetów ViewHoldera
         */
        @Override
        public void bindType(Question question) {
            questionObject = question;
            this.question.setText(((NumericQ)question).getText());
            this.question.setTextColor(ContextCompat.getColor( context,textColor(questionObject)));
            if(((NumericQ)questionObject).getAnswerSet())
                this.answer.setText(numericAnswerTransform(String.valueOf(((NumericQ)questionObject).getAnswer())));
            answer.setImeOptions(EditorInfo.IME_ACTION_DONE);

            if(editable)
            {
                answer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String ans = answer.getText().toString();

                        try{
                            if(ans.isEmpty())
                                ((NumericQ)questionObject).clearAnswer();
                            else
                            {
                                if(!((NumericQ)questionObject).setAnswer(Double.parseDouble(ans)))
                                {
                                    ((NumericQ)questionObject).clearAnswer();
                                    toast.show();
                                }
                            }

                        }
                        catch(NumberFormatException exception)
                        {
                            ((NumericQ)questionObject).clearAnswer();
                            toast.show();
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
            else
                answer.setEnabled(false);

        }

        /**
         * Metoda pomocnicza, która modyfikuje sposób wyświetlania odpowiedzi, tak by był on bardziej
         * przyjazny dla użytkownika.
         *
         * @param answer wartość odpowiedzi w postaci tekstu, który należy zmodyfikować
         * @return przetworzony tekst opowiedzi
         */
        private String numericAnswerTransform(String answer)
        {
            String[] numberParts=answer.split("\\.");
            if(numberParts[numberParts.length-1].equals("0"))
                return numberParts[0];
            else
                return answer;
        }
    }


    /**
     * Klasa będąca rozszerzeniem klasy {@link ViewHolder}. Zarządza elementami związanymi z widokiem pytania
     * typu {@link TrueFalseQ}.
     *
     */
    public class ViewHolderTrueFalseQ extends ViewHolder {
        private final TextView question;
        private final CheckBox answer;
        private Question questionObject;

        /**
         * Kontruktor pobierający kontrolki z widoku pojedynczego elementu listy.
         *
         * @param view widok pojedynczego elementu listy
         */
        ViewHolderTrueFalseQ(View view) {
            super(view);

            question =  view.findViewById(R.id.questionTextTF);
            answer =  view.findViewById(R.id.answerBox);
        }

        /**
         * Metoda wywoływana w celu wyświetlenia danych konkretnego elementu listy. Powoduje modyfikację
         * elementów przetrzymywanych w ViewHolderze zgodnie z danymi przechowywanymi w podanym jako parametr pytaniu.
         *
         * @param question pytanie, które ma zostać wyświetlone przy pomocy elmenetów ViewHoldera
         */
        @Override
        public void bindType(Question question) {
            questionObject = question;
            this.question.setText(((TrueFalseQ)question).getText());
            this.question.setTextColor(ContextCompat.getColor( context,textColor(questionObject)));
            this.answer.setChecked(((TrueFalseQ)questionObject).getAnswer());

            if(editable)
            {
                answer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                        ((TrueFalseQ)questionObject).setAnswer(answer.isChecked());
                    }
                });
            }
            else
                answer.setEnabled(false);

        }
    }

    /**
     * Klasa będąca rozszerzeniem klasy {@link ViewHolder}. Zarządza elementami związanymi z widokiem pytania
     * typu {@link BulletedQ}.
     *
     */
    public class ViewHolderBulletedQ extends ViewHolder {
        private final TextView question;
        private final RecyclerView answers;
        private BulletedAnswerAdapter aAdapter;
        private Question questionObject;
        private int answerID;

        /**
         * Kontruktor pobierający kontrolki z widoku pojedynczego elementu listy.
         *
         * @param view widok pojedynczego elementu listy
         */
        ViewHolderBulletedQ(View view) {
            super(view);

            question =  view.findViewById(R.id.questionTextB);
            answers =  view.findViewById(R.id.answer_recyclerview);
        }

        /**
         * Metoda wywoływana w celu wyświetlenia danych konkretnego elementu listy. Powoduje modyfikację
         * elementów przetrzymywanych w ViewHolderze zgodnie z danymi przechowywanymi w podanym jako parametr pytaniu.
         *
         * @param question pytanie, które ma zostać wyświetlone przy pomocy elmenetów ViewHoldera
         */
        @Override
        public void bindType(Question question) {
            questionObject = question;
            this.question.setText(((BulletedQ)question).getText());
            this.question.setTextColor(ContextCompat.getColor( context,textColor(questionObject)));

            // Set the adapter
            if (answers != null) {
                Context context = answers.getContext();

                aAdapter = new BulletedAnswerAdapter(((BulletedQ)question).getAnswers(),((BulletedQ)questionObject).getAnswer(),context);
                RecyclerView.LayoutManager layout = new LinearLayoutManager(context);
                layout.setAutoMeasureEnabled(true);
                answers.setLayoutManager(layout);
                answers.setAdapter(aAdapter);
                if(editable)
                {
                    answers.addOnItemTouchListener(new RecyclerClickListener( context, new ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            answerID = ((BulletedQ)questionObject).getAnswers().get(position).getId();
                            ((BulletedQ)questionObject).setAnswer(answerID);
                            aAdapter.SetAnswerID(answerID);
                            aAdapter.clearColors();
                            aAdapter.color(view,answerID);
                        }
                    }));
                }


            }
        }
    }

    /**
     * Metoda zwracająca id zasobu z kolorem, w którym ma być wyświetlona treść pytania ustawionym na podstawie wagi pytania.
     *
     * @param question pytanie, dla którego chcemy określić kolor tekstu
     * @return id zasobu wybranego koloru
     */
    private int textColor(Question question)
    {
        switch(question.getStrength())
        {
            case NEUTRAL:
                return R.color.colorPrimary;
            case WEAK:
                return R.color.weakQuestion;
            case MIDSTRONG:
                return R.color.midstrongQuestion;
            case STRONG:
                return R.color.colorAccentDark;
            default:
                return R.color.colorPrimary;
        }
    }

    /**
     * Konstruktor ustawiający listę pytań formularza wykorzystywaną przez adapter do wyświetlenia we fragmencie.
     *
     * @param context kontekst aplikacji
     * @param questions lista pytań, która powinna zostać wyświetlona we fragmencie
     * @param editable informacja o tym, czy formularz ma być edytowalny (true - formularz edytowalny; false
     *                 - formularz jedynie do podglądu)
     */
    QuestionAdapter(Context context, final List<Question> questions, boolean editable) {
        this.questions = questions;
        this.context = context;
        this.editable = editable;
    }

    /**
     * Metoda zwracająca typ pytania na pozycji podanej jako parametr.
     *
     * @param position pozycja pytania na liście pytań
     * @return typ pytania
     */
    @Override
    public int getItemViewType(int position) {
        return questions.get(position).getListItemType();
    }

    /**
     * Metoda pobierająca widok pojedynczego elementu RecyclerView i generująca nowy obiekt odpowiedniej
     * klasy {@link ViewHolder} w zależności od typu pytania.
     *
     * @param parent grupa, do której dodany zostanie widok po jego utworzeniu
     * @param viewType rodzaj widoku związany z rodzajem pytania
     * @return nowoutworzony ViewHolder, który przechowuje widok podanego typu
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case Question.BULLETED:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.bulleted_question, parent, false);
                return new ViewHolderBulletedQ(view);
            case Question.DESCRIPTIVE:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.descriptive_question, parent, false);
                return new ViewHolderDescriptiveQ(view);
            case Question.TRUEFALSE:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.true_false_question, parent, false);
                return new ViewHolderTrueFalseQ(view);
            case Question.NUMERIC:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.numeric_question, parent, false);
                return new ViewHolderNumericQ(view);

        }
        return null;
    }

    /**
     * Metoda wywoływana w celu wyświetlenia danych konkretnego elementu listy. Powoduje wywołanie
     * odpowiedniej metody {@code bindType} w zależności od typu pytania na podanej pozycji.
     *
     * @param holder obiekt klasy ViewHolder, który powinien zostać zaktualizowany, tak by przechowywał dane zawarte w
     *               obiekcie znajdującym się na podanej pozycji w liście wszystkich elementów zarządzanych przez adapter
     * @param position pozycja obiektu na liście wszystkich elementów zarządzanych przez adapter
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Question question = questions.get(position);
        holder.bindType(question);
    }

    /**
     * Metoda pobierająca liczbę elementów listy do wyświetlenia.
     *
     * @return liczba pytań wyświetlanych we fragmencie
     */
    @Override
    public int getItemCount() {
        return questions.size();
    }


    /**
     * Metoda pobierająca odpowiedzi na pytania wprowadzone przez użytkownika.
     *
     * @param form typ formularza, dla którego zwracane są odpowiedzi
     * @return lista odpowiedzi użytkownika na pytania formularza
     */
    List<Answer> returnAnswers(Form form)
    {
        List<Answer> answers = new ArrayList<>();
        for(Question q:questions)
        {
            Answer answer = new Answer(-1);//no answer
            if(q instanceof DescriptiveQ)
            {
                answer = new TextAnswer(((DescriptiveQ) q).getID());
                ((TextAnswer)answer).Value= ((DescriptiveQ) q).getAnswer();
            }
            else if (q instanceof TrueFalseQ)
            {
                answer = new TrueFalseAnswer(((TrueFalseQ) q).getId());
                ((TrueFalseAnswer)answer).Value = ((TrueFalseQ) q).getAnswer();
            }
            else if (q instanceof BulletedQ)
            {
                answer = new NumericAnswer(((BulletedQ) q).getID());
                if(form == Form.NIHSS && ((BulletedQ) q).getAnswer()==-1)
                    ((NumericAnswer)answer).Value = 0;
                else
                    ((NumericAnswer)answer).Value = ((BulletedQ) q).getAnswer();
            }
            else if (q instanceof NumericQ)
            {
                answer = new NumericAnswer(((NumericQ) q).getID());
                if(form==Form.NIHSS && !((NumericQ) q).getAnswerSet())
                    ((NumericAnswer) answer).Value = 0;
                else
                    ((NumericAnswer) answer).Value = ((NumericQ) q).getAnswer();
            }
            answers.add(answer);
        }
        return answers;
    }
}
