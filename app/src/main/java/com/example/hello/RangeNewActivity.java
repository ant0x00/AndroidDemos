package com.example.hello;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aoyou.daterangeprovider.DateRander;
import com.aoyou.daterangeprovider.IDateSelectListening;
import com.aoyou.daterangeprovider.SelectedResult;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RangeNewActivity extends BaseActivity implements
        CalendarView.OnCalendarInterceptListener,
        CalendarView.OnCalendarRangeSelectListener,
        CalendarView.OnMonthChangeListener,
        View.OnClickListener {


    private DateRander dateRander;

    public static void show(Context context) {
        context.startActivity(new Intent(context, RangeNewActivity.class));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_range;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        setStatusBarDarkMode();

    }

    @Override
    protected void initData() {
        dateRander = (DateRander) findViewById(R.id.date_ranger);


        Date now = new Date();
        Date startDate = new Date();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(java.util.Calendar.MONTH, 12);
        Date endDate = calendar.getTime();
        java.util.Calendar yesterday = java.util.Calendar.getInstance();
        yesterday.setTime(now);
        yesterday.add(java.util.Calendar.DAY_OF_MONTH, -1);
        Date startEnableDay = now;
        java.util.Calendar interCal = java.util.Calendar.getInstance();
        interCal.setTime(now);
        interCal.add(java.util.Calendar.DAY_OF_MONTH,2);
        startEnableDay=interCal.getTime();
        Date endEnableDay = endDate;
        List<Long> selectedDates = new ArrayList<>();
        dateRander.initSingleSelect(startDate, endDate, startEnableDay, endEnableDay, selectedDates, new IDateSelectListening() {
            @Override
            public void selected(List<SelectedResult> dates) {
//                Intent data = new Intent();
//                data.putExtra(DATE_SELECT, dates.get(0).getDate().getTime());
//                setResult(ResultCodeEnum.DATE_SELECT.value(), data);
//                finish();
            }
        });
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }


    @Override
    public void onClick(View v) {

    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 屏蔽某些不可点击的日期，可根据自己的业务自行修改
     * 如 calendar > 2018年1月1日 && calendar <= 2020年12月31日
     *
     * @param calendar calendar
     * @return 是否屏蔽某些不可点击的日期，MonthView和WeekView有类似的API可调用
     */
    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        return false;
        //return calendar.getTimeInMillis()<getCurrentDayMill() ;
    }


    private long getCurrentDayMill(){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.HOUR,0);
        calendar.set(java.util.Calendar.MINUTE,0);
        calendar.set(java.util.Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
        Toast.makeText(this,
                calendar.toString() + (isClick ? "拦截不可点击" : "拦截设定为无效日期"),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMonthChange(int year, int month) {
        Log.e("onMonthChange", "  -- " + year + "  --  " + month);
    }

    @Override
    public void onCalendarSelectOutOfRange(Calendar calendar) {
        // TODO: 2018/9/13 超出范围提示
    }

    @Override
    public void onSelectOutOfRange(Calendar calendar, boolean isOutOfMinRange) {
        Toast.makeText(this,
                calendar.toString() + (isOutOfMinRange ? "小于最小选择范围" : "超过最大选择范围"),
                Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarRangeSelect(Calendar calendar, boolean isEnd) {

    }

    private static final String[] WEEK = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
}
