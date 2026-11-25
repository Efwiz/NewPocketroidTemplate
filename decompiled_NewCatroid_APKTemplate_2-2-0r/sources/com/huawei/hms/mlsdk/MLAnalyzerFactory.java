package com.huawei.hms.mlsdk;

import com.huawei.hms.mlsdk.classification.MLImageClassificationAnalyzer;
import com.huawei.hms.mlsdk.classification.MLLocalClassificationAnalyzerSetting;
import com.huawei.hms.mlsdk.classification.MLRemoteClassificationAnalyzerSetting;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.document.MLDocumentAnalyzer;
import com.huawei.hms.mlsdk.document.MLDocumentSetting;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzer;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.face.face3d.ML3DFaceAnalyzer;
import com.huawei.hms.mlsdk.face.face3d.ML3DFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting;
import com.huawei.hms.mlsdk.landmark.MLRemoteLandmarkAnalyzer;
import com.huawei.hms.mlsdk.landmark.MLRemoteLandmarkAnalyzerSetting;
import com.huawei.hms.mlsdk.objects.MLObjectAnalyzer;
import com.huawei.hms.mlsdk.objects.MLObjectAnalyzerSetting;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.MLRemoteProductVisionSearchAnalyzer;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.MLRemoteProductVisionSearchAnalyzerSetting;
import com.huawei.hms.mlsdk.text.MLLocalTextSetting;
import com.huawei.hms.mlsdk.text.MLRemoteTextSetting;
import com.huawei.hms.mlsdk.text.MLTextAnalyzer;

/* JADX INFO: loaded from: classes10.dex */
public class MLAnalyzerFactory {
    private final MLApplication application;
    private final MLSharedPreferenceStore spStore;
    private static MLRemoteLandmarkAnalyzerSetting remoteAnalyzerSetting = new MLRemoteLandmarkAnalyzerSetting.Factory().create();
    private static MLFaceAnalyzerSetting faceAnalyzerSetting = new MLFaceAnalyzerSetting.Factory().create();
    private static ML3DFaceAnalyzerSetting face3dAnalyzerSetting = new ML3DFaceAnalyzerSetting.Factory().create();
    private static MLLocalTextSetting localTextSetting = new MLLocalTextSetting.Factory().create();
    private static MLRemoteTextSetting remoteTextSetting = new MLRemoteTextSetting.Factory().create();
    private static MLDocumentSetting fileConfig = new MLDocumentSetting.Factory().create();
    private static MLLocalClassificationAnalyzerSetting localClassificationSetting = new MLLocalClassificationAnalyzerSetting.Factory().create();
    private static MLRemoteClassificationAnalyzerSetting remoteClassificationSetting = new MLRemoteClassificationAnalyzerSetting.Factory().create();
    private static MLObjectAnalyzerSetting objectSetting = new MLObjectAnalyzerSetting.Factory().create();
    private static MLImageSegmentationSetting imgsegSetting = new MLImageSegmentationSetting.Factory().create();
    private static MLRemoteProductVisionSearchAnalyzerSetting remoteProductVisionSearchSetting = new MLRemoteProductVisionSearchAnalyzerSetting.Factory().create();

    MLAnalyzerFactory(MLApplication mLApplication) {
        this.application = mLApplication;
        this.spStore = MLSharedPreferenceStore.getAppStore(mLApplication);
    }

    public static MLAnalyzerFactory getInstance() {
        return getInstance(MLApplication.getInstance());
    }

    public ML3DFaceAnalyzer get3DFaceAnalyzer(ML3DFaceAnalyzerSetting mL3DFaceAnalyzerSetting) {
        return ML3DFaceAnalyzer.create(this.application, mL3DFaceAnalyzerSetting);
    }

    public MLFaceAnalyzer getFaceAnalyzer(MLFaceAnalyzerSetting mLFaceAnalyzerSetting) {
        return MLFaceAnalyzer.create(this.application, mLFaceAnalyzerSetting);
    }

    public MLImageSegmentationAnalyzer getImageSegmentationAnalyzer(MLImageSegmentationSetting mLImageSegmentationSetting) {
        return MLImageSegmentationAnalyzer.create(this.application, mLImageSegmentationSetting);
    }

