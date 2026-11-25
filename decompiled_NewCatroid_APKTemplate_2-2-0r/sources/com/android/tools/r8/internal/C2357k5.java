package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.k5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2357k5 extends AbstractC2272j implements Serializable, Cloneable {
    protected C2357k5() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2272j
    public final boolean a() {
        throw new NoSuchElementException();
    }

    public final Object clone() {
        return AbstractC2588n5.a;
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return false;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return false;
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return 0;
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return -1;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2282j5
    public final boolean q() {
        throw new NoSuchElementException();
    }
}
