package com.komoriwu.one.all;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.komoriwu.one.R;
import com.komoriwu.one.all.fragment.CategoryFragment;
import com.komoriwu.one.all.fragment.DailyFragment;
import com.komoriwu.one.all.fragment.FindFragment;
import com.komoriwu.one.all.fragment.RecommendFragment;
import com.komoriwu.one.all.leftmenu.AllCategoriesActivity;
import com.komoriwu.one.all.mvp.AllContract;
import com.komoriwu.one.all.mvp.AllPresenter;
import com.komoriwu.one.all.search.SearchActivity;
import com.komoriwu.one.base.MvpBaseFragment;
import com.komoriwu.one.model.bean.event.ScrollYEvent;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by KomoriWu
 * on 2017/9/15.
 */

public class AllFragment extends MvpBaseFragment<AllPresenter> implements AllContract.View {
    private static final String TAG = AllFragment.class.getSimpleName();
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_view_pager)
    SmartTabLayout tabViewPager;
    @BindView(R.id.iv_all_category)
    ImageView ivAllCategory;
    @BindView(R.id.iv_search)
    ImageView ivSearch;

    @Override
    protected void setInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all, null);
    }

    @Override
    public void init() {
        initTab();
    }

    private void initTab() {
        String[] tabs = getResources().getStringArray(R.array.tabs);
        FragmentPagerItems pages = new FragmentPagerItems(getActivity());
        pages.add(FragmentPagerItem.of(tabs[0], FindFragment.class));
        pages.add(FragmentPagerItem.of(tabs[1], RecommendFragment.class));
        pages.add(FragmentPagerItem.of(tabs[2], DailyFragment.class));
        for (int i = 3; i < tabs.length; i++) {
            pages.add(FragmentPagerItem.of(tabs[i], CategoryFragment.class));
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        tabViewPager.setViewPager(viewPager);
    }


    @Override
    public void showErrorMsg(String msg) {

    }

    public void scrollToTop() {
        EventBus.getDefault().post(new ScrollYEvent(viewPager.getCurrentItem()));
    }


    @OnClick({R.id.iv_all_category, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_all_category:
                startActivity(new Intent(getActivity(), AllCategoriesActivity.class));
                break;
            case R.id.iv_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.screen_top_in, R.anim.screen_null);
                break;
        }

    }


}
