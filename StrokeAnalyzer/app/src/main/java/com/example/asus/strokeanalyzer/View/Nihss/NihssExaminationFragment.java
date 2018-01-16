package com.example.asus.strokeanalyzer.View.Nihss;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.example.asus.strokeanalyzer.Model.NihssExamination;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.Helpers.LineDecoration;
import com.example.asus.strokeanalyzer.View.Form.FormFragment;
import com.example.asus.strokeanalyzer.View.Helpers.ClickListener;
import com.example.asus.strokeanalyzer.View.Helpers.RecyclerClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.

 * create an instance of this fragment.
 */
public class NihssExaminationFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT_ID = "patient_id";

    private RecyclerView recyclerView;
    private NihssAdapter nAdapter;
    private List<NihssExamination> examinations = new ArrayList<>();
    PatientService patientService;
    int patientID;
    FragmentActivity activity;

    public static NihssExaminationFragment newInstance(long patientID) {
        NihssExaminationFragment fragment = new NihssExaminationFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PATIENT_ID, (int)patientID);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            patientID = getArguments().getInt(ARG_PATIENT_ID);
        }
        activity = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nihss_examination, container, false);

        view.setBackgroundColor(getResources().getColor(R.color.colorBackground, null));

        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity!=null)
        {
            ActionBar bar =  activity.getSupportActionBar();
            if(bar!=null)
            {
                bar.setTitle("Badania NIHSS");
            }
        }

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            patientService = new PatientService(getContext());

            //get patients list from database
            examinations = patientService.GetPatientById(patientID).getNihssHistory();
            Collections.reverse(examinations);

            nAdapter = new NihssAdapter(examinations,context);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            /*recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new LineDecoration(context, LinearLayoutManager.VERTICAL));
            ItemTouchHelper.Callback callback =
                    new SwipeHelperCallback(nAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);*/
            recyclerView.setAdapter(nAdapter);
            recyclerView.addItemDecoration(new LineDecoration(this.getContext()));

            recyclerView.addOnItemTouchListener(new RecyclerClickListener( getActivity().getApplicationContext(), new ClickListener() {
                @Override
                public void onClick(View view, int position) {

                   //NIC___________________TODO______________

                    if(position==0)
                    {
                        //move to proper form
                        FormFragment setFragment = FormFragment.newInstance(Form.NIHSS, patientID, false, false);
                        //move to demograhic form
                        //ResultsFragment setFragment= new ResultsFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentFrame, setFragment, null)
                                .addToBackStack(null)
                                .commit();
                    }

/*                    // Creating Bundle object
                    Bundle bundel = new Bundle();

                    // Storing data into bundle
                    Patient patient = patients.get(position);
                    bundel.putInt(getString(R.string.patient_id_tag), patient.Id);

                    //print dialog with actions for patient
                    DialogFragment dialog = new PatientsListActionFragment();
                    dialog.setArguments(bundel);
                    dialog.show(getActivity().getSupportFragmentManager(), "PatientsListActionFragment");*/

                }
            }));

        }

        return recyclerView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.examination, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {

            if(activity!=null)
            {
                //move to proper form
                FormFragment setFragment = FormFragment.newInstance(Form.NIHSS,patientID, false, true);
                //move to demograhic form
                //ResultsFragment setFragment= new ResultsFragment();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentFrame, setFragment, null)
                        .addToBackStack(null)
                        .commit();
            }

           /* //saving patients answers
            SaveAnswers();
            patientService.UpdatePatient(patient);

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
    public void onDestroy()
    {
        super.onDestroy();
        getFragmentManager().popBackStack("forms_list", POP_BACK_STACK_INCLUSIVE);
    }



   /* // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NihssExaminationFragment() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NihssExaminationFragment.
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
