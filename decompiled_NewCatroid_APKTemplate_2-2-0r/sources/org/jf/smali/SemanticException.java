package org.jf.smali;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

/* JADX INFO: loaded from: classes6.dex */
public class SemanticException extends RecognitionException {
    private String errorMessage;

    SemanticException(IntStream intStream, String str, Object... objArr) {
        super(intStream);
        this.errorMessage = String.format(str, objArr);
    }

    SemanticException(IntStream intStream, Exception exc) {
        super(intStream);
        this.errorMessage = exc.getMessage();
    }

    SemanticException(IntStream intStream, CommonTree commonTree, String str, Object... objArr) {
        this.input = intStream;
        this.token = commonTree.getToken();
        this.index = commonTree.getTokenStartIndex();
        this.line = this.token.getLine();
        this.charPositionInLine = this.token.getCharPositionInLine();
        this.errorMessage = String.format(str, objArr);
    }

    SemanticException(IntStream intStream, Token token, String str, Object... objArr) {
        this.input = intStream;
        this.token = token;
        this.index = ((CommonToken) token).getStartIndex();
        this.line = token.getLine();
        this.charPositionInLine = token.getCharPositionInLine();
        this.errorMessage = String.format(str, objArr);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.errorMessage;
    }
}
