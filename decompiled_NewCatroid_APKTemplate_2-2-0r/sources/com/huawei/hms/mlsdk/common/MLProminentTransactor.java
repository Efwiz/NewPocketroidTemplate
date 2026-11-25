package com.huawei.hms.mlsdk.common;

import android.util.Log;
import android.util.SparseArray;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MLProminentTransactor<T> implements MLAnalyzer.MLTransactor<T> {
    public MLAnalyzer<T> a;
    public MLResultTrailer<T> b;
    public int c = 3;
    public boolean d = false;
    public int e = 0;
    public Object f;

    public MLProminentTransactor(MLAnalyzer<T> mLAnalyzer, MLResultTrailer<T> mLResultTrailer) {
        this.a = mLAnalyzer;
        this.b = mLResultTrailer;
    }

    public boolean compare(Object obj, Object obj2) {
        return false;
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer.MLTransactor
    public void destroy() {
        this.b.completeCallback();
    }

    public abstract int getSpecificTarget(MLAnalyzer.Result<T> result);

    public void setMaxFrameLostCount(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Invalid maxFrameLostCount: " + i);
        }
        this.c = i;
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer.MLTransactor
    public void transactResult(MLAnalyzer.Result<T> result) {
        if (result == null) {
            Log.w("ConcentrateTransactor", "transactResult() result is null.");
            return;
        }
        SparseArray<T> analyseList = result.getAnalyseList();
        T t = null;
        if (analyseList.size() <= 0) {
            if (this.e != this.c) {
                this.b.lostCallback(result);
            } else {
                this.b.completeCallback();
                this.d = false;
                this.f = null;
            }
            this.e++;
            return;
        }
        this.e = 0;
        if (this.d) {
            Object obj = this.f;
            for (int i = 0; i < analyseList.size(); i++) {
                T tValueAt = analyseList.valueAt(i);
                if (compare(obj, tValueAt)) {
                    t = tValueAt;
                    break;
                }
            }
            if (t != null) {
                this.b.objectUpdateCallback(result, t);
                return;
            } else {
                this.b.completeCallback();
                this.d = false;
            }
        }
        int specificTarget = getSpecificTarget(result);
        T t2 = analyseList.get(specificTarget);
        if (t2 != null) {
            this.d = true;
            this.f = t2;
            this.a.traceItem(specificTarget);
            this.b.objectCreateCallback(specificTarget, t2);
            this.b.objectUpdateCallback(result, t2);
        }
    }
}
