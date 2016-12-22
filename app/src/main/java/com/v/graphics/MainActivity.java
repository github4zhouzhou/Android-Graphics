package com.v.graphics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.v.graphics.shadowDemo.SecondActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FrameLayout root = (FrameLayout)findViewById(R.id.activity_main);
//        root.addView(new RegionView(MainActivity.this));

        Button btn = (Button) findViewById(R.id.btn_new_activity);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        //QuadView qView = (QuadView) findViewById(R.id.id_quad);
        //qView.setVisibility(View.VISIBLE);
        //qView.startAnim();

        //PaintBasicView pbView = (PaintBasicView) findViewById(R.id.pbv);
        //pbView.setVisibility(View.VISIBLE);
        //pbView.startAnim();
    }
}
