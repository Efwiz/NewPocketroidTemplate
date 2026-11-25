package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Pw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1282Pw extends AbstractC1309Qw {
    static final /* synthetic */ boolean b = true;
    private final String a;

    public C1282Pw(String str) {
        super();
        if (!b && str == null) {
            throw new AssertionError();
        }
        this.a = str;
    }

    @Override // com.android.tools.r8.internal.AbstractC1309Qw
    public final C1282Pw b() {
        return this;
    }

    public final String e() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C1282Pw.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C1282Pw) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a;
    }
}
