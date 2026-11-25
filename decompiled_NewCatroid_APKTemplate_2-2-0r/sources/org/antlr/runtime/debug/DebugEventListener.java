package org.antlr.runtime.debug;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;

/* JADX INFO: loaded from: classes12.dex */
public interface DebugEventListener {
    public static final int FALSE = 0;
    public static final String PROTOCOL_VERSION = "2";
    public static final int TRUE = 1;

    void LT(int i, Object obj);

    void LT(int i, Token token);

    void addChild(Object obj, Object obj2);

    void becomeRoot(Object obj, Object obj2);

    void beginBacktrack(int i);

    void beginResync();

    void commence();

    void consumeHiddenToken(Token token);

    void consumeNode(Object obj);

    void consumeToken(Token token);

    void createNode(Object obj);

    void createNode(Object obj, Token token);

    void endBacktrack(int i, boolean z);

    void endResync();

    void enterAlt(int i);

    void enterDecision(int i, boolean z);

    void enterRule(String str, String str2);

    void enterSubRule(int i);

    void errorNode(Object obj);

    void exitDecision(int i);

    void exitRule(String str, String str2);

    void exitSubRule(int i);

    void location(int i, int i2);

    void mark(int i);

    void nilNode(Object obj);

    void recognitionException(RecognitionException recognitionException);

    void rewind();

    void rewind(int i);

    void semanticPredicate(boolean z, String str);

    void setTokenBoundaries(Object obj, int i, int i2);

    void terminate();
}
