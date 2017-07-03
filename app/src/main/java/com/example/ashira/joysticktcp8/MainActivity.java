package com.example.ashira.joysticktcp8;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    main_variable main_var;
    Thread thread_tcp = new Thread(new Thread_TCP());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonconnect    = (Button) findViewById(R.id.buttonCon);
        Button buttonnext       = (Button) findViewById(R.id.buttonNext);
        final EditText edittext = (EditText)findViewById(R.id.editText2);
        final Context context = this;

        //Next button option
        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nextScreen =  new Intent(context , SecondScreenActivity.class);

                // starting new activity
                startActivity(nextScreen);

            }
        });

        //Connect Button option
        buttonconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_var.SERVER_IP = edittext.getText().toString();
                thread_tcp.start();
            }
        });
    }
}
