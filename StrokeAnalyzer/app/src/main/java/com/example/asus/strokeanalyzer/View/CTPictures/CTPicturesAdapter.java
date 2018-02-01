package com.example.asus.strokeanalyzer.View.CTPictures;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.asus.strokeanalyzer.R;

/**
 * Klasa będąca rozszerzeniem klasy {@link BaseAdapter}. Odpowiedzialna jest za zarządzanie widokiem
 * listy obrazów CT mózgu reprezentujących rozległość udaru. Zarządza obiektami przechowującymi
 * widoki dla pojedynczego elementu listy oraz odpowiada za uzupełnianie ich.
 *
 * @author Marta Marciszewicz
 */

class CTPicturesAdapter extends BaseAdapter {

    final private Bitmap CTpictures[];

    /**
     * Kontruktor ustawiający listę obrazów CT wykorzystywaną przez adapter do wyświetlenia we fragmencie.
     *
     * @param pictures tablica obrazów, które mają zostać wyświetlone w widoku
     */
    CTPicturesAdapter(Bitmap[] pictures) {
        this.CTpictures = pictures;
    }

    /**
     * Metoda zwracająca liczbę obrazów CT do wyświetlenia.
     *
     * @return liczba obrazów CT mózgu
     */
    @Override
    public int getCount() {
        return CTpictures.length;
    }

    /**
     * Metoda zwracająca element listy na wskazanej pozycji.
     *
     * @param i pozycja na liście, z której element powinien być zwrócony
     * @return wybrany element listy
     */
    @Override
    public Object getItem(int i) {
        return CTpictures[i];
    }

    /**
     * Metoda zwracająca id elementu listy ze wskazanej pozycji.
     *
     * @param i pozycja na liście, z której id elementu ma zostać zwrócone
     * @return id elementu listy
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Metoda zwracająca widok, który wyświetla obraz na wskazanej pozycji listy.
     *
     * @param i pozycja elementu listy, dla którego widok chcemu pozyskać
     * @param view widok, który chcemy ponownie wykorzystać (może przyjmować wartość null jeżeli nie
     *             ma widoku do ponownego wykorzystania)
     * @param viewGroup widok-rodzic, do którego przypięty zostanie utworzony widok
     * @return widok dla danych ze wskazanej pozycji
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        @SuppressLint("ViewHolder") View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ct_image, viewGroup, false);

        ImageView pictures = itemView.findViewById(R.id.basicPic);
        pictures.setImageBitmap((Bitmap)getItem(i));
        return itemView;
    }
}
