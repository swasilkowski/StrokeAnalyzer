package com.example.asus.strokeanalyzer.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.View.Form.FormFragment;
import com.example.asus.strokeanalyzer.View.Nihss.NihssExaminationFragment;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FormListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormListFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT_ID = "patient_id";

    private Integer patientID;


    public static FormListFragment newInstance(Integer id) {
        FormListFragment fragment = new FormListFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PATIENT_ID, id);
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

View view = inflater.inflate(R.layout.fragment_form_list, container, false);

        view.setBackgroundColor(getResources().getColor(R.color.buttonBackgroundColor, null));
        view.setClickable(true);

        final Button nihssBt= view.findViewById(R.id.nihssBt);
        nihssBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //move to proper form
                NihssExaminationFragment setFragment = NihssExaminationFragment.newInstance(patientID);
                //move to demograhic form
                //ResultsFragment setFragment= new ResultsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentFrame, setFragment, null)
                        .addToBackStack(null)
                        .commit();
            }
        });
/*        final Button sbBt= (Button) view.findViewById(R.id.sbBt);
        sbBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                printForm(sbBt, Form.StrokeBricks);
            }
        });*/
        final Button treatmentBt= view.findViewById(R.id.treatmentBt);
        treatmentBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                printForm(treatmentBt, Form.ThrombolyticTreatment);
            }
        });
/*        final Button hatBt= (Button) view.findViewById(R.id.hatBt);
        hatBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                printForm(hatBt, Form.Hat);
            }
        });
        final Button dragonBt= (Button) view.findViewById(R.id.dragonBt);
        dragonBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                printForm(dragonBt, Form.Dragon);
            }
        });*/
        final Button iscoreBt= view.findViewById(R.id.iscoreBt);
        iscoreBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                printForm(iscoreBt, Form.iScore);
            }
        });
        final Button demoandclinicBt= view.findViewById(R.id.dandcBt);
        demoandclinicBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                printForm(demoandclinicBt, Form.DemographicAndClinic);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public void printForm(View v, Form form)
    {
        //move to proper form
        FormFragment setFragment = FormFragment.newInstance(form, patientID, false, false);
        //move to demograhic form
        //ResultsFragment setFragment= new ResultsFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame, setFragment, null)
                .addToBackStack(null)
                .commit();
    }

/*    @Override
    public void onDetach()
    {
        super.onDetach();
        getFragmentManager().popBackStack("forms_list", POP_BACK_STACK_INCLUSIVE);
    }*/

   /* // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FormListFragment() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormListFragment.
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
