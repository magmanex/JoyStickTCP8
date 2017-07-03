package com.example.ashira.joysticktcp8;

import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by ashira on 3/7/2560.
 */

public class Thread_TCP implements Runnable {
    main_variable main_var;
    Socket clientsocket = null;
    PrintWriter outToServer = null;

    String s_mode ;
    String s_high  ;
    String s_roll ;
    String s_pitch;
    String s_yaw   ;
    String s_angle ;
    String s_sum   ;


    @Override
    public void run() {
        try{
            clientsocket = new Socket(main_var.SERVER_IP,main_var.PORT);
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }

        while (true)
        {
            Socket test1 = clientsocket;
            s_mode  = convert_zero3(main_var.check_mode);
            s_high  = convert_zero5(main_var.check_high);
            s_roll  = convert_zero3(main_var.check_roll) ;
            s_pitch = convert_zero3(main_var.check_pitch);
            s_yaw   = convert_zero3(main_var.check_yaw);
            s_angle = convert_zero3(main_var.check_angle);
            s_sum   = convert_zero3(main_var.check_sum);

            try {
                outToServer = new PrintWriter(test1.getOutputStream(),true);
                outToServer.println ("test sentence na krub") ; //ประโยคที่ทำการส่งให้ server
                Thread.sleep(1500);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }



    public void connect()
    {

        try{
            clientsocket = new Socket(main_var.SERVER_IP,main_var.PORT);
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e) {
        // TODO Auto-generated catch block

        e.printStackTrace();
        }

    }

    public String convert_zero3(int x)
    {
        String result="";
        if( x< 10 ) result="00" + String.valueOf(x);
        else if ( x < 100 ) result = "0" + String.valueOf(x);
        else result = String.valueOf(x);
        return result;
    }

    public String convert_zero5(int x)
    {
        String result="";
        if( x< 10 )           result = "0000" + String.valueOf(x);
        else if ( x < 100 )   result = "000" + String.valueOf(x);
        else if ( x < 1000 )  result = "00" + String.valueOf(x);
        else if ( x < 10000 ) result = "0" + String.valueOf(x);
        else                  result = String.valueOf(x);
        return result;
    }


    //For check sum
    public void checksum()
    {
        main_var.check_sum = checksum_mode() + checksum_roll() + checksum_pitch()
                           + checksum_high() +  checksum_yaw() + checksum_angle();
    }

    public int checksum_mode()
    {
        int sum=0;
        for (int i = 0;i<s_mode.length();i++)
        {
            sum += Character.getNumericValue(s_mode.charAt(i));
        }
        return sum;
    }

    public int checksum_high()
    {
        int sum=0;
        for (int i = 0;i<s_high.length();i++)
        {
            sum += Character.getNumericValue(s_high.charAt(i));
        }
        return sum;
    }
    public int checksum_roll()
    {
        int sum=0;
        for (int i = 0;i<s_roll.length();i++)
        {
            sum += Character.getNumericValue(s_roll.charAt(i));
        }
        return sum;
    }

    public int checksum_pitch()
    {
        int sum=0;
        for (int i = 0;i<s_pitch.length();i++)
        {
            sum += Character.getNumericValue(s_pitch.charAt(i));
        }
        return sum;
    }

    public int checksum_yaw()
    {
        int sum=0;
        for (int i = 0;i<s_yaw.length();i++)
        {
            sum += Character.getNumericValue(s_yaw.charAt(i));
        }
        return sum;
    }

    public int checksum_angle()
    {
        int sum=0;
        for (int i = 0;i<s_angle.length();i++)
        {
            sum += Character.getNumericValue(s_angle.charAt(i));
        }
        return sum;
    }
}
