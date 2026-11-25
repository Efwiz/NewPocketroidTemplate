package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;

/* JADX INFO: compiled from: CodeAnalyzerInitializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DummyCodeAnalyzerInitializer;", "Lorg/jetbrains/kotlin/resolve/CodeAnalyzerInitializer;", "()V", "createTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DummyCodeAnalyzerInitializer implements CodeAnalyzerInitializer {
    @Override // org.jetbrains.kotlin.resolve.CodeAnalyzerInitializer
    public BindingTrace createTrace() {
        return new BindingTraceContext(true);
    }
}
