package com.v.graphics.porterDuff;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.v.graphics.R;

public class PorterDuffActivity extends Activity implements View.OnClickListener{

    private Button btnMyView,btnRoundImg,btnInvertImg,btnTxtWave,btnIrregularWave,btnHeartmap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porter_duff);
        findViewById(R.id.myviewbtn).setOnClickListener(this);
        findViewById(R.id.rounddstinbtn).setOnClickListener(this);
        findViewById(R.id.invertdstinbtn).setOnClickListener(this);
        findViewById(R.id.textwavebtn).setOnClickListener(this);
        findViewById(R.id.irregularwavebtn).setOnClickListener(this);
        findViewById(R.id.heartbitbtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideAllViews();
        switch (v.getId()){
            case R.id.myviewbtn:
                findViewById(R.id.myview).setVisibility(View.VISIBLE);
                break;
            case R.id.rounddstinbtn:
                findViewById(R.id.roundimage).setVisibility(View.VISIBLE);
                break;
            case R.id.invertdstinbtn:
                findViewById(R.id.invertimg).setVisibility(View.VISIBLE);
                break;
            case R.id.textwavebtn:
                findViewById(R.id.textwaveview).setVisibility(View.VISIBLE);
                break;
            case R.id.irregularwavebtn:
                findViewById(R.id.irregularwaveview).setVisibility(View.VISIBLE);
                break;
            case R.id.heartbitbtn:
                findViewById(R.id.heartbitview).setVisibility(View.VISIBLE);
                break;
        }
    }

    private void hideAllViews(){
        findViewById(R.id.myview).setVisibility(View.GONE);
        findViewById(R.id.roundimage).setVisibility(View.GONE);
        findViewById(R.id.invertimg).setVisibility(View.GONE);
        findViewById(R.id.textwaveview).setVisibility(View.GONE);
        findViewById(R.id.irregularwaveview).setVisibility(View.GONE);
        findViewById(R.id.heartbitview).setVisibility(View.GONE);
    }
}
