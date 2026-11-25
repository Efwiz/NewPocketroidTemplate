package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0902Cm extends AbstractC1160Ll {
    final /* synthetic */ AbstractC0930Dm c;

    C0902Cm(AbstractC0930Dm abstractC0930Dm) {
        this.c = abstractC0930Dm;
    }

    @Override // java.util.List
    public final Object get(int i) {
        return this.c.get(i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1160Ll
    final AbstractC1271Pl k() {
        return this.c;
    }
}
