package org.antlr.runtime.tree;

import java.util.HashMap;
import java.util.Map;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

/* JADX INFO: loaded from: classes12.dex */
public abstract class BaseTreeAdaptor implements TreeAdaptor {
    protected Map<Object, Integer> treeToUniqueIDMap;
    protected int uniqueNodeID = 1;

    public abstract Token createToken(int i, String str);

    public abstract Token createToken(Token token);

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object nil() {
        return create(null);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object errorNode(TokenStream tokenStream, Token token, Token token2, RecognitionException recognitionException) {
        return new CommonErrorNode(tokenStream, token, token2, recognitionException);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public boolean isNil(Object obj) {
        return ((Tree) obj).isNil();
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object dupTree(Object obj) {
        return dupTree(obj, null);
    }

    public Object dupTree(Object obj, Object obj2) {
        if (obj == null) {
            return null;
        }
        Object objDupNode = dupNode(obj);
        setChildIndex(objDupNode, getChildIndex(obj));
        setParent(objDupNode, obj2);
        int childCount = getChildCount(obj);
        for (int i = 0; i < childCount; i++) {
            addChild(objDupNode, dupTree(getChild(obj, i), obj));
        }
        return objDupNode;
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public void addChild(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            return;
        }
        ((Tree) obj).addChild((Tree) obj2);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object becomeRoot(Object obj, Object obj2) {
        Tree child = (Tree) obj;
        Tree tree = (Tree) obj2;
        if (obj2 == null) {
            return obj;
        }
        if (child.isNil()) {
            int childCount = child.getChildCount();
            if (childCount == 1) {
                child = child.getChild(0);
            } else if (childCount > 1) {
                throw new RuntimeException("more than one node as root (TODO: make exception hierarchy)");
            }
        }
        child.addChild(tree);
        return child;
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object rulePostProcessing(Object obj) {
        Tree tree = (Tree) obj;
        if (tree == null || !tree.isNil()) {
            return tree;
        }
        if (tree.getChildCount() == 0) {
            return null;
        }
        if (tree.getChildCount() != 1) {
            return tree;
        }
        Tree child = tree.getChild(0);
        child.setParent(null);
        child.setChildIndex(-1);
        return child;
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object becomeRoot(Token token, Object obj) {
        return becomeRoot(create(token), obj);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object create(int i, Token token) {
        Token tokenCreateToken = createToken(token);
        tokenCreateToken.setType(i);
        return (Tree) create(tokenCreateToken);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object create(int i, Token token, String str) {
        if (token == null) {
            return create(i, str);
        }
        Token tokenCreateToken = createToken(token);
        tokenCreateToken.setType(i);
        tokenCreateToken.setText(str);
        return (Tree) create(tokenCreateToken);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object create(int i, String str) {
        return (Tree) create(createToken(i, str));
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public int getType(Object obj) {
        return ((Tree) obj).getType();
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public void setType(Object obj, int i) {
        throw new NoSuchMethodError("don't know enough about Tree node");
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public String getText(Object obj) {
        return ((Tree) obj).getText();
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public void setText(Object obj, String str) {
        throw new NoSuchMethodError("don't know enough about Tree node");
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object getChild(Object obj, int i) {
        return ((Tree) obj).getChild(i);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public void setChild(Object obj, int i, Object obj2) {
        ((Tree) obj).setChild(i, (Tree) obj2);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object deleteChild(Object obj, int i) {
        return ((Tree) obj).deleteChild(i);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public int getChildCount(Object obj) {
        return ((Tree) obj).getChildCount();
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public int getUniqueID(Object obj) {
        if (this.treeToUniqueIDMap == null) {
            this.treeToUniqueIDMap = new HashMap();
        }
        Integer num = this.treeToUniqueIDMap.get(obj);
        if (num != null) {
            return num.intValue();
        }
        int i = this.uniqueNodeID;
        this.treeToUniqueIDMap.put(obj, Integer.valueOf(i));
        this.uniqueNodeID++;
        return i;
    }
}
