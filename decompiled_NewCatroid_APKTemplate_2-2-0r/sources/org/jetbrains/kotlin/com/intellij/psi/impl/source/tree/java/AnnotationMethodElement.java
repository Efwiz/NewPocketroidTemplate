package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;

/* JADX INFO: loaded from: classes3.dex */
public class AnnotationMethodElement extends MethodElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "child", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/AnnotationMethodElement", "getChildRole"));
    }

    public AnnotationMethodElement() {
        super(ANNOTATION_METHOD);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.MethodElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        if (i == 248) {
            return findChildByType(ANNOTATION_MEMBER_VALUE_BIT_SET);
        }
        return super.findChildByRole(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.MethodElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        if (ANNOTATION_MEMBER_VALUE_BIT_SET.contains(aSTNode.getElementType())) {
            return 248;
        }
        return super.getChildRole(aSTNode);
    }
}
