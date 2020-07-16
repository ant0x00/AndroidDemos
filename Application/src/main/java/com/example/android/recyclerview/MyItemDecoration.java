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
        if(parent.getChildAdapterPosition(view) == 0){
            outRect.left = dpToPx(10);
            outRect.right = dpToPx(5);
        }else if(parent.getChildAdapterPosition(view)>0 && parent.getChildAdapterPosition(view)%3 == 2){
            outRect.left = dpToPx(10);
            outRect.right = dpToPx(5);
        }else if(parent.getChildAdapterPosition(view)%3 == 0){
            outRect.left = dpToPx(5);
            outRect.right = dpToPx(5);
        }else {
            outRect.left = dpToPx(5);
            outRect.right = dpToPx(10);
        }
        outRect.bottom = dpToPx(5f);
    }

    private int dpToPx(int dps) {
        return Math.round(context.getResources().getDisplayMetrics().density * dps);
    }

    public int dpToPx(float dips)
    {
        return (int) (dips *  context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
