package com.aoyou.daterangeprovider;

import android.content.Context;

/**
 * Created by yanjizhou on 2017/5/5.
 */

public class Core {

    public float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return pxValue / scale + 0.5f;
    }
}
