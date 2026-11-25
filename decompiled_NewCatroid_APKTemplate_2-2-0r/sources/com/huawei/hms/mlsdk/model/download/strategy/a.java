package com.huawei.hms.mlsdk.model.download.strategy;

import com.huawei.hms.mlsdk.model.download.MLRemoteModel;

/* JADX INFO: compiled from: ModelStrategyFactory.java */
/* JADX INFO: loaded from: classes10.dex */
public class a {
    public ModelConfigManagerStrategy a(MLRemoteModel mLRemoteModel) {
        return mLRemoteModel.getConfigStrategy();
    }

    public ModelDownloadStrategy b(MLRemoteModel mLRemoteModel) {
        return mLRemoteModel.getDownloadStrategy();
    }

    public ModelFileManagerStrategy c(MLRemoteModel mLRemoteModel) {
        return mLRemoteModel.getFileStrategy();
    }
}
