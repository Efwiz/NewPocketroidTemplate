package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import okhttp3.HttpUrl;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1022Gr extends AbstractC3165ur implements InterfaceC0967Er, RandomAccess, Serializable, Cloneable {
    protected C1022Gr() {
    }

    @Override // com.android.tools.r8.internal.J
    /* JADX INFO: renamed from: a */
    public final boolean add(Integer num) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final void b(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final int c(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    public final Object clone() {
        return AbstractC1053Hr.a;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        List list = (List) obj;
        return (list == this || list.isEmpty()) ? 0 : -1;
    }

    @Override // java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        return (obj instanceof List) && ((List) obj).isEmpty();
    }

    @Override // java.util.List
    public final Object get(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final int h(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC3165ur, java.util.Collection, java.util.List
    public final int hashCode() {
        return 1;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final int i(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        return -1;
    }

    @Override // com.android.tools.r8.internal.AbstractC3165ur, com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final InterfaceC3018sr iterator() {
        return AbstractC0935Dr.a;
    }

    @Override // com.android.tools.r8.internal.J
    public final boolean j(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        return -1;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er, java.util.List
    public final InterfaceC0993Fr listIterator() {
        return AbstractC0935Dr.a;
    }

    @Override // java.util.List
    public final Object remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final List subList(int i, int i2) {
        if (i == 0 && i2 == 0) {
            return this;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // com.android.tools.r8.internal.J, java.util.AbstractCollection
    public final String toString() {
        return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final void a(int i, int[] iArr, int i2, int i3) {
        if (i != 0 || i3 != 0 || i2 < 0 || i2 > iArr.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3165ur, com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final InterfaceC0841Ar iterator() {
        return AbstractC0935Dr.a;
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        return AbstractC0935Dr.a;
    }

    @Override // com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC3165ur, com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final InterfaceC0993Fr iterator() {
        return AbstractC0935Dr.a;
    }

    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        if (i == 0) {
            return AbstractC0935Dr.a;
        }
        throw new IndexOutOfBoundsException(String.valueOf(i));
    }

    @Override // com.android.tools.r8.internal.InterfaceC0967Er
    public final void a(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC3165ur, com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final Iterator iterator() {
        return AbstractC0935Dr.a;
    }
}
