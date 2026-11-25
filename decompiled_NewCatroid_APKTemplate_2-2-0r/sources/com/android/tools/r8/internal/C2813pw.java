package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2813pw extends AbstractC2884qw {
    private final C2424kx a;
    static final /* synthetic */ boolean c = true;
    private static final AbstractC2884qw b = new C2813pw(C2424kx.a());

    public C2813pw(C2424kx c2424kx) {
        super();
        if (!c && c2424kx == null) {
            throw new AssertionError();
        }
        this.a = c2424kx;
    }

    @Override // com.android.tools.r8.internal.AbstractC2884qw
    public final C2424kx b() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.AbstractC2884qw
    public final boolean d() {
        return this.a.e();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C2813pw.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C2813pw) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a.toString();
    }
}
