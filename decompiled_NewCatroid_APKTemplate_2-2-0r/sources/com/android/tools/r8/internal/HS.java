package com.android.tools.r8.internal;

import java.util.ListIterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class HS extends AbstractC1160Ll {
    private final AbstractC1271Pl c;
    private final AbstractC1639am d;

    HS(AbstractC1271Pl abstractC1271Pl, AbstractC1639am abstractC1639am) {
        this.c = abstractC1271Pl;
        this.d = abstractC1639am;
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, com.android.tools.r8.internal.AbstractC1271Pl
    final int a(int i, Object[] objArr) {
        return this.d.a(i, objArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final Object[] b() {
        return this.d.b();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int c() {
        return this.d.c();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int d() {
        return this.d.d();
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.lang.Iterable
    public final void forEach(Consumer consumer) {
        this.d.forEach(consumer);
    }

    @Override // java.util.List
    public final Object get(int i) {
        return this.d.get(i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am
    /* JADX INFO: renamed from: j */
    public final AbstractC3253w10 listIterator(int i) {
        return this.d.listIterator(i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1160Ll
    AbstractC1271Pl k() {
        return this.c;
    }

    final AbstractC1639am l() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, java.util.List
    public final ListIterator listIterator(int i) {
        return this.d.listIterator(i);
    }
}
