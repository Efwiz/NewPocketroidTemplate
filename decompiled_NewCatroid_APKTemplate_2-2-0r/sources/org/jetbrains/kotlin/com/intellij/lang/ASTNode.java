package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public interface ASTNode extends UserDataHolder {
    public static final ASTNode[] EMPTY_ARRAY = new ASTNode[0];

    void addChild(ASTNode aSTNode, ASTNode aSTNode2);

    void addChildren(ASTNode aSTNode, ASTNode aSTNode2, ASTNode aSTNode3);

    Object clone();

    ASTNode copyElement();

    ASTNode findChildByType(IElementType iElementType);

    ASTNode findChildByType(TokenSet tokenSet);

    ASTNode findLeafElementAt(int i);

    CharSequence getChars();

    ASTNode[] getChildren(TokenSet tokenSet);

    <T> T getCopyableUserData(Key<T> key);

    IElementType getElementType();

    ASTNode getFirstChildNode();

    ASTNode getLastChildNode();

    PsiElement getPsi();

    <T extends PsiElement> T getPsi(Class<T> cls);

    int getStartOffset();

    String getText();

    int getTextLength();

    TextRange getTextRange();

    ASTNode getTreeNext();

    ASTNode getTreeParent();

    ASTNode getTreePrev();

    <T> void putCopyableUserData(Key<T> key, T t);

    void removeChild(ASTNode aSTNode);

    void removeRange(ASTNode aSTNode, ASTNode aSTNode2);

    void replaceAllChildrenToChildrenOf(ASTNode aSTNode);

    void replaceChild(ASTNode aSTNode, ASTNode aSTNode2);

    boolean textContains(char c);

    default int getStartOffsetInParent() {
        ASTNode treeParent = getTreeParent();
        return getStartOffset() - (treeParent == null ? 0 : treeParent.getStartOffset());
    }
}
