package com.android.tools.r8.internal;

import java.util.Objects;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2424kx {
    static final /* synthetic */ boolean c = true;
    private final AbstractC2035fx a;
    private final AbstractC3246vx b;

    public C2424kx(AbstractC2035fx abstractC2035fx, AbstractC3246vx abstractC3246vx) {
        boolean z = c;
        if (!z && abstractC2035fx == null) {
            throw new AssertionError();
        }
        if (!z && abstractC3246vx == null) {
            throw new AssertionError();
        }
        this.a = abstractC2035fx;
        this.b = abstractC3246vx;
    }

    public static C2424kx a() {
        return new C2424kx(AbstractC2035fx.a(), AbstractC3246vx.a());
    }

    public final String b() {
        if (!f()) {
            throw new C2883qv("Attempt to obtain exact qualified type for inexact pattern");
        }
        return "L" + this.a.b().replace('.', '/') + (this.a.e() ? "" : "/") + this.b.b().e() + ";";
    }

    public final AbstractC3246vx c() {
        return this.b;
    }

    public final AbstractC2035fx d() {
        return this.a;
    }

    public final boolean e() {
        return this.a.c() && this.b.c();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C2424kx.class != obj.getClass()) {
            return false;
        }
        C2424kx c2424kx = (C2424kx) obj;
        return this.a.equals(c2424kx.a) && this.b.equals(c2424kx.b);
    }

    public final boolean f() {
        return this.a.d() && this.b.d();
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.a.hashCode()), Integer.valueOf(this.b.hashCode()));
    }

    public final String toString() {
        AbstractC2035fx abstractC2035fx = this.a;
        return abstractC2035fx + (abstractC2035fx.e() ? "" : ".") + this.b;
    }

    public static C2424kx a(String str) {
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf == 0) {
            throw new C2883qv("Unexpected '.' at index 0 in '" + str + FormatHelper.QUOTE);
        }
        C2343jxIA c2343jxIA = null;
        if (iLastIndexOf > 0) {
            return new C2342jx().a(AbstractC2035fx.a(str.substring(0, iLastIndexOf))).a(AbstractC3246vx.a(str.substring(iLastIndexOf + 1))).a();
        }
        return new C2342jx().a(AbstractC2035fx.f()).a(AbstractC3246vx.a(str)).a();
    }
}
