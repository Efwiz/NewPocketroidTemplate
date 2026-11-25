package com.huawei.hms.mlsdk.mlvision;

import android.os.IBinder;
import android.os.IInterface;
import com.huawei.hms.framework.common.ExceptionCode;
import com.huawei.hms.ml.common.imgseg.IRemoteImageSegmentationCreator;
import com.huawei.hms.mlsdk.dynamic.AbstractInitializer;

/* JADX INFO: compiled from: RemoteImageSegmentationInitializer.java */
/* JADX INFO: loaded from: classes10.dex */
public class c extends AbstractInitializer {

    /* JADX INFO: compiled from: RemoteImageSegmentationInitializer.java */
    private static final class b {
        static c a = new c(null);
    }

    /* synthetic */ c(a aVar) {
    }

    public static c a() {
        return b.a;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    protected IInterface generateDynamicDelegateBridge(IBinder iBinder) throws Exception {
        return IRemoteImageSegmentationCreator.Stub.asInterface(iBinder).newRemoteImageSegmentationDecoderDelegate();
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    protected String getCreatorClass() {
        return "com.huawei.hms.mlkit.imgseg.Creator";
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
        return "huawei_module_mlkit_imgseg";
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer, com.huawei.hms.mlsdk.dynamic.IInitializer
    public void release() {
    }
}
