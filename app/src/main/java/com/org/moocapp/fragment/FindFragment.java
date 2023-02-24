package com.org.moocapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.pickerviewlibrary.picker.TeaPickerView;
import com.example.pickerviewlibrary.picker.entity.PickerData;
import com.example.pickerviewlibrary.picker.entity.ProvinceBean;
import com.example.pickerviewlibrary.picker.entity.SecondBean;
import com.example.pickerviewlibrary.picker.entity.ThirdBean;
import com.example.pickerviewlibrary.picker.util.JsonArrayUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gklee.regionselector.OnRegionDataSetListener;
import com.gklee.regionselector.RegionBean;
import com.gklee.regionselector.RegionLevel;
import com.gklee.regionselector.RegionSelectDialog;

import com.org.moocapp.R;

import com.org.moocapp.activaty.PublishProblemActivity;
import com.org.moocapp.adapter.FindAdapter;
import com.org.moocapp.dialog.BottomDialog;
import com.org.moocapp.dialog.ConfirmDialogQuit;
import com.org.moocapp.dialog.CustomDialog;
import com.org.moocapp.dialog.MyDialog;
import com.org.moocapp.dialog.WMEditTextDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FindFragment extends BaseFragment {

    /**
     * 爱好列表集合
     */
    private ArrayList<String> mHobbyNameList;//用于选择器显示
    private OptionsPickerView mHobbyPickerView;//选择器
    /**
     * 数据级联选择器
     */
    RegionSelectDialog regionSelectDialog;


    TeaPickerView teaPickerView;
    List<String> mProvinceDatas = new ArrayList<>();

    Map<String, List<String>> mSecondDatas = new HashMap<>();
    Map<String, List<String>> mThirdDatas = new HashMap<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"资讯", "问题"};
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private Button add_problem_btn;

    public FindFragment() {
        // Required empty public constructor
    }

    public static FindFragment newInstance() {
        FindFragment fragment = new FindFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView() {
        viewPager = v.findViewById(R.id.fixedViewPager_message);
        slidingTabLayout = v.findViewById(R.id.slidingTabLayout_message);
        add_problem_btn = v.findViewById(R.id.add_problem_btn);
    }

    @Override
    protected void initData() {

        //初始化时，传入RegionLevel设置三级联动or四级联动）
        regionSelectDialog = new RegionSelectDialog(getActivity(), RegionLevel.LEVEL_FOUR);
        //获取省级数据，此处为测试数据
        List provinceBeans = new ArrayList<>();
        RegionBean temp = new RegionBean("1", "网络工程");
        provinceBeans.add(temp);
        RegionBean temp2 = new RegionBean("2", "后端");
        provinceBeans.add(temp2);
        //此步骤必须，否则无法显示
        regionSelectDialog.setOnRegionDataSetListenr(new OnRegionDataSetListener() {

            //必须有返回值
            @Override
            public List<RegionBean> setProvinceList() {
                return provinceBeans;
            }

            //必须有返回值
            @Override
            public List<RegionBean> setOnProvinceSelected(RegionBean regionBean) {

                //接收选中的省份
//                mProvince = regionBean.getName();
                //返回显示的城市
                List<RegionBean> cityBeans = new ArrayList<>();
                cityBeans.add(new RegionBean("2", "佛山市"));
                return cityBeans;
            }

            //必须有返回值
            @Override
            public List<RegionBean> setOnCitySelected(RegionBean regionBean) {
                //接收选中的城市
//                mCity = regionBean.getName();
                //返回显示的区县
                List<RegionBean> zoneBeans = new ArrayList<>();
                zoneBeans.add(new RegionBean("3", "顺德区"));
                return zoneBeans;
            }

            //四级联动时，必须有返回值。三级联动时，可忽略。
            @Override
            public List<RegionBean> setOnZoneSelected(RegionBean regionBean) {
                //接收选中的区县
//                mZone = regionBean.getName();

                List<RegionBean> zoneList = new ArrayList<>();
                zoneList.add(new RegionBean("4", "乐从镇"));
                //返回显示的街道
                //（如果是三级联动，则不需要返回）
                return zoneList;
            }

            @Override
            public void setOnAreaSelected(RegionBean regionBean) {
                //如果是四级联动，则得到选中的街道,如果是三级联动，则没有返回
//                mArea = regionBean.getName();
//                tv_region.setText(mProvince + " " + mCity + " " + mZone + " " + mArea);
            }

        });
        //========================================初始化爱好列表集合========================================
        mHobbyNameList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            mHobbyNameList.add("" + i);
        }

        //============初始化选择器============
        initHobbyOptionPicker();
        //如果想要直接赋值的话，这样写
        /*if(mHobbyNameList.size() > 0){
            hobbyTv.setText(mHobbyNameList.get(0));//默认展现第一个
        }*/
//============初始化选择器============
        intiPickerView();
//============初始化选择器============

    }

    private void intiPickerView() {
        //一级列表
        ProvinceBean provinceBean = new ProvinceBean();
        mProvinceDatas.addAll(provinceBean.getRepData().getProvince());

        //二级列表
        SecondBean secondBean = new SecondBean();
        mSecondDatas.putAll(secondBean.getRepData().getSecond());

        //三级列表
        ThirdBean thirdBean = new ThirdBean();
        mThirdDatas.putAll(thirdBean.getRepData().getThird());

        Log.i("json", JsonArrayUtil.toJson(mProvinceDatas));
        Log.i("json", JsonArrayUtil.toJson(mSecondDatas));
        Log.i("json", JsonArrayUtil.toJson(mThirdDatas));

        //设置数据有多少层级
        PickerData data = new PickerData();
        data.setFirstDatas(mProvinceDatas);//json: ["广东","江西"]
        data.setSecondDatas(mSecondDatas);//json: {"江西":["南昌","赣州"],"广东":["广州","深圳","佛山","东莞"]}
        data.setThirdDatas(mThirdDatas);//json: {"广州":["天河区","白云区","番禹区","花都区"],"赣州":["章贡区","黄金开发区"],"东莞":["东城","南城"],"深圳":["南山区","宝安区","龙华区"],"佛山":["禅城区","顺德区"],"南昌":["东湖区","青云谱区","青山湖区"]}

        data.setInitSelectText("请选择");

        teaPickerView = new TeaPickerView(getActivity(), data);
        teaPickerView.setScreenH(3)
                .setDiscolourHook(true)
                .setRadius(25)
                .setContentLine(true)
                .setRadius(25)
                .build();

//        add_problem_btn.setOnClickListener( v -> {
//            //显示选择器
//            teaPickerView.show(add_problem_btn);
//        });

        //选择器点击事件
        teaPickerView.setOnPickerClickListener(pickerData -> {
//            Toast.makeText(MainActivity.this,pickerData.getFirstText()+","+pickerData.getSecondText()+","+pickerData.getThirdText(),Toast.LENGTH_SHORT).show();
            showToast(pickerData.getFirstText() + "," + pickerData.getSecondText() + "," + pickerData.getThirdText());
            teaPickerView.dismiss();//关闭选择器
        });
    }

    //初始化爱好选择器
    private void initHobbyOptionPicker() {
        mHobbyPickerView = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = mHobbyNameList.get(options1);
            }
        })
                .setTitleText("选择分类")//标题文字
                .setTitleSize(20)//标题文字大小
                .setTitleColor(getResources().getColor(R.color.black))//标题文字颜色
                .setCancelText("取消")//取消按钮文字
                .setCancelColor(getResources().getColor(R.color.black))//取消按钮文字颜色
                .setSubmitText("确定")//确认按钮文字
                .setSubmitColor(getResources().getColor(R.color.black))//确定按钮文字颜色
                .setContentTextSize(20)//滚轮文字大小
                .setTextColorCenter(getResources().getColor(R.color.black))//设置选中文本的颜色值
                .setLineSpacingMultiplier(1.8f)//行间距
                .setDividerColor(getResources().getColor(R.color.black))//设置分割线的颜色
                .setSelectOptions(0)//设置选择的值
                .build();

        mHobbyPickerView.setPicker(mHobbyNameList);//添加数据
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //以下是添加tab和对应页面Fragments
//        for (String title : mTitles) {
//            mFragments.add(MessageFragment.newInstance(title));
//        }
        mFragments.add(MessageFragment.newInstance(mTitles[0]));
        mFragments.add(ProblemFragment.newInstance());
