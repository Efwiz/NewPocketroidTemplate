package com.android.tools.r8.naming.mappinginformation;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.internal.AbstractC1249Ot;
import com.android.tools.r8.internal.C1333Rt;
import com.android.tools.r8.internal.C1434Ut;
import java.util.function.Consumer;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class a extends d {
    private final String a;

    private a(String str) {
        this.a = str;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final a a() {
        return this;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final e b(e eVar) {
        return eVar;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final String p() {
        C1333Rt c1333Rt = new C1333Rt();
        c1333Rt.a("id", new C1434Ut("sourceFile"));
        c1333Rt.a(PsiTreeChangeEvent.PROP_FILE_NAME, new C1434Ut(this.a));
        return c1333Rt.toString();
    }

    public final String q() {
        return this.a;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final boolean a(e eVar) {
        eVar.getClass();
        return !(eVar instanceof a);
    }

    public static a a(String str) {
        return new a(str);
    }

    public static void a(C1333Rt c1333Rt, DiagnosticsHandler diagnosticsHandler, int i, Consumer consumer) {
        try {
            AbstractC1249Ot abstractC1249OtA = c1333Rt.a(PsiTreeChangeEvent.PROP_FILE_NAME);
            if (abstractC1249OtA == null) {
                diagnosticsHandler.info(MappingInformationDiagnostics.a(i, PsiTreeChangeEvent.PROP_FILE_NAME, "sourceFile"));
            }
            if (abstractC1249OtA != null) {
                consumer.accept(new a(abstractC1249OtA.f()));
            }
        } catch (IllegalStateException | UnsupportedOperationException unused) {
            diagnosticsHandler.info(MappingInformationDiagnostics.a(i));
        }
    }
}
