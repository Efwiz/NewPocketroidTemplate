package com.huawei.hms.mlsdk.skeleton;

import android.content.Context;
import android.os.Bundle;
import android.os.IInterface;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlkit.skeleton.common.IRemoteSkeletonDelegate;
import com.huawei.hms.mlkit.skeleton.common.SkeletonFrameParcel;
import com.huawei.hms.mlkit.skeleton.common.SkeletonOptionsParcel;
import com.huawei.hms.mlkit.skeleton.common.SkeletonParcel;
import com.huawei.hms.mlsdk.internal.client.adapter.AvailableAdapterManager;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: RemoteOnDeviceSkeleton.java */
/* JADX INFO: loaded from: classes10.dex */
public class a {
    public boolean a = false;

    /* JADX INFO: compiled from: RemoteOnDeviceSkeleton.java */
    public static final class b {
        public static final a a = new a(null);
    }

    public /* synthetic */ a(C0121a c0121a) {
    }

    public synchronized int a(SkeletonOptionsParcel skeletonOptionsParcel) {
        if (this.a) {
            return 0;
        }
        com.huawei.hms.mlsdk.skeleton.b bVarA = com.huawei.hms.mlsdk.skeleton.b.a();
        IInterface dynamicDelegate = bVarA.getDynamicDelegate();
        if (dynamicDelegate == null) {
            return -1;
        }
        try {
            int iInitialize = dynamicDelegate instanceof IRemoteSkeletonDelegate ? ((IRemoteSkeletonDelegate) dynamicDelegate).initialize(ObjectWrapper.wrap(bVarA.getDynamicContext()), skeletonOptionsParcel) : -1;
            if (iInitialize < 0) {
                return -1;
            }
            this.a = true;
            return iInitialize;
        } catch (Exception e) {
            SmartLog.e("remoteOnDeviceSkeleton", "initialize Exception e: " + e);
            return -1;
        }
    }

    public synchronized void b(Context context) {
        if (this.a) {
            a();
            this.a = false;
        }
        AvailableAdapterManager.getInstance().release(context);
        com.huawei.hms.mlsdk.skeleton.b.a().release();
    }

    public synchronized void a() {
        IInterface dynamicDelegate = com.huawei.hms.mlsdk.skeleton.b.a().getDynamicDelegate();
        if (dynamicDelegate == null) {
            return;
        }
        try {
            if (dynamicDelegate instanceof IRemoteSkeletonDelegate) {
                ((IRemoteSkeletonDelegate) dynamicDelegate).destroy();
            }
        } catch (Exception e) {
            SmartLog.e("remoteOnDeviceSkeleton", "destroy Exception e: " + e);
        }
    }

    public synchronized void a(Context context) {
        com.huawei.hms.mlsdk.skeleton.b.a().initial(context);
        AvailableAdapterManager.getInstance().notifyDownloadIfNeeded(context, com.huawei.hms.mlsdk.skeleton.b.a());
    }

    public synchronized List<SkeletonParcel> a(Context context, Bundle bundle, SkeletonFrameParcel skeletonFrameParcel, SkeletonOptionsParcel skeletonOptionsParcel) {
        if (!AvailableAdapterManager.getInstance().isAvailable(context, com.huawei.hms.mlsdk.skeleton.b.a())) {
            return new ArrayList();
        }
        if (!this.a && a(skeletonOptionsParcel) >= 0) {
            this.a = true;
        }
        if (!this.a) {
            return new ArrayList();
        }
        IInterface dynamicDelegate = com.huawei.hms.mlsdk.skeleton.b.C0122b.a.getDynamicDelegate();
        if (dynamicDelegate == null) {
            return new ArrayList();
        }
        try {
            if (dynamicDelegate instanceof IRemoteSkeletonDelegate) {
                return ((IRemoteSkeletonDelegate) dynamicDelegate).detect(bundle, skeletonFrameParcel, skeletonOptionsParcel);
            }
        } catch (Exception e) {
            SmartLog.e("remoteOnDeviceSkeleton", "detect Exception e: " + e);
        }
        return new ArrayList();
    }

    public synchronized float a(Context context, List<SkeletonParcel> list, List<SkeletonParcel> list2, SkeletonOptionsParcel skeletonOptionsParcel) {
        try {
            if (!AvailableAdapterManager.getInstance().isAvailable(context, com.huawei.hms.mlsdk.skeleton.b.a())) {
                return 0.0f;
            }
            if (!this.a && a(skeletonOptionsParcel) >= 0) {
                this.a = true;
            }
            if (!this.a) {
                return 0.0f;
            }
            IInterface dynamicDelegate = com.huawei.hms.mlsdk.skeleton.b.C0122b.a.getDynamicDelegate();
            if (dynamicDelegate == null) {
                return 0.0f;
            }
            try {
                if (dynamicDelegate instanceof IRemoteSkeletonDelegate) {
                    return ((IRemoteSkeletonDelegate) dynamicDelegate).calcSimilarity(list, list2, skeletonOptionsParcel);
                }
            } catch (Exception e) {
                SmartLog.e("remoteOnDeviceSkeleton", "detect Exception e: " + e);
            }
            return 0.0f;
        } catch (Throwable th) {
            throw th;
        }
    }
}
