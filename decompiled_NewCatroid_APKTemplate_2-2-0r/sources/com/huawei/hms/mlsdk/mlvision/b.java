package com.huawei.hms.mlsdk.mlvision;

import android.os.IBinder;
import android.os.IInterface;
import com.huawei.hms.framework.common.ExceptionCode;
import com.huawei.hms.ml.common.face.IRemoteFaceCreator;
import com.huawei.hms.mlsdk.dynamic.AbstractInitializer;

/* JADX INFO: compiled from: RemoteFaceInitializer.java */
/* JADX INFO: loaded from: classes10.dex */
public class b extends AbstractInitializer {

    /* JADX INFO: renamed from: com.huawei.hms.mlsdk.mlvision.b$b, reason: collision with other inner class name */
    /* JADX INFO: compiled from: RemoteFaceInitializer.java */
    private static final class C0120b {
        static final b a = new b(null);
    }

    /* synthetic */ b(a aVar) {
    }

    public static b a() {
        return C0120b.a;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    protected IInterface generateDynamicDelegateBridge(IBinder iBinder) throws Exception {
        return IRemoteFaceCreator.Stub.asInterface(iBinder).newRemoteFaceDetectorDelegate();
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    public String getCreatorClass() {
        return "com.huawei.hms.mlkit.face.FaceCreator";
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
        return "huawei_module_mlkit_face";
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer, com.huawei.hms.mlsdk.dynamic.IInitializer
    public void release() {
    }
}
