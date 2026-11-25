package antlr;

import antlr.collections.impl.BitSet;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes.dex */
public class ANTLRTokdefParser extends LLkParser implements ANTLRTokdefParserTokenTypes {
    public static final String[] _tokenNames = {"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", SchemaSymbols.ATTVAL_ID, "STRING", "ASSIGN", "LPAREN", "RPAREN", "INT", "WS", "SL_COMMENT", "ML_COMMENT", "ESC", "DIGIT", "XDIGIT"};
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
    private Tool antlrTool;

    public void setTool(Tool tool) {
        if (this.antlrTool == null) {
            this.antlrTool = tool;
            return;
        }
        throw new IllegalStateException("antlr.Tool already registered");
    }

    protected Tool getTool() {
        return this.antlrTool;
    }

    @Override // antlr.Parser
    public void reportError(String str) {
        if (getTool() != null) {
            getTool().error(str, getFilename(), -1, -1);
        } else {
            super.reportError(str);
        }
    }

    @Override // antlr.Parser
    public void reportError(RecognitionException recognitionException) {
        if (getTool() != null) {
            getTool().error(recognitionException.getErrorMessage(), recognitionException.getFilename(), recognitionException.getLine(), recognitionException.getColumn());
        } else {
            super.reportError(recognitionException);
        }
    }

    @Override // antlr.Parser
    public void reportWarning(String str) {
        if (getTool() != null) {
            getTool().warning(str, getFilename(), -1, -1);
        } else {
            super.reportWarning(str);
        }
    }

    protected ANTLRTokdefParser(TokenBuffer tokenBuffer, int i) {
        super(tokenBuffer, i);
        this.tokenNames = _tokenNames;
    }

    public ANTLRTokdefParser(TokenBuffer tokenBuffer) {
        this(tokenBuffer, 3);
    }

    protected ANTLRTokdefParser(TokenStream tokenStream, int i) {
        super(tokenStream, i);
        this.tokenNames = _tokenNames;
    }

    public ANTLRTokdefParser(TokenStream tokenStream) {
        this(tokenStream, 3);
    }

    public ANTLRTokdefParser(ParserSharedInputState parserSharedInputState) {
        super(parserSharedInputState, 3);
        this.tokenNames = _tokenNames;
    }

    public final void file(ImportVocabTokenManager importVocabTokenManager) throws RecognitionException, TokenStreamException {
        try {
            LT(1);
            match(4);
            while (true) {
                if (LA(1) != 4 && LA(1) != 5) {
                    return;
                } else {
                    line(importVocabTokenManager);
                }
            }
        } catch (RecognitionException e) {
            reportError(e);
            consume();
            consumeUntil(_tokenSet_0);
        }
    }

    public final void line(ImportVocabTokenManager importVocabTokenManager) throws RecognitionException, TokenStreamException {
        Token tokenLT;
        Token tokenLT2;
        try {
            Token token = null;
            if (LA(1) == 5) {
                Token tokenLT3 = LT(1);
                match(5);
                tokenLT2 = null;
                token = tokenLT3;
                tokenLT = null;
            } else if (LA(1) == 4 && LA(2) == 6 && LA(3) == 5) {
                tokenLT = LT(1);
                match(4);
                match(6);
                Token tokenLT4 = LT(1);
                match(5);
                tokenLT2 = null;
                token = tokenLT4;
            } else if (LA(1) == 4 && LA(2) == 7) {
                tokenLT = LT(1);
                match(4);
                match(7);
                tokenLT2 = LT(1);
                match(5);
                match(8);
            } else if (LA(1) == 4 && LA(2) == 6 && LA(3) == 9) {
                tokenLT = LT(1);
                match(4);
                tokenLT2 = null;
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
            match(6);
            Token tokenLT5 = LT(1);
            match(9);
            Integer numValueOf = Integer.valueOf(tokenLT5.getText());
            if (token != null) {
                importVocabTokenManager.define(token.getText(), numValueOf.intValue());
                if (tokenLT != null) {
                    StringLiteralSymbol stringLiteralSymbol = (StringLiteralSymbol) importVocabTokenManager.getTokenSymbol(token.getText());
                    stringLiteralSymbol.setLabel(tokenLT.getText());
                    importVocabTokenManager.mapToTokenSymbol(tokenLT.getText(), stringLiteralSymbol);
                    return;
                }
                return;
            }
            if (tokenLT != null) {
                importVocabTokenManager.define(tokenLT.getText(), numValueOf.intValue());
                if (tokenLT2 != null) {
                    importVocabTokenManager.getTokenSymbol(tokenLT.getText()).setParaphrase(tokenLT2.getText());
                }
            }
        } catch (RecognitionException e) {
            reportError(e);
            consume();
            consumeUntil(_tokenSet_1);
        }
    }

    private static final long[] mk_tokenSet_0() {
        return new long[]{2, 0};
    }

    private static final long[] mk_tokenSet_1() {
        return new long[]{50, 0};
    }
}
