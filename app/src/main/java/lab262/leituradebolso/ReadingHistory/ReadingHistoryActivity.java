package lab262.leituradebolso.ReadingHistory;

import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Extensions.LayoutManager;
import lab262.leituradebolso.Model.ReadingModel;
import lab262.leituradebolso.Persistence.DBManager;
import lab262.leituradebolso.R;
import lab262.leituradebolso.ReadingDay.ReadingDayActivity;


import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.widget.SearchView;

public class ReadingHistoryActivity extends AppCompatActivity implements View.OnClickListener,
        ReadingHistorySelectorFragment.OnFragmentInteractionListener,
        ReadingHistoryListFragment.OnFragmentInteractionListener, SearchView.OnQueryTextListener{


    private Button rightButton, readingDayButton;
    private ImageView rightButtonImage, readingDayButtonImage;
    private SearchView searchView;

    ReadingHistoryListFragment fragmentAllListView, fragmentTannedListView, fragmentNotReadListView;
    ViewPagerAdapter viewPagerAdapter;
    android.support.v4.view.ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_history);
        configureActionBar();
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

        //Customize Action Bar
        TextView textView = (TextView) findViewById(R.id.titleActionBarTextView);
        textView.setTypeface(LayoutManager.sharedInstance().typefaceQuicksandBold);
        textView.setText(R.string.title_activity_history_reading);
        getSupportActionBar().setElevation(0);
    }

    private void getInstanceViews(){
        View fragment = findViewById(R.id.fragment);
        pager = (android.support.v4.view.ViewPager) fragment.findViewById(R.id.pager);
        viewPagerAdapter = (ViewPagerAdapter) pager.getAdapter();
        fragmentAllListView = (ReadingHistoryListFragment) viewPagerAdapter.getItem(0);
        fragmentTannedListView = (ReadingHistoryListFragment) viewPagerAdapter.getItem(1);
        fragmentNotReadListView = (ReadingHistoryListFragment) viewPagerAdapter.getItem(2);
    }

    private void setPropertyView(){

        readingDayButton = (Button) findViewById(R.id.leftButton);
        readingDayButton.setOnClickListener(this);
        readingDayButtonImage = (ImageView) findViewById(R.id.leftImageView);
        readingDayButtonImage.setBackgroundResource(R.drawable.ic_reading_day);

        rightButton = (Button) findViewById(R.id.rightButton);
        rightButtonImage = (ImageView) findViewById(R.id.rightImageView);

        rightButton.setVisibility(View.INVISIBLE);
        rightButtonImage.setVisibility(View.INVISIBLE);

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

    private void setNoturneMode(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pager.setBackgroundColor(getResources().getColor(R.color.colorNoturne,null));
        }else {
            pager.setBackgroundColor(getResources().getColor(R.color.colorNoturne));
        }
    }

    private void resetNoturneMode(){
        pager.setBackgroundColor(Color.WHITE);
    }

    private void refreshListsViews(){
        if (fragmentAllListView.readingListView!=null){
            fragmentAllListView.arrayReadingModels = ReadingModel.getAllReadingData();
            fragmentAllListView.refreshListView(this);
        }
        if (fragmentNotReadListView.readingListView!=null){
            fragmentNotReadListView.arrayReadingModels = ReadingModel.getNotReadReadingData();
            fragmentNotReadListView.refreshListView(this);
        }
        if (fragmentTannedListView.readingListView!=null){
            fragmentTannedListView.arrayReadingModels = ReadingModel.getTannedReadingData();
            fragmentTannedListView.refreshListView(this);
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

    @Override
    protected void onStart() {
        super.onStart();
        //Configure Noturne Mode
        refreshListsViews();
        if (DBManager.getCachedUser().getNoturneMode()){
            setNoturneMode();
        }else {
            resetNoturneMode();
        }
    }
}
