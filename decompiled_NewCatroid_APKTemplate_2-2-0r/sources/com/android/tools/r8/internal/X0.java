package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class X0 extends Y0 implements Serializable {
    static final /* synthetic */ boolean d = true;
    protected final InterfaceC2298jK a;
    protected final int b;
    protected int c;

    public X0(InterfaceC2298jK interfaceC2298jK, int i, int i2) {
        this.a = interfaceC2298jK;
        this.b = i;
        this.c = i2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        this.a.add(this.c, obj);
        this.c++;
        if (!d) {
            a();
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.Y0, java.util.List
    public final boolean addAll(int i, Collection collection) {
        j(i);
        this.c = collection.size() + this.c;
        return this.a.addAll(this.b + i, collection);
    }

    @Override // com.android.tools.r8.internal.Y0, java.lang.Comparable
    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return compareTo((List) obj);
    }

    @Override // com.android.tools.r8.internal.Y0, java.util.List
    /* JADX INFO: renamed from: e */
    public final X0 subList(int i, int i2) {
        j(i);
        j(i2);
        if (i <= i2) {
            return new X0(this, i, i2);
        }
        throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // java.util.List
    public final Object get(int i) {
        k(i);
        return this.a.get(this.b + i);
    }

    @Override // com.android.tools.r8.internal.Y0, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final InterfaceC1912eK iterator() {
        return listIterator(0);
    }

    @Override // com.android.tools.r8.internal.Y0
    /* JADX INFO: renamed from: l */
    public final InterfaceC2376kK listIterator(int i) {
        j(i);
        return new W0(this, i);
    }

    @Override // com.android.tools.r8.internal.Y0, java.util.List
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.List
    public final Object remove(int i) {
        k(i);
        this.c--;
        return this.a.remove(this.b + i);
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        k(i);
        return this.a.set(this.b + i, obj);
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

    @Override // com.android.tools.r8.internal.Y0, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final Iterator iterator() {
        return listIterator(0);
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        j(i);
        this.a.add(this.b + i, obj);
        this.c++;
        if (d) {
            return;
        }
        a();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2298jK
    public final void a(int i, int i2) {
        j(i);
        j(i2);
        InterfaceC2298jK interfaceC2298jK = this.a;
        int i3 = this.b;
        interfaceC2298jK.a(i3 + i, i3 + i2);
        this.c -= i2 - i;
        if (d) {
            return;
        }
        a();
    }
}
