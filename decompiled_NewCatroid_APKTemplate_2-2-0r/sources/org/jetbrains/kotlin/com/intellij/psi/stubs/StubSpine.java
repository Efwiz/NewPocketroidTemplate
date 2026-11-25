package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.List;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.StubbedSpine;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
class StubSpine implements StubbedSpine {
    private final StubTree myTree;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", Constants.TYPE_TREE, "org/jetbrains/kotlin/com/intellij/psi/stubs/StubSpine", "<init>"));
    }

    StubSpine(StubTree stubTree) {
        if (stubTree == null) {
            $$$reportNull$$$0(0);
        }
        this.myTree = stubTree;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.StubbedSpine
    public int getStubCount() {
        return this.myTree.getPlainList().size();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.StubbedSpine
    public PsiElement getStubPsi(int i) {
        List<StubElement<?>> plainList = this.myTree.getPlainList();
        if (i >= plainList.size()) {
            return null;
        }
        return plainList.get(i).getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.StubbedSpine
    public IElementType getStubType(int i) {
        List<StubElement<?>> plainList = this.myTree.getPlainList();
        if (i >= plainList.size()) {
            return null;
        }
        return plainList.get(i).getStubType();
    }
}
