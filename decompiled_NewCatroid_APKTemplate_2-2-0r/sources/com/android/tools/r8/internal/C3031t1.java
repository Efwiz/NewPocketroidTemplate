package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.t1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3031t1 extends AbstractC3100u1 implements Serializable {
    static final /* synthetic */ boolean d = true;
    protected final InterfaceC2991sS a;
    protected final int b;
    protected int c;

    public C3031t1(InterfaceC2991sS interfaceC2991sS, int i, int i2) {
        this.a = interfaceC2991sS;
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

    @Override // com.android.tools.r8.internal.AbstractC3100u1, java.util.List
    public final boolean addAll(int i, Collection collection) {
        j(i);
        this.c = collection.size() + this.c;
        return this.a.addAll(this.b + i, collection);
    }

    @Override // com.android.tools.r8.internal.AbstractC3100u1, java.util.List
    /* JADX INFO: renamed from: e */
    public final C3031t1 subList(int i, int i2) {
        j(i);
        j(i2);
        if (i <= i2) {
            return new C3031t1(this, i, i2);
        }
        throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // java.util.List
    public final Object get(int i) {
        k(i);
        return this.a.get(this.b + i);
    }

    @Override // com.android.tools.r8.internal.AbstractC3100u1, com.android.tools.r8.internal.AbstractC2891r1, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC2703oS
    public final InterfaceC1912eK iterator() {
        return listIterator(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC3100u1
    /* JADX INFO: renamed from: l */
    public final InterfaceC2376kK listIterator(int i) {
        j(i);
        return new C2962s1(this, i);
    }

    @Override // com.android.tools.r8.internal.AbstractC3100u1, java.util.List
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

    @Override // com.android.tools.r8.internal.AbstractC3100u1, com.android.tools.r8.internal.AbstractC2891r1, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC2703oS
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

    @Override // com.android.tools.r8.internal.InterfaceC2991sS
    public final void a(int i, int i2) {
        j(i);
        j(i2);
        InterfaceC2991sS interfaceC2991sS = this.a;
        int i3 = this.b;
        interfaceC2991sS.a(i3 + i, i3 + i2);
        this.c -= i2 - i;
        if (d) {
            return;
        }
        a();
    }
}
