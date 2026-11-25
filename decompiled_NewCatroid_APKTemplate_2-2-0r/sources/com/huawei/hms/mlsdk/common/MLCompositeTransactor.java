package com.huawei.hms.mlsdk.common;

import android.util.SparseArray;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class MLCompositeTransactor<T> implements MLAnalyzer.MLTransactor<T> {
    public TrailerFactory<T> a;
    public SparseArray<MLTrailerEvent> b = new SparseArray<>();
    public int c = 3;

    public static class Creator<T> {
        public MLCompositeTransactor<T> a;

        public Creator(TrailerFactory<T> trailerFactory) {
            MLCompositeTransactor<T> mLCompositeTransactor = new MLCompositeTransactor<>(null);
            this.a = mLCompositeTransactor;
            if (trailerFactory == null) {
                throw new IllegalArgumentException("No trailer factory supplied.");
            }
            mLCompositeTransactor.a = trailerFactory;
        }

        public MLCompositeTransactor<T> create() {
            return this.a;
        }

        public Creator<T> setMaxFrameLostCount(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Invalid maxFrameLostCount " + i);
            }
            this.a.c = i;
            return this;
        }
    }

    public class MLTrailerEvent {
        public MLResultTrailer<T> a;
        public int b = 0;

        public /* synthetic */ MLTrailerEvent(MLCompositeTransactor mLCompositeTransactor, AnonymousClass1 anonymousClass1) {
        }
    }

    public interface TrailerFactory<T> {
        MLResultTrailer<T> create(T t);
    }

    public MLCompositeTransactor() {
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer.MLTransactor
    public void destroy() {
        for (int i = 0; i < this.b.size(); i++) {
            this.b.valueAt(i).a.completeCallback();
        }
        this.b.clear();
    }

    @Override // com.huawei.hms.mlsdk.common.MLAnalyzer.MLTransactor
    public void transactResult(MLAnalyzer.Result<T> result) {
        MLTrailerEvent mLTrailerEvent;
        MLTrailerEvent mLTrailerEventValueAt;
        T tValueAt;
        SparseArray<T> analyseList = result.getAnalyseList();
        for (int i = 0; i < analyseList.size(); i++) {
            int iKeyAt = analyseList.keyAt(i);
            if (this.b.get(iKeyAt) == null && (tValueAt = analyseList.valueAt(i)) != null) {
                MLTrailerEvent mLTrailerEvent2 = new MLTrailerEvent(this, null);
                mLTrailerEvent2.a = this.a.create(tValueAt);
                mLTrailerEvent2.a.objectCreateCallback(iKeyAt, tValueAt);
                this.b.append(iKeyAt, mLTrailerEvent2);
            }
        }
        SparseArray<T> analyseList2 = result.getAnalyseList();
        HashSet hashSet = new HashSet();
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            int iKeyAt2 = this.b.keyAt(i2);
            if (analyseList2.get(iKeyAt2) == null && (mLTrailerEventValueAt = this.b.valueAt(i2)) != null) {
                mLTrailerEventValueAt.b++;
                if (mLTrailerEventValueAt.b < this.c) {
                    mLTrailerEventValueAt.a.lostCallback(result);
                } else {
                    mLTrailerEventValueAt.a.completeCallback();
                    hashSet.add(Integer.valueOf(iKeyAt2));
                }
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            this.b.delete(((Integer) it.next()).intValue());
        }
        SparseArray<T> analyseList3 = result.getAnalyseList();
        for (int i3 = 0; i3 < analyseList3.size(); i3++) {
            int iKeyAt3 = analyseList3.keyAt(i3);
            T tValueAt2 = analyseList3.valueAt(i3);
            if (tValueAt2 != null && (mLTrailerEvent = this.b.get(iKeyAt3)) != null) {
                mLTrailerEvent.b = 0;
                mLTrailerEvent.a.objectUpdateCallback(result, tValueAt2);
            }
        }
    }

    public /* synthetic */ MLCompositeTransactor(AnonymousClass1 anonymousClass1) {
    }
}
