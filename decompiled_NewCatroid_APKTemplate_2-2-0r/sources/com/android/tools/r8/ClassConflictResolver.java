package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.references.ClassReference;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface ClassConflictResolver {
    Origin resolveDuplicateClass(ClassReference classReference, Collection<Origin> collection, DiagnosticsHandler diagnosticsHandler);
}
