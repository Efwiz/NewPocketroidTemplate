package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class ClassInitializerElement extends CompositeElement implements Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) ClassInitializerElement.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "child", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/ClassInitializerElement", "getChildRole"));
    }

    public ClassInitializerElement() {
        super(CLASS_INITIALIZER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 8) {
            return findChildByType(MODIFIER_LIST);
        }
        if (i != 17) {
            return null;
        }
        return findChildByType(CODE_BLOCK);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType != C_STYLE_COMMENT && elementType != END_OF_LINE_COMMENT) {
            if (elementType == MODIFIER_LIST) {
                return 8;
            }
            if (elementType == CODE_BLOCK) {
                return 17;
            }
        }
        return 0;
    }
}
