package com.aoyou.daterangeprovider;

import java.util.Date;

/**
 * Created by yanjizhou on 2017/5/5.
 */

public interface IDateRanderItemOpration {
    void changeBackgroundColor(int color);
    void changeTxtColor(int color);
    void setEnable(boolean isenable);
    void resetDefault();
    void select(boolean isSelected);
    Date getFiledDate();
    int[] getXY();
}
