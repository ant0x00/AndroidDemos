package com.aoyou.daterangeprovider;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yanjizhou on 2017/5/4.
 */

public class DateRander extends LinearLayout implements DateRanderItem.ISelectListening, View.OnClickListener {

    private Date startDate, endDate, startEnableDate, endEnableDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    private SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    private Date now;
    private boolean canMultiSelect;
    private IDateSelectListening iDateSelectListening;
    private List<SelectedResult> selectedDates = new ArrayList<>();
    private RelativeLayout rlTab;

    private TextView tvFirstDateTitle;
    private TextView tvSecondDateTitle;

    private String firstDateTitle, secondDateTitle;

    private View selectedLine;

    private LinearLayout llFirstDate;
    private LinearLayout llSecondDate;

    private TextView tvFirstDate;

    private TextView tvSecondDate;

    private RelativeLayout rlFrame;

    private TextView moveDate;
    private RelativeLayout rlSelectedLineFrame;

    private LinearLayout ll_frame;

    private ScrollView scrollView;

    private Date date;
    public DateRander(Context context) {
        super(context);

    }

//    public DateRander(Context context, Date startDate, Date endDate, Date startEnableDate, Date endEnableDate) {
//        super(context);
//
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.startEnableDate = startEnableDate;
//        this.endEnableDate = endEnableDate;
//    }

