package org.jetbrains.kotlin.diagnostics.rendering;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DiagnosticParameterRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\u001a,\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u001a&\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00060\t\u001a3\u0010\n\u001a\u0004\u0018\u00010\u000b\"\u0004\b\u0000\u0010\f2\u0006\u0010\r\u001a\u0002H\f2\u000e\u0010\u000e\u001a\n\u0012\u0004\u0012\u0002H\f\u0018\u00010\u00012\u0006\u0010\u000f\u001a\u00020\u0005¢\u0006\u0002\u0010\u0010¨\u0006\u0011"}, d2 = {"ContextDependentRenderer", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "O", "block", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "", "Renderer", "Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "Lkotlin/Function1;", "renderParameter", "", "P", "parameter", "renderer", "context", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;)Ljava/lang/Object;", "frontend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticParameterRendererKt {
    public static final <O> ContextIndependentParameterRenderer<O> Renderer(final Function1<? super O, String> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new ContextIndependentParameterRenderer<O>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRendererKt.Renderer.1
            @Override // org.jetbrains.kotlin.diagnostics.rendering.ContextIndependentParameterRenderer
            public String render(O obj) {
                return block.invoke(obj);
            }
        };
    }

    public static final <O> DiagnosticParameterRenderer<O> ContextDependentRenderer(final Function2<? super O, ? super RenderingContext, String> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new DiagnosticParameterRenderer<O>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRendererKt.ContextDependentRenderer.1
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public String render(O obj, RenderingContext renderingContext) {
                Intrinsics.checkNotNullParameter(renderingContext, "renderingContext");
                return block.invoke(obj, renderingContext);
            }
        };
    }

    public static final <P> Object renderParameter(P p, DiagnosticParameterRenderer<? super P> diagnosticParameterRenderer, RenderingContext context) {
        String strRender;
        Intrinsics.checkNotNullParameter(context, "context");
        return (diagnosticParameterRenderer == null || (strRender = diagnosticParameterRenderer.render(p, context)) == null) ? p : strRender;
    }
}
