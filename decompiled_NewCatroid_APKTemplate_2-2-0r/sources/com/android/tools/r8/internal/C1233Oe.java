package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Oe, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1233Oe {
    static final /* synthetic */ boolean a = true;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(InterfaceC2089gZ interfaceC2089gZ) {
    }

    public static boolean b(C1333Rt c1333Rt, com.android.tools.r8.utils.O2 o2, Origin origin) {
        if (c1333Rt.b("configuration_format_version")) {
            return c1333Rt.a("configuration_format_version").b() >= 200;
        }
        o2.a(new StringDiagnostic("Invalid desugared library configuration. Expected required key 'configuration_format_version'", origin));
        throw null;
    }

    public static InterfaceC1206Ne a(com.android.tools.r8.d0 d0Var, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, boolean z, int i) {
        return a(d0Var, c0722p1, o2, z, i, new Consumer() { // from class: com.android.tools.r8.internal.Oe$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1233Oe.a((InterfaceC2089gZ) obj);
            }
        });
    }

    public static InterfaceC1206Ne a(com.android.tools.r8.d0 d0Var, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, boolean z, int i, Consumer<InterfaceC2089gZ<?>> consumer) {
        Origin origin = d0Var.getOrigin();
        if (!a && origin == null) {
            throw new AssertionError();
        }
        try {
            String strA = d0Var.a();
            C1333Rt c1333RtD = C1386Tt.a(strA).d();
            if (b(c1333RtD, o2, origin)) {
                return new C2291jD(c0722p1, o2, z, i).a(origin, strA, c1333RtD);
            }
            if (a(c1333RtD, o2, origin)) {
                return new C1185Mk(c0722p1, o2, z, i).a(origin, strA, c1333RtD, consumer);
            }
            return new C1672bB(c0722p1, o2, z, i).a(origin, strA, c1333RtD, consumer);
        } catch (Exception e) {
            o2.a(new ExceptionDiagnostic(e, origin));
            throw null;
        }
    }

    public static boolean a(C1333Rt c1333Rt, com.android.tools.r8.utils.O2 o2, Origin origin) {
        if (c1333Rt.b("configuration_format_version")) {
            int iB = c1333Rt.a("configuration_format_version").b();
            return iB >= 100 && iB < 200;
        }
        o2.a(new StringDiagnostic("Invalid desugared library configuration. Expected required key 'configuration_format_version'", origin));
        throw null;
    }
}
