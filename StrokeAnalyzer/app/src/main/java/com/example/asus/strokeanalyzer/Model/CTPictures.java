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
    private static Map<Region,Bitmap> regions = new Hashtable<>();
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
        regions.put(Region.A1_L,BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p1));
        regions.put(Region.A1_R,BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p2));
        regions.put(Region.A2_L,BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p3));
        regions.put(Region.A2_R, BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p4));
        regions.put(Region.A3_L, BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p5));
        regions.put(Region.A3_R, BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p6));
        regions.put(Region.BGIC_L, BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p7));
        regions.put(Region.BGIC_R,BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p8));
        regions.put(Region.CR_L,BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p1) );
        regions.put(Region.CR_R,BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p2));
        regions.put(Region.M1_L,BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p3));
        regions.put(Region.M1_R, BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p4));
        regions.put(Region.M2_L, BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p5));
        regions.put(Region.M2_R, BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p6));
        regions.put(Region.M3_L, BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p7));
        regions.put(Region.M3_R,BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p8));
        regions.put(Region.M4_L,BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p1) );
        regions.put(Region.M4_R,BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p2));
        regions.put(Region.M5_L,BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p3));
        regions.put(Region.M5_R, BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p4));
        regions.put(Region.M6_L, BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p5));
        regions.put(Region.M6_R, BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p6));
        regions.put(Region.P_L, BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p7));
        regions.put(Region.P_R,BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p8));
        regions.put(Region.T_L, BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p7));
        regions.put(Region.T_R,BitmapFactory.decodeResource( appContext.getResources(), R.drawable.p8));

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
            paint.setAlpha(180);
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
        return regions.get(region);
    }
}
