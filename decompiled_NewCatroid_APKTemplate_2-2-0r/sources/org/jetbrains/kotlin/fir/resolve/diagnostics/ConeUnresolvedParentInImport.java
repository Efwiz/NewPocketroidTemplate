package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeUnresolvedParentInImport;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "parentClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "(Lorg/jetbrains/kotlin/name/ClassId;)V", "getParentClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeUnresolvedParentInImport implements ConeDiagnostic {
    private final ClassId parentClassId;

    public ConeUnresolvedParentInImport(ClassId parentClassId) {
        Intrinsics.checkNotNullParameter(parentClassId, "parentClassId");
        this.parentClassId = parentClassId;
    }

    public final ClassId getParentClassId() {
        return this.parentClassId;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "unresolved import";
    }
}
