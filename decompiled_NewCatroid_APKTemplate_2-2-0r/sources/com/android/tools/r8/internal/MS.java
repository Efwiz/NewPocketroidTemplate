package com.android.tools.r8.internal;

import java.util.Spliterator;
import java.util.Spliterators;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class MS extends AbstractC1639am {
    static final AbstractC1639am d = new MS(new Object[0]);
    final transient Object[] c;

    MS(Object[] objArr) {
        this.c = objArr;
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, com.android.tools.r8.internal.AbstractC1271Pl
    final int a(int i, Object[] objArr) {
        Object[] objArr2 = this.c;
        System.arraycopy(objArr2, 0, objArr, i, objArr2.length);
        return i + this.c.length;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final Object[] b() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int c() {
        return this.c.length;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int d() {
        return 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return false;
    }

    @Override // java.util.List
    public final Object get(int i) {
        return this.c[i];
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.util.List
    /* JADX INFO: renamed from: j */
    public final AbstractC3253w10 listIterator(int i) {
        Object[] objArr = this.c;
        int length = objArr.length;
        AbstractC3123uM.a(length >= 0);
        AbstractC3123uM.a(0, length, objArr.length);
        AbstractC3123uM.b(i, length);
        return length == 0 ? C3316wt.e : new C3316wt(objArr, length, i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.c.length;
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, com.android.tools.r8.internal.AbstractC1271Pl, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        return Spliterators.spliterator(this.c, 1296);
    }
}
