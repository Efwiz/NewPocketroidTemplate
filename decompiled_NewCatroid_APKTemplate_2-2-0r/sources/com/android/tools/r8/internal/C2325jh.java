package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3979v2;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Iterator;
import java.util.function.BiFunction;
import okhttp3.HttpUrl;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2325jh extends AbstractC2359k7 {
    static final /* synthetic */ boolean c = true;
    private final String b;

    C2325jh(String str) {
        if (!c && str == null) {
            throw new AssertionError();
        }
        this.b = str;
    }

    public static String d(InterfaceC1348Si interfaceC1348Si) {
        return a(interfaceC1348Si, 2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(int i, InterfaceC1348Si interfaceC1348Si, InterfaceC3107u6 interfaceC3107u6) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(C0684j2 c0684j2, InterfaceC3107u6 interfaceC3107u6) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(C0702m c0702m, C0767v2 c0767v2, InterfaceC3107u6 interfaceC3107u6, BiFunction biFunction) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(C0702m c0702m, InterfaceC3107u6 interfaceC3107u6, int i, EnumC1812d20 enumC1812d20, BiFunction biFunction) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(C0702m c0702m, InterfaceC3107u6 interfaceC3107u6, C0767v2... c0767v2Arr) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(AbstractC1579a10 abstractC1579a10, C0767v2 c0767v2) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, C0767v2 c0767v2) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, C2284j7 c2284j7) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, InterfaceC3054tM interfaceC3054tM) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(InterfaceC3107u6 interfaceC3107u6, AbstractC3472z00 abstractC3472z00) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 a(BiFunction biFunction) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 b(InterfaceC3107u6 interfaceC3107u6, C0767v2 c0767v2) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 b(InterfaceC3107u6 interfaceC3107u6, C2284j7 c2284j7) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 c(InterfaceC3107u6 interfaceC3107u6, C2284j7 c2284j7) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3327x1
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C2325jh.class != obj.getClass()) {
            return false;
        }
        return this.b.equals(((C2325jh) obj).b);
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final C2325jh f() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 h() {
        return this;
    }

    public final int hashCode() {
        return this.b.hashCode();
    }

    @Override // com.android.tools.r8.internal.AbstractC2359k7
    public final AbstractC2359k7 i() {
        return this;
    }

    public final String k() {
        return this.b;
    }

    public static String c(InterfaceC1348Si interfaceC1348Si) {
        return a(interfaceC1348Si, 1);
    }

    public static String b(C0767v2 c0767v2) {
        return a(c0767v2);
    }

    public static String b(EnumC1812d20 enumC1812d20) {
        return a(enumC1812d20);
    }

    private static String a(C0767v2 c0767v2) {
        if (!c0767v2.M0() && !c0767v2.O0()) {
            if (c0767v2.U0()) {
                return "null";
            }
            if (!c && !c0767v2.W0()) {
                throw new AssertionError();
            }
            return "primitive " + c0767v2.L0();
        }
        return c0767v2.L0();
    }

    private static String a(InterfaceC1348Si interfaceC1348Si, int i) {
        if (interfaceC1348Si.a()) {
            if (interfaceC1348Si.M()) {
                if (interfaceC1348Si.l()) {
                    return "null";
                }
                if (interfaceC1348Si.G()) {
                    BS bsT = interfaceC1348Si.f().T();
                    if (bsT.r()) {
                        return a(bsT);
                    }
                    if (c || bsT.w()) {
                        return "initialized " + a(bsT);
                    }
                    throw new AssertionError();
                }
                boolean z = c;
                if (!z && !interfaceC1348Si.N()) {
                    throw new AssertionError();
                }
                C0767v2 c0767v2T = interfaceC1348Si.j().T();
                if (c0767v2T.M0()) {
                    return c0767v2T.L0();
                }
                if (z || c0767v2T.O0()) {
                    return "initialized " + c0767v2T.L0();
                }
                throw new AssertionError();
            }
            if (c || interfaceC1348Si.isPrimitive()) {
                return "primitive " + interfaceC1348Si.asPrimitive().getTypeName();
            }
            throw new AssertionError();
        }
        if (interfaceC1348Si.r()) {
            if (interfaceC1348Si.y()) {
                C0767v2 c0767v2D = interfaceC1348Si.D();
                if (c0767v2D != null) {
                    return "uninitialized " + c0767v2D.L0();
                }
                return "uninitialized-new";
            }
            return "uninitialized-this";
        }
        if (!c && !interfaceC1348Si.q() && !interfaceC1348Si.k()) {
            throw new AssertionError();
        }
        if (i == 1) {
            return "top";
        }
        return interfaceC1348Si.q() ? "a single width value" : "a double width value";
    }

    private static String a(AbstractC3472z00 abstractC3472z00) {
        if (abstractC3472z00.r()) {
            C1969f3 c1969f3A = abstractC3472z00.a();
            AbstractC3472z00 abstractC3472z00R = c1969f3A.R();
            if (!c && !abstractC3472z00R.w() && !abstractC3472z00R.H()) {
                throw new AssertionError();
            }
            boolean z = abstractC3472z00R.w() && !abstractC3472z00R.b().S().f();
            StringBuilder sb = new StringBuilder();
            if (z) {
                sb.append("(");
            }
            sb.append(a(abstractC3472z00R));
            if (z) {
                sb.append(Constant.AFTER_QUTO);
            }
            for (int i = 0; i < c1969f3A.U(); i++) {
                sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            }
            return sb.toString();
        }
        if (abstractC3472z00.w()) {
            C1229Oa c1229OaB = abstractC3472z00.b();
            StringBuilder sb2 = new StringBuilder(c1229OaB.R().L0());
            if (!c1229OaB.S().f()) {
                Iterator it = c1229OaB.S().c().iterator();
                sb2.append(" implements ").append(((C0767v2) ((C3979v2) it.next()).a()).L0());
                while (it.hasNext()) {
                    sb2.append(", ").append(((C0767v2) ((C3979v2) it.next()).a()).L0());
                }
            }
            return sb2.toString();
        }
        if (abstractC3472z00.E()) {
            return "null";
        }
        if (c || abstractC3472z00.H()) {
            return abstractC3472z00.c().Q();
        }
        throw new AssertionError();
    }

    private static String a(EnumC1812d20 enumC1812d20) {
        if (enumC1812d20.a()) {
            return "object";
        }
        return "primitive " + enumC1812d20.d().Q();
    }
}
