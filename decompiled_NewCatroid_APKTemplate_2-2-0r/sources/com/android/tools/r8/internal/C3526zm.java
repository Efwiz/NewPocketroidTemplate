package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3526zm extends AbstractC1791cn {
    final /* synthetic */ AbstractC0836Am d;

    private C3526zm(AbstractC0836Am abstractC0836Am) {
        this.d = abstractC0836Am;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (!(obj instanceof ZF)) {
            return false;
        }
        ZF zf = (ZF) obj;
        return zf.a() > 0 && this.d.b(zf.b()) == zf.a();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return this.d.e();
    }

    @Override // com.android.tools.r8.internal.AbstractC1791cn
    final Object get(int i) {
        return this.d.j(i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm, java.util.Collection, java.util.Set
    public final int hashCode() {
        return this.d.hashCode();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.d.i().size();
    }

    /* synthetic */ C3526zm(AbstractC0836Am abstractC0836Am, int i) {
        this(abstractC0836Am);
    }
}
