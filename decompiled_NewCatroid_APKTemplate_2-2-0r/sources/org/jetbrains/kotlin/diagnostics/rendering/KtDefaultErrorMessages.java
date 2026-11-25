package org.jetbrains.kotlin.diagnostics.rendering;

import kotlin.Metadata;
import org.jetbrains.kotlin.diagnostics.BackendErrors;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap;

/* JADX INFO: compiled from: KtDefaultErrorMessages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/KtDefaultErrorMessages;", "Lorg/jetbrains/kotlin/diagnostics/rendering/BaseDiagnosticRendererFactory;", "()V", "MAP", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "getMAP", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDefaultErrorMessages extends BaseDiagnosticRendererFactory {
    public static final KtDefaultErrorMessages INSTANCE = new KtDefaultErrorMessages();
    private static final KtDiagnosticFactoryToRendererMap MAP;

    private KtDefaultErrorMessages() {
    }

    static {
        KtDiagnosticFactoryToRendererMap ktDiagnosticFactoryToRendererMap = new KtDiagnosticFactoryToRendererMap("KT");
        ktDiagnosticFactoryToRendererMap.put(BackendErrors.INSTANCE.getNON_LOCAL_RETURN_IN_DISABLED_INLINE(), "Non-local returns are not allowed with inlining disabled");
        MAP = ktDiagnosticFactoryToRendererMap;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory
    public KtDiagnosticFactoryToRendererMap getMAP() {
        return MAP;
    }
}
