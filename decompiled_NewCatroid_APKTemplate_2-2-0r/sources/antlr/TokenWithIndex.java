package antlr;

/* JADX INFO: loaded from: classes.dex */
public class TokenWithIndex extends CommonToken {
    int index;

    public TokenWithIndex() {
    }

    public TokenWithIndex(int i, String str) {
        super(i, str);
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public int getIndex() {
        return this.index;
    }

    @Override // antlr.CommonToken, antlr.Token
    public String toString() {
        return new StringBuffer("[").append(this.index).append(":\"").append(getText()).append("\",<").append(getType()).append(">,line=").append(this.line).append(",col=").append(this.col).append("]\n").toString();
    }
}
