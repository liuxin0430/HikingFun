package com.example.hikingfun;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 4; // total page number

    private StepFragment stepFragment = null;
    private GpsFragment gpsFragment = null;
    private NoteFragment noteFragment = null;
    private MoreFragment moreFragment = null;

    public MyFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        gpsFragment = new GpsFragment();
        stepFragment = new StepFragment();
        noteFragment = new NoteFragment();
        moreFragment = new MoreFragment();

    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_GPS:
                fragment = gpsFragment;
                break;
            case MainActivity.PAGE_STEP:
                fragment = stepFragment;
                break;
            case MainActivity.PAGE_NOTE:
                fragment = noteFragment;
                break;
            case MainActivity.PAGE_MORE:
                fragment = moreFragment;
                break;
        }
        return fragment;
    }
}
