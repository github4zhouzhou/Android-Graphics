package com.v.demo.shadow;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.v.demo.R;


/**
 * Created by v on 16/6/24.
 */
public class SecondActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView tv = (TextView)findViewById(R.id.tv);
        tv.setShadowLayer(2,5,5, Color.GREEN);

    }
}
