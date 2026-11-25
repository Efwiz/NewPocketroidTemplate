package com.huawei.hms.mlsdk.face;

import android.util.SparseArray;
import com.huawei.hms.mlsdk.common.MLAnalyzer;
import com.huawei.hms.mlsdk.common.MLProminentTransactor;
import com.huawei.hms.mlsdk.common.MLResultTrailer;

/* JADX INFO: loaded from: classes10.dex */
public class MLMaxSizeFaceTransactor extends MLProminentTransactor<MLFace> {

    public static class Creator {
        private MLMaxSizeFaceTransactor MLMaxSizeFaceTransactor;

        public Creator(MLAnalyzer<MLFace> mLAnalyzer, MLResultTrailer<MLFace> mLResultTrailer) {
            this.MLMaxSizeFaceTransactor = new MLMaxSizeFaceTransactor(mLAnalyzer, mLResultTrailer);
        }

        public MLMaxSizeFaceTransactor create() {
            return this.MLMaxSizeFaceTransactor;
        }

        public Creator setMaxFrameLostCount(int i) {
            this.MLMaxSizeFaceTransactor.setMaxFrameLostCount(i);
            return this;
        }
    }

    public MLMaxSizeFaceTransactor(MLAnalyzer<MLFace> mLAnalyzer, MLResultTrailer<MLFace> mLResultTrailer) {
        super(mLAnalyzer, mLResultTrailer);
    }

    @Override // com.huawei.hms.mlsdk.common.MLProminentTransactor
    public boolean compare(Object obj, Object obj2) {
        return ((MLFace) obj).getTracingIdentity() == ((MLFace) obj2).getTracingIdentity();
    }

    @Override // com.huawei.hms.mlsdk.common.MLProminentTransactor
    public int getSpecificTarget(MLAnalyzer.Result<MLFace> result) {
        SparseArray<MLFace> analyseList = result.getAnalyseList();
        if (analyseList.size() == 0) {
            throw new IllegalArgumentException("No faces for selectFocus.");
        }
        int iKeyAt = analyseList.keyAt(0);
        float width = analyseList.valueAt(0).getWidth();
        for (int i = 1; i < analyseList.size(); i++) {
            int iKeyAt2 = analyseList.keyAt(i);
            float width2 = analyseList.valueAt(i).getWidth();
            if (width2 > width) {
                iKeyAt = iKeyAt2;
                width = width2;
            }
        }
        return iKeyAt;
    }
}
