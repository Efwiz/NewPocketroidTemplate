package com.android.tools.r8.internal;

import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3243vv extends C3092tv {
    private final String b;
    private final String c;
    private final String d;

    public C3243vv(String str, String str2, String str3) {
        super();
        this.b = str;
        this.c = str2;
        this.d = str3;
    }

    @Override // com.android.tools.r8.internal.C3092tv
    public final String a() {
        return this.b + this.c + ":" + this.d;
    }

    @Override // com.android.tools.r8.internal.C3092tv
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C3243vv.class != obj.getClass()) {
            return false;
        }
        C3243vv c3243vv = (C3243vv) obj;
        return this.b.equals(c3243vv.b) && this.c.equals(c3243vv.c) && this.d.equals(c3243vv.d);
    }

    @Override // com.android.tools.r8.internal.C3092tv
    public final int hashCode() {
        return Objects.hash(this.b, this.c, this.d);
    }
}
