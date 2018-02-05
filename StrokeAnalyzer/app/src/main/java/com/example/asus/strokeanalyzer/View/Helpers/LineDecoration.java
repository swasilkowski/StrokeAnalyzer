package com.example.asus.strokeanalyzer.View.Helpers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Klasa odpowiedzialna za rysowanie linii oddzielającej od siebie elementy {@link RecyclerView}.
 * Jest to klasa stanowiąca rozszerzenie klasy {@link RecyclerView.ItemDecoration} i służy do uatrakcyjnienia wyglądu
 * aplikacji.
 *
 * @author Marta Marciszewicz
 */

public class LineDecoration extends RecyclerView.ItemDecoration {

    /**
     * Atrybuty stylu aplikacji.
     */
    private static final int[] attributes = new int[]{
            android.R.attr.listDivider
    };

    /**
     * Obiekt odrysowywany jako separator elementów {@link RecyclerView}.
     */
    final private Drawable divider;

    /**
     * Konstruktor klasy pozwala na pobranie atrybutów stylu z kontekstu aplikacji.
     *
     * @param context kontekst aplikacji
     */
    public LineDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(attributes);
        divider = a.getDrawable(0);
        a.recycle();
    }

    /**
     * Metoda pozwalająca na odrysowanie dekoracji na obiekcie klasy Canvas dostarczonym przez
     * widok z RecyclerView. Metoda odrysowuje linie na każdym elemencie RecyclerView.
     *
     * @param c obiekt klasy Canvas, na którym rysowana będzie dekoracja
     * @param parent obiekt RecyclerView, do którego dołączona ma zostać dekoracja
     * @param state aktualny stan RecyclerView, do którego dołączona ma zostać dekoracja
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int left = parent.getPaddingLeft();
        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    /**
     * Metoda wyznacza przesunięcie dla elementu widoku RecyclerView, spowodowane dodaniem dekoracji.
     *
     * @param outRect obiekt, któremu wyznaczane jest przesunięcie
     * @param view widok, do którego ma zostać dołączona dekoracja
     * @param parent obiekt klasy RecyclerView,  do którego dołączona ma zostać dekoracja
     * @param state aktualny stan RecyclerView, do którego dołączona ma zostać dekoracja
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, divider.getIntrinsicHeight());
    }
}
