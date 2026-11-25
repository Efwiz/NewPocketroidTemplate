package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: ktElementUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000\u001a\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"deleteSemicolon", "", "Lorg/jetbrains/kotlin/psi/KtElement;", "unpackFunctionLiteral", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "allowParentheses", "", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtElementUtilsKt {
    public static final void deleteSemicolon(KtElement ktElement) {
        PsiElement psiElementSkipSiblingsForward;
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        if ((ktElement instanceof KtEnumEntry) || (psiElementSkipSiblingsForward = PsiTreeUtil.skipSiblingsForward(ktElement, PsiWhiteSpace.class, PsiComment.class)) == null || !Intrinsics.areEqual(psiElementSkipSiblingsForward.getNode().getElementType(), KtTokens.SEMICOLON)) {
            return;
        }
        PsiElement psiElementSkipSiblingsForward2 = PsiTreeUtil.skipSiblingsForward(psiElementSkipSiblingsForward, PsiWhiteSpace.class);
        PsiElement prevSibling = psiElementSkipSiblingsForward2 != null ? psiElementSkipSiblingsForward2.getPrevSibling() : null;
        if (prevSibling != null) {
            psiElementSkipSiblingsForward = prevSibling;
        }
        ktElement.getParentMethod().deleteChildRange(ktElement.getNextSibling(), psiElementSkipSiblingsForward);
    }

    public static /* synthetic */ KtLambdaExpression unpackFunctionLiteral$default(KtExpression ktExpression, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return unpackFunctionLiteral(ktExpression, z);
    }

    public static final KtLambdaExpression unpackFunctionLiteral(KtExpression ktExpression, boolean z) {
        KtExpression expression;
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        if (ktExpression instanceof KtLambdaExpression) {
            return (KtLambdaExpression) ktExpression;
        }
        if (ktExpression instanceof KtLabeledExpression) {
            KtExpression baseExpression = ((KtLabeledExpression) ktExpression).getBaseExpression();
            if (baseExpression != null) {
                return unpackFunctionLiteral(baseExpression, z);
            }
        } else if (ktExpression instanceof KtAnnotatedExpression) {
            KtExpression baseExpression2 = ((KtAnnotatedExpression) ktExpression).getBaseExpression();
            if (baseExpression2 != null) {
                return unpackFunctionLiteral(baseExpression2, z);
            }
        } else if ((ktExpression instanceof KtParenthesizedExpression) && z && (expression = ((KtParenthesizedExpression) ktExpression).getExpression()) != null) {
            return unpackFunctionLiteral(expression, z);
        }
        return null;
    }
}
