package com.example.android.basicpermissions.model.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.basicpermissions.R;
import com.example.android.basicpermissions.model.DepartCityInfoVo;
import com.example.android.basicpermissions.view.AlphabetListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by libin on 2016/12/5.
 */

public class PinnedHeaderListViewAdapter<T> extends BasePinnedHeaderAdapter<T> {
    public List<String> alphabets;   //字母索引数据集合，品牌列表比较特殊，需要对原始的分组字母集合进行处理
//    protected SharePreferenceHelper sharePreferenceHelper;
    public PinnedHeaderListViewAdapter(Context context, TreeMap<String, List<T>> mMap, ListView listView,
                                       AlphabetListView alphabetListView) {
        super(context, mMap);
        alphabetListView.setAdapter(listView, this, alphabetPositionListener, initAlphabets(0));
//        sharePreferenceHelper = new SharePreferenceHelper(context);
    }


    AlphabetListView.AlphabetPositionListener alphabetPositionListener = new AlphabetListView.AlphabetPositionListener() {
        @Override
        public int getPosition(String letter) {
            if (alphabetPositionMap != null && alphabetPositionMap.containsKey(letter)) {
                return alphabetPositionMap.get(letter);
            }
            return 0;
        }
    };

    /**
     * 过滤掉手动添加的数据标签
     * @param position  从哪个位置开始
     * @return
     */
    private List<String> initAlphabets(int position) {
        alphabets = new ArrayList<String>();
        if (sections != null && sections.size() > 0) {
            //sections中前两个是客户端加入的分组标签，初始化字母索引时需要剔除，但不能直接从删除sections中原始数据
            int size = sections.size();
            alphabets.add(0, "定位");
            for (int i = position; i < size; i++) {
                alphabets.add(sections.get(i));
            }
        }
        return alphabets;
    }


    @Override
    protected View getListView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.home_template_departcity_select_item_4, null);
            viewHolder.header = (TextView) convertView.findViewById(R.id.pinnedheaderlistview_header);
            viewHolder.tvDepartCityName = (TextView) convertView.findViewById(R.id.tv_depart_city_name);
            viewHolder.viewDepartCityListviewSplite=(View)convertView.findViewById(R.id.view_depart_city_listview_splite);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       // convertView.setBackgroundResource(R.drawable.app_listview_item_bg);

        if (datas != null) {
            int section = getSectionForPosition(position);
            DepartCityInfoVo departCityItem = (DepartCityInfoVo) getItem(position);
            if (departCityItem != null) {
                if (getPositionForSection(section) == position) {   //如果集合中字母对应的位置等于下标值，则显示字母，否则则隐藏
                    viewHolder.header.setVisibility(View.VISIBLE);
                    viewHolder.header.setText(sections.get(section));
                } else {
                    viewHolder.header.setVisibility(View.GONE);
                }
                String cityName = departCityItem.getCityName();
                if (null != cityName) {
                    viewHolder.tvDepartCityName.setText(cityName);
                }
//                //当地选中城市显示为红色
//                String currentCity=sharePreferenceHelper.getSharedPreference(Constants.DEPART_CITY_NAME,"");
//                if(currentCity!=null){
//                    if(cityName.equals(currentCity)){
//                        viewHolder.tvDepartCityName.setTextColor(context.getResources().getColor(R.color.adaptation_four_ff5523));
//                    }
//                    else {
//                        //drawable/home_template_depart_city_word_select_4
//                        viewHolder.tvDepartCityName.setTextColor(context.getResources().getColor(R.color.adaptation_four_333333));
//                    }
//                }
            }
            //显示分隔线
            int nextSection=getSectionForPosition(position+1);
            if(section==nextSection){
                viewHolder.viewDepartCityListviewSplite.setVisibility(View.VISIBLE);
            }
            else{
                viewHolder.viewDepartCityListviewSplite.setVisibility(View.GONE);
            }



        }

        return convertView;
    }

    /**
     * 设置头部固定布局和内容
     * @param header  头部的布局
     * @param section 头部内容
     */
    @Override
    protected void setHeaderContent(View header, String section) {
        TextView textView = (TextView) header.findViewById(R.id.pinnedheaderlistview_header);
        textView.setText(section);
    }


    class ViewHolder {
        private TextView header;// 头部
        private TextView tvDepartCityName;
        private View viewDepartCityListviewSplite;
    }

}
