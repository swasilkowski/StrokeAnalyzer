package com.example.asus.strokeanalyzer.View.Form;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormFragment extends Fragment {

    private RecyclerView recyclerView;
    private QuestionAdapter qAdapter;
    private Form formType;
    private Patient patient;
    private Integer patientID;
    private boolean creatingPatient;
    private boolean newForm;
    private List<Answer> answers = new ArrayList<>();
    private List<Question> printQuestions = new ArrayList<>();
    private List<com.example.asus.strokeanalyzer.Model.Form.Question.Question> questions = new ArrayList<>();
    PatientService patientService;

    public static FormFragment newInstance(Form form, long patientID, boolean create, boolean newForm) {
        FormFragment fragment = new FormFragment();
        fragment.formType = form;
        fragment.patientID = (int)patientID;
        fragment.creatingPatient = create;
        fragment.newForm = newForm;
        //----------zmienic----------------
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_form, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        recyclerView = (RecyclerView) view;

        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(formType.Print());

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            patientService = new PatientService(context);
            patient = patientService.GetPatientById(patientID);


            if(newForm==true && formType == Form.NIHSS)
            {
                clearPreviousAnswers();
            }

            //get questions list
            List<Integer> questionIDs = FormsStructure.QuestionsPrintedInForm.get(formType);

            //zmienia sie rozmiar na dwa razy wieksze
            prepareQuestions(questionIDs);



            qAdapter = new QuestionAdapter(printQuestions, answers,context);
            RecyclerView.LayoutManager layout = new LinearLayoutManager(context);
            layout.setAutoMeasureEnabled(true);
            recyclerView.setLayoutManager(layout);
            /*recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new LineDecoration(context, LinearLayoutManager.VERTICAL));
            ItemTouchHelper.Callback callback =
                    new SwipeHelperCallback(nAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);*/
            recyclerView.setAdapter(qAdapter);
            recyclerView.addItemDecoration(new LineDecoration(this.getContext()));

           /* recyclerView.addOnItemTouchListener(new RecyclerClickListener( getActivity().getApplicationContext(), recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    // Creating Bundle object
                    Bundle bundel = new Bundle();

                    // Storing data into bundle
                    Patient patient = patients.get(position);
                    bundel.putInt(getString(R.string.patient_number_tag), patient.PatientNumber);

                    //print dialog with actions for patient
                    DialogFragment dialog = new PatientsListActionFragment();
                    dialog.setArguments(bundel);
                    dialog.show(getActivity().getSupportFragmentManager(), "PatientsListActionFragment");

                }
            }));
*/
        }

        return recyclerView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.form, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

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

            //List<Fragment> currentStackState =  getFragmentManager().getFragments();
            getFragmentManager().popBackStack();

            //if we were creating a new patient we need to clear backstack and put there list of patients and our patient profile
/*            List<Fragment> currentStackState =  getFragmentManager().getFragments();
            if(creatingPatient)
            {
                getFragmentManager().popBackStack(getString(R.string.new_patient_tag), POP_BACK_STACK_INCLUSIVE);

                PatientsListFragment listFragment= new PatientsListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentFrame, listFragment, null)
                        .addToBackStack(null)
                        .commit();

                PatientProfileFragment setFragment= PatientProfileFragment.newInstance(patient.Id);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentFrame, setFragment, null)
                        .addToBackStack(null)
                        .commit();

            }*/

/*            //przejdz do nazwania nowego rankingu
            NewRankingFragment setFragment= new NewRankingFragment();
            getActivity().getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentFrame, setFragment, null)
                    .addToBackStack(null)
                    .commit();

            //((FloatingActionButton) getView().findViewById(R.id.fab)).hide();*/
            // currentStackState =  getFragmentManager().getFragments();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView ()
    {
        super.onDestroyView();
        SaveAnswers();
        patientService.UpdatePatient(patient);
        if(formType == Form.NIHSS)
            return;

        getFragmentManager().popBackStack("forms_list", POP_BACK_STACK_INCLUSIVE);
    }


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

            questions.add(question);
            printQuestions.add(printedQuestion);
        }
    }

    private void clearPreviousAnswers()
    {
        List<Integer> questions = new ArrayList<>(FormsStructure.QuestionsUsedForForm.get(formType));
        questions.addAll(FormsStructure.QuestionsPrintedInForm.get(formType));

        for(Integer id:questions)
        {
            if(patient.PatientAnswers.containsKey(id));
                patient.PatientAnswers.remove(id);
        }
    }

    public void SaveAnswers()
    {
        answers = qAdapter.returnAnswers();
        for(Answer ans:answers)
        {
            if(patient.PatientAnswers.containsKey(ans.GetQuestionID()))
                patient.PatientAnswers.remove(ans.GetQuestionID());

            patient.PatientAnswers.put(ans.GetQuestionID(),ans);
        }

    }

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


    /*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FormFragment() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormFragment.
     *//*
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

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
