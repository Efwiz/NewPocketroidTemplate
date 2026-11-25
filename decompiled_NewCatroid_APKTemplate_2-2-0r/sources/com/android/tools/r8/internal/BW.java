package com.android.tools.r8.internal;

import com.android.tools.r8.startup.StartupProfileProvider;
import com.android.tools.r8.startup.diagnostic.MissingStartupProfileItemsDiagnostic;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class BW {
    public static StartupProfileProvider a(Path path) {
        return new AW(path, C3912j0.b());
    }

    public static String a(com.android.tools.r8.utils.A1 a1, StartupProfileProvider startupProfileProvider) throws Exception {
        AbstractC3135uW.a aVarA = AbstractC3135uW.a(a1, MissingStartupProfileItemsDiagnostic.a.c(), startupProfileProvider);
        aVarA.b();
        startupProfileProvider.getStartupProfile(aVarA);
        final StringBuilder sb = new StringBuilder();
        aVarA.build().a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.BW$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                BW.a(sb, (CW) obj);
            }
        });
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(StringBuilder sb, CW cw) {
        cw.a(sb);
        sb.append('\n');
    }
}
