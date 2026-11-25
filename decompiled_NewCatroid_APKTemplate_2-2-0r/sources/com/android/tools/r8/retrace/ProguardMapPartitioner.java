package com.android.tools.r8.retrace;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.internal.GN;
import java.io.IOException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface ProguardMapPartitioner {
    static ProguardMapPartitionerBuilder<?, ?> builder(DiagnosticsHandler diagnosticsHandler) {
        return new GN.a(diagnosticsHandler);
    }

    MappingPartitionMetadata run() throws IOException;
}
