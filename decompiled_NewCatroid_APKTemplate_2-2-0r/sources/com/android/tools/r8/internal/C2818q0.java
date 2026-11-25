package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* JADX INFO: renamed from: com.android.tools.r8.internal.q0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C2818q0 extends AbstractC2657o0 implements List {
    final /* synthetic */ AbstractC2889r0 f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C2818q0(AbstractC2889r0 abstractC2889r0, Object obj, List list, AbstractC2657o0 abstractC2657o0) {
        super(abstractC2889r0, obj, list, abstractC2657o0);
        this.f = abstractC2889r0;
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        c();
        boolean zIsEmpty = this.b.isEmpty();
        ((List) this.b).add(i, obj);
        AbstractC2889r0.b(this.f);
        if (zIsEmpty) {
            a();
        }
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        if (collection.isEmpty()) {
            return false;
        }
        int size = size();
        boolean zAddAll = ((List) this.b).addAll(i, collection);
        if (zAddAll) {
            AbstractC2889r0.a(this.f, this.b.size() - size);
            if (size == 0) {
                a();
            }
        }
        return zAddAll;
    }

    final List e() {
        return (List) this.b;
    }

    @Override // java.util.List
    public final Object get(int i) {
        c();
        return ((List) this.b).get(i);
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        c();
        return ((List) this.b).indexOf(obj);
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        c();
        return ((List) this.b).lastIndexOf(obj);
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        c();
        return new C2745p0(this);
    }

    @Override // java.util.List
    public final Object remove(int i) {
        c();
        Object objRemove = ((List) this.b).remove(i);
        AbstractC2889r0.c(this.f);
        d();
        return objRemove;
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        c();
        return ((List) this.b).set(i, obj);
    }

    @Override // java.util.List
    public final List subList(int i, int i2) {
        c();
        AbstractC2889r0 abstractC2889r0 = this.f;
        Object obj = this.a;
        List listSubList = ((List) this.b).subList(i, i2);
        AbstractC2657o0 abstractC2657o0 = this.c;
        if (abstractC2657o0 == null) {
            abstractC2657o0 = this;
        }
        abstractC2889r0.getClass();
        return listSubList instanceof RandomAccess ? new C2505m0(abstractC2889r0, obj, listSubList, abstractC2657o0) : new C2818q0(abstractC2889r0, obj, listSubList, abstractC2657o0);
    }

    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        c();
        return new C2745p0(this, i);
    }
}
