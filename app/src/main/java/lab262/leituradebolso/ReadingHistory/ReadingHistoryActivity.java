package lab262.leituradebolso.ReadingHistory;

import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.External.SlidingTabLayout;
import lab262.leituradebolso.R;
import lab262.leituradebolso.ReadingDay.ReadingDayActivity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ReadingHistoryActivity extends AppCompatActivity implements View.OnClickListener, ReadingHistoryListFragment.OnFragmentInteractionListener {

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    int Numboftabs =3;

    private Typeface typeface;
    private ImageButton searchButton, readingDayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureActionBar();
        setContentView(R.layout.activity_reading_history);
        getInstanceViews();
        setPropertyView();
    }

    private void configureActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled (false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
    }

    private void getInstanceViews(){
        typeface =Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");
    }

    private void setPropertyView(){
        TextView textView = (TextView) findViewById(R.id.titleActionBarTextView);
        textView.setTypeface(typeface);
        textView.setText(R.string.title_activity_history_reading);

        getSupportActionBar().setElevation(0);

        readingDayButton = (ImageButton) findViewById(R.id.leftButton);
        searchButton = (ImageButton) findViewById(R.id.rightButton);

        readingDayButton.setBackgroundResource(R.drawable.ic_reading_day);
        searchButton.setBackgroundResource(R.drawable.ic_search);

        readingDayButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        CharSequence Titles[]={getString(R.string.title_selector_all),getString(R.string.title_selector_tanned),
                getString(R.string.title_selector_unread)};

        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.selector_color);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            //Reading Day button
            case R.id.leftButton:
                ActivityManager.changeActivity(ReadingHistoryActivity.this, ReadingDayActivity.class);
                break;
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
