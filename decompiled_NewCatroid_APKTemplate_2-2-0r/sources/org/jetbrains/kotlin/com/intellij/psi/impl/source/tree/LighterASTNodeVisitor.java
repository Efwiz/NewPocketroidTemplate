package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTTokenNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterLazyParseableNode;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LighterASTNodeVisitor {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "node";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LighterASTNodeVisitor";
        if (i != 1) {
            objArr[2] = "visitTokenNode";
        } else {
            objArr[2] = "visitLazyParseableNode";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract void visitNode(LighterASTNode lighterASTNode);

    public void visitTokenNode(LighterASTTokenNode lighterASTTokenNode) {
        if (lighterASTTokenNode == null) {
            $$$reportNull$$$0(0);
        }
        visitNode(lighterASTTokenNode);
    }

    public void visitLazyParseableNode(LighterLazyParseableNode lighterLazyParseableNode) {
        if (lighterLazyParseableNode == null) {
            $$$reportNull$$$0(1);
        }
        visitNode(lighterLazyParseableNode);
    }
}
