package com.example.android.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;

    public MyItemDecoration(Context context) {
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = dpToPx(5);
        outRect.right = dpToPx(5);
        outRect.bottom = dpToPx(5);
    }

    private int dpToPx(int dps) {
        return Math.round(context.getResources().getDisplayMetrics().density * dps);
    }


}
