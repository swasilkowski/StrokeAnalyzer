package com.example.asus.strokeanalyzer.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.test.mock.MockApplication;

import com.example.asus.strokeanalyzer.Model.EnumValues.Region;
import com.example.asus.strokeanalyzer.R;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Asus on 20.11.2017.
 */

public final class CTPictures {

    private static Context appContext;

    private static Bitmap[] basicPictures = new Bitmap[4];
    //key - region's id, value - resoruce id of images connected with this region
    private static Map<Region, List<Integer>> regions = new Hashtable<>();
    private static Map<Integer, Integer> pictureBasicPictureRelation = new Hashtable<>();

    private CTPictures(){}

    public static void InitializeCTPictures (Context context)
    {
        appContext =context;

        //setting basic brain pictures
        basicPictures[0] = BitmapFactory.decodeResource( appContext.getResources(), R.drawable.brain_1);
        basicPictures[1] = BitmapFactory.decodeResource( appContext.getResources(), R.drawable.brain_2);
        basicPictures[2] = BitmapFactory.decodeResource( appContext.getResources(), R.drawable.brain_3);
        basicPictures[3] = BitmapFactory.decodeResource( appContext.getResources(), R.drawable.brain_4);

        //assigning images of brain parts to basic brain pictures
        pictureBasicPictureRelation.put(R.drawable.a1_l_9,0);
        pictureBasicPictureRelation.put(R.drawable.a1_p_9,0);
        pictureBasicPictureRelation.put(R.drawable.m2_l_9,0);
        pictureBasicPictureRelation.put(R.drawable.m2_p_9,0);
        pictureBasicPictureRelation.put(R.drawable.m3_l_9,0);
        pictureBasicPictureRelation.put(R.drawable.m3_p_9,0);
        pictureBasicPictureRelation.put(R.drawable.p_l_9,0);
        pictureBasicPictureRelation.put(R.drawable.p_p_9,0);
        pictureBasicPictureRelation.put(R.drawable.a1_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.a1_p_14,1);
        pictureBasicPictureRelation.put(R.drawable.bgic_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.bgic_p_14,1);
        pictureBasicPictureRelation.put(R.drawable.m1_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.m1_p_14,1);
        pictureBasicPictureRelation.put(R.drawable.m2_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.m2_p_14,1);
        pictureBasicPictureRelation.put(R.drawable.m3_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.m3_p_14,1);
        pictureBasicPictureRelation.put(R.drawable.p_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.p_p_14,1);
        pictureBasicPictureRelation.put(R.drawable.t_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.t_p_14,1);
        pictureBasicPictureRelation.put(R.drawable.a1_l_16,2);
        pictureBasicPictureRelation.put(R.drawable.a1_p_16,2);
        pictureBasicPictureRelation.put(R.drawable.cr_l_16,2);
        pictureBasicPictureRelation.put(R.drawable.cr_p_16,2);
        pictureBasicPictureRelation.put(R.drawable.m4_l_16,2);
        pictureBasicPictureRelation.put(R.drawable.m4_p_16,2);
        pictureBasicPictureRelation.put(R.drawable.m5_l_16,2);
        pictureBasicPictureRelation.put(R.drawable.m5_p_16,2);
        pictureBasicPictureRelation.put(R.drawable.m6_l_16,2);
        pictureBasicPictureRelation.put(R.drawable.m6_p_16,2);
        pictureBasicPictureRelation.put(R.drawable.p_l_16,2);
        pictureBasicPictureRelation.put(R.drawable.p_p_16,2);
        pictureBasicPictureRelation.put(R.drawable.a1_l_23,3);
        pictureBasicPictureRelation.put(R.drawable.a1_p_23,3);
        pictureBasicPictureRelation.put(R.drawable.a2_l_23,3);
        pictureBasicPictureRelation.put(R.drawable.a2_p_23,3);
        pictureBasicPictureRelation.put(R.drawable.a3_l_23,3);
        pictureBasicPictureRelation.put(R.drawable.a3_p_23,3);
        pictureBasicPictureRelation.put(R.drawable.m4_l_23,3);
        pictureBasicPictureRelation.put(R.drawable.m4_p_23,3);
        pictureBasicPictureRelation.put(R.drawable.m5_l_23,3);
        pictureBasicPictureRelation.put(R.drawable.m5_p_23,3);
        pictureBasicPictureRelation.put(R.drawable.m6_l_23,3);
        pictureBasicPictureRelation.put(R.drawable.m6_p_23,3);

        //assigning regions to images
        List<Integer> picturesResId = new ArrayList<>();
        picturesResId.add(R.drawable.a1_l_9);
        picturesResId.add(R.drawable.a1_l_14);
        picturesResId.add(R.drawable.a1_l_16);
        picturesResId.add(R.drawable.a1_l_23);
        regions.put(Region.A1_L, new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.a1_p_9);
        picturesResId.add(R.drawable.a1_p_14);
        picturesResId.add(R.drawable.a1_p_16);
        picturesResId.add(R.drawable.a1_p_23);
        regions.put(Region.A1_R,new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.a2_l_23);
        regions.put(Region.A2_L,new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.a2_p_23);
        regions.put(Region.A2_R, new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.a3_l_23);
        regions.put(Region.A3_L,  new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.a3_p_23);
        regions.put(Region.A3_R, new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.bgic_l_14);
        regions.put(Region.BGIC_L,  new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.bgic_p_14);
        regions.put(Region.BGIC_R,new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.cr_l_16);
        regions.put(Region.CR_L,new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.cr_p_16);
        regions.put(Region.CR_R,new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m1_l_14);
        regions.put(Region.M1_L,new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m1_p_14);
        regions.put(Region.M1_R, new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m2_l_9);
        picturesResId.add(R.drawable.m2_l_14);
        regions.put(Region.M2_L,new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m2_p_9);
        picturesResId.add(R.drawable.m2_p_14);
        regions.put(Region.M2_R, new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m3_l_9);
        picturesResId.add(R.drawable.m3_l_14);
        regions.put(Region.M3_L, new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m3_p_9);
        picturesResId.add(R.drawable.m3_p_14);
        regions.put(Region.M3_R,new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m4_l_16);
        picturesResId.add(R.drawable.m4_l_23);
        regions.put(Region.M4_L, new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m4_p_16);
        picturesResId.add(R.drawable.m4_p_23);
        regions.put(Region.M4_R, new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m5_l_16);
        picturesResId.add(R.drawable.m5_l_23);
        regions.put(Region.M5_L, new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m5_p_16);
        picturesResId.add(R.drawable.m5_p_23);
        regions.put(Region.M5_R, new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m6_l_16);
        picturesResId.add(R.drawable.m6_l_23);
        regions.put(Region.M6_L,new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m6_p_16);
        picturesResId.add(R.drawable.m6_p_23);
        regions.put(Region.M6_R,new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.p_l_9);
        picturesResId.add(R.drawable.p_l_14);
        picturesResId.add(R.drawable.p_l_16);
        regions.put(Region.P_L, new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.p_p_9);
        picturesResId.add(R.drawable.p_p_14);
        picturesResId.add(R.drawable.p_p_16);
        regions.put(Region.P_R,new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.t_l_14);
        regions.put(Region.T_L,new ArrayList<Integer>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.t_p_14);
        regions.put(Region.T_R,new ArrayList<Integer>(picturesResId));

    }

