package com.meng.demo.toolbardemo;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class TextInputLayoutActivity extends AppCompatActivity {

    private TextInputLayout tilPhone;
    private TextInputLayout tilCode;
    private TextInputEditText etPhone;
    private TextInputEditText etCode;


    /**
     * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700
     **/
    public static final String CHINA_TELECOM_PATTERN = "(^1(33|53|77|8[019])\\d{8}$)|(^1700\\d{7}$)";

    /**
     * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1709
     **/
    public static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";

    /**
     * 中国移动号码格式验证
     * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184
     * ,187,188,147,178,1705
     **/
    public static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";
    private TextView sendCodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input_layout);

        initView();
    }

    private void initView() {
        tilPhone = (TextInputLayout) findViewById(R.id.til_phone);
        tilCode = (TextInputLayout) findViewById(R.id.til_code);
        etPhone = (TextInputEditText) findViewById(R.id.et_phone);
        etCode = (TextInputEditText) findViewById(R.id.et_code);
        sendCodeBtn = (TextView) findViewById(R.id.tv_send_code);
        
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                judgePhone(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etPhone.getText().toString())){
                    Toast.makeText(TextInputLayoutActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(TextInputLayoutActivity.this, "发送验证码成功", Toast.LENGTH_SHORT).show();

                final int count = 10;
                Observable.interval(0,1, TimeUnit.SECONDS)
                        .take(count+2)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onCompleted() {
                                sendCodeBtn.setEnabled(true);
                                sendCodeBtn.setText("获取验证码");
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onNext(Long o) {
                                sendCodeBtn.setEnabled(false);
                                sendCodeBtn.setText("重新发送"+(count-o)+"s");
                            }
                        });
            }
        });
    }


    private void judgePhone(CharSequence phone){
        if(phone.length() == 11){
            if(isPhoneNum(etPhone.getText().toString().trim())){
                tilPhone.setErrorEnabled(false);
                sendCodeBtn.setEnabled(true);
            }else{
                tilPhone.setErrorEnabled(true);
                tilPhone.setError("输入的手机号码格式不正确");
            }
        }else{
            sendCodeBtn.setEnabled(false);
        }
    }

    /**
     * 验证手机号格式
     * @param phoneNum
     * @return
     */
    private boolean isPhoneNum(String phoneNum) {
        Pattern p = Pattern.compile(CHINA_MOBILE_PATTERN);//移动
        Matcher m = p.matcher(phoneNum);

        if(!m.matches()){
            p = Pattern.compile(CHINA_UNICOM_PATTERN);//联通
            m = p.matcher(phoneNum);
            if(!m.matches()){
                p = Pattern.compile(CHINA_TELECOM_PATTERN);//电信
                m = p.matcher(phoneNum);
                if(!m.matches()){
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }
        }else{
            return true;
        }
    }
}
