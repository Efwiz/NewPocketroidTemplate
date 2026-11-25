package org.jetbrains.kotlin.js.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer;
import org.jetbrains.kotlin.diagnostics.rendering.RenderingContext;

/* JADX INFO: compiled from: jsRenderers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H$J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCallDataRenderer;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCallData;", "()V", ConfigConstants.CONFIG_KEY_FORMAT, "", "data", "Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCallDataWithCode;", "render", "obj", "renderingContext", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JsCallDataRenderer implements DiagnosticParameterRenderer<JsCallData> {
    protected abstract String format(JsCallDataWithCode data);

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
    public String render(JsCallData obj, RenderingContext renderingContext) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(renderingContext, "renderingContext");
        return obj instanceof JsCallDataWithCode ? format((JsCallDataWithCode) obj) : obj.getMessage();
    }
}
