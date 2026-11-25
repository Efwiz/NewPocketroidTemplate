package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaParserDefinition;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class JavaFileElement extends FileElement {
    private static final Logger LOG = Logger.getInstance((Class<?>) JavaFileElement.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 3) {
            objArr[0] = "child";
        } else {
            objArr[0] = "newElement";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/JavaFileElement";
        if (i == 1) {
            objArr[2] = "getChildRole";
        } else if (i == 2 || i == 3) {
            objArr[2] = "replaceChildInternal";
        } else {
            objArr[2] = "deleteChildInternal";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public JavaFileElement(CharSequence charSequence) {
        super(JavaParserDefinition.JAVA_FILE, charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        if (aSTNode.getElementType() == JavaElementType.CLASS) {
            PsiJavaFile psiJavaFile = (PsiJavaFile) SourceTreeToPsiMap.treeToPsiNotNull(this);
            if (psiJavaFile.getClasses().length == 1) {
                psiJavaFile.delete();
                return;
            }
        }
        super.deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 1) {
            return findChildByType(JavaElementType.PACKAGE_STATEMENT);
        }
        if (i != 2) {
            return null;
        }
        return findChildByType(JavaElementType.IMPORT_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaElementType.PACKAGE_STATEMENT) {
            return 1;
        }
        if (elementType == JavaElementType.IMPORT_LIST) {
            return 2;
        }
        return elementType == JavaElementType.CLASS ? 3 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void replaceChildInternal(ASTNode aSTNode, TreeElement treeElement) {
        ASTNode treeNext;
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (treeElement == null) {
            $$$reportNull$$$0(3);
        }
        if (treeElement.getElementType() == JavaElementType.IMPORT_LIST) {
            LOG.assertTrue(aSTNode.getElementType() == JavaElementType.IMPORT_LIST);
            if (treeElement.getFirstChildNode() == null && (treeNext = aSTNode.getTreeNext()) != null && treeNext.getElementType() == TokenType.WHITE_SPACE) {
                removeChild(treeNext);
            }
        }
        super.replaceChildInternal(aSTNode, treeElement);
    }
}
