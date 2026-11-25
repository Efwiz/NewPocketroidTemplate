package org.jetbrains.kotlin.diagnostics.rendering;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.renderer.AnnotationArgumentsRenderingPolicy;
import org.jetbrains.kotlin.renderer.ClassifierNamePolicy;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualAnnotationsIncompatibilityType;

/* JADX INFO: compiled from: ExpectActualAnnotationIncompatibilityDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/ExpectActualAnnotationIncompatibilityDiagnosticRenderers;", "", "()V", "DESCRIPTOR_RENDERER", "Lorg/jetbrains/kotlin/diagnostics/rendering/SmartDescriptorRenderer;", "INCOMPATIBILITY", "Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualAnnotationsIncompatibilityType;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "descriptorRender", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpectActualAnnotationIncompatibilityDiagnosticRenderers {
    public static final SmartDescriptorRenderer DESCRIPTOR_RENDERER;
    public static final ContextIndependentParameterRenderer<ExpectActualAnnotationsIncompatibilityType<? extends AnnotationDescriptor>> INCOMPATIBILITY;
    public static final ExpectActualAnnotationIncompatibilityDiagnosticRenderers INSTANCE = new ExpectActualAnnotationIncompatibilityDiagnosticRenderers();
    private static final DescriptorRenderer descriptorRender;

    private ExpectActualAnnotationIncompatibilityDiagnosticRenderers() {
    }

    static {
        DescriptorRenderer descriptorRendererWithOptions = DescriptorRenderer.INSTANCE.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.ExpectActualAnnotationIncompatibilityDiagnosticRenderers$descriptorRender$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setAnnotationArgumentsRenderingPolicy(AnnotationArgumentsRenderingPolicy.UNLESS_EMPTY);
                withOptions.setModifiers(SetsKt.emptySet());
                withOptions.setWithDefinedIn(true);
                withOptions.setClassifierNamePolicy(ClassifierNamePolicy.SHORT.INSTANCE);
            }
        });
        descriptorRender = descriptorRendererWithOptions;
        DESCRIPTOR_RENDERER = RenderersKt.asRenderer(descriptorRendererWithOptions);
        INCOMPATIBILITY = DiagnosticParameterRendererKt.Renderer(new Function1<ExpectActualAnnotationsIncompatibilityType<? extends AnnotationDescriptor>, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.ExpectActualAnnotationIncompatibilityDiagnosticRenderers$INCOMPATIBILITY$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ExpectActualAnnotationsIncompatibilityType<? extends AnnotationDescriptor> incompatibilityType) {
                Intrinsics.checkNotNullParameter(incompatibilityType, "incompatibilityType");
                StringBuilder sbAppend = new StringBuilder("Annotation `").append(DescriptorRenderer.renderAnnotation$default(ExpectActualAnnotationIncompatibilityDiagnosticRenderers.descriptorRender, incompatibilityType.getExpectAnnotation(), null, 2, null)).append("` ");
                if (incompatibilityType instanceof ExpectActualAnnotationsIncompatibilityType.MissingOnActual) {
                    sbAppend.append("is missing on actual declaration");
                } else if (incompatibilityType instanceof ExpectActualAnnotationsIncompatibilityType.DifferentOnActual) {
                    sbAppend.append("has different arguments on actual declaration: `").append(DescriptorRenderer.renderAnnotation$default(ExpectActualAnnotationIncompatibilityDiagnosticRenderers.descriptorRender, (AnnotationDescriptor) ((ExpectActualAnnotationsIncompatibilityType.DifferentOnActual) incompatibilityType).getActualAnnotation(), null, 2, null)).append("`");
                }
                String string = sbAppend.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        });
    }
}
