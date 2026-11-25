package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2951rt extends AbstractC2963s10 {
    int a = 0;
    final /* synthetic */ Object[] b;

    C2951rt(Object[] objArr) {
        this.b = objArr;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a < this.b.length;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object[] objArr = this.b;
        int i = this.a;
        Object obj = objArr[i];
        objArr[i] = null;
        this.a = i + 1;
        return obj;
    }
}
