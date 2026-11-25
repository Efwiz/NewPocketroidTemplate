package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class M extends N implements Serializable {
    static final /* synthetic */ boolean d = true;
    protected final InterfaceC0967Er a;
    protected final int b;
    protected int c;

    public M(InterfaceC0967Er interfaceC0967Er, int i, int i2) {
        this.a = interfaceC0967Er;
        this.b = i;
        this.c = i2;
    }

    @Override // com.android.tools.r8.internal.N, java.util.List
    public final void add(int i, Object obj) {
        b(i, ((Integer) obj).intValue());
    }

    @Override // com.android.tools.r8.internal.N, java.util.List
    public final boolean addAll(int i, Collection collection) {
        k(i);
        this.c = collection.size() + this.c;
        return this.a.addAll(this.b + i, collection);
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final void b(int i, int i2) {
        k(i);
        this.a.b(this.b + i, i2);
        this.c++;
        if (d) {
            return;
        }
        a();
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final int c(int i, int i2) {
        l(i);
        return this.a.c(this.b + i, i2);
    }

    @Override // com.android.tools.r8.internal.N, java.lang.Comparable
    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return compareTo((List) obj);
    }

    @Override // com.android.tools.r8.internal.N, java.util.List
    /* JADX INFO: renamed from: e */
    public final InterfaceC0967Er subList(int i, int i2) {
        k(i);
        k(i2);
        if (i <= i2) {
            return new M(this, i, i2);
        }
        throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // com.android.tools.r8.internal.N, java.util.List
    public final Object get(int i) {
        return Integer.valueOf(i(i));
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final int h(int i) {
        l(i);
        this.c--;
        return this.a.h(this.b + i);
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final int i(int i) {
        l(i);
        return this.a.i(this.b + i);
    }

    @Override // com.android.tools.r8.internal.N, com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final InterfaceC0841Ar iterator() {
        return listIterator(0);
    }

    @Override // com.android.tools.r8.internal.J
    public final boolean j(int i) {
        int iM = m(i);
        if (iM == -1) {
            return false;
        }
        this.c--;
        this.a.h(this.b + iM);
        if (!d) {
            a();
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.N, java.util.List
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // com.android.tools.r8.internal.N
    /* JADX INFO: renamed from: o */
    public final InterfaceC0993Fr listIterator(int i) {
        k(i);
        return new L(this, i);
    }

    @Override // com.android.tools.r8.internal.N, java.util.List
    public final Object remove(int i) {
        return Integer.valueOf(h(i));
    }

    @Override // com.android.tools.r8.internal.N, java.util.List
    public final Object set(int i, Object obj) {
        return Integer.valueOf(c(i, ((Integer) obj).intValue()));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.c - this.b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        boolean z = d;
        if (!z && this.b > this.a.size()) {
            throw new AssertionError();
        }
        if (!z && this.c > this.a.size()) {
            throw new AssertionError();
        }
        if (!z && this.c < this.b) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.N, com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final Iterator iterator() {
        return listIterator(0);
    }

    @Override // com.android.tools.r8.internal.N, com.android.tools.r8.internal.J, com.android.tools.r8.internal.InterfaceC3088tr
    public final boolean a(int i) {
        this.a.b(this.c, i);
        this.c++;
        if (!d) {
            a();
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final void a(int i, int[] iArr, int i2, int i3) {
        k(i);
        if (i + i3 <= size()) {
            this.a.a(this.b + i, iArr, i2, i3);
            return;
        }
        throw new IndexOutOfBoundsException("End index (" + i + i3 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final void a(int i, int i2) {
        k(i);
        k(i2);
        InterfaceC0967Er interfaceC0967Er = this.a;
        int i3 = this.b;
        interfaceC0967Er.a(i3 + i, i3 + i2);
        this.c -= i2 - i;
        if (d) {
            return;
        }
        a();
    }

    @Override // com.android.tools.r8.internal.N
    public final boolean a(int i, InterfaceC3088tr interfaceC3088tr) {
        k(i);
        return super.a(i, interfaceC3088tr);
    }

    @Override // com.android.tools.r8.internal.N
    public final boolean a(int i, InterfaceC0967Er interfaceC0967Er) {
        k(i);
        return a(i, (InterfaceC3088tr) interfaceC0967Er);
    }
}
