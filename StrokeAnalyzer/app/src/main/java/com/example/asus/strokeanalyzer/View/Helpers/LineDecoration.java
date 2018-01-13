package com.example.asus.strokeanalyzer.View.Helpers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Marta on 22.03.2017.
 */

public class LineDecoration extends RecyclerView.ItemDecoration {

    private static final int[] attributes = new int[]{
            android.R.attr.listDivider
    };

    private Drawable divider;

    public LineDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(attributes);
        divider = a.getDrawable(0);
        a.recycle();
    }

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

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, divider.getIntrinsicHeight());
    }
}
