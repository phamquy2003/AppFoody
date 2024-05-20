package com.henrryd.appfoody2.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.henrryd.appfoody2.View.Fragments.food_fragment;
import com.henrryd.appfoody2.View.Fragments.where_fragment;

public class AdapterViewHome extends FragmentStatePagerAdapter {

    food_fragment foodFragment;
    where_fragment whereFragment;
    public AdapterViewHome(@NonNull FragmentManager fm) {
        super(fm);
        foodFragment = new food_fragment();
        whereFragment = new where_fragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return whereFragment ;
            case 1:
                return foodFragment ;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
