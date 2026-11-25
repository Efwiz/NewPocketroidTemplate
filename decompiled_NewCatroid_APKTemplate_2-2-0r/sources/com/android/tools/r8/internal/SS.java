package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class SS extends AbstractC1048Hm {
    static final SS h = new SS(0, 0, new Object[0], null);
    private final transient Object[] d;
    final transient Object[] e;
    private final transient int f;
    private final transient int g;

    SS(int i, int i2, Object[] objArr, Object[] objArr2) {
        this.d = objArr;
        this.e = objArr2;
        this.f = i2;
        this.g = i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int a(int i, Object[] objArr) {
        Object[] objArr2 = this.d;
        System.arraycopy(objArr2, 0, objArr, i, objArr2.length);
        return i + this.d.length;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final Object[] b() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int c() {
        return this.d.length;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        Object[] objArr = this.e;
        if (obj == null || objArr == null) {
            return false;
        }
        int iA = AbstractC0900Ck.a(obj);
        while (true) {
            int i = iA & this.f;
            Object obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
            if (obj2.equals(obj)) {
                return true;
            }
            iA = i + 1;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int d() {
        return 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    /* JADX INFO: renamed from: f */
    public final AbstractC2963s10 iterator() {
        return AbstractC3533zt.a(this.d);
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm, java.util.Collection, java.util.Set
    public final int hashCode() {
        return this.g;
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm
    final AbstractC1639am i() {
        return this.e == null ? MS.d : new HS(this, AbstractC1639am.a(this.d));
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return AbstractC3533zt.a(this.d);
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm
    final boolean j() {
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.d.length;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        return Spliterators.spliterator(this.d, 1297);
    }
}
