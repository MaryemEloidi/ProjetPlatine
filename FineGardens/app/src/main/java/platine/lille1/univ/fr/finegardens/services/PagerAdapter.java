package platine.lille1.univ.fr.finegardens.services;

/**
 * Created by cactus on 11/03/2018.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import platine.lille1.univ.fr.finegardens.fragments.ListeFavorisFragment;
import platine.lille1.univ.fr.finegardens.fragments.MapFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {


        return mFragmentList.get(position);

    }

    @Override
    public int getCount() {
                 return mFragmentList.size();

    }
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}