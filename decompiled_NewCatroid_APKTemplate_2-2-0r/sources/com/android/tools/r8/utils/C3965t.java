package com.android.tools.r8.utils;

import com.android.tools.r8.DataDirectoryResource;
import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.DataResourceConsumer;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.internal.V5;
import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.utils.t, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3965t implements DataResourceConsumer {
    final /* synthetic */ DataResourceConsumer a;
    final /* synthetic */ C3969u b;

    C3965t(C3969u c3969u, DataResourceConsumer dataResourceConsumer) {
        this.b = c3969u;
        this.a = dataResourceConsumer;
    }

    @Override // com.android.tools.r8.DataResourceConsumer
    public final void accept(DataDirectoryResource dataDirectoryResource, DiagnosticsHandler diagnosticsHandler) {
        DataResourceConsumer dataResourceConsumer = this.a;
        if (dataResourceConsumer != null) {
            dataResourceConsumer.accept(dataDirectoryResource, diagnosticsHandler);
        }
    }

    @Override // com.android.tools.r8.DataResourceConsumer
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
        DataResourceConsumer dataResourceConsumer = this.a;
        if (dataResourceConsumer != null) {
            dataResourceConsumer.finished(diagnosticsHandler);
        }
    }

    @Override // com.android.tools.r8.DataResourceConsumer
    public final void accept(DataEntryResource dataEntryResource, DiagnosticsHandler diagnosticsHandler) {
        try {
            DataEntryResource dataEntryResourceFromBytes = DataEntryResource.fromBytes(V5.a(dataEntryResource.getByteStream()), dataEntryResource.getName(), dataEntryResource.getOrigin());
            this.b.e.a.a(dataEntryResourceFromBytes);
            DataResourceConsumer dataResourceConsumer = this.a;
            if (dataResourceConsumer != null) {
                dataResourceConsumer.accept(dataEntryResourceFromBytes, diagnosticsHandler);
            }
        } catch (ResourceException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
