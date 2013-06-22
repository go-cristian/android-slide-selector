package com.iyubinest.widget;

import com.iyubinest.sliderselectorwidget.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ViewFlipper;

public class SlideSelect extends ViewFlipper implements OnTouchListener{

	private Animation slideLeftIn,slideRightIn,slideLeftOut,slideRightOut;
	private boolean state=false;
	private ImageView leftImage,rightImage;
	private final int animDuration = 80;
	private float lastX = 0;
	
	public SlideSelect(Context context) {
		super(context);
		init();
	}

	public SlideSelect(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		slideLeftIn = AnimationUtils.loadAnimation(getContext(), R.anim.in_left_to_right);
		slideRightIn = AnimationUtils.loadAnimation(getContext(), R.anim.in_right_to_left);
		slideLeftOut = AnimationUtils.loadAnimation(getContext(), R.anim.out_right_to_left);
		slideRightOut = AnimationUtils.loadAnimation(getContext(), R.anim.out_left_to_right);
		slideLeftIn.setDuration(animDuration);
		slideRightIn.setDuration(animDuration);
		slideLeftOut.setDuration(animDuration);
		slideRightOut.setDuration(animDuration);
		
		leftImage = new ImageView(getContext());
		rightImage = new ImageView(getContext());
		
		leftImage.setImageResource(R.drawable.off);
		rightImage.setImageResource(R.drawable.on);
		addView(leftImage);
		addView(rightImage);
		setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(final View v, final MotionEvent event) {
		int action = event.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			lastX = event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			if (lastX - event.getX() > 30) {
				if(state)
					changeState(false,true);
				return true;
			} else if (lastX - event.getX() < -30) {
				if(!state)
					changeState(true,true);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (lastX - event.getX() < 30 && lastX - event.getX() > -30) {
				if(!state)
					changeState(true,true);
				else if(state)
					changeState(false,true);
				return true;
			}
			break;
			
		}
		return true;
	}

	public void changeState(boolean state, boolean anim){
		if(anim){
			if(state){
				setInAnimation(slideLeftIn);
				setOutAnimation(slideRightOut);
				showNext();
			}else{
				setInAnimation(slideRightIn);
				setOutAnimation(slideLeftOut);
				showPrevious();
			}
		}
		this.state=state;
	}
	
}
