package com.gyx.projectconstruct;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

import com.gyx.projectconstruct.utils.Log4jConfigure;

public class MainActivity extends Activity {
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log4jConfigure.debug("hello world");
        StatService.trackCustomEvent(this,"init","");

    }
}
