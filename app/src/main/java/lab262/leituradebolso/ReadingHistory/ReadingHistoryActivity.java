package lab262.leituradebolso.ReadingHistory;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.R;
import lab262.leituradebolso.ReadingDay.ReadingDayActivity;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import android.support.v7.widget.SearchView;

public class ReadingHistoryActivity extends AppCompatActivity implements View.OnClickListener,
        ReadingHistorySelectorFragment.OnFragmentInteractionListener,
        ReadingHistoryListFragment.OnFragmentInteractionListener, SearchView.OnQueryTextListener{


    private ImageButton rightButton, readingDayButton;
    private Typeface typeface;
    private SearchView searchView;

    ReadingHistoryListFragment fragmentAllListView, fragmentTannedListView, fragmentNotReadListView;
    ViewPagerAdapter viewPagerAdapter;
    android.support.v4.view.ViewPager pager;


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

        View fragment = findViewById(R.id.fragment);
        pager = (android.support.v4.view.ViewPager) fragment.findViewById(R.id.pager);
        viewPagerAdapter = (ViewPagerAdapter) pager.getAdapter();
        fragmentAllListView = (ReadingHistoryListFragment) viewPagerAdapter.getItem(0);
        fragmentTannedListView = (ReadingHistoryListFragment) viewPagerAdapter.getItem(1);
        fragmentNotReadListView = (ReadingHistoryListFragment) viewPagerAdapter.getItem(2);
    }

    private void setPropertyView(){

        //Customize Action Bar
        TextView textView = (TextView) findViewById(R.id.titleActionBarTextView);
        textView.setTypeface(typeface);
        textView.setText(R.string.title_activity_history_reading);
        getSupportActionBar().setElevation(0);

        readingDayButton = (ImageButton) findViewById(R.id.leftButton);
        readingDayButton.setBackgroundResource(R.drawable.ic_reading_day);
        readingDayButton.setOnClickListener(this);

        rightButton = (ImageButton) findViewById(R.id.rightButton);
        rightButton.setVisibility(View.INVISIBLE);

    }

    private void customizeSearch(){

        int closeButtonId = android.support.v7.appcompat.R.id.search_close_btn;
        ImageView closeButtonImage = (ImageView) searchView.findViewById(closeButtonId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            closeButtonImage.setColorFilter(getResources().getColor(R.color.colorAccent,null));
        }else {
            closeButtonImage.setColorFilter(getResources().getColor(R.color.colorAccent));
        }

        int searchEditId = android.support.v7.appcompat.R.id.search_src_text;
        EditText editText = (EditText) searchView.findViewById(searchEditId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            editText.setTextColor(getResources().getColor(R.color.colorAccent,null));
        }else {
            editText.setTextColor(getResources().getColor(R.color.colorAccent));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            editText.setHintTextColor(getResources().getColor(R.color.colorAccent,null));
        }else {
            editText.setHintTextColor(getResources().getColor(R.color.colorAccent));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //Reading Day button
            case R.id.leftButton:
                ActivityManager.changeActivityAndRemoveParentActivity(ReadingHistoryActivity.this, ReadingDayActivity.class);
                break;

        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        customizeSearch();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                resetFilter();
                break;
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterListView(newText);
        return true;
    }

    private void filterListView(String textToFilter){
        switch (pager.getCurrentItem()){
            case 0:
                fragmentAllListView.filterReadingList(textToFilter);
                break;
            case 1:
                fragmentTannedListView.filterReadingList(textToFilter);
                break;
            case 2:
                fragmentNotReadListView.filterReadingList(textToFilter);
                break;
        }
    }

    private void resetFilter(){
        fragmentAllListView.resetFilter();
        fragmentTannedListView.resetFilter();
        fragmentNotReadListView.resetFilter();
    }
}
