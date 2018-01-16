package com.example.asus.strokeanalyzer.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.example.asus.strokeanalyzer.Model.Form.Answer.NumericAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TextAnswer;
import com.example.asus.strokeanalyzer.Model.Form.Answer.TrueFalseAnswer;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.Model.results.iScoreResult;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.CTPictures.CTPicturesFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT_ID = "patient_id";

    private Patient patient;
    private Integer patientID;
    private PatientService patientService;

    public ResultsFragment() {
        // Required empty public constructor
    }

    public static ResultsFragment newInstance(Integer patientID) {
        ResultsFragment fragment = new ResultsFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PATIENT_ID, patientID);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            patientID = getArguments().getInt(ARG_PATIENT_ID);
        }

    }

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

        patientService = new PatientService(view.getContext());
        patient = patientService.GetPatientById(patientID);

        //perform operations before showin result
        //preparePatient(patient);

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
            ((TextView) view.findViewById(R.id.iscore30Days)).setText(String.valueOf(resultiScore.PrognosisFor30Days));
            ((TextView) view.findViewById(R.id.iscore1Year)).setText(String.valueOf(resultiScore.PrognosisFor1Year));
        }



        //button leading to CT pictures
        final Button CTPicturesBt= view.findViewById(R.id.CTPicutresBt);
        CTPicturesBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showCTPictures(CTPicturesBt);
            }
        });

        return view;
    }

    public void showCTPictures(View view)
    {
        CTPicturesFragment setFragment= CTPicturesFragment.newInstance(patientID);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame, setFragment, null)
                .addToBackStack(null)
                .commit();
    }

    private String wrongAnswersText(List<Answer> answers)
    {
        StringBuilder text = new StringBuilder();

        for(Answer ans:answers)
        {
            text.append(FormsStructure.Questions.get(ans.GetQuestionID()).GetText());
            text.append("  ");
            text.append(answerText(ans));
            text.append("\n");
        }

        return text.toString();
    }

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
    }

/*
    private OnFragmentInteractionListener mListener;



    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultsFragment.
     *//*
    // TODO: Rename and change types and number of parameters




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
