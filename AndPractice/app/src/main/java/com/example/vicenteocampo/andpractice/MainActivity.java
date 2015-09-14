package com.example.vicenteocampo.andpractice;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
//Vicente Ocampo
//Udacity Project
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // xml contains fragment that contains grid
        setContentView(R.layout.activity_main);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}

