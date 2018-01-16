package com.example.asus.strokeanalyzer.View;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.DialogWindows.ReportFragment;
import com.example.asus.strokeanalyzer.View.Form.FormFragment;
import com.example.asus.strokeanalyzer.View.Nihss.NihssExaminationFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PatientProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientProfileFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT_ID = "patient_id";

    TextView name;
    TextView number;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PATIENT = "patient_number";

    private Integer patientID;
    private Patient patient;
    PatientService patientService;
    FragmentActivity activity;

    //private OnFragmentInteractionListener mListener;

    public PatientProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PatientProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientProfileFragment newInstance(Integer patientID) {
        PatientProfileFragment fragment = new PatientProfileFragment();

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
        activity= getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_profile, container, false);

        view.setBackgroundColor(getResources().getColor(R.color.colorBackground, null));


        patientService = new PatientService(view.getContext());

        patient = patientService.GetPatientById(patientID);
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity!=null)
        {
            ActionBar bar =  activity.getSupportActionBar();
            if(bar!=null)
            {
                bar.setTitle("Profil: "+patient.Name+" "+patient.Surname);
            }
        }

        //set patient data
        name = view.findViewById(R.id.patientNameShow);
        number = view.findViewById(R.id.patientNumberShow);
        name.setText(patient.Name +" "+ patient.Surname);
        number.setText(Long.toString(patient.PatientNumber));

        final Button resultBt=  view.findViewById(R.id.resultBt);
        resultBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showResults(resultBt);
            }
        });
        final Button reportBt=  view.findViewById(R.id.reportBt);
        reportBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                generateReport(reportBt);
            }
        });
        final Button formsBt= view.findViewById(R.id.formsBt);
        formsBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chooseForm(formsBt);
            }
        });



        return view;
    }

    public void generateReport(View v)
    {
        if(activity!=null)
        {
            // Creating Bundle object
            Bundle bundel = new Bundle();

            // Storing data into bundle
            bundel.putInt(getString(R.string.patient_id_tag), patient.Id);

            //print dialog with actions for patient
            DialogFragment dialog = ReportFragment.newInstance(patient);
            dialog.setArguments(bundel);
            dialog.show(activity.getSupportFragmentManager(), "ReportFragment");
        }


    }

    public void showResults(View v)
    {
        if(activity!=null)
        {
            ResultsFragment setFragment= ResultsFragment.newInstance(patient.Id);
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentFrame, setFragment, null)
                    .addToBackStack(null)
                    .commit();
        }

    }

    public void chooseForm(View v)
    {
        if(activity!=null)
        {
            FormListFragment setFragment= FormListFragment.newInstance(patient.Id);
            activity.getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentFrame, setFragment, null)
                    .addToBackStack("forms_list")
                    .commit();
        }

    }






/*
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
