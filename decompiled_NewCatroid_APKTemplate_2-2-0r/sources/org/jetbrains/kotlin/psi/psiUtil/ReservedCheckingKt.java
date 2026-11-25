package org.jetbrains.kotlin.psi.psiUtil;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;

/* JADX INFO: compiled from: ReservedChecking.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t\u001a\u0016\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\t\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\r"}, d2 = {"MESSAGE_FOR_YIELD_BEFORE_LAMBDA", "", "getMESSAGE_FOR_YIELD_BEFORE_LAMBDA", "()Ljava/lang/String;", "checkReservedYield", "", "expression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "sink", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "checkReservedYieldBeforeLambda", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReservedCheckingKt {
    private static final String MESSAGE_FOR_YIELD_BEFORE_LAMBDA = "Reserved yield block/lambda. Use 'yield() { ... }' or 'yield(fun...)'";

    public static final void checkReservedYield(KtSimpleNameExpression ktSimpleNameExpression, DiagnosticSink sink) {
        PsiElement identifier;
        Intrinsics.checkNotNullParameter(sink, "sink");
        if (Intrinsics.areEqual(ktSimpleNameExpression != null ? ktSimpleNameExpression.getReferencedName() : null, PsiKeyword.YIELD) && (identifier = ktSimpleNameExpression.getIdentifier()) != null && Intrinsics.areEqual(identifier.getNode().getElementType(), KtTokens.IDENTIFIER) && Intrinsics.areEqual(PsiKeyword.YIELD, identifier.getText())) {
            sink.report(Errors.YIELD_IS_RESERVED.on(identifier, "Identifier 'yield' is reserved. Use backticks to call it: `yield`"));
        }
    }

    public static final String getMESSAGE_FOR_YIELD_BEFORE_LAMBDA() {
        return MESSAGE_FOR_YIELD_BEFORE_LAMBDA;
    }

    public static final void checkReservedYieldBeforeLambda(PsiElement element, DiagnosticSink sink) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(sink, "sink");
        PsiElement previousWord = KtPsiUtil.getPreviousWord(element, PsiKeyword.YIELD);
        if (previousWord != null) {
            sink.report(Errors.YIELD_IS_RESERVED.on(previousWord, MESSAGE_FOR_YIELD_BEFORE_LAMBDA));
        }
    }
}
