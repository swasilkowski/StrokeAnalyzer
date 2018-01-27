package com.example.asus.strokeanalyzer.View.CTPictures;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.asus.strokeanalyzer.R;

/**
 * Klasa będąca podklasą {@link Fragment}. Pozwala na wyświetlenie powiększonego obrazu CT mózgu
 * z nanesionymi obszarami prawdopodobnego występowania udaru.
 * Do stworzenia instancji tego fragmentu należy wykorzystać metodę {@link CTPictureFullFragment#newInstance}.
 *
 * @author Marta Marciszewicz
 */
public class CTPictureFullFragment extends Fragment {

    private static final String ARG_PICTURE = "picture";

    ImageView selectedImage;
    Bitmap image;

    /**
     * Metoda tworząca nową instancję fragmentu przy użyciu podanych parametrów.
     *
     * @param image obraz, który ma zostać powiększony
     * @return (CTPictureFullFragment) nowa instancja fragmentu CTPictureFullFragment
     */
    public static CTPictureFullFragment newInstance(Bitmap image) {
        CTPictureFullFragment fragment = new CTPictureFullFragment();
        //fragment.image = image;

        Bundle args = new Bundle();
        args.putParcelable(ARG_PICTURE, image);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Metoda wołana w celu zainicjowania tworzenia fragmentu.
     *
     * @param savedInstanceState poprzedni stan fragmentu, w przypadku, gdy jest on odtwarzany z zapisanego wcześniej stanu
     *                           (może przyjmować wartość null)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            image = getArguments().getParcelable(ARG_PICTURE);
        }
    }

    /**
     * Metoda wywoływana w momencie, gdy fragment jest wyświetlany użytkownikowi. Aplikacja wykorzystuje tę metodę
     * do kontrolowania elementu ActionBar.
     */
    @Override
    public void onResume()
    {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    /**
     * Metoda pozwalająca na zainicjowanie interfejsu użytkownika dla fragmentu. Funkcja oprócz wstrzyknięcia widoku
     * fragmentu ustawia obraz w nim wyświetlany.
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ctpicture_full, container, false);
        view.setClickable(true);
        view.setBackgroundColor(getResources().getColor(R.color.pictureBackground, null));
        selectedImage = view.findViewById(R.id.basicPicFullsize);
        selectedImage.setImageBitmap(image);

        return view;
    }
}
