package com.android.tools.r8.internal;

import androidx.exifinterface.media.ExifInterface;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.dex.B$$ExternalSyntheticLambda1;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0792w4;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import net.bytebuddy.description.method.MethodDescription;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qo, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2876qo {
    public static C0729q2 a(C0722p1 c0722p1) {
        C0767v2 c0767v2E = c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;");
        ProgramResource.Kind kind = ProgramResource.Kind.CF;
        Origin originUnknown = Origin.unknown();
        com.android.tools.r8.graph.E eG = com.android.tools.r8.graph.E.g(33);
        C0767v2 c0767v2E2 = c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServer;");
        C0800x2 c0800x2S0 = C0800x2.s0();
        C0759u2 c0759u2C = c0722p1.c("InstrumentationServerImpl.java");
        int i = com.android.tools.r8.graph.W4.c;
        List listEmptyList = Collections.emptyList();
        List listEmptyList2 = Collections.emptyList();
        List listEmptyList3 = Collections.emptyList();
        int i2 = com.android.tools.r8.graph.O2.d;
        return new C0729q2(c0767v2E, kind, originUnknown, eG, c0767v2E2, c0800x2S0, c0759u2C, null, listEmptyList, listEmptyList2, listEmptyList3, null, Collections.emptyList(), C0706m3.b.f(), C0658g0.t0(), new com.android.tools.r8.graph.T0[]{com.android.tools.r8.graph.T0.d1().a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.c(JvmAbi.INSTANCE_FIELD))).a(com.android.tools.r8.graph.Q2.g(26)).a(com.android.tools.r8.androidapi.h.f()).a(), com.android.tools.r8.graph.T0.d1().a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.e("Z"), c0722p1.c("writeToLogcat"))).a(com.android.tools.r8.graph.Q2.g(10)).a(com.android.tools.r8.androidapi.h.f()).a(), com.android.tools.r8.graph.T0.d1().a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.e("Ljava/lang/String;"), c0722p1.c("logcatTag"))).a(com.android.tools.r8.graph.Q2.g(10)).a(com.android.tools.r8.androidapi.h.f()).a()}, new com.android.tools.r8.graph.T0[]{com.android.tools.r8.graph.T0.d1().a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.e("Ljava/util/LinkedHashSet;"), c0722p1.c("lines"))).a(com.android.tools.r8.graph.Q2.g(18)).a(com.android.tools.r8.androidapi.h.f()).a()}, C0792w4.a.a(b(c0722p1), c(c0722p1)), c0722p1.i(), new B$$ExternalSyntheticLambda1());
    }

    private static com.android.tools.r8.graph.W0[] b(final C0722p1 c0722p1) {
        com.android.tools.r8.graph.W0.a aVarB = com.android.tools.r8.graph.W0.Y1().a(C0725p4.a(2, true)).a(com.android.tools.r8.androidapi.h.f()).b(com.android.tools.r8.androidapi.h.f());
        Z8 z8 = Z8.h;
        return new com.android.tools.r8.graph.W0[]{aVarB.a(z8).a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.b(c0722p1.e(ExifInterface.GPS_MEASUREMENT_INTERRUPTED), new C0767v2[0]), c0722p1.c("<init>"))).a(new Function() { // from class: com.android.tools.r8.internal.qo$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC2876qo.a(c0722p1, (C0684j2) obj);
            }
        }).a(), com.android.tools.r8.graph.W0.Y1().a(C0725p4.a(9, false)).a(com.android.tools.r8.androidapi.h.f()).b(com.android.tools.r8.androidapi.h.f()).a(z8).a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), new C0767v2[0]), c0722p1.c("getInstance"))).a(new Function() { // from class: com.android.tools.r8.internal.qo$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC2876qo.b(c0722p1, (C0684j2) obj);
            }
        }).a(), com.android.tools.r8.graph.W0.Y1().a(C0725p4.a(9, false)).a(com.android.tools.r8.androidapi.h.f()).b(com.android.tools.r8.androidapi.h.f()).a(z8).a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.b(c0722p1.e(ExifInterface.GPS_MEASUREMENT_INTERRUPTED), c0722p1.e("Ljava/lang/String;")), c0722p1.c("addMethod"))).a(new Function() { // from class: com.android.tools.r8.internal.qo$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC2876qo.c(c0722p1, (C0684j2) obj);
            }
        }).a(), com.android.tools.r8.graph.W0.Y1().a(C0725p4.a(2, false)).a(com.android.tools.r8.androidapi.h.f()).b(com.android.tools.r8.androidapi.h.f()).a(z8).a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.b(c0722p1.e(ExifInterface.GPS_MEASUREMENT_INTERRUPTED), c0722p1.e("Ljava/lang/String;")), c0722p1.c("addLine"))).a(new Function() { // from class: com.android.tools.r8.internal.qo$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC2876qo.d(c0722p1, (C0684j2) obj);
            }
        }).a(), com.android.tools.r8.graph.W0.Y1().a(C0725p4.a(2, false)).a(com.android.tools.r8.androidapi.h.f()).b(com.android.tools.r8.androidapi.h.f()).a(z8).a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.b(c0722p1.e(ExifInterface.GPS_MEASUREMENT_INTERRUPTED), c0722p1.e("Ljava/lang/String;")), c0722p1.c("writeToLogcat"))).a(new Function() { // from class: com.android.tools.r8.internal.qo$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC2876qo.e(c0722p1, (C0684j2) obj);
            }
        }).a(), com.android.tools.r8.graph.W0.Y1().a(C0725p4.a(8, true)).a(com.android.tools.r8.androidapi.h.f()).b(com.android.tools.r8.androidapi.h.f()).a(z8).a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.b(c0722p1.e(ExifInterface.GPS_MEASUREMENT_INTERRUPTED), new C0767v2[0]), c0722p1.c(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME))).a(new Function() { // from class: com.android.tools.r8.internal.qo$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC2876qo.f(c0722p1, (C0684j2) obj);
            }
        }).a()};
    }

    private static com.android.tools.r8.graph.W0[] c(final C0722p1 c0722p1) {
        return new com.android.tools.r8.graph.W0[]{com.android.tools.r8.graph.W0.Y1().a(C0725p4.a(1, false)).a(com.android.tools.r8.androidapi.h.f()).b(com.android.tools.r8.androidapi.h.f()).a(Z8.h).a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.b(c0722p1.e(ExifInterface.GPS_MEASUREMENT_INTERRUPTED), c0722p1.e("Ljava/io/File;")), c0722p1.c("writeToFile"))).a(new Function() { // from class: com.android.tools.r8.internal.qo$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC2876qo.g(c0722p1, (C0684j2) obj);
            }
        }).a()};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W d(C0722p1 c0722p1, C0684j2 c0684j2) {
        M7 m7 = new M7();
        M7 m8 = new M7();
        M7 m9 = new M7();
        M7 m10 = new M7();
        M7 m11 = new M7();
        M7 m12 = new M7();
        M7 m13 = new M7();
        M7 m14 = new M7();
        M7 m15 = new M7();
        M7 m16 = new M7();
        M7 m17 = new M7();
        M7 m18 = new M7();
        C0767v2 c0767v2 = c0684j2.e;
        EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
        O7 o7 = new O7(enumC1812d20, 0);
        com.android.tools.r8.graph.Y0 y0A = c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.e("Ljava/util/LinkedHashSet;"), c0722p1.c("lines"));
        C3413y7 c3413y7 = new C3413y7(y0A, y0A);
        D8 d8 = new D8(D8.a.Dup);
        R8 r8 = new R8(enumC1812d20, 2);
        R7 r7 = new R7(EnumC3423yF.ENTER);
        O7 o8 = new O7(enumC1812d20, 0);
        com.android.tools.r8.graph.Y0 y0A2 = c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.e("Ljava/util/LinkedHashSet;"), c0722p1.c("lines"));
        C3413y7 c3413y8 = new C3413y7(y0A2, y0A2);
        O7 o9 = new O7(enumC1812d20, 1);
        I7 i7 = new I7(182, c0722p1.a(c0722p1.e("Ljava/util/LinkedHashSet;"), c0722p1.b(c0722p1.v1, c0722p1.Z1), c0722p1.c(ProductAction.ACTION_ADD)), false);
        EnumC1076Il enumC1076Il = EnumC1076Il.NE;
        EnumC1812d20 enumC1812d21 = EnumC1812d20.INT;
        C3039t7 c3039t7 = new C3039t7(enumC1076Il, enumC1812d21, m11);
        EnumC3423yF enumC3423yF = EnumC3423yF.EXIT;
        return new C0753u(c0767v2, 2, 4, AbstractC1639am.a(m7, o7, c3413y7, d8, r8, r7, m8, o8, c3413y8, o9, i7, c3039t7, m9, new O7(enumC1812d20, 2), new R7(enumC3423yF), m10, new C3186v8(), m11, new C2284j7(new C2413kp(new int[]{0, 1, 2}, new InterfaceC1348Si[]{InterfaceC1348Si.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;")), InterfaceC1348Si.b(c0722p1.X1), InterfaceC1348Si.b(c0722p1.Z1)}, 0)), new O7(enumC1812d20, 2), new R7(enumC3423yF), m12, new C2899r7(m15), m13, new C2284j7(new C2413kp(new int[]{0, 1, 2}, new InterfaceC1348Si[]{InterfaceC1348Si.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;")), InterfaceC1348Si.b(c0722p1.X1), InterfaceC1348Si.b(c0722p1.Z1)}, 0), new ArrayDeque(Arrays.asList(InterfaceC1348Si.b(c0722p1.m3)))), new R8(enumC1812d20, 3), new O7(enumC1812d20, 2), new R7(enumC3423yF), m14, new O7(enumC1812d20, 3), new V8(), m15, new C2284j7(new C2413kp(new int[]{0, 1}, new InterfaceC1348Si[]{InterfaceC1348Si.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;")), InterfaceC1348Si.b(c0722p1.X1)}, 0)), new O8(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.v1, c0722p1.c("writeToLogcat"))), new C3039t7(EnumC1076Il.EQ, enumC1812d21, m17), m16, new O7(enumC1812d20, 0), new O7(enumC1812d20, 1), new I7(183, c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.b(c0722p1.D1, c0722p1.X1), c0722p1.c("writeToLogcat")), false), m17, new C2284j7(new C2413kp(new int[]{0, 1}, new InterfaceC1348Si[]{InterfaceC1348Si.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;")), InterfaceC1348Si.b(c0722p1.X1)}, 0)), new C3186v8(), m18), AbstractC1639am.a(new W8(m8, m10, AbstractC1639am.c(c0722p1.m3), AbstractC1639am.c(m13)), new W8(m11, m12, AbstractC1639am.c(c0722p1.m3), AbstractC1639am.c(m13)), new W8(m13, m14, AbstractC1639am.c(c0722p1.m3), AbstractC1639am.c(m13))), AbstractC1639am.i(), Position.UNKNOWN, C2049g6.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W e(C0722p1 c0722p1, C0684j2 c0684j2) {
        M7 m7 = new M7();
        M7 m8 = new M7();
        M7 m9 = new M7();
        C0767v2 c0767v2 = c0684j2.e;
        O8 o8 = new O8(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.X1, c0722p1.c("logcatTag")));
        O7 o7 = new O7(EnumC1812d20.OBJECT, 1);
        C0767v2 c0767v2E = c0722p1.e("Landroid/util/Log;");
        C0767v2 c0767v3 = c0722p1.A1;
        C0767v2 c0767v4 = c0722p1.X1;
        return new C0753u(c0767v2, 2, 2, AbstractC1639am.a(m7, o8, o7, new I7(184, c0722p1.a(c0767v2E, c0722p1.b(c0767v3, c0767v4, c0767v4), c0722p1.c(ASTExpr.DEFAULT_INDEX_VARIABLE_NAME)), false), new D8(D8.a.Pop), m8, new C3186v8(), m9), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W f(C0722p1 c0722p1, C0684j2 c0684j2) {
        return new C0753u(c0684j2.e, 2, 0, AbstractC1639am.a(new M7(), new V7(null, c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;")), new D8(D8.a.Dup), new I7(183, c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("<init>")), false), new P8(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.c(JvmAbi.INSTANCE_FIELD))), new C3186v8()), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W g(C0722p1 c0722p1, C0684j2 c0684j2) {
        M7 m7 = new M7();
        M7 m8 = new M7();
        M7 m9 = new M7();
        M7 m10 = new M7();
        M7 m11 = new M7();
        M7 m12 = new M7();
        M7 m13 = new M7();
        M7 m14 = new M7();
        M7 m15 = new M7();
        M7 m16 = new M7();
        M7 m17 = new M7();
        M7 m18 = new M7();
        M7 m19 = new M7();
        M7 m20 = new M7();
        M7 m21 = new M7();
        M7 m22 = new M7();
        M7 m23 = new M7();
        C0767v2 c0767v2 = c0684j2.e;
        V7 v7 = new V7(null, c0722p1.e("Ljava/io/PrintWriter;"));
        D8.a aVar = D8.a.Dup;
        D8 d8 = new D8(aVar);
        EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
        O7 o7 = new O7(enumC1812d20, 1);
        C1891e7 c1891e7 = new C1891e7(c0722p1.c("UTF-8"));
        I7 i7 = new I7(183, c0722p1.a(c0722p1.e("Ljava/io/PrintWriter;"), c0722p1.b(c0722p1.D1, c0722p1.e("Ljava/io/File;"), c0722p1.X1), c0722p1.c("<init>")), false);
        R8 r8 = new R8(enumC1812d20, 2);
        O7 o8 = new O7(enumC1812d20, 0);
        com.android.tools.r8.graph.Y0 y0A = c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.e("Ljava/util/LinkedHashSet;"), c0722p1.c("lines"));
        C3413y7 c3413y7 = new C3413y7(y0A, y0A);
        D8 d9 = new D8(aVar);
        R8 r9 = new R8(enumC1812d20, 3);
        com.android.tools.r8.graph.Y0 y0A2 = c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.e("Ljava/util/LinkedHashSet;"), c0722p1.c("lines"));
        InterfaceC1348Si[] interfaceC1348SiArr = {InterfaceC1348Si.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;")), InterfaceC1348Si.b(c0722p1.e("Ljava/io/File;")), InterfaceC1348Si.b(c0722p1.e("Ljava/io/PrintWriter;")), InterfaceC1348Si.b(c0722p1.Z1), InterfaceC1348Si.b(c0722p1.e("Ljava/util/Iterator;"))};
        C0767v2 c0767v2E = c0722p1.e("Ljava/io/PrintWriter;");
        C0767v2 c0767v3 = c0722p1.D1;
        C0767v2[] c0767v2Arr = {c0722p1.X1};
        InterfaceC1348Si[] interfaceC1348SiArr2 = {InterfaceC1348Si.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;")), InterfaceC1348Si.b(c0722p1.e("Ljava/io/File;")), InterfaceC1348Si.b(c0722p1.e("Ljava/io/PrintWriter;")), InterfaceC1348Si.b(c0722p1.Z1)};
        EnumC3423yF enumC3423yF = EnumC3423yF.EXIT;
        return new C0753u(c0767v2, 4, 8, AbstractC1639am.a(m7, v7, d8, o7, c1891e7, i7, r8, m8, o8, c3413y7, d9, r9, new R7(EnumC3423yF.ENTER), m9, new O7(enumC1812d20, 0), new C3413y7(y0A2, y0A2), new I7(182, c0722p1.a(c0722p1.e("Ljava/util/LinkedHashSet;"), c0722p1.b(c0722p1.e("Ljava/util/Iterator;"), new C0767v2[0]), c0722p1.c("iterator")), false), new R8(enumC1812d20, 4), m10, new C2284j7(new C2413kp(new int[]{0, 1, 2, 3, 4}, interfaceC1348SiArr, 0)), new O7(enumC1812d20, 4), new I7(185, c0722p1.a(c0722p1.e("Ljava/util/Iterator;"), c0722p1.b(c0722p1.v1, new C0767v2[0]), c0722p1.c("hasNext")), true), new C3039t7(EnumC1076Il.EQ, EnumC1812d20.INT, m13), new O7(enumC1812d20, 4), new I7(185, c0722p1.a(c0722p1.e("Ljava/util/Iterator;"), c0722p1.b(c0722p1.Z1, new C0767v2[0]), c0722p1.c(ES6Iterator.NEXT_METHOD)), true), new M6(c0722p1.X1, false), new R8(enumC1812d20, 5), m11, new O7(enumC1812d20, 2), new O7(enumC1812d20, 5), new I7(182, c0722p1.a(c0767v2E, c0722p1.b(c0767v3, c0767v2Arr), c0722p1.c("println")), false), m12, new C2899r7(m10), m13, new C2284j7(new C2413kp(new int[]{0, 1, 2, 3}, interfaceC1348SiArr2, 0)), new O7(enumC1812d20, 3), new R7(enumC3423yF), m14, new C2899r7(m17), m15, new C2284j7(new C2413kp(new int[]{0, 1, 2, 3}, new InterfaceC1348Si[]{InterfaceC1348Si.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;")), InterfaceC1348Si.b(c0722p1.e("Ljava/io/File;")), InterfaceC1348Si.b(c0722p1.e("Ljava/io/PrintWriter;")), InterfaceC1348Si.b(c0722p1.Z1)}, 0), new ArrayDeque(Arrays.asList(InterfaceC1348Si.b(c0722p1.m3)))), new R8(enumC1812d20, 6), new O7(enumC1812d20, 3), new R7(enumC3423yF), m16, new O7(enumC1812d20, 6), new V8(), m17, new C2284j7(new C2413kp(new int[]{0, 1, 2}, new InterfaceC1348Si[]{InterfaceC1348Si.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;")), InterfaceC1348Si.b(c0722p1.e("Ljava/io/File;")), InterfaceC1348Si.b(c0722p1.e("Ljava/io/PrintWriter;"))}, 0)), new O7(enumC1812d20, 2), new I7(182, c0722p1.a(c0722p1.e("Ljava/io/PrintWriter;"), c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("close")), false), m18, new C2899r7(m22), m19, new C2284j7(new C2413kp(new int[]{0, 1, 2}, new InterfaceC1348Si[]{InterfaceC1348Si.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;")), InterfaceC1348Si.b(c0722p1.e("Ljava/io/File;")), InterfaceC1348Si.b(c0722p1.e("Ljava/io/PrintWriter;"))}, 0), new ArrayDeque(Arrays.asList(InterfaceC1348Si.b(c0722p1.m3)))), new R8(enumC1812d20, 7), m20, new O7(enumC1812d20, 2), new I7(182, c0722p1.a(c0722p1.e("Ljava/io/PrintWriter;"), c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("close")), false), m21, new O7(enumC1812d20, 7), new V8(), m22, new C2284j7(new C2413kp(new int[]{0, 1, 2}, new InterfaceC1348Si[]{InterfaceC1348Si.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;")), InterfaceC1348Si.b(c0722p1.e("Ljava/io/File;")), InterfaceC1348Si.b(c0722p1.e("Ljava/io/PrintWriter;"))}, 0)), new C3186v8(), m23), AbstractC1639am.a(new W8(m9, m14, AbstractC1639am.c(c0722p1.m3), AbstractC1639am.c(m15)), new W8(m15, m16, AbstractC1639am.c(c0722p1.m3), AbstractC1639am.c(m15)), new W8(m8, m17, AbstractC1639am.c(c0722p1.m3), AbstractC1639am.c(m19)), new W8(m19, m20, AbstractC1639am.c(c0722p1.m3), AbstractC1639am.c(m19))), AbstractC1639am.i(), Position.UNKNOWN, C2049g6.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W c(C0722p1 c0722p1, C0684j2 c0684j2) {
        return new C0753u(c0684j2.e, 2, 1, AbstractC1639am.a(new M7(), new I7(184, c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), new C0767v2[0]), c0722p1.c("getInstance")), false), new O7(EnumC1812d20.OBJECT, 0), new I7(183, c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.b(c0722p1.D1, c0722p1.X1), c0722p1.c("addLine")), false), new M7(), new C3186v8(), new M7()), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W a(C0722p1 c0722p1, C0684j2 c0684j2) {
        M7 m7 = new M7();
        M7 m8 = new M7();
        M7 m9 = new M7();
        M7 m10 = new M7();
        C0767v2 c0767v2 = c0684j2.e;
        EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
        O7 o7 = new O7(enumC1812d20, 0);
        I7 i7 = new I7(183, c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServer;"), c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("<init>")), false);
        O7 o8 = new O7(enumC1812d20, 0);
        V7 v7 = new V7(null, c0722p1.e("Ljava/util/LinkedHashSet;"));
        D8 d8 = new D8(D8.a.Dup);
        I7 i8 = new I7(183, c0722p1.a(c0722p1.e("Ljava/util/LinkedHashSet;"), c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("<init>")), false);
        com.android.tools.r8.graph.Y0 y0A = c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.e("Ljava/util/LinkedHashSet;"), c0722p1.c("lines"));
        return new C0753u(c0767v2, 3, 1, AbstractC1639am.a(m7, o7, i7, m8, o8, v7, d8, i8, new C3482z7(y0A, y0A), m9, new C3186v8(), m10, new B7[0]), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W b(C0722p1 c0722p1, C0684j2 c0684j2) {
        return new C0753u(c0684j2.e, 1, 0, AbstractC1639am.a(new M7(), new O8(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.c(JvmAbi.INSTANCE_FIELD))), new C3109u8(EnumC1812d20.OBJECT)), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }
}
