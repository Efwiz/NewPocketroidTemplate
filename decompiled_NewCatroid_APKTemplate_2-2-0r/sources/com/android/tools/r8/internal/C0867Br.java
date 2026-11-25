package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Br, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0867Br extends O implements Serializable, Cloneable {
    protected C0867Br() {
    }

    public final Object clone() {
        return AbstractC0935Dr.a;
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return false;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3018sr
    public final int i() {
        throw new NoSuchElementException();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return 0;
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return -1;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0841Ar
    public final int t() {
        throw new NoSuchElementException();
    }
}
