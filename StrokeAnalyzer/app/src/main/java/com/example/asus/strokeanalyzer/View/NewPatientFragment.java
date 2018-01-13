package com.example.asus.strokeanalyzer.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.strokeanalyzer.Model.Analyzers.StrokeBricksAnalyzer;
import com.example.asus.strokeanalyzer.Model.EnumValues.Form;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.DialogWindows.NumberAlertFragment;
import com.example.asus.strokeanalyzer.View.Form.FormFragment;
import com.example.asus.strokeanalyzer.View.Patient.PatientsListFragment;

import static android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class NewPatientFragment extends Fragment {

    private EditText name;
    private EditText surname;
    private EditText number;
    private PatientService patientService;
    private boolean DONE;

    public NewPatientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_patient, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tworzenie profilu pacjenta");

        view.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        view.setClickable(true);
        //mListener.setTitleName(getString(R.string.title_fragmet_change_name));
        name = (EditText) view.findViewById(R.id.name);
        surname = (EditText) view.findViewById(R.id.surname);
        number = (EditText) view.findViewById(R.id.patientNumber);

        final Button nextBt= (Button) view.findViewById(R.id.nextBt);
        nextBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!createPatient(v))
                    Toast.makeText(v.getContext(), getString(R.string.toast_new_patient), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DONE = false;
        patientService = new PatientService(getContext());
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public void onPause()
    {
        super.onPause();
        //getFragmentManager().popBackStack(getString(R.string.new_patient_tag), POP_BACK_STACK_INCLUSIVE);
        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        //mListener = null;
    }

    public boolean createPatient(View v)
    {
        final String name = this.name.getText().toString();
        final String surname = this.surname.getText().toString();
        final String number = this.number.getText().toString();

        if(name.isEmpty() || surname.isEmpty() || number.isEmpty())
            return false;

        try
        {
            long patientNumber = Long.parseLong(number);
            //sprawdz czy istnieje pacjent o takim numerze
            if(patientService.isPatientNumberTaken(patientNumber))
            {
                NumberAlertFragment.NumberAlertDialogListener listener = new NumberAlertFragment.NumberAlertDialogListener() {
                    @Override
                    public void onDialogNumberPositiveClick(DialogFragment dialog) {
                        addPatient(name,surname,number);
                        dialog.dismiss();
                    }

                    @Override
                    public void onDialogNumberNegativeClick(DialogFragment dialog) {
                        dialog.dismiss();
                    }
                };

                //print dialog with actions for patient
                DialogFragment dialog = NumberAlertFragment.newInstance(listener);
                //dialog.setArguments(bundel);
                dialog.show(getActivity().getSupportFragmentManager(), "NumberAlertFragment");
                return true;
            }

            addPatient(name,surname,number);
        }
        catch(NumberFormatException exception)
        {
            Toast.makeText(v.getContext(),"Numer pacjenta jest za długi. Wprwadź inny numer", Toast.LENGTH_LONG).show();
        }

        return true;
    }

    public void addPatient(String name, String surname, String number)
    {
        //add patient to database - create profile
        Patient newPatient = new Patient();
        newPatient.Name = name;
        newPatient.Surname = surname;
        newPatient.PatientNumber = Long.parseLong(number); //zlap wyjatki!!!

        PatientService patientService = new PatientService(getContext());
        long patientID = patientService.AddPatient(newPatient);


        getFragmentManager().popBackStack(getString(R.string.new_patient_tag), POP_BACK_STACK_INCLUSIVE);

        PatientsListFragment listFragment= new PatientsListFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame, listFragment, null)
                .addToBackStack(null)
                .commit();

        //move to demograhic form
        //FormFragment setFragment = FormFragment.newInstance(Form.DemographicAndClinic, patientID, true, true);
        PatientProfileFragment setFragment = PatientProfileFragment.newInstance((int)patientID);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame, setFragment, null)
                .addToBackStack(null)
                .commit();
    }


/*    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewPatientFragment.
     *//*
    // TODO: Rename and change types and number of parameters
    public static NewPatientFragment newInstance(String param1, String param2) {
        NewPatientFragment fragment = new NewPatientFragment();
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
