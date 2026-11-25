package com.android.tools.r8;

import java.io.IOException;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface ProgramResourceProvider {
    default void finished(DiagnosticsHandler diagnosticsHandler) throws IOException {
    }

    default DataResourceProvider getDataResourceProvider() {
        return null;
    }

    Collection<ProgramResource> getProgramResources() throws ResourceException;
}
