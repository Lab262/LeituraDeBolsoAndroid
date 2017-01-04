package lab262.leituradebolso.OnBoard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lab262.leituradebolso.R;

/**
 * Created by luisresende on 21/12/16.
 */

public class FragmentOnBoard extends Fragment {


    private View view;
    private int step;

    //Variables for view
    private ImageView stepImageView;
    private TextView titleStepTextView,descriptionTextView;

    public FragmentOnBoard() {
        // Required empty public constructor
    }

    public static FragmentOnBoard newInstance(int numberStep) {
        FragmentOnBoard fragment = new FragmentOnBoard();
        fragment.step = numberStep;
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
        view = inflater.inflate(R.layout.fragment_on_board, container, false);
        getInstanceViews();
        setupView();
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getInstanceViews(){
        stepImageView = (ImageView) view.findViewById(R.id.stepImageView);
        titleStepTextView = (TextView) view.findViewById(R.id.titleStepTextView);
        descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
    }

    private void setupView(){
        switch (this.step){
            case 0:
                stepImageView.setImageResource(R.drawable.onboard_step_one);
                break;
            case 1:
                stepImageView.setImageResource(R.drawable.onboard_step_two);
                break;
            case 2:
                stepImageView.setImageResource(R.drawable.onboard_step_three);
                break;

        }
        String [] arrayTitles = getResources().getStringArray(R.array.title_onboard);
        String [] arrayDescription = getResources().getStringArray(R.array.description_onboard);

        titleStepTextView.setText(arrayTitles[step]);
        descriptionTextView.setText(arrayDescription[step]);
    }
}
