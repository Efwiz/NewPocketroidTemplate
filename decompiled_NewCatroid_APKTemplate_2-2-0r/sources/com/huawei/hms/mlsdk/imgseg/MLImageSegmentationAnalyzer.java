package com.huawei.hms.mlsdk.imgseg;

import android.os.Bundle;
import android.util.SparseArray;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.ml.common.imgseg.ImageSegmentationDetectorParcel;
import com.huawei.hms.ml.common.imgseg.ImageSegmentationOptionsParcel;
import com.huawei.hms.mlsdk.common.AppSettingHolder;
import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.imgseg.client.RemoteImageSegmentationDecoder;
import com.huawei.hms.mlsdk.mlvision.c;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes10.dex */
public class MLImageSegmentationAnalyzer extends MLAnalyzer<MLImageSegmentation> {
    private static final String TAG = "MLImageSegmentationAnalyzer";
    private static Map<AppSettingHolder<MLImageSegmentationSetting>, MLImageSegmentationAnalyzer> appOptionDetectMap = new HashMap();

    /* JADX INFO: renamed from: app, reason: collision with root package name */
    private MLApplication f193app;
    private Bundle bundle;
    private MLImageSegmentationSetting options;

    private MLImageSegmentationAnalyzer(MLApplication mLApplication, MLImageSegmentationSetting mLImageSegmentationSetting) {
        this.f193app = mLApplication;
        this.options = mLImageSegmentationSetting;
        this.bundle = mLApplication.toBundle();
    }

    private static MLImageSegmentation convert(ImageSegmentationDetectorParcel imageSegmentationDetectorParcel, ImageSegmentationOptionsParcel imageSegmentationOptionsParcel) {
        return new MLImageSegmentation(imageSegmentationDetectorParcel.masks, imageSegmentationDetectorParcel.foreground, imageSegmentationDetectorParcel.grayscale, imageSegmentationDetectorParcel.original);
    }

    public static synchronized MLImageSegmentationAnalyzer create(MLApplication mLApplication, MLImageSegmentationSetting mLImageSegmentationSetting) {
        MLImageSegmentationAnalyzer mLImageSegmentationAnalyzer;
        AppSettingHolder<MLImageSegmentationSetting> appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mLImageSegmentationSetting);
        mLImageSegmentationAnalyzer = appOptionDetectMap.get(appSettingHolderCreate);
        if (mLImageSegmentationAnalyzer == null) {
            mLImageSegmentationAnalyzer = new MLImageSegmentationAnalyzer(mLApplication, mLImageSegmentationSetting);
            appOptionDetectMap.put(appSettingHolderCreate, mLImageSegmentationAnalyzer);
        }
        RemoteImageSegmentationDecoder.getInstance().prepare(mLApplication.getAppContext());
        ImageSegmentationOptionsParcel imageSegmentationOptionsParcel = new ImageSegmentationOptionsParcel(mLImageSegmentationSetting.getAnalyzerType(), mLImageSegmentationSetting.isExact(), mLImageSegmentationSetting.getScene(), mLApplication.toBundle(), 1);
        c cVarA = c.a();
        RemoteImageSegmentationDecoder.getInstance().initial(imageSegmentationOptionsParcel, cVarA, cVarA.getDynamicDelegate());
        return mLImageSegmentationAnalyzer;
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public SparseArray<MLImageSegmentation> analyseFrame(MLFrame mLFrame) {
        if (mLFrame == null) {
            throw new IllegalArgumentException("No frame found.");
        }
        mLFrame.initialize();
        ImageSegmentationDetectorParcel imageSegmentationDetectorParcelDetect = RemoteImageSegmentationDecoder.getInstance().detect(this.f193app.getAppContext(), this.bundle, mLFrame.getFrame(false, true), new ImageSegmentationOptionsParcel(this.options.getAnalyzerType(), this.options.isExact(), this.options.getScene(), this.bundle, 1));
        SparseArray<MLImageSegmentation> sparseArray = new SparseArray<>();
        if (imageSegmentationDetectorParcelDetect != null) {
            sparseArray.put(0, new MLImageSegmentation(imageSegmentationDetectorParcelDetect.masks, imageSegmentationDetectorParcelDetect.foreground, imageSegmentationDetectorParcelDetect.grayscale, imageSegmentationDetectorParcelDetect.original));
        }
        return sparseArray;
    }

    public Task<MLImageSegmentation> asyncAnalyseFrame(MLFrame mLFrame) {
        mLFrame.initialize();
        final MLFrame frame = mLFrame.getFrame(false, true);
        final ImageSegmentationOptionsParcel imageSegmentationOptionsParcel = new ImageSegmentationOptionsParcel(this.options.getAnalyzerType(), this.options.isExact(), this.options.getScene(), this.bundle, 1);
        return Tasks.callInBackground(new Callable<MLImageSegmentation>() { // from class: com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public MLImageSegmentation call() throws Exception {
                ImageSegmentationDetectorParcel imageSegmentationDetectorParcelDetect = RemoteImageSegmentationDecoder.getInstance().detect(MLImageSegmentationAnalyzer.this.f193app.getAppContext(), MLImageSegmentationAnalyzer.this.bundle, frame, imageSegmentationOptionsParcel);
                MLImageSegmentation mLImageSegmentation = MLImageSegmentation.getInstance();
                if (imageSegmentationDetectorParcelDetect == null) {
                    return mLImageSegmentation;
                }
                mLImageSegmentation.masks = imageSegmentationDetectorParcelDetect.masks;
                mLImageSegmentation.foreground = imageSegmentationDetectorParcelDetect.foreground;
                mLImageSegmentation.grayscale = imageSegmentationDetectorParcelDetect.grayscale;
                mLImageSegmentation.original = imageSegmentationDetectorParcelDetect.original;
                return mLImageSegmentation;
            }
        });
    }

    public void stop() throws IOException {
        RemoteImageSegmentationDecoder.getInstance().release(this.f193app.getAppContext());
    }
}
