package org.antlr.runtime.debug;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.TreeAdaptor;

/* JADX INFO: loaded from: classes12.dex */
public class TraceDebugEventListener extends BlankDebugEventListener {
    TreeAdaptor adaptor;

    public TraceDebugEventListener(TreeAdaptor treeAdaptor) {
        this.adaptor = treeAdaptor;
    }

    public void enterRule(String str) {
        System.out.println("enterRule " + str);
    }

    public void exitRule(String str) {
        System.out.println("exitRule " + str);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void enterSubRule(int i) {
        System.out.println("enterSubRule");
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void exitSubRule(int i) {
        System.out.println("exitSubRule");
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void location(int i, int i2) {
        System.out.println("location " + i + ":" + i2);
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void consumeNode(Object obj) {
        System.out.println("consumeNode " + this.adaptor.getUniqueID(obj) + " " + this.adaptor.getText(obj) + " " + this.adaptor.getType(obj));
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void LT(int i, Object obj) {
        System.out.println("LT " + i + " " + this.adaptor.getUniqueID(obj) + " " + this.adaptor.getText(obj) + " " + this.adaptor.getType(obj));
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void nilNode(Object obj) {
        System.out.println("nilNode " + this.adaptor.getUniqueID(obj));
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void createNode(Object obj) {
        System.out.println("create " + this.adaptor.getUniqueID(obj) + ": " + this.adaptor.getText(obj) + ", " + this.adaptor.getType(obj));
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void createNode(Object obj, Token token) {
        int uniqueID = this.adaptor.getUniqueID(obj);
        this.adaptor.getText(obj);
        System.out.println("create " + uniqueID + ": " + token.getTokenIndex());
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void becomeRoot(Object obj, Object obj2) {
        System.out.println("becomeRoot " + this.adaptor.getUniqueID(obj) + ", " + this.adaptor.getUniqueID(obj2));
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void addChild(Object obj, Object obj2) {
        System.out.println("addChild " + this.adaptor.getUniqueID(obj) + ", " + this.adaptor.getUniqueID(obj2));
    }

    @Override // org.antlr.runtime.debug.BlankDebugEventListener, org.antlr.runtime.debug.DebugEventListener
    public void setTokenBoundaries(Object obj, int i, int i2) {
        System.out.println("setTokenBoundaries " + this.adaptor.getUniqueID(obj) + ", " + i + ", " + i2);
    }
}
