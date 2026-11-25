package com.android.tools.r8.internal;

import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3318wv extends C3092tv {
    static final /* synthetic */ boolean e = true;
    private final String b;
    private final String c;
    private final String d;

    public C3318wv(String str, String str2, String str3) {
        super();
        boolean z = e;
        if (!z && str == null) {
            throw new AssertionError();
        }
        if (!z && str2 == null) {
            throw new AssertionError();
        }
        if (!z && str3 == null) {
            throw new AssertionError();
        }
        this.b = str;
        this.c = str2;
        this.d = str3;
    }

    @Override // com.android.tools.r8.internal.C3092tv
    public final String a() {
        return this.b + this.c + this.d;
    }

    @Override // com.android.tools.r8.internal.C3092tv
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C3318wv.class != obj.getClass()) {
            return false;
        }
        C3318wv c3318wv = (C3318wv) obj;
        return this.b.equals(c3318wv.b) && this.c.equals(c3318wv.c) && this.d.equals(c3318wv.d);
    }

    @Override // com.android.tools.r8.internal.C3092tv
    public final int hashCode() {
        return Objects.hash(this.b, this.c, this.d);
    }
}
