package com.example.asus.strokeanalyzer.View.DialogWindows;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends DialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT_ID = "patient_id";

    //Patient patient;
    Integer patientID;


    public interface GenerateReportDialogListener {
        void onDialogReportPositiveClick(android.support.v4.app.DialogFragment dialog, int patientID);
        void onDialogReportNegativeClick(android.support.v4.app.DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    GenerateReportDialogListener _listener;

    public static ReportFragment newInstance(Patient patient) {
        ReportFragment fragment = new ReportFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PATIENT_ID, patient.Id);
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

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            _listener = (GenerateReportDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ChooseNameDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fragment_report, null);
       // final EditText emailView = (EditText) dialogView.findViewById(R.id.emailAddress);

        builder.setTitle(R.string.report_generation)
                .setView(dialogView)
                .setPositiveButton(R.string.ok_bt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //generate report
                        _listener.onDialogReportPositiveClick(ReportFragment.this, patientID);

                    }
                })
                .setNegativeButton(R.string.cancel_bt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // User cancelled the dialog
                        // Send the negative button event back to the host activity
                        _listener.onDialogReportNegativeClick(ReportFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
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

    public ReportFragment() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     *//*
    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false);
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
