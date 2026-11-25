package com.huawei.hms.mlsdk.asr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.huawei.hms.mlsdk.asr.engine.AsrConstants;
import com.huawei.hms.mlsdk.asr.engine.AsrEngine;
import com.huawei.hms.mlsdk.asr.engine.AsrEngineConfig;
import com.huawei.hms.mlsdk.asr.engine.AsrError;
import com.huawei.hms.mlsdk.asr.engine.AsrResult;
import com.huawei.hms.mlsdk.asr.engine.cloud.vo.AsrLanguageResponse;
import com.huawei.hms.mlsdk.asr.engine.utils.HttpUtils;
import com.huawei.hms.mlsdk.asr.engine.utils.JsonUtil;
import com.huawei.hms.mlsdk.asr.engine.utils.SmartLogger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes10.dex */
public class MLAsrRecognizer {
    public static final String RESULTS_RECOGNIZED = "results_recognized";
    public static final String RESULTS_RECOGNIZING = "results_recognizing";
    private static final String TAG = "MLAsrRecognizer";
    private AsrEngine mAsrEngine;
    private MLAsrListener mAsrListener;
    private AsrEngineConfig mConfig;
    private final Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private volatile boolean mHasStartedRecognized = false;
    private LanguageCallback mLanguageCallback;

    public interface LanguageCallback {
        void onError(int i, String str);

        void onResult(List<String> list);
    }

    class a implements AsrEngine.Callback {
        boolean a = false;
        private Handler b = new Handler(Looper.getMainLooper());
        private Executor c = new ExecutorC0116a();

        /* JADX INFO: renamed from: com.huawei.hms.mlsdk.asr.MLAsrRecognizer$a$a, reason: collision with other inner class name */
        class ExecutorC0116a implements Executor {
            ExecutorC0116a() {
            }

            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) {
                a.this.b.post(runnable);
            }
        }

        class b implements Runnable {
            final /* synthetic */ byte[] a;
            final /* synthetic */ double b;

            b(byte[] bArr, double d) {
                this.a = bArr;
                this.b = d;
            }

            @Override // java.lang.Runnable
            public void run() {
                MLAsrRecognizer.this.mAsrListener.onVoiceDataReceived(this.a, (float) this.b, new Bundle());
            }
        }

        a() {
        }

        @Override // com.huawei.hms.mlsdk.asr.engine.AsrEngine.Callback
        public void onEnergy(double d, byte[] bArr, Bundle bundle) {
            if (MLAsrRecognizer.this.mAsrListener != null) {
                this.c.execute(new b(bArr, d));
            }
        }

        @Override // com.huawei.hms.mlsdk.asr.engine.AsrEngine.Callback
        public void onError(AsrError asrError, String str) {
            String str2 = ",errorMessage: " + (asrError.getMessage() == null ? "no error message" : asrError.getMessage());
            int iTranslateErrorCode = MLAsrRecognizer.this.translateErrorCode(asrError.getErrorCode());
            Integer subErrorCode = asrError.getSubErrorCode();
            if (MLAsrRecognizer.this.mAsrListener != null) {
                if (asrError.getMessage().equals("No network")) {
                    MLAsrRecognizer.this.mAsrListener.onState(7, new Bundle());
                } else {
                    MLAsrRecognizer.this.mAsrListener.onState(iTranslateErrorCode, new Bundle());
                }
            }
            if (subErrorCode != null) {
                str2 = ",subError code: " + subErrorCode + str2;
            }
            if (MLAsrRecognizer.this.mAsrListener != null) {
                MLAsrRecognizer.this.mAsrListener.onError(iTranslateErrorCode, str2);
            }
            SmartLogger.i(MLAsrRecognizer.TAG, "onError code:" + asrError.getErrorCode() + str2);
            MLAsrRecognizer.this.destroy();
        }

