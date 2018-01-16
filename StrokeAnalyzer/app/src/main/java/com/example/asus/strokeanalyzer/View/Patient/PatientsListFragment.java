package com.example.asus.strokeanalyzer.View.Patient;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.DialogWindows.PatientsListActionFragment;
import com.example.asus.strokeanalyzer.View.Helpers.LineDecoration;
import com.example.asus.strokeanalyzer.View.Helpers.ClickListener;
import com.example.asus.strokeanalyzer.View.Helpers.RecyclerClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class PatientsListFragment extends Fragment  {

    private RecyclerView recyclerView;
    private PatientAdapter pAdapter;
    private List<Patient> patients = new ArrayList<>();
    PatientService patientService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPause()
    {
        super.onPause();
        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_patients_list, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.colorBackground, null));

        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity!=null)
        {
            ActionBar bar =  activity.getSupportActionBar();
            if(bar!=null)
            {
                bar.show();
                bar.setTitle("Wybierz pacjenta");
            }
        }



        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            patientService = new PatientService(getContext());

            //get patients list from database
            patients = patientService.GetPatientsList();

            pAdapter = new PatientAdapter(patients,context);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            /*recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new LineDecoration(context, LinearLayoutManager.VERTICAL));
            ItemTouchHelper.Callback callback =
                    new SwipeHelperCallback(nAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);*/
            recyclerView.setAdapter(pAdapter);
            recyclerView.addItemDecoration(new LineDecoration(this.getContext()));

            recyclerView.addOnItemTouchListener(new RecyclerClickListener( getActivity().getApplicationContext(), new ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    // Creating Bundle object
                    Bundle bundel = new Bundle();

                    // Storing data into bundle
                    final Patient patient = patients.get(position);
                    bundel.putInt(getString(R.string.patient_id_tag), patient.Id);

                    //print dialog with actions for patient
                    PatientsListActionFragment.DeleteListener listener = new PatientsListActionFragment.DeleteListener() {
                        @Override
                        public void patientDeleted(final int patientID) {

                            Patient tmp = new Patient();
                            tmp.Id = patientID;
                            patients.remove(patients.indexOf(tmp));
                            pAdapter.notifyDataSetChanged();
                        }
                    };
                    DialogFragment dialog =PatientsListActionFragment.newInstance(listener);
                    dialog.setArguments(bundel);
                    dialog.show(getActivity().getSupportFragmentManager(), "PatientsListActionFragment");


                }
            }));

        }

        return recyclerView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();

        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        if(activity!=null)
        {
            ActionBar bar = activity.getSupportActionBar();
            if(bar!=null)
                bar.hide();
        }

       // mListener = null;
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

    public PatientsListFragment() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientsListFragment.
     *//*
    // TODO: Rename and change types and number of parameters
    public static PatientsListFragment newInstance(String param1, String param2) {
        PatientsListFragment fragment = new PatientsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
