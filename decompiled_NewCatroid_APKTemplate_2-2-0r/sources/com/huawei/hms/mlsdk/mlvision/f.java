package com.huawei.hms.mlsdk.mlvision;

import android.os.IBinder;
import android.os.IInterface;
import com.huawei.hms.framework.common.ExceptionCode;
import com.huawei.hms.ml.common.ocr.IRemoteTextCreator;
import com.huawei.hms.mlsdk.dynamic.AbstractInitializer;

/* JADX INFO: compiled from: RemoteTextInitializer.java */
/* JADX INFO: loaded from: classes10.dex */
public class f extends AbstractInitializer {

    /* JADX INFO: compiled from: RemoteTextInitializer.java */
    private static final class b {
        static f a = new f(null);
    }

    /* synthetic */ f(a aVar) {
    }

    public static f a() {
        return b.a;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    protected IInterface generateDynamicDelegateBridge(IBinder iBinder) throws Exception {
        return IRemoteTextCreator.Stub.asInterface(iBinder).newRemoteTextRecognizerDelegate();
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    public String getCreatorClass() {
        return "com.huawei.hms.mlkit.ocr.Creator";
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
        return "huawei_module_mlkit_ocr";
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer, com.huawei.hms.mlsdk.dynamic.IInitializer
    public void release() {
    }
}
