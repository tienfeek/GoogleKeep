/**
 * 
 */
package it.gmariotti.android.examples.gui;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * TODO
 * @author wangtianfei01
 *
 */
public class DetailFragment extends Fragment {
    
    private View originalView;
    private ValueAnimator startScaleAnimator;
    private  ValueAnimator closeAnimator;
    
    private boolean animating = false;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.detail_view, null);
        rootView.setAlpha(0);
        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if(startScaleAnimator.isRunning()) return;
                closeSacle();
            }
        });
    }
    
    
    public boolean getAnimating(){
        return animating;
    }
    
    public void startScale(View originalView){
        if(getView()==null) return;
        
        if(closeAnimator != null) closeAnimator.cancel();
        
        this.animating = true;
        this.originalView = originalView;
        
        final Rect originalLocation = new Rect();
        final Rect destLocation = new Rect();
        originalView.getGlobalVisibleRect(originalLocation);
        final View rootView = getView();
        rootView.getGlobalVisibleRect(destLocation);
        
        final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rootView.getLayoutParams();
        layoutParams.topMargin = originalLocation.top - destLocation.top;
        layoutParams.leftMargin = originalLocation.left;
        layoutParams.rightMargin = originalLocation.right;
        layoutParams.bottomMargin = originalLocation.bottom;
        
        final int originalViewWidth = originalView.getWidth();
        final int originalViewHeight = originalView.getHeight();
        final int destViewWidth = rootView.getWidth();
        final int destViewHeight = rootView.getHeight();
        
        int bgRes = getArguments().getInt("bgRes", R.drawable.shadow_blue);
        rootView.setBackgroundResource(bgRes);
        rootView.requestLayout();
        
        
        startScaleAnimator = ValueAnimator.ofFloat(0,1);
        startScaleAnimator.setDuration(400);
        startScaleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        startScaleAnimator.start();
        startScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float)animation.getAnimatedValue();
                
                int alpha = (int) (200 * value);
                rootView.setAlpha(value);
                
                layoutParams.topMargin = (int) (originalLocation.top - ( originalLocation.top ) * value);
                layoutParams.leftMargin = (int) ((originalLocation.left - destLocation.left)  * (1.0f - value));
                layoutParams.width = (int) (originalViewWidth + (destViewWidth - originalViewWidth) * value);
                layoutParams.height = (int) (originalViewHeight + (destViewHeight - originalViewHeight) * value);
                rootView.requestLayout();
            }
        });
        startScaleAnimator.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animating = false;
            }
        });
        
    }
    
    
    public void closeSacle(){
        startScaleAnimator.cancel();
        
        final Rect originalLocation = new Rect();
        final Rect destLocation = new Rect();
        originalView.getGlobalVisibleRect(originalLocation);
        final View rootView = getView();
        rootView.getGlobalVisibleRect(destLocation);
        
        final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rootView.getLayoutParams();
        
        final int originalViewWidth = originalView.getWidth();
        final int originalViewHeight = originalView.getHeight();
        final int destViewWidth = rootView.getWidth();
        final int destViewHeight = rootView.getHeight();
        
        rootView.requestLayout();
        
        ValueAnimator closeAnimator = ValueAnimator.ofFloat(1,0);
        closeAnimator.setDuration(400);
        closeAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        closeAnimator.start();
        closeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float)animation.getAnimatedValue();
                
                rootView.setAlpha(value);
                
                layoutParams.topMargin = (int) (originalLocation.top - ( originalLocation.top ) * value);
                layoutParams.leftMargin = (int) ((originalLocation.left - destLocation.left)  * (1.0f - value));
                layoutParams.width = (int) (originalViewWidth + (destViewWidth - originalViewWidth) * value);
                layoutParams.height = (int) (originalViewHeight + (destViewHeight - originalViewHeight) * value);
                rootView.requestLayout();
            }
        });
        
        closeAnimator.addListener(new AnimatorListenerAdapter() {
            
            @Override
            public void onAnimationEnd(Animator animation) {
                KeepGuiActivity activity = (KeepGuiActivity)getActivity();
                activity.removeDetail();
                animating = false;
            }
            
            @Override
            public void onAnimationCancel(Animator animation) {
                KeepGuiActivity activity = (KeepGuiActivity)getActivity();
                activity.removeDetail();
                animating = false;
            }
        });
    }
}
