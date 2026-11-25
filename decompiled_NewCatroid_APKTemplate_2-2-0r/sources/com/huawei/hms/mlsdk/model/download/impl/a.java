package com.huawei.hms.mlsdk.model.download.impl;

import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.model.download.MLModelDownloadListener;
import java.io.File;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: compiled from: HttpDownloadListener.java */
/* JADX INFO: loaded from: classes10.dex */
public class a implements com.huawei.hms.mlsdk.model.download.p.f {
    private final MLModelDownloadListener b;
    private final CountDownLatch a = new CountDownLatch(1);
    private File c = null;

    public a(MLModelDownloadListener mLModelDownloadListener) {
        this.b = mLModelDownloadListener;
    }

    public File a() {
        try {
            this.a.await();
        } catch (InterruptedException e) {
            SmartLog.e("MLSDK_MODEL_HttpDownloadListener", com.huawei.hms.mlsdk.model.download.p.a.a("getModelFile InterruptedException: ").append(e.getMessage()).toString());
        }
        return this.c;
    }

    @Override // com.huawei.hms.mlsdk.model.download.p.f
    public void a(long j, long j2) {
        MLModelDownloadListener mLModelDownloadListener = this.b;
        if (mLModelDownloadListener != null) {
            mLModelDownloadListener.onProcess(j, j2);
        }
    }

    @Override // com.huawei.hms.mlsdk.model.download.p.f
    public void a(String str, Exception exc) {
        SmartLog.e("MLSDK_MODEL_HttpDownloadListener", com.huawei.hms.mlsdk.model.download.p.a.a("HttpDownloadListener onFailure: ").append(exc.getMessage()).toString());
        this.a.countDown();
    }

    @Override // com.huawei.hms.mlsdk.model.download.p.f
    public void a(String str, File file) {
        SmartLog.i("MLSDK_MODEL_HttpDownloadListener", "HttpDownloadListener onComplete: modelName" + str);
        this.c = file;
        this.a.countDown();
    }
}
