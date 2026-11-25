package com.huawei.hms.mlsdk.langdetect.p;

import android.os.IBinder;
import android.os.IInterface;
import com.huawei.hms.framework.common.ExceptionCode;
import com.huawei.hms.ml.language.common.langdetect.IRemoteLangDetectCreator;
import com.huawei.hms.mlsdk.dynamic.AbstractInitializer;

/* JADX INFO: compiled from: RemoteLangDetectInitializer.java */
/* JADX INFO: loaded from: classes10.dex */
public class e extends AbstractInitializer {

    /* JADX INFO: compiled from: RemoteLangDetectInitializer.java */
    private static final class b {
        static final e a = new e(null);
    }

    /* synthetic */ e(a aVar) {
    }

    public static e a() {
        return b.a;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    protected IInterface generateDynamicDelegateBridge(IBinder iBinder) throws Exception {
        return IRemoteLangDetectCreator.Stub.asInterface(iBinder).newRemoteLangDetectDelegate();
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    protected String getCreatorClass() {
        return "com.huawei.hms.mlkit.langdetect.Creator";
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer, com.huawei.hms.mlsdk.dynamic.IInitializer
    public synchronized IInterface getDynamicDelegate() {
        return super.getDynamicDelegate();
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    protected int getMinApkVersion() {
        return ExceptionCode.UNABLE_TO_RESOLVE_HOST;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    protected String getModuleName() {
        return "huawei_module_mlkit_langdetect";
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer, com.huawei.hms.mlsdk.dynamic.IInitializer
    public void release() {
    }
}
