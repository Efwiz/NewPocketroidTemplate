package com.huawei.hms.mlsdk.tts.client;

import android.os.IBinder;
import android.os.IInterface;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlkit.tts.common.IRemoteTtsCreator;
import com.huawei.hms.mlsdk.dynamic.AbstractInitializer;

/* JADX INFO: compiled from: RemoteTtsInitializer.java */
/* JADX INFO: loaded from: classes10.dex */
public class d extends AbstractInitializer {
    private String a;
    private String b;
    private int c;

    public d(ModuleInfo moduleInfo) {
        this.a = moduleInfo.getModuleName();
        this.b = moduleInfo.getCreatorName();
        this.c = moduleInfo.getMinApkVersion();
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    protected IInterface generateDynamicDelegateBridge(IBinder iBinder) throws Exception {
        SmartLog.i("TTS_SDK_RemoteTtsInitializer", "generateDynamicDelegateBridge|Enter!");
        return IRemoteTtsCreator.Stub.asInterface(iBinder).newRemoteTtsDelegate();
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    public String getCreatorClass() {
        return this.b;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer, com.huawei.hms.mlsdk.dynamic.IInitializer
    public synchronized IInterface getDynamicDelegate() {
        SmartLog.i("TTS_SDK_RemoteTtsInitializer", "getDynamicDelegate|Enter!");
        return super.getDynamicDelegate();
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    protected int getMinApkVersion() {
        return this.c;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    protected String getModuleName() {
        return this.a;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer, com.huawei.hms.mlsdk.dynamic.IInitializer
    public void release() {
        SmartLog.i("TTS_SDK_RemoteTtsInitializer", "release|Enter!");
    }
}
