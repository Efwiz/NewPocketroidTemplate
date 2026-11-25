package org.antlr.runtime;

/* JADX INFO: loaded from: classes12.dex */
public class CommonTokenStream extends BufferedTokenStream {
    protected int channel;

    public CommonTokenStream() {
        this.channel = 0;
    }

    public CommonTokenStream(TokenSource tokenSource) {
        super(tokenSource);
        this.channel = 0;
    }

    public CommonTokenStream(TokenSource tokenSource, int i) {
        this(tokenSource);
        this.channel = i;
    }

    @Override // org.antlr.runtime.BufferedTokenStream, org.antlr.runtime.IntStream
    public void consume() {
        if (this.p == -1) {
            setup();
        }
        this.p++;
        sync(this.p);
        while (this.tokens.get(this.p).getChannel() != this.channel) {
            this.p++;
            sync(this.p);
        }
    }

    @Override // org.antlr.runtime.BufferedTokenStream
    protected Token LB(int i) {
        if (i == 0 || this.p - i < 0) {
            return null;
        }
        int iSkipOffTokenChannelsReverse = this.p;
        for (int i2 = 1; i2 <= i; i2++) {
            iSkipOffTokenChannelsReverse = skipOffTokenChannelsReverse(iSkipOffTokenChannelsReverse - 1);
        }
        if (iSkipOffTokenChannelsReverse < 0) {
            return null;
        }
        return this.tokens.get(iSkipOffTokenChannelsReverse);
    }

    @Override // org.antlr.runtime.BufferedTokenStream, org.antlr.runtime.TokenStream
    public Token LT(int i) {
        if (this.p == -1) {
            setup();
        }
        if (i == 0) {
            return null;
        }
        if (i < 0) {
            return LB(-i);
        }
        int iSkipOffTokenChannels = this.p;
        for (int i2 = 1; i2 < i; i2++) {
            iSkipOffTokenChannels = skipOffTokenChannels(iSkipOffTokenChannels + 1);
        }
        if (iSkipOffTokenChannels > this.range) {
            this.range = iSkipOffTokenChannels;
        }
        return this.tokens.get(iSkipOffTokenChannels);
    }

    protected int skipOffTokenChannels(int i) {
        sync(i);
        while (this.tokens.get(i).getChannel() != this.channel) {
            i++;
            sync(i);
        }
        return i;
    }

    protected int skipOffTokenChannelsReverse(int i) {
        while (i >= 0 && this.tokens.get(i).getChannel() != this.channel) {
            i--;
        }
        return i;
    }

    @Override // org.antlr.runtime.BufferedTokenStream
    public void reset() {
        super.reset();
        this.p = skipOffTokenChannels(0);
    }

    @Override // org.antlr.runtime.BufferedTokenStream
    protected void setup() {
        int i = 0;
        this.p = 0;
        sync(0);
        while (this.tokens.get(i).getChannel() != this.channel) {
            i++;
            sync(i);
        }
        this.p = i;
    }

    public int getNumberOfOnChannelTokens() {
        fill();
        int i = 0;
        for (int i2 = 0; i2 < this.tokens.size(); i2++) {
            Token token = this.tokens.get(i2);
            if (token.getChannel() == this.channel) {
                i++;
            }
            if (token.getType() == -1) {
                break;
            }
        }
        return i;
    }

    @Override // org.antlr.runtime.BufferedTokenStream
    public void setTokenSource(TokenSource tokenSource) {
        super.setTokenSource(tokenSource);
        this.channel = 0;
    }
}
