package com.example.asus.strokeanalyzer.View.Form;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TextAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TrueFalseAnswer;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.Form.Question.BulletedQuestion;
import com.example.asus.strokeanalyzer.Model.Form.Question.DescriptiveQuestion;
import com.example.asus.strokeanalyzer.Model.Form.Question.NumericQuestion;
import com.example.asus.strokeanalyzer.Model.Form.Question.TrueFalseQuestion;
import com.example.asus.strokeanalyzer.Model.NihssExamination;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.Helpers.LineDecoration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

/**
 * Klasa będąca rozszerzeniem klasy {@link Fragment}. Pozwala na wyświetlenie listy pytań wybranego przez użytkownika
 * formularza, ich uzupełnienie oraz zapisanie zebranych danych w bazie danych.
 * Do stworzenia instancji tego fragmentu należy wykorzystać metodę {@link FormFragment#newInstance}.
 */
public class FormFragment extends Fragment {

    private RecyclerView recyclerView; //list of questions
    private QuestionAdapter qAdapter; //questions list adapter
    private Form formType; //typ of a form
    private Patient patient; //patient whose data is supposed to be changed
    private Integer patientID; //id of this patient
    private boolean newForm; //true if new form is being created
    final private List<Question> printQuestions = new ArrayList<>(); // list of questions printed in this form
    private PatientService patientService; //serwis used for communication with database

    /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów.
     *
     * @param form typ formularza, który ma zostać wyświetlony we fragmencie
     * @param patientID id pacjenta, którego dane będą modyfikowane w formularzu
     * @param newForm wartość informująca o tym, czy formularz tworzony jest na nowo (true - jeżeli
     *                formularz będzie tworzony na nowo; false - jeżeli następuje edycja formularza)
     * @return nowa instancja fragmentu FormFragment
     */
    public static FormFragment newInstance(Form form, long patientID, boolean newForm) {
        FormFragment fragment = new FormFragment();
        fragment.formType = form;
        fragment.patientID = (int)patientID;
        fragment.newForm = newForm;
        return fragment;
    }

    /**
     * Metoda wołana w celu zainicjowania tworzenia fragmentu.
     *
     * @param savedInstanceState poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
     * Metoda pozwalająca na zainicjowanie interfejsu użytkownika dla fragmentu. Funkcja oprócz wstrzyknięcia widoku
     * fragmentu inicjalizuje obiekt klasy RecyclerView odpowiedzialny za prezentację listy pytań formularza przy wykorzystaniu
     * klasy {@link QuestionAdapter}.
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

        View view = inflater.inflate(R.layout.fragment_form, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.colorBackground, null));

        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity!=null)
        {
            ActionBar bar =  activity.getSupportActionBar();
            if(bar!=null)
            {
                bar.show();
                bar.setTitle(formType.Print());
            }
        }

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            patientService = new PatientService(context);
            patient = patientService.GetPatientById(patientID);

            if(newForm && formType == Form.NIHSS)
            {
                clearPreviousAnswers();
            }

            //get questions list
            List<Integer> questionIDs = FormsStructure.QuestionsPrintedInForm.get(formType);

            //zmienia sie rozmiar na dwa razy wieksze
            prepareQuestions(questionIDs);

            qAdapter = new QuestionAdapter(printQuestions);
            RecyclerView.LayoutManager layout = new LinearLayoutManager(context);
            layout.setAutoMeasureEnabled(true);
            recyclerView.setLayoutManager(layout);
            recyclerView.setAdapter(qAdapter);
            recyclerView.addItemDecoration(new LineDecoration(this.getContext()));
        }

        return recyclerView;
    }

    /**
     * Metoda umożliwiająca zainicjowanie standardowego menu aktywności.
     *
     * @param menu obiekt klasy Menu, w którym powinna znajdować się definicja menu dla
     *             tego fragmentu
     * @param inflater obiekt klasy MenuInflater pozwalający na pozyskanie menu z zasobów aplikacji
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.form, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    /**
     * Metoda wywoływana w momencie wyboru przez użytkownika jednej z opcji w menu fragmentu.
     * Funkcja jest odpowiedzialna za zapisanie odpowiedzi użytkownika na pytania formularza oraz
     * powrót do widoku profilu pacjenta.
     *
     * @param item elementu menu, który został wybrany przez użytkownika
     * @return false - jeżeli element menu ma być przetworzony standardowo;
     *          true - jeżeli element menu został obsłużony wewnątrz funkcji
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.done) {

            //saving patients answers
            SaveAnswers();
            if(formType==Form.NIHSS)
            {
                SaveExamination();
            }
            patientService.UpdatePatient(patient);

            FragmentManager manager = getFragmentManager();
            if(manager!=null)
            {
                manager.popBackStack();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Metoda wywoływana w momencie, gdy widok został odłączony od fragmentu. Funkcja odpowiedzialna
     * jest za zapisanie odpowiedzi użytkownika w jego profilu oraz aktualizację bazy danych. Dodatkowo
     * aplikacja wykorzystuje tę metodę do usunięcia dodatkowego fragmentu ze stosu fragmentów.
     *
     */
    @Override
    public void onDestroyView ()
    {
        super.onDestroyView();
        SaveAnswers();
        patientService.UpdatePatient(patient);
        if(formType == Form.NIHSS)
            return;

        FragmentManager manager = getFragmentManager();
        if(manager!=null)
        {
            manager.popBackStack("forms_list", POP_BACK_STACK_INCLUSIVE);
        }
    }