        @Override // com.huawei.hms.mlsdk.asr.engine.AsrEngine.Callback
        public void onResult(AsrResult asrResult) {
            if (MLAsrRecognizer.this.mAsrListener == null) {
                MLAsrRecognizer.this.destroy();
                return;
            }
            Bundle bundle = new Bundle();
            int retCode = asrResult.getRetCode();
            if (retCode == 3) {
                MLAsrRecognizer.this.mAsrListener.onState(3, new Bundle());
                this.a = true;
                bundle.putString(MLAsrRecognizer.RESULTS_RECOGNIZED, "");
                MLAsrRecognizer.this.mAsrListener.onResults(bundle);
                MLAsrRecognizer.this.destroy();
                return;
            }
            if (retCode != 4) {
                if (retCode == 5) {
                    if (MLAsrRecognizer.this.mConfig.getFeature() == 11) {
                        if (asrResult.getText() == null) {
                            SmartLogger.d(MLAsrRecognizer.TAG, "RECOGNIZING result.getText() is null");
                            return;
                        } else {
                            bundle.putString(MLAsrRecognizer.RESULTS_RECOGNIZING, asrResult.getText());
                            MLAsrRecognizer.this.mAsrListener.onRecognizingResults(bundle);
                            return;
                        }
                    }
                    return;
                }
                if (retCode != 8) {
                    MLAsrRecognizer.this.destroy();
                    SmartLogger.w(MLAsrRecognizer.TAG, "!!! other code -->" + asrResult.getRetCode());
                    return;
                }
            }
            if (this.a) {
                SmartLogger.i(MLAsrRecognizer.TAG, "NO_SOUND_TIMES_EXCEED has detected before");
                return;
            }
            if (asrResult.getText() != null) {
                bundle.putString(MLAsrRecognizer.RESULTS_RECOGNIZED, asrResult.getText());
                MLAsrRecognizer.this.mAsrListener.onResults(bundle);
            } else {
                bundle.putString(MLAsrRecognizer.RESULTS_RECOGNIZED, "");
                SmartLogger.i(MLAsrRecognizer.TAG, " onResults text is null");
                MLAsrRecognizer.this.mAsrListener.onResults(bundle);
            }
            MLAsrRecognizer.this.destroy();
        }