    public static Bitmap[] GenerateOutputImage(List<Region> _regions)
    {
        Bitmap[] brainPictures = new Bitmap[4];
        brainPictures[0] = basicPictures[0].copy(basicPictures[0].getConfig(), true);
        brainPictures[1] = basicPictures[1].copy(basicPictures[0].getConfig(), true);
        brainPictures[2] = basicPictures[2].copy(basicPictures[0].getConfig(), true);
        brainPictures[3] = basicPictures[3].copy(basicPictures[0].getConfig(), true);


        //------------------TODO----------------NIE DZIALA
        _regions = new ArrayList<>();
        _regions.add(Region.A1_L);
        _regions.add(Region.A1_R);
        _regions.add(Region.M2_L);
        for(Region r:_regions)
        {
            //region image
            //List<Bitmap> regionImages = GetPictures(r);
            //image Ids
            List<Integer> ids = regions.get(r);

            for(Integer image: ids)
            {
                //brain image
                Bitmap brainImg = brainPictures[pictureBasicPictureRelation.get(image)];

                //region image
                Bitmap regionImage = GetPicture(image);

                Bitmap bmOverlay = Bitmap.createBitmap(brainImg.getWidth(), brainImg.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bmOverlay);
                canvas.drawARGB(0x00, 0, 0, 0);
                Paint paint = new Paint();
                paint.setAlpha(120);
                canvas.drawBitmap(brainImg, 0, 0, null);
                canvas.drawBitmap(regionImage, 0, 0, paint);


                //moze zbedna czesc??
                BitmapDrawable drawable = new BitmapDrawable(bmOverlay);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                brainPictures[pictureBasicPictureRelation.get(image)] = drawable.getBitmap();
            }


        }

        return brainPictures;
    }

    static Bitmap GetPicture(Integer resourceID)
    {
        return BitmapFactory.decodeResource( appContext.getResources(), resourceID);
    }
}
