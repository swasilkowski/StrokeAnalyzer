package com.example.asus.strokeanalyzer.View;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.asus.strokeanalyzer.Model.CTPictures;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.Model.Patient;
import com.example.asus.strokeanalyzer.Model.Report;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;
import com.example.asus.strokeanalyzer.View.DialogWindows.ReportFragment;
import com.example.asus.strokeanalyzer.View.Patient.PatientsListFragment;

import java.io.File;

import static java.security.AccessController.getContext;

public class MainStartActivity extends AppCompatActivity implements ReportFragment.GenerateReportDialogListener {

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);
        //getSupportActionBar().hide();

        //inicialization of fragment manager
        fm = getSupportFragmentManager();
        FormsStructure.InitializeQuestionsList();

    }

    @Override
    protected  void onResume()
    {
        super.onResume();
        getSupportActionBar().hide();
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
        PatientService patientService = new PatientService(dialog.getContext());
        String fileName = Report.GenerateReport( patientService.GetPatientById(patientID), dialog.getContext());

        if(fileName!=null)
            share(fileName, patientService.GetPatientById(patientID).PatientNumber);
    }

    @Override
    public void onDialogReportNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    private void share(String fileName, long patientNumber) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        File file = new File(fileName);

        if(file.exists()) {
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.report_share_msg_title));
            intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.report_share_msg_text)+ String.valueOf(patientNumber));
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+fileName));
            startActivity(Intent.createChooser(intent, getString(R.string.report_share_title)));
        }
    }
}
