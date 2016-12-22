package com.v.demo.redPoint;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.v.demo.R;


public class RedPointActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_point);

        final FrameLayout containerView = (FrameLayout) findViewById(R.id.container);

        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedPointView redPointView = new RedPointView(RedPointActivity.this);
                redPointView.setLayoutParams(layoutParams);
                containerView.removeAllViews();
                containerView.addView(redPointView);
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedPointTextView redPointView = new RedPointTextView(RedPointActivity.this);
                redPointView.setLayoutParams(layoutParams);
                containerView.removeAllViews();
                containerView.addView(redPointView);
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedPointExploredView redPointView = new RedPointExploredView(RedPointActivity.this);
                redPointView.setLayoutParams(layoutParams);
                containerView.removeAllViews();
                containerView.addView(redPointView);
            }
        });
    }
}
