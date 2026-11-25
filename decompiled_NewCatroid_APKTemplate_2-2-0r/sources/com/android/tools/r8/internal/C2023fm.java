package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2023fm extends AbstractC2562mm {
    final /* synthetic */ AbstractC2107gm d;

    C2023fm(AbstractC2107gm abstractC2107gm) {
        this.d = abstractC2107gm;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    /* JADX INFO: renamed from: f */
    public final AbstractC2963s10 iterator() {
        return this.d.u();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return this.d.u();
    }

    @Override // com.android.tools.r8.internal.AbstractC2562mm
    final AbstractC2178hm l() {
        return this.d;
    }
}
