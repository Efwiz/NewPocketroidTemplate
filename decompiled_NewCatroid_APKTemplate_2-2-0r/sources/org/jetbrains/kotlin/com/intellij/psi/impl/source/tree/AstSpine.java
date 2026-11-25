package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.StubbedSpine;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class AstSpine implements StubbedSpine {
    static final AstSpine EMPTY_SPINE = new AstSpine(Collections.emptyList());
    private final List<CompositeElement> myNodes;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "psi";
        } else if (i != 2) {
            objArr[0] = "nodes";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/AstSpine";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/AstSpine";
        } else {
            objArr[1] = "getSpineNodes";
        }
        if (i == 1) {
            objArr[2] = "getStubIndex";
        } else if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    AstSpine(List<CompositeElement> list) {
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        this.myNodes = list;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.StubbedSpine
    public int getStubCount() {
        return this.myNodes.size();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.StubbedSpine
    public PsiElement getStubPsi(int i) {
        if (i >= this.myNodes.size()) {
            return null;
        }
        return this.myNodes.get(i).getPsi();
    }

    public int getStubIndex(StubBasedPsiElement stubBasedPsiElement) {
        if (stubBasedPsiElement == null) {
            $$$reportNull$$$0(1);
        }
        return this.myNodes.indexOf((CompositeElement) stubBasedPsiElement.getNode());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.StubbedSpine
    public IElementType getStubType(int i) {
        if (i >= this.myNodes.size()) {
            return null;
        }
        return this.myNodes.get(i).getElementType();
    }

    public List<CompositeElement> getSpineNodes() {
        List<CompositeElement> list = this.myNodes;
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        return list;
    }
}
