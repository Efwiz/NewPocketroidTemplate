package com.huawei.hms.mlplugin.asr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.huawei.hms.mlplugin.asr.view.EllipsizeTextView;
import com.huawei.hms.mlplugin.asr.view.VoiceWaveView;
import com.huawei.hms.mlsdk.asr.MLAsrConstants;
import com.huawei.hms.mlsdk.asr.engine.AsrEngine;
import com.huawei.hms.mlsdk.asr.engine.AsrEngineConfig;
import com.huawei.hms.mlsdk.asr.engine.AsrError;
import com.huawei.hms.mlsdk.asr.engine.AsrResult;
import com.huawei.hms.mlsdk.asr.engine.utils.SmartLogger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/* JADX INFO: loaded from: classes10.dex */
public class MLAsrCaptureActivity extends BaseActivity {
    private b c;
    private EllipsizeTextView d;
    private AsrEngine b = null;
    private boolean e = false;
    private boolean f = false;
    private boolean g = false;

    class a implements AsrEngine.Callback {
        final /* synthetic */ AsrEngineConfig a;

        /* JADX INFO: renamed from: com.huawei.hms.mlplugin.asr.MLAsrCaptureActivity$a$a, reason: collision with other inner class name */
        class RunnableC0115a implements Runnable {
            final /* synthetic */ String a;
            final /* synthetic */ int b;
            final /* synthetic */ AsrError c;

            RunnableC0115a(String str, int i, AsrError asrError) {
                this.a = str;
                this.b = i;
                this.c = asrError;
            }

            @Override // java.lang.Runnable
            public void run() {
                Intent intent = new Intent();
                String str = this.a;
                if (str == null) {
                    str = "";
                }
                intent.putExtra("result", str);
                SmartLogger.i("AsrProcessor", "onError in capture activity====>" + this.a);
                intent.putExtra("errorCode", this.b);
                intent.putExtra(MLAsrCaptureConstants.ASR_ERROR_MESSAGE, this.c.getMessage() != null ? this.c.getMessage() : "");
                Integer subErrorCode = this.c.getSubErrorCode();
                if (subErrorCode != null) {
                    intent.putExtra(MLAsrCaptureConstants.ASR_SUB_ERROR_CODE, subErrorCode);
                }
                MLAsrCaptureActivity.this.setResult(-1, intent);
                MLAsrCaptureActivity.this.finish();
            }
        }

        a(AsrEngineConfig asrEngineConfig) {
            this.a = asrEngineConfig;
        }

        @Override // com.huawei.hms.mlsdk.asr.engine.AsrEngine.Callback
        public void onEnergy(double d, byte[] bArr, Bundle bundle) {
            ((com.huawei.hms.mlplugin.asr.a) MLAsrCaptureActivity.this.c).a(d, bArr, bundle);
        }

        @Override // com.huawei.hms.mlsdk.asr.engine.AsrEngine.Callback
        public void onError(AsrError asrError, String str) {
            String strB;
            int iB = MLAsrCaptureActivity.this.b(asrError.getErrorCode());
            SmartLogger.e("AsrProcessor", "onError code : " + iB + ", message : " + asrError.getSubErrorCode() + ", " + asrError.getMessage());
            if (iB == 11202) {
                strB = MLAsrCaptureActivity.b(MLAsrCaptureActivity.this, 7);
            } else {
                strB = iB == 11204 ? MLAsrCaptureActivity.b(MLAsrCaptureActivity.this, 6) : MLAsrCaptureActivity.b(MLAsrCaptureActivity.this, 40);
            }
            MLAsrCaptureActivity.a(MLAsrCaptureActivity.this, strB);
            MLAsrCaptureActivity.this.getWindow().getDecorView().postDelayed(new RunnableC0115a(str, iB, asrError), 1000L);
        }

