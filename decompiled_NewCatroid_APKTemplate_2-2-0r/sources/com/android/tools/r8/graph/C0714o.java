package com.android.tools.r8.graph;

/* JADX INFO: renamed from: com.android.tools.r8.graph.o, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0714o extends AbstractC0720p {
    private final C0722p1 a;

    public C0714o(C0722p1 c0722p1) {
        this.a = c0722p1;
    }

    @Override // com.android.tools.r8.graph.AbstractC0720p
    public final String a(String str) {
        return str.equals("Lcom/android/tools/r8/RecordTag;") ? "Ljava/lang/Record;" : str;
    }

    @Override // com.android.tools.r8.graph.AbstractC0720p
    public final C0767v2 b(C0767v2 c0767v2) {
        C0722p1 c0722p1 = this.a;
        return c0767v2 == c0722p1.b2 ? c0722p1.a2 : c0767v2;
    }

    @Override // com.android.tools.r8.graph.AbstractC0720p
    public final C0767v2 a(C0767v2 c0767v2) {
        C0722p1 c0722p1 = this.a;
        return c0767v2 == c0722p1.a2 ? c0722p1.b2 : c0767v2;
    }
}
