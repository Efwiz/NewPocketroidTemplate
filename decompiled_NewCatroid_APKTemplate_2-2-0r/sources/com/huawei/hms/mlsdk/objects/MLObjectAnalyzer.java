package com.huawei.hms.mlsdk.objects;

import android.graphics.Rect;
import android.util.SparseArray;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.ml.common.object.ObjectDetectorOptionsParcel;
import com.huawei.hms.ml.common.object.ObjectDetectorParcel;
import com.huawei.hms.mlsdk.common.AppSettingHolder;
import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.objects.internal.client.RemoteObjectDecoder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes10.dex */
public class MLObjectAnalyzer extends MLAnalyzer<MLObject> {
    private static final String TAG = "MLObjectAnalyzer";
    private static Map<AppSettingHolder<MLObjectAnalyzerSetting>, MLObjectAnalyzer> appOptionDetectMap = new HashMap();

    /* JADX INFO: renamed from: app, reason: collision with root package name */
    private MLApplication f197app;
    private MLObjectAnalyzerSetting options;

    private MLObjectAnalyzer(MLApplication mLApplication, MLObjectAnalyzerSetting mLObjectAnalyzerSetting) {
        this.f197app = mLApplication;
        this.options = mLObjectAnalyzerSetting;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<MLObject> convert(List<ObjectDetectorParcel> list, ObjectDetectorOptionsParcel objectDetectorOptionsParcel) {
        ArrayList arrayList = new ArrayList();
        if (list == null) {
            return arrayList;
        }
        for (ObjectDetectorParcel objectDetectorParcel : list) {
            Rect rect = objectDetectorParcel.rect;
            Float f = null;
            Integer num = objectDetectorOptionsParcel.detectorMode == 1 ? objectDetectorParcel.trackingId : null;
            if (objectDetectorParcel.category != 0) {
                f = objectDetectorParcel.confidence;
            }
            arrayList.add(new MLObject(rect, num, f, convertCategoryTo(objectDetectorParcel.category)));
        }
        return arrayList;
    }

    private static final int convertCategoryTo(int i) {
        switch (i) {
            case 0:
            default:
                return 0;
            case 1:
                return 3;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 5;
            case 5:
                return 4;
            case 6:
                return 6;
        }
    }

    public static synchronized MLObjectAnalyzer create(MLApplication mLApplication, MLObjectAnalyzerSetting mLObjectAnalyzerSetting) {
        MLObjectAnalyzer mLObjectAnalyzer;
        AppSettingHolder<MLObjectAnalyzerSetting> appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mLObjectAnalyzerSetting);
        mLObjectAnalyzer = appOptionDetectMap.get(appSettingHolderCreate);
        if (mLObjectAnalyzer == null) {
            mLObjectAnalyzer = new MLObjectAnalyzer(mLApplication, mLObjectAnalyzerSetting);
            appOptionDetectMap.put(appSettingHolderCreate, mLObjectAnalyzer);
        }
        RemoteObjectDecoder.getInstance().prepare(mLApplication.getAppContext());
        RemoteObjectDecoder.getInstance().initial(mLApplication.getAppContext(), new ObjectDetectorOptionsParcel(mLObjectAnalyzerSetting.getAnalyzerType() == 0 ? 2 : 1, mLObjectAnalyzerSetting.isMultipleResultsAllowed(), mLObjectAnalyzerSetting.isClassificationAllowed(), mLApplication.toBundle()));
        return mLObjectAnalyzer;
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public SparseArray<MLObject> analyseFrame(MLFrame mLFrame) {
        if (mLFrame == null) {
            throw new IllegalArgumentException("No frame found.");
        }
        mLFrame.initialize();
        int i = 0;
        MLFrame frame = mLFrame.getFrame(false, true);
        ObjectDetectorOptionsParcel objectDetectorOptionsParcel = new ObjectDetectorOptionsParcel(this.options.getAnalyzerType() == 0 ? 2 : 1, this.options.isMultipleResultsAllowed(), this.options.isClassificationAllowed(), this.f197app.toBundle());
        List<MLObject> listConvert = convert(RemoteObjectDecoder.getInstance().detect(this.f197app.getAppContext(), this.f197app.toBundle(), frame, objectDetectorOptionsParcel), objectDetectorOptionsParcel);
        SparseArray<MLObject> sparseArray = new SparseArray<>();
        Iterator<MLObject> it = listConvert.iterator();
        while (it.hasNext()) {
            sparseArray.put(i, it.next());
            i++;
        }
        return sparseArray;
    }

    public Task<List<MLObject>> asyncAnalyseFrame(MLFrame mLFrame) {
        mLFrame.initialize();
        final MLFrame frame = mLFrame.getFrame(false, true);
        final ObjectDetectorOptionsParcel objectDetectorOptionsParcel = new ObjectDetectorOptionsParcel(this.options.getAnalyzerType() == 0 ? 2 : 1, this.options.isMultipleResultsAllowed(), this.options.isClassificationAllowed(), this.f197app.toBundle());
        return Tasks.call(new Callable<List<MLObject>>() { // from class: com.huawei.hms.mlsdk.objects.MLObjectAnalyzer.1
            @Override // java.util.concurrent.Callable
            public List<MLObject> call() throws Exception {
                List<ObjectDetectorParcel> listDetect = RemoteObjectDecoder.getInstance().detect(MLObjectAnalyzer.this.f197app.getAppContext(), MLObjectAnalyzer.this.f197app.toBundle(), frame, objectDetectorOptionsParcel);
                return listDetect != null ? MLObjectAnalyzer.convert(listDetect, objectDetectorOptionsParcel) : new ArrayList();
            }
        });
    }

    public void stop() throws IOException {
        RemoteObjectDecoder.getInstance().release(this.f197app.getAppContext());
    }
}
