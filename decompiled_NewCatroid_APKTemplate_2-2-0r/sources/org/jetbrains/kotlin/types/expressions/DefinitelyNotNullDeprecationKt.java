package org.jetbrains.kotlin.types.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtOperationExpression;
import org.jetbrains.kotlin.psi.KtPostfixExpression;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: definitelyNotNullDeprecation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"reportDeprecatedDefinitelyNotNullSyntax", "", "Lorg/jetbrains/kotlin/psi/KtOperationExpression;", "rhs", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DefinitelyNotNullDeprecationKt {
    public static final void reportDeprecatedDefinitelyNotNullSyntax(KtOperationExpression ktOperationExpression, KtTypeReference ktTypeReference, ExpressionTypingContext context) {
        Intrinsics.checkNotNullParameter(ktOperationExpression, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        KtOperationExpression ktOperationExpression2 = ktOperationExpression;
        PsiElement psiElementNextLeaf$default = PsiUtilsKt.nextLeaf$default(ktOperationExpression2, false, 1, null);
        if ((psiElementNextLeaf$default instanceof LeafPsiElement) && ((LeafPsiElement) psiElementNextLeaf$default).getElementType() == KtTokens.EXCLEXCL) {
            if ((ktTypeReference != null ? ktTypeReference.getTypeElement() : null) instanceof KtUserType) {
                PsiElement psiElementFindCommonParent = PsiTreeUtil.findCommonParent(psiElementNextLeaf$default, ktOperationExpression2);
                if (psiElementFindCommonParent instanceof KtPostfixExpression) {
                    KtPostfixExpression ktPostfixExpression = (KtPostfixExpression) psiElementFindCommonParent;
                    if (ktPostfixExpression.getOperationToken() == KtTokens.EXCLEXCL) {
                        context.trace.report(Errors.DEPRECATED_SYNTAX_WITH_DEFINITELY_NOT_NULL.on(ktPostfixExpression));
                    }
                }
            }
        }
    }
}
