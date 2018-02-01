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

/**
 * Klasa stanowiąca rozszerzenie klasy {@link AppCompatActivity}. Jest to główna i jedyna aktywność w aplikacji.
 * Wyświetlane są w niej fragmenty reprezentujące poszczególne ekrany aplikacji. Jednocześnie stanowi ona
 * ekran startowy aplikacji.
 */
public class MainStartActivity extends AppCompatActivity{

    private FragmentManager fm;

    /**
     * Metoda wywoływana w momencie startowania aktywności. W jej wnętrzu dokonywana jest inicjalizacja
     * UI aktywności oraz innych pól klasy.
     *
     * @param savedInstanceState poprzedni stan aktywności, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);

        //inicialization of fragment manager
        fm = getSupportFragmentManager();
        FormsStructure.InitializeQuestionsList();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));

    }

    /**
     * Metoda wywoływana, w momencie, gdy aktywność jest wyświetlana użytkownikowi. Aplikacja wykorzystuje tę metodę
     * do kontrolowania elementu ActionBar.
     */
    @Override
    protected  void onResume()
    {
        super.onResume();
        ActionBar bar = getSupportActionBar();
        if(bar!=null)
            bar.hide();
    }

    /**
     * Metoda dokonująca przejścia do fragmentu wyświetlającego listę pacjentów, których profile przechowuje aplikacja.
     *
     * @param v obiekt przycisku wybranego przez użytkownika
     */
    public void showList(View v)
    {
        PatientsListFragment setFragment= new PatientsListFragment();
        fm.beginTransaction()
                .replace(R.id.fragmentFrame, setFragment, null)
                .addToBackStack(null)
                .commit();

    }

    /**
     * Metoda dokonująca przejścia do fragmentu umożliwiającego stworzenie nowego profilu pacjenta.
     *
     * @param v obiekt przycisku wybranego przez użytkownika
     */
    public void addPatient(View v)
    {
        NewPatientFragment setFragment= new NewPatientFragment();
        fm.beginTransaction()
                .replace(R.id.fragmentFrame, setFragment, null)
                .addToBackStack(getString(R.string.new_patient_tag))
                .commit();
    }

}
