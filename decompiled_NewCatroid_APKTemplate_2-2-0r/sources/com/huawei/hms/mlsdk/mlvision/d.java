package com.huawei.hms.mlsdk.mlvision;

import android.os.IBinder;
import android.os.IInterface;
import com.huawei.hms.framework.common.ExceptionCode;
import com.huawei.hms.ml.common.label.IRemoteLabelCreator;
import com.huawei.hms.mlsdk.dynamic.AbstractInitializer;

/* JADX INFO: compiled from: RemoteLabelInitializer.java */
/* JADX INFO: loaded from: classes10.dex */
public class d extends AbstractInitializer {

    /* JADX INFO: compiled from: RemoteLabelInitializer.java */
    private static final class b {
        static final d a = new d(null);
    }

    /* synthetic */ d(a aVar) {
    }

    public static d a() {
        return b.a;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    protected IInterface generateDynamicDelegateBridge(IBinder iBinder) throws Exception {
        return IRemoteLabelCreator.Stub.asInterface(iBinder).newRemoteImageLabelerDelegate();
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    public String getCreatorClass() {
        return "com.huawei.hms.mlkit.label.LabelCreator";
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
        return "huawei_module_mlkit_label";
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer, com.huawei.hms.mlsdk.dynamic.IInitializer
    public void release() {
    }
}
