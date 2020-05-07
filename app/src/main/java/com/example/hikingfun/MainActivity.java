package com.example.hikingfun;

import com.example.hikingfun.MoreFragment.CallBackDarkMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements CallBackDarkMode {
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mPagerAdapter;

    private RadioGroup mRadioGroup;
    private RadioButton mRbGps;
    private RadioButton mRbStep;
    private RadioButton mRbNote;
    private RadioButton mRbMore;

    public static final int PAGE_GPS = 0;
    public static final int PAGE_STEP = 1;
    public static final int PAGE_NOTE = 2;
    public static final int PAGE_MORE = 3;
    private static boolean DARK_MODE = false;

    @Override
    public void SendDarkStatus(boolean darkOrLight) {
        DARK_MODE = darkOrLight;
        recreate();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        if (getDarkModeStatus(this) || DARK_MODE) {
            setTheme(R.style.main_theme_dark);
        }else {
            setTheme(R.style.main_theme_light);
        }

        setContentView(R.layout.activity_main);

        initView();

    }
    private static boolean getDarkModeStatus(Context context) {
        int mode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return mode == Configuration.UI_MODE_NIGHT_YES;
    }

    private void initView() {
        // radioGroup
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mRbGps = (RadioButton) findViewById(R.id.rb_gps);
        mRbStep = (RadioButton) findViewById(R.id.rb_step);
        mRbNote = (RadioButton) findViewById(R.id.rb_note);
        mRbMore = (RadioButton) findViewById(R.id.rb_more);
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_gps:
                        mViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_step:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_note:
                        mViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.rb_more:
                        mViewPager.setCurrentItem(3, false);
                        break;
                }
            }
        });

        // ViewPager
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case PAGE_GPS:
                        mRadioGroup.check(R.id.rb_gps);
                        break;
                    case PAGE_STEP:
                        mRadioGroup.check(R.id.rb_step);
                        break;
                    case PAGE_NOTE:
                        mRadioGroup.check(R.id.rb_note);
                        break;
                    case PAGE_MORE:
                        mRadioGroup.check(R.id.rb_more);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == 2) {
                    switch (mViewPager.getCurrentItem()) {
                        case PAGE_GPS:
                            mRadioGroup.check(R.id.rb_gps);
                            break;
                        case PAGE_STEP:
                            mRadioGroup.check(R.id.rb_step);
                            break;
                        case PAGE_NOTE:
                            mRadioGroup.check(R.id.rb_note);
                            break;
                        case PAGE_MORE:
                            mRadioGroup.check(R.id.rb_more);
                            break;
                    }
                }
            }
        });


    }



}
