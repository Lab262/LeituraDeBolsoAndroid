package lab262.leituradebolso.ReadingHistory;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Model.ReadingModel;
import lab262.leituradebolso.Persistence.DBManager;
import lab262.leituradebolso.R;
import lab262.leituradebolso.ReadingDay.ReadingDayActivity;


public class ReadingHistoryListFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {

    public ListView readingListView;
    private View view;

    private OnFragmentInteractionListener mListener;

    public ReadingModel[] arrayReadingModels;
    private ReadingHistoryListAdapter adapter;

    public ReadingHistoryListFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ReadingHistoryListFragment(ReadingModel[] readingModels) {
        arrayReadingModels = readingModels;
    }


    public static ReadingHistoryListFragment newInstance(ReadingModel [] readingModels) {
        ReadingHistoryListFragment fragment = new ReadingHistoryListFragment(readingModels);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reading_history_list, container, false);
        getInstanceViews();
        loadReadingList();
        readingListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ReadingModel readingModel = (ReadingModel) adapter.getItem(i);
        Bundle bundleExtras = new Bundle();
        bundleExtras.putString(ReadingModel.keyModelSelected,readingModel.idReading);
        ActivityManager.changeActivity(getContext(),ReadingDayActivity.class,bundleExtras);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DBManager.getCachedUser().getNoturneMode()){
            setNoturneMode();
        }else {
            resetNoturneMode();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void getInstanceViews(){
        readingListView = (ListView) view.findViewById(R.id.readingListView);
    }

    private void loadReadingList() {
        adapter = new ReadingHistoryListAdapter(getContext().getApplicationContext(), arrayReadingModels);
        readingListView.setAdapter(adapter);
    }

    public void filterReadingList(String stringFilter){
        ArrayList<ReadingModel> arrayFiltered = new ArrayList<>();
        for (ReadingModel readingModel : arrayReadingModels){
            if (readingModel.title.toLowerCase().contains(stringFilter.toLowerCase())){
                arrayFiltered.add(readingModel);
            }
        }
        if (arrayFiltered.isEmpty()){
            adapter.updateData(new ReadingModel[0]);
        }else {
            ReadingModel[] arrayFilteredReadingModels = new ReadingModel[arrayFiltered.size()];
            arrayFilteredReadingModels = arrayFiltered.toArray(arrayFilteredReadingModels);
            adapter.updateData(arrayFilteredReadingModels);
        }
    }

    public void resetFilter(){
        adapter.updateData(arrayReadingModels);
    }

    public void refreshListView(Context context){
        adapter = new ReadingHistoryListAdapter(context, arrayReadingModels);
        readingListView.setAdapter(adapter);
    }

    private void setNoturneMode(){
        ColorDrawable grey_10;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            grey_10 = new ColorDrawable(this.getResources().getColor(R.color.grey_line_layout_10_opc,null));

        }else {
            grey_10 = new ColorDrawable(this.getResources().getColor(R.color.grey_line_layout_10_opc));
        }
        readingListView.setDivider(grey_10);
        readingListView.setDividerHeight(1);
    }

    private void resetNoturneMode(){
        ColorDrawable grey;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            grey = new ColorDrawable(this.getResources().getColor(R.color.grey_line_layout,null));

        }else {
            grey = new ColorDrawable(this.getResources().getColor(R.color.grey_line_layout));
        }
        readingListView.setDivider(grey);
        readingListView.setDividerHeight(1);
    }
}
