package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.KotlinClassMetadata;
import com.android.tools.r8.shaking.C3712e3;
import com.android.tools.r8.shaking.C3732h3;
import com.android.tools.r8.shaking.EnumC3758l3;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import com.android.tools.r8.utils.C3970u0;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1745cA {
    private static final C1671bA a;
    private static final C1671bA b;
    static final /* synthetic */ boolean c = true;

    static com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j a(C0684j2 c0684j2) {
        StringBuilder sb = new StringBuilder("(");
        for (C0767v2 c0767v2 : c0684j2.h.f.a) {
            sb.append(c0767v2.b1());
        }
        sb.append(Constant.AFTER_QUTO);
        sb.append(c0684j2.h.e.b1());
        return new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j(c0684j2.f.toString(), sb.toString());
    }

    public static C1671bA b() {
        return b;
    }

    public static C1671bA c() {
        return a;
    }

    static {
        bAIA baia = null;
        a = new C1671bA("NO_KOTLIN_INFO");
        b = new C1671bA("INVALID_KOTLIN_INFO");
    }

    static com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar, int i) {
        return new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j(jVar.c() + "$default", jVar.b().replace(Constant.AFTER_QUTO, AbstractC2619nY.a(i, "I") + "Ljava/lang/Object;)"));
    }

    public static boolean a(C0702m c0702m) {
        AbstractC0727q0 abstractC0727q0D = c0702m.f().d(c0702m.a().t3);
        if (abstractC0727q0D == null || abstractC0727q0D.I1()) {
            return true;
        }
        com.android.tools.r8.shaking.L2 l2D0 = c0702m.Y().D0();
        if (l2D0 == null || l2D0.v() == null) {
            return false;
        }
        Iterator<com.android.tools.r8.shaking.S2> it = l2D0.v().iterator();
        while (it.hasNext()) {
            if (a(it.next(), c0702m.Y().a)) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(com.android.tools.r8.shaking.S2 s2, C0722p1 c0722p1) {
        s2.getClass();
        if (s2 instanceof C3712e3) {
            return a(s2.s().K(), c0722p1);
        }
        if (!(s2 instanceof C3732h3)) {
            return false;
        }
        C3732h3 c3732h3T = s2.t();
        if (c3732h3T.H() == EnumC3758l3.KEEP_CLASS_MEMBERS || c3732h3T.G().d) {
            return false;
        }
        return c3732h3T.c().a(c0722p1.t3);
    }

    static String a(AbstractC0727q0 abstractC0727q0, String str) {
        com.android.tools.r8.graph.J3 j3D1 = abstractC0727q0.d1();
        if (j3D1 != null && j3D1.d() != null) {
            return C3970u0.c(str);
        }
        if (!abstractC0727q0.E1() && !abstractC0727q0.z1()) {
            return C3970u0.f(str);
        }
        return a(str, true);
    }

    static String a(String str, boolean z) {
        if (z) {
            return "." + C3970u0.f(str);
        }
        return C3970u0.c(str);
    }

    static int[] a() {
        return KotlinClassMetadata.b;
    }

    static Object a(Consumer consumer, Object obj) {
        consumer.accept(obj);
        return obj;
    }

    static boolean a(C0702m c0702m, InterfaceC3680a0 interfaceC3680a0, Consumer consumer, com.android.tools.r8.utils.G3 g3) {
        if (interfaceC3680a0 != null) {
            return ((Boolean) g3.a(interfaceC3680a0, consumer, c0702m)).booleanValue();
        }
        return false;
    }

    static boolean a(C0702m c0702m, List list, List list2, com.android.tools.r8.utils.G3 g3) {
        if (!c && !list2.isEmpty()) {
            throw new AssertionError();
        }
        Objects.requireNonNull(list2);
        return a(c0702m, list, new C1746cA$$ExternalSyntheticLambda0(list2), g3);
    }

    static boolean a(C0702m c0702m, List list, Consumer consumer, com.android.tools.r8.utils.G3 g3) {
        Iterator it = list.iterator();
        boolean zBooleanValue = false;
        while (it.hasNext()) {
            zBooleanValue |= ((Boolean) g3.a(it.next(), consumer, c0702m)).booleanValue();
        }
        return zBooleanValue;
    }
}
