package com.huawei.hms.mlsdk.text;

import android.content.Context;
import android.util.SparseArray;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.text.internal.client.CloudTextAnalyzer;
import com.huawei.hms.mlsdk.text.internal.client.LocalTextAnalyzer;
import com.huawei.hms.mlsdk.text.internal.client.NativeTextAnalyzer;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes10.dex */
public class MLTextAnalyzer extends MLAnalyzer<MLText.Block> implements Closeable {
    public static final int OCR_LOCAL_TYPE = 0;
    public static final int OCR_REMOTE_TYPE = 1;
    private final int analyseType;
    private final CloudTextAnalyzer cloudTextAnalyzer;
    private LocalTextAnalyzer localTextAnalyzer;
    private NativeTextAnalyzer nativeTextAnalyzer;
    private static Map<LocalTextAnalyzer, MLTextAnalyzer> localTextRecognizer = new HashMap();
    private static Map<CloudTextAnalyzer, MLTextAnalyzer> cloudTextRecognizer = new HashMap();

    public static class Factory {
        private MLLocalTextSetting.Factory localTextSettingFactory = new MLLocalTextSetting.Factory();
        private Context mContext;

        public Factory(Context context) {
            this.mContext = context;
        }

        public MLTextAnalyzer create() {
            return new MLTextAnalyzer(new NativeTextAnalyzer(this.mContext, this.localTextSettingFactory.create()));
        }

        public Factory setLanguage(String str) {
            this.localTextSettingFactory.setLanguage(str);
            return this;
        }

        public Factory setLocalOCRMode(int i) {
            this.localTextSettingFactory.setOCRMode(i);
            return this;
        }
    }

    private static class HmsMLVisionTextTask implements Callable<MLText> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public MLText call() throws Exception {
            return new MLText();
        }
    }

    public static synchronized MLTextAnalyzer create(MLApplication mLApplication, MLLocalTextSetting mLLocalTextSetting, MLRemoteTextSetting mLRemoteTextSetting, boolean z) {
        if (!z && mLRemoteTextSetting == null) {
            try {
                mLRemoteTextSetting = new MLRemoteTextSetting.Factory().create();
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z && mLLocalTextSetting == null) {
            mLLocalTextSetting = new MLLocalTextSetting.Factory().create();
        }
        if (!z) {
            CloudTextAnalyzer cloudTextAnalyzerCreate = CloudTextAnalyzer.create(mLApplication, mLRemoteTextSetting);
            MLTextAnalyzer mLTextAnalyzer = cloudTextRecognizer.get(cloudTextAnalyzerCreate);
            if (mLTextAnalyzer == null) {
                mLTextAnalyzer = new MLTextAnalyzer(null, cloudTextAnalyzerCreate, 1);
                cloudTextRecognizer.put(cloudTextAnalyzerCreate, mLTextAnalyzer);
            }
            return mLTextAnalyzer;
        }
        LocalTextAnalyzer localTextAnalyzer = LocalTextAnalyzer.getInstance(mLApplication, mLLocalTextSetting);
        MLTextAnalyzer mLTextAnalyzer2 = localTextRecognizer.get(localTextAnalyzer);
        if (mLTextAnalyzer2 == null) {
            mLTextAnalyzer2 = new MLTextAnalyzer(localTextAnalyzer, null, 0);
            localTextRecognizer.put(localTextAnalyzer, mLTextAnalyzer2);
        }
        if (mLTextAnalyzer2.localTextAnalyzer == null) {
            mLTextAnalyzer2.localTextAnalyzer = localTextAnalyzer;
        }
        return mLTextAnalyzer2;
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public SparseArray<MLText.Block> analyseFrame(MLFrame mLFrame) {
        if (mLFrame == null) {
            throw new IllegalArgumentException("No frame supplied.");
        }
        mLFrame.initialize();
        return this.nativeTextAnalyzer.analyse(mLFrame.getFrame(false, true));
    }

    public Task<MLText> asyncAnalyseFrame(MLFrame mLFrame) {
        if (this.localTextAnalyzer == null && this.cloudTextAnalyzer == null) {
            return Tasks.call(new HmsMLVisionTextTask());
        }
        mLFrame.initialize();
        MLFrame frame = mLFrame.getFrame(false, true);
        LocalTextAnalyzer localTextAnalyzer = this.localTextAnalyzer;
        return localTextAnalyzer != null ? localTextAnalyzer.processImage(frame) : this.cloudTextAnalyzer.processImage(frame);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        release();
    }

    public int getAnalyseType() {
        return this.analyseType;
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public boolean isAvailable() {
        NativeTextAnalyzer nativeTextAnalyzer = this.nativeTextAnalyzer;
        if (nativeTextAnalyzer != null) {
            return nativeTextAnalyzer.isAvailable();
        }
        LocalTextAnalyzer localTextAnalyzer = this.localTextAnalyzer;
        if (localTextAnalyzer != null) {
            return localTextAnalyzer.isAvailable();
        }
        return false;
    }

    public void release() {
        super.destroy();
        NativeTextAnalyzer nativeTextAnalyzer = this.nativeTextAnalyzer;
        if (nativeTextAnalyzer != null) {
            nativeTextAnalyzer.release();
            this.nativeTextAnalyzer = null;
        }
        LocalTextAnalyzer localTextAnalyzer = this.localTextAnalyzer;
        if (localTextAnalyzer != null) {
            localTextAnalyzer.release();
            this.localTextAnalyzer = null;
        }
    }

    public void stop() throws IOException {
        release();
    }

    private MLTextAnalyzer(LocalTextAnalyzer localTextAnalyzer, CloudTextAnalyzer cloudTextAnalyzer, int i) {
        this.analyseType = i;
        this.localTextAnalyzer = localTextAnalyzer;
        this.cloudTextAnalyzer = cloudTextAnalyzer;
        this.nativeTextAnalyzer = null;
    }

    private MLTextAnalyzer(NativeTextAnalyzer nativeTextAnalyzer) {
        this.nativeTextAnalyzer = nativeTextAnalyzer;
        this.localTextAnalyzer = null;
        this.cloudTextAnalyzer = null;
        this.analyseType = 0;
    }
}
