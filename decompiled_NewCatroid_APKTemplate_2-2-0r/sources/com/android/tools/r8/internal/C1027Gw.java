package com.android.tools.r8.internal;

import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1027Gw {
    private static final C1027Gw e = new C1027Gw(G1.a(), AbstractC2450lF.a(), AbstractC2450lF.a(), AbstractC2450lF.a());
    public static final /* synthetic */ int f = 0;
    private final Set a;
    private final AbstractC2450lF b;
    private final AbstractC2450lF c;
    private final AbstractC2450lF d;

    public C1027Gw(Set set, AbstractC2450lF abstractC2450lF, AbstractC2450lF abstractC2450lF2, AbstractC2450lF abstractC2450lF3) {
        this.a = set;
        this.b = abstractC2450lF;
        this.c = abstractC2450lF2;
        this.d = abstractC2450lF3;
    }

    public static C1027Gw a() {
        return e;
    }

    public final Set b() {
        return this.a;
    }

    public final AbstractC2450lF c() {
        return this.c;
    }

    public final AbstractC2450lF d() {
        return this.b;
    }

    public final AbstractC2450lF e() {
        return this.d;
    }

    public boolean f() {
        if (this.a.size() == G1.values().length) {
            AbstractC2450lF abstractC2450lF = this.b;
            abstractC2450lF.getClass();
            if (abstractC2450lF instanceof C2219iF) {
                AbstractC2450lF abstractC2450lF2 = this.c;
                abstractC2450lF2.getClass();
                if (abstractC2450lF2 instanceof C2219iF) {
                    AbstractC2450lF abstractC2450lF3 = this.d;
                    abstractC2450lF3.getClass();
                    if (abstractC2450lF3 instanceof C2219iF) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final boolean g() {
        return this.a.size() == G1.values().length;
    }

    public String toString() {
        if (f()) {
            return "*";
        }
        StringBuilder sb = new StringBuilder();
        AbstractC2925rU.a(C2781pU.a(sb), this);
        return sb.toString();
    }
}
