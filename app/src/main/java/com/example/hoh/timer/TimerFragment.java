package com.example.hoh.timer;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoh.R;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("ValidFragment")
public class TimerFragment extends Fragment {
    private MyCount timer;
    private TextView textView;
    private Button btn_start;
    private Button btn_stop;
    private ImageButton btn_add_min;
    private ImageButton btn_add_second;
    private ImageButton btn_sub_min;
    private ImageButton btn_sub_second;
    private long time;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer,container,false);
        textView = (TextView)view.findViewById(R.id.timer);
        btn_start = (Button) view.findViewById(R.id.btn_start);
        btn_stop = (Button) view.findViewById(R.id.btn_stop);
        btn_add_min = (ImageButton) view.findViewById(R.id.btn_add_min);
        btn_add_second = (ImageButton) view.findViewById(R.id.btn_add_second);
        btn_sub_min = (ImageButton) view.findViewById(R.id.btn_sub_min);
        btn_sub_second = (ImageButton) view.findViewById(R.id.btn_sub_second);
        time = 0;
        timer = null;
        //计时button
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer == null) {
                    timer = new MyCount(time, 1000);
                    timer.start();
                }
            }
        });

        //停止button
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timer == null) {
                    return;
                }
                timer.cancel();
                timer = null;
            }
        });

        //增加min button
        btn_add_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断timer是否在倒计时
                if (timer != null) {
                    return;
                }
                time += 60000;
                textView.setText(getTime(time));
            }
        });

        //增加second button
        btn_add_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null) {
                    return;
                }
                time += 1000;
                textView.setText(getTime(time));
            }
        });

        //减少min button
        btn_sub_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null) {
                    return;
                }
                if (time - 60000 >= 0) {
                    time -= 60000;
                    textView.setText(getTime(time));
                }

            }
        });

        //减少second button
        btn_sub_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null) {
                    return;
                }
                if (time - 1000 >= 0) {
                    time -= 1000;
                    textView.setText(getTime(time));
                }
            }
        });





        return view;
    }


    //倒计时的内部类
    class MyCount extends CountDownTimer{
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            textView.setText("请等待30秒(" + millisUntilFinished / 1000 + ")...");
//            Toast.makeText(getActivity(), millisUntilFinished / 1000 + "", Toast.LENGTH_LONG).show();//toast有显示时间延迟
            textView.setText(getTime(time));
            time -= 1000;

        }

        @Override
        public void onFinish() {
            textView.setText(getTime(time));
            timer = null;
        }

    }


    public String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("mm：ss");
        Date date = new Date(time);
        return format.format(date);
    }


}

