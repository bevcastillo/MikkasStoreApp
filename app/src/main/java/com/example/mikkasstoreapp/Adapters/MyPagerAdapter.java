package com.example.mikkasstoreapp.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mikkasstoreapp.Tabs.CompletedPaymentsTabFragment;
import com.example.mikkasstoreapp.Tabs.PendingPaymentsTabFragment;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public MyPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int i) {
        //returning the current tabs
        switch (i){
            case 0:
                PendingPaymentsTabFragment tab1 = new PendingPaymentsTabFragment();
                return tab1;
            case 1:
                CompletedPaymentsTabFragment tab2 = new CompletedPaymentsTabFragment();
                return tab2;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}