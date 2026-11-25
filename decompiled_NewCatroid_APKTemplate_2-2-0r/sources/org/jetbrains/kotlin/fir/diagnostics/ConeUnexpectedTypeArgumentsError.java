package org.jetbrains.kotlin.fir.diagnostics;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;

/* JADX INFO: compiled from: CommonDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/diagnostics/ConeUnexpectedTypeArgumentsError;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "reason", "", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/KtSourceElement;)V", "getReason", "()Ljava/lang/String;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeUnexpectedTypeArgumentsError implements ConeDiagnostic {
    private final String reason;
    private final KtSourceElement source;

    public ConeUnexpectedTypeArgumentsError(String reason, KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        this.reason = reason;
        this.source = ktSourceElement;
    }

    public /* synthetic */ ConeUnexpectedTypeArgumentsError(String str, KtSourceElement ktSourceElement, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : ktSourceElement);
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return this.reason;
    }

    public final KtSourceElement getSource() {
        return this.source;
    }
}
