package com.huawei.hms.mlsdk.internal.client.adapter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.update.kpms.KpmsConstant;

/* JADX INFO: loaded from: classes10.dex */
public class BridgeActivity extends Activity {
    private static final int BRIDGE_REQUEST_CODE = 2084;
    private static final int KIT_UPDATE_RESULT_CANCELED = 3;
    private static final int KIT_UPDATE_RESULT_FAILED = 0;
    private static final int KIT_UPDATE_RESULT_SUCCESS = 1;
    private static final int KIT_UPDATE_RESULT_UPDATE_LATER = 2;
    private static final int RESULT_FAILED = 0;
    private static final int RESULT_SUCCESS = -1;
    private static final String TAG = "BridgeActivity";

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initialActivityFeature();
        startModuleDownloadActivity();
    }

    private void initialActivityFeature() {
        getWindow().addFlags(67108864);
        requestWindowFeature(1);
    }

    private void startModuleDownloadActivity() {
        Intent intent;
        try {
            intent = (Intent) getIntent().getParcelableExtra("module_download_activity");
        } catch (Exception unused) {
            SmartLog.e(TAG, "startModuleDownloadActivity failed");
            intent = null;
        }
        if (intent != null) {
            startActivityForResult(intent, BRIDGE_REQUEST_CODE);
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        int intExtra = intent.getIntExtra(KpmsConstant.KIT_UPDATE_RESULT, -1);
        String str = TAG;
        SmartLog.w(str, "requestCode = " + i);
        SmartLog.w(str, "resultCode = " + i2);
        SmartLog.w(str, "kitUpdateResult = " + intExtra);
        finish();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }
}
