package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Lm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1161Lm extends AbstractC2562mm {
    final /* synthetic */ C1188Mm d;

    C1161Lm(C1188Mm c1188Mm) {
        this.d = c1188Mm;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    /* JADX INFO: renamed from: f */
    public final AbstractC2963s10 iterator() {
        return a().iterator();
    }

    @Override // java.lang.Iterable
    public final void forEach(Consumer consumer) {
        a().forEach(consumer);
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm
    final AbstractC1639am i() {
        return new C1135Km(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return a().iterator();
    }

    @Override // com.android.tools.r8.internal.AbstractC2562mm
    final AbstractC2178hm l() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        return a().spliterator();
    }
}
