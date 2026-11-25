package org.jetbrains.kotlin.psi;

import java.util.Arrays;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType;

/* JADX INFO: loaded from: classes6.dex */
public class KtElementImplStub<T extends StubElement<?>> extends StubBasedPsiElementBase<T> implements StubBasedPsiElement<T>, KtElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 5 || i == 8 || i == 10 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 5 || i == 8 || i == 10 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "nodeType";
                break;
            case 2:
                objArr[0] = "node";
                break;
            case 3:
            case 5:
            case 8:
            case 10:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/psi/KtElementImplStub";
                break;
            case 4:
            case 6:
            case 7:
                objArr[0] = "visitor";
                break;
            case 9:
                objArr[0] = "elementType";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 3) {
            objArr[1] = "getLanguage";
        } else if (i == 5) {
            objArr[1] = "getContainingKtFile";
        } else if (i == 8) {
            objArr[1] = "getReferences";
        } else if (i == 10) {
            objArr[1] = "getStubOrPsiChildrenAsList";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtElementImplStub";
        } else {
            objArr[1] = "getPsiOrParent";
        }
        switch (i) {
            case 3:
            case 5:
            case 8:
            case 10:
            case 11:
                break;
            case 4:
            case 7:
                objArr[2] = "accept";
                break;
            case 6:
                objArr[2] = "acceptChildren";
                break;
            case 9:
                objArr[2] = "getStubOrPsiChildrenAsList";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5 && i != 8 && i != 10 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.psi.KtPureElement
    public KtElement getPsiOrParent() {
        return this;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtElementImplStub(T t, IStubElementType iStubElementType) {
        super(t, iStubElementType);
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (iStubElementType == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtElementImplStub(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        KotlinLanguage kotlinLanguage = KotlinLanguage.INSTANCE;
        if (kotlinLanguage == null) {
            $$$reportNull$$$0(3);
        }
        return kotlinLanguage;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return getElementType().toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
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

    @Override // org.jetbrains.kotlin.psi.KtPureElement
    public KtFile getContainingKtFile() {
        String str;
        PsiFile containingFile = getContainingFile();
        if (!(containingFile instanceof KtFile)) {
            if (containingFile.isValid()) {
                try {
                    str = " " + containingFile.getText();
                } catch (Exception unused) {
                    str = "";
                }
            } else {
                str = "";
            }
            throw new IllegalStateException("KtElement not inside KtFile: " + containingFile + str + " of type " + containingFile.getClass() + " for element " + this + " of type " + getClass() + (containingFile instanceof PsiFileImpl ? " node = " + getNode() : ""));
        }
        KtFile ktFile = (KtFile) containingFile;
        if (ktFile == null) {
            $$$reportNull$$$0(5);
        }
        return ktFile;
    }

    @Override // org.jetbrains.kotlin.psi.KtElement
    public <D> void acceptChildren(KtVisitor<Void, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(6);
        }
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof KtElement) {
                ((KtElement) firstChild).accept(ktVisitor, d);
            }
        }
    }

    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(7);
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
        if (references.length > 0) {
            return references[0];
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference[] getReferences() {
        PsiReference[] referencesFromProviders = KotlinReferenceProvidersService.getReferencesFromProviders(this);
        if (referencesFromProviders == null) {
            $$$reportNull$$$0(8);
        }
        return referencesFromProviders;
    }

    protected <PsiT extends KtElementImplStub<?>, StubT extends StubElement<?>> List<PsiT> getStubOrPsiChildrenAsList(KtStubElementType<StubT, PsiT> ktStubElementType) {
        if (ktStubElementType == null) {
            $$$reportNull$$$0(9);
        }
        List<PsiT> listAsList = Arrays.asList(getStubOrPsiChildren(ktStubElementType, ktStubElementType.getArrayFactory()));
        if (listAsList == null) {
            $$$reportNull$$$0(10);
        }
        return listAsList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        PsiElement parentSubstitute = KtPsiUtilKt.getParentSubstitute(this);
        return parentSubstitute != null ? parentSubstitute : super.getLightParent();
    }
}
