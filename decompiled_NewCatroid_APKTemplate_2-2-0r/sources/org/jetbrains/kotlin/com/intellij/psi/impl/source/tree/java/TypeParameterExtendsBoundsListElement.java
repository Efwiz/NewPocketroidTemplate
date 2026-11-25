package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import com.huawei.hms.framework.common.ContainerUtils;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class TypeParameterExtendsBoundsListElement extends ReferenceListElement {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "child", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/TypeParameterExtendsBoundsListElement", "getChildRole"));
    }

    public TypeParameterExtendsBoundsListElement() {
        super(JavaElementType.EXTENDS_BOUND_LIST, JavaTokenType.EXTENDS_KEYWORD, PsiKeyword.EXTENDS, JavaTokenType.AND, ContainerUtils.FIELD_DELIMITER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ReferenceListElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaTokenType.AND) {
            return 123;
        }
        return elementType == JavaElementType.JAVA_CODE_REFERENCE ? 78 : 0;
    }
}
