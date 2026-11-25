package org.jetbrains.kotlin.backend.common;

import kotlin.Metadata;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap;
import org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory;
import org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers;
import org.jetbrains.kotlin.diagnostics.rendering.Renderers;

/* JADX INFO: compiled from: CommonBackendErrors.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/KtDefaultCommonBackendErrorMessages;", "Lorg/jetbrains/kotlin/diagnostics/rendering/BaseDiagnosticRendererFactory;", "()V", "MAP", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "getMAP", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDefaultCommonBackendErrorMessages extends BaseDiagnosticRendererFactory {
    public static final KtDefaultCommonBackendErrorMessages INSTANCE = new KtDefaultCommonBackendErrorMessages();
    private static final KtDiagnosticFactoryToRendererMap MAP;

    private KtDefaultCommonBackendErrorMessages() {
    }

    static {
        KtDiagnosticFactoryToRendererMap ktDiagnosticFactoryToRendererMap = new KtDiagnosticFactoryToRendererMap("KT");
        ktDiagnosticFactoryToRendererMap.put(CommonBackendErrors.INSTANCE.getNO_ACTUAL_FOR_EXPECT(), "Expected {0} has no actual declaration in module {1}", CommonRenderers.STRING, Renderers.MODULE_WITH_PLATFORM);
        ktDiagnosticFactoryToRendererMap.put(CommonBackendErrors.INSTANCE.getMANY_INTERFACES_MEMBER_NOT_IMPLEMENTED(), "{0} must override {1} because it inherits multiple interface methods of it", CommonRenderers.STRING, CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(CommonBackendErrors.INSTANCE.getMANY_IMPL_MEMBER_NOT_IMPLEMENTED(), "{0} must override {1} because it inherits many implementations of it", CommonRenderers.STRING, CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(CommonBackendErrors.INSTANCE.getINCOMPATIBLE_MATCHING(), "Expect declaration `{0}` doesn''t match actual `{1}` because {2}", CommonRenderers.STRING, CommonRenderers.STRING, BackendDiagnosticRenderers.INSTANCE.getINCOMPATIBILITY());
        ktDiagnosticFactoryToRendererMap.put(CommonBackendErrors.INSTANCE.getACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT(), "{2}.\nAll annotations from expect `{0}` must be present with the same arguments on actual `{1}`, otherwise they might behave incorrectly.", BackendDiagnosticRenderers.INSTANCE.getSYMBOL_OWNER_DECLARATION_FQ_NAME(), BackendDiagnosticRenderers.INSTANCE.getSYMBOL_OWNER_DECLARATION_FQ_NAME(), BackendDiagnosticRenderers.INSTANCE.getEXPECT_ACTUAL_ANNOTATION_INCOMPATIBILITY());
        ktDiagnosticFactoryToRendererMap.put(CommonBackendErrors.INSTANCE.getEVALUATION_ERROR(), "Cannot evaluate constant expression: {0}", CommonRenderers.STRING);
        MAP = ktDiagnosticFactoryToRendererMap;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory
    public KtDiagnosticFactoryToRendererMap getMAP() {
        return MAP;
    }
}
