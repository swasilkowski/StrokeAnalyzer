package com.example.asus.strokeanalyzer.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.asus.strokeanalyzer.Model.Analyzers.StrokeBricksAnalyzer;
import com.example.asus.strokeanalyzer.Model.EnumValues.Region;
import com.example.asus.strokeanalyzer.Model.results.DragonResult;
import com.example.asus.strokeanalyzer.Model.results.HatResult;
import com.example.asus.strokeanalyzer.Model.results.TreatmentResult;
import com.example.asus.strokeanalyzer.Model.Form.Answer.Answer;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.Model.results.iScoreResult;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.CTPictures.CTPicturesFragment;

import java.util.List;

/**
 * Klasa będąca podklasą {@link Fragment}. Fragment wyświetla zgromadzone wyniki wszystkich skal wykorzystywanych w aplikacji
 * dla konkretnego pacjenta.
 * Do stworzenia instancji tego fragmentu należy wykorzystać metodę {@link FormListFragment#newInstance}.
 *
 * @author Marta Marciszewicz
 */
public class ResultsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT_ID = "patient_id";

    private Integer patientID;
    FragmentActivity activity;

    /**
     * Publiczny konstruktor bezparametrowy - jest wymagany, ale nie jest wykorzystywany
     */
    public ResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów.
     *
     * @param patientID Id pacjenta, które wyniki analizy skal mają zostać wyświetlone
     * @return (ResultsFragment) nowa instancja fragmentu ResultsFragment
     */
    public static ResultsFragment newInstance(Integer patientID) {
        ResultsFragment fragment = new ResultsFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PATIENT_ID, patientID);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Metoda wołana w celu zainicjowania tworzenia fragmentu. Metoda ustawia wartość pól klasy przekazane
     * jako argumenty poprzez {@link Bundle}. Dodatkowo zapisuje obiekt aktywności fragmentu.
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
        activity=getActivity();
    }

    /**
     * Metoda pozwalająca na zainicjowanie interfejsu użytkownika dla fragmentu. Funkcja oprócz wstrzyknięcia widoku
     * fragmentu pobiera wyniki poszczególnych skal dla pacjenta, zamieszcza otrzymane wartości w konkretnych
     * elementach widoku i ustawia akcje przycisku przechodzącego do zdjęć CT mózgu.
     *
     * @param inflater obiekt umożliwiający wstrzyknięcie widoku do fragmentu
     * @param container widok-rodzic, do którego powinien być podpięty UI fragmentu
     * @param savedInstanceState  poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     * @return (View) widok interfejsu użytkownika fragmentu (może przyjąć wartość null)
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        view.setBackgroundColor(getResources().getColor(R.color.colorBackground, null));
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity!=null)
        {
            ActionBar bar =  activity.getSupportActionBar();
            if(bar!=null)
            {
                bar.setTitle("Wyniki");
            }
        }

        PatientService patientService = new PatientService(view.getContext());
        Patient patient = patientService.GetPatientById(patientID);

        //patients results
        int nihssSum =patient.getNihss();
        if(nihssSum>=0)
            ((TextView) view.findViewById(R.id.nihssSum)).setText(String.valueOf(nihssSum));

        List<Region> regions = patient.getStrokeBricksAffectedRegions();
        if(regions!=null)
            ((TextView) view.findViewById(R.id.sbDescription)).setText(StrokeBricksAnalyzer.CreateStrokeRangeDescription(regions));

        TreatmentResult treatment = patient.getTreatmentDecision();
        if(treatment!=null)
        {
            ((TextView) view.findViewById(R.id.treatmentDecision)).setText(treatment.Decision?"Zalecane":"NIE zalecane");
            ((TextView) view.findViewById(R.id.wrongAnswers)).setText(wrongAnswersText(treatment.badAnswers));
        }

        HatResult resultHat = patient.getHatPrognosis();
        if(resultHat!=null)
        {
            ((TextView) view.findViewById(R.id.hatScore)).setText(String.valueOf(resultHat.Score));
            ((TextView) view.findViewById(R.id.hatSymptomaticICH)).setText(String.valueOf(resultHat.RiskOfSymptomaticICH));
            ((TextView) view.findViewById(R.id.hatFatalICH)).setText(String.valueOf(resultHat.RiskOfFatalICH));
        }

        DragonResult resultDragon = patient.getDragonPrognosis();
        if(resultDragon!=null)
        {
            ((TextView) view.findViewById(R.id.dragonScore)).setText(String.valueOf(resultDragon.Score));
            ((TextView) view.findViewById(R.id.dragonGoodOutcomePrognosis)).setText(String.valueOf(resultDragon.GoodOutcomePrognosis));
            ((TextView) view.findViewById(R.id.dragonMiserableOutcomePrognosis)).setText(String.valueOf(resultDragon.MiserableOutcomePrognosis));
        }

        iScoreResult resultiScore = patient.getIscorePrognosis();
        if(resultiScore!=null)
        {
            ((TextView) view.findViewById(R.id.iscore30Days)).setText(String.valueOf(resultiScore.ScoreFor30Days));
            ((TextView) view.findViewById(R.id.iscore1Year)).setText(String.valueOf(resultiScore.ScoreFor1Year));
        }

        //button leading to CT pictures
        final Button CTPicturesBt= view.findViewById(R.id.CTPicutresBt);
        CTPicturesBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showCTPictures();
            }
        });

        return view;
    }

    /**
     * Metoda dokonująca przejścia do fragmentu wyświetlającego zdjęcia CT mózgu
     */
    public void showCTPictures()
    {
        if(activity!=null)
        {
            CTPicturesFragment setFragment= CTPicturesFragment.newInstance(patientID);
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentFrame, setFragment, null)
                    .addToBackStack(null)
                    .commit();
        }

    }

    /**
     * Metoda generuje tekst zawierający wszystkie pytania, na które udzielona przez użytkownika odpowiedź
     * była nieprawidłowa i spowodowała wykluczenie pacjenta z leczenia trombolitycznego.
     *
     * @param answers lista odpowiedzi, które wpłynęły na wykluczenie pacjenta z leczenia trombolitycznego
     * @return (String) zgrupowany tekst wszystkich błędnych pytań
     */
    private String wrongAnswersText(List<Answer> answers)
    {
        StringBuilder text = new StringBuilder();

        for(Answer ans:answers)
        {
            text.append(FormsStructure.Questions.get(ans.GetQuestionID()).GetText());
            text.append("\n");
        }

        return text.toString();
    }

/* ----TODO------usun
    private String answerText(Answer answer)
    {
        if(answer instanceof NumericAnswer)
        {
            return String.valueOf(((NumericAnswer) answer).Value);
        }
        else if(answer instanceof TextAnswer)
        {
            return ((TextAnswer) answer).Value;
        }
        else if(answer instanceof TrueFalseAnswer)
        {
            return ((TrueFalseAnswer) answer).Value?"Tak":"Nie";
        }
        return "Brak odpowiedzi";
    }*/

}
