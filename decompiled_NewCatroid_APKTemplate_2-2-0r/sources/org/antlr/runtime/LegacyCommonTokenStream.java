package org.antlr.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes12.dex */
public class LegacyCommonTokenStream implements TokenStream {
    protected int channel;
    protected Map<Integer, Integer> channelOverrideMap;
    protected boolean discardOffChannelTokens;
    protected Set<Integer> discardSet;
    protected int lastMarker;
    protected int p;
    protected int range;
    protected TokenSource tokenSource;
    protected List<Token> tokens;

    @Override // org.antlr.runtime.IntStream
    public void release(int i) {
    }

    public LegacyCommonTokenStream() {
        this.channel = 0;
        this.discardOffChannelTokens = false;
        this.range = -1;
        this.p = -1;
        this.tokens = new ArrayList(500);
    }

    public LegacyCommonTokenStream(TokenSource tokenSource) {
        this();
        this.tokenSource = tokenSource;
    }

    public LegacyCommonTokenStream(TokenSource tokenSource, int i) {
        this(tokenSource);
        this.channel = i;
    }

    public void setTokenSource(TokenSource tokenSource) {
        this.tokenSource = tokenSource;
        this.tokens.clear();
        this.p = -1;
        this.channel = 0;
    }

    protected void fillBuffer() {
        Integer num;
        Token tokenNextToken = this.tokenSource.nextToken();
        int i = 0;
        while (tokenNextToken != null && tokenNextToken.getType() != -1) {
            Map<Integer, Integer> map = this.channelOverrideMap;
            if (map != null && (num = map.get(Integer.valueOf(tokenNextToken.getType()))) != null) {
                tokenNextToken.setChannel(num.intValue());
            }
            Set<Integer> set = this.discardSet;
            if ((set == null || !set.contains(new Integer(tokenNextToken.getType()))) && (!this.discardOffChannelTokens || tokenNextToken.getChannel() == this.channel)) {
                tokenNextToken.setTokenIndex(i);
                this.tokens.add(tokenNextToken);
                i++;
            }
            tokenNextToken = this.tokenSource.nextToken();
        }
        this.p = 0;
        this.p = skipOffTokenChannels(0);
    }

    @Override // org.antlr.runtime.IntStream
    public void consume() {
        if (this.p < this.tokens.size()) {
            int i = this.p + 1;
            this.p = i;
            this.p = skipOffTokenChannels(i);
        }
    }

    protected int skipOffTokenChannels(int i) {
        int size = this.tokens.size();
        while (i < size && this.tokens.get(i).getChannel() != this.channel) {
            i++;
        }
        return i;
    }

    protected int skipOffTokenChannelsReverse(int i) {
        while (i >= 0 && this.tokens.get(i).getChannel() != this.channel) {
            i--;
        }
        return i;
    }

    public void setTokenTypeChannel(int i, int i2) {
        if (this.channelOverrideMap == null) {
            this.channelOverrideMap = new HashMap();
        }
        this.channelOverrideMap.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void discardTokenType(int i) {
        if (this.discardSet == null) {
            this.discardSet = new HashSet();
        }
        this.discardSet.add(Integer.valueOf(i));
    }

    public void discardOffChannelTokens(boolean z) {
        this.discardOffChannelTokens = z;
    }

    public List<? extends Token> getTokens() {
        if (this.p == -1) {
            fillBuffer();
        }
        return this.tokens;
    }

    public List<? extends Token> getTokens(int i, int i2) {
        return getTokens(i, i2, (BitSet) null);
    }

    public List<? extends Token> getTokens(int i, int i2, BitSet bitSet) {
        if (this.p == -1) {
            fillBuffer();
        }
        if (i2 >= this.tokens.size()) {
            i2 = this.tokens.size() - 1;
        }
        if (i < 0) {
            i = 0;
        }
        if (i > i2) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        while (i <= i2) {
            Token token = this.tokens.get(i);
            if (bitSet == null || bitSet.member(token.getType())) {
                arrayList.add(token);
            }
            i++;
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }

    public List<? extends Token> getTokens(int i, int i2, List<Integer> list) {
        return getTokens(i, i2, new BitSet(list));
    }

    public List<? extends Token> getTokens(int i, int i2, int i3) {
        return getTokens(i, i2, BitSet.of(i3));
    }

    @Override // org.antlr.runtime.TokenStream
    public Token LT(int i) {
        if (this.p == -1) {
            fillBuffer();
        }
        if (i == 0) {
            return null;
        }
        if (i < 0) {
            return LB(-i);
        }
        if ((this.p + i) - 1 >= this.tokens.size()) {
            List<Token> list = this.tokens;
            return list.get(list.size() - 1);
        }
        int iSkipOffTokenChannels = this.p;
        for (int i2 = 1; i2 < i; i2++) {
            iSkipOffTokenChannels = skipOffTokenChannels(iSkipOffTokenChannels + 1);
        }
        if (iSkipOffTokenChannels >= this.tokens.size()) {
            List<Token> list2 = this.tokens;
            return list2.get(list2.size() - 1);
        }
        if (iSkipOffTokenChannels > this.range) {
            this.range = iSkipOffTokenChannels;
        }
        return this.tokens.get(iSkipOffTokenChannels);
    }

    protected Token LB(int i) {
        if (this.p == -1) {
            fillBuffer();
        }
        if (i == 0) {
            return null;
        }
        int iSkipOffTokenChannelsReverse = this.p;
        if (iSkipOffTokenChannelsReverse - i < 0) {
            return null;
        }
        for (int i2 = 1; i2 <= i; i2++) {
            iSkipOffTokenChannelsReverse = skipOffTokenChannelsReverse(iSkipOffTokenChannelsReverse - 1);
        }
        if (iSkipOffTokenChannelsReverse < 0) {
            return null;
        }
        return this.tokens.get(iSkipOffTokenChannelsReverse);
    }

    @Override // org.antlr.runtime.TokenStream
    public Token get(int i) {
        return this.tokens.get(i);
    }

    public List<? extends Token> get(int i, int i2) {
        if (this.p == -1) {
            fillBuffer();
        }
        if (i < 0 || i2 < 0) {
            return null;
        }
        return this.tokens.subList(i, i2);
    }

    @Override // org.antlr.runtime.IntStream
    public int LA(int i) {
        return LT(i).getType();
    }

    @Override // org.antlr.runtime.IntStream
    public int mark() {
        if (this.p == -1) {
            fillBuffer();
        }
        int iIndex = index();
        this.lastMarker = iIndex;
        return iIndex;
    }

    @Override // org.antlr.runtime.IntStream
    public int size() {
        return this.tokens.size();
    }

    @Override // org.antlr.runtime.IntStream
    public int index() {
        return this.p;
    }

    @Override // org.antlr.runtime.TokenStream
    public int range() {
        return this.range;
    }

    @Override // org.antlr.runtime.IntStream
    public void rewind(int i) {
        seek(i);
    }

    @Override // org.antlr.runtime.IntStream
    public void rewind() {
        seek(this.lastMarker);
    }

    public void reset() {
        this.p = 0;
        this.lastMarker = 0;
    }

    @Override // org.antlr.runtime.IntStream
    public void seek(int i) {
        this.p = i;
    }

    @Override // org.antlr.runtime.TokenStream
    public TokenSource getTokenSource() {
        return this.tokenSource;
    }

    @Override // org.antlr.runtime.IntStream
    public String getSourceName() {
        return getTokenSource().getSourceName();
    }

    public String toString() {
        if (this.p == -1) {
            fillBuffer();
        }
        return toString(0, this.tokens.size() - 1);
    }

    @Override // org.antlr.runtime.TokenStream
    public String toString(int i, int i2) {
        if (i < 0 || i2 < 0) {
            return null;
        }
        if (this.p == -1) {
            fillBuffer();
        }
        if (i2 >= this.tokens.size()) {
            i2 = this.tokens.size() - 1;
        }
        StringBuilder sb = new StringBuilder();
        while (i <= i2) {
            sb.append(this.tokens.get(i).getText());
            i++;
        }
        return sb.toString();
    }

    @Override // org.antlr.runtime.TokenStream
    public String toString(Token token, Token token2) {
        if (token == null || token2 == null) {
            return null;
        }
        return toString(token.getTokenIndex(), token2.getTokenIndex());
    }
}
