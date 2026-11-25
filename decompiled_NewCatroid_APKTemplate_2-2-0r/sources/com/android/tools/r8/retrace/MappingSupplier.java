package com.android.tools.r8.retrace;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.retrace.MappingSupplier;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class MappingSupplier<T extends MappingSupplier<T>> {
    public abstract Retracer createRetracer(DiagnosticsHandler diagnosticsHandler);

    public abstract Set<com.android.tools.r8.naming.mappinginformation.b> getMapVersions(DiagnosticsHandler diagnosticsHandler);

    public abstract T registerClassUse(DiagnosticsHandler diagnosticsHandler, ClassReference classReference);

    public abstract void verifyMappingFileHash(DiagnosticsHandler diagnosticsHandler);
}
