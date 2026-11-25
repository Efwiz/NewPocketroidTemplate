package com.huawei.hms.mlsdk.skeleton;

import android.os.IBinder;
import android.os.IInterface;
import com.huawei.hms.framework.common.ExceptionCode;
import com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonCreator;
import com.huawei.hms.mlsdk.dynamic.AbstractInitializer;

/* JADX INFO: compiled from: RemoteSkeletonInitializer.java */
/* JADX INFO: loaded from: classes10.dex */
public class b extends AbstractInitializer {

    /* JADX INFO: renamed from: com.huawei.hms.mlsdk.skeleton.b$b, reason: collision with other inner class name */
    /* JADX INFO: compiled from: RemoteSkeletonInitializer.java */
    public static final class C0122b {
        public static final b a = new b(null);
    }

    public /* synthetic */ b(a aVar) {
    }

    public static b a() {
        return C0122b.a;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    public IInterface generateDynamicDelegateBridge(IBinder iBinder) throws Exception {
        return IRemoteSkeletonCreator.Stub.asInterface(iBinder).newRemoteSkeletonDelegate();
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    public String getCreatorClass() {
        return "com.huawei.hms.mlkit.skeleton.SkeletonCreator";
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer, com.huawei.hms.mlsdk.dynamic.IInitializer
    public synchronized IInterface getDynamicDelegate() {
        return super.getDynamicDelegate();
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    public int getMinApkVersion() {
        return ExceptionCode.UNABLE_TO_RESOLVE_HOST;
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer
    public String getModuleName() {
        return "huawei_module_mlkit_skeleton";
    }

    @Override // com.huawei.hms.mlsdk.dynamic.AbstractInitializer, com.huawei.hms.mlsdk.dynamic.IInitializer
    public void release() {
    }
}
