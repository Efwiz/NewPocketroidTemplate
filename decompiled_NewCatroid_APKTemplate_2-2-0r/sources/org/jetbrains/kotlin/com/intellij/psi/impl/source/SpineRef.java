package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.Stub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubTree;

/* JADX INFO: loaded from: classes3.dex */
class SpineRef extends SubstrateRef {
    private final PsiFileImpl myFile;
    private final int myIndex;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/SpineRef";
        } else {
            objArr[0] = "file";
        }
        if (i == 1) {
            objArr[1] = "getNode";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/SpineRef";
        } else {
            objArr[1] = "getContainingFile";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    SpineRef(PsiFileImpl psiFileImpl, int i) {
        if (psiFileImpl == null) {
            $$$reportNull$$$0(0);
        }
        this.myFile = psiFileImpl;
        this.myIndex = i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
    public ASTNode getNode() {
        CompositeElement compositeElement = this.myFile.calcTreeElement().getStubbedSpine().getSpineNodes().get(this.myIndex);
        if (compositeElement == null) {
            $$$reportNull$$$0(1);
        }
        return compositeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
    public Stub getStub() {
        StubTree stubTree = this.myFile.getStubTree();
        if (stubTree == null) {
            return null;
        }
        return stubTree.getPlainList().get(this.myIndex);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
    public Stub getGreenStub() {
        StubTree greenStubTree = this.myFile.getGreenStubTree();
        if (greenStubTree == null) {
            return null;
        }
        return greenStubTree.getPlainList().get(this.myIndex);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
    public boolean isValid() {
        return this.myFile.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
    public PsiFile getContainingFile() {
        PsiFileImpl psiFileImpl = this.myFile;
        if (psiFileImpl == null) {
            $$$reportNull$$$0(2);
        }
        return psiFileImpl;
    }
}
