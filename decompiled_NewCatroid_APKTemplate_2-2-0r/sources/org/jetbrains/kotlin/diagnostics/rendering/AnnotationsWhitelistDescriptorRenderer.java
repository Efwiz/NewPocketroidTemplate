package org.jetbrains.kotlin.diagnostics.rendering;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.platform.PlatformSpecificDiagnosticComponents;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions;

/* JADX INFO: compiled from: AnnotationsWhitelistDescriptorRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B,\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u001d\u0010\u0005\u001a\u0019\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00010\u0006¢\u0006\u0002\b\b¢\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R%\u0010\u0005\u001a\u0019\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00010\u0006¢\u0006\u0002\b\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/AnnotationsWhitelistDescriptorRenderer;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DeclarationWithDiagnosticComponents;", "baseRenderer", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "toParameterRenderer", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;Lkotlin/jvm/functions/Function1;)V", "render", "", "obj", "renderingContext", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationsWhitelistDescriptorRenderer implements DiagnosticParameterRenderer<DeclarationWithDiagnosticComponents> {
    private final DescriptorRenderer baseRenderer;
    private final Function1<DescriptorRenderer, DiagnosticParameterRenderer<DeclarationDescriptor>> toParameterRenderer;

    /* JADX WARN: Multi-variable type inference failed */
    public AnnotationsWhitelistDescriptorRenderer(DescriptorRenderer baseRenderer, Function1<? super DescriptorRenderer, ? extends DiagnosticParameterRenderer<? super DeclarationDescriptor>> toParameterRenderer) {
        Intrinsics.checkNotNullParameter(baseRenderer, "baseRenderer");
        Intrinsics.checkNotNullParameter(toParameterRenderer, "toParameterRenderer");
        this.baseRenderer = baseRenderer;
        this.toParameterRenderer = toParameterRenderer;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
    public String render(DeclarationWithDiagnosticComponents obj, RenderingContext renderingContext) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(renderingContext, "renderingContext");
        final DeclarationDescriptor declaration = obj.getDeclaration();
        final PlatformSpecificDiagnosticComponents diagnosticComponents = obj.getDiagnosticComponents();
        return this.toParameterRenderer.invoke(this.baseRenderer.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.AnnotationsWhitelistDescriptorRenderer.render.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                final PlatformSpecificDiagnosticComponents platformSpecificDiagnosticComponents = diagnosticComponents;
                final DeclarationDescriptor declarationDescriptor = declaration;
                withOptions.setAnnotationFilter(new Function1<AnnotationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.AnnotationsWhitelistDescriptorRenderer.render.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(AnnotationDescriptor annotation) {
                        Intrinsics.checkNotNullParameter(annotation, "annotation");
                        return Boolean.valueOf(platformSpecificDiagnosticComponents.isNullabilityAnnotation(annotation, declarationDescriptor));
                    }
                });
            }
        })).render(declaration, renderingContext);
    }
}
