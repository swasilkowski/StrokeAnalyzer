package com.example.asus.strokeanalyzer.View;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.View.DialogWindows.ReportFragment;
import com.example.asus.strokeanalyzer.View.Patient.PatientsListFragment;

public class MainStartActivity extends AppCompatActivity implements ReportFragment.GenerateReportDialogListener {

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);

        //inicialization of fragment manager
        fm = getSupportFragmentManager();
    }

    public void showList(View v)
    {
        PatientsListFragment setFragment= new PatientsListFragment();
        fm.beginTransaction()
                .replace(R.id.fragmentFrame, setFragment, null)
                .addToBackStack(null)
                .commit();

    }

    public void addPatient(View v)
    {
        NewPatientFragment setFragment= new NewPatientFragment();
        fm.beginTransaction()
                .replace(R.id.fragmentFrame, setFragment, null)
                .addToBackStack(getString(R.string.new_patient_tag))
                .commit();
    }

    @Override
    public void onDialogReportPositiveClick(DialogFragment dialog, int patientID) {

        //generate report about the patient
        ;
    }

    @Override
    public void onDialogReportNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
