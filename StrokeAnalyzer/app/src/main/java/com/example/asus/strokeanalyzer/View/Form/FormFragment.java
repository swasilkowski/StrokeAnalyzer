package com.example.asus.strokeanalyzer.View.Form;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.Form.Question.BulletedQuestion;
import com.example.asus.strokeanalyzer.Model.Form.Question.DescriptiveQuestion;
import com.example.asus.strokeanalyzer.Model.Form.Question.TrueFalseQuestion;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.NewPatientFragment;
import com.example.asus.strokeanalyzer.View.Patient.PatientsListFragment;
import com.example.asus.strokeanalyzer.View.PatientProfileFragment;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Question> printQuestions = new ArrayList<>();
    private List<com.example.asus.strokeanalyzer.Model.Form.Question.Question> questions = new ArrayList<>();

    public static FormFragment newInstance(Form form, long patientID) {
        FormFragment fragment = new FormFragment();
        fragment.formType = form;
        PatientService patientService = new PatientService(fragment.getContext());
        fragment.patient = patientService.GetPatientById((int)patientID);
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

        DescriptiveQuestion p1 = new DescriptiveQuestion(1,"Czy masz zielone oczy?");
        TrueFalseQuestion p2 = new TrueFalseQuestion(2, "Czy myjesz zęby?");
        DescriptiveQuestion p3 = new DescriptiveQuestion(3, "Jaki masz kolor skarpetek?");
        Map<Integer,String> tmp = new HashMap<Integer,String>();
        tmp.put(1, "5");
        tmp.put(2, "7");
        tmp.put(3, "3");
        BulletedQuestion p4 = new BulletedQuestion(4, "Ile masz lat?",tmp );

        questions.add(p1);
        questions.add(p2);
        questions.add(p3);
        questions.add(p4);

        DescriptiveQ q1 = new DescriptiveQ(p1.GetID(), p1.GetText());
        TrueFalseQ q2 = new TrueFalseQ(p2.GetID(),p2.GetText());
        DescriptiveQ q3 = new DescriptiveQ(p3.GetID(),p3.GetText());
        BulletedQ q4 = new BulletedQ(p4.GetID(), p4.GetText(), tmp);

        printQuestions.add(q1);
        printQuestions.add(q2);
        printQuestions.add(q3);
        printQuestions.add(q4);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_form, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        recyclerView = (RecyclerView) view;

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            //get questions list
            //List<Integer> questionIDs = FormsStructure.QuestionsPrintedInForm.get(formType);
            //............................

            qAdapter = new QuestionAdapter(printQuestions,context);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            /*recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItem(context, LinearLayoutManager.VERTICAL));
            ItemTouchHelper.Callback callback =
                    new SwipeHelperCallback(nAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);*/
            recyclerView.setAdapter(qAdapter);

           /* recyclerView.addOnItemTouchListener(new RecyclerTouchListener( getActivity().getApplicationContext(), recyclerView, new ClickListener() {
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

            //List<Fragment> currentStackState =  getFragmentManager().getFragments();
            getFragmentManager().popBackStack();

            //if we were creating a new patient we need to clear backstack and put there list of patients and our patient profile
            List<Fragment> currentStackState =  getFragmentManager().getFragments();
            if(newPatient(currentStackState))
            {
                getFragmentManager().popBackStack(getString(R.string.new_patient_tag), POP_BACK_STACK_INCLUSIVE);

                PatientsListFragment listFragment= new PatientsListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentFrame, listFragment, null)
                        .addToBackStack(null)
                        .commit();

                PatientProfileFragment setFragment= PatientProfileFragment.newInstance(patient);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentFrame, setFragment, null)
                        .addToBackStack(null)
                        .commit();

            }

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

    private boolean newPatient(List<Fragment> fragments)
    {


        for(Fragment f: fragments)
        {
            if(f instanceof NewPatientFragment)
                return true;
        }
        return false;
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
