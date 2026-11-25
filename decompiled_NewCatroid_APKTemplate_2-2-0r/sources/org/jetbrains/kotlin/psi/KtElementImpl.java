package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;

/* JADX INFO: loaded from: classes6.dex */
public class KtElementImpl extends ASTWrapperPsiElement implements KtElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/psi/KtElementImpl";
                break;
            case 2:
            case 4:
            case 5:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i == 1) {
            objArr[1] = "getLanguage";
        } else if (i == 3) {
            objArr[1] = "getContainingKtFile";
        } else if (i == 6) {
            objArr[1] = "getReferences";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtElementImpl";
        } else {
            objArr[1] = "getPsiOrParent";
        }
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 7:
                break;
            case 2:
            case 5:
                objArr[2] = "accept";
                break;
            case 4:
                objArr[2] = "acceptChildren";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.psi.KtPureElement
    public KtElement getPsiOrParent() {
        return this;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtElementImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        KotlinLanguage kotlinLanguage = KotlinLanguage.INSTANCE;
        if (kotlinLanguage == null) {
            $$$reportNull$$$0(1);
        }
        return kotlinLanguage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTWrapperPsiElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return getNode().getElementType().toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof KtVisitor) {
            accept((KtVisitor) psiElementVisitor, null);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtPureElement
    public KtFile getContainingKtFile() {
        PsiFile containingFile = getContainingFile();
        if (!(containingFile instanceof KtFile)) {
            throw new IllegalStateException("KtElement not inside KtFile: " + containingFile + ((containingFile == null || !containingFile.isValid()) ? "" : " " + containingFile.getText()) + " for element " + this + " of type " + getClass() + " node = " + getNode());
        }
        KtFile ktFile = (KtFile) containingFile;
        if (ktFile == null) {
            $$$reportNull$$$0(3);
        }
        return ktFile;
    }

    @Override // org.jetbrains.kotlin.psi.KtElement
    public <D> void acceptChildren(KtVisitor<Void, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(4);
        }
        KtPsiUtil.visitChildren(this, ktVisitor, d);
    }

    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(5);
        }
        return ktVisitor.visitKtElement(this, d);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        KtElementUtilsKt.deleteSemicolon(this);
        super.delete();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        PsiReference[] references = getReferences();
        if (references.length == 1) {
            return references[0];
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference[] getReferences() {
        PsiReference[] referencesFromProviders = KotlinReferenceProvidersService.getReferencesFromProviders(this);
        if (referencesFromProviders == null) {
            $$$reportNull$$$0(6);
        }
        return referencesFromProviders;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTWrapperPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getParent() {
        PsiElement parentSubstitute = KtPsiUtilKt.getParentSubstitute(this);
        return parentSubstitute != null ? parentSubstitute : super.getParent();
    }
}
