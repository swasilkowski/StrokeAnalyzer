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
    //key - region's id, value - resoruce of image
    private static Map<Region,Integer> regions = new Hashtable<>();
    private static Map<Region, Integer> regionBasicPictureRelation = new Hashtable<>();

    private CTPictures(){}

    public static void InitializeCTPictures (Context context)
    {
        appContext =context;

        //setting basic brain pictures
        basicPictures[0] = BitmapFactory.decodeResource( appContext.getResources(), R.drawable.brain);
        basicPictures[1] = BitmapFactory.decodeResource( appContext.getResources(), R.drawable.brain);
        basicPictures[2] = BitmapFactory.decodeResource( appContext.getResources(), R.drawable.brain);
        basicPictures[3] = BitmapFactory.decodeResource( appContext.getResources(), R.drawable.brain);

        //assigning regions to basic brain pictures
        regionBasicPictureRelation.put(Region.A1_L,0);
        regionBasicPictureRelation.put(Region.A1_R,1);
        regionBasicPictureRelation.put(Region.A2_L,2);
        regionBasicPictureRelation.put(Region.A2_R, 3);
        regionBasicPictureRelation.put(Region.A3_L, 0);
        regionBasicPictureRelation.put(Region.A3_R, 1);
        regionBasicPictureRelation.put(Region.BGIC_L, 2);
        regionBasicPictureRelation.put(Region.BGIC_R,3);
        regionBasicPictureRelation.put(Region.CR_L,0 );
        regionBasicPictureRelation.put(Region.CR_R,1);
        regionBasicPictureRelation.put(Region.M1_L,2);
        regionBasicPictureRelation.put(Region.M1_R, 3);
        regionBasicPictureRelation.put(Region.M2_L, 0);
        regionBasicPictureRelation.put(Region.M2_R, 1);
        regionBasicPictureRelation.put(Region.M3_L, 2);
        regionBasicPictureRelation.put(Region.M3_R,3);
        regionBasicPictureRelation.put(Region.M4_L,0 );
        regionBasicPictureRelation.put(Region.M4_R,1);
        regionBasicPictureRelation.put(Region.M5_L,2);
        regionBasicPictureRelation.put(Region.M5_R, 3);
        regionBasicPictureRelation.put(Region.M6_L, 0);
        regionBasicPictureRelation.put(Region.M6_R, 1);
        regionBasicPictureRelation.put(Region.P_L, 2);
        regionBasicPictureRelation.put(Region.P_R,3);
        regionBasicPictureRelation.put(Region.T_L, 0);
        regionBasicPictureRelation.put(Region.T_R,1);

        //assigning regions to images
        regions.put(Region.A1_L, R.drawable.p1);
        regions.put(Region.A1_R,R.drawable.p2);
        regions.put(Region.A2_L,R.drawable.p3);
        regions.put(Region.A2_R, R.drawable.p4);
        regions.put(Region.A3_L,  R.drawable.p5);
        regions.put(Region.A3_R, R.drawable.p6);
        regions.put(Region.BGIC_L,  R.drawable.p7);
        regions.put(Region.BGIC_R,R.drawable.p8);
        regions.put(Region.CR_L,R.drawable.p1);
        regions.put(Region.CR_R, R.drawable.p2);
        regions.put(Region.M1_L,R.drawable.p3);
        regions.put(Region.M1_R, R.drawable.p4);
        regions.put(Region.M2_L,R.drawable.p5);
        regions.put(Region.M2_R, R.drawable.p6);
        regions.put(Region.M3_L, R.drawable.p7);
        regions.put(Region.M3_R,R.drawable.p8);
        regions.put(Region.M4_L, R.drawable.p1);
        regions.put(Region.M4_R, R.drawable.p2);
        regions.put(Region.M5_L, R.drawable.p3);
        regions.put(Region.M5_R,  R.drawable.p4);
        regions.put(Region.M6_L, R.drawable.p5);
        regions.put(Region.M6_R, R.drawable.p6);
        regions.put(Region.P_L, R.drawable.p7);
        regions.put(Region.P_R,R.drawable.p8);
        regions.put(Region.T_L, R.drawable.p7);
        regions.put(Region.T_R,R.drawable.p8);

    }

    public static Bitmap[] GenerateOutputImage(List<Region> regions)
    {
        Bitmap[] brainPictures = new Bitmap[4];
        brainPictures[0] = basicPictures[0].copy(basicPictures[0].getConfig(), true);
        brainPictures[1] = basicPictures[1].copy(basicPictures[0].getConfig(), true);
        brainPictures[2] = basicPictures[2].copy(basicPictures[0].getConfig(), true);
        brainPictures[3] = basicPictures[3].copy(basicPictures[0].getConfig(), true);

        for(Region r:regions)
        {
            //brain image
            Bitmap brainImg = brainPictures[regionBasicPictureRelation.get(r)];

            //region image
            Bitmap regionImg = GetPicture(r);

            Bitmap bmOverlay = Bitmap.createBitmap(brainImg.getWidth(), brainImg.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawARGB(0x00, 0, 0, 0);
            Paint paint = new Paint();
            paint.setAlpha(120);
            canvas.drawBitmap(brainImg, 0, 0, null);
            canvas.drawBitmap(regionImg, 0, 0, paint);

            //moze zbedna czesc??
            BitmapDrawable drawable = new BitmapDrawable(bmOverlay);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

            brainPictures[regionBasicPictureRelation.get(r)] = drawable.getBitmap();

        }

        return brainPictures;
    }

    static Bitmap GetPicture(Region region)
    {
        return BitmapFactory.decodeResource( appContext.getResources(), regions.get(region));
    }
}
