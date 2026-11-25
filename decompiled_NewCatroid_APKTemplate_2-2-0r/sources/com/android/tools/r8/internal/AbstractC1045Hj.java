package com.android.tools.r8.internal;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Hj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1045Hj extends B0 implements Serializable {
    protected AbstractC1045Hj() {
    }

    static Method a(Class cls, Class... clsArr) {
        try {
            return cls.getMethod("valueOf", clsArr);
        } catch (NoSuchMethodException e) {
            String name2 = cls.getName();
            throw new RuntimeException(new StringBuilder("valueOf".length() + name2.length() + 45).append("Generated message class \"").append(name2).append("\" missing method \"valueOf\".").toString(), e);
        }
    }

    protected AbstractC1045Hj(int i) {
    }

    protected boolean a(C2396kb c2396kb, C2547mb c2547mb, C2867qh c2867qh, int i) {
        return c2396kb.a(i, c2547mb);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0032  */
    static boolean a(C1785ci c1785ci, B0 b0, C2396kb c2396kb, C2547mb c2547mb, C2867qh c2867qh, int i) {
        boolean z;
        Object objC;
        B0 b1;
        int i2 = i & 7;
        C1012Gj c1012GjA = c2867qh.a(i >>> 3, b0);
        boolean z2 = false;
        if (c1012GjA == null) {
            z = false;
            z2 = true;
        } else {
            L20 l20 = c1012GjA.d.b;
            int i3 = C1785ci.e;
            if (i2 == l20.b()) {
                z = false;
            } else {
                C0985Fj c0985Fj = c1012GjA.d;
                if (c0985Fj.c && c0985Fj.b.c()) {
                    L20 l21 = c1012GjA.d.b;
                    if (i2 == 2) {
                        z = true;
                    } else {
                        z = false;
                        z2 = true;
                    }
                } else {
                    z = false;
                    z2 = true;
                }
            }
        }
        if (z2) {
            return c2396kb.a(i, c2547mb);
        }
        A0 a0D = null;
        if (z) {
            int iC = c2396kb.c(c2396kb.j());
            if (c1012GjA.d.b == L20.g) {
                if (c2396kb.a() > 0) {
                    c2396kb.j();
                    c1012GjA.d.getClass();
                    throw null;
                }
            } else {
                while (c2396kb.a() > 0) {
                    c1785ci.a(c1012GjA.d, C1785ci.a(c2396kb, c1012GjA.d.b));
                }
            }
            c2396kb.b(iC);
            return true;
        }
        int iOrdinal = c1012GjA.d.b.a().ordinal();
        if (iOrdinal != 7) {
            if (iOrdinal != 8) {
                objC = C1785ci.a(c2396kb, c1012GjA.d.b);
            } else {
                C0985Fj c0985Fj2 = c1012GjA.d;
                if (!c0985Fj2.c && (b1 = (B0) c1785ci.a(c0985Fj2)) != null) {
                    a0D = b1.e();
                }
                if (a0D == null) {
                    a0D = c1012GjA.c.d();
                }
                C0985Fj c0985Fj3 = c1012GjA.d;
                if (c0985Fj3.b == L20.e) {
                    c2396kb.a(c0985Fj3.a, a0D, c2867qh);
                } else {
                    c2396kb.a(a0D, c2867qh);
                }
                objC = a0D.c();
            }
            C0985Fj c0985Fj4 = c1012GjA.d;
            if (c0985Fj4.c) {
                c1785ci.a(c0985Fj4, c1012GjA.b(objC));
                return true;
            }
            c1785ci.c(c0985Fj4, c1012GjA.b(objC));
            return true;
        }
        c2396kb.j();
        c1012GjA.d.getClass();
        throw null;
    }

    public static C1012Gj a(AbstractC0958Ej abstractC0958Ej, Serializable serializable, AbstractC1045Hj abstractC1045Hj, int i, L20 l20, Class cls) {
        return new C1012Gj(abstractC0958Ej, serializable, abstractC1045Hj, new C0985Fj(i, l20, false), cls);
    }

    public static C1012Gj a(AbstractC0958Ej abstractC0958Ej, AbstractC1045Hj abstractC1045Hj, int i, L20 l20, Class cls) {
        return new C1012Gj(abstractC0958Ej, Collections.emptyList(), abstractC1045Hj, new C0985Fj(i, l20, true), cls);
    }
}
