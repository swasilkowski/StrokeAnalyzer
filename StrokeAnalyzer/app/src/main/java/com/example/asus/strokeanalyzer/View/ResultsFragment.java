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
 * Klasa będąca rozszerzeniem klasy {@link Fragment}. Pozwala na wyświetlenie zgromadzonych wyników
 * wszystkich skal wykorzystywanych w aplikacji dla konkretnego pacjenta.
 * Do stworzenia instancji tego fragmentu należy wykorzystać metodę {@link FormListFragment#newInstance}.
 *
 * @author Marta Marciszewicz
 */
public class ResultsFragment extends Fragment {

    /**
     * Zmienna przechowująca klucz, który pozwala na zapisanie i pobranie danych z obiektu klasy
     * {@link Bundle} wykorzystywanego do przekazania parametrów fragmentu.
     */
    private static final String ARG_PATIENT_ID = "patient_id";

    /**
     * Id pacjenta, którego wyniki mają zostać wyświetlone.
     */
    private Integer patientID;
    /**
     * Aktywność związana z fragmentem.
     */
    private FragmentActivity activity;

    /**
     * Publiczny konstruktor bezparametrowy - jest wymagany, ale nie jest wykorzystywany.
     */
    public ResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów.
     *
     * @param patientID id pacjenta, którego wyniki analizy skal mają zostać wyświetlone
     * @return nowa instancja fragmentu ResultsFragment
     */
    public static ResultsFragment newInstance(Integer patientID) {
        ResultsFragment fragment = new ResultsFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PATIENT_ID, patientID);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Metoda wołana w celu zainicjowania tworzenia fragmentu. Metoda ustawia wartości pól klasy przekazane
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
     * Metoda pozwalająca na zainicjowanie interfejsu użytkownika dla fragmentu.
     *
     * @param inflater obiekt umożliwiający wstrzyknięcie widoku do fragmentu
     * @param container widok-rodzic, do którego powinien być podpięty UI fragmentu
     * @param savedInstanceState  poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     * @return widok interfejsu użytkownika fragmentu (może przyjąć wartość null)
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
            ((TextView) view.findViewById(R.id.iscore30Days)).setText(String.valueOf(resultiScore.PrognosisFor30DaysDescription));
            ((TextView) view.findViewById(R.id.iscore1Year)).setText(String.valueOf(resultiScore.PrognosisFor1YearDescription));
            ((TextView) view.findViewById(R.id.iscoreTreatment)).setText(String.valueOf(resultiScore.ThrombolyticRecommendation));

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
     * Metoda dokonująca przejścia do fragmentu wyświetlającego zdjęcia CT mózgu.
     */
    private void showCTPictures()
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
     * Metoda pomocnicza generująca tekst zawierający wszystkie pytania, na które udzielona przez użytkownika odpowiedź
     * była nieprawidłowa i spowodowała wykluczenie pacjenta z leczenia trombolitycznego.
     *
     * @param answers lista odpowiedzi, które wpłynęły na wykluczenie pacjenta z leczenia trombolitycznego
     * @return wygenerowany tekst składający się ze wszystkich pytań, dla których udzielona została błędna odpowiedź
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

}
