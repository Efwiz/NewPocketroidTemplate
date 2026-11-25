package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class P implements ClassFileConsumer, ProgramResourceProvider {
    private final ArrayList a = new ArrayList();

    P() {
    }

    @Override // com.android.tools.r8.ClassFileConsumer
    public final synchronized void accept(ByteDataView byteDataView, String str, DiagnosticsHandler diagnosticsHandler) {
        this.a.add(ProgramResource.fromBytes(Origin.unknown(), ProgramResource.Kind.CF, byteDataView.copyByteData(), Collections.singleton(str)));
    }

    @Override // com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final Collection getProgramResources() {
        return this.a;
    }
}
