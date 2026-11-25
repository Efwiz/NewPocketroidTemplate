package org.jetbrains.kotlin.js.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: compiled from: jsRenderers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCallDataHtmlRenderer;", "Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCallDataRenderer;", "()V", ConfigConstants.CONFIG_KEY_FORMAT, "", "data", "Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCallDataWithCode;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsCallDataHtmlRenderer extends JsCallDataRenderer {
    public static final JsCallDataHtmlRenderer INSTANCE = new JsCallDataHtmlRenderer();

    private JsCallDataHtmlRenderer() {
    }

    @Override // org.jetbrains.kotlin.js.resolve.diagnostics.JsCallDataRenderer
    protected String format(JsCallDataWithCode data) {
        Intrinsics.checkNotNullParameter(data, "data");
        TextRange codeRange = data.getCodeRange();
        return data.getMessage() + " in code:<br><pre>" + JsRenderersKt.underlineAsHtml(data.getCode(), codeRange.getStartOffset(), codeRange.getEndOffset()) + "</pre>";
    }
}
