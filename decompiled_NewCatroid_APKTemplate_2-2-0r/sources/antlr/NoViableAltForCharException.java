package antlr;

/* JADX INFO: loaded from: classes.dex */
public class NoViableAltForCharException extends RecognitionException {
    public char foundChar;

    public NoViableAltForCharException(char c, CharScanner charScanner) {
        super("NoViableAlt", charScanner.getFilename(), charScanner.getLine(), charScanner.getColumn());
        this.foundChar = c;
    }

    public NoViableAltForCharException(char c, String str, int i) {
        this(c, str, i, -1);
    }

    public NoViableAltForCharException(char c, String str, int i, int i2) {
        super("NoViableAlt", str, i, i2);
        this.foundChar = c;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        char c = this.foundChar;
        if (c >= ' ' && c <= '~') {
            return new StringBuffer().append(new StringBuffer("unexpected char: '").append(this.foundChar).toString()).append('\'').toString();
        }
        return new StringBuffer("unexpected char: 0x").append(Integer.toHexString(this.foundChar).toUpperCase()).toString();
    }
}
