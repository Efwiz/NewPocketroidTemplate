package com.huawei.hms.mlsdk.text.internal.client;

import android.content.Context;
import android.os.Bundle;
import android.os.IInterface;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.ml.common.ocr.IRemoteTextRecognizerDelegate;
import com.huawei.hms.ml.common.ocr.TextDetectorFrameParcel;
import com.huawei.hms.ml.common.ocr.TextDetectorOptionsParcel;
import com.huawei.hms.ml.common.ocr.TextParcel;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.internal.client.adapter.AvailableAdapterManager;
import com.huawei.hms.mlsdk.mlvision.f;

/* JADX INFO: loaded from: classes10.dex */
public class TextAnalyzer {
    private static final String TAG = "TextAnalyzer";
    private boolean initialed;

    private static final class Holder {
        static final TextAnalyzer INSTANCE = new TextAnalyzer();

        private Holder() {
        }
    }

    public static TextAnalyzer getInstance() {
        return Holder.INSTANCE;
    }

    private void notifyDownloadIfNeeded(Context context) {
        AvailableAdapterManager.getInstance().notifyDownloadIfNeeded(context, f.a());
    }

    public synchronized int destroy() {
        IInterface dynamicDelegate = f.a().getDynamicDelegate();
        if (dynamicDelegate == null) {
            return -1;
        }
        try {
            return ((IRemoteTextRecognizerDelegate) dynamicDelegate).unloadModel();
        } catch (Exception e) {
            SmartLog.e(TAG, "destroy Exception e: " + e);
            return -1;
        }
    }

    public synchronized int initial(Context context, TextDetectorOptionsParcel textDetectorOptionsParcel) throws Exception {
        try {
            f fVarA = f.a();
            IInterface dynamicDelegate = fVarA.getDynamicDelegate();
            if (dynamicDelegate == null) {
                return -1;
            }
            try {
                try {
                    int iInitial = ((IRemoteTextRecognizerDelegate) dynamicDelegate).initial(ObjectWrapper.wrap(fVarA.getDynamicContext()), textDetectorOptionsParcel);
                    if (iInitial == 0) {
                        this.initialed = true;
                    }
                    return iInitial;
                } catch (Exception e) {
                    SmartLog.e(TAG, "initial Exception e: " + e);
                    return -1;
                }
            } catch (Throwable th) {
                SmartLog.e(TAG, "initial Throwable e: " + th);
                return -1;
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public boolean isAvailable(Context context) {
        return AvailableAdapterManager.getInstance().isAvailable(context, f.a());
    }

    public synchronized void prepare(Context context) {
        f.a().initial(context);
        notifyDownloadIfNeeded(context);
    }

    public synchronized TextParcel recognizeWithBitmap(Context context, Bundle bundle, TextDetectorFrameParcel textDetectorFrameParcel, TextDetectorOptionsParcel textDetectorOptionsParcel) throws Exception {
        if (!this.initialed && initial(context, textDetectorOptionsParcel) >= 0) {
            this.initialed = true;
        }
        if (!this.initialed) {
            return null;
        }
        IInterface dynamicDelegate = f.a().getDynamicDelegate();
        if (dynamicDelegate == null) {
            return null;
        }
        try {
            return ((IRemoteTextRecognizerDelegate) dynamicDelegate).detect(bundle, textDetectorFrameParcel, textDetectorOptionsParcel);
        } catch (Exception e) {
            SmartLog.e(TAG, "recognizeWithBitmap Exception e: " + e);
            return null;
        }
    }

    public synchronized void release(Context context) {
        if (this.initialed) {
            destroy();
            this.initialed = false;
        }
        AvailableAdapterManager.getInstance().release(context);
        f.a().release();
    }

    private TextAnalyzer() {
        this.initialed = false;
    }
}
