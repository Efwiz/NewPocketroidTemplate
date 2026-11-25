package org.jetbrains.kotlin.js.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;

/* JADX INFO: compiled from: JsCallChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0004"}, d2 = {"isConstantStringLiteral", "", "Lorg/jetbrains/kotlin/psi/KtExpression;", "(Lorg/jetbrains/kotlin/psi/KtExpression;)Z", "js.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsCallCheckerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isConstantStringLiteral(KtExpression ktExpression) {
        if (!(ktExpression instanceof KtStringTemplateExpression)) {
            return false;
        }
        KtStringTemplateEntry[] entries = ((KtStringTemplateExpression) ktExpression).getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        for (KtStringTemplateEntry ktStringTemplateEntry : entries) {
            if (!(ktStringTemplateEntry instanceof KtLiteralStringTemplateEntry)) {
                return false;
            }
        }
        return true;
    }
}
