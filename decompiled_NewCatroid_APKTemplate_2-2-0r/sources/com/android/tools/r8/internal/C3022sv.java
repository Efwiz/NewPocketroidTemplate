package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3022sv extends C3092tv {
    static final /* synthetic */ boolean c = true;
    private final String b;

    public C3022sv(String str) {
        super();
        if (!c && str == null) {
            throw new AssertionError();
        }
        this.b = str;
    }

    @Override // com.android.tools.r8.internal.C3092tv
    public final String a() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.C3092tv
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C3022sv.class != obj.getClass()) {
            return false;
        }
        return this.b.equals(((C3022sv) obj).b);
    }

    @Override // com.android.tools.r8.internal.C3092tv
    public final int hashCode() {
        return this.b.hashCode();
    }
}
