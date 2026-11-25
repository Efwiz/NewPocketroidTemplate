package org.antlr.runtime.debug;

import java.util.ArrayList;
import java.util.List;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;

/* JADX INFO: loaded from: classes12.dex */
public class DebugEventHub implements DebugEventListener {
    protected List<DebugEventListener> listeners;

    public DebugEventHub(DebugEventListener debugEventListener) {
        ArrayList arrayList = new ArrayList();
        this.listeners = arrayList;
        arrayList.add(debugEventListener);
    }

    public DebugEventHub(DebugEventListener debugEventListener, DebugEventListener debugEventListener2) {
        ArrayList arrayList = new ArrayList();
        this.listeners = arrayList;
        arrayList.add(debugEventListener);
        this.listeners.add(debugEventListener2);
    }

    public void addListener(DebugEventListener debugEventListener) {
        this.listeners.add(debugEventListener);
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void enterRule(String str, String str2) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).enterRule(str, str2);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void exitRule(String str, String str2) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).exitRule(str, str2);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void enterAlt(int i) {
        for (int i2 = 0; i2 < this.listeners.size(); i2++) {
            this.listeners.get(i2).enterAlt(i);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void enterSubRule(int i) {
        for (int i2 = 0; i2 < this.listeners.size(); i2++) {
            this.listeners.get(i2).enterSubRule(i);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void exitSubRule(int i) {
        for (int i2 = 0; i2 < this.listeners.size(); i2++) {
            this.listeners.get(i2).exitSubRule(i);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void enterDecision(int i, boolean z) {
        for (int i2 = 0; i2 < this.listeners.size(); i2++) {
            this.listeners.get(i2).enterDecision(i, z);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void exitDecision(int i) {
        for (int i2 = 0; i2 < this.listeners.size(); i2++) {
            this.listeners.get(i2).exitDecision(i);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void location(int i, int i2) {
        for (int i3 = 0; i3 < this.listeners.size(); i3++) {
            this.listeners.get(i3).location(i, i2);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void consumeToken(Token token) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).consumeToken(token);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void consumeHiddenToken(Token token) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).consumeHiddenToken(token);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void LT(int i, Token token) {
        for (int i2 = 0; i2 < this.listeners.size(); i2++) {
            this.listeners.get(i2).LT(i, token);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void mark(int i) {
        for (int i2 = 0; i2 < this.listeners.size(); i2++) {
            this.listeners.get(i2).mark(i);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void rewind(int i) {
        for (int i2 = 0; i2 < this.listeners.size(); i2++) {
            this.listeners.get(i2).rewind(i);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void rewind() {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).rewind();
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void beginBacktrack(int i) {
        for (int i2 = 0; i2 < this.listeners.size(); i2++) {
            this.listeners.get(i2).beginBacktrack(i);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void endBacktrack(int i, boolean z) {
        for (int i2 = 0; i2 < this.listeners.size(); i2++) {
            this.listeners.get(i2).endBacktrack(i, z);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void recognitionException(RecognitionException recognitionException) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).recognitionException(recognitionException);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void beginResync() {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).beginResync();
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void endResync() {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).endResync();
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void semanticPredicate(boolean z, String str) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).semanticPredicate(z, str);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void commence() {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).commence();
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void terminate() {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).terminate();
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void consumeNode(Object obj) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).consumeNode(obj);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void LT(int i, Object obj) {
        for (int i2 = 0; i2 < this.listeners.size(); i2++) {
            this.listeners.get(i2).LT(i, obj);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void nilNode(Object obj) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).nilNode(obj);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void errorNode(Object obj) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).errorNode(obj);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void createNode(Object obj) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).createNode(obj);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void createNode(Object obj, Token token) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).createNode(obj, token);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void becomeRoot(Object obj, Object obj2) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).becomeRoot(obj, obj2);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void addChild(Object obj, Object obj2) {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).addChild(obj, obj2);
        }
    }

    @Override // org.antlr.runtime.debug.DebugEventListener
    public void setTokenBoundaries(Object obj, int i, int i2) {
        for (int i3 = 0; i3 < this.listeners.size(); i3++) {
            this.listeners.get(i3).setTokenBoundaries(obj, i, i2);
        }
    }
}
