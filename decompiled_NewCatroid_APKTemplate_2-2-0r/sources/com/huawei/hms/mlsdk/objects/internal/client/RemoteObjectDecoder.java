package com.huawei.hms.mlsdk.objects.internal.client;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IInterface;
import com.huawei.hms.common.Preconditions;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.ml.common.object.IRemoteObjectDecoderDelegate;
import com.huawei.hms.ml.common.object.ObjectDetectorFrameParcel;
import com.huawei.hms.ml.common.object.ObjectDetectorOptionsParcel;
import com.huawei.hms.ml.common.object.ObjectDetectorParcel;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.internal.client.adapter.AvailableAdapterManager;
import com.huawei.hms.mlsdk.mlvision.e;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class RemoteObjectDecoder {
    public static final String TAG = "RemoteObjectDecoder";
    private static volatile Object lock = new Object();
    private boolean initialed;

    private static final class Holder {
        static final RemoteObjectDecoder INSTANCE = new RemoteObjectDecoder();

        private Holder() {
        }
    }

    private static ObjectDetectorFrameParcel convert(MLFrame mLFrame, ObjectDetectorOptionsParcel objectDetectorOptionsParcel) {
        Bitmap bitmap = mLFrame.readBitmap();
        ByteBuffer byteBuffer = mLFrame.getByteBuffer();
        Preconditions.checkState((bitmap == null && byteBuffer == null) ? false : true, "bitmap and byteBuffer can't be empty at the same time");
        MLFrame.Property propertyAcquireProperty = mLFrame.acquireProperty();
        return new ObjectDetectorFrameParcel.Builder().setBytes(byteBuffer == null ? null : byteBuffer.array()).setBitmap(bitmap).setWidth(propertyAcquireProperty.getWidth()).setHeight(propertyAcquireProperty.getHeight()).setRotation(propertyAcquireProperty.getQuadrant()).setFormat(propertyAcquireProperty.getFormatType()).build();
    }

    public static RemoteObjectDecoder getInstance() {
        return Holder.INSTANCE;
    }

    private boolean isAvailable(Context context) {
        return AvailableAdapterManager.getInstance().isAvailable(context, e.a());
    }

    private void notifyDownloadIfNeeded(Context context) {
        AvailableAdapterManager.getInstance().notifyDownloadIfNeeded(context, e.a());
    }

    public synchronized int destroy(Context context) {
        try {
            IInterface dynamicDelegate = e.a().getDynamicDelegate();
            if (dynamicDelegate == null) {
                return -1;
            }
            try {
                return ((IRemoteObjectDecoderDelegate) dynamicDelegate).destroy();
            } catch (Exception e) {
                SmartLog.d(TAG, "destroy Exception e: " + e);
                return -1;
            } catch (Throwable th) {
                SmartLog.d(TAG, "destroy Throwable e: " + th);
                return -1;
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public synchronized List<ObjectDetectorParcel> detect(Context context, Bundle bundle, MLFrame mLFrame, ObjectDetectorOptionsParcel objectDetectorOptionsParcel) {
        ArrayList arrayList = new ArrayList();
        if (!isAvailable(context)) {
            return arrayList;
        }
        if (!this.initialed && initial(context, objectDetectorOptionsParcel) >= 0) {
            this.initialed = true;
        }
        if (!this.initialed) {
            return arrayList;
        }
        IInterface dynamicDelegate = e.a().getDynamicDelegate();
        if (dynamicDelegate == null) {
            return arrayList;
        }
        try {
            return ((IRemoteObjectDecoderDelegate) dynamicDelegate).detect(bundle, convert(mLFrame, objectDetectorOptionsParcel), objectDetectorOptionsParcel);
        } catch (Exception e) {
            SmartLog.d(TAG, "detect Exception  e: " + e);
            return arrayList;
        }
    }

    public synchronized int initial(Context context, ObjectDetectorOptionsParcel objectDetectorOptionsParcel) {
        try {
            e eVarA = e.a();
            IInterface dynamicDelegate = eVarA.getDynamicDelegate();
            if (dynamicDelegate == null) {
                return -1;
            }
            try {
                try {
                    int iInitial = ((IRemoteObjectDecoderDelegate) dynamicDelegate).initial(ObjectWrapper.wrap(eVarA.getDynamicContext()), objectDetectorOptionsParcel);
                    if (iInitial == 0) {
                        this.initialed = true;
                    }
                    return iInitial;
                } catch (Exception e) {
                    SmartLog.d(TAG, "initial Exception e: " + e);
                    return -1;
                }
            } catch (Throwable th) {
                SmartLog.d(TAG, "initial Throwable e: " + th);
                return -1;
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public synchronized void prepare(Context context) {
        e.a().initial(context);
        notifyDownloadIfNeeded(context);
    }

    public synchronized void release(Context context) {
        if (this.initialed) {
            destroy(context);
            this.initialed = false;
        }
        AvailableAdapterManager.getInstance().release(context);
        e.a().release();
    }

    private RemoteObjectDecoder() {
        this.initialed = false;
    }
}
