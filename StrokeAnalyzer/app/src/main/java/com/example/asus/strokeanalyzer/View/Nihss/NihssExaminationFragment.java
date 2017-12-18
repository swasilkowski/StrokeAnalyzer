package com.example.asus.strokeanalyzer.View.Nihss;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.NihssExamination;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.Form.FormFragment;
import com.example.asus.strokeanalyzer.View.Helpers.ClickListener;
import com.example.asus.strokeanalyzer.View.Helpers.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.

 * create an instance of this fragment.
 */
public class NihssExaminationFragment extends Fragment {


    private RecyclerView recyclerView;
    private NihssAdapter nAdapter;
    private List<NihssExamination> examinations = new ArrayList<>();
    PatientService patientService;
    int patientID;

    public static NihssExaminationFragment newInstance(long patientID) {
        NihssExaminationFragment fragment = new NihssExaminationFragment();
        fragment.patientID = (int)patientID;

        //proper form down
     /*   Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nihss_examination, container, false);

        view.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        recyclerView = (RecyclerView) view;

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            patientService = new PatientService(getContext());

            //get patients list from database
            examinations = patientService.GetPatientById(patientID).getNihssHistory();

            nAdapter = new NihssAdapter(examinations,context);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            /*recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItem(context, LinearLayoutManager.VERTICAL));
            ItemTouchHelper.Callback callback =
                    new SwipeHelperCallback(nAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);*/
            recyclerView.setAdapter(nAdapter);

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener( getActivity().getApplicationContext(), recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    //move to proper form
                    FormFragment setFragment = FormFragment.newInstance(Form.NIHSS,patientID);
                    //move to demograhic form
                    //ResultsFragment setFragment= new ResultsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentFrame, setFragment, null)
                            .addToBackStack(null)
                            .commit();

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
