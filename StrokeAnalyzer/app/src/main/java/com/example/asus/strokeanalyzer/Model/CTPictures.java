package com.example.asus.strokeanalyzer.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.example.asus.strokeanalyzer.Model.EnumValues.Region;
import com.example.asus.strokeanalyzer.R;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * CTPictures to klasa zarządzająca obrazami CT wyświetlanymi w aplikacji.
 * <p>
 * Przechowuje ona powiązania pomiędzy regionami zdefiniowanymi w modelu StrokeBricks,
 * a obrazkami reprezentującymi regiony. Określa też zależność pomiędzy obrazami regonów,
 * a obrazami bazowymi.
 * <p>
 * Pozwala na wygenerowanie obrazów CT mózgu z zaznaczonymi regionami możliwego występowania udar
 * na podstawie dostarczonej listy regionów.
 *
 * @author Marta Marciszewicz
 *
 */
public final class CTPictures {

    private static Context appContext;
    //key - region's id, value - list of resoruce id of images connected with this region
    final private static Map<Region, List<Integer>> regions = new Hashtable<>();
    //key - region's picture ID, value - ID of a basic brain picture on which particular region's image is visible
    final private static Map<Integer, Integer> pictureBasicPictureRelation = new Hashtable<>();

    /**
     * Domyślny konstruktor klasy
     * Oznaczony jako prywatny, by uniemożliwić jego wywoływanie, co ma na celu zasymulowanie statyczności klasy.
     */
    private CTPictures(){}

    /**
     * Metoda inicjalizująca mapy wykorzystywane w procesie generowania obrazów z naniesionymi regionami
     * występowania udaru.
     * @param context kontekst aplikacji niezbędny do pobierania Bitmap z obrazami z zasobów aplikacji
     */
    public static void InitializeCTPictures (Context context)
    {
        appContext =context;

        //assigning images of brain parts to basic brain pictures
        pictureBasicPictureRelation.put(R.drawable.a1_l_9,0);
        pictureBasicPictureRelation.put(R.drawable.a1_r_9,0);
        pictureBasicPictureRelation.put(R.drawable.m2_l_9,0);
        pictureBasicPictureRelation.put(R.drawable.m2_r_9,0);
        pictureBasicPictureRelation.put(R.drawable.m3_l_9,0);
        pictureBasicPictureRelation.put(R.drawable.m3_r_9,0);
        pictureBasicPictureRelation.put(R.drawable.p_l_9,0);
        pictureBasicPictureRelation.put(R.drawable.p_r_9,0);
        pictureBasicPictureRelation.put(R.drawable.a1_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.a1_r_14,1);
        pictureBasicPictureRelation.put(R.drawable.bgic_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.bgic_r_14,1);
        pictureBasicPictureRelation.put(R.drawable.m1_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.m1_r_14,1);
        pictureBasicPictureRelation.put(R.drawable.m2_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.m2_r_14,1);
        pictureBasicPictureRelation.put(R.drawable.m3_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.m3_r_14,1);
        pictureBasicPictureRelation.put(R.drawable.p_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.p_r_14,1);
        pictureBasicPictureRelation.put(R.drawable.t_l_14,1);
        pictureBasicPictureRelation.put(R.drawable.t_r_14,1);
        pictureBasicPictureRelation.put(R.drawable.a1_l_16,2);
        pictureBasicPictureRelation.put(R.drawable.a1_r_16,2);
        pictureBasicPictureRelation.put(R.drawable.cr_l_16,2);
        pictureBasicPictureRelation.put(R.drawable.cr_r_16,2);
        pictureBasicPictureRelation.put(R.drawable.m4_l_16,2);
        pictureBasicPictureRelation.put(R.drawable.m4_r_16,2);
        pictureBasicPictureRelation.put(R.drawable.m5_l_16,2);
        pictureBasicPictureRelation.put(R.drawable.m5_r_16,2);
        pictureBasicPictureRelation.put(R.drawable.m6_l_16,2);
        pictureBasicPictureRelation.put(R.drawable.m6_r_16,2);
        pictureBasicPictureRelation.put(R.drawable.p_l_16,2);
        pictureBasicPictureRelation.put(R.drawable.p_r_16,2);
        pictureBasicPictureRelation.put(R.drawable.a1_l_23,3);
        pictureBasicPictureRelation.put(R.drawable.a1_r_23,3);
        pictureBasicPictureRelation.put(R.drawable.a2_l_23,3);
        pictureBasicPictureRelation.put(R.drawable.a2_r_23,3);
        pictureBasicPictureRelation.put(R.drawable.a3_l_23,3);
        pictureBasicPictureRelation.put(R.drawable.a3_r_23,3);
        pictureBasicPictureRelation.put(R.drawable.m4_l_23,3);
        pictureBasicPictureRelation.put(R.drawable.m4_r_23,3);
        pictureBasicPictureRelation.put(R.drawable.m5_l_23,3);
        pictureBasicPictureRelation.put(R.drawable.m5_r_23,3);
        pictureBasicPictureRelation.put(R.drawable.m6_l_23,3);
        pictureBasicPictureRelation.put(R.drawable.m6_r_23,3);

        //assigning regions to images
        List<Integer> picturesResId = new ArrayList<>();
        picturesResId.add(R.drawable.a1_l_9);
        picturesResId.add(R.drawable.a1_l_14);
        picturesResId.add(R.drawable.a1_l_16);
        picturesResId.add(R.drawable.a1_l_23);
        regions.put(Region.A1_L, new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.a1_r_9);
        picturesResId.add(R.drawable.a1_r_14);
        picturesResId.add(R.drawable.a1_r_16);
        picturesResId.add(R.drawable.a1_r_23);
        regions.put(Region.A1_R,new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.a2_l_23);
        regions.put(Region.A2_L,new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.a2_r_23);
        regions.put(Region.A2_R, new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.a3_l_23);
        regions.put(Region.A3_L,  new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.a3_r_23);
        regions.put(Region.A3_R, new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.bgic_l_14);
        regions.put(Region.BGIC_L,  new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.bgic_r_14);
        regions.put(Region.BGIC_R,new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.cr_l_16);
        regions.put(Region.CR_L,new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.cr_r_16);
        regions.put(Region.CR_R,new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m1_l_14);
        regions.put(Region.M1_L,new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m1_r_14);
        regions.put(Region.M1_R, new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m2_l_9);
        picturesResId.add(R.drawable.m2_l_14);
        regions.put(Region.M2_L,new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m2_r_9);
        picturesResId.add(R.drawable.m2_r_14);
        regions.put(Region.M2_R, new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m3_l_9);
        picturesResId.add(R.drawable.m3_l_14);
        regions.put(Region.M3_L, new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m3_r_9);
        picturesResId.add(R.drawable.m3_r_14);
        regions.put(Region.M3_R,new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m4_l_16);
        picturesResId.add(R.drawable.m4_l_23);
        regions.put(Region.M4_L, new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m4_r_16);
        picturesResId.add(R.drawable.m4_r_23);
        regions.put(Region.M4_R, new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m5_l_16);
        picturesResId.add(R.drawable.m5_l_23);
        regions.put(Region.M5_L, new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m5_r_16);
        picturesResId.add(R.drawable.m5_r_23);
        regions.put(Region.M5_R, new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m6_l_16);
        picturesResId.add(R.drawable.m6_l_23);
        regions.put(Region.M6_L,new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.m6_r_16);
        picturesResId.add(R.drawable.m6_r_23);
        regions.put(Region.M6_R,new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.p_l_9);
        picturesResId.add(R.drawable.p_l_14);
        picturesResId.add(R.drawable.p_l_16);
        regions.put(Region.P_L, new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.p_r_9);
        picturesResId.add(R.drawable.p_r_14);
        picturesResId.add(R.drawable.p_r_16);
        regions.put(Region.P_R,new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.t_l_14);
        regions.put(Region.T_L,new ArrayList<>(picturesResId));
        picturesResId.clear();
        picturesResId.add(R.drawable.t_r_14);
        regions.put(Region.T_R,new ArrayList<>(picturesResId));

    }

