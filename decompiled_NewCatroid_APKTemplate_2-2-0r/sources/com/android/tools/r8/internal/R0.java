package com.android.tools.r8.internal;

import java.util.Set;
import java.util.SortedMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class R0 extends P0 implements SortedMap {
    protected R0() {
    }

    @Override // java.util.SortedMap
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public abstract R0 subMap(Object obj, Object obj2);

    @Override // java.util.SortedMap
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public abstract R0 headMap(Object obj);

    @Override // java.util.SortedMap
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public abstract R0 tailMap(Object obj);

    @Override // java.util.Map, java.util.SortedMap
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public abstract InterfaceC2842qK keySet();

    @Override // java.util.Map, java.util.SortedMap
    public final Set entrySet() {
        return k();
    }

    public abstract InterfaceC2842qK k();
}
