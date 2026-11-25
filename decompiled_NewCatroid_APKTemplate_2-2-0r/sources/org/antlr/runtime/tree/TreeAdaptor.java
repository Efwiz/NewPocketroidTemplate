package org.antlr.runtime.tree;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

/* JADX INFO: loaded from: classes12.dex */
public interface TreeAdaptor {
    void addChild(Object obj, Object obj2);

    Object becomeRoot(Object obj, Object obj2);

    Object becomeRoot(Token token, Object obj);

    Object create(int i, String str);

    Object create(int i, Token token);

    Object create(int i, Token token, String str);

    Object create(Token token);

    Object deleteChild(Object obj, int i);

    Object dupNode(Object obj);

    Object dupTree(Object obj);

    Object errorNode(TokenStream tokenStream, Token token, Token token2, RecognitionException recognitionException);

    Object getChild(Object obj, int i);

    int getChildCount(Object obj);

    int getChildIndex(Object obj);

    Object getParent(Object obj);

    String getText(Object obj);

    Token getToken(Object obj);

    int getTokenStartIndex(Object obj);

    int getTokenStopIndex(Object obj);

    int getType(Object obj);

    int getUniqueID(Object obj);

    boolean isNil(Object obj);

    Object nil();

    void replaceChildren(Object obj, int i, int i2, Object obj2);

    Object rulePostProcessing(Object obj);

    void setChild(Object obj, int i, Object obj2);

    void setChildIndex(Object obj, int i);

    void setParent(Object obj, Object obj2);

    void setText(Object obj, String str);

    void setTokenBoundaries(Object obj, Token token, Token token2);

    void setType(Object obj, int i);
}