    /**
     * Metoda generująca obrazy mózgu z zaznaczonymi regionami możliwego wystąpienia udaru.
     * Funkcja przechodzi po wszystkich regionach dostarczonych jako parametr. Dla każdego z regionów
     * pobiera jego obraz  zasobów apliakcji i wczytuje do Bitmapy, a następnie odrysowuje przy pomocy
     * klasy Canvas na bitmapie zawierającej przykłądowy obraz CT mózgu.
     * @param _regions lista regionów, które należy zaznaczyć na przykładowych obrazach CT mózgu
     * @return (Bitmap[]) tablica Bitmap zawierająca przerobione obrazy CT mózgu z nanesionymi regionami,
     *          które może obejmować udar
     */
    public static Bitmap[] GenerateOutputImage(List<Region> _regions)
    {
        Bitmap[] brainPictures = new Bitmap[4];
        @SuppressWarnings("unused") Bitmap tmp = BitmapFactory.decodeResource( appContext.getResources(), R.drawable.brain_1);
        brainPictures[0] = BitmapFactory.decodeResource( appContext.getResources(), R.drawable.brain_1);
        brainPictures[1] = BitmapFactory.decodeResource( appContext.getResources(), R.drawable.brain_2);
        brainPictures[2] = BitmapFactory.decodeResource( appContext.getResources(), R.drawable.brain_3);
        brainPictures[3] = BitmapFactory.decodeResource( appContext.getResources(), R.drawable.brain_4);

        if(_regions==null)
            return brainPictures;

        for(Region r:_regions)
        {
            //image Ids
            List<Integer> ids = regions.get(r);

            for(Integer image: ids)
            {
                //brain image
                Bitmap brainImg = brainPictures[pictureBasicPictureRelation.get(image)];

                //region image
                Bitmap regionImage = GetPicture(image);

                //image transformation
                Bitmap bmOverlay = Bitmap.createBitmap(brainImg.getWidth(), brainImg.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bmOverlay);
                canvas.drawARGB(0x00, 0, 0, 0);
                Paint paint = new Paint();
                paint.setAlpha(120);
                canvas.drawBitmap(brainImg, 0, 0, null);
                canvas.drawBitmap(regionImage, 0, 0, paint);

                brainPictures[pictureBasicPictureRelation.get(image)] = bmOverlay;
            }
        }
        return brainPictures;
    }

    /**
     * Metoda pobrająca obraz o podanym ID z zasobów i zwracająca go w postaci Bitmapy.
     * Wykorzystywana wewnątrz klasy nanoszącej na podstawowe zdjęcia CT obrazy reprezentujące poszczególne regiony mózgu.
     * @param resourceID ID zasobu zawierającego obraz
     * @return (Bitmap) bitmapa zawierająca obraz o podanym w parametrze ID
     */
    private static Bitmap GetPicture(Integer resourceID)
    {
        return BitmapFactory.decodeResource( appContext.getResources(), resourceID);
    }
}
