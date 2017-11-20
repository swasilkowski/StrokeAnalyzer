package com.example.asus.strokeanalyzer.Model;

import android.media.Image;

import com.example.asus.strokeanalyzer.Model.EnumValues.Region;

import java.util.Dictionary;
import java.util.List;

/**
 * Created by Asus on 20.11.2017.
 */

public final class CTPictures {

    private static Image[] basicPictures = new Image[4];
    //private static Dictionary<Region,Image> regions;
    //private static Dictionary<Region, Integer> regionBasicPictureRelation;

    private CTPictures() {}

    public static Image[] GenerateOutputImage(List<Region> regions){ return new Image[4];}
    //public static Image GetPicture(Region region);
}
