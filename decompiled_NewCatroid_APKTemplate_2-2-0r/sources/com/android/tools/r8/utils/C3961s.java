package com.android.tools.r8.utils;

import com.android.tools.r8.ByteDataView;
import com.android.tools.r8.DataResourceConsumer;
import com.android.tools.r8.DexIndexedConsumer;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.internal.C2807pq;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.s, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3961s extends DexIndexedConsumer.ForwardingConsumer {
    static final /* synthetic */ boolean f = true;
    private C2807pq c;
    final /* synthetic */ DexIndexedConsumer d;
    final /* synthetic */ C3989y e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C3961s(C3989y c3989y, DexIndexedConsumer dexIndexedConsumer, DexIndexedConsumer dexIndexedConsumer2) {
        super(dexIndexedConsumer);
        this.e = c3989y;
        this.d = dexIndexedConsumer2;
        this.c = new C2807pq();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Integer num, C3985x c3985x) {
        this.e.a.a(c3985x.a, c3985x.b);
    }

    @Override // com.android.tools.r8.DexIndexedConsumer.ForwardingConsumer, com.android.tools.r8.DexIndexedConsumer
    public final void accept(int i, ByteDataView byteDataView, Set set, DiagnosticsHandler diagnosticsHandler) {
        super.accept(i, byteDataView, (Set<String>) set, diagnosticsHandler);
        byte[] bArrCopyByteData = byteDataView.copyByteData();
        synchronized (this) {
            this.c.a(i, new C3985x(set, bArrCopyByteData));
        }
    }

    @Override // com.android.tools.r8.DexIndexedConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
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
            this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.s$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a((Integer) obj, (C3985x) obj2);
                }
            });
            this.c = null;
        }
    }

    @Override // com.android.tools.r8.DexIndexedConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer
    public final DataResourceConsumer getDataResourceConsumer() {
        DexIndexedConsumer dexIndexedConsumer = this.d;
        return new r(this, dexIndexedConsumer != null ? dexIndexedConsumer.getDataResourceConsumer() : null);
    }
}
