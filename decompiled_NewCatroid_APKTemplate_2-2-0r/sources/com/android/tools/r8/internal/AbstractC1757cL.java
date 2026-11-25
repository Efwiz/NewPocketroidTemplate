package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1757cL {
    static final /* synthetic */ boolean b = true;
    protected final EnumC1686bL a;

    protected AbstractC1757cL(EnumC1686bL enumC1686bL) {
        this.a = enumC1686bL;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static AbstractC1757cL a(AbstractC2412ko abstractC2412ko) {
        EnumC1686bL enumC1686bL;
        Object[] objArr = 0;
        if (!abstractC2412ko.v1()) {
            if (abstractC2412ko.p2()) {
                return new YK(abstractC2412ko.t0().h);
            }
            if (!b && !abstractC2412ko.Y1()) {
                throw new AssertionError();
            }
            AbstractC1305Qs abstractC1305QsB0 = abstractC2412ko.b0();
            EnumC1812d20[] enumC1812d20Arr = new EnumC1812d20[((ArrayList) abstractC1305QsB0.h1()).size()];
            Iterator it = ((ArrayList) abstractC1305QsB0.h1()).iterator();
            int i = 0;
            while (it.hasNext()) {
                enumC1812d20Arr[i] = ((S10) it.next()).d0();
                i++;
            }
            return new XK(abstractC1305QsB0.S2(), abstractC1305QsB0.M2(), abstractC1305QsB0.b() != null, enumC1812d20Arr, abstractC1305QsB0 instanceof C1433Us ? abstractC1305QsB0.f0().T2() : null);
        }
        T4 t4X = abstractC2412ko.x();
        if (t4X.k1()) {
            enumC1686bL = EnumC1686bL.a;
        } else if (t4X instanceof C3218vY) {
            enumC1686bL = EnumC1686bL.b;
        } else if (t4X instanceof CF) {
            enumC1686bL = EnumC1686bL.c;
        } else if (t4X instanceof C3379xf) {
            enumC1686bL = EnumC1686bL.d;
        } else if (t4X instanceof US) {
            enumC1686bL = EnumC1686bL.e;
        } else if (t4X.Y1()) {
            enumC1686bL = EnumC1686bL.f;
        } else {
            if (!t4X.p2()) {
                throw new C3405y10();
            }
            enumC1686bL = EnumC1686bL.g;
        }
        return new WK(enumC1686bL, t4X.J2());
    }

    public abstract int a(AbstractC1757cL abstractC1757cL);

    public abstract int a(C2102gl c2102gl, ZK zk, int i);

    public abstract String a();

    public abstract boolean a(AbstractC1476Wj abstractC1476Wj);

    public abstract String b();

    public abstract boolean c();

    public abstract int d();

    public abstract boolean equals(Object obj);

    public abstract int hashCode();
}
