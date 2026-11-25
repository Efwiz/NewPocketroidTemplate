package com.huawei.hms.mlsdk.face.face3d;

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
public final class ML3DFaceAnalyzer extends MLAnalyzer<ML3DFace> {
    private static final String TAG = "ML3DFaceAnalyzer";
    private static Map<AppSettingHolder<ML3DFaceAnalyzerSetting>, ML3DFaceAnalyzer> appSettingAnalyzerMap = new HashMap();

    /* JADX INFO: renamed from: app, reason: collision with root package name */
    private MLApplication f192app;
    private g bidirectionArray;
    private String mBaseObj;
    private ML3DFaceAnalyzerSetting setting;
    private Object syncObject;

    @Deprecated
    public static class Factory {
        private ML3DFaceAnalyzerSetting.Factory factory = new ML3DFaceAnalyzerSetting.Factory();
        private final Context mContext;

        public Factory(Context context) {
            this.mContext = context;
        }

        public ML3DFaceAnalyzer create() {
            ML3DFaceAnalyzerSetting mL3DFaceAnalyzerSettingCreate = this.factory.create();
            RemoteFaceDetector.getInstance().prepare(this.mContext);
            return new ML3DFaceAnalyzer(this.mContext, mL3DFaceAnalyzerSettingCreate);
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

    public static synchronized ML3DFaceAnalyzer create(MLApplication mLApplication, ML3DFaceAnalyzerSetting mL3DFaceAnalyzerSetting) {
        ML3DFaceAnalyzer mL3DFaceAnalyzer;
        SmartLog.i(TAG, "create 3D MLFaceAnalyzer");
        AppSettingHolder<ML3DFaceAnalyzerSetting> appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mL3DFaceAnalyzerSetting);
        mL3DFaceAnalyzer = appSettingAnalyzerMap.get(appSettingHolderCreate);
        if (mL3DFaceAnalyzer == null) {
            mL3DFaceAnalyzer = new ML3DFaceAnalyzer(mLApplication, mL3DFaceAnalyzerSetting);
            appSettingAnalyzerMap.put(appSettingHolderCreate, mL3DFaceAnalyzer);
        }
        RemoteFaceDetector.getInstance().prepare(mLApplication.getAppContext());
        RemoteFaceDetector.getInstance().initialize(mLApplication.getAppContext(), FaceHelper.create(mL3DFaceAnalyzerSetting));
        return mL3DFaceAnalyzer;
    }

    private void initializeObject(Context context) {
        this.mBaseObj = null;
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public SparseArray<ML3DFace> analyseFrame(MLFrame mLFrame) {
        String str = TAG;
        SmartLog.i(str, "enter 3D syncAnalyseFrame");
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        mLFrame.initialize();
        List<ML3DFace> listDetect3DFromRemote = RemoteFaceDetector.getInstance().detect3DFromRemote(mLFrame.getFrame(false, true), this.setting, this.f192app, this.mBaseObj);
        if (listDetect3DFromRemote.isEmpty()) {
            SmartLog.e(str, "syncAnalyseFrame faceParcelList is empty!");
        }
        SparseArray<ML3DFace> sparseArray = new SparseArray<>();
        for (int i = 0; i < listDetect3DFromRemote.size(); i++) {
            sparseArray.put(i, listDetect3DFromRemote.get(i));
        }
        Log.i(TAG, a.a("analyseFrame duration= ").append(SystemClock.elapsedRealtime() - jElapsedRealtime).toString());
        return sparseArray;
    }

    public Task<List<ML3DFace>> asyncAnalyseFrame(MLFrame mLFrame) {
        SmartLog.i(TAG, "enter 3D asyncAnalyseFrame");
        mLFrame.initialize();
        final MLFrame frame = mLFrame.getFrame(false, true);
        if (frame.getByteBuffer() != null) {
            frame.getByteBuffer().rewind();
        }
        return Tasks.call(new Callable<List<ML3DFace>>() { // from class: com.huawei.hms.mlsdk.face.face3d.ML3DFaceAnalyzer.1
            @Override // java.util.concurrent.Callable
            public List<ML3DFace> call() throws Exception {
                List<ML3DFace> listDetect3DFromRemote = ML3DFaceAnalyzer.this.isAvailable() ? RemoteFaceDetector.getInstance().detect3DFromRemote(frame, ML3DFaceAnalyzer.this.setting, ML3DFaceAnalyzer.this.f192app, ML3DFaceAnalyzer.this.mBaseObj) : new ArrayList<>();
                if (listDetect3DFromRemote.isEmpty()) {
                    SmartLog.e(ML3DFaceAnalyzer.TAG, "asyncAnalyseFrame faceParcelList is empty!");
                }
                return listDetect3DFromRemote;
            }
        });
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public void destroy() {
        super.destroy();
        synchronized (this.syncObject) {
            RemoteFaceDetector.getInstance().release(this.f192app.getAppContext());
        }
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public boolean isAvailable() {
        return RemoteFaceDetector.getInstance().isAvailable(this.f192app.getAppContext());
    }

    protected void release() throws Throwable {
        synchronized (this.syncObject) {
            destroy();
        }
    }

    public void stop() throws IOException {
        synchronized (this.syncObject) {
            RemoteFaceDetector.getInstance().release(this.f192app.getAppContext());
        }
    }

    private ML3DFaceAnalyzer(MLApplication mLApplication, ML3DFaceAnalyzerSetting mL3DFaceAnalyzerSetting) {
        this.bidirectionArray = null;
        this.syncObject = null;
        this.f192app = mLApplication;
        this.setting = mL3DFaceAnalyzerSetting;
        this.bidirectionArray = new g();
        this.syncObject = new Object();
        initializeObject(mLApplication.getAppContext());
    }

    private ML3DFaceAnalyzer(Context context, ML3DFaceAnalyzerSetting mL3DFaceAnalyzerSetting) {
        this.bidirectionArray = null;
        this.syncObject = null;
        this.bidirectionArray = new g();
        this.syncObject = new Object();
        this.f192app = MLApplication.initialize(context);
        this.setting = mL3DFaceAnalyzerSetting;
        initializeObject(context);
    }
}
