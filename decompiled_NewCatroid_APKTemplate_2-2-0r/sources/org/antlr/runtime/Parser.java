package org.antlr.runtime;

/* JADX INFO: loaded from: classes12.dex */
public class Parser extends BaseRecognizer {
    public TokenStream input;

    public Parser(TokenStream tokenStream) {
        setTokenStream(tokenStream);
    }

    public Parser(TokenStream tokenStream, RecognizerSharedState recognizerSharedState) {
        super(recognizerSharedState);
        this.input = tokenStream;
    }

    @Override // org.antlr.runtime.BaseRecognizer
    public void reset() {
        super.reset();
        TokenStream tokenStream = this.input;
        if (tokenStream != null) {
            tokenStream.seek(0);
        }
    }

    @Override // org.antlr.runtime.BaseRecognizer
    protected Object getCurrentInputSymbol(IntStream intStream) {
        return ((TokenStream) intStream).LT(1);
    }

    @Override // org.antlr.runtime.BaseRecognizer
    protected Object getMissingSymbol(IntStream intStream, RecognitionException recognitionException, int i, BitSet bitSet) {
        String str;
        if (i == -1) {
            str = "<missing EOF>";
        } else {
            str = "<missing " + getTokenNames()[i] + ">";
        }
        CommonToken commonToken = new CommonToken(i, str);
        TokenStream tokenStream = (TokenStream) intStream;
        Token tokenLT = tokenStream.LT(1);
        if (tokenLT.getType() == -1) {
            tokenLT = tokenStream.LT(-1);
        }
        commonToken.line = tokenLT.getLine();
        commonToken.charPositionInLine = tokenLT.getCharPositionInLine();
        commonToken.channel = 0;
        commonToken.input = tokenLT.getInputStream();
        return commonToken;
    }

    public void setTokenStream(TokenStream tokenStream) {
        this.input = null;
        reset();
        this.input = tokenStream;
    }

    public TokenStream getTokenStream() {
        return this.input;
    }

    @Override // org.antlr.runtime.BaseRecognizer, org.antlr.runtime.TokenSource
    public String getSourceName() {
        return this.input.getSourceName();
    }

    public void traceIn(String str, int i) {
        super.traceIn(str, i, this.input.LT(1));
    }

    public void traceOut(String str, int i) {
        super.traceOut(str, i, this.input.LT(1));
    }
}
