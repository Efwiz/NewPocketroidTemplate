package org.jetbrains.kotlin.js.translate.reference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.psi.KtCallExpression;

/* JADX INFO: compiled from: QualifiedExpressionWithCallSelectorAccessTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\u0001H\u0016J\b\u0010\n\u001a\u00020\u0005H\u0016J\u0010\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/reference/QualifiedExpressionWithCallSelectorAccessTranslator;", "Lorg/jetbrains/kotlin/js/translate/reference/AccessTranslator;", "selector", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "receiver", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/psi/KtCallExpression;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "getCached", "translateAsGet", "translateAsSet", "setTo", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class QualifiedExpressionWithCallSelectorAccessTranslator implements AccessTranslator {
    private final TranslationContext context;
    private final JsExpression receiver;
    private final KtCallExpression selector;

    public QualifiedExpressionWithCallSelectorAccessTranslator(KtCallExpression selector, JsExpression jsExpression, TranslationContext context) {
        Intrinsics.checkNotNullParameter(selector, "selector");
        Intrinsics.checkNotNullParameter(context, "context");
        this.selector = selector;
        this.receiver = jsExpression;
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public JsExpression translateAsGet() {
        JsNode jsNodeInvokeCallExpressionTranslator = QualifiedExpressionTranslator.invokeCallExpressionTranslator(this.receiver, this.selector, this.context);
        Intrinsics.checkNotNull(jsNodeInvokeCallExpressionTranslator, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsExpression");
        return (JsExpression) jsNodeInvokeCallExpressionTranslator;
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public JsExpression translateAsSet(JsExpression setTo) {
        Intrinsics.checkNotNullParameter(setTo, "setTo");
        throw new IllegalStateException("Set access is not supported for " + getClass().getSimpleName());
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public AccessTranslator getCached() {
        throw new IllegalStateException("Cashed access is not supported for " + getClass().getSimpleName());
    }
}
