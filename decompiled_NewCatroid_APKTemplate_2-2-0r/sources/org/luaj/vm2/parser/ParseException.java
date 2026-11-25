package org.luaj.vm2.parser;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes6.dex */
public class ParseException extends Exception {
    private static final long serialVersionUID = 1;
    public Token currentToken;
    protected String eol;
    public int[][] expectedTokenSequences;
    public String[] tokenImage;

    public ParseException() {
        this.eol = System.getProperty(SystemProperties.LINE_SEPARATOR, "\n");
    }

    public ParseException(String str) {
        super(str);
        this.eol = System.getProperty(SystemProperties.LINE_SEPARATOR, "\n");
    }

    public ParseException(Token token, int[][] iArr, String[] strArr) {
        super(initialise(token, iArr, strArr));
        this.eol = System.getProperty(SystemProperties.LINE_SEPARATOR, "\n");
        this.currentToken = token;
        this.expectedTokenSequences = iArr;
        this.tokenImage = strArr;
    }

    static String add_escapes(String str) {
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

    private static String initialise(Token token, int[][] iArr, String[] strArr) {
        StringBuffer stringBufferAppend;
        String str;
        int[] iArr2;
        String property = System.getProperty(SystemProperties.LINE_SEPARATOR, "\n");
        StringBuffer stringBuffer = new StringBuffer();
        int length = 0;
        for (int i = 0; i < iArr.length; i++) {
            int[] iArr3 = iArr[i];
            if (length < iArr3.length) {
                length = iArr3.length;
            }
            int i2 = 0;
            while (true) {
                iArr2 = iArr[i];
                if (i2 >= iArr2.length) {
                    break;
                }
                stringBuffer.append(strArr[iArr2[i2]]).append(StringUtil.SPACE);
                i2++;
            }
            if (iArr2[iArr2.length - 1] != 0) {
                stringBuffer.append("...");
            }
            stringBuffer.append(property).append("    ");
        }
        Token token2 = token.next;
        String string = "Encountered \"";
        for (int i3 = 0; i3 < length; i3++) {
            if (i3 != 0) {
                string = new StringBuffer().append(string).append(" ").toString();
            }
            if (token2.kind == 0) {
                string = new StringBuffer().append(string).append(strArr[0]).toString();
                break;
            }
            string = new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(string).append(" ").append(strArr[token2.kind]).toString()).append(" \"").toString()).append(add_escapes(token2.image)).toString()).append(" \"").toString();
            token2 = token2.next;
        }
        String string2 = new StringBuffer().append(new StringBuffer().append(string).append("\" at line ").append(token.next.beginLine).append(", column ").append(token.next.beginColumn).toString()).append(".").append(property).toString();
        if (iArr.length == 1) {
            stringBufferAppend = new StringBuffer().append(string2);
            str = "Was expecting:";
        } else {
            stringBufferAppend = new StringBuffer().append(string2);
            str = "Was expecting one of:";
        }
        return new StringBuffer().append(stringBufferAppend.append(str).append(property).append("    ").toString()).append(stringBuffer.toString()).toString();
    }
}
