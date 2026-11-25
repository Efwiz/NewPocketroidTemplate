package com.huawei.hms.mlsdk.mlvision;

import android.util.SparseArray;

/* JADX INFO: compiled from: BidirectionArray.java */
/* JADX INFO: loaded from: classes10.dex */
public class g {
    private static final Object b = new Object();
    private SparseArray<Integer> a;

    public g() {
        new SparseArray();
        this.a = new SparseArray<>();
    }

    public int a(int i) {
        synchronized (b) {
            if (this.a.get(i) == null) {
                return -1;
            }
            return this.a.get(i).intValue();
        }
    }
}
