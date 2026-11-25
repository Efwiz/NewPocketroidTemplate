package com.huawei.hms.mlsdk.imgseg.client;

import android.content.Context;
import android.os.Bundle;
import android.os.IInterface;
import com.huawei.hms.common.Preconditions;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.ml.common.imgseg.IRemoteImageSegmentationDecoderDelegate;
import com.huawei.hms.ml.common.imgseg.ImageSegmentationDetectorFrameParcel;
import com.huawei.hms.ml.common.imgseg.ImageSegmentationDetectorParcel;
import com.huawei.hms.ml.common.imgseg.ImageSegmentationOptionsParcel;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.dynamic.IInitializer;
import com.huawei.hms.mlsdk.internal.client.adapter.AvailableAdapterManager;
import com.huawei.hms.mlsdk.mlvision.c;

/* JADX INFO: loaded from: classes10.dex */
public class RemoteImageSegmentationDecoder {
    public static final String TAG = "RemoteImageSegmentationDecoder";
    private static volatile Object lock = new Object();
    private boolean initialed;

    private static final class Holder {
        static final RemoteImageSegmentationDecoder INSTANCE = new RemoteImageSegmentationDecoder();

        private Holder() {
        }
    }

    private static ImageSegmentationDetectorFrameParcel convert(MLFrame mLFrame) {
        Preconditions.checkState((mLFrame.readBitmap() == null && mLFrame.getByteBuffer() == null) ? false : true, "bitmap and byteBuffer can't be empty at the same time");
        return new ImageSegmentationDetectorFrameParcel(mLFrame.getByteBuffer() == null ? null : mLFrame.getByteBuffer().array(), mLFrame.acquireProperty().getWidth(), mLFrame.acquireProperty().getHeight(), mLFrame.acquireProperty().getQuadrant(), mLFrame.acquireProperty().getFormatType(), mLFrame.acquireProperty().getWidth(), mLFrame.acquireProperty().getHeight(), mLFrame.readBitmap(), mLFrame.acquireProperty().getItemIdentity());
    }

    public static RemoteImageSegmentationDecoder getInstance() {
        return Holder.INSTANCE;
    }

    private boolean isAvailable(Context context) {
        AvailableAdapterManager availableAdapterManager = AvailableAdapterManager.getInstance();
        if (availableAdapterManager != null) {
            availableAdapterManager.isAvailable(context, c.a());
        }
        return AvailableAdapterManager.getInstance().isAvailable(context, c.a());
    }

    private void notifyDownloadIfNeeded(Context context) {
        AvailableAdapterManager.getInstance().notifyDownloadIfNeeded(context, c.a());
    }

    public synchronized int destroy(Context context) {
        try {
            IInterface dynamicDelegate = c.a().getDynamicDelegate();
            if (dynamicDelegate == null) {
                return -1;
            }
            try {
                return ((IRemoteImageSegmentationDecoderDelegate) dynamicDelegate).destroy();
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

    public synchronized ImageSegmentationDetectorParcel detect(Context context, Bundle bundle, MLFrame mLFrame, ImageSegmentationOptionsParcel imageSegmentationOptionsParcel) {
        if (!isAvailable(context)) {
            return new ImageSegmentationDetectorParcel();
        }
        c cVarA = c.a();
        IInterface dynamicDelegate = cVarA.getDynamicDelegate();
        if (!this.initialed && initial(imageSegmentationOptionsParcel, cVarA, dynamicDelegate) >= 0) {
            this.initialed = true;
        }
        if (!this.initialed) {
            return new ImageSegmentationDetectorParcel();
        }
        if (dynamicDelegate == null) {
            return new ImageSegmentationDetectorParcel();
        }
        try {
            return ((IRemoteImageSegmentationDecoderDelegate) dynamicDelegate).detect(bundle, convert(mLFrame), imageSegmentationOptionsParcel);
        } catch (Exception e) {
            SmartLog.d(TAG, "detect Exception  e: " + e);
            return new ImageSegmentationDetectorParcel();
        }
    }

    public synchronized int initial(ImageSegmentationOptionsParcel imageSegmentationOptionsParcel, IInitializer iInitializer, IInterface iInterface) {
        if (iInterface == null) {
            return -1;
        }
        try {
            if (this.initialed) {
                return 0;
            }
            try {
                int iInitial = ((IRemoteImageSegmentationDecoderDelegate) iInterface).initial(ObjectWrapper.wrap(iInitializer.getDynamicContext()), imageSegmentationOptionsParcel);
                if (iInitial == 0) {
                    this.initialed = true;
                }
                return iInitial;
            } catch (Exception e) {
                SmartLog.d(TAG, "initial Exception e: " + e);
                return -1;
            } catch (Throwable th) {
                SmartLog.d(TAG, "initial Throwable e: " + th);
                return -1;
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public synchronized void prepare(Context context) {
        c.a().initial(context);
        notifyDownloadIfNeeded(context);
    }

    public synchronized void release(Context context) {
        if (this.initialed) {
            destroy(context);
            this.initialed = false;
        }
        AvailableAdapterManager.getInstance().release(context);
        c.a().release();
    }

    private RemoteImageSegmentationDecoder() {
        this.initialed = false;
    }
}
