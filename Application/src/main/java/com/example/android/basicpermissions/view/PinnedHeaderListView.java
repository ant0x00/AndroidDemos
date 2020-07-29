package com.example.android.basicpermissions.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.WrapperListAdapter;

import com.example.android.basicpermissions.utils.Common;


/**
 * Created by libin on 2016/12/5.
 */

/**
 * A ListView that maintains a header pinned at the top of the list. The
 * pinned header can be pushed up and dissolved as needed.
 * ListView,维护一个头固定在列表的顶部。固定头可以推高,渐变溶解。
 */
public class PinnedHeaderListView extends ListView {

    /**
     * Adapter interface.  The list adapter must implement this interface.
     * 适配器接口。适配器列表必须实现这个接口。
     */
    public interface PinnedHeaderAdapter {

        /**
         * Pinned header state: don't show the header.
         * 固定头状态:不显示标题。
         */
        public static final int PINNED_HEADER_GONE = 0;

        /**
         * Pinned header state: show the header at the top of the list.
         * 固定头状态:显示列表的顶部的标题。
         */
        public static final int PINNED_HEADER_VISIBLE = 1;

        /**
         * Pinned header state: show the header. If the header extends beyond
         * the bottom of the first shown element, push it up and clip.
         * 固定头状态:显示标题。如果头超出第一的底部显示的元素,把它剪辑。
         */
        public static final int PINNED_HEADER_PUSHED_UP = 2;

        /**
         * Computes the desired state of the pinned header for the given
         * position of the first visible list item. Allowed return values are
         * 计算所需的固定头状态给定位置的第一个列表项可见。允许返回值
         *
         * {@link #PINNED_HEADER_GONE}, {@link #PINNED_HEADER_VISIBLE} or
         * {@link #PINNED_HEADER_PUSHED_UP}.
         */
        int getPinnedHeaderState(int position);

        /**
         * Configures the pinned header view to match the first visible list item.
         * 配置固定头视图匹配第一个列表项可见。
         *
         * @param header pinned header view.  固定头视图。
         * @param position position of the first visible list item.  第一个列表项可见的位置。
         * @param alpha fading of the header view, between 0 and 255.  渐变色的值在0到255之间
         */
        void configurePinnedHeader(View header, int position, int alpha);
    }

    private static final int MAX_ALPHA = 255;

    private PinnedHeaderAdapter mAdapter;
    private View mHeaderView;
    private boolean mHeaderViewVisible;

    private int mHeaderViewWidth;

    private int mHeaderViewHeight;

    private View mFirstHeaderView;
    private int mFirstHeaderViewWidth;
    private int mFirstHeaderViewHeight;
    private Context context;

    private boolean isHasAlphabet = true;

