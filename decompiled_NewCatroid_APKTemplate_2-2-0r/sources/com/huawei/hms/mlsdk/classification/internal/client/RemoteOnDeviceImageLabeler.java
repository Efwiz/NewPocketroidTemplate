package com.huawei.hms.mlsdk.classification.internal.client;

import android.content.Context;
import android.os.Bundle;
import android.os.IInterface;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.ml.common.label.IRemoteImageLabelerDelegate;
import com.huawei.hms.ml.common.label.ImageLabelParcel;
import com.huawei.hms.ml.common.label.ImageLabelerFrameParcel;
import com.huawei.hms.ml.common.label.ImageLabelerOptionsParcel;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.internal.client.adapter.AvailableAdapterManager;
import com.huawei.hms.mlsdk.mlvision.d;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class RemoteOnDeviceImageLabeler {
    private static final int DEFAULT_ERROR_CODE = -1;
    private static final String TAG = "remoteOnDeviceImgLabel";
    private boolean isInitialed;

    private static final class Holder {
        static final RemoteOnDeviceImageLabeler INSTANCE = new RemoteOnDeviceImageLabeler();

        private Holder() {
        }
    }

    public static RemoteOnDeviceImageLabeler getInstance() {
        return Holder.INSTANCE;
    }

    private void notifyDownloadIfNeeded(Context context) {
        AvailableAdapterManager.getInstance().notifyDownloadIfNeeded(context, d.a());
    }

    public synchronized void destroy() {
        IInterface dynamicDelegate = d.a().getDynamicDelegate();
        if (dynamicDelegate == null) {
            return;
        }
        try {
            ((IRemoteImageLabelerDelegate) dynamicDelegate).destroy();
        } catch (Exception e) {
            SmartLog.e(TAG, "destroy Exception e: " + e);
        }
    }

    public synchronized List<ImageLabelParcel> detectWithBitmap(Context context, Bundle bundle, ImageLabelerFrameParcel imageLabelerFrameParcel, ImageLabelerOptionsParcel imageLabelerOptionsParcel) {
        ArrayList arrayList = new ArrayList();
        if (!isAvailable(context)) {
            return arrayList;
        }
        if (!this.isInitialed && initialize(context, imageLabelerOptionsParcel) >= 0) {
            this.isInitialed = true;
        }
        if (!this.isInitialed) {
            return arrayList;
        }
        IInterface dynamicDelegate = d.a().getDynamicDelegate();
        if (dynamicDelegate == null) {
            return arrayList;
        }
        try {
            return ((IRemoteImageLabelerDelegate) dynamicDelegate).detect(bundle, imageLabelerFrameParcel, imageLabelerOptionsParcel);
        } catch (Exception e) {
            SmartLog.e(TAG, "detect Exception e: " + e);
            return arrayList;
        }
    }

    public synchronized int initialize(Context context, ImageLabelerOptionsParcel imageLabelerOptionsParcel) {
        d dVarA = d.a();
        IInterface dynamicDelegate = dVarA.getDynamicDelegate();
        if (dynamicDelegate == null) {
            return -1;
        }
        try {
            int iInitialize = ((IRemoteImageLabelerDelegate) dynamicDelegate).initialize(ObjectWrapper.wrap(dVarA.getDynamicContext()), imageLabelerOptionsParcel);
            if (iInitialize == 0) {
                this.isInitialed = true;
            }
            return iInitialize;
        } catch (Exception e) {
            SmartLog.e(TAG, "initialize Exception e: " + e);
            return -1;
        }
    }

    public boolean isAvailable(Context context) {
        return AvailableAdapterManager.getInstance().isAvailable(context, d.a());
    }

    public synchronized void prepare(Context context) {
        d.a().initial(context);
        notifyDownloadIfNeeded(context);
    }

    public synchronized void release(Context context) {
        if (this.isInitialed) {
            destroy();
            this.isInitialed = false;
        }
        AvailableAdapterManager.getInstance().release(context);
        d.a().release();
    }

    private RemoteOnDeviceImageLabeler() {
        this.isInitialed = false;
    }
}
