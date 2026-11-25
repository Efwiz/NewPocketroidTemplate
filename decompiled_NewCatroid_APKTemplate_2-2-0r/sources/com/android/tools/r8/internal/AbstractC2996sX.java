package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2996sX {
    static final /* synthetic */ boolean a = true;

    static String a(C0722p1 c0722p1, C0684j2 c0684j2, S10 s10, C0767v2 c0767v2) {
        if (s10.i()) {
            return null;
        }
        if (s10.K()) {
            return s10.b.F().J2().toString();
        }
        Number numberA = a(c0722p1, s10);
        if (numberA == null) {
            return null;
        }
        if (s10.w().H()) {
            if (c0767v2 == c0722p1.v1) {
                return String.valueOf(numberA.intValue() != 0);
            }
            if (c0767v2 == c0722p1.w1) {
                return String.valueOf((int) numberA.byteValue());
            }
            if (c0767v2 == c0722p1.C1) {
                return String.valueOf((int) numberA.shortValue());
            }
            if (c0767v2 == c0722p1.x1) {
                return String.valueOf((char) numberA.intValue());
            }
            if (c0767v2 == c0722p1.A1) {
                return String.valueOf(numberA.intValue());
            }
            if (c0767v2 == c0722p1.B1) {
                return String.valueOf(numberA.longValue());
            }
            if (c0767v2 == c0722p1.z1) {
                return String.valueOf(numberA.floatValue());
            }
            if (c0767v2 == c0722p1.y1) {
                return String.valueOf(numberA.doubleValue());
            }
        } else if (s10.w().E() && !c0684j2.b(c0722p1) && c0767v2 != c0722p1.G1) {
            if (a || numberA.intValue() == 0) {
                return "null";
            }
            throw new AssertionError();
        }
        return null;
    }

    static Number a(C0722p1 c0722p1, S10 s10) {
        if (s10.i()) {
            return null;
        }
        if (s10.b.B1()) {
            C0978Fc c0978FcE = s10.b.E();
            if (c0978FcE.H2() == EnumC1812d20.LONG) {
                return Long.valueOf(c0978FcE.O2());
            }
            if (c0978FcE.H2() == EnumC1812d20.FLOAT) {
                return Float.valueOf(c0978FcE.M2());
            }
            if (c0978FcE.H2() == EnumC1812d20.DOUBLE) {
                return Double.valueOf(c0978FcE.L2());
            }
            if (a || c0978FcE.H2() == EnumC1812d20.INT || c0978FcE.H2() == EnumC1812d20.OBJECT) {
                return Integer.valueOf(c0978FcE.N2());
            }
            throw new AssertionError();
        }
        if (s10.b.q2()) {
            C1831dI c1831dIW0 = s10.b.w0();
            if (!a && c1831dIW0.h1().size() != 1) {
                throw new AssertionError();
            }
            Number numberA = a(c0722p1, (S10) c1831dIW0.h1().get(0));
            if (numberA == null) {
                return null;
            }
            C0767v2 c0767v2A = c1831dIW0.i.a(c0722p1);
            if (c0767v2A == c0722p1.v1) {
                return Integer.valueOf(numberA.intValue() == 0 ? 0 : 1);
            }
            if (c0767v2A == c0722p1.w1) {
                return Byte.valueOf(numberA.byteValue());
            }
            if (c0767v2A == c0722p1.C1) {
                return Short.valueOf(numberA.shortValue());
            }
            if (c0767v2A == c0722p1.x1) {
                return Integer.valueOf(numberA.intValue());
            }
            if (c0767v2A == c0722p1.A1) {
                return Integer.valueOf(numberA.intValue());
            }
            if (c0767v2A == c0722p1.B1) {
                return Long.valueOf(numberA.longValue());
            }
            if (c0767v2A == c0722p1.z1) {
                return Float.valueOf(numberA.floatValue());
            }
            if (c0767v2A == c0722p1.y1) {
                return Double.valueOf(numberA.doubleValue());
            }
        }
        return null;
    }
}
