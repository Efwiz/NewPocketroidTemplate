package com.android.tools.r8.naming.mappinginformation;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.internal.AbstractC1249Ot;
import com.android.tools.r8.internal.AbstractC2465lT;
import com.android.tools.r8.internal.C1333Rt;
import com.android.tools.r8.internal.C1442Vb;
import com.android.tools.r8.internal.C2307jT;
import com.android.tools.r8.internal.C2308jU;
import com.android.tools.r8.internal.C2385kT;
import com.android.tools.r8.internal.C2431l10;
import com.android.tools.r8.internal.PK;
import com.android.tools.r8.internal.RK;
import com.android.tools.r8.naming.MapVersion;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class e {
    public a a() {
        return null;
    }

    public abstract boolean a(e eVar);

    public b b() {
        return null;
    }

    public abstract e b(e eVar);

    public PK c() {
        return null;
    }

    public RK d() {
        return null;
    }

    public d e() {
        return null;
    }

    public C2307jT f() {
        return null;
    }

    public C2385kT g() {
        return null;
    }

    public AbstractC2465lT h() {
        return null;
    }

    public C2308jU i() {
        return null;
    }

    public C2431l10 j() {
        return null;
    }

    public boolean k() {
        return false;
    }

    public boolean l() {
        return this instanceof PK;
    }

    public boolean m() {
        return false;
    }

    public boolean n() {
        return false;
    }

    public boolean o() {
        return this instanceof C2308jU;
    }

    public abstract String p();

    public static void a(MapVersion mapVersion, C1333Rt c1333Rt, DiagnosticsHandler diagnosticsHandler, int i, Consumer consumer) {
        AbstractC1249Ot abstractC1249OtA = c1333Rt.a("id");
        if (abstractC1249OtA == null) {
            diagnosticsHandler.info(MappingInformationDiagnostics.b(i));
        }
        String strF = abstractC1249OtA.f();
        if (strF == null) {
            diagnosticsHandler.info(MappingInformationDiagnostics.c(i));
            return;
        }
        strF.hashCode();
        switch (strF) {
            case "com.android.tools.r8.outlineCallsite":
                PK.a(mapVersion, c1333Rt, consumer);
                break;
            case "com.android.tools.r8.outline":
                RK.a(mapVersion, consumer);
                break;
            case "com.android.tools.r8.residualsignature":
                AbstractC2465lT.a(mapVersion, c1333Rt, consumer);
                break;
            case "sourceFile":
                a.a(c1333Rt, diagnosticsHandler, i, consumer);
                break;
            case "com.android.tools.r8.synthesized":
                C1442Vb.a(mapVersion, consumer);
                break;
            case "com.android.tools.r8.mapping":
                b.a(c1333Rt, i, consumer);
                break;
            case "com.android.tools.r8.rewriteFrame":
                C2308jU.a(mapVersion, c1333Rt, consumer);
                break;
            default:
                diagnosticsHandler.info(MappingInformationDiagnostics.a(i, strF));
                C2431l10.a(strF, c1333Rt, consumer);
                break;
        }
    }

    public static void a(List list, e eVar, Consumer consumer) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            e eVar2 = (e) it.next();
            if (!eVar2.a(eVar)) {
                consumer.accept(eVar2);
                return;
            }
        }
        list.add(eVar);
    }
}