        @Override // com.huawei.hms.mlsdk.asr.engine.AsrEngine.Callback
        public void onState(int i) {
            SmartLogger.i(MLAsrRecognizer.TAG, "status code：" + i);
            if (MLAsrRecognizer.this.mAsrListener == null) {
                return;
            }
            if (i == 1) {
                SmartLogger.d(MLAsrRecognizer.TAG, "please start speeching");
                MLAsrRecognizer.this.mAsrListener.onStartListening();
                MLAsrRecognizer.this.mAsrListener.onState(i, new Bundle());
            } else if (i != 10) {
                MLAsrRecognizer.this.mAsrListener.onState(i, new Bundle());
            } else {
                SmartLogger.d(MLAsrRecognizer.TAG, "user started to speech");
                MLAsrRecognizer.this.mAsrListener.onStartingOfSpeech();
            }
        }
    }

    class b implements Callback {
        final /* synthetic */ List a;
        final /* synthetic */ long b;

        b(List list, long j) {
            this.a = list;
            this.b = j;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            SmartLogger.e(MLAsrRecognizer.TAG, "language list unavailable:" + iOException.getMessage());
            MLAsrRecognizer.this.onError(MLAsrConstants.ERR_NO_NETWORK, "language list unavailable:" + iOException.getMessage());
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            String strString = response.body().string();
            AsrLanguageResponse asrLanguageResponse = (AsrLanguageResponse) new Gson().fromJson(strString, AsrLanguageResponse.class);
            if (asrLanguageResponse == null) {
                MLAsrRecognizer.this.onError(MLAsrConstants.ERR_NO_NETWORK, "Abnormal network connection.");
                return;
            }
            String retCode = asrLanguageResponse.getRetCode();
            if (TextUtils.isEmpty(retCode)) {
                SmartLogger.e(MLAsrRecognizer.TAG, "connect failed:" + asrLanguageResponse.getRetMsg());
                MLAsrRecognizer.this.onError(MLAsrConstants.ERR_NO_NETWORK, "Abnormal network connection.");
                return;
            }
            if (!retCode.equals("0")) {
                if (retCode.equals("001001")) {
                    SmartLogger.e(MLAsrRecognizer.TAG, "Abnormal network connection.");
                    MLAsrRecognizer.this.onError(11219, asrLanguageResponse.getRetMsg());
                    return;
                } else {
                    SmartLogger.e(MLAsrRecognizer.TAG, "connect failed:" + asrLanguageResponse.getRetMsg());
                    MLAsrRecognizer.this.onError(MLAsrConstants.ERR_NO_NETWORK, asrLanguageResponse.getRetMsg());
                    return;
                }
            }
            SmartLogger.d(MLAsrRecognizer.TAG, "language list response=" + strString);
            if (!HttpUtils.getInstance().isLegalJson(strString)) {
                SmartLogger.e(MLAsrRecognizer.TAG, "language list parse error.[" + strString + "]");
                MLAsrRecognizer.this.onError(MLAsrConstants.ERR_SERVICE_UNAVAILABLE, "language list parse error.[" + strString + "]");
                return;
            }
            try {
                JSONArray jsonArray = JsonUtil.getJsonArray(JsonUtil.parseJson(strString).getJSONObject("result"), "languages");
                if (jsonArray != null && jsonArray.length() != 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        this.a.add(jsonArray.getString(i));
                    }
                    MLAsrRecognizer.this.onResult(this.a);
                    SmartLogger.d(MLAsrRecognizer.TAG, "request support languages time is : " + (System.currentTimeMillis() - this.b));
                    return;
                }
                SmartLogger.e(MLAsrRecognizer.TAG, "Response json not contain languages");
                MLAsrRecognizer.this.onError(MLAsrConstants.ERR_SERVICE_UNAVAILABLE, "Response json not contain languages");
            } catch (JSONException e) {
                SmartLogger.e(MLAsrRecognizer.TAG, "JSONException", e);
                MLAsrRecognizer.this.onError(MLAsrConstants.ERR_SERVICE_UNAVAILABLE, "JSONException" + e.getMessage());
            }
        }
    }

    class c implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ String b;

        c(int i, String str) {
            this.a = i;
            this.b = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (MLAsrRecognizer.this.mLanguageCallback != null) {
                MLAsrRecognizer.this.mLanguageCallback.onError(this.a, this.b);
            }
        }
    }

    class d implements Runnable {
        final /* synthetic */ List a;

        d(List list) {
            this.a = list;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (MLAsrRecognizer.this.mLanguageCallback != null) {
                MLAsrRecognizer.this.mLanguageCallback.onResult(this.a);
            }
        }
    }

    private MLAsrRecognizer(Context context) {
        this.mContext = context;
        SmartLogger.d(TAG, "Context is not null");
        SmartLogger.d(TAG, "mContext:" + context.toString());
    }

    public static MLAsrRecognizer createAsrRecognizer(Context context) {
        if (context != null) {
            return new MLAsrRecognizer(context);
        }
        throw new IllegalArgumentException("Context cannot be null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(int i, String str) {
        this.mHandler.post(new c(i, str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onResult(List<String> list) {
        this.mHandler.post(new d(list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int translateErrorCode(int i) {
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

    public void destroy() {
        AsrEngine asrEngine = this.mAsrEngine;
        if (asrEngine != null) {
            asrEngine.destroy();
            this.mAsrEngine = null;
        }
        this.mHasStartedRecognized = false;
    }

    public void getLanguages(LanguageCallback languageCallback) {
        this.mLanguageCallback = languageCallback;
        ArrayList arrayList = new ArrayList();
        long jCurrentTimeMillis = System.currentTimeMillis();
        HttpUtils.getInstance().requestSupportLanguages(HttpUtils.getInstance().getOkHttpClient(), AsrConstants.AsrGrs.ASR_SHORT_QUERY_LANGUAGE_URL, new b(arrayList, jCurrentTimeMillis));
    }

    public void setAsrListener(MLAsrListener mLAsrListener) {
        this.mAsrListener = mLAsrListener;
    }

    public void startRecognizing(Intent intent) {
        Bundle extras;
        if (intent == null) {
            throw new IllegalArgumentException("intent must not be null");
        }
        if (this.mHasStartedRecognized) {
            SmartLogger.w(TAG, "AsrRecognizer is processing now");
            return;
        }
        this.mHasStartedRecognized = true;
        SmartLogger.i(TAG, "start Recognizing");
        try {
            extras = intent.getExtras();
        } catch (RuntimeException e) {
            SmartLogger.e(TAG, "RuntimeException e = " + e.getMessage());
            extras = null;
        }
        this.mConfig = new AsrEngineConfig().load(extras);
        AsrEngine asrEngine = new AsrEngine(this.mConfig, new a());
        this.mAsrEngine = asrEngine;
        asrEngine.create();
    }
}
