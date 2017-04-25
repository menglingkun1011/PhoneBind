package com.meng.demo.toolbardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;


public class ObserverActivity extends AppCompatActivity {

    private static final String TAG = "ObserverActivity";

    String[] strs = {"12","23","ew"};
    private Button btn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);
//        case1();
//        case2();
//        case3();

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                case1();
            }
        });
        tv = (TextView) findViewById(R.id.tv);

    }

    private void case1() {
        //被观察者
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {//要做的事情
                //subscriber 订阅者
                subscriber.onNext("hello");
                subscriber.onNext("Rx java");

                subscriber.onCompleted();
            }
        });

        //观察者

        Observer<String> observer = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: "+s);
                tv.setText(s);
            }
        };

        //订阅者 让观察者和被观察者联系起来
        observable.subscribe(observer);
    }

    private void case2() {
        //被观察者
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {//要做的事情
                //subscriber 订阅者
                subscriber.onNext("hello");
                subscriber.onNext("Rx java");

                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: "+s);
                Log.e(TAG, "onNext: "+s);
                System.out.print("ss"+s);
            }
        });
    }

    private void case3(){

        Observable.from(strs)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "onNext: "+s);
                    }
                });
    }
}