        @Override // com.huawei.hms.mlsdk.asr.engine.AsrEngine.Callback
        public void onResult(AsrResult asrResult) {
            if (asrResult.getRetCode() == 5) {
                if (this.a.getFeature() == 11) {
                    MLAsrCaptureActivity.a(MLAsrCaptureActivity.this, asrResult.getText());
                }
            } else {
                MLAsrCaptureActivity.a(MLAsrCaptureActivity.this, asrResult.getText());
                Intent intent = new Intent();
                SmartLogger.d("AsrProcessor", "onResult in capture activity====>" + asrResult.getText());
                intent.putExtra("result", asrResult.getText() == null ? "" : asrResult.getText());
                MLAsrCaptureActivity.this.setResult(0, intent);
                MLAsrCaptureActivity.this.finish();
            }
        }

        @Override // com.huawei.hms.mlsdk.asr.engine.AsrEngine.Callback
        public void onState(int i) {
            if (MLAsrCaptureActivity.this.a(i)) {
                MLAsrCaptureActivity.a(MLAsrCaptureActivity.this, MLAsrCaptureActivity.b(MLAsrCaptureActivity.this, i));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(int i) {
        return i == 7 || i == 40 || i == 6 || i == 2 || i == 9 || i == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int b(int i) {
        if (i == 6) {
            return 6;
        }
        if (i == 7) {
            return MLAsrConstants.ERR_NO_NETWORK;
        }
        if (i == 40) {
            return MLAsrConstants.ERR_SERVICE_UNAVAILABLE;
        }
        if (i != 44) {
            return i;
        }
        return 11219;
    }

    private void d() {
        AsrEngine asrEngine = this.b;
        if (asrEngine != null) {
            asrEngine.destroy();
        }
        VoiceWaveView voiceWaveView = this.a;
        if (voiceWaveView != null) {
            voiceWaveView.e();
        }
    }

    @Override // com.huawei.hms.mlplugin.asr.BaseActivity
    protected void b() {
        super.b();
        this.d = (EllipsizeTextView) findViewById(R.id.details);
        this.a.d();
    }

    @Override // android.app.Activity
    public void finish() {
        Log.i("AsrProcessor", "plugin ui finish() ");
        if (this.e) {
            return;
        }
        this.e = true;
        d();
        super.finish();
        overridePendingTransition(R.anim.mlkit_asr_popup_slide_show, R.anim.mlkit_asr_popup_slide_dismiss);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        SmartLogger.d("AsrProcessor", "onBackPressed");
        Intent intent = new Intent();
        intent.putExtra("result", this.b.getAsrResult());
        setResult(0, intent);
        super.onBackPressed();
    }

    /* JADX WARN: Code duplicated, block: B:24:0x00a5  */
    @Override // com.huawei.hms.mlplugin.asr.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        Bundle extras;
        b aVar;
        super.onCreate(bundle);
        SmartLogger.d("AsrProcessor", " onCreate()  -- mIsAlreadyPause:--- " + this.f);
        try {
            extras = getIntent().getExtras();
        } catch (RuntimeException e) {
            SmartLogger.e("MLAsrCaptureActivity", "RuntimeException e = " + e.getMessage());
            extras = null;
        }
        if (extras != null) {
            try {
                if (extras.containsKey(MLAsrCaptureConstants.WAVE_PAINTER_CLASS)) {
                    try {
                        try {
                            aVar = (b) Class.forName(extras.getString(MLAsrCaptureConstants.WAVE_PAINTER_CLASS)).newInstance();
                            if (aVar == null) {
                                aVar = new com.huawei.hms.mlplugin.asr.a(this.a);
                            }
                        } catch (RuntimeException e2) {
                            SmartLogger.e("MLAsrCaptureActivity", "RuntimeException e = " + e2.getMessage());
                            aVar = new com.huawei.hms.mlplugin.asr.a(this.a);
                        }
                    } catch (Exception e3) {
                        SmartLogger.e("MLAsrCaptureActivity", "Exception e = " + e3.getMessage());
                        aVar = new com.huawei.hms.mlplugin.asr.a(this.a);
                    }
                } else {
                    aVar = new com.huawei.hms.mlplugin.asr.a(this.a);
                }
            } catch (Throwable th) {
                new com.huawei.hms.mlplugin.asr.a(this.a);
                throw th;
            }
        } else {
            aVar = new com.huawei.hms.mlplugin.asr.a(this.a);
        }
        this.c = aVar;
        AsrEngineConfig asrEngineConfigLoad = new AsrEngineConfig().load(extras);
        overridePendingTransition(R.anim.mlkit_asr_popup_slide_show, R.anim.mlkit_asr_popup_slide_dismiss);
        AsrEngine asrEngine = new AsrEngine(asrEngineConfigLoad, new a(asrEngineConfigLoad));
        this.b = asrEngine;
        asrEngine.create();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        Log.i("AsrProcessor", "plugin ui onDestroy");
        super.onDestroy();
        d();
    }

    @Override // android.app.Activity
    protected void onPause() {
        this.f = true;
        SmartLogger.i("AsrProcessor", "plugin ui onPause()");
        AsrEngine asrEngine = this.b;
        if (asrEngine != null) {
            asrEngine.destroy();
        }
        super.onPause();
        this.a.b();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        SmartLogger.d("AsrProcessor", " onResume()  -- mIsAlreadyPause:--- " + this.f);
        if (!this.f) {
            Log.i("AsrProcessor", "plugin ui resume");
            this.a.c();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("result", this.b.getAsrResult());
        setResult(0, intent);
        SmartLogger.d("AsrProcessor", " onResume()");
        finish();
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        Log.i("AsrProcessor", "plugin ui start");
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        SmartLogger.d("AsrProcessor", "event type is" + motionEvent.getAction());
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int scaledWindowTouchSlop = ViewConfiguration.get(this).getScaledWindowTouchSlop();
        View decorView = getWindow().getDecorView();
        int i = -scaledWindowTouchSlop;
        if ((x < i || y < i || x > decorView.getWidth() + scaledWindowTouchSlop || y > decorView.getHeight() + scaledWindowTouchSlop) && !this.g) {
            int action = motionEvent.getAction();
            if (action == 0) {
                SmartLogger.d("AsrProcessor", "按住");
            } else if (action == 1) {
                SmartLogger.d("AsrProcessor", "抬起");
            } else if (action == 2) {
                SmartLogger.d("AsrProcessor", "移动");
            }
            this.g = true;
            SmartLogger.i("MLAsrCaptureActivity", "===================================================================isOutOfBounds");
            SmartLogger.d("AsrProcessor", "onTouchOutside,result===" + this.b.getAsrResult());
            Intent intent = new Intent();
            intent.putExtra("result", this.b.getAsrResult());
            setResult(0, intent);
        }
        SmartLogger.i("MLAsrCaptureActivity", "==================================================================isInsideBounds");
        return super.onTouchEvent(motionEvent);
    }

    static /* synthetic */ void a(MLAsrCaptureActivity mLAsrCaptureActivity, String str) {
        EllipsizeTextView ellipsizeTextView = mLAsrCaptureActivity.d;
        if (ellipsizeTextView != null) {
            ellipsizeTextView.setEllipsizeText(str);
        }
    }

    static /* synthetic */ String b(MLAsrCaptureActivity mLAsrCaptureActivity, int i) {
        String[] stringArray;
        int iNextInt;
        if (i == 1) {
            stringArray = mLAsrCaptureActivity.getResources().getStringArray(R.array.state_listening);
        } else if (i == 2) {
            stringArray = mLAsrCaptureActivity.getResources().getStringArray(R.array.state_nosound);
        } else if (i == 6) {
            stringArray = mLAsrCaptureActivity.getResources().getStringArray(R.array.state_nounderstand);
        } else if (i == 7) {
            stringArray = mLAsrCaptureActivity.getResources().getStringArray(R.array.state_nonetwork);
        } else if (i != 9) {
            stringArray = mLAsrCaptureActivity.getResources().getStringArray(R.array.state_service_unavailable);
        } else {
            stringArray = mLAsrCaptureActivity.getResources().getStringArray(R.array.state_waiting);
        }
        if (stringArray == null || stringArray.length == 0) {
            return null;
        }
        try {
            iNextInt = SecureRandom.getInstanceStrong().nextInt(stringArray.length);
        } catch (NoSuchAlgorithmException e) {
            Log.i("AsrProcessor", "e : " + e.getMessage());
            iNextInt = 0;
        }
        return stringArray[iNextInt];
    }

    @Override // com.huawei.hms.mlplugin.asr.BaseActivity
    public int a() {
        return R.layout.mlkit_asr_layout_capture_activity;
    }
}
