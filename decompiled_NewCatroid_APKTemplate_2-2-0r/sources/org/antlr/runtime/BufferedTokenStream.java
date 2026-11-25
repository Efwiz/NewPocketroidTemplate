package org.antlr.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes12.dex */
public class BufferedTokenStream implements TokenStream {
    protected int lastMarker;
    protected TokenSource tokenSource;
    protected List<Token> tokens = new ArrayList(100);
    protected int p = -1;
    protected int range = -1;

    @Override // org.antlr.runtime.IntStream
    public void release(int i) {
    }

    public BufferedTokenStream() {
    }

    public BufferedTokenStream(TokenSource tokenSource) {
        this.tokenSource = tokenSource;
    }

    @Override // org.antlr.runtime.TokenStream
    public TokenSource getTokenSource() {
        return this.tokenSource;
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
    public int mark() {
        if (this.p == -1) {
            setup();
        }
        int iIndex = index();
        this.lastMarker = iIndex;
        return iIndex;
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

    @Override // org.antlr.runtime.IntStream
    public int size() {
        return this.tokens.size();
    }

    @Override // org.antlr.runtime.IntStream
    public void consume() {
        if (this.p == -1) {
            setup();
        }
        int i = this.p + 1;
        this.p = i;
        sync(i);
    }

    protected void sync(int i) {
        int size = (i - this.tokens.size()) + 1;
        if (size > 0) {
            fetch(size);
        }
    }

    protected void fetch(int i) {
        for (int i2 = 1; i2 <= i; i2++) {
            Token tokenNextToken = this.tokenSource.nextToken();
            tokenNextToken.setTokenIndex(this.tokens.size());
            this.tokens.add(tokenNextToken);
            if (tokenNextToken.getType() == -1) {
                return;
            }
        }
    }

    @Override // org.antlr.runtime.TokenStream
    public Token get(int i) {
        if (i < 0 || i >= this.tokens.size()) {
            throw new NoSuchElementException("token index " + i + " out of range 0.." + (this.tokens.size() - 1));
        }
        return this.tokens.get(i);
    }

    public List<? extends Token> get(int i, int i2) {
        if (i < 0 || i2 < 0) {
            return null;
        }
        if (this.p == -1) {
            setup();
        }
        ArrayList arrayList = new ArrayList();
        if (i2 >= this.tokens.size()) {
            i2 = this.tokens.size() - 1;
        }
        while (i <= i2) {
            Token token = this.tokens.get(i);
            if (token.getType() == -1) {
                break;
            }
            arrayList.add(token);
            i++;
        }
        return arrayList;
    }

    @Override // org.antlr.runtime.IntStream
    public int LA(int i) {
        return LT(i).getType();
    }

    protected Token LB(int i) {
        int i2 = this.p;
        if (i2 - i < 0) {
            return null;
        }
        return this.tokens.get(i2 - i);
    }

    @Override // org.antlr.runtime.TokenStream
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
        int i2 = (this.p + i) - 1;
        sync(i2);
        if (i2 >= this.tokens.size()) {
            List<Token> list = this.tokens;
            return list.get(list.size() - 1);
        }
        if (i2 > this.range) {
            this.range = i2;
        }
        return this.tokens.get(i2);
    }

    protected void setup() {
        sync(0);
        this.p = 0;
    }

    public void setTokenSource(TokenSource tokenSource) {
        this.tokenSource = tokenSource;
        this.tokens.clear();
        this.p = -1;
    }

    public List<? extends Token> getTokens() {
        return this.tokens;
    }

    public List<? extends Token> getTokens(int i, int i2) {
        return getTokens(i, i2, (BitSet) null);
    }

    public List<? extends Token> getTokens(int i, int i2, BitSet bitSet) {
        if (this.p == -1) {
            setup();
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

    @Override // org.antlr.runtime.IntStream
    public String getSourceName() {
        return this.tokenSource.getSourceName();
    }

    public String toString() {
        if (this.p == -1) {
            setup();
        }
        fill();
        return toString(0, this.tokens.size() - 1);
    }

    @Override // org.antlr.runtime.TokenStream
    public String toString(int i, int i2) {
        if (i < 0 || i2 < 0) {
            return null;
        }
        if (this.p == -1) {
            setup();
        }
        if (i2 >= this.tokens.size()) {
            i2 = this.tokens.size() - 1;
        }
        StringBuilder sb = new StringBuilder();
        while (i <= i2) {
            Token token = this.tokens.get(i);
            if (token.getType() == -1) {
                break;
            }
            sb.append(token.getText());
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

    public void fill() {
        if (this.p == -1) {
            setup();
        }
        if (this.tokens.get(this.p).getType() == -1) {
            return;
        }
        int i = this.p + 1;
        sync(i);
        while (this.tokens.get(i).getType() != -1) {
            i++;
            sync(i);
        }
    }
}
