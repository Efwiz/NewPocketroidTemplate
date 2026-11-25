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
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: renamed from: com.android.tools.r8.internal.po, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2805po {
    public static C0729q2 a(C0722p1 c0722p1) {
        C0767v2 c0767v2E = c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServer;");
        ProgramResource.Kind kind = ProgramResource.Kind.CF;
        Origin originUnknown = Origin.unknown();
        com.android.tools.r8.graph.E eG = com.android.tools.r8.graph.E.g(1057);
        C0767v2 c0767v2E2 = c0722p1.e(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR);
        C0800x2 c0800x2S0 = C0800x2.s0();
        C0759u2 c0759u2C = c0722p1.c("InstrumentationServer.java");
        int i = com.android.tools.r8.graph.W4.c;
        List listEmptyList = Collections.emptyList();
        List listEmptyList2 = Collections.emptyList();
        List listEmptyList3 = Collections.emptyList();
        int i2 = com.android.tools.r8.graph.O2.d;
        return new C0729q2(c0767v2E, kind, originUnknown, eG, c0767v2E2, c0800x2S0, c0759u2C, null, listEmptyList, listEmptyList2, listEmptyList3, null, Collections.emptyList(), C0706m3.b.f(), C0658g0.t0(), new com.android.tools.r8.graph.T0[0], new com.android.tools.r8.graph.T0[0], C0792w4.a.a(b(c0722p1), new com.android.tools.r8.graph.W0[]{com.android.tools.r8.graph.W0.Y1().a(C0725p4.a(1025, false)).a(com.android.tools.r8.androidapi.h.f()).b(com.android.tools.r8.androidapi.h.f()).a(Z8.h).a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServer;"), c0722p1.b(c0722p1.e(ExifInterface.GPS_MEASUREMENT_INTERRUPTED), c0722p1.e("Ljava/io/File;")), c0722p1.c("writeToFile"))).a()}), c0722p1.i(), new B$$ExternalSyntheticLambda1());
    }

    private static com.android.tools.r8.graph.W0[] b(final C0722p1 c0722p1) {
        com.android.tools.r8.graph.W0.a aVarB = com.android.tools.r8.graph.W0.Y1().a(C0725p4.a(1, true)).a(com.android.tools.r8.androidapi.h.f()).b(com.android.tools.r8.androidapi.h.f());
        Z8 z8 = Z8.h;
        return new com.android.tools.r8.graph.W0[]{aVarB.a(z8).a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServer;"), c0722p1.b(c0722p1.e(ExifInterface.GPS_MEASUREMENT_INTERRUPTED), new C0767v2[0]), c0722p1.c("<init>"))).a(new Function() { // from class: com.android.tools.r8.internal.po$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC2805po.a(c0722p1, (C0684j2) obj);
            }
        }).a(), com.android.tools.r8.graph.W0.Y1().a(C0725p4.a(9, false)).a(com.android.tools.r8.androidapi.h.f()).b(com.android.tools.r8.androidapi.h.f()).a(z8).a(c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServer;"), c0722p1.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServer;"), new C0767v2[0]), c0722p1.c("getInstance"))).a(new Function() { // from class: com.android.tools.r8.internal.po$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC2805po.b(c0722p1, (C0684j2) obj);
            }
        }).a()};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W b(C0722p1 c0722p1, C0684j2 c0684j2) {
        return new C0753u(c0684j2.e, 1, 0, AbstractC1639am.a(new M7(), new I7(184, c0722p1.a(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), c0722p1.b(c0722p1.e("Lcom/android/tools/r8/startup/InstrumentationServerImpl;"), new C0767v2[0]), c0722p1.c("getInstance")), false), new C3109u8(EnumC1812d20.OBJECT)), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.W a(C0722p1 c0722p1, C0684j2 c0684j2) {
        return new C0753u(c0684j2.e, 1, 1, AbstractC1639am.a(new M7(), new O7(EnumC1812d20.OBJECT, 0), new I7(183, c0722p1.a(c0722p1.Z1, c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.c("<init>")), false), new C3186v8(), new M7()), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }
}
