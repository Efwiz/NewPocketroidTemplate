package com.huawei.hms.mlsdk.text.internal.client;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import com.huawei.hms.ml.common.ocr.TextDetectorOptionsParcel;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.text.MLLocalTextSetting;
import com.huawei.hms.mlsdk.text.MLText;

/* JADX INFO: loaded from: classes10.dex */
public class NativeTextAnalyzer {
    private final MLApplication hmsMLApp;
    private final Context mContext;
    private final MLLocalTextSetting settings;

    public NativeTextAnalyzer(Context context, MLLocalTextSetting mLLocalTextSetting) {
        this.mContext = context;
        this.hmsMLApp = MLApplication.initialize(context);
        if (mLLocalTextSetting == null) {
            this.settings = new MLLocalTextSetting.Factory().create();
        } else {
            this.settings = mLLocalTextSetting;
        }
        prepare(context);
    }

    public SparseArray<MLText.Block> analyse(MLFrame mLFrame) {
        if (!isAvailable()) {
            return new SparseArray<>();
        }
        if (mLFrame == null) {
            throw new IllegalArgumentException("No frame supplied.");
        }
        Bundle bundle = this.hmsMLApp.toBundle();
        try {
            return TextUtils.textParcelToBlock(TextAnalyzer.getInstance().recognizeWithBitmap(this.mContext, bundle, TextUtils.convert(mLFrame), new TextDetectorOptionsParcel(this.settings.getLanguage(), this.settings.getOCRMode(), bundle)));
        } catch (Exception unused) {
            return new SparseArray<>();
        }
    }

    public boolean isAvailable() {
        return TextAnalyzer.getInstance().isAvailable(this.hmsMLApp.getAppContext());
    }

    public void prepare(Context context) {
        TextAnalyzer.getInstance().prepare(context);
    }

    public void release() {
        TextAnalyzer.getInstance().release(this.hmsMLApp.getAppContext());
    }
}
