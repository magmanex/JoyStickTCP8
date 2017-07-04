package com.example.ashira.joysticktcp8;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by ashira on 3/7/2560.
 */

public class SecondScreenActivity  extends AppCompatActivity implements SensorEventListener {

    //Global Variable
    main_variable main_var;

    //JoyStick
    RelativeLayout layout_joystick;
    com.example.ashira.joysticktcp8.JoyStickClass js;

    //compass
    private ImageView image;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;

    //Seekbar
    SeekBar seek_bar;
    TextView text_view;

    //Yaw
    TextView textView_Yaw;
    SeekBar seek_yaw;

    //Takeoff/on
    boolean State_power=false;
    ToggleButton toggle_state;
    TextView textView_state;

    ////////////////////////////////////////////////////////////////////////////////////
    //Can move out if done
    TextView textView1, textView2, textView3, textView4, textView5,tvHeading;
    ////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondscreen);

        main_var.check_roll  = 100;
        main_var.check_pitch = 100;
        main_var.check_yaw   = 100;
////////////////////////////////////////////////////////////////////////////////////
        //Textview Jotstick Detail
        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        textView4 = (TextView)findViewById(R.id.textView4);
        textView5 = (TextView)findViewById(R.id.textView5);
        textView_Yaw = (TextView)findViewById(R.id.tvYaw);


        image = (ImageView) findViewById(R.id.imageViewCompass);
        //image.setVisibility(View.INVISIBLE);
        tvHeading = (TextView) findViewById(R.id.tvCompass);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
////////////////////////////////////////////////////////////////////////////////////
        controller_Joystick();
        controller_yaw();
        seebbarr( );
        Power_Takeoff();
        mode();
    }

    private void controller_yaw() {

        seek_yaw = (SeekBar)findViewById(R.id.seekyaw);


        seek_yaw.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override

            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                main_var.check_yaw = i;
                textView_Yaw.setText("Yaw : " + String.valueOf(main_var.check_yaw));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seek_yaw.setProgress(100);
                main_var.check_yaw = 100;

            }
        });

    }

    private void mode() {
        final RadioButton rad_frame = (RadioButton)findViewById(R.id.frame);
        final RadioButton rad_earth = (RadioButton)findViewById(R.id.earth);
        RadioButton rad_con = (RadioButton)findViewById(R.id.control);

        RadioButton.OnClickListener myOptionOnClickListener = new RadioButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rad_frame.isChecked()) {
                    main_var.check_mode = 7;

                }
                else if(rad_earth.isChecked()){
                    main_var.check_mode = 11;
                }
                else {
                    main_var.check_mode = 15;
                }
            }
        };

        rad_frame.setOnClickListener(myOptionOnClickListener);
        rad_earth.setOnClickListener(myOptionOnClickListener);
        rad_con.setOnClickListener(myOptionOnClickListener);
    }


    private void controller_Joystick() {
        //joystick
        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);
        js = new com.example.ashira.joysticktcp8.JoyStickClass(getApplicationContext(), layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);



        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    main_var.check_roll = js.getX();
                    main_var.check_roll += 100;
                    if (main_var.check_roll < 0 ) main_var.check_roll= 0;
                    if (main_var.check_roll > 200 ) main_var.check_roll = 200;
                    textView1.setText("X : " + String.valueOf(main_var.check_pitch));
                    //s_roll = convert_zero3(x_joy);


                    main_var.check_pitch = js.getY();
                    main_var.check_pitch += 100;
                    if (main_var.check_pitch < 0 ) main_var.check_pitch = 0;
                    if (main_var.check_pitch > 200 ) main_var.check_pitch = 200;
                    textView2.setText("Y : " + String.valueOf( main_var.check_pitch));
                    //s_pitch = convert_zero3(y_joy);
                    textView3.setText("Angle : " + String.valueOf(js.getAngle()));
                    textView4.setText("Distance : " + String.valueOf(js.getDistance()));

                    int direction = js.get8Direction();
                    if(direction == com.example.ashira.joysticktcp8.JoyStickClass.STICK_UP) {
                        textView5.setText("Direction : Up");
                    } else if(direction == com.example.ashira.joysticktcp8.JoyStickClass.STICK_UPRIGHT) {
                        textView5.setText("Direction : Up Right");
                    } else if(direction == com.example.ashira.joysticktcp8.JoyStickClass.STICK_RIGHT) {
                        textView5.setText("Direction : Right");
                    } else if(direction == com.example.ashira.joysticktcp8.JoyStickClass.STICK_DOWNRIGHT) {
                        textView5.setText("Direction : Down Right");
                    } else if(direction == com.example.ashira.joysticktcp8.JoyStickClass.STICK_DOWN) {
                        textView5.setText("Direction : Down");
                    } else if(direction == com.example.ashira.joysticktcp8.JoyStickClass.STICK_DOWNLEFT) {
                        textView5.setText("Direction : Down Left");
                    } else if(direction == com.example.ashira.joysticktcp8.JoyStickClass.STICK_LEFT) {
                        textView5.setText("Direction : Left");
                    } else if(direction == com.example.ashira.joysticktcp8.JoyStickClass.STICK_UPLEFT) {
                        textView5.setText("Direction : Up Left");
                    } else if(direction == com.example.ashira.joysticktcp8.JoyStickClass.STICK_NONE) {
                        textView5.setText("Direction : Center");
                    }
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    textView1.setText("X :");
                    textView2.setText("Y :");
                    textView3.setText("Angle :");
                    textView4.setText("Distance :");
                    textView5.setText("Direction :");

                    main_var.check_roll=100;
                    main_var.check_pitch=100;
                }

                return true;
            }
        });



    }


    private void seebbarr() {
        seek_bar = (SeekBar)findViewById(R.id.seekBar);
        text_view =(TextView)findViewById(R.id.textView);
        text_view.setText("Covered : " + seek_bar.getProgress() + " / " +seek_bar.getMax());

        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    int progress_value;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        main_var.check_high = progress_value;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        text_view.setText("Covered : " + progress_value + " / " +seek_bar.getMax());
                    }
                }
        );
    }

    private void Power_Takeoff() {

        final Context context = this;

        toggle_state = (ToggleButton)findViewById(R.id.toggleButton_Takeoff);
        toggle_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    State_power = true;
                    main_var.check_mode = 3;
                    main_var.check_high = 1000;
                } else {
                    State_power = false;
                    main_var.check_mode = 1;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent nextScreen =  new Intent(context , MainActivity.class);

                    startActivity(nextScreen);

                }
            }

        /*if(State_power)
        {
            msg = "Take off";
            textView_state.setText(msg);
        }
        else
        {
            msg = "Landing";
            textView_state.setText(msg);
        }
        */
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause() {
        super.onPause();



        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // get the angle around the z-axis rotated
        float degree = Math.round(sensorEvent.values[0]) ;
        main_var.check_angle = Math.round(degree);



        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees" + "\n" );

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree-90;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
