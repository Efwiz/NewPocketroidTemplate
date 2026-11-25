package org.luaj.vm2.parser;

/* JADX INFO: loaded from: classes6.dex */
public class TokenMgrError extends Error {
    static final int INVALID_LEXICAL_STATE = 2;
    static final int LEXICAL_ERROR = 0;
    static final int LOOP_DETECTED = 3;
    static final int STATIC_LEXER_ERROR = 1;
    private static final long serialVersionUID = 1;
    int errorCode;

    public TokenMgrError() {
    }

    public TokenMgrError(String str, int i) {
        super(str);
        this.errorCode = i;
    }

    public TokenMgrError(boolean z, int i, int i2, int i3, String str, char c, int i4) {
        this(LexicalError(z, i, i2, i3, str, c), i4);
    }

    protected static String LexicalError(boolean z, int i, int i2, int i3, String str, char c) {
        return new StringBuffer("Lexical error at line ").append(i2).append(", column ").append(i3).append(".  Encountered: ").append(z ? "<EOF> " : new StringBuffer("\"").append(addEscapes(String.valueOf(c))).append("\" (").append((int) c).append("), ").toString()).append("after : \"").append(addEscapes(str)).append("\"").toString();
    }

    protected static final String addEscapes(String str) {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt != 0) {
                if (cCharAt == '\"') {
                    string = "\\\"";
                } else if (cCharAt == '\'') {
                    string = "\\'";
                } else if (cCharAt == '\\') {
                    string = "\\\\";
                } else if (cCharAt == '\f') {
                    string = "\\f";
                } else if (cCharAt != '\r') {
                    switch (cCharAt) {
                        case '\b':
                            string = "\\b";
                            break;
                        case '\t':
                            string = "\\t";
                            break;
                        case '\n':
                            string = "\\n";
                            break;
                        default:
                            char cCharAt2 = str.charAt(i);
                            if (cCharAt2 < ' ' || cCharAt2 > '~') {
                                String string2 = new StringBuffer("0000").append(Integer.toString(cCharAt2, 16)).toString();
                                string = new StringBuffer("\\u").append(string2.substring(string2.length() - 4, string2.length())).toString();
                            } else {
                                stringBuffer.append(cCharAt2);
                            }
                            break;
                    }
                } else {
                    string = "\\r";
                }
                stringBuffer.append(string);
            }
        }
        return stringBuffer.toString();
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return super.getMessage();
    }
}
