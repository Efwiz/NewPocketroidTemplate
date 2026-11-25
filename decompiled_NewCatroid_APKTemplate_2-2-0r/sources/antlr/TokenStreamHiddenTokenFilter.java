package antlr;

import antlr.collections.impl.BitSet;

/* JADX INFO: loaded from: classes.dex */
public class TokenStreamHiddenTokenFilter extends TokenStreamBasicFilter implements TokenStream {
    protected CommonHiddenStreamToken firstHidden;
    protected BitSet hideMask;
    protected CommonHiddenStreamToken lastHiddenToken;
    protected CommonHiddenStreamToken nextMonitoredToken;

    public TokenStreamHiddenTokenFilter(TokenStream tokenStream) {
        super(tokenStream);
        this.firstHidden = null;
        this.hideMask = new BitSet();
    }

    protected void consume() throws TokenStreamException {
        this.nextMonitoredToken = (CommonHiddenStreamToken) this.input.nextToken();
    }

    private void consumeFirst() throws TokenStreamException {
        consume();
        CommonHiddenStreamToken commonHiddenStreamTokenLA = null;
        while (true) {
            if (!this.hideMask.member(LA(1).getType()) && !this.discardMask.member(LA(1).getType())) {
                return;
            }
            if (this.hideMask.member(LA(1).getType())) {
                if (commonHiddenStreamTokenLA == null) {
                    commonHiddenStreamTokenLA = LA(1);
                } else {
                    commonHiddenStreamTokenLA.setHiddenAfter(LA(1));
                    LA(1).setHiddenBefore(commonHiddenStreamTokenLA);
                    commonHiddenStreamTokenLA = LA(1);
                }
                this.lastHiddenToken = commonHiddenStreamTokenLA;
                if (this.firstHidden == null) {
                    this.firstHidden = commonHiddenStreamTokenLA;
                }
            }
            consume();
        }
    }

    public BitSet getDiscardMask() {
        return this.discardMask;
    }

    public CommonHiddenStreamToken getHiddenAfter(CommonHiddenStreamToken commonHiddenStreamToken) {
        return commonHiddenStreamToken.getHiddenAfter();
    }

    public CommonHiddenStreamToken getHiddenBefore(CommonHiddenStreamToken commonHiddenStreamToken) {
        return commonHiddenStreamToken.getHiddenBefore();
    }

    public BitSet getHideMask() {
        return this.hideMask;
    }

    public CommonHiddenStreamToken getInitialHiddenToken() {
        return this.firstHidden;
    }

    public void hide(int i) {
        this.hideMask.add(i);
    }

    public void hide(BitSet bitSet) {
        this.hideMask = bitSet;
    }

    protected CommonHiddenStreamToken LA(int i) {
        return this.nextMonitoredToken;
    }

    @Override // antlr.TokenStreamBasicFilter, antlr.TokenStream
    public Token nextToken() throws TokenStreamException {
        if (LA(1) == null) {
            consumeFirst();
        }
        CommonHiddenStreamToken commonHiddenStreamTokenLA = LA(1);
        commonHiddenStreamTokenLA.setHiddenBefore(this.lastHiddenToken);
        this.lastHiddenToken = null;
        consume();
        CommonHiddenStreamToken commonHiddenStreamTokenLA2 = commonHiddenStreamTokenLA;
        while (true) {
            if (!this.hideMask.member(LA(1).getType()) && !this.discardMask.member(LA(1).getType())) {
                return commonHiddenStreamTokenLA;
            }
            if (this.hideMask.member(LA(1).getType())) {
                commonHiddenStreamTokenLA2.setHiddenAfter(LA(1));
                if (commonHiddenStreamTokenLA2 != commonHiddenStreamTokenLA) {
                    LA(1).setHiddenBefore(commonHiddenStreamTokenLA2);
                }
                commonHiddenStreamTokenLA2 = LA(1);
                this.lastHiddenToken = commonHiddenStreamTokenLA2;
            }
            consume();
        }
    }
}
