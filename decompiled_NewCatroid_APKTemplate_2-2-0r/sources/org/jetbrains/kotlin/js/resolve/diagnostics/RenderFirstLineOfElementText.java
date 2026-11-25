package org.jetbrains.kotlin.js.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer;
import org.jetbrains.kotlin.diagnostics.rendering.RenderingContext;

/* JADX INFO: compiled from: jsRenderers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/RenderFirstLineOfElementText;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "()V", "render", "", "obj", "renderingContext", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RenderFirstLineOfElementText implements DiagnosticParameterRenderer<PsiElement> {
    public static final RenderFirstLineOfElementText INSTANCE = new RenderFirstLineOfElementText();

    private RenderFirstLineOfElementText() {
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
    public String render(PsiElement obj, RenderingContext renderingContext) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(renderingContext, "renderingContext");
        String text = obj.getText();
        Intrinsics.checkNotNull(text);
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) text, '\n', 0, false, 6, (Object) null);
        if (iIndexOf$default == -1) {
            return text;
        }
        StringBuilder sb = new StringBuilder();
        String strSubstring = text.substring(0, iIndexOf$default);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return sb.append(strSubstring).append("...").toString();
    }
}
