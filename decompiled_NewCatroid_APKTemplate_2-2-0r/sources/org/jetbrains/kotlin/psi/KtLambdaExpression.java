package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifiableCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: loaded from: classes6.dex */
public class KtLambdaExpression extends LazyParseablePsiElement implements PsiModifiableCodeBlock, KtExpression {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 8) ? 2 : 3];
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 8) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtLambdaExpression";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1) {
            objArr[1] = "getFunctionLiteral";
        } else if (i == 2) {
            objArr[1] = "getValueParameters";
        } else if (i == 3) {
            objArr[1] = "asElement";
        } else if (i == 4) {
            objArr[1] = "getLeftCurlyBrace";
        } else if (i == 5) {
            objArr[1] = "getContainingKtFile";
        } else if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtLambdaExpression";
        } else {
            objArr[1] = "getPsiOrParent";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
                break;
            case 6:
                objArr[2] = "acceptChildren";
                break;
            case 7:
            default:
                objArr[2] = "accept";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public KtElement asElement() {
        return this;
    }

    @Override // org.jetbrains.kotlin.psi.KtPureElement
    public KtElement getPsiOrParent() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifiableCodeBlock
    public boolean shouldChangeModificationCount(PsiElement psiElement) {
        return false;
    }

    public KtLambdaExpression(CharSequence charSequence) {
        super(KtNodeTypes.LAMBDA_EXPRESSION, charSequence);
    }

    @Override // org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(0);
        }
        return ktVisitor.visitLambdaExpression(this, d);
    }

    public KtFunctionLiteral getFunctionLiteral() {
        KtFunctionLiteral ktFunctionLiteral = (KtFunctionLiteral) findChildByType(KtNodeTypes.FUNCTION_LITERAL).getPsi(KtFunctionLiteral.class);
        if (ktFunctionLiteral == null) {
            $$$reportNull$$$0(1);
        }
        return ktFunctionLiteral;
    }

    public List<KtParameter> getValueParameters() {
        List<KtParameter> valueParameters = getFunctionLiteral().getValueParameters();
        if (valueParameters == null) {
            $$$reportNull$$$0(2);
        }
        return valueParameters;
    }

    public KtBlockExpression getBodyExpression() {
        return getFunctionLiteral().getBodyExpression();
    }

    public boolean hasDeclaredReturnType() {
        return getFunctionLiteral().mo7396getTypeReference() != null;
    }

    public ASTNode getLeftCurlyBrace() {
        ASTNode aSTNodeFindChildByType = getFunctionLiteral().getNode().findChildByType(KtTokens.LBRACE);
        if (aSTNodeFindChildByType == null) {
            $$$reportNull$$$0(4);
        }
        return aSTNodeFindChildByType;
    }

    public ASTNode getRightCurlyBrace() {
        return getFunctionLiteral().getNode().findChildByType(KtTokens.RBRACE);
    }

    @Override // org.jetbrains.kotlin.psi.KtPureElement
    public KtFile getContainingKtFile() {
        KtFile containingKtFile = PsiUtilsKt.getContainingKtFile(this);
        if (containingKtFile == null) {
            $$$reportNull$$$0(5);
        }
        return containingKtFile;
    }

    @Override // org.jetbrains.kotlin.psi.KtElement
    public <D> void acceptChildren(KtVisitor<Void, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(6);
        }
        KtPsiUtil.visitChildren(this, ktVisitor, d);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(7);
        }
        if (psiElementVisitor instanceof KtVisitor) {
            accept((KtVisitor) psiElementVisitor, null);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return getNode().getElementType().toString();
    }
}
