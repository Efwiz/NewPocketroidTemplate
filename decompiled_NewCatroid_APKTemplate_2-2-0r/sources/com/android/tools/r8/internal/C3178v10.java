package com.android.tools.r8.internal;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* JADX INFO: renamed from: com.android.tools.r8.internal.v10, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3178v10 extends AbstractList implements YA, RandomAccess {
    private final YA a;

    public C3178v10(YA ya) {
        this.a = ya;
    }

    @Override // com.android.tools.r8.internal.YA
    public final List c0() {
        return this.a.c0();
    }

    @Override // com.android.tools.r8.internal.YA
    public final Y5 g(int i) {
        return this.a.g(i);
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        return (String) this.a.get(i);
    }

    @Override // com.android.tools.r8.internal.YA
    public final C3178v10 h() {
        return this;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return new C3101u10(this);
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        return new C3032t10(this, i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.a.size();
    }

    @Override // com.android.tools.r8.internal.YA
    public final void a(Y5 y5) {
        throw new UnsupportedOperationException();
    }
}
