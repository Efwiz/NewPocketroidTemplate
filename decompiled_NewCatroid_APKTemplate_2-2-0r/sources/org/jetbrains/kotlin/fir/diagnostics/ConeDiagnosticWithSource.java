package org.jetbrains.kotlin.fir.diagnostics;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;

/* JADX INFO: compiled from: ConeSimpleDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnosticWithSource;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeDiagnosticWithSource implements ConeDiagnostic {
    private final KtSourceElement source;

    public ConeDiagnosticWithSource(KtSourceElement source) {
        Intrinsics.checkNotNullParameter(source, "source");
        this.source = source;
    }

    public final KtSourceElement getSource() {
        return this.source;
    }
}
