package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubTree;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiFileWithStubSupport extends PsiFile {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiFileWithStubSupport", "getStubbedSpine"));
    }

    StubTree getStubTree();

    default StubbedSpine getStubbedSpine() {
        StubTree stubTree = getStubTree();
        if (stubTree == null) {
            throw new UnsupportedOperationException("Please implement getStubbedSpine method");
        }
        StubbedSpine spine = stubTree.getSpine();
        if (spine == null) {
            $$$reportNull$$$0(0);
        }
        return spine;
    }
}
