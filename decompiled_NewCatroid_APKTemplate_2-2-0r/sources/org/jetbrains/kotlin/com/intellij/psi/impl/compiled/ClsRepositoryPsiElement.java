package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ClsRepositoryPsiElement<T extends StubElement> extends ClsElementImpl implements StubBasedPsiElement<T> {
    private final T myStub;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsRepositoryPsiElement", "getChildren"));
    }

    protected ClsRepositoryPsiElement(T t) {
        this.myStub = t;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement
    public IStubElementType getElementType() {
        return this.myStub.getStubType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return this.myStub.getParentStub().getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiManager getManager() {
        PsiFile containingFile = getContainingFile();
        if (containingFile == null) {
            throw new PsiInvalidElementAccessException(this);
        }
        return containingFile.getManager();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        StubElement parentStub = this.myStub;
        while (!(parentStub instanceof PsiFileStub)) {
            parentStub = parentStub.getParentStub();
        }
        return (PsiFile) parentStub.getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement
    public T getStub() {
        return this.myStub;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        PsiFile containingFile = getContainingFile();
        return containingFile != null && containingFile.getIsPhysical();
    }

    public PsiElement[] getChildren() {
        List<StubElement> childrenStubs = getStub().getChildrenStubs();
        if (childrenStubs.size() == 0) {
            PsiElement[] psiElementArr = EMPTY_ARRAY;
            if (psiElementArr == null) {
                $$$reportNull$$$0(0);
            }
            return psiElementArr;
        }
        PsiElement[] psiElementArr2 = new PsiElement[childrenStubs.size()];
        for (int i = 0; i < childrenStubs.size(); i++) {
            psiElementArr2[i] = childrenStubs.get(i).getPsi();
        }
        return psiElementArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getFirstChild() {
        List<StubElement> childrenStubs = getStub().getChildrenStubs();
        if (childrenStubs.isEmpty()) {
            return null;
        }
        return childrenStubs.get(0).getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getLastChild() {
        List<StubElement> childrenStubs = getStub().getChildrenStubs();
        if (childrenStubs.isEmpty()) {
            return null;
        }
        return childrenStubs.get(childrenStubs.size() - 1).getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNextSibling() {
        PsiElement[] children = getLightParent().getChildren();
        int iIndexOf = ArrayUtil.indexOf(children, this);
        if (iIndexOf < 0 || iIndexOf >= children.length - 1) {
            return null;
        }
        return children[iIndexOf + 1];
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getPrevSibling() {
        PsiElement[] children = getLightParent().getChildren();
        int iIndexOf = ArrayUtil.indexOf(children, this);
        if (iIndexOf < 1) {
            return null;
        }
        return children[iIndexOf - 1];
    }
}
