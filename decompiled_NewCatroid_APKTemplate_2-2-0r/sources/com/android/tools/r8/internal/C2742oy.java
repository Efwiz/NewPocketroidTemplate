package com.android.tools.r8.internal;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oy, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2742oy {
    private C0942Dy a;
    private String b;

    public C2742oy(C0942Dy c0942Dy, String str) {
        AbstractC0936Ds.c(c0942Dy, ModuleXmlParser.TYPE);
        this.a = c0942Dy;
        this.b = str;
    }

    public final C0942Dy a() {
        return this.a;
    }

    public final String b() {
        return this.b;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C2742oy)) {
            return false;
        }
        C2742oy c2742oy = (C2742oy) obj;
        return AbstractC0936Ds.a(this.a, c2742oy.a) && AbstractC0936Ds.a((Object) this.b, (Object) c2742oy.b);
    }

    public final int hashCode() {
        int iHashCode = this.a.hashCode() * 31;
        String str = this.b;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        return "KmFlexibleTypeUpperBound(type=" + this.a + ", typeFlexibilityId=" + this.b + ')';
    }
}