    public DateRander(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public DateRander(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DateRander(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        
    }

    public void initSingleSelect(Date startDate, Date endDate, Date startEnableDate, Date endEnableDate, IDateSelectListening iDateSelectListening)
    {
        selectedDates = new ArrayList<>();
        this.startDate = removeTime(startDate);
        this.endDate = removeTime(endDate);
        this.startEnableDate = removeTime(startEnableDate);
        this.endEnableDate = removeTime(endEnableDate);
        this.canMultiSelect = false;
        this.iDateSelectListening = iDateSelectListening;

        String s = yyyyMMdd.format(new Date());

        try {
            now = yyyyMMdd.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        init();
    }

    public void initSingleSelect(Date startDate, Date endDate, Date startEnableDate, Date endEnableDate, List<Long> selectedDates, IDateSelectListening iDateSelectListening)
    {

        this.startDate = removeTime(startDate);
        this.endDate = removeTime(endDate);
        this.startEnableDate = removeTime(startEnableDate);
        this.endEnableDate = removeTime(endEnableDate);
        this.canMultiSelect = false;

        this.iDateSelectListening = iDateSelectListening;


        for(int i=0; i<selectedDates.size(); i++)
        {
            if(selectedDates.get(i) == 0)
                continue;
            SelectedResult selectedResult = new SelectedResult();
            selectedResult.setDate(removeTime(new Date(selectedDates.get(i))));
            this.selectedDates.add(selectedResult);
        }

        String s = yyyyMMdd.format(new Date());

        try {
            now = yyyyMMdd.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        init();
    }

    public void initDoubleSelect(Date startDate, Date endDate, Date startEnableDate, Date endEnableDate, String firstDateTitle, String secondDateTitle, IDateSelectListening iDateSelectListening)
    {

        this.startDate = removeTime(startDate);
        this.endDate = removeTime(endDate);
        this.startEnableDate = removeTime(startEnableDate);
        this.endEnableDate = removeTime(endEnableDate);
        this.canMultiSelect = true;
        this.iDateSelectListening = iDateSelectListening;
        this.firstDateTitle = firstDateTitle;
        this.secondDateTitle = secondDateTitle;

        String s = yyyyMMdd.format(new Date());

        try {
            now = yyyyMMdd.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        init();
    }

    public void initDoubleSelect(Date startDate, Date endDate, Date startEnableDate, Date endEnableDate, String firstDateTitle, String secondDateTitle, List<Long> selectedDates, IDateSelectListening iDateSelectListening)
    {
        for(int i=0; i<selectedDates.size(); i++)
        {
            if(selectedDates.get(i) == 0)continue;
            SelectedResult selectedResult = new SelectedResult();
            selectedResult.setDate(removeTime(new Date(selectedDates.get(i))));
            this.selectedDates.add(selectedResult);
        }

        if(this.selectedDates.size() > 0)
        {
            startEnableDate = this.selectedDates.get(0).getDate();
        }

        initDoubleSelect(startDate, endDate, startEnableDate, endEnableDate, firstDateTitle, secondDateTitle, iDateSelectListening);
        if(selectedDates.size() > 0 && selectedDates.get(0) > 0) {
            String d = yyyyMMdd.format(selectedDates.get(0));
            tvFirstDate.setVisibility(VISIBLE);
            changeTabStatus(true, tvFirstDate, tvFirstDateTitle, d);
            moveToRight();
        }
    }

    public void init()
    {
        View view = View.inflate(getContext(), R.layout.date_ranger_frame, null);

        ll_frame = (LinearLayout) view.findViewById(R.id.date_frame);

        scrollView = (ScrollView) view.findViewById(R.id.sv_frame);

        llFirstDate = (LinearLayout) view.findViewById(R.id.ll_first_date);
        llFirstDate.setOnClickListener(this);
        llSecondDate = (LinearLayout) view.findViewById(R.id.ll_second_date);
        llSecondDate.setOnClickListener(this);

        tvFirstDateTitle = (TextView) view.findViewById(R.id.tv_first_date_title);
        tvFirstDate = (TextView) view.findViewById(R.id.tv_first_date);

        tvSecondDateTitle = (TextView) view.findViewById(R.id.tv_second_date_title);

        tvSecondDate = (TextView) view.findViewById(R.id.tv_second_date);

        rlTab = (RelativeLayout) view.findViewById(R.id.rl_tab);

        selectedLine = view.findViewById(R.id.selected_line);

        moveDate = (TextView)view.findViewById(R.id.move_date);

        rlFrame = (RelativeLayout)view.findViewById(R.id.rl_frame);

        rlSelectedLineFrame = (RelativeLayout) view.findViewById(R.id.rl_selected_line_frame);

        if(canMultiSelect) {
            rlTab.setVisibility(VISIBLE);
            tvFirstDateTitle.setText(firstDateTitle);
            tvSecondDateTitle.setText(secondDateTitle);
        }
        else {
            rlTab.setVisibility(GONE);
        }

        final Calendar cal = Calendar.getInstance();
        try {
            startDate = formatDate(startDate);
            endDate = formatDate(endDate);

            final long[] tmpDate = {startDate.getTime()};
//            if(this.selectedDates.size() > 0)
//            {
//                tmpDate[0] = this.selectedDates.get(0).getDate().getTime();
//            }

            date = startDate;;
            new Thread(new Runnable() {
                @Override
                public void run() {


                    while(endDate.getTime() - date.getTime() >= 0)
                    {

                        cal.setTime(date);
                        initPreMothDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), ll_frame);
                        cal.add(Calendar.MONTH, 1);
                        date = cal.getTime();
                    }
                }
            }).start();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        this.addView(view);
    }

    private void initPreMothDate(int year, int month, final LinearLayout ll_frame)
    {
        final View view = View.inflate(getContext(), R.layout.date_ranger_date, null);

        TextView tvSectionTitle = (TextView)view.findViewById(R.id.tv_year_month);
        month = month + 1;

        tvSectionTitle.setText(year + "年" + month + "月");

        TableLayout tableLayout = (TableLayout)view.findViewById(R.id.tabel_date);

        initTableLayout(tableLayout, year, month);

        ((Activity)getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ll_frame.addView(view);
            }
        });

    }


    private Date formatDate(Date date) throws ParseException {

        String s = sdf.format(date);
        return sdf.parse(s);
    }

    private List<IDateRanderItemOpration> lisDays = new ArrayList<>();
    public void initTableLayout(TableLayout tableLayout, int year, int month)
    {
        try {
            Calendar cal = Calendar.getInstance();
            Date date = sdf.parse(year + "-" + month);
            cal.setTime(date);
            int weekday = cal.get(Calendar.DAY_OF_WEEK) -1;

            int monthTmp = cal.get(Calendar.MONTH) + 1;

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            while(monthTmp == month) {
                TableRow tableRow = new TableRow(getContext());
                tableRow.setLayoutParams(layoutParams);
                tableLayout.addView(tableRow);

                for (int i = 0; i < 7; i++) {


                    if(i == weekday)
                    {
                        DateRanderItem dateRanderItem = new DateRanderItem(getContext(), now);

                        dateRanderItem.setSelectListening(this);
                        dateRanderItem.init(tableRow, weekday, cal.getTime(), startEnableDate, endEnableDate);
                        lisDays.add(dateRanderItem);

                        if(this.selectedDates.size() > 0)
                        {
                            Date selectedDate = this.selectedDates.get(0).getDate();
                            long selectedDateLong = selectedDate.getTime();
                            if(startEnableDate.getTime() <= selectedDateLong && cal.getTime().getTime() == selectedDateLong)
                            {
                                dateRanderItem.select(true);
                            }
                        }
//                        dateRanderItem.setDay(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), "");
                        weekday++;
                        if(weekday >6)
                            weekday = 0;
                        cal.add(Calendar.DAY_OF_MONTH, 1);
                        if(cal.get(Calendar.MONTH) + 1 != month)
                            break;
                    }
                    else
                    {
                        View vItem = View.inflate(getContext(), R.layout.date_ranger_date_item, null);

                        tableRow.addView(vItem);
                    }

                }



                monthTmp = cal.get(Calendar.MONTH)+1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void select(Date date, IDateRanderItemOpration selectedObj) {
        date = removeTime(date);
        SelectedResult selectedResult = new SelectedResult();
        selectedResult.setDate(date);

        String displayDate = yyyyMMdd.format(date);
        if(canMultiSelect) {
            selectedDates.add(selectedResult);
            if (selectedDates.size() == 1) {
                for (int i = 0; i < lisDays.size(); i++) {
                    if (date.getTime() == lisDays.get(i).getFiledDate().getTime()) {
                        break;
                    }

                    lisDays.get(i).setEnable(false);
                }

                moveDate.setText(displayDate);
                moveDateToLeftTab(selectedObj, displayDate);
                moveToRight();
                scroll(selectedObj);
            }

            if (selectedDates.size() > 1) {
                moveDateToRightTab(selectedObj,displayDate);
                iDateSelectListening.selected(selectedDates);
                selectedDates.clear();
            }
        }
        else {
            selectedDates = new ArrayList<>();

            selectedDates.add(selectedResult);
            if (selectedDates.size() > 0) {
                iDateSelectListening.selected(selectedDates);
                selectedDates.clear();
            }
        }
    }

    private void moveToLeft()
    {
        tvFirstDateTitle.post(new Runnable() {
            @Override
            public void run() {
                int[] xy = getViewXY(llFirstDate);
                int[] maginx = getViewXY(rlSelectedLineFrame);



                moveSelectLine(xy[0] - maginx[0]);

                changeTabStatus(false, tvFirstDate, tvFirstDateTitle, "");
                changeTabStatus(false, tvSecondDate, tvSecondDateTitle, "");

                selectedDates.clear();
                //初始化
                for(int i=0; i<lisDays.size(); i++)
                {
                    lisDays.get(i).resetDefault();
                }
            }
        });
    }


    private void moveToRight()
    {
        tvSecondDateTitle.post(new Runnable() {
            @Override
            public void run() {

                if(selectedDates.size() == 0)
                {
                    Toast.makeText(getContext(), "请选择" + firstDateTitle, Toast.LENGTH_LONG).show();
                    return;
                }
                int[] xy = getViewXY(llSecondDate);

                int[] maginx = getViewXY(rlSelectedLineFrame);
                moveSelectLine(xy[0] - maginx[0]);
//
//                if(selectedDates.size() > 0)
//                {
//                    int y = xy[1];
//
//                    int[] frameXy = getViewXY(ll_frame);
//
//                    int scrollY = y - frameXy[1];
//
//                    scrollView.scrollTo(scrollView.getScrollX(), scrollY);
//                }
            }
        });
    }

    private void scroll(IDateRanderItemOpration selectedObj)
    {


        if(selectedDates.size() > 0)
        {
            int[] xy = selectedObj.getXY();
            int y = xy[1];

            int[] frameXy = getViewXY(ll_frame);

            int scrollY = y - frameXy[1];

            scrollView.scrollTo(scrollView.getScrollX(), scrollY);
        }
    }

    private void changeTabStatus(boolean isSelected, TextView display, TextView title, String displayDate)
    {
        if(isSelected)
        {
//            display.setVisibility(VISIBLE);
            display.setText(displayDate);
            moveDate.setText(displayDate);
            title.setTextSize(new Core().px2dip(getContext(), getResources().getDimensionPixelOffset(R.dimen.size_17dp)));
        }
        else
        {
            display.setVisibility(GONE);
            display.setText("");
            moveDate.setText("");
            title.setTextSize(new Core().px2dip(getContext(), getResources().getDimensionPixelOffset(R.dimen.size_19dp)));
        }
    }

    private void moveDateToLeftTab(final IDateRanderItemOpration selectedObj, String displayDate)
    {
        changeTabStatus(true, tvFirstDate, tvFirstDateTitle, displayDate);
        tvFirstDate.post(new Runnable() {
            @Override
            public void run() {
                int[] endXY = getViewXY(tvFirstDate);
                int[] parentXY = getViewXY((View)tvFirstDate.getParent());
                int[] startXY = selectedObj.getXY();
                move(moveDate, startXY[0], startXY[1], endXY[0]-parentXY[0], endXY[1]-parentXY[1], new LeftAnimatorEvent());
            }
        });
    }

    private void moveDateToRightTab(final IDateRanderItemOpration selectedObj, String displayDate)
    {
        changeTabStatus(true, tvSecondDate, tvSecondDateTitle, displayDate);
        tvSecondDate.post(new Runnable() {
            @Override
            public void run() {
                int[] endXY = getViewXY(tvSecondDate);
                int[] parentXY = getViewXY((View)tvSecondDate.getParent());
                int[] startXY = selectedObj.getXY();
                move(moveDate, startXY[0], startXY[1], endXY[0], endXY[1], new RightAnimatorEvent());
            }
        });
    }


    private int[] getViewXY(View view)
    {

        int[] xy = new int[2];
        view.getLocationOnScreen(xy);
        return xy;
    }

    private void move(final TextView moveDate, int startX, int startY, int endX, int endY, Animator.AnimatorListener animatorListener )
    {
        int[] frameXY = getViewXY(rlFrame);
        moveDate.setX(startX - frameXY[0] - (moveDate.getWidth()/2));
        moveDate.setY(startY - frameXY[1]);

        List<Animator> animators = new ArrayList<>();
        ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(moveDate, "translationX", startX, endX);
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(moveDate, "translationY", startY, endY);
        animators.add(translationXAnim);
        animators.add(translationYAnim);
        AnimatorSet btnSexAnimatorSet = new AnimatorSet();

        btnSexAnimatorSet.addListener(animatorListener);
        btnSexAnimatorSet.playTogether(animators);
        btnSexAnimatorSet.setStartDelay(300);
        btnSexAnimatorSet.start();

    }

    class LeftAnimatorEvent implements Animator.AnimatorListener{

        @Override
        public void onAnimationStart(Animator animation) {
            moveDate.setVisibility(VISIBLE);

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            moveDate.setVisibility(GONE);
            tvFirstDate.setVisibility(VISIBLE);
            tvFirstDate.setText(moveDate.getText());
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    class RightAnimatorEvent implements Animator.AnimatorListener{

        @Override
        public void onAnimationStart(Animator animation) {
            moveDate.setVisibility(VISIBLE);

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            moveDate.setVisibility(GONE);
            tvSecondDate.setVisibility(VISIBLE);
            tvSecondDate.setText(moveDate.getText());
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    private void moveSelectLine(int x)
    {
        float lineX = selectedLine.getX();


        ObjectAnimator.ofFloat(selectedLine, "translationX", lineX, x).setDuration(300).start();
    }

    @Override
    public void onClick(View v) {
        if(v == llFirstDate)
        {
            moveToLeft();
        }
        else if(v == llSecondDate)
        {
            moveToRight();
        }
    }

    private Date removeTime(Date date)
    {
        Date result = null;
        String s = yyyyMMdd.format(date);

        try {
            result = yyyyMMdd.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
