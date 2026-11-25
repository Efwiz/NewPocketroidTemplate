package org.jetbrains.kotlin.psi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifiableCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: loaded from: classes6.dex */
public class KtBlockExpression extends LazyParseablePsiElement implements PsiModifiableCodeBlock, KtElement, KtExpression, KtStatementExpression {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 3 : 2];
        if (i == 2 || i == 3 || i == 4) {
            objArr[0] = "visitor";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtBlockExpression";
        }
        switch (i) {
            case 1:
                objArr[1] = "getContainingKtFile";
                break;
            case 2:
            case 3:
            case 4:
                objArr[1] = "org/jetbrains/kotlin/psi/KtBlockExpression";
                break;
            case 5:
                objArr[1] = "getChildren";
                break;
            case 6:
                objArr[1] = "getPsiOrParent";
                break;
            case 7:
                objArr[1] = "getStatements";
                break;
            default:
                objArr[1] = "getLanguage";
                break;
        }
        if (i == 2) {
            objArr[2] = "acceptChildren";
        } else if (i == 3 || i == 4) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.psi.KtPureElement
    public KtElement getPsiOrParent() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifiableCodeBlock
    public boolean shouldChangeModificationCount(PsiElement psiElement) {
        return false;
    }

    public KtBlockExpression(CharSequence charSequence) {
        super(KtNodeTypes.BLOCK, charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        KotlinLanguage kotlinLanguage = KotlinLanguage.INSTANCE;
        if (kotlinLanguage == null) {
            $$$reportNull$$$0(0);
        }
        return kotlinLanguage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return getNode().getElementType().toString();
    }

    @Override // org.jetbrains.kotlin.psi.KtPureElement
    public KtFile getContainingKtFile() {
        KtFile containingKtFile = PsiUtilsKt.getContainingKtFile(this);
        if (containingKtFile == null) {
            $$$reportNull$$$0(1);
        }
        return containingKtFile;
    }

    @Override // org.jetbrains.kotlin.psi.KtElement
    public <D> void acceptChildren(KtVisitor<Void, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        KtPsiUtil.visitChildren(this, ktVisitor, d);
    }

    @Override // org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(3);
        }
        return ktVisitor.visitBlockExpression(this, d);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElementVisitor instanceof KtVisitor) {
            accept((KtVisitor) psiElementVisitor, null);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        KtElementUtilsKt.deleteSemicolon(this);
        super.delete();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        ArrayList arrayList = null;
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild.getNode() instanceof CompositeElement) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(firstChild);
            }
        }
        PsiElement[] psiElementArray = arrayList == null ? PsiElement.EMPTY_ARRAY : PsiUtilCore.toPsiElementArray(arrayList);
        if (psiElementArray == null) {
            $$$reportNull$$$0(5);
        }
        return psiElementArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightOwner() {
        PsiElement parentSubstitute = KtPsiUtilKt.getParentSubstitute(this);
        return parentSubstitute != null ? parentSubstitute : super.getLightOwner();
    }

    public KtExpression getFirstStatement() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }

    public List<KtExpression> getStatements() {
        List<KtExpression> listAsList = Arrays.asList(findChildrenByClass(KtExpression.class));
        if (listAsList == null) {
            $$$reportNull$$$0(7);
        }
        return listAsList;
    }

    public TextRange getLastBracketRange() {
        PsiElement rBrace = getRBrace();
        if (rBrace != null) {
            return rBrace.getTextRange();
        }
        return null;
    }

    public PsiElement getRBrace() {
        return findPsiChildByType(KtTokens.RBRACE);
    }

    public PsiElement getLBrace() {
        return findPsiChildByType(KtTokens.LBRACE);
    }
}
