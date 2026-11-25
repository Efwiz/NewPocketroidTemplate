package com.android.tools.r8.utils;

import com.android.tools.r8.ByteDataView;
import com.android.tools.r8.DexFilePerClassFileConsumer;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.GlobalSyntheticsConsumer;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.C3405y10;
import java.io.IOException;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.utils.t1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3967t1 extends AbstractC3983w1 implements DexFilePerClassFileConsumer {
    private final GlobalSyntheticsConsumer a;
    private final C3959r1 b = new C3959r1(ProgramResource.Kind.DEX);

    public C3967t1(GlobalSyntheticsConsumer globalSyntheticsConsumer) {
        this.a = globalSyntheticsConsumer;
    }

    @Override // com.android.tools.r8.utils.AbstractC3983w1
    public final void a(C0702m c0702m) {
        byte[] bArrA;
        try {
            bArrA = this.b.a();
        } catch (IOException e) {
            c0702m.Y().e.error(new ExceptionDiagnostic(e));
            bArrA = null;
        }
        if (bArrA != null) {
            this.a.accept(ByteDataView.of(bArrA), null, c0702m.Y().e);
        }
        this.a.finished(c0702m.Y().e);
    }

    @Override // com.android.tools.r8.DexFilePerClassFileConsumer
    public final synchronized void accept(String str, ByteDataView byteDataView, Set set, DiagnosticsHandler diagnosticsHandler) {
        C3959r1 c3959r1 = this.b;
        byte[] bArrCopyByteData = byteDataView.copyByteData();
        c3959r1.getClass();
        c3959r1.b.add(new C3979v2(C3959r1.a(str), bArrCopyByteData));
    }

    @Override // com.android.tools.r8.DexFilePerClassFileConsumer
    public final boolean combineSyntheticClassesWithPrimaryClass() {
        return false;
    }

    @Override // com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
        throw new C3405y10("Unexpected call to non-internal finished.");
    }
}