    public MLImageClassificationAnalyzer getLocalImageClassificationAnalyzer(MLLocalClassificationAnalyzerSetting mLLocalClassificationAnalyzerSetting) {
        return MLImageClassificationAnalyzer.create(this.application, mLLocalClassificationAnalyzerSetting);
    }

    public MLObjectAnalyzer getLocalObjectAnalyzer(MLObjectAnalyzerSetting mLObjectAnalyzerSetting) {
        return MLObjectAnalyzer.create(this.application, mLObjectAnalyzerSetting);
    }

    public MLTextAnalyzer getLocalTextAnalyzer() {
        return MLTextAnalyzer.create(this.application, localTextSetting, null, true);
    }

    public MLDocumentAnalyzer getRemoteDocumentAnalyzer(MLDocumentSetting mLDocumentSetting) {
        return MLDocumentAnalyzer.create(this.application, mLDocumentSetting);
    }

    public MLImageClassificationAnalyzer getRemoteImageClassificationAnalyzer(MLRemoteClassificationAnalyzerSetting mLRemoteClassificationAnalyzerSetting) {
        return MLImageClassificationAnalyzer.create(this.application, mLRemoteClassificationAnalyzerSetting);
    }

    public MLRemoteLandmarkAnalyzer getRemoteLandmarkAnalyzer() {
        return MLRemoteLandmarkAnalyzer.create(this.application, remoteAnalyzerSetting);
    }

    public MLRemoteProductVisionSearchAnalyzer getRemoteProductVisionSearchAnalyzer(MLRemoteProductVisionSearchAnalyzerSetting mLRemoteProductVisionSearchAnalyzerSetting) {
        return MLRemoteProductVisionSearchAnalyzer.create(this.application, mLRemoteProductVisionSearchAnalyzerSetting);
    }

    public MLTextAnalyzer getRemoteTextAnalyzer() {
        return MLTextAnalyzer.create(this.application, null, remoteTextSetting, false);
    }

    public boolean isStatisticsAllowed() {
        return this.spStore.isStatisticsAllowed();
    }

    public void setStatisticsAllowed(boolean z) {
        this.spStore.setStatisticsAllowed(z);
    }

    public static MLAnalyzerFactory getInstance(MLApplication mLApplication) {
        return new MLAnalyzerFactory(mLApplication);
    }

    public ML3DFaceAnalyzer get3DFaceAnalyzer() {
        return ML3DFaceAnalyzer.create(this.application, face3dAnalyzerSetting);
    }

    public MLFaceAnalyzer getFaceAnalyzer() {
        return MLFaceAnalyzer.create(this.application, faceAnalyzerSetting);
    }

    public MLImageSegmentationAnalyzer getImageSegmentationAnalyzer() {
        return MLImageSegmentationAnalyzer.create(this.application, imgsegSetting);
    }

    public MLImageClassificationAnalyzer getLocalImageClassificationAnalyzer() {
        return MLImageClassificationAnalyzer.create(this.application, localClassificationSetting);
    }

    public MLObjectAnalyzer getLocalObjectAnalyzer() {
        return MLObjectAnalyzer.create(this.application, objectSetting);
    }

    public MLTextAnalyzer getLocalTextAnalyzer(MLLocalTextSetting mLLocalTextSetting) {
        return MLTextAnalyzer.create(this.application, mLLocalTextSetting, null, true);
    }

    public MLDocumentAnalyzer getRemoteDocumentAnalyzer() {
        return MLDocumentAnalyzer.create(this.application, fileConfig);
    }

    public MLImageClassificationAnalyzer getRemoteImageClassificationAnalyzer() {
        return MLImageClassificationAnalyzer.create(this.application, remoteClassificationSetting);
    }

    public MLRemoteLandmarkAnalyzer getRemoteLandmarkAnalyzer(MLRemoteLandmarkAnalyzerSetting mLRemoteLandmarkAnalyzerSetting) {
        return MLRemoteLandmarkAnalyzer.create(this.application, mLRemoteLandmarkAnalyzerSetting);
    }

    public MLRemoteProductVisionSearchAnalyzer getRemoteProductVisionSearchAnalyzer() {
        return MLRemoteProductVisionSearchAnalyzer.create(this.application, remoteProductVisionSearchSetting);
    }

    public MLTextAnalyzer getRemoteTextAnalyzer(MLRemoteTextSetting mLRemoteTextSetting) {
        return MLTextAnalyzer.create(this.application, null, mLRemoteTextSetting, false);
    }
}
