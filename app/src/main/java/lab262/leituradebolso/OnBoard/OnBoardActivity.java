package lab262.leituradebolso.OnBoard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import lab262.leituradebolso.R;

public class OnBoardActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager pager;
    private OnBoardViewPagerAdapter adapter;
    private int numberViews = 3;

    private Button skipButton;
    private ImageView arrowImageView, firstStepImageView, secondStepImageView, thirdStepImageView;
    private TextView doneTextView;
    private TransitionDrawable transitionSelect;
    private int TIME_ANIMATION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        getInstanceViews();
        setPropertyViews();
    }

    private void getInstanceViews(){
        skipButton = (Button) findViewById(R.id.skipButton);
        pager = (ViewPager) findViewById(R.id.pager);
        arrowImageView = (ImageView) findViewById(R.id.arrowImageView);
        firstStepImageView = (ImageView) findViewById(R.id.firstStepImageView);
        secondStepImageView = (ImageView) findViewById(R.id.secondStepImageView);
        thirdStepImageView = (ImageView) findViewById(R.id.thirdStepImageView);
        doneTextView = (TextView) findViewById(R.id.doneTextView);

        Drawable[] layersSelect = new Drawable[2];
        layersSelect[0] = secondStepImageView.getDrawable();
        layersSelect[1] = firstStepImageView.getDrawable();
        transitionSelect = new TransitionDrawable(layersSelect);

    }

    private void setPropertyViews(){
        skipButton.setOnClickListener(this);
        adapter =  new OnBoardViewPagerAdapter(getSupportFragmentManager(),numberViews);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);
    }

    private void crossfade(View viewToAppear, final View viewToDesapear) {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        viewToAppear.setAlpha(0f);
        viewToAppear.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        viewToAppear.animate()
                .alpha(1f)
                .setDuration(TIME_ANIMATION)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        viewToDesapear.animate()
                .alpha(0f)
                .setDuration(TIME_ANIMATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        viewToDesapear.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.skipButton:
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                doneTextView.setVisibility(View.GONE);
                arrowImageView.setAlpha(1f);
                arrowImageView.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    secondStepImageView.setImageDrawable(getResources().getDrawable(R.drawable.bar_unselected_onboard,null));
                    thirdStepImageView.setImageDrawable(getResources().getDrawable(R.drawable.bar_unselected_onboard,null));
                }else {
                    secondStepImageView.setImageDrawable(getResources().getDrawable(R.drawable.bar_unselected_onboard));
                    thirdStepImageView.setImageDrawable(getResources().getDrawable(R.drawable.bar_unselected_onboard));
                }
                firstStepImageView.setImageDrawable(transitionSelect);
                break;
            case 1:
                doneTextView.setVisibility(View.GONE);
                arrowImageView.setAlpha(1f);
                arrowImageView.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    firstStepImageView.setImageDrawable(getResources().getDrawable(R.drawable.bar_unselected_onboard,null));
                    thirdStepImageView.setImageDrawable(getResources().getDrawable(R.drawable.bar_unselected_onboard,null));
                }else {
                    firstStepImageView.setImageDrawable(getResources().getDrawable(R.drawable.bar_unselected_onboard));
                    thirdStepImageView.setImageDrawable(getResources().getDrawable(R.drawable.bar_unselected_onboard));
                }
                secondStepImageView.setImageDrawable(transitionSelect);
                break;
            case 2:
                crossfade(doneTextView,arrowImageView);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    firstStepImageView.setImageDrawable(getResources().getDrawable(R.drawable.bar_unselected_onboard,null));
                    secondStepImageView.setImageDrawable(getResources().getDrawable(R.drawable.bar_unselected_onboard,null));
                }else {
                    firstStepImageView.setImageDrawable(getResources().getDrawable(R.drawable.bar_unselected_onboard));
                    secondStepImageView.setImageDrawable(getResources().getDrawable(R.drawable.bar_unselected_onboard));
                }
                thirdStepImageView.setImageDrawable(transitionSelect);
                break;
        }
        transitionSelect.startTransition(TIME_ANIMATION);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
