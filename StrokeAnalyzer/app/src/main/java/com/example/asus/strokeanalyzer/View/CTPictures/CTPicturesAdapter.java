package com.example.asus.strokeanalyzer.View.CTPictures;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.asus.strokeanalyzer.R;

/**
 * Created by Asus on 07.12.2017.
 */

public class CTPicturesAdapter extends BaseAdapter {

    Context context;
    Bitmap CTpictures[];

    LayoutInflater inflter;

    public CTPicturesAdapter(Context Context, Bitmap[] pictures) {
        this.context = Context;
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

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ct_image, viewGroup, false);

        ImageView pictures = (ImageView) itemView.findViewById(R.id.basicPic);
        pictures.setImageBitmap((Bitmap)getItem(i));
        return itemView;
    }
}
