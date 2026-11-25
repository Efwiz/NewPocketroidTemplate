package com.huawei.hms.mlsdk.common;

import android.util.SparseArray;
import com.huawei.hms.ml.common.utils.SmartLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class MLCompositeAnalyzer extends MLAnalyzer<Object> {
    public List<MLAnalyzer<? extends Object>> a = new ArrayList();

    public static class Creator {
        public MLCompositeAnalyzer a = new MLCompositeAnalyzer(null);

        public Creator add(MLAnalyzer<? extends Object> mLAnalyzer) {
            this.a.a.add(mLAnalyzer);
            return this;
        }

        public MLCompositeAnalyzer create() {
            SmartLog.i("MLCompositeAnalyzer", "enter create, analyzerList size is:" + this.a.a.size());
            if (this.a.a.size() != 0) {
                return this.a;
            }
            throw new NullPointerException("Not any analyzer registed in MLCompositateAnalyzer.");
        }
    }

    public MLCompositeAnalyzer() {
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public SparseArray<Object> analyseFrame(MLFrame mLFrame) {
        SparseArray<Object> sparseArray = new SparseArray<>();
        Iterator<MLAnalyzer<? extends Object>> it = this.a.iterator();
        SmartLog.i("MLCompositeAnalyzer", "analyzerList size is:" + this.a.size());
        int i = 0;
        while (it.hasNext()) {
            SparseArray<? extends Object> sparseArrayAnalyseFrame = it.next().analyseFrame(mLFrame);
            int i2 = 0;
            while (i2 < sparseArrayAnalyseFrame.size()) {
                int iKeyAt = sparseArrayAnalyseFrame.keyAt(i2);
                if (sparseArray.get(i) != null) {
                    throw new IllegalStateException(new StringBuilder(105).append("The analyse result of item id:").append(iKeyAt).append(" already exists").toString());
                }
                sparseArray.append(i, sparseArrayAnalyseFrame.valueAt(i2));
                i2++;
                i++;
            }
        }
        return sparseArray;
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public void destroy() {
        Iterator<MLAnalyzer<? extends Object>> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().destroy();
        }
        this.a.clear();
        super.destroy();
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public boolean isAvailable() {
        Iterator<MLAnalyzer<? extends Object>> it = this.a.iterator();
        while (it.hasNext()) {
            if (!it.next().isAvailable()) {
                return false;
            }
        }
        return true;
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public void obtainPicture(MLFrame mLFrame) {
        Iterator<MLAnalyzer<? extends Object>> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().obtainPicture(mLFrame);
        }
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer
    public synchronized void setTransactor(MLAnalyzer.MLTransactor<Object> mLTransactor) {
        throw new UnsupportedOperationException("MultiDetector.setProcessor is not supported.  You should set a processor instance on each underlying detector instead.");
    }

    public /* synthetic */ MLCompositeAnalyzer(AnonymousClass1 anonymousClass1) {
    }
}
