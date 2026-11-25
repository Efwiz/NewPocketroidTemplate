package org.antlr.runtime.debug;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;

/* JADX INFO: loaded from: classes12.dex */
public class DebugEventRepeater implements DebugEventListener {
    protected DebugEventListener listener;

    public DebugEventRepeater(DebugEventListener debugEventListener) {
        this.listener = debugEventListener;
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void enterRule(String str, String str2) {
        this.listener.enterRule(str, str2);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void exitRule(String str, String str2) {
        this.listener.exitRule(str, str2);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void enterAlt(int i) {
        this.listener.enterAlt(i);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void enterSubRule(int i) {
        this.listener.enterSubRule(i);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void exitSubRule(int i) {
        this.listener.exitSubRule(i);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void enterDecision(int i, boolean z) {
        this.listener.enterDecision(i, z);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void exitDecision(int i) {
        this.listener.exitDecision(i);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void location(int i, int i2) {
        this.listener.location(i, i2);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void consumeToken(Token token) {
        this.listener.consumeToken(token);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void consumeHiddenToken(Token token) {
        this.listener.consumeHiddenToken(token);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void LT(int i, Token token) {
        this.listener.LT(i, token);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void mark(int i) {
        this.listener.mark(i);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void rewind(int i) {
        this.listener.rewind(i);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void rewind() {
        this.listener.rewind();
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void beginBacktrack(int i) {
        this.listener.beginBacktrack(i);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void endBacktrack(int i, boolean z) {
        this.listener.endBacktrack(i, z);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void recognitionException(RecognitionException recognitionException) {
        this.listener.recognitionException(recognitionException);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void beginResync() {
        this.listener.beginResync();
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void endResync() {
        this.listener.endResync();
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void semanticPredicate(boolean z, String str) {
        this.listener.semanticPredicate(z, str);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void commence() {
        this.listener.commence();
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void terminate() {
        this.listener.terminate();
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void consumeNode(Object obj) {
        this.listener.consumeNode(obj);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void LT(int i, Object obj) {
        this.listener.LT(i, obj);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void nilNode(Object obj) {
        this.listener.nilNode(obj);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void errorNode(Object obj) {
        this.listener.errorNode(obj);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void createNode(Object obj) {
        this.listener.createNode(obj);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void createNode(Object obj, Token token) {
        this.listener.createNode(obj, token);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void becomeRoot(Object obj, Object obj2) {
        this.listener.becomeRoot(obj, obj2);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void addChild(Object obj, Object obj2) {
        this.listener.addChild(obj, obj2);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void setTokenBoundaries(Object obj, int i, int i2) {
        this.listener.setTokenBoundaries(obj, i, i2);
    }
}
