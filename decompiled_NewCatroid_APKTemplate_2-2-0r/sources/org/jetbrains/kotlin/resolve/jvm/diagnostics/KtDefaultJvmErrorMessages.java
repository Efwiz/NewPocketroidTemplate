package org.jetbrains.kotlin.resolve.jvm.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap;
import org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory;
import org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers;
import org.jetbrains.kotlin.diagnostics.rendering.ContextIndependentParameterRenderer;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRendererKt;
import org.jetbrains.kotlin.diagnostics.rendering.Renderers;
import org.jetbrains.kotlin.diagnostics.rendering.RenderingContext;
import org.jetbrains.kotlin.resolve.MemberComparator;

/* JADX INFO: compiled from: JvmBackendErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/KtDefaultJvmErrorMessages;", "Lorg/jetbrains/kotlin/diagnostics/rendering/BaseDiagnosticRendererFactory;", "()V", "CONFLICTING_JVM_DECLARATIONS_DATA", "Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;", "MAP", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "getMAP", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDefaultJvmErrorMessages extends BaseDiagnosticRendererFactory {
    public static final ContextIndependentParameterRenderer<ConflictingJvmDeclarationsData> CONFLICTING_JVM_DECLARATIONS_DATA;
    public static final KtDefaultJvmErrorMessages INSTANCE = new KtDefaultJvmErrorMessages();
    private static final KtDiagnosticFactoryToRendererMap MAP;

    private KtDefaultJvmErrorMessages() {
    }

    static {
        ContextIndependentParameterRenderer<ConflictingJvmDeclarationsData> contextIndependentParameterRendererRenderer = DiagnosticParameterRendererKt.Renderer(new Function1<ConflictingJvmDeclarationsData, String>() { // from class: org.jetbrains.kotlin.resolve.jvm.diagnostics.KtDefaultJvmErrorMessages$CONFLICTING_JVM_DECLARATIONS_DATA$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ConflictingJvmDeclarationsData it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Collection<DeclarationDescriptor> signatureDescriptors = it.getSignatureDescriptors();
                MemberComparator INSTANCE2 = MemberComparator.INSTANCE;
                Intrinsics.checkNotNullExpressionValue(INSTANCE2, "INSTANCE");
                List listSortedWith = CollectionsKt.sortedWith(signatureDescriptors, INSTANCE2);
                RenderingContext.Impl impl = new RenderingContext.Impl(listSortedWith);
                StringBuilder sbAppend = new StringBuilder().append(StringsKt.trimIndent("\n                The following declarations have the same JVM signature (" + it.getSignature().getName() + it.getSignature().getDesc() + "):\n                \n                "));
                List list = listSortedWith;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    arrayList.add("    " + Renderers.WITHOUT_MODIFIERS.render((DeclarationDescriptor) it2.next(), (RenderingContext) impl));
                }
                return sbAppend.append(org.jetbrains.kotlin.utils.StringsKt.join(arrayList, "\n")).toString();
            }
        });
        CONFLICTING_JVM_DECLARATIONS_DATA = contextIndependentParameterRendererRenderer;
        KtDiagnosticFactoryToRendererMap ktDiagnosticFactoryToRendererMap = new KtDiagnosticFactoryToRendererMap("KT");
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getCONFLICTING_JVM_DECLARATIONS(), "Platform declaration clash: {0}", contextIndependentParameterRendererRenderer);
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getACCIDENTAL_OVERRIDE(), "Accidental override: {0}", contextIndependentParameterRendererRenderer);
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getCONFLICTING_INHERITED_JVM_DECLARATIONS(), "Inherited platform declarations clash: {0}", contextIndependentParameterRendererRenderer);
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getTYPEOF_SUSPEND_TYPE(), "Suspend functional types are not supported in typeOf");
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getTYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND(), "Non-reified type parameters with recursive bounds are not supported yet: {0}", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getSUSPENSION_POINT_INSIDE_MONITOR(), "A suspension point at {0} is inside a critical section", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getSCRIPT_CAPTURING_NESTED_CLASS(), "Nested class {0} captures the script class instance. Try to use explicit inner modifier for both nested {0} and outer {1}", CommonRenderers.STRING, CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getSCRIPT_CAPTURING_OBJECT(), "Object {0} captures the script class instance. Try to use class or anonymous object instead", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getSCRIPT_CAPTURING_INTERFACE(), "Interface {0} captures the script class instance. Try to use class instead", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getSCRIPT_CAPTURING_ENUM(), "Enum class {0} captures the script class instance. Try to use class or anonymous object instead", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getSCRIPT_CAPTURING_ENUM_ENTRY(), "Enum entry {0} captures the script class instance. Try to use class or anonymous object instead", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getINLINE_CALL_CYCLE(), "The ''{0}'' invocation is a part of inline cycle", CommonRenderers.NAME);
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getNOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC(), "All of multi-file class parts should be annotated with @JvmSynthetic if at least one of them is");
        ktDiagnosticFactoryToRendererMap.put(JvmBackendErrors.INSTANCE.getDUPLICATE_CLASS_NAMES(), "Duplicate JVM class name ''{0}'' generated from: {1}", CommonRenderers.STRING, CommonRenderers.STRING);
        MAP = ktDiagnosticFactoryToRendererMap;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory
    public KtDiagnosticFactoryToRendererMap getMAP() {
        return MAP;
    }
}