    public PinnedHeaderListView(Context context) {
        super(context);
        this.context = context;
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void hindHeaderView(){
        if(mHeaderView != null){
            mHeaderView.setVisibility(View.GONE);
        }
    }

    public void showHeaderView(){
        if(mHeaderView != null){
            mHeaderView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置头部固定的view
     * @param view  头部显示固定view
     */
    public void setPinnedHeaderView(View view) {
        mHeaderView = view;
        if (mHeaderView != null) {
            setFadingEdgeLength(0);
        }
        requestLayout();
    }

    /**
     * 设置头部固定的view
     * @param view  头部显示固定view
     * @param isHasAlphabet  是否显示右侧字母导航  flse不显示，ture显示
     */
    public void setPinnedHeaderView(View view, boolean isHasAlphabet) {
        this.isHasAlphabet = isHasAlphabet;
        mHeaderView = view;
        if (mHeaderView != null) {
            setFadingEdgeLength(0);
        }
        requestLayout();
    }
    /**
     * 设置头部固定的view
     * @param view  头部显示固定view
     */
    public void setPinnedHeaderView(View view, View firstView) {
        mHeaderView = view;
        mFirstHeaderView=firstView;
        if (mHeaderView != null) {
            setFadingEdgeLength(0);
        }
        requestLayout();
//        if(mFirstHeaderView!=null){
//            int w = View.MeasureSpec.makeMeasureSpec(0,
//                    View.MeasureSpec.UNSPECIFIED);
//            int h = View.MeasureSpec.makeMeasureSpec(0,
//                    View.MeasureSpec.UNSPECIFIED);
//            mFirstHeaderView.measure(w, h);
//            mFirstHeaderViewHeight = mFirstHeaderView.getMeasuredHeight();
//            mFirstHeaderViewWidth = mFirstHeaderView.getMeasuredWidth();
//        }

    }

    /**
     * Sets the data behind this ListView.
     *
     * The adapter passed to this method may be wrapped by a {@link WrapperListAdapter},
     * depending on the ListView features currently in use. For instance, adding
     * headers and/or footers will cause the adapter to be wrapped.
     * 这背后的数据视图。适配器传递给这个方法可以包装一个{ @link WrapperListAdapter },
     * 取决于ListView目前使用的特性。例如,添加页眉和/或页脚会导致适配器包装。
     *
     * @param adapter The ListAdapter which is responsible for maintaining the
     *        data backing this list and for producing a view to represent an
     *        item in that data set.
     *                ListAdapter负责维护的数据支持这个列表,生成一个视图来表示一个数据集的项
     *
     * @see #getAdapter()
     */
    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (PinnedHeaderAdapter)adapter;
    }

    /**
     * 测量view的大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Common commonTools=new Common(context);
        if (mHeaderView != null) {
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
            if (isHasAlphabet) {
                mHeaderViewWidth = mHeaderView.getMeasuredWidth()-commonTools.dip2px(30);
            } else {
                mHeaderViewWidth = mHeaderView.getMeasuredWidth();
            }

            mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        }

   }

    /**
     * 设置子view的位置
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mHeaderView != null) {
            mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
            configureHeaderView(getFirstVisiblePosition());
        }
    }

    public void configureHeaderView(int position) {
        if (mHeaderView == null) {
            return;
        }
        if(position==0){
            mHeaderViewVisible = false;
            return;//position为0时是头文件
        }
        else{
            mHeaderViewVisible=true;
        }

        if (isHasAlphabet){
            position = position - 1;
        }

        if (mAdapter != null) {
            int state = mAdapter.getPinnedHeaderState(position);
            switch (state) {
                case PinnedHeaderAdapter.PINNED_HEADER_GONE: {
                    mHeaderViewVisible = false;
                    break;
                }

                case PinnedHeaderAdapter.PINNED_HEADER_VISIBLE: {
                    mAdapter.configurePinnedHeader(mHeaderView, position, MAX_ALPHA);
                    if (mHeaderView.getTop() != 0) {
                        mHeaderView.layout(0, 0, mHeaderViewWidth,
                                mHeaderViewHeight);
                    }
//                    if(position>0)
//                    {
//                        mHeaderViewVisible = true;
//                    }
//                    else
//                    {
//                        mHeaderViewVisible = false;
//                    }
                    break;
                }

                case PinnedHeaderAdapter.PINNED_HEADER_PUSHED_UP: {
                    View firstView = getChildAt(0);
                    if (null == firstView) {
                        return;
                    }
                    int bottom = firstView.getBottom();
                    //                int itemHeight = firstView.getHeight();
                    int headerHeight = mHeaderView.getHeight();
                    int y;
                    int alpha;
                    headerHeight -= 1;//解决推动时候，中间有缝隙问题
                    if (bottom < headerHeight) {
                        y = (bottom - headerHeight);
                        alpha = MAX_ALPHA * (headerHeight + y) / headerHeight;
                    } else {
                        y = 0;
                        alpha = MAX_ALPHA;
                    }
                    mAdapter.configurePinnedHeader(mHeaderView, position, alpha);
                    if (mHeaderView.getTop() != y) {
                        mHeaderView.layout(0, y, mHeaderViewWidth,
                                mHeaderViewHeight + y);
                    }
                    mHeaderViewVisible = true;
                    break;
                }
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHeaderViewVisible) {

            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }

}
