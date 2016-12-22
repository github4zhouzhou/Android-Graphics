package com.v.demo.shadow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.v.demo.R;


/**
 * Created by v on 16/6/26.
 */
public class FirstActivity extends Activity implements View.OnClickListener {
    private ShadowLayerView mShadowLayerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mShadowLayerView = (ShadowLayerView)findViewById(R.id.shadowlayerview);
        findViewById(R.id.radius_btn).setOnClickListener(this);
        findViewById(R.id.dx_btn).setOnClickListener(this);
        findViewById(R.id.dy_btn).setOnClickListener(this);
        findViewById(R.id.clear_btn).setOnClickListener(this);
        findViewById(R.id.show_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.radius_btn:
                mShadowLayerView.changeRadius();
                break;
            case R.id.dx_btn:
                mShadowLayerView.changeDx();;
                break;
            case R.id.dy_btn:
                mShadowLayerView.changeDy();
                break;
            case R.id.clear_btn:
                mShadowLayerView.clearShadow();
                break;
            case R.id.show_btn:
                mShadowLayerView.showShadow();
                break;
        }
    }
}
