package lab262.leituradebolso.OnBoard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import lab262.leituradebolso.ReadingHistory.ReadingHistoryListFragment;

/**
 * Created by luisresende on 21/12/16.
 */

public class OnBoardViewPagerAdapter extends FragmentStatePagerAdapter {

    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    FragmentOnBoard tab1, tab2, tab3;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public OnBoardViewPagerAdapter(FragmentManager fm, int mNumbOfTabsumb) {
        super(fm);

        this.NumbOfTabs = mNumbOfTabsumb;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            if (tab1==null){
                tab1 = FragmentOnBoard.newInstance(0);
            }
            return tab1;
        }
        else if (position==1)         // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            if (tab2==null){
                tab2 = FragmentOnBoard.newInstance(1);
            }
            return tab2;

        }else {

            if (tab3==null){
                tab3 = FragmentOnBoard.newInstance(2);
            }
            return tab3;

        }


    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

}