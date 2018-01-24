package com.example.asus.strokeanalyzer.View.CTPictures;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.asus.strokeanalyzer.Model.CTPictures;
import com.example.asus.strokeanalyzer.R;
import com.example.asus.strokeanalyzer.Services.PatientService;

/**
 * Klasa będąca podklasą {@link Fragment}. Pozwala na wyświetlenie miniatur obrazów CT móżgu
 *  z naniesionymi obszarami prawdopodobnego występowania udaru móżgu u pacjenta.
 * Do stworzenia instancji tego fragmentu należy wykorzystać metodę {@link CTPicturesFragment#newInstance}.
 *
 * @author Marta Marciszewicz
 */
public class CTPicturesFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT_ID = "patient_id";

    GridView picturesGrid;
    private Integer patientID;
    private PatientService patientService;
    Bitmap[] editedPictures;

    /**
     * Publiczny konstruktor bezparametrowy - jest wymagany, ale nie jest wykorzystywany
     *
     */
    public CTPicturesFragment() {
        // Required empty public constructor
    }

    /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów.
     *
     * @param patientID Id pacjenta, którego dotyczą obrazy mózgu wyświetlane we fragmencie
     * @return (CTPicturesFragment) nowa instancja fragmentu CTPicturesFragment
     */
    public static CTPicturesFragment newInstance(Integer patientID) {
        CTPicturesFragment fragment = new CTPicturesFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PATIENT_ID, patientID);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Metoda wołana w celu zainicjowania tworzenia fragmentu. Metoda ustawia wartość pól klasy przekazane
     * jako argumenty poprzez {@link Bundle}
     *
     * @param savedInstanceState poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            patientID = getArguments().getInt(ARG_PATIENT_ID);
        }
    }

    /**
     * Metoda pozwalająca na zainicjowanie interfejsu użytkownika dla fragmentu. Funkcja oprócz wstrzyknięcia widoku
     * fragmentu inicjalizuje obiekt klasy RecyclerView odpowiedzialny za prezentację obrazów CT przy wykorzystaniu
     * klasy {@link CTPicturesAdapter}
     *
     * @param inflater obiekt umożliwiający wstrzyknięcie widoku do fragmentu
     * @param container widok-rodzic, do którego powinien być podpięty UI fragmentu
     * @param savedInstanceState poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     * @return (View) widok interfejsu użytkownika fragmentu (może przyjąć wartość null)
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ctpictures, container, false);

        view.setBackgroundColor(getResources().getColor(R.color.pictureBackground, null));

        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity!=null)
        {
            ActionBar bar =  activity.getSupportActionBar();
            if(bar!=null)
            {
                bar.setTitle("Uszkodzone obszary mózgu");
            }
        }

        Context context = view.getContext();
        CTPictures.InitializeCTPictures(context);
        picturesGrid = view.findViewById(R.id.CTPicturesView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        patientService = new PatientService(context);
        editedPictures = CTPictures.GenerateOutputImage(patientService.GetPatientById(patientID).getStrokeBricksAffectedRegions());
        CTPicturesAdapter customAdapter = new CTPicturesAdapter(editedPictures);
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
}
