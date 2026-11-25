package com.huawei.hms.mlsdk.asr.engine;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.auth.api.proxy.AuthApiStatusCodes;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import com.huawei.hms.mlsdk.asr.MLAsrConstants;
import com.huawei.hms.mlsdk.asr.engine.utils.SmartLogger;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.tts.MLTtsAudioFragment;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes10.dex */
public class AsrEngine {
    private static final String TAG = "AsrEngine";
    private Callback callback;
    private AsrEngineConfig engineConfig;
    private com.huawei.hms.mlsdk.asr.engine.b mAsrProcessor;
    private com.huawei.hms.mlsdk.asr.engine.c mAsrRecorder;
    private String mAsrResult;
    private com.huawei.hms.mlsdk.asr.engine.d mAsrVadDetector;
    private int recognizerType;
    private Long recordStartTime = null;
    private Long processStartTime = null;
    private volatile boolean mIsDestroying = false;
    private String mTaskId = null;
    private Runnable mDestroy = new a();
    private Handler posterHandler = new Handler(Looper.getMainLooper());
    private Executor posterExecutor = new h();

    public interface Callback {
        void onEnergy(double d, byte[] bArr, Bundle bundle);

        void onError(AsrError asrError, String str);

        void onResult(AsrResult asrResult);

        void onState(int i);
    }

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            AsrEngine.this.runDestroy();
        }
    }

    class c implements com.huawei.hms.mlsdk.asr.engine.c.InterfaceC0119c {
        c() {
        }
    }

    class e implements Runnable {
        final /* synthetic */ Bundle a;

        e(Bundle bundle) {
            this.a = bundle;
        }

        @Override // java.lang.Runnable
        public void run() {
            AsrEngine.this.callback.onError(new AsrError(40, "not HuaWei phone and not Emui", this.a), null);
        }
    }

    class f implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ Bundle b;

        f(String str, Bundle bundle) {
            this.a = str;
            this.b = bundle;
        }

        @Override // java.lang.Runnable
        public void run() {
            AsrEngine.this.callback.onError(new AsrError(40, this.a, this.b), null);
        }
    }

    class g implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ Bundle b;

        g(String str, Bundle bundle) {
            this.a = str;
            this.b = bundle;
        }

        @Override // java.lang.Runnable
        public void run() {
            AsrEngine.this.callback.onError(new AsrError(40, this.a, this.b), null);
        }
    }

    class h implements Executor {
        h() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            AsrEngine.this.posterHandler.post(runnable);
        }
    }

    public AsrEngine(AsrEngineConfig asrEngineConfig, Callback callback) {
        this.engineConfig = asrEngineConfig;
        this.callback = callback;
    }

    private String checkParameters() {
        int feature;
        if (isRecognizerLong() || (feature = this.engineConfig.getFeature()) == 12 || feature == 11) {
            return null;
        }
        return "Invalid feature options";
    }

    private String checkPermissions() {
        String[] strArr = {"android.permission.INTERNET", "android.permission.ACCESS_WIFI_STATE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.RECORD_AUDIO"};
        Context appContext = MLApplication.getInstance().getAppContext();
        PackageManager packageManager = appContext.getPackageManager();
        String packageName = appContext.getPackageName();
        for (int i2 = 0; i2 < 4; i2++) {
            String str = strArr[i2];
            if (packageManager.checkPermission(str, packageName) == -1) {
                return com.huawei.hms.mlsdk.asr.o.a.a("Permission missing: ", str);
            }
        }
        return null;
    }

    private boolean isEMUIAndHuaWeiPhone() {
        String str;
        if (!com.huawei.hms.mlsdk.asr.o.g.a()) {
            try {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                str = (String) cls.getDeclaredMethod("get", String.class).invoke(cls, "ro.product.manufacturer");
            } catch (ClassNotFoundException unused) {
                SmartLogger.e("EmuiUtil", Constant.CLASS_NOT_FIND_EXCEPTION);
                str = "";
            } catch (IllegalAccessException unused2) {
                SmartLogger.e("EmuiUtil", "IllegalAccessException");
                str = "";
            } catch (NoSuchMethodException unused3) {
                SmartLogger.e("EmuiUtil", Constant.NO_SUCH_METHOD_EXCEPTION);
                str = "";
            } catch (InvocationTargetException unused4) {
                SmartLogger.e("EmuiUtil", "InvocationTargetException");
                str = "";
            }
            SmartLogger.i("EmuiUtil", "Get Manufacturer: " + str);
            if (!"HUAWEI".equals(str) && !"HONOR".equals(str) && !this.engineConfig.getLanguage().equals("zh") && !this.engineConfig.getLanguage().equals("en-US")) {
                Bundle bundle = new Bundle();
                bundle.putInt(MLAsrCaptureConstants.ASR_SUB_ERROR_CODE, 3016);
                this.posterExecutor.execute(new e(bundle));
                SmartLogger.e(TAG, "not HuaWei phone and not Emui");
                return false;
            }
        }
        return true;
    }

    private boolean legalityCheckPassed() {
        String strCheckPermissions = checkPermissions();
        if (strCheckPermissions != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(MLAsrCaptureConstants.ASR_SUB_ERROR_CODE, 1004);
            this.posterExecutor.execute(new f(strCheckPermissions, bundle));
            return false;
        }
        String strCheckParameters = checkParameters();
        if (strCheckParameters == null) {
            return true;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putInt(MLAsrCaptureConstants.ASR_SUB_ERROR_CODE, AuthApiStatusCodes.AUTH_API_CLIENT_ERROR);
        this.posterExecutor.execute(new g(strCheckParameters, bundle2));
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runDestroy() {
        com.huawei.hms.mlsdk.asr.engine.c cVar = this.mAsrRecorder;
        if (cVar != null) {
            cVar.a();
            this.mAsrRecorder = null;
        }
        com.huawei.hms.mlsdk.asr.engine.d dVar = this.mAsrVadDetector;
        if (dVar != null) {
            dVar.a();
            this.mAsrVadDetector = null;
        }
        com.huawei.hms.mlsdk.asr.engine.b bVar = this.mAsrProcessor;
        if (bVar != null) {
            bVar.a();
            this.mAsrProcessor = null;
        }
        this.mIsDestroying = false;
    }

    public AsrEngine create() {
        if (!isEMUIAndHuaWeiPhone() || !legalityCheckPassed()) {
            return this;
        }
        com.huawei.hms.mlsdk.asr.o.c.b().a(MLApplication.getInstance().getAppContext(), isRecognizerLong());
        StringBuilder sbAppend = new StringBuilder().append(String.valueOf(System.currentTimeMillis()));
        StringBuilder sb = new StringBuilder(3);
        try {
            SecureRandom instanceStrong = SecureRandom.getInstanceStrong();
            for (int i2 = 0; i2 < 3; i2++) {
                sb.append("0123456789".charAt(instanceStrong.nextInt(10)));
            }
        } catch (NoSuchAlgorithmException e2) {
            SmartLogger.i("HaAdapter_MLKitAsr", com.huawei.hms.mlsdk.asr.o.a.a("e:").append(e2.getMessage()).toString());
        }
        String string = sbAppend.append(sb.toString()).toString();
        com.huawei.hms.mlsdk.asr.o.c.b().a(string);
        SmartLogger.i(TAG, "TaskId: " + string);
        String language = this.engineConfig.getLanguage();
        Bundle bundle = new Bundle();
        bundle.putString("applanguage", language);
        com.huawei.hms.mlsdk.asr.o.c.b().a(string, 0, bundle);
        setTaskId(string);
        com.huawei.hms.mlsdk.asr.engine.b bVar = new com.huawei.hms.mlsdk.asr.engine.b(this);
        this.mAsrProcessor = bVar;
        bVar.a(string);
        this.mAsrRecorder = new com.huawei.hms.mlsdk.asr.engine.c();
        this.mAsrVadDetector = new com.huawei.hms.mlsdk.asr.engine.d(this);
        this.mAsrProcessor.a(new b(bundle));
        this.mAsrRecorder.a(new c());
        this.mAsrVadDetector.a(new d(bundle));
        if (this.mAsrProcessor.d()) {
            this.mAsrRecorder.b();
        }
        this.mAsrProcessor.f();
        return this;
    }

    public void destroy() {
        if (isRecognizerLong()) {
            com.huawei.hms.mlsdk.asr.o.c.b().f(getTaskId());
        }
        com.huawei.hms.mlsdk.asr.o.c.b().a();
        if (this.mIsDestroying) {
            SmartLogger.w(TAG, "AsrEngine is destroying");
        } else {
            this.mIsDestroying = true;
            new Thread(this.mDestroy).start();
        }
    }

    public String getAsrResult() {
        String str = this.mAsrResult;
        return str == null ? "" : str;
    }

    public AsrEngineConfig getEngineConfig() {
        return this.engineConfig;
    }

    public Long getProcessStartTime() {
        return this.processStartTime;
    }

    public int getRecognizerType() {
        return this.recognizerType;
    }

    public Long getRecordStartTime() {
        return this.recordStartTime;
    }

    public String getTaskId() {
        return this.mTaskId;
    }

    public boolean hasResult() {
        return !"".equals(getAsrResult());
    }

    public boolean isRecognizerLong() {
        return this.engineConfig.getRecognizerType() == 1;
    }

    public void setAsrResult(String str) {
        this.mAsrResult = str;
    }

    public void setRecognizerType(int i2) {
        this.recognizerType = i2;
    }

    public void setTaskId(String str) {
        this.mTaskId = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class i implements Runnable {
        private Callback a;
        private int b;
        private AsrResult c;
        private AsrError d;
        private String e;
        private int f = 1;

        public i(Callback callback, int i) {
            this.a = callback;
            this.b = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            int i = this.f;
            if (i == 1) {
                this.a.onState(this.b);
            } else if (i != 2) {
                this.a.onError(this.d, this.e);
            } else {
                SmartLogger.d("ResultDeliveryRunnable", com.huawei.hms.mlsdk.asr.o.a.a("result.text is: onResult= ").append(this.c.getText()).append("  ").append(this.c.getRetCode()).toString());
                this.a.onResult(this.c);
            }
        }

        public i(Callback callback, AsrResult asrResult) {
            this.a = callback;
            this.c = asrResult;
        }

        public i(Callback callback, AsrError asrError, String str) {
            this.a = callback;
            this.d = asrError;
            this.e = str;
        }
    }

    class d implements com.huawei.hms.mlsdk.asr.engine.d.a {
        private boolean a = false;
        final /* synthetic */ Bundle b;

        d(Bundle bundle) {
            this.b = bundle;
        }

        public void a(com.huawei.hms.mlsdk.asr.engine.d.b bVar) {
            Bundle bundle = new Bundle();
            bundle.putString(MLAsrConstants.WAVE_PAINE_ENCODING, "pcm");
            bundle.putInt(MLAsrConstants.WAVE_PAINE_SAMPLE_RATE, MLTtsAudioFragment.SAMPLE_RATE_16K);
            bundle.putInt(MLAsrConstants.WAVE_PAINE_BIT_WIDTH, 16);
            bundle.putInt(MLAsrConstants.WAVE_PAINE_CHANNEL_COUNT, 1);
            AsrEngine.this.callback.onEnergy(bVar.b(), bVar.a(), bundle);
            SmartLogger.d(AsrEngine.TAG, "onResult(AsrVadDetector.Result result)");
            if (AsrEngine.this.mAsrProcessor == null || !this.a) {
                return;
            }
            AsrEngine.this.mAsrProcessor.a(bVar.a());
        }

        public void b(long j) {
            AsrEngine.this.posterExecutor.execute(new i(AsrEngine.this.callback, 2));
            SmartLogger.d(AsrEngine.TAG, "onNoSound(long time)");
        }

        public void c(long j) {
            AsrEngine.this.posterExecutor.execute(new i(AsrEngine.this.callback, new AsrResult(3)));
            SmartLogger.d(AsrEngine.TAG, "onNoSoundTimesExceed(long time)");
            if (AsrEngine.this.mAsrProcessor != null) {
                SmartLogger.i(AsrEngine.TAG, "onNoSoundTimesExceed and send finish");
                AsrEngine.this.mAsrProcessor.e();
            }
        }

        public void d(long j) {
            SmartLogger.i(AsrEngine.TAG, "onVoiceEnd");
            this.b.putLong("speechEndTime", System.currentTimeMillis());
            SmartLogger.i(AsrEngine.TAG, "onVoiceEnd speechEndTime: " + System.currentTimeMillis());
            com.huawei.hms.mlsdk.asr.o.c.b().a(AsrEngine.this.getTaskId(), 2, this.b);
            if (AsrEngine.this.mAsrProcessor != null) {
                AsrEngine.this.mAsrRecorder.a();
                AsrEngine.this.mAsrProcessor.b();
            }
        }

        public void e(long j) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.b.putLong("speechStartTime", jCurrentTimeMillis);
            SmartLogger.i(AsrEngine.TAG, "speechStartTime: " + jCurrentTimeMillis);
            com.huawei.hms.mlsdk.asr.o.c.b().a(AsrEngine.this.getTaskId(), 1, this.b);
            this.a = true;
            AsrEngine.this.posterExecutor.execute(new i(AsrEngine.this.callback, 10));
            SmartLogger.d(AsrEngine.TAG, "onVoiceStart(long time)");
            SmartLogger.i(AsrEngine.TAG, "start send audio to cloud at " + j);
        }

        public void a(long j) {
            AsrEngine.this.posterExecutor.execute(new i(AsrEngine.this.callback, new AsrResult(4, AsrEngine.this.mAsrResult)));
            SmartLogger.d(AsrEngine.TAG, "onMaxDurationExceed(long time)");
        }

        public void a(AsrError asrError) {
            AsrEngine.this.posterExecutor.execute(new i(AsrEngine.this.callback, asrError, AsrEngine.this.mAsrResult));
            SmartLogger.d(AsrEngine.TAG, "onError(AsrError error)");
        }
    }

    class b implements com.huawei.hms.mlsdk.asr.engine.b.e {
        boolean a = false;
        final /* synthetic */ Bundle b;

        b(Bundle bundle) {
            this.b = bundle;
        }

        public void a(AsrResult asrResult) {
            String text = asrResult.getText();
            if (text != null && !"".equals(text)) {
                AsrEngine.this.mAsrResult = text;
            }
            if (asrResult.getRetCode() != 5 && !this.a) {
                this.a = true;
                if (com.huawei.hms.mlsdk.asr.o.c.b().b(AsrEngine.this.getTaskId()) == 0) {
                    long jC = com.huawei.hms.mlsdk.asr.o.c.b().c(AsrEngine.this.getTaskId());
                    if (jC == 0 && AsrEngine.this.mAsrProcessor != null) {
                        jC = AsrEngine.this.mAsrProcessor.c();
                        SmartLogger.i(AsrEngine.TAG, "there is no sendFinalDataTime and use processor.getSendLastDataTime, is " + jC);
                    }
                    long jCurrentTimeMillis = System.currentTimeMillis() - jC;
                    SmartLogger.i(AsrEngine.TAG, "onResult but lastWordCost is 0 ,so to calculate lastWorCost: " + jCurrentTimeMillis);
                    this.b.putInt("lastWordCost", (int) jCurrentTimeMillis);
                    com.huawei.hms.mlsdk.asr.o.c.b().a(AsrEngine.this.getTaskId(), 4, this.b);
                }
                if (AsrEngine.this.mAsrResult != null) {
                    int length = AsrEngine.this.mAsrResult.length();
                    SmartLogger.i(AsrEngine.TAG, "onResult textLenth :" + length);
                    this.b.putInt("textLength", length);
                    com.huawei.hms.mlsdk.asr.o.c.b().a(AsrEngine.this.getTaskId(), 7, this.b);
                    this.b.putInt("result", 0);
                    com.huawei.hms.mlsdk.asr.o.c.b().a(AsrEngine.this.getTaskId(), 9, this.b);
                    com.huawei.hms.mlsdk.asr.o.c.b().f(AsrEngine.this.getTaskId());
                }
            }
            SmartLogger.d(AsrEngine.TAG, com.huawei.hms.mlsdk.asr.o.a.a("result.text is:").append(asrResult.getText()).append(", engine.asrResult====>").append(AsrEngine.this.mAsrResult).append("  ").append(asrResult.getRetCode()).toString());
            if (!AsrEngine.this.isRecognizerLong()) {
                AsrEngine.this.posterExecutor.execute(new i(AsrEngine.this.callback, new AsrResult(asrResult.getRetCode(), AsrEngine.this.mAsrResult)));
                return;
            }
            String str = AsrEngine.this.mAsrResult;
            if (!TextUtils.isEmpty(AsrEngine.this.mAsrResult)) {
                AsrResult asrResult2 = new AsrResult(asrResult.getRetCode(), null, str, asrResult.isFinal());
                asrResult2.setWordOffsetList(asrResult.getWordOffsetList());
                asrResult2.setSentenceOffsetList(asrResult.getSentenceOffsetList());
                SmartLogger.d(AsrEngine.TAG, "onResult(AsrResult result)");
                AsrEngine.this.posterExecutor.execute(new i(AsrEngine.this.callback, asrResult2));
                if (asrResult.isFinal()) {
                    AsrEngine.this.mAsrResult = "";
                }
            }
            if (8 == asrResult.getRetCode()) {
                AsrEngine.this.destroy();
            }
        }

        public void a(AsrError asrError) {
            int errorCode = asrError.getErrorCode();
            if (AsrEngine.this.mAsrResult != null) {
                int length = AsrEngine.this.mAsrResult.length();
                SmartLogger.i(AsrEngine.TAG, "onError textLenth :" + length);
                this.b.putInt("textLength", length);
                com.huawei.hms.mlsdk.asr.o.c.b().a(AsrEngine.this.getTaskId(), 7, this.b);
            }
            this.b.putInt("result", errorCode);
            com.huawei.hms.mlsdk.asr.o.c.b().a(AsrEngine.this.getTaskId(), 9, this.b);
            this.b.putString("errMsg", asrError.toString());
            com.huawei.hms.mlsdk.asr.o.c.b().a(AsrEngine.this.getTaskId(), 10, this.b);
            com.huawei.hms.mlsdk.asr.o.c.b().f(AsrEngine.this.getTaskId());
            AsrEngine.this.posterExecutor.execute(new i(AsrEngine.this.callback, asrError, AsrEngine.this.mAsrResult));
            SmartLogger.d(AsrEngine.TAG, "onError(AsrError error)");
        }

        public void a(int i) {
            AsrEngine.this.posterExecutor.execute(new i(AsrEngine.this.callback, i));
            SmartLogger.d(AsrEngine.TAG, "onState(int state)");
        }

        public void a(long j) {
            SmartLogger.d(AsrEngine.TAG, "onSilence");
            AsrEngineConfig asrEngineConfig = AsrEngine.this.engineConfig;
            int vadStartMuteDetectTimes = asrEngineConfig.getVadStartMuteDetectTimes() * asrEngineConfig.getVadStartMuteDuration();
            SmartLogger.i(AsrEngine.TAG, com.huawei.hms.mlsdk.asr.o.a.a("engine.hasResult: ").append(AsrEngine.this.hasResult()).append("engine.recordStartTime: ").append(AsrEngine.this.recordStartTime).append("time-engine.recordStartTime").append(j - AsrEngine.this.recordStartTime.longValue()).append("maxAllowMuteDuration").append(vadStartMuteDetectTimes).toString());
            if (!AsrEngine.this.hasResult() && j - AsrEngine.this.recordStartTime.longValue() < vadStartMuteDetectTimes) {
                SmartLogger.i(AsrEngine.TAG, "onSilence return");
                return;
            }
            if (AsrEngine.this.isRecognizerLong()) {
                return;
            }
            a(new AsrResult(8));
            if (AsrEngine.this.mAsrProcessor != null) {
                SmartLogger.i(AsrEngine.TAG, "server detect silence and send finish");
                AsrEngine.this.mAsrRecorder.a();
                AsrEngine.this.mAsrProcessor.e();
            }
        }
    }
}
