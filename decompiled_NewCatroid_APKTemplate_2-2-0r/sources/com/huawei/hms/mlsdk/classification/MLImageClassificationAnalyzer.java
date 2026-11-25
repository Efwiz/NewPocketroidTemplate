package com.huawei.hms.mlsdk.classification;

import android.util.SparseArray;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.Preconditions;
import com.huawei.hms.ml.common.label.ImageLabelerOptionsParcel;
import com.huawei.hms.mlsdk.classification.internal.client.NativeCloudImageLabeler;
import com.huawei.hms.mlsdk.classification.internal.client.NativeOnDeviceImageLabeler;
import com.huawei.hms.mlsdk.classification.internal.client.RemoteOnDeviceImageLabeler;
import com.huawei.hms.mlsdk.common.AppSettingHolder;
import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLFrame;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class MLImageClassificationAnalyzer extends MLAnalyzer<MLImageClassification> {
    public static final int TYPE_LOCAL = 0;
    public static final int TYPE_REMOTE = 1;
    private static Map<AppSettingHolder<MLLocalClassificationAnalyzerSetting>, MLImageClassificationAnalyzer> localClassificationMap = new HashMap();
    private static Map<AppSettingHolder<MLRemoteClassificationAnalyzerSetting>, MLImageClassificationAnalyzer> remoteClassificationMap = new HashMap();
    private final NativeCloudImageLabeler cloudImageLabeler;
    private final NativeOnDeviceImageLabeler onDeviceImageLabeler;
    private final int type;

    @Retention(RetentionPolicy.CLASS)
    public @interface ImageClassificationType {
    }

    private MLImageClassificationAnalyzer(NativeOnDeviceImageLabeler nativeOnDeviceImageLabeler, NativeCloudImageLabeler nativeCloudImageLabeler) {
        Preconditions.checkArgument((nativeOnDeviceImageLabeler == null && nativeCloudImageLabeler == null) ? false : true, "One of on-device, cloud or on-device AutoML image labeler should be set.");
        this.onDeviceImageLabeler = nativeOnDeviceImageLabeler;
        this.cloudImageLabeler = nativeCloudImageLabeler;
        if (nativeCloudImageLabeler != null) {
            this.type = 1;
        } else {
            this.type = 0;
        }
    }

    public static synchronized MLImageClassificationAnalyzer create(MLApplication mLApplication, MLLocalClassificationAnalyzerSetting mLLocalClassificationAnalyzerSetting) {
        MLImageClassificationAnalyzer mLImageClassificationAnalyzer;
        AppSettingHolder<MLLocalClassificationAnalyzerSetting> appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mLLocalClassificationAnalyzerSetting);
        mLImageClassificationAnalyzer = localClassificationMap.get(appSettingHolderCreate);
        if (mLImageClassificationAnalyzer == null) {
            MLImageClassificationAnalyzer mLImageClassificationAnalyzer2 = new MLImageClassificationAnalyzer(new NativeOnDeviceImageLabeler(mLApplication.getAppContext(), mLLocalClassificationAnalyzerSetting, mLApplication.toBundle()), null);
            localClassificationMap.put(appSettingHolderCreate, mLImageClassificationAnalyzer2);
            mLImageClassificationAnalyzer = mLImageClassificationAnalyzer2;
        }
        RemoteOnDeviceImageLabeler.getInstance().prepare(mLApplication.getAppContext());
        RemoteOnDeviceImageLabeler.getInstance().initialize(mLApplication.getAppContext(), new ImageLabelerOptionsParcel(mLLocalClassificationAnalyzerSetting.getMinAcceptablePossibility(), mLApplication.toBundle()));
        return mLImageClassificationAnalyzer;
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public SparseArray<MLImageClassification> analyseFrame(MLFrame mLFrame) {
        if (mLFrame == null) {
            throw new IllegalArgumentException("Missing frame.");
        }
        mLFrame.initialize();
        MLFrame frame = mLFrame.getFrame(false, true);
        NativeCloudImageLabeler nativeCloudImageLabeler = this.cloudImageLabeler;
        if (nativeCloudImageLabeler != null) {
            return nativeCloudImageLabeler.analyseFrame(mLFrame.readBitmap());
        }
        NativeOnDeviceImageLabeler nativeOnDeviceImageLabeler = this.onDeviceImageLabeler;
        if (nativeOnDeviceImageLabeler != null) {
            return nativeOnDeviceImageLabeler.analyseFrame(frame);
        }
        throw new IllegalArgumentException("Missing analyzer");
    }

    public Task<List<MLImageClassification>> asyncAnalyseFrame(MLFrame mLFrame) {
        if (mLFrame == null) {
            throw new IllegalArgumentException("Missing frame.");
        }
        mLFrame.initialize();
        MLFrame frame = mLFrame.getFrame(false, true);
        NativeCloudImageLabeler nativeCloudImageLabeler = this.cloudImageLabeler;
        if (nativeCloudImageLabeler != null) {
            return nativeCloudImageLabeler.asyncAnalyseFrame(mLFrame.readBitmap());
        }
        NativeOnDeviceImageLabeler nativeOnDeviceImageLabeler = this.onDeviceImageLabeler;
        if (nativeOnDeviceImageLabeler != null) {
            return nativeOnDeviceImageLabeler.asyncAnalyseFrame(frame);
        }
        throw new IllegalArgumentException("Missing analyzer");
    }

    public int getAnalyzerType() {
        return this.type;
    }

    public void stop() throws IOException {
        NativeOnDeviceImageLabeler nativeOnDeviceImageLabeler = this.onDeviceImageLabeler;
        if (nativeOnDeviceImageLabeler != null) {
            nativeOnDeviceImageLabeler.close();
        }
        NativeCloudImageLabeler nativeCloudImageLabeler = this.cloudImageLabeler;
        if (nativeCloudImageLabeler != null) {
            nativeCloudImageLabeler.close();
        }
    }

    public static synchronized MLImageClassificationAnalyzer create(MLApplication mLApplication, MLRemoteClassificationAnalyzerSetting mLRemoteClassificationAnalyzerSetting) {
        MLImageClassificationAnalyzer mLImageClassificationAnalyzer;
        AppSettingHolder<MLRemoteClassificationAnalyzerSetting> appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mLRemoteClassificationAnalyzerSetting);
        mLImageClassificationAnalyzer = remoteClassificationMap.get(appSettingHolderCreate);
        if (mLImageClassificationAnalyzer == null) {
            mLImageClassificationAnalyzer = new MLImageClassificationAnalyzer(null, new NativeCloudImageLabeler(mLApplication, mLRemoteClassificationAnalyzerSetting));
            remoteClassificationMap.put(appSettingHolderCreate, mLImageClassificationAnalyzer);
        }
        return mLImageClassificationAnalyzer;
    }
}
