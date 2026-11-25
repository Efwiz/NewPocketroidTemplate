package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1864dm extends AbstractC2963s10 {
    final /* synthetic */ AbstractC2963s10 a;

    C1864dm(AbstractC2963s10 abstractC2963s10) {
        this.a = abstractC2963s10;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        return ((Map.Entry) this.a.next()).getKey();
    }
}
