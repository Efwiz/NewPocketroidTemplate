package com.android.tools.r8.internal;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.y, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3401y extends AbstractC3324x implements SortedMap {
    protected AbstractC3401y() {
    }

    @Override // com.android.tools.r8.internal.AbstractC3324x, java.util.Map
    public final Set entrySet() {
        C0964Eo c0964Eo = (C0964Eo) this;
        if (c0964Eo.l == null) {
            c0964Eo.l = new C0864Bo(c0964Eo, 0);
        }
        return c0964Eo.l;
    }

    @Override // java.util.SortedMap
    public final Object firstKey() {
        C0964Eo c0964Eo = (C0964Eo) this;
        if (c0964Eo.k != 0) {
            return Integer.valueOf(c0964Eo.b[c0964Eo.f]);
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedMap
    public final SortedMap headMap(Object obj) {
        ((Integer) obj).intValue();
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC3324x
    /* JADX INFO: renamed from: k */
    public final InterfaceC2529mK entrySet() {
        C0964Eo c0964Eo = (C0964Eo) this;
        if (c0964Eo.l == null) {
            c0964Eo.l = new C0864Bo(c0964Eo, 0);
        }
        return c0964Eo.l;
    }

    @Override // java.util.Map, java.util.SortedMap
    public abstract InterfaceC1357Sr keySet();

    @Override // java.util.SortedMap
    public final Object lastKey() {
        C0964Eo c0964Eo = (C0964Eo) this;
        if (c0964Eo.k != 0) {
            return Integer.valueOf(c0964Eo.b[c0964Eo.g]);
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.SortedMap
    public final SortedMap subMap(Object obj, Object obj2) {
        ((Integer) obj).intValue();
        ((Integer) obj2).intValue();
        throw new UnsupportedOperationException();
    }

    @Override // java.util.SortedMap
    public final SortedMap tailMap(Object obj) {
        ((Integer) obj).intValue();
        throw new UnsupportedOperationException();
    }
}
