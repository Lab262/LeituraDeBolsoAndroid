package lab262.leituradebolso.ReadingHistory;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Model.EmojiModel;
import lab262.leituradebolso.Model.ReadingModel;
import lab262.leituradebolso.Persistence.DBManager;
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
        arrayReadingModels = getReadingData();
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
        bundleExtras.putString("modelreading",readingModel.idReading);
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

    private ReadingModel[] getReadingData() {

        RealmResults<ReadingModel> realmResults = (RealmResults<ReadingModel>) DBManager.getAll(ReadingModel.class);

        ReadingModel[] dummyData = new ReadingModel[realmResults.size()];

        for (int i=0; i<realmResults.size(); i++){
            dummyData[i] = realmResults.get(i);
        }

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

    public void refreshListView(){
        adapter.notifyDataSetChanged();
    }
}
