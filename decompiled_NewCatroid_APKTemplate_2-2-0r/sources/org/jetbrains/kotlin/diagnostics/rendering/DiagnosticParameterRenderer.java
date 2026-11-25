package org.jetbrains.kotlin.diagnostics.rendering;

import kotlin.Metadata;

/* JADX INFO: compiled from: DiagnosticParameterRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00020\u0002J\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u0007H&¢\u0006\u0002\u0010\bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "O", "", "render", "", "obj", "renderingContext", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;)Ljava/lang/String;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DiagnosticParameterRenderer<O> {
    String render(O obj, RenderingContext renderingContext);
}
