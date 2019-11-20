package com.fct.miei.ipm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.brutal.ninjas.hackaton19.R;

public class HelpActivity extends AppCompatActivity {

    public HelpActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        String image = extras.getString("image");

        ImageView img = findViewById(R.id.helpImage);

        if(image.equalsIgnoreCase("cadeiras")){
            img.setImageResource(R.drawable.cadeiras);
        }
        else{
            img.setImageResource(R.drawable.ranking_help);
        }

        System.out.println(image);

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();

        return true;
    }
}
