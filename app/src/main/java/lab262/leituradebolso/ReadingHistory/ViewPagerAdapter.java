package lab262.leituradebolso.ReadingHistory;

/**
 * Created by luisresende on 13/09/16.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import lab262.leituradebolso.Model.ReadingModel;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    ReadingHistoryListFragment tab1, tab2, tab3;
    ReadingModel [] allReadingModels, tannedReadingModels, notReadReadingModels;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb, ArrayList<ReadingModel[]> readingModels) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        allReadingModels = readingModels.get(0);
        tannedReadingModels = readingModels.get(1);
        notReadReadingModels = readingModels.get(2);
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            if (tab1==null){
                tab1 = ReadingHistoryListFragment.newInstance(allReadingModels);
            }
            return tab1;
        }
        else if (position==1)         // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            if (tab2==null){
                tab2 = ReadingHistoryListFragment.newInstance(tannedReadingModels);
            }
            return tab2;

        }else {

            if (tab3==null){
                tab3 = ReadingHistoryListFragment.newInstance(notReadReadingModels);
            }
            return tab3;

        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

}