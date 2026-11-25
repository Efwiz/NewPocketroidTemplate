package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.c0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1730c0 extends AbstractC1807d0 implements Serializable {
    static final /* synthetic */ boolean d = true;
    protected final YC a;
    protected final int b;
    protected int c;

    public C1730c0(YC yc, int i, int i2) {
        this.a = yc;
        this.b = i;
        this.c = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
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

    @Override // com.android.tools.r8.internal.AbstractC1807d0, com.android.tools.r8.internal.AbstractC1576a0
    /* JADX INFO: renamed from: a */
    public final XC iterator() {
        return listIterator(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0, java.util.List
    public final void add(int i, Object obj) {
        a(i, ((Long) obj).longValue());
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0, java.util.List
    public final boolean addAll(int i, Collection collection) {
        j(i);
        this.c = collection.size() + this.c;
        return this.a.addAll(this.b + i, collection);
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0
    public final boolean c(long j) {
        int iD = d(j);
        if (iD == -1) {
            return false;
        }
        this.c--;
        this.a.e(this.b + iD);
        if (!d) {
            b();
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0, java.lang.Comparable
    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return compareTo((List) obj);
    }

    @Override // com.android.tools.r8.internal.YC
    public final long e(int i) {
        k(i);
        this.c--;
        return this.a.e(this.b + i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0, java.util.List
    public final Object get(int i) {
        return Long.valueOf(b(i));
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0, com.android.tools.r8.internal.AbstractC1576a0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return listIterator(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0
    /* JADX INFO: renamed from: l */
    public final AbstractC1880e0 listIterator(int i) {
        j(i);
        return new C1656b0(this, i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0, java.util.List
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0, java.util.List
    public final Object remove(int i) {
        return Long.valueOf(e(i));
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0, java.util.List
    public final Object set(int i, Object obj) {
        return Long.valueOf(b(i, ((Long) obj).longValue()));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.c - this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0
    public final boolean a(long j) {
        this.a.a(this.c, j);
        this.c++;
        if (!d) {
            b();
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.YC
    public final long b(int i) {
        k(i);
        return this.a.b(this.b + i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1807d0, java.util.List
    /* JADX INFO: renamed from: e */
    public final C1730c0 subList(int i, int i2) {
        j(i);
        j(i2);
        if (i <= i2) {
            return new C1730c0(this, i, i2);
        }
        throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // com.android.tools.r8.internal.YC
    public final void a(int i, long j) {
        j(i);
        this.a.a(this.b + i, j);
        this.c++;
        if (d) {
            return;
        }
        b();
    }

    @Override // com.android.tools.r8.internal.YC
    public final long b(int i, long j) {
        k(i);
        return this.a.b(this.b + i, j);
    }

    @Override // com.android.tools.r8.internal.YC
    public final void a(int i, int i2) {
        j(i);
        j(i2);
        YC yc = this.a;
        int i3 = this.b;
        yc.a(i3 + i, i3 + i2);
        this.c -= i2 - i;
        if (d) {
            return;
        }
        b();
    }
}
