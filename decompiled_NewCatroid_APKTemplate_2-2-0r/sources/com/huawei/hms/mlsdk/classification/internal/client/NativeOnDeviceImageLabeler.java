package com.huawei.hms.mlsdk.classification.internal.client;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.ml.common.label.ImageLabelParcel;
import com.huawei.hms.ml.common.label.ImageLabelerFrameParcel;
import com.huawei.hms.ml.common.label.ImageLabelerOptionsParcel;
import com.huawei.hms.mlsdk.classification.MLImageClassification;
import com.huawei.hms.mlsdk.classification.MLLocalClassificationAnalyzerSetting;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLFrame;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes10.dex */
public class NativeOnDeviceImageLabeler {
    private final Context mContext;
    private ImageLabelerOptionsParcel optionsParcel;

    public NativeOnDeviceImageLabeler(Context context, MLLocalClassificationAnalyzerSetting mLLocalClassificationAnalyzerSetting, Bundle bundle) {
        this.mContext = context;
        this.optionsParcel = new ImageLabelerOptionsParcel(mLLocalClassificationAnalyzerSetting.getMinAcceptablePossibility(), bundle);
    }

    private static ImageLabelerFrameParcel convert(MLFrame mLFrame) {
        ImageLabelerFrameParcel imageLabelerFrameParcel = new ImageLabelerFrameParcel();
        MLFrame.Property propertyAcquireProperty = mLFrame.acquireProperty();
        imageLabelerFrameParcel.width = propertyAcquireProperty.getWidth();
        imageLabelerFrameParcel.height = propertyAcquireProperty.getHeight();
        imageLabelerFrameParcel.format = propertyAcquireProperty.getFormatType();
        imageLabelerFrameParcel.rotation = propertyAcquireProperty.getQuadrant();
        ByteBuffer byteBuffer = mLFrame.getByteBuffer();
        if (byteBuffer != null) {
            imageLabelerFrameParcel.bytes = byteBuffer.array();
        }
        imageLabelerFrameParcel.bitmap = mLFrame.readBitmap();
        return imageLabelerFrameParcel;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MLImageClassification> getClassifications(MLFrame mLFrame) {
        List<ImageLabelParcel> listDetectWithBitmap = RemoteOnDeviceImageLabeler.getInstance().detectWithBitmap(this.mContext, MLApplication.getInstance().toBundle(), convert(mLFrame), this.optionsParcel);
        if (listDetectWithBitmap == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < listDetectWithBitmap.size(); i++) {
            ImageLabelParcel imageLabelParcel = listDetectWithBitmap.get(i);
            arrayList.add(new MLImageClassification(imageLabelParcel.text, imageLabelParcel.confidence.floatValue(), ""));
        }
        return arrayList;
    }

    public SparseArray<MLImageClassification> analyseFrame(MLFrame mLFrame) {
        SparseArray<MLImageClassification> sparseArray = new SparseArray<>();
        List<MLImageClassification> classifications = getClassifications(mLFrame);
        for (int i = 0; i < classifications.size(); i++) {
            sparseArray.put(i, classifications.get(i));
        }
        return sparseArray;
    }

    public Task<List<MLImageClassification>> asyncAnalyseFrame(final MLFrame mLFrame) {
        return Tasks.call(new Callable<List<MLImageClassification>>() { // from class: com.huawei.hms.mlsdk.classification.internal.client.NativeOnDeviceImageLabeler.1
            @Override // java.util.concurrent.Callable
            public List<MLImageClassification> call() throws Exception {
                return NativeOnDeviceImageLabeler.this.getClassifications(mLFrame);
            }
        });
    }

    public void close() {
        RemoteOnDeviceImageLabeler.getInstance().release(this.mContext);
    }
}
