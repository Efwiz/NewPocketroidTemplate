package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public interface SyntaxTreeBuilder {

    public interface Marker {
        void collapse(IElementType iElementType);

        void done(IElementType iElementType);

        void drop();

        void error(String str);

        void rollbackTo();

        void setCustomEdgeTokenBinders(WhitespacesAndCommentsBinder whitespacesAndCommentsBinder, WhitespacesAndCommentsBinder whitespacesAndCommentsBinder2);
    }

    void advanceLexer();

    void enforceCommentTokens(TokenSet tokenSet);

    boolean eof();

    void error(String str);

    int getCurrentOffset();

    LighterASTNode getLatestDoneMarker();

    CharSequence getOriginalText();

    String getTokenText();

    IElementType getTokenType();

    IElementType lookAhead(int i);

    IElementType rawLookup(int i);

    int rawTokenIndex();

    int rawTokenTypeStart(int i);

    void remapCurrentToken(IElementType iElementType);

    void setDebugMode(boolean z);

    void setTokenTypeRemapper(ITokenTypeRemapper iTokenTypeRemapper);

    void setWhitespaceSkippedCallback(WhitespaceSkippedCallback whitespaceSkippedCallback);
}
