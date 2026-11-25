package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2486lm extends AbstractC2562mm {
    private final transient AbstractC2178hm d;
    private final transient AbstractC1639am e;

    C2486lm(AbstractC2178hm abstractC2178hm, AbstractC1639am abstractC1639am) {
        this.d = abstractC2178hm;
        this.e = abstractC1639am;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int a(int i, Object[] objArr) {
        return this.e.a(i, objArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    /* JADX INFO: renamed from: f */
    public final AbstractC2963s10 iterator() {
        return this.e.iterator();
    }

    @Override // java.lang.Iterable
    public final void forEach(Consumer consumer) {
        this.e.forEach(consumer);
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm
    final AbstractC1639am i() {
        return new HS(this, this.e);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return this.e.iterator();
    }

    @Override // com.android.tools.r8.internal.AbstractC2562mm
    final AbstractC2178hm l() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        return this.e.spliterator();
    }
}
