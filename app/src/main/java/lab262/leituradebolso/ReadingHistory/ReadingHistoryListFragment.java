package lab262.leituradebolso.ReadingHistory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Model.ReadingModel;
import lab262.leituradebolso.R;
import lab262.leituradebolso.ReadingDay.ReadingDayActivity;

/**
 * Activities that contain this fragment must implement the
 * {@link ReadingHistoryListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReadingHistoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadingHistoryListFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListView readingListView;
    private View view;

    private OnFragmentInteractionListener mListener;

    private ReadingModel[] arrayReadingModels;
    private ReadingHistoryListAdapter adapter;

    public ReadingHistoryListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ReadingHistoryListFragment newInstance(String param1, String param2) {
        ReadingHistoryListFragment fragment = new ReadingHistoryListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reading_history_list, container, false);
        getInstanceViews();
        arrayReadingModels = getDummyData();
        loadReadingList(arrayReadingModels);
        readingListView.setOnItemClickListener(this);
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        ReadingModel readingModel = arrayReadingModels[i];
        Bundle bundleExtras = new Bundle();
        bundleExtras.putParcelable("modelreading",readingModel);
        ActivityManager.changeActivity(getContext(),ReadingDayActivity.class,bundleExtras);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getInstanceViews(){
        readingListView = (ListView) view.findViewById(R.id.readingListView);
    }

    private void loadReadingList(ReadingModel[] servicesRequested) {
        adapter = new ReadingHistoryListAdapter(getContext().getApplicationContext(), servicesRequested);
        readingListView.setAdapter(adapter);
    }

    private ReadingModel[] getDummyData() {

        ArrayList<String> emojis = new ArrayList<String>();
        emojis.add(ReadingModel.getEmijoByUnicode(0x1F601));
        emojis.add(ReadingModel.getEmijoByUnicode(0x1F602));
        emojis.add(ReadingModel.getEmijoByUnicode(0x1F603));

        ReadingModel reading1 = new ReadingModel("1","Rocky Montains", "Leandro OG", "5 min", "bla bla bla bla",
                emojis, false, true);
        ReadingModel reading2 = new ReadingModel("1","Amei te ver", "Fernanda Vesquez", "5 min", getString(R.string.placeholder_reading),
                emojis, true, false);

        ReadingModel[] dummyData = new ReadingModel[2];

        dummyData[0] = reading1;
        dummyData[1] = reading2;

        return dummyData;

    }

    public void filterReadingList(String stringFilter){
        ArrayList<ReadingModel> arrayFiltered = new ArrayList<>();
        for (ReadingModel readingModel : arrayReadingModels){
            if (readingModel.title.toLowerCase().contains(stringFilter.toLowerCase())){
                arrayFiltered.add(readingModel);
            }
        }
        if (!arrayFiltered.isEmpty()){
            ReadingModel[] arrayFilteredReadingModels = new ReadingModel[arrayFiltered.size()];
            arrayFilteredReadingModels = arrayFiltered.toArray(arrayFilteredReadingModels);
            adapter.updateData(arrayFilteredReadingModels);
        }
    }

    public void resetFilter(){
        adapter.updateData(arrayReadingModels);
    }
}
