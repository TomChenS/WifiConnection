package com.ittest.cs.wificonnection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ittest.cs.wificonnection.Utils.SpUtil;
import com.ittest.cs.wificonnection.Utils.WifiUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtnon;
    private Button mBtnoff;
    private Button mBtnText;
    private TextView mTv;
    private TextView mWifi;
    private TextView mText;
    private Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
//        设置点击事件
        mBtnon.setOnClickListener(this);
        mBtnoff.setOnClickListener(this);
        mBtnText.setOnClickListener(this);
    }

    private void initData() {
//        初始化数据
        mTv.setText("当前wifi："+ WifiUtil.getWifiName(this));
        mWifi.setText("已绑定wifi："+("".equals(SpUtil.getString(this,"wifi"))? "未绑定":SpUtil.getString(this,"wifi")));
        mText.setText("提醒内容："+("".equals(SpUtil.getString(this,"内容"))? "记得打卡":SpUtil.getString(this,"内容")));
    }

    private void initView() {
//        初始化控件
        mBtnon = (Button) findViewById(R.id.button_btn_on);
        mBtnoff = (Button) findViewById(R.id.button_btn_off);
        mBtnText = (Button) findViewById(R.id.button_text);
        mTv = (TextView) findViewById(R.id.tv);
        mWifi = (TextView) findViewById(R.id.tv_wifi);
        mText = (TextView) findViewById(R.id.tv_text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_btn_on:
                SpUtil.saveBoolean(this,"检测",true);
                SpUtil.saveString(this,"wifi",WifiUtil.getWifiName(this));
                initData();
                mServiceIntent = new Intent(this,WifiService.class);
                startService(mServiceIntent);
                break;
            case R.id.button_btn_off:
                SpUtil.saveBoolean(this,"检测",false);
                SpUtil.saveString(this,"wifi","未绑定");
                initData();
                stopService(mServiceIntent);
                break;
            case R.id.button_text:
                EditText editText = (EditText) findViewById(R.id.et);
                String eText = editText.getText().toString();
                SpUtil.saveString(this,"内容",eText);
                editText.setText(null);
                initData();
                //关闭输入法键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            default:

                break;
        }

    }
}