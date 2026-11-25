package org.jetbrains.kotlin.diagnostics.rendering;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DiagnosticParameterRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0006J\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\bH\u0016¢\u0006\u0002\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "O", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "render", "", "obj", "(Ljava/lang/Object;)Ljava/lang/String;", "renderingContext", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;)Ljava/lang/String;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ContextIndependentParameterRenderer<O> extends DiagnosticParameterRenderer<O> {
    String render(O obj);

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
    default String render(O obj, RenderingContext renderingContext) {
        Intrinsics.checkNotNullParameter(renderingContext, "renderingContext");
        return render(obj);
    }
}
