package org.jetbrains.kotlin.diagnostics.rendering;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: smartRenderers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/SmartTypeRenderer;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "Lorg/jetbrains/kotlin/types/KotlinType;", "baseRenderer", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "(Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;)V", "render", "", "obj", "renderingContext", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SmartTypeRenderer implements DiagnosticParameterRenderer<KotlinType> {
    private final DescriptorRenderer baseRenderer;

    public SmartTypeRenderer(DescriptorRenderer baseRenderer) {
        Intrinsics.checkNotNullParameter(baseRenderer, "baseRenderer");
        this.baseRenderer = baseRenderer;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
    public String render(KotlinType obj, final RenderingContext renderingContext) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(renderingContext, "renderingContext");
        return this.baseRenderer.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.SmartTypeRenderer$render$adaptiveRenderer$1
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
                withOptions.setClassifierNamePolicy(AdaptiveClassifierNamePolicyKt.getAdaptiveClassifierPolicy(renderingContext));
            }
        }).renderType(obj);
    }
}
