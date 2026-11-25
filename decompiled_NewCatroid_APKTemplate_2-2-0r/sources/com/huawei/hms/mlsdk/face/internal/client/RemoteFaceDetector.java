package com.huawei.hms.mlsdk.face.internal.client;

import android.content.Context;
import android.os.Bundle;
import android.os.IInterface;
import android.os.SystemClock;
import android.util.Log;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.ml.common.face.FaceDetectorOptionsParcel;
import com.huawei.hms.ml.common.face.FaceFrameParcel;
import com.huawei.hms.ml.common.face.FaceParcel;
import com.huawei.hms.ml.common.face.IRemoteFaceDetectorDelegate;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.face.MLFace;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.face.face3d.ML3DFace;
import com.huawei.hms.mlsdk.face.face3d.ML3DFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.internal.client.adapter.AvailableAdapterManager;
import com.huawei.hms.mlsdk.mlvision.a;
import com.huawei.hms.mlsdk.mlvision.b;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class RemoteFaceDetector {
    private static final int DEFAULT_ERROE_CODE = -1;
    private static final String TAG = "RemoteFaceDetector";
    private static volatile Object lock = new Object();
    private boolean isInitialed;

    private static final class Holder {
        static final RemoteFaceDetector INSTANCE = new RemoteFaceDetector();

        private Holder() {
        }
    }

    private boolean expectedFace(FaceParcel faceParcel, MLFrame mLFrame, MLFaceAnalyzerSetting mLFaceAnalyzerSetting) {
        if (mLFaceAnalyzerSetting.getKeyPointType() == 0) {
            faceParcel.landmarks = new ArrayList();
        }
        if (mLFaceAnalyzerSetting.getShapeType() == 3) {
            faceParcel.contours = new ArrayList();
        }
        float minFaceProportion = mLFaceAnalyzerSetting.getMinFaceProportion();
        if (minFaceProportion < 0.0f || minFaceProportion > 1.0f || mLFrame.acquireProperty().getQuadrant() == 0 || mLFrame.acquireProperty().getQuadrant() == 2) {
            return true;
        }
        float height = mLFrame.acquireProperty().getHeight();
        float f = faceParcel.width;
        return (f == 0.0f || height == 0.0f || f / height <= minFaceProportion) ? false : true;
    }

    public static RemoteFaceDetector getInstance() {
        return Holder.INSTANCE;
    }

    private void notifyDownloadIfNeeded(Context context) {
        AvailableAdapterManager.getInstance().notifyDownloadIfNeeded(context, b.a());
    }

    public MLFaceAnalyzerSetting convertSetting(MLFaceAnalyzerSetting mLFaceAnalyzerSetting) {
        int i;
        int featureType = mLFaceAnalyzerSetting.getFeatureType();
        if (featureType == 1) {
            i = 2;
        } else {
            i = featureType == 2 ? 1 : featureType;
        }
        return new MLFaceAnalyzerSetting(mLFaceAnalyzerSetting.getKeyPointType(), mLFaceAnalyzerSetting.getShapeType(), i, mLFaceAnalyzerSetting.getPerformanceType() == 2 ? 1 : 2, mLFaceAnalyzerSetting.isPoseDisabled(), mLFaceAnalyzerSetting.isTracingAllowed(), mLFaceAnalyzerSetting.getTracingMode(), mLFaceAnalyzerSetting.isMaxSizeFaceOnly(), mLFaceAnalyzerSetting.getMinFaceProportion());
    }

    public synchronized void destroy(Context context) {
        String str = TAG;
        SmartLog.i(str, "Destroy");
        IInterface dynamicDelegate = b.a().getDynamicDelegate();
        if (dynamicDelegate == null) {
            SmartLog.i(str, "Destroy delegate is null");
            return;
        }
        try {
            ((IRemoteFaceDetectorDelegate) dynamicDelegate).destroy();
        } catch (Exception e) {
            SmartLog.e(TAG, "Destroy Exception e: " + e);
        }
    }

    public synchronized List<FaceParcel> detect(Context context, Bundle bundle, FaceFrameParcel faceFrameParcel, FaceDetectorOptionsParcel faceDetectorOptionsParcel) {
        ArrayList arrayList = new ArrayList();
        if (!this.isInitialed && initialize(context, faceDetectorOptionsParcel) >= 0) {
            this.isInitialed = true;
        }
        if (!this.isInitialed) {
            SmartLog.i(TAG, "Detect Initialed is false");
            return arrayList;
        }
        IInterface dynamicDelegate = b.a().getDynamicDelegate();
        if (dynamicDelegate == null) {
            SmartLog.i(TAG, "Detect delegate is null");
            return arrayList;
        }
        try {
            return ((IRemoteFaceDetectorDelegate) dynamicDelegate).detect(bundle, faceFrameParcel, faceDetectorOptionsParcel);
        } catch (Exception e) {
            SmartLog.e(TAG, "Detect Exception e: " + e);
            return arrayList;
        }
    }

    public List<ML3DFace> detect3DFromRemote(MLFrame mLFrame, ML3DFaceAnalyzerSetting mL3DFaceAnalyzerSetting, MLApplication mLApplication, String str) {
        int i;
        String str2 = TAG;
        SmartLog.i(str2, "enter detect3DFromRemote");
        ArrayList arrayList = new ArrayList();
        FaceFrameParcel faceFrameParcel = new FaceFrameParcel();
        try {
            ByteBuffer byteBuffer = mLFrame.getByteBuffer();
            if (byteBuffer == null || byteBuffer.remaining() == 0) {
                faceFrameParcel.setBitmap(mLFrame.readBitmap());
                i = 117;
            } else {
                int iRemaining = byteBuffer.remaining();
                byte[] bArr = new byte[iRemaining];
                byteBuffer.get(bArr, 0, iRemaining);
                faceFrameParcel.setBytes(bArr);
                i = 17;
            }
            faceFrameParcel.setTimestampMillis(mLFrame.acquireProperty().getTimestamp());
            faceFrameParcel.setRotation(mLFrame.acquireProperty().getQuadrant());
            faceFrameParcel.setWidth(mLFrame.acquireProperty().getWidth());
            faceFrameParcel.setHeight(mLFrame.acquireProperty().getHeight());
            faceFrameParcel.setFormat(i);
            faceFrameParcel.setFrameId(mLFrame.acquireProperty().getItemIdentity());
            FaceDetectorOptionsParcel faceDetectorOptionsParcelCreate = FaceHelper.create(mL3DFaceAnalyzerSetting);
            Bundle bundle = mLApplication.toBundle();
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            List<FaceParcel> listDetect = detect(mLApplication.getAppContext(), bundle, faceFrameParcel, faceDetectorOptionsParcelCreate);
            Log.e(str2, "Total duration 06===" + (SystemClock.elapsedRealtime() - jElapsedRealtime));
            if (listDetect == null) {
                SmartLog.e(str2, "detect fail, :  faceParcelList is null");
                return arrayList;
            }
            Iterator<FaceParcel> it = listDetect.iterator();
            while (it.hasNext()) {
                arrayList.add(FaceHelper.toML3DFace(it.next(), str));
            }
            return arrayList;
        } catch (RuntimeException e) {
            SmartLog.e(TAG, a.a("detect exception e: ").append(e.getMessage()).toString());
        } catch (Exception e2) {
            SmartLog.e(TAG, a.a("detect exception e:  ").append(e2.getMessage()).toString());
        }
    }

    public List<MLFace> detectFromRemote(MLFrame mLFrame, MLFaceAnalyzerSetting mLFaceAnalyzerSetting, MLApplication mLApplication) {
        Exception e;
        RuntimeException e2;
        int i;
        String str = TAG;
        SmartLog.i(str, "enter detect2DFromRemote");
        ArrayList<MLFace> arrayList = new ArrayList();
        FaceFrameParcel faceFrameParcel = new FaceFrameParcel();
        try {
            ByteBuffer byteBuffer = mLFrame.getByteBuffer();
            if (byteBuffer == null || byteBuffer.remaining() == 0) {
                faceFrameParcel.setBitmap(mLFrame.readBitmap());
                i = 117;
            } else {
                int iRemaining = byteBuffer.remaining();
                byte[] bArr = new byte[iRemaining];
                byteBuffer.get(bArr, 0, iRemaining);
                faceFrameParcel.setBytes(bArr);
                i = 17;
            }
            faceFrameParcel.setTimestampMillis(mLFrame.acquireProperty().getTimestamp());
            faceFrameParcel.setRotation(mLFrame.acquireProperty().getQuadrant());
            faceFrameParcel.setWidth(mLFrame.acquireProperty().getWidth());
            faceFrameParcel.setHeight(mLFrame.acquireProperty().getHeight());
            faceFrameParcel.setFormat(i);
            faceFrameParcel.setFrameId(mLFrame.acquireProperty().getItemIdentity());
            FaceDetectorOptionsParcel faceDetectorOptionsParcelCreate = FaceHelper.create(convertSetting(mLFaceAnalyzerSetting));
            Bundle bundle = mLApplication.toBundle();
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            List<FaceParcel> listDetect = detect(mLApplication.getAppContext(), bundle, faceFrameParcel, faceDetectorOptionsParcelCreate);
            Log.e(str, "Total duration 06===" + (SystemClock.elapsedRealtime() - jElapsedRealtime));
            if (listDetect == null) {
                SmartLog.e(str, "detect fail, :  faceParcelList is null");
                return arrayList;
            }
            for (FaceParcel faceParcel : listDetect) {
                if (expectedFace(faceParcel, mLFrame, mLFaceAnalyzerSetting)) {
                    arrayList.add(FaceHelper.toMLFace(faceParcel));
                }
            }
            if (!mLFaceAnalyzerSetting.isMaxSizeFaceOnly() || arrayList.isEmpty()) {
                return arrayList;
            }
            MLFace mLFace = null;
            for (MLFace mLFace2 : arrayList) {
                if (mLFace == null || mLFace.getWidth() * mLFace.getHeight() <= mLFace2.getWidth() * mLFace2.getHeight()) {
                    mLFace = mLFace2;
                }
            }
            ArrayList arrayList2 = new ArrayList();
            try {
                arrayList2.add(mLFace);
                return arrayList2;
            } catch (RuntimeException e3) {
                e2 = e3;
                arrayList = arrayList2;
                SmartLog.e(TAG, a.a("detect exception e: ").append(e2.getMessage()).toString());
                return arrayList;
            } catch (Exception e4) {
                e = e4;
                arrayList = arrayList2;
                SmartLog.e(TAG, a.a("detect exception e:  ").append(e.getMessage()).toString());
                return arrayList;
            }
        } catch (RuntimeException e5) {
            e2 = e5;
        } catch (Exception e6) {
            e = e6;
        }
    }

    public synchronized int initialize(Context context, FaceDetectorOptionsParcel faceDetectorOptionsParcel) {
        String str = TAG;
        SmartLog.i(str, "enter initialize");
        if (this.isInitialed) {
            return 0;
        }
        b bVarA = b.a();
        IInterface dynamicDelegate = bVarA.getDynamicDelegate();
        if (dynamicDelegate == null) {
            SmartLog.i(str, "initialize delegate is null");
            return -1;
        }
        try {
            int iInitialize = ((IRemoteFaceDetectorDelegate) dynamicDelegate).initialize(ObjectWrapper.wrap(bVarA.getDynamicContext()), faceDetectorOptionsParcel);
            if (iInitialize >= 0) {
                this.isInitialed = true;
                SmartLog.e(str, "initialize success");
            } else {
                SmartLog.e(str, "initialize failure");
            }
            return iInitialize;
        } catch (Exception e) {
            SmartLog.e(TAG, "Initialize Exception e: " + e);
            return -1;
        }
    }

    public boolean isAvailable(Context context) {
        return AvailableAdapterManager.getInstance().isAvailable(context, b.a());
    }

    public synchronized void prepare(Context context) {
        b.a().initial(context);
        notifyDownloadIfNeeded(context);
    }

    public synchronized void release(Context context) {
        SmartLog.i(TAG, "release");
        if (this.isInitialed) {
            destroy(context);
            this.isInitialed = false;
        }
        AvailableAdapterManager.getInstance().release(context);
    }

    public boolean setFocus(int i) {
        return true;
    }

    private RemoteFaceDetector() {
        this.isInitialed = false;
    }
}
