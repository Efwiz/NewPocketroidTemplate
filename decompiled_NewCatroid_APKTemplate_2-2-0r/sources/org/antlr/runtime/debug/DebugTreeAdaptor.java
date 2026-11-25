package org.antlr.runtime.debug;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.TreeAdaptor;

/* JADX INFO: loaded from: classes12.dex */
public class DebugTreeAdaptor implements TreeAdaptor {
    protected TreeAdaptor adaptor;
    protected DebugEventListener dbg;

    public DebugTreeAdaptor(DebugEventListener debugEventListener, TreeAdaptor treeAdaptor) {
        this.dbg = debugEventListener;
        this.adaptor = treeAdaptor;
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object create(Token token) {
        if (token.getTokenIndex() < 0) {
            return create(token.getType(), token.getText());
        }
        Object objCreate = this.adaptor.create(token);
        this.dbg.createNode(objCreate, token);
        return objCreate;
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object errorNode(TokenStream tokenStream, Token token, Token token2, RecognitionException recognitionException) {
        Object objErrorNode = this.adaptor.errorNode(tokenStream, token, token2, recognitionException);
        if (objErrorNode != null) {
            this.dbg.errorNode(objErrorNode);
        }
        return objErrorNode;
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object dupTree(Object obj) {
        Object objDupTree = this.adaptor.dupTree(obj);
        simulateTreeConstruction(objDupTree);
        return objDupTree;
    }

    protected void simulateTreeConstruction(Object obj) {
        this.dbg.createNode(obj);
        int childCount = this.adaptor.getChildCount(obj);
        for (int i = 0; i < childCount; i++) {
            Object child = this.adaptor.getChild(obj, i);
            simulateTreeConstruction(child);
            this.dbg.addChild(obj, child);
        }
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object dupNode(Object obj) {
        Object objDupNode = this.adaptor.dupNode(obj);
        this.dbg.createNode(objDupNode);
        return objDupNode;
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object nil() {
        Object objNil = this.adaptor.nil();
        this.dbg.nilNode(objNil);
        return objNil;
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public boolean isNil(Object obj) {
        return this.adaptor.isNil(obj);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public void addChild(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            return;
        }
        this.adaptor.addChild(obj, obj2);
        this.dbg.addChild(obj, obj2);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object becomeRoot(Object obj, Object obj2) {
        Object objBecomeRoot = this.adaptor.becomeRoot(obj, obj2);
        this.dbg.becomeRoot(obj, obj2);
        return objBecomeRoot;
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object rulePostProcessing(Object obj) {
        return this.adaptor.rulePostProcessing(obj);
    }

    public void addChild(Object obj, Token token) {
        addChild(obj, create(token));
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object becomeRoot(Token token, Object obj) {
        Object objCreate = create(token);
        this.adaptor.becomeRoot(objCreate, obj);
        this.dbg.becomeRoot(token, obj);
        return objCreate;
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object create(int i, Token token) {
        Object objCreate = this.adaptor.create(i, token);
        this.dbg.createNode(objCreate);
        return objCreate;
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object create(int i, Token token, String str) {
        Object objCreate = this.adaptor.create(i, token, str);
        this.dbg.createNode(objCreate);
        return objCreate;
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object create(int i, String str) {
        Object objCreate = this.adaptor.create(i, str);
        this.dbg.createNode(objCreate);
        return objCreate;
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public int getType(Object obj) {
        return this.adaptor.getType(obj);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public void setType(Object obj, int i) {
        this.adaptor.setType(obj, i);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public String getText(Object obj) {
        return this.adaptor.getText(obj);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public void setText(Object obj, String str) {
        this.adaptor.setText(obj, str);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Token getToken(Object obj) {
        return this.adaptor.getToken(obj);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public void setTokenBoundaries(Object obj, Token token, Token token2) {
        this.adaptor.setTokenBoundaries(obj, token, token2);
        if (obj == null || token == null || token2 == null) {
            return;
        }
        this.dbg.setTokenBoundaries(obj, token.getTokenIndex(), token2.getTokenIndex());
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public int getTokenStartIndex(Object obj) {
        return this.adaptor.getTokenStartIndex(obj);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public int getTokenStopIndex(Object obj) {
        return this.adaptor.getTokenStopIndex(obj);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object getChild(Object obj, int i) {
        return this.adaptor.getChild(obj, i);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public void setChild(Object obj, int i, Object obj2) {
        this.adaptor.setChild(obj, i, obj2);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object deleteChild(Object obj, int i) {
        return deleteChild(obj, i);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public int getChildCount(Object obj) {
        return this.adaptor.getChildCount(obj);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public int getUniqueID(Object obj) {
        return this.adaptor.getUniqueID(obj);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public Object getParent(Object obj) {
        return this.adaptor.getParent(obj);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public int getChildIndex(Object obj) {
        return this.adaptor.getChildIndex(obj);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public void setParent(Object obj, Object obj2) {
        this.adaptor.setParent(obj, obj2);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public void setChildIndex(Object obj, int i) {
        this.adaptor.setChildIndex(obj, i);
    }

    @Override // org.antlr.runtime.tree.TreeAdaptor
    public void replaceChildren(Object obj, int i, int i2, Object obj2) {
        this.adaptor.replaceChildren(obj, i, i2, obj2);
    }

    public DebugEventListener getDebugListener() {
        return this.dbg;
    }

    public void setDebugListener(DebugEventListener debugEventListener) {
        this.dbg = debugEventListener;
    }

    public TreeAdaptor getTreeAdaptor() {
        return this.adaptor;
    }
}
