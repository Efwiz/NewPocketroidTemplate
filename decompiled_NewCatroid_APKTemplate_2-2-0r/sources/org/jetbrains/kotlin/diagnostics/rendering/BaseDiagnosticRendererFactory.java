package org.jetbrains.kotlin.diagnostics.rendering;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderer;

/* JADX INFO: compiled from: DiagnosticRendererFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0096\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/BaseDiagnosticRendererFactory;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticRendererFactory;", "()V", "MAP", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "getMAP", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "invoke", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BaseDiagnosticRendererFactory implements DiagnosticRendererFactory {
    public abstract KtDiagnosticFactoryToRendererMap getMAP();

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticRendererFactory
    public KtDiagnosticRenderer invoke(KtDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        return getMAP().get(diagnostic.getFactory());
    }
}
