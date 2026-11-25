package org.jetbrains.kotlin.psi;

import kotlin.jvm.functions.Function1;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.psi.stubs.KotlinConstantExpressionStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtConstantExpressionElementType;

/* JADX INFO: loaded from: classes6.dex */
public class KtConstantExpression extends KtElementImplStub<KotlinConstantExpressionStub> implements KtExpression {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i != 3) {
            objArr[0] = "node";
        } else {
            objArr[0] = "newElement";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtConstantExpression";
        if (i == 2) {
            objArr[2] = "accept";
        } else if (i != 3) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtConstantExpression(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtConstantExpression(KotlinConstantExpressionStub kotlinConstantExpressionStub) {
        super(kotlinConstantExpressionStub, KtConstantExpressionElementType.INSTANCE.kindToConstantElementType(kotlinConstantExpressionStub.kind()));
        if (kotlinConstantExpressionStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitConstantExpression(this, d);
    }

    /* JADX INFO: renamed from: lambda$replace$0$org-jetbrains-kotlin-psi-KtConstantExpression, reason: not valid java name */
    /* synthetic */ PsiElement m7389lambda$replace$0$orgjetbrainskotlinpsiKtConstantExpression(PsiElement psiElement) {
        return super.replace(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        return KtExpressionImpl.INSTANCE.replaceExpression(this, psiElement, true, new Function1() { // from class: org.jetbrains.kotlin.psi.KtConstantExpression$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m7389lambda$replace$0$orgjetbrainskotlinpsiKtConstantExpression((PsiElement) obj);
            }
        });
    }
}
