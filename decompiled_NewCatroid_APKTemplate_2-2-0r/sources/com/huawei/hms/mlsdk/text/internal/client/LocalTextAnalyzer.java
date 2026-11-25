package com.huawei.hms.mlsdk.text.internal.client;

import android.content.Context;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.ml.common.ocr.TextDetectorOptionsParcel;
import com.huawei.hms.ml.common.ocr.TextParcel;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.text.MLLocalTextSetting;
import com.huawei.hms.mlsdk.text.MLText;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes10.dex */
public class LocalTextAnalyzer {
    private static String TAG = "LocalTextAnalyzer";
    private static Map<String, LocalTextAnalyzer> analyzerMap = new HashMap();
    private MLApplication hmsMLApp;
    private MLLocalTextSetting setting;

    public LocalTextAnalyzer(MLApplication mLApplication, MLLocalTextSetting mLLocalTextSetting) {
        this.hmsMLApp = mLApplication;
        if (mLLocalTextSetting == null) {
            this.setting = new MLLocalTextSetting.Factory().create();
        } else {
            this.setting = mLLocalTextSetting;
        }
    }

    public static synchronized LocalTextAnalyzer getInstance(MLApplication mLApplication, MLLocalTextSetting mLLocalTextSetting) {
        LocalTextAnalyzer localTextAnalyzer;
        if (mLApplication != null) {
            if (mLApplication.getUniqueKey() != null) {
                localTextAnalyzer = analyzerMap.get(mLApplication.getUniqueKey());
                if (localTextAnalyzer == null) {
                    localTextAnalyzer = new LocalTextAnalyzer(mLApplication, mLLocalTextSetting);
                    analyzerMap.put(mLApplication.getUniqueKey(), localTextAnalyzer);
                }
                localTextAnalyzer.setSetting(mLLocalTextSetting);
                localTextAnalyzer.prepare(mLApplication.getAppContext());
                try {
                    TextAnalyzer.getInstance().initial(mLApplication.getAppContext(), new TextDetectorOptionsParcel(mLLocalTextSetting.getLanguage(), mLLocalTextSetting.getOCRMode(), mLApplication.toBundle()));
                } catch (Exception unused) {
                    SmartLog.e(TAG, "Initial failed.");
                }
            }
        }
        throw new NullPointerException();
        return localTextAnalyzer;
    }

    private void prepare(Context context) {
        TextAnalyzer.getInstance().prepare(context);
    }

    private void setSetting(MLLocalTextSetting mLLocalTextSetting) {
        this.setting = mLLocalTextSetting;
    }

    public boolean isAvailable() {
        return TextAnalyzer.getInstance().isAvailable(this.hmsMLApp.getAppContext());
    }

    public Task<MLText> processImage(final MLFrame mLFrame) {
        return Tasks.callInBackground(new Callable<MLText>() { // from class: com.huawei.hms.mlsdk.text.internal.client.LocalTextAnalyzer.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public MLText call() throws Exception {
                if (!LocalTextAnalyzer.this.isAvailable()) {
                    return new MLText();
                }
                TextParcel textParcelRecognizeWithBitmap = TextAnalyzer.getInstance().recognizeWithBitmap(LocalTextAnalyzer.this.hmsMLApp.getAppContext(), LocalTextAnalyzer.this.hmsMLApp.toBundle(), TextUtils.convert(mLFrame), new TextDetectorOptionsParcel(LocalTextAnalyzer.this.setting.getLanguage(), LocalTextAnalyzer.this.setting.getOCRMode(), LocalTextAnalyzer.this.hmsMLApp.toBundle()));
                return (textParcelRecognizeWithBitmap == null || textParcelRecognizeWithBitmap.getBlocks() == null || textParcelRecognizeWithBitmap.getBlocks().size() == 0) ? new MLText() : new MLText(textParcelRecognizeWithBitmap.getText(), TextUtils.textParcelToBlock(textParcelRecognizeWithBitmap));
            }
        });
    }

    public void release() {
        TextAnalyzer.getInstance().release(this.hmsMLApp.getAppContext());
    }
}
