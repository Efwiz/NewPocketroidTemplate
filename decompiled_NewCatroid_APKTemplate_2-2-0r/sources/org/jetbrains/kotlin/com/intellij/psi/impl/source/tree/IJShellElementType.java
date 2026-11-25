package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.java.JShellLanguage;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType;

/* JADX INFO: loaded from: classes3.dex */
public abstract class IJShellElementType extends ILazyParseableElementType {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "debugName", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/IJShellElementType", "<init>"));
    }

    public abstract PsiElement createPsi(ASTNode aSTNode);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IJShellElementType(String str) {
        super(str, JShellLanguage.INSTANCE);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }
}
