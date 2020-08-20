package com.aoyou.daterangeprovider;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yanjizhou on 2017/5/8.
 */

public class SelectedResult {
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHoliday()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String holiday = new Holiday().getLunarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) +1, calendar.get(Calendar.DATE), false);
        return holiday;
    }
}