    /**
     * Metoda generująca obiekty klasy {@link Question} wykorzystywane w widoku
     * z obiektów klasy {@link com.example.asus.strokeanalyzer.Model.Form.Question.Question}
     * Funkcja sprawdza dodatkowo, czy użytkownik odpowiedział już na dane pytanie i ustawia ewentualną wybraną przez
     * niego odpowiedź.
     *
     * @param questionIDs lista id pytań, które powinny zostać wyświetlone w formularzu
     */
    private void prepareQuestions(List<Integer> questionIDs)
    {
        if(questionIDs ==null) return;
        for(Integer id: questionIDs)
        {
            com.example.asus.strokeanalyzer.Model.Form.Question.Question question = FormsStructure.Questions.get(id);
            Question printedQuestion = null;

            if(question instanceof DescriptiveQuestion)
            {
                printedQuestion = new DescriptiveQ(question.GetID(), question.GetText());
                if(patient.PatientAnswers.containsKey(question.GetID()))
                    ((DescriptiveQ)printedQuestion).setAnswer(((TextAnswer)patient.PatientAnswers.get(question.GetID())).Value);
            }
            else if(question instanceof TrueFalseQuestion)
            {
                printedQuestion = new TrueFalseQ(question.GetID(), question.GetText());
                if(patient.PatientAnswers.containsKey(question.GetID()))
                    ((TrueFalseQ)printedQuestion).setAnswer(((TrueFalseAnswer)patient.PatientAnswers.get(question.GetID())).Value);
            }
            else if(question instanceof BulletedQuestion)
            {
                printedQuestion = new BulletedQ(question.GetID(), question.GetText(), ((BulletedQuestion) question).GetPosiibleValues());
                if(patient.PatientAnswers.containsKey(question.GetID()))
                    ((BulletedQ)printedQuestion).setAnswer((int)((NumericAnswer)patient.PatientAnswers.get(question.GetID())).Value);
            }
            else if(question instanceof NumericQuestion)
            {
                printedQuestion = new NumericQ(question.GetID(), question.GetText(), ((NumericQuestion) question).Range);
                if(patient.PatientAnswers.containsKey(question.GetID()))
                    ((NumericQ)printedQuestion).setAnswer(((NumericAnswer)patient.PatientAnswers.get(question.GetID())).Value);
            }

            printQuestions.add(printedQuestion);
        }
    }

    /**
     * Metoda usuwająca odpowiedzi użytkownika na pytania formularza w momencie, gdy formularz tworzony jest na nowo.
     */
    private void clearPreviousAnswers()
    {
        List<Integer> questions = new ArrayList<>(FormsStructure.QuestionsUsedForForm.get(formType));
        questions.addAll(FormsStructure.QuestionsPrintedInForm.get(formType));

        for(Integer id:questions)
        {
            if(patient.PatientAnswers.containsKey(id))
                patient.PatientAnswers.remove(id);
        }
    }

    /**
     * Metoda zapisująca w obiekcie klasy {@link Patient} odpowiedzi udzielone przez użytkownika na
     * pytania formularza.
     */
    private void SaveAnswers()
    {
        List<Answer> answers = qAdapter.returnAnswers();
        for(Answer ans: answers)
        {
            if(patient.PatientAnswers.containsKey(ans.GetQuestionID()))
                patient.PatientAnswers.remove(ans.GetQuestionID());

            patient.PatientAnswers.put(ans.GetQuestionID(),ans);
        }

    }

    /**
     * Metoda zapisująca w obiekcie klasy {@link Patient} nowe badanie w skali NIHSS.
     */
    private void SaveExamination()
    {
        NihssExamination examination = new NihssExamination();
        examination.Date = Calendar.getInstance().getTime();
        examination.Answers  = new ArrayList<>();

        List<Integer> questionIDs = FormsStructure.QuestionsUsedForForm.get(Form.NIHSS);
        for(int i=0;i<questionIDs.size();i++) {
            Answer userAnswer = patient.PatientAnswers.get(questionIDs.get(i));
            examination.Answers.add(userAnswer);
        }

        patient.addNihssExamination(examination);
    }
}
