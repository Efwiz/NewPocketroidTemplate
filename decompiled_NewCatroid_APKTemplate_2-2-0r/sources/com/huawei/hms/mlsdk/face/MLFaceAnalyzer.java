package com.huawei.hms.mlsdk.face;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.ml.common.base.SmartLog;
import com.huawei.hms.mlsdk.common.AppSettingHolder;
import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.face.internal.client.FaceHelper;
import com.huawei.hms.mlsdk.face.internal.client.RemoteFaceDetector;
import com.huawei.hms.mlsdk.mlvision.a;
import com.huawei.hms.mlsdk.mlvision.g;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes10.dex */
public final class MLFaceAnalyzer extends MLAnalyzer<MLFace> {
    private static final String TAG = "MLFaceAnalyzer";
    private static Map<AppSettingHolder<MLFaceAnalyzerSetting>, MLFaceAnalyzer> appSettingAnalyzerMap = new HashMap();

    /* JADX INFO: renamed from: app, reason: collision with root package name */
    private MLApplication f191app;
    private g bidirectionArray;
    private MLFaceAnalyzerSetting setting;
    private Object syncObject;

    @Deprecated
    public static class Factory {
        private MLFaceAnalyzerSetting.Factory factory = new MLFaceAnalyzerSetting.Factory();
        private final Context mContext;

        public Factory(Context context) {
            this.mContext = context;
        }

        public MLFaceAnalyzer create() {
            MLFaceAnalyzerSetting mLFaceAnalyzerSettingCreate = this.factory.create();
            RemoteFaceDetector.getInstance().prepare(this.mContext);
            return new MLFaceAnalyzer(this.mContext, mLFaceAnalyzerSettingCreate);
        }

        public Factory setFeatureType(int i) {
            if (i != 2 && i != 1) {
                throw new IllegalArgumentException("Invalid feature type: " + i);
            }
            this.factory.setFeatureType(i);
            return this;
        }

        public Factory setKeyPointType(int i) {
            if (i != 0 && i != 1 && i != 2) {
                throw new IllegalArgumentException("Invalid keyPointType type: " + i);
            }
            if (i == 0) {
                this.factory.setKeyPointType(0);
                this.factory.setShapeType(3);
            } else if (i == 1) {
                this.factory.setKeyPointType(1);
                this.factory.setShapeType(3);
            } else {
                this.factory.setKeyPointType(0);
                this.factory.setShapeType(2);
            }
            return this;
        }

        public Factory setMaxSizeFaceOnly(boolean z) {
            this.factory.setMaxSizeFaceOnly(z);
            return this;
        }

        public Factory setMinFaceProportion(float f) {
            if (f < 0.0f || f > 1.0f) {
                throw new IllegalArgumentException("Invalid minimum proportional: " + f);
            }
            this.factory.setMinFaceProportion(f);
            return this;
        }

        public Factory setPerformanceType(int i) {
            if (i != 1 && i != 2) {
                throw new IllegalArgumentException("Invalid performance type: " + i);
            }
            this.factory.setPerformanceType(i);
            return this;
        }

        public Factory setTracingAllowed(boolean z) {
            this.factory.setTracingAllowed(z);
            return this;
        }
    }

