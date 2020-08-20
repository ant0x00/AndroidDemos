package com.aoyou.daterangeprovider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Yan on 2017/5/4.
 */

public class DateRanderItem implements IDateRanderItemOpration, View.OnClickListener {
    private Context context;
    private boolean selected = false;
    private boolean canSelected = true;
    private Date now;

    public DateRanderItem(Context context, Date now)
    {
        this.context = context;
        this.now = now;
    }


    private View vItem;
    private TextView tvDay;
    private TextView tvHoliday;
    private Date currentFiledDate;
    private Date startEnableDate;
    private Date endEnableDate;
    private int weekday;
    private ISelectListening iSelectListening;

    private IDateRanderItemOpration iDateRanderItemOpration;

    public void init(TableRow tableRow, int weekday, Date date, Date startEnableDate, Date endEnableDate)
    {
        this.currentFiledDate = date;
        this.weekday = weekday;
        this.startEnableDate = startEnableDate;
        this.endEnableDate = endEnableDate;
        iDateRanderItemOpration = this;

        vItem = View.inflate(context, R.layout.date_ranger_date_item, null);
        tvDay = (TextView)vItem.findViewById(R.id.tv_day_of_week);
        tvHoliday = (TextView)vItem.findViewById(R.id.tv_holiday);
        tableRow.addView(vItem);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        setDay(String.valueOf(date.getDate()), new Holiday().getLunarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) +1, calendar.get(Calendar.DATE), false));

        if(startEnableDate.getTime() > currentFiledDate.getTime() || endEnableDate.getTime() < currentFiledDate.getTime())
        {
            setEnable(false);
        }
        else if(weekday == WEEK_END.SARTDAY.value() || weekday == WEEK_END.SUNDAY.value())
        {
            setEnable(true);
        }
        else
        {
            setEnable(true);
        }


    }


    private void setDay(String day, String holiday)
    {
        if(currentFiledDate.getTime() == now.getTime())
        {
            tvDay.setText("今天");
        }
        else {
            tvDay.setText(day);
        }
        tvHoliday.setText(holiday);
    }

    @Override
    public void changeBackgroundColor(int color) {
        vItem.setBackgroundColor(color);
    }

    @Override
    public void changeTxtColor(int color) {
        tvDay.setTextColor(color);
        tvHoliday.setTextColor(color);
    }

    @Override
    public void setEnable(boolean isenable) {

        if(!canSelected)return;
//        select(isenable);
        if(isenable)
        {
            resetDefault();
            vItem.setOnClickListener(this);
        }
        else
        {
            setDisablField();
            vItem.setOnClickListener(null);
        }


    }

    @Override
    public void resetDefault() {

        if(startEnableDate.getTime() > currentFiledDate.getTime() || endEnableDate.getTime() < currentFiledDate.getTime())
        {
            setDisablField();
        }
        else if(weekday == WEEK_END.SARTDAY.value() || weekday == WEEK_END.SUNDAY.value())
        {
            setWeekDay();
            vItem.setOnClickListener(this);
        }
        else
        {
            setDefaultField();
            vItem.setOnClickListener(this);
        }
    }

    @Override
    public void select(boolean isSelected) {
        this.selected = isSelected;

        if(isSelected)
        {
            setSelected();
        }
        else
        {
            resetDefault();
        }
    }

    @Override
    public Date getFiledDate() {
        return currentFiledDate;
    }

    @Override
    public int[] getXY() {
        int[] xy = new int[2];
        ((View)vItem).getLocationOnScreen(xy);
        return xy;
    }

    private void setDefaultField()
    {
        changeBackgroundColor(context.getResources().getColor(R.color.color_ffffff));
        changeTxtColor(context.getResources().getColor(R.color.color_333333));
    }

    private void setDisablField()
    {
        changeBackgroundColor(context.getResources().getColor(R.color.color_ffffff));
        changeTxtColor(context.getResources().getColor(R.color.color_cccccc));
    }

    private void setWeekDay()
    {
        changeBackgroundColor(context.getResources().getColor(R.color.transparent));
        changeTxtColor(context.getResources().getColor(R.color.color_ff5523));
    }

    private void setSelected()
    {
        changeBackgroundColor(context.getResources().getColor(R.color.color_ff5523));
        changeTxtColor(context.getResources().getColor(R.color.color_ffffff));
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelectListening(ISelectListening iSelectListening)
    {
        this.iSelectListening = iSelectListening;
    }

    @Override
    public void onClick(View v) {
        select(true);
        iSelectListening.select(currentFiledDate, iDateRanderItemOpration);
    }

    enum WEEK_END
    {
        SUNDAY(0),
        SARTDAY(6);
        private int nCode;

        // 构造函数，枚举类型只能为私有
        private WEEK_END(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }

        public int value()
        {
            return nCode;
        }
    }

    interface ISelectListening
    {
        void select(Date date, IDateRanderItemOpration selectedObj);
    }
}
