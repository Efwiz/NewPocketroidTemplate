package com.android.tools.r8.internal;

import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1016Gm {
    Object[] a;
    int b;

    AbstractC1016Gm(int i) {
        this.a = new Object[i];
        this.b = 0;
    }

    abstract AbstractC1016Gm a(Object obj);

    abstract AbstractC1048Hm a();

    abstract AbstractC1016Gm b();

    final void b(Object obj) {
        int i = this.b + 1;
        Object[] objArr = this.a;
        if (i > objArr.length) {
            this.a = Arrays.copyOf(this.a, AbstractC1241Ol.a(objArr.length, i));
        }
        Object[] objArr2 = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        objArr2[i2] = obj;
    }

    AbstractC1016Gm c() {
        return this;
    }

    AbstractC1016Gm(AbstractC1016Gm abstractC1016Gm) {
        Object[] objArr = abstractC1016Gm.a;
        this.a = Arrays.copyOf(objArr, objArr.length);
        this.b = abstractC1016Gm.b;
    }
}
