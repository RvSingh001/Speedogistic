package com.e.speedogistic.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.e.speedogistic.R;
import com.e.speedogistic.ui.bookings.BookingFrag;
import com.e.speedogistic.ui.feedback.FeedBackFragment;
import com.e.speedogistic.ui.transProfile.TransProfileFrag;
import com.e.speedogistic.ui.userfeedback.UserFeedback;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment=null;
      switch (position){
          case 0:
              fragment=new TransProfileFrag();
              break;
          case 1:
              fragment=new BookingFrag();
              break;
          case 2:
              fragment=new UserFeedback();
              break;

      }
      return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}