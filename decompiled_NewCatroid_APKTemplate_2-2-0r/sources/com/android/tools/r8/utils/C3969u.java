package com.android.tools.r8.utils;

import com.android.tools.r8.ByteDataView;
import com.android.tools.r8.DataResourceConsumer;
import com.android.tools.r8.DexFilePerClassFileConsumer;
import com.android.tools.r8.DiagnosticsHandler;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.u, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3969u extends DexFilePerClassFileConsumer.ForwardingConsumer {
    static final /* synthetic */ boolean f = true;
    private TreeMap c;
    final /* synthetic */ DexFilePerClassFileConsumer d;
    final /* synthetic */ C3989y e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C3969u(C3989y c3989y, DexFilePerClassFileConsumer dexFilePerClassFileConsumer, DexFilePerClassFileConsumer dexFilePerClassFileConsumer2) {
        super(dexFilePerClassFileConsumer);
        this.e = c3989y;
        this.d = dexFilePerClassFileConsumer2;
        this.c = new TreeMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, C3985x c3985x) {
        this.e.a.a(str, c3985x.a, c3985x.b);
    }

    @Override // com.android.tools.r8.DexFilePerClassFileConsumer.ForwardingConsumer, com.android.tools.r8.DexFilePerClassFileConsumer
    public final void accept(String str, ByteDataView byteDataView, Set set, DiagnosticsHandler diagnosticsHandler) {
        super.accept(str, byteDataView, (Set<String>) set, diagnosticsHandler);
        byte[] bArrCopyByteData = byteDataView.copyByteData();
        synchronized (this) {
            this.c.put(str, new C3985x(set, bArrCopyByteData));
        }
    }

    @Override // com.android.tools.r8.DexFilePerClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
        super.finished(diagnosticsHandler);
        C3989y c3989y = this.e;
        if (c3989y.b) {
            if (f) {
                return;
            }
            getDataResourceConsumer();
        } else {
            c3989y.b = true;
            this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.u$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a((String) obj, (C3985x) obj2);
                }
            });
            this.c = null;
        }
    }

    @Override // com.android.tools.r8.DexFilePerClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer
    public final DataResourceConsumer getDataResourceConsumer() {
        DexFilePerClassFileConsumer dexFilePerClassFileConsumer = this.d;
        return new C3965t(this, dexFilePerClassFileConsumer != null ? dexFilePerClassFileConsumer.getDataResourceConsumer() : null);
    }
}
