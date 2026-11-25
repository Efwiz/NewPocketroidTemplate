package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class JS extends AbstractC2562mm {
    final /* synthetic */ KS d;

    JS(KS ks) {
        this.d = ks;
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

    @Override // com.android.tools.r8.internal.AbstractC2562mm, com.android.tools.r8.internal.AbstractC1048Hm, java.util.Collection, java.util.Set
    public final int hashCode() {
        return this.d.e.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm
    final AbstractC1639am i() {
        return new IS(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return a().iterator();
    }

    @Override // com.android.tools.r8.internal.AbstractC2562mm, com.android.tools.r8.internal.AbstractC1048Hm
    final boolean j() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2562mm
    final AbstractC2178hm l() {
        return this.d;
    }
}
