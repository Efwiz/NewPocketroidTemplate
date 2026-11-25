package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeUnresolvedNameError;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeUnresolvedError;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "qualifier", "", "getQualifier", "()Ljava/lang/String;", "reason", "getReason", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeUnresolvedNameError implements ConeUnresolvedError {
    private final Name name;

    public ConeUnresolvedNameError(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
    }

    public final Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedError
    public String getQualifier() {
        String strAsString = this.name.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Unresolved name: " + this.name;
    }
}
