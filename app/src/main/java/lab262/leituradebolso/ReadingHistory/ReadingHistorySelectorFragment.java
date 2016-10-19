package lab262.leituradebolso.ReadingHistory;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.realm.RealmResults;
import lab262.leituradebolso.External.SlidingTabLayout;
import lab262.leituradebolso.Model.ReadingModel;
import lab262.leituradebolso.Persistence.DBManager;
import lab262.leituradebolso.R;


public class ReadingHistorySelectorFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    int Numboftabs =3;

    public View view;

    public ReadingHistorySelectorFragment() {
        // Required empty public constructor
    }

    public static ReadingHistorySelectorFragment newInstance(String param1, String param2) {
        ReadingHistorySelectorFragment fragment = new ReadingHistorySelectorFragment();
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
        view = inflater.inflate(R.layout.fragment_reading_history_selector, container, false);
        setPropertyViews();
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

    private void setPropertyViews(){
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        CharSequence Titles[]={getString(R.string.title_selector_all),getString(R.string.title_selector_tanned),
                getString(R.string.title_selector_unread)};

        ArrayList<ReadingModel []> arrayListReadingsModel = new ArrayList<>();

        arrayListReadingsModel.add(ReadingModel.getAllReadingData());
        arrayListReadingsModel.add(ReadingModel.getTannedReadingData());
        arrayListReadingsModel.add(ReadingModel.getNotReadReadingData());

        adapter =  new ViewPagerAdapter(getFragmentManager(),Titles,Numboftabs, arrayListReadingsModel);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return getResources().getColor(R.color.selector_color,null);
                }else {
                    return getResources().getColor(R.color.selector_color);
                }
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
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


}
