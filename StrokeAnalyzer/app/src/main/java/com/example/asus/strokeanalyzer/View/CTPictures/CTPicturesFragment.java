package com.example.asus.strokeanalyzer.View.CTPictures;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.asus.strokeanalyzer.Model.CTPictures;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CTPicturesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CTPicturesFragment extends Fragment {

    GridView picturesGrid;
    private Integer patientID;
    private PatientService patientService;
    Bitmap[] editedPictures;

    //int logos[] = {R.drawable.brain, R.drawable.brain, R.drawable.brain, R.drawable.brain};


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   // private static final String ARG_PARAM1 = "param1";
   // private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Patient patient;

   // private OnFragmentInteractionListener mListener;

    public CTPicturesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment CTPicturesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CTPicturesFragment newInstance(Integer patientID) {
        CTPicturesFragment fragment = new CTPicturesFragment();
        fragment.patientID = patientID;
        //-------------zmien na to podspodem--------------------
        /*Bundle args = new Bundle();
        args.putInt(ARG_PATIENT, patient.PatientNumber);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ctpictures, container, false);

        view.setBackgroundColor(getResources().getColor(R.color.pictureBackground));

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Uszkodzone obszary mózgu");

        Context context = view.getContext();
        CTPictures.InitializeCTPictures(context);
        picturesGrid = (GridView) view.findViewById(R.id.CTPicturesView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        patientService = new PatientService(context);
        editedPictures = CTPictures.GenerateOutputImage(patientService.GetPatientById(patientID).getStrokeBricksAffectedRegions());
        CTPicturesAdapter customAdapter = new CTPicturesAdapter(context.getApplicationContext(), editedPictures);
        picturesGrid.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        picturesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CTPictureFullFragment setFragment= CTPictureFullFragment.newInstance(editedPictures[position]);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentFrame, setFragment, null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        // Inflate the layout for this fragment
        return view;
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