//        for (int i=0;i<mTitles.length-1;i++){
//            mFragments.add(VideoFragment.newInstance(mTitles[i]));
//        }
//        mFragments.add(MyFragment.newInstance());
        viewPager.setOffscreenPageLimit(mFragments.size());
        //数据
        viewPager.setAdapter(new FindAdapter(getFragmentManager(), mTitles, mFragments));
//        不可用
//        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setViewPager(viewPager, mTitles);
        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                System.out.println(position);
                if (position == 1) {
                    add_problem_btn.setVisibility(View.VISIBLE);
                } else {
                    add_problem_btn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
//        slidingTabLayout.setViewPager(viewPager,mTitles,getActivity(),mFragments);

        add_problem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.add_problem_btn:    //添加问题点击事件
                        navigateTo(PublishProblemActivity.class, null);
                        //----------------------------------------------
//                        CustomDialog customDialog = new CustomDialog(getContext());
//                        customDialog.setsTitle("提示").setsMessage("是否确定执行此操作？").setsCancel("取消", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                customDialog.dismiss();
//                            }
//                        }).setsConfirm("确定", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                customDialog.dismiss();
//                            }
//                        }).show();
                        // ----------------------------------------------
//                        ConfirmDialogQuit confirmDialog = new ConfirmDialogQuit(getContext());
//                        confirmDialog.setOnDialogClickListener(new ConfirmDialogQuit.OnDialogClickListener() {
//                            @Override
//                            public void onOKClick() {
//                                confirmDialog.dismiss();
//                            }
//
//                            @Override
//                            public void onCancelClick() {
//                                confirmDialog.dismiss();
//                            }
//                        });
////                        setCanceledOnTouchOutside、setCancelable二者只能写一
////                        confirmDialog.setCanceledOnTouchOutside(true);
//                        confirmDialog.setCancelable(true);//点击空白处不消失
//                        confirmDialog.show();
                        // ----------------------------------------------

                        //---------------------提示消息-------------------------


//                        MyDialog myDialog = new MyDialog(getContext());
//                        if (myDialog.isShowing()) {
//                            myDialog.dismiss();
//                        } else {
//                            myDialog.show();
//                        }
                        //-----------------------底部弹出-----------------------
//                        BottomDialog dialog = new BottomDialog(getContext(), R.layout.layout_dialog_login_out,
//                                new int[]{R.id.bt_ok, R.id.bt_cancel});
//                        dialog.show();
//                        dialog.setOnBottomItemClickListener(new BottomDialog.OnBottomItemClickListener() {
//                            @Override
//                            public void onBottomItemClick(BottomDialog dialog, View view) {
//                                switch (view.getId()) {
//                                    case R.id.bt_ok:   //退出登录
//                                        //服务器登出  mCenterPI.logout();
//                                        dialog.cancel();
//                                        break;
//                                    case R.id.bt_cancel:  //取消
//                                        dialog.cancel();
//                                        break;
//                                }
//                            }
//                        });
                        //----------------------列表选择器------------------------
//                        mHobbyPickerView.show();
                        //-------------------数据级联选择器---------------------------
//                        regionSelectDialog.showDialog();
                        //-------------------数据级联选择器---------------------------
//                        teaPickerView.show(add_problem_btn);
                        //
                        break;
                }
            }
        });
    }
    //    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        //        根据布局文件生成视图对象
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_find, container, false);
//        viewPager = view.findViewById(R.id.fixedViewPager_message);
//        slidingTabLayout = view.findViewById(R.id.slidingTabLayout_message);
//        add_problem_btn = view.findViewById(R.id.add_problem_btn);
//        return view;
//    }
}