package com.meng.demo.toolbardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "麻辣隔壁";
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        case2();
    }

    private void case2() {
        int count = 59;
        Observable.interval(0,1, TimeUnit.SECONDS)
                .take(count+2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"onError");
                    }

                    @Override
                    public void onNext(Long o) {

                        Log.e(TAG,"onNext"+o);
                    }
                });
    }
}
