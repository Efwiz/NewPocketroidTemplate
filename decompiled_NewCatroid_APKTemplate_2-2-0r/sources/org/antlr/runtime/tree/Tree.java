package org.antlr.runtime.tree;

import java.util.List;
import org.antlr.runtime.Token;

/* JADX INFO: loaded from: classes12.dex */
public interface Tree {
    public static final Tree INVALID_NODE = new CommonTree(Token.INVALID_TOKEN);

    void addChild(Tree tree);

    Object deleteChild(int i);

    Tree dupNode();

    void freshenParentAndChildIndexes();

    Tree getAncestor(int i);

    List<?> getAncestors();

    int getCharPositionInLine();

    Tree getChild(int i);

    int getChildCount();

    int getChildIndex();

    int getLine();

    Tree getParent();

    String getText();

    int getTokenStartIndex();

    int getTokenStopIndex();

    int getType();

    boolean hasAncestor(int i);

    boolean isNil();

    void replaceChildren(int i, int i2, Object obj);

    void setChild(int i, Tree tree);

    void setChildIndex(int i);

    void setParent(Tree tree);

    void setTokenStartIndex(int i);

    void setTokenStopIndex(int i);

    String toString();

    String toStringTree();
}
