package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: loaded from: classes6.dex */
@Deprecated
abstract class KtNamedDeclarationNotStubbed extends KtDeclarationImpl implements KtNamedDeclaration {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtNamedDeclarationNotStubbed";
        } else if (i != 2) {
            objArr[0] = "node";
        } else {
            objArr[0] = "name";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtNamedDeclarationNotStubbed";
        } else {
            objArr[1] = "getNameAsSafeName";
        }
        if (i != 1) {
            if (i != 2) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "setName";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtNamedDeclarationNotStubbed(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        String text;
        PsiElement psiElementMo7392getNameIdentifier = mo7392getNameIdentifier();
        if (psiElementMo7392getNameIdentifier == null || (text = psiElementMo7392getNameIdentifier.getText()) == null) {
            return null;
        }
        return KtPsiUtil.unquoteIdentifier(text);
    }

    public Name getNameAsName() {
        String myName = getMyName();
        if (myName != null) {
            return Name.identifier(myName);
        }
        return null;
    }

    public Name getNameAsSafeName() {
        Name nameSafeName = KtPsiUtil.safeName(getMyName());
        if (nameSafeName == null) {
            $$$reportNull$$$0(1);
        }
        return nameSafeName;
    }

    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiElement mo7392getNameIdentifier() {
        return findChildByType(KtTokens.IDENTIFIER);
    }

    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        PsiElement psiElementMo7392getNameIdentifier = mo7392getNameIdentifier();
        if (psiElementMo7392getNameIdentifier == null) {
            throw new IncorrectOperationException();
        }
        return psiElementMo7392getNameIdentifier.replace(new KtPsiFactory(getProject()).createNameIdentifier(str));
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        PsiElement psiElementMo7392getNameIdentifier = mo7392getNameIdentifier();
        return (psiElementMo7392getNameIdentifier != null ? psiElementMo7392getNameIdentifier.getTextRange() : getTextRange()).getStartOffset();
    }
}
