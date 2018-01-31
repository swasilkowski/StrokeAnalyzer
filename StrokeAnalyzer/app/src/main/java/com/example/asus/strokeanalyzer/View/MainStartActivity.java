package com.example.asus.strokeanalyzer.View;

import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.asus.strokeanalyzer.Model.Form.FormsStructure;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.View.Patient.PatientsListFragment;


public class MainStartActivity extends AppCompatActivity{

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);
        //getSupportActionBar().hide();

        //inicialization of fragment manager
        fm = getSupportFragmentManager();
        FormsStructure.InitializeQuestionsList();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));

    }

    @Override
    protected  void onResume()
    {
        super.onResume();
        ActionBar bar = getSupportActionBar();
        if(bar!=null)
            bar.hide();
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

}
