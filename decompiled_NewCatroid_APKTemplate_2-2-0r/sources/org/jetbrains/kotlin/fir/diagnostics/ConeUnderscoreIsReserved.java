package org.jetbrains.kotlin.fir.diagnostics;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;

/* JADX INFO: compiled from: ConeSimpleDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/diagnostics/ConeUnderscoreIsReserved;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnosticWithSource;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "reason", "", "getReason", "()Ljava/lang/String;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeUnderscoreIsReserved extends ConeDiagnosticWithSource {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeUnderscoreIsReserved(KtSourceElement source) {
        super(source);
        Intrinsics.checkNotNullParameter(source, "source");
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Names _, __, ___, ..., are reserved in Kotlin";
    }
}