    public static synchronized MLFaceAnalyzer create(MLApplication mLApplication, MLFaceAnalyzerSetting mLFaceAnalyzerSetting) {
        MLFaceAnalyzer mLFaceAnalyzer;
        SmartLog.i(TAG, "create 2D MLFaceAnalyzer");
        AppSettingHolder<MLFaceAnalyzerSetting> appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mLFaceAnalyzerSetting);
        mLFaceAnalyzer = appSettingAnalyzerMap.get(appSettingHolderCreate);
        if (mLFaceAnalyzer == null) {
            mLFaceAnalyzer = new MLFaceAnalyzer(mLApplication, mLFaceAnalyzerSetting);
            appSettingAnalyzerMap.put(appSettingHolderCreate, mLFaceAnalyzer);
        }
        RemoteFaceDetector.getInstance().prepare(mLApplication.getAppContext());
        RemoteFaceDetector.getInstance().initialize(mLApplication.getAppContext(), FaceHelper.create(RemoteFaceDetector.getInstance().convertSetting(mLFaceAnalyzerSetting)));
        return mLFaceAnalyzer;
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public SparseArray<MLFace> analyseFrame(MLFrame mLFrame) {
        String str = TAG;
        SmartLog.i(str, "enter syncAnalyseFrame");
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        mLFrame.initialize();
        List<MLFace> listDetectFromRemote = RemoteFaceDetector.getInstance().detectFromRemote(mLFrame.getFrame(false, true), this.setting, this.f191app);
        if (listDetectFromRemote.isEmpty()) {
            SmartLog.e(str, "syncAnalyseFrame faceParcelList is empty!");
        }
        SparseArray<MLFace> sparseArray = new SparseArray<>();
        for (int i = 0; i < listDetectFromRemote.size(); i++) {
            sparseArray.put(i, listDetectFromRemote.get(i));
        }
        Log.i(TAG, a.a("syncAnalyseFrame duration= ").append(SystemClock.elapsedRealtime() - jElapsedRealtime).toString());
        return sparseArray;
    }

    public Task<List<MLFace>> asyncAnalyseFrame(MLFrame mLFrame) {
        SmartLog.i(TAG, "enter asyncAnalyseFrame");
        mLFrame.initialize();
        final MLFrame frame = mLFrame.getFrame(false, true);
        if (frame.getByteBuffer() != null) {
            frame.getByteBuffer().rewind();
        }
        return Tasks.call(new Callable<List<MLFace>>() { // from class: com.huawei.hms.mlsdk.face.MLFaceAnalyzer.1
            @Override // java.util.concurrent.Callable
            public List<MLFace> call() throws Exception {
                List<MLFace> listDetectFromRemote = MLFaceAnalyzer.this.isAvailable() ? RemoteFaceDetector.getInstance().detectFromRemote(frame, MLFaceAnalyzer.this.setting, MLFaceAnalyzer.this.f191app) : new ArrayList<>();
                if (listDetectFromRemote.isEmpty()) {
                    SmartLog.e(MLFaceAnalyzer.TAG, "asyncAnalyseFrame faceParcelList is empty!");
                }
                return listDetectFromRemote;
            }
        });
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public void destroy() {
        super.destroy();
        synchronized (this.syncObject) {
            RemoteFaceDetector.getInstance().release(this.f191app.getAppContext());
        }
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public boolean isAvailable() {
        return RemoteFaceDetector.getInstance().isAvailable(this.f191app.getAppContext());
    }

    protected void release() throws Throwable {
        synchronized (this.syncObject) {
            destroy();
        }
    }

    public boolean setTrackIdentiy(int i) {
        boolean focus;
        int iA = this.bidirectionArray.a(i);
        synchronized (this.syncObject) {
            focus = RemoteFaceDetector.getInstance().setFocus(iA);
        }
        return focus;
    }

    public void stop() throws IOException {
        synchronized (this.syncObject) {
            RemoteFaceDetector.getInstance().release(this.f191app.getAppContext());
        }
    }

    private MLFaceAnalyzer(MLApplication mLApplication, MLFaceAnalyzerSetting mLFaceAnalyzerSetting) {
        this.bidirectionArray = null;
        this.syncObject = null;
        this.f191app = mLApplication;
        this.setting = mLFaceAnalyzerSetting;
        this.bidirectionArray = new g();
        this.syncObject = new Object();
    }

    private MLFaceAnalyzer(Context context, MLFaceAnalyzerSetting mLFaceAnalyzerSetting) {
        this.bidirectionArray = null;
        this.syncObject = null;
        this.bidirectionArray = new g();
        this.syncObject = new Object();
        this.f191app = MLApplication.initialize(context);
        this.setting = mLFaceAnalyzerSetting;
    }
}
