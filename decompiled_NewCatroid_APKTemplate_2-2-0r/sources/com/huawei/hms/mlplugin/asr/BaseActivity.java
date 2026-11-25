package com.huawei.hms.mlplugin.asr;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.huawei.hms.mlplugin.asr.view.VoiceWaveView;

/* JADX INFO: loaded from: classes10.dex */
public abstract class BaseActivity extends Activity {
    protected VoiceWaveView a;

    public abstract int a();

    protected void b() {
        this.a = (VoiceWaveView) findViewById(R.id.waveLineView);
    }

    protected void c() {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = -1;
        attributes.height = -2;
        attributes.gravity = 80;
        window.setAttributes(attributes);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(a());
        c();
        b();
    }
}
