package com.android.tools.r8.internal;

import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3321wx {
    private final String a;
    private final Map b;

    public C3321wx(String str, Map map) {
        AbstractC0936Ds.c(str, "className");
        this.a = str;
        this.b = map;
    }

    public final Map<String, AbstractC3467yx> a() {
        return this.b;
    }

    public final String b() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C3321wx)) {
            return false;
        }
        C3321wx c3321wx = (C3321wx) obj;
        return AbstractC0936Ds.a((Object) this.a, (Object) c3321wx.a) && AbstractC0936Ds.a(this.b, c3321wx.b);
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 31);
    }

    public final String toString() {
        return "KmAnnotation(className=" + this.a + ", arguments=" + this.b + ')';
    }
}
