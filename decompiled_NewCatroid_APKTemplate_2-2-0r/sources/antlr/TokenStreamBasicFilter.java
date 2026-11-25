package antlr;

import antlr.ASdebug.ASDebugStream;
import antlr.ASdebug.IASDebugStream;
import antlr.ASdebug.TokenOffsetInfo;
import antlr.collections.impl.BitSet;

/* JADX INFO: loaded from: classes.dex */
public class TokenStreamBasicFilter implements TokenStream, IASDebugStream {
    protected BitSet discardMask = new BitSet();
    protected TokenStream input;

    public TokenStreamBasicFilter(TokenStream tokenStream) {
        this.input = tokenStream;
    }

    public void discard(int i) {
        this.discardMask.add(i);
    }

    public void discard(BitSet bitSet) {
        this.discardMask = bitSet;
    }

    @Override // antlr.TokenStream
    public Token nextToken() throws TokenStreamException {
        Token tokenNextToken = this.input.nextToken();
        while (tokenNextToken != null && this.discardMask.member(tokenNextToken.getType())) {
            tokenNextToken = this.input.nextToken();
        }
        return tokenNextToken;
    }

    @Override // antlr.ASdebug.IASDebugStream
    public String getEntireText() {
        return ASDebugStream.getEntireText(this.input);
    }

    @Override // antlr.ASdebug.IASDebugStream
    public TokenOffsetInfo getOffsetInfo(Token token) {
        return ASDebugStream.getOffsetInfo(this.input, token);
    }
}
