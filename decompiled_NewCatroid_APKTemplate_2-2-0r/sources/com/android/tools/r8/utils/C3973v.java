package com.android.tools.r8.utils;

import com.android.tools.r8.DataDirectoryResource;
import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.DataResourceConsumer;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.internal.V5;
import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.utils.v, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3973v implements DataResourceConsumer {
    final /* synthetic */ DataResourceConsumer a;
    final /* synthetic */ C3980w b;

    C3973v(C3980w c3980w, DataResourceConsumer dataResourceConsumer) {
        this.b = c3980w;
        this.a = dataResourceConsumer;
    }

    @Override // com.android.tools.r8.DataResourceConsumer
    public final void accept(DataDirectoryResource dataDirectoryResource, DiagnosticsHandler diagnosticsHandler) {
        this.b.e.a.a(DataDirectoryResource.fromName(dataDirectoryResource.getName(), dataDirectoryResource.getOrigin()));
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
