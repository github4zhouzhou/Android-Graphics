package com.v.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.v.demo.redPoint.RedPointActivity;
import com.v.demo.shadow.ShadowActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRedPoint = (Button) findViewById(R.id.btn_red_point);
        btnRedPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RedPointActivity.class));
            }
        });

        Button btnShadow = (Button) findViewById(R.id.btn_shadow);
        btnShadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShadowActivity.class));
            }
        });
    }
}
