package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class ReferenceListElement extends CompositeElement {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final IElementType myKeyword;
    private final String myKeywordText;
    private final IElementType mySeparator;
    private final String mySeparatorText;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "child";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/ReferenceListElement";
        if (i != 1) {
            objArr[2] = "deleteChildInternal";
        } else {
            objArr[2] = "getChildRole";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public ReferenceListElement(IElementType iElementType, IElementType iElementType2, String str) {
        this(iElementType, iElementType2, str, JavaTokenType.COMMA, ",");
    }

    public ReferenceListElement(IElementType iElementType, IElementType iElementType2, String str, IElementType iElementType3, String str2) {
        super(iElementType);
        this.myKeyword = iElementType2;
        this.myKeywordText = str;
        this.mySeparator = iElementType3;
        this.mySeparatorText = str2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [org.jetbrains.kotlin.com.intellij.lang.ASTNode, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ReferenceListElement] */
    /* JADX WARN: Type inference failed for: r3v0, types: [org.jetbrains.kotlin.com.intellij.lang.ASTNode, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement] */
    /* JADX WARN: Type inference failed for: r3v7, types: [org.jetbrains.kotlin.com.intellij.lang.ASTNode] */
    /* JADX WARN: Type inference failed for: r3v8, types: [org.jetbrains.kotlin.com.intellij.lang.ASTNode] */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        TreeElement treeElementAddInternal = super.addInternal(treeElement, aSTNode, aSTNode2, bool);
        CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(this);
        if (treeElement == aSTNode && treeElement.getElementType() == JavaElementType.JAVA_CODE_REFERENCE) {
            for (ASTNode treeNext = treeElement.getTreeNext(); treeNext != null && treeNext.getElementType() != this.mySeparator; treeNext = treeNext.getTreeNext()) {
                if (treeNext.getElementType() == JavaElementType.JAVA_CODE_REFERENCE) {
                    LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(this.mySeparator, this.mySeparatorText, charTableFindCharTableByTree, getManager());
                    super.addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, treeElement, Boolean.FALSE);
                    break;
                }
            }
            while (true) {
                treeElement = treeElement.getTreePrev();
                if (treeElement == 0 || treeElement.getElementType() == this.mySeparator) {
                    break;
                }
                if (treeElement.getElementType() == JavaElementType.JAVA_CODE_REFERENCE) {
                    LeafElement leafElementCreateSingleLeafElement2 = Factory.createSingleLeafElement(this.mySeparator, this.mySeparatorText, charTableFindCharTableByTree, getManager());
                    super.addInternal(leafElementCreateSingleLeafElement2, leafElementCreateSingleLeafElement2, treeElement, Boolean.FALSE);
                    break;
                }
            }
        }
        if (findChildByType(this.myKeyword) == null && findChildByType(JavaElementType.JAVA_CODE_REFERENCE) != null) {
            LeafElement leafElementCreateSingleLeafElement3 = Factory.createSingleLeafElement(this.myKeyword, this.myKeywordText, charTableFindCharTableByTree, getManager());
            super.addInternal(leafElementCreateSingleLeafElement3, leafElementCreateSingleLeafElement3, getFirstChildNode(), Boolean.TRUE);
        }
        return treeElementAddInternal;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        if (aSTNode.getElementType() == JavaElementType.JAVA_CODE_REFERENCE) {
            ASTNode aSTNodeSkipWhitespaceAndComments = PsiImplUtil.skipWhitespaceAndComments(aSTNode.getTreeNext());
            if (aSTNodeSkipWhitespaceAndComments != null && aSTNodeSkipWhitespaceAndComments.getElementType() == this.mySeparator) {
                deleteChildInternal(aSTNodeSkipWhitespaceAndComments);
            } else {
                ASTNode aSTNodeSkipWhitespaceAndCommentsBack = PsiImplUtil.skipWhitespaceAndCommentsBack(aSTNode.getTreePrev());
                if (aSTNodeSkipWhitespaceAndCommentsBack != null && (aSTNodeSkipWhitespaceAndCommentsBack.getElementType() == this.mySeparator || aSTNodeSkipWhitespaceAndCommentsBack.getElementType() == this.myKeyword)) {
                    deleteChildInternal(aSTNodeSkipWhitespaceAndCommentsBack);
                }
            }
        }
        super.deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaTokenType.COMMA) {
            return 23;
        }
        return elementType == JavaElementType.JAVA_CODE_REFERENCE ? 29 : 0;
    }
}
