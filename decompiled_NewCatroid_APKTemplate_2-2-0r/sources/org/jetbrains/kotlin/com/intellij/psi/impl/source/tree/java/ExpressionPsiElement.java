package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class ExpressionPsiElement extends CompositePsiElement {
    private final int myHC;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "child";
        } else {
            objArr[0] = "newElement";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/ExpressionPsiElement";
        objArr[2] = "replaceChildInternal";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public ExpressionPsiElement(IElementType iElementType) {
        super(iElementType);
        int i = CompositePsiElement.ourHC;
        CompositePsiElement.ourHC = i + 1;
        this.myHC = i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void replaceChildInternal(ASTNode aSTNode, TreeElement treeElement) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        if (treeElement == null) {
            $$$reportNull$$$0(1);
        }
        super.replaceChildInternal(aSTNode, JavaSourceUtil.addParenthToReplacedChild(aSTNode, treeElement, getManager()));
    }

    public final int hashCode() {
        return this.myHC;
    }
}
