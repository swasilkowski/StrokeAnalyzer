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
 * Klasa stanowiąca adapter dla pojedynczego obrazu CT mózgu reprezentującego rozległość udaru.
 * Pozwala na wyświetlenie przybliżenia pojedynczego zdjęcia.
 */

public class CTPicturesAdapter extends BaseAdapter {

    private Bitmap CTpictures[];

    CTPicturesAdapter(Bitmap[] pictures) {
        this.CTpictures = pictures;
    }
    @Override
    public int getCount() {
        return CTpictures.length;
    }
    @Override
    public Object getItem(int i) {
        return CTpictures[i];
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        @SuppressLint("ViewHolder") View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ct_image, viewGroup, false);

        ImageView pictures = itemView.findViewById(R.id.basicPic);
        pictures.setImageBitmap((Bitmap)getItem(i));
        return itemView;
    }
}
