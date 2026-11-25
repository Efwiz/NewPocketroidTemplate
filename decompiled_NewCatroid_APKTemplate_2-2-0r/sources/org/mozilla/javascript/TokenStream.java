package org.mozilla.javascript;

import java.io.IOException;
import java.io.Reader;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes4.dex */
class TokenStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final char BYTE_ORDER_MARK = 65279;
    private static final int EOF_CHAR = -1;
    Token.CommentType commentType;
    int cursor;
    private boolean dirtyLine;
    private boolean isBinary;
    private boolean isHex;
    private boolean isOctal;
    private boolean isOldOctal;
    int lineno;
    private double number;
    private Parser parser;
    private int quoteChar;
    String regExpFlags;
    private char[] sourceBuffer;
    int sourceCursor;
    private int sourceEnd;
    private Reader sourceReader;
    private String sourceString;
    private int stringBufferTop;
    int tokenBeg;
    int tokenEnd;
    private int ungetCursor;
    private boolean xmlIsAttribute;
    private boolean xmlIsTagContent;
    private int xmlOpenTagsCount;
    private String string = "";
    private char[] stringBuffer = new char[128];
    private ObjToIntMap allStrings = new ObjToIntMap(50);
    private final int[] ungetBuffer = new int[3];
    private boolean hitEOF = false;
    private int lineStart = 0;
    private int lineEndChar = -1;
    private String commentPrefix = "";
    private int commentCursor = -1;

    private static boolean isAlpha(int i) {
        if (i <= 90) {
            return 65 <= i;
        }
        return 97 <= i && i <= 122;
    }

    static boolean isDigit(int i) {
        return 48 <= i && i <= 57;
    }

    TokenStream(Parser parser, Reader reader, String str, int i) {
        this.parser = parser;
        this.lineno = i;
        if (reader != null) {
            if (str != null) {
                Kit.codeBug();
            }
            this.sourceReader = reader;
            this.sourceBuffer = new char[512];
            this.sourceEnd = 0;
        } else {
            if (str == null) {
                Kit.codeBug();
            }
            this.sourceString = str;
            this.sourceEnd = str.length();
        }
        this.cursor = 0;
        this.sourceCursor = 0;
    }

    String tokenToString(int i) {
        return "";
    }

    static boolean isKeyword(String str, int i, boolean z) {
        return stringToKeyword(str, i, z) != 0;
    }

    private static int stringToKeyword(String str, int i, boolean z) {
        if (i < 200) {
            return stringToKeywordForJS(str);
        }
        return stringToKeywordForES(str, z);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:200:0x029e  */
    /* JADX WARN: Code duplicated, block: B:219:0x02d6  */
    /* JADX WARN: Code duplicated, block: B:221:0x02da A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:225:0x02e4 A[PHI: r2
      0x02e4: PHI (r2v39 int) = (r2v2 int), (r2v0 int), (r2v40 int), (r2v40 int), (r2v40 int) binds: [B:217:0x02d3, B:158:0x021c, B:220:0x02d8, B:221:0x02da, B:223:0x02e0] A[DONT_GENERATE, DONT_INLINE]] */
    private static int stringToKeywordForJS(String str) {
        String str2;
        int i;
        int i2 = 116;
        switch (str.length()) {
            case 2:
                i2 = 119;
                char cCharAt = str.charAt(1);
                if (cCharAt != 'f') {
                    if (cCharAt == 'n') {
                        if (str.charAt(0) == 'i') {
                            i = 52;
                        }
                        break;
                    } else if (cCharAt == 'o' && str.charAt(0) == 'd') {
                        i = i2;
                        break;
                    }
                    str2 = null;
                    i2 = 0;
                    if (str2 != null) {
                        i = i2;
                    } else {
                        i = i2;
                    }
                } else if (str.charAt(0) != 'i') {
                    str2 = null;
                    i2 = 0;
                    if (str2 != null || str2 == str || str2.equals(str)) {
                        i = i2;
                    } else {
                        i = 0;
                    }
                } else {
                    i = 113;
                }
                break;
            case 3:
                char cCharAt2 = str.charAt(0);
                if (cCharAt2 != 'f') {
                    if (cCharAt2 != 'i') {
                        if (cCharAt2 != 'l') {
                            if (cCharAt2 != 'n') {
                                if (cCharAt2 == 't') {
                                    if (str.charAt(2) == 'y' && str.charAt(1) == 'r') {
                                        i = 82;
                                        break;
                                    }
                                } else {
                                    if (cCharAt2 == 'v' && str.charAt(2) == 'r' && str.charAt(1) == 'a') {
                                        i = 123;
                                    }
                                    break;
                                }
                            } else if (str.charAt(2) == 'w' && str.charAt(1) == 'e') {
                                i = 30;
                                break;
                            }
                        } else if (str.charAt(2) == 't' && str.charAt(1) == 'e') {
                            i = 154;
                            break;
                        }
                    } else if (str.charAt(2) == 't' && str.charAt(1) == 'n') {
                        i = 128;
                        break;
                    }
                    str2 = null;
                    i2 = 0;
                    if (str2 != null) {
                        i = i2;
                    } else {
                        i = i2;
                    }
                } else if (str.charAt(2) == 'r' && str.charAt(1) == 'o') {
                    i = 120;
                } else {
                    str2 = null;
                    i2 = 0;
                    if (str2 != null) {
                        i = i2;
                    } else {
                        i = i2;
                    }
                }
                break;
            case 4:
                char cCharAt3 = str.charAt(0);
                if (cCharAt3 == 'b') {
                    str2 = "byte";
                } else if (cCharAt3 == 'c') {
                    char cCharAt4 = str.charAt(3);
                    if (cCharAt4 == 'e') {
                        if (str.charAt(2) == 's' && str.charAt(1) == 'a') {
                            i = i2;
                            break;
                        }
                    } else if (cCharAt4 == 'r' && str.charAt(2) == 'a' && str.charAt(1) == 'h') {
                        i = 128;
                        break;
                    }
                    str2 = null;
                    i2 = 0;
                    if (str2 != null) {
                        i = i2;
                    } else {
                        i = i2;
                    }
                } else if (cCharAt3 == 'e') {
                    char cCharAt5 = str.charAt(3);
                    if (cCharAt5 == 'e') {
                        if (str.charAt(2) == 's' && str.charAt(1) == 'l') {
                            i = 114;
                            break;
                        }
                    } else if (cCharAt5 == 'm' && str.charAt(2) == 'u' && str.charAt(1) == 'n') {
                        i = 128;
                        break;
                    }
                    str2 = null;
                    i2 = 0;
                    if (str2 != null) {
                        i = i2;
                    } else {
                        i = i2;
                    }
                } else if (cCharAt3 == 'g') {
                    str2 = PsiKeyword.GOTO;
                } else if (cCharAt3 != 'l') {
                    if (cCharAt3 != 'n') {
                        if (cCharAt3 == 't') {
                            char cCharAt6 = str.charAt(3);
                            if (cCharAt6 == 'e') {
                                if (str.charAt(2) == 'u' && str.charAt(1) == 'r') {
                                    i = 45;
                                    break;
                                }
                            } else if (cCharAt6 == 's' && str.charAt(2) == 'i' && str.charAt(1) == 'h') {
                                i = 43;
                                break;
                            }
                        } else if (cCharAt3 == 'v') {
                            str2 = PsiKeyword.VOID;
                            i2 = 127;
                        } else if (cCharAt3 == 'w') {
                            str2 = "with";
                            i2 = 124;
                        }
                        str2 = null;
                        i2 = 0;
                    } else {
                        str2 = "null";
                        i2 = 42;
                    }
                    if (str2 != null) {
                        i = i2;
                    } else {
                        i = i2;
                    }
                } else {
                    str2 = "long";
                }
                i2 = 128;
                if (str2 != null) {
                    i = i2;
                } else {
                    i = i2;
                }
                break;
            case 5:
                char cCharAt7 = str.charAt(2);
                if (cCharAt7 != 'a') {
                    if (cCharAt7 == 'e') {
                        char cCharAt8 = str.charAt(0);
                        if (cCharAt8 == 'b') {
                            str2 = PsiKeyword.BREAK;
                            i2 = 121;
                        } else if (cCharAt8 == 'y') {
                            str2 = PsiKeyword.YIELD;
                            i2 = 73;
                        } else {
                            str2 = null;
                            i2 = 0;
                        }
                    } else if (cCharAt7 == 'i') {
                        str2 = PsiKeyword.WHILE;
                        i2 = 118;
                    } else if (cCharAt7 == 'l') {
                        str2 = "false";
                        i2 = 44;
                    } else if (cCharAt7 == 'r') {
                        str2 = PsiKeyword.THROW;
                        i2 = 50;
                    } else if (cCharAt7 != 't') {
                        switch (cCharAt7) {
                            case 'n':
                                char cCharAt9 = str.charAt(0);
                                if (cCharAt9 == 'c') {
                                    str2 = PsiKeyword.CONST;
                                    i2 = 155;
                                } else if (cCharAt9 != 'f') {
                                    str2 = null;
                                    i2 = 0;
                                } else {
                                    str2 = "final";
                                }
                                break;
                            case 'o':
                                char cCharAt10 = str.charAt(0);
                                if (cCharAt10 == 'f') {
                                    str2 = "float";
                                } else if (cCharAt10 != 's') {
                                    str2 = null;
                                    i2 = 0;
                                } else {
                                    str2 = "short";
                                }
                                break;
                            case 'p':
                                str2 = PsiKeyword.SUPER;
                                break;
                            default:
                                str2 = null;
                                i2 = 0;
                                break;
                        }
                    } else {
                        str2 = PsiKeyword.CATCH;
                        i2 = 125;
                    }
                    if (str2 != null) {
                        i = i2;
                    } else {
                        i = i2;
                    }
                } else {
                    str2 = "class";
                }
                i2 = 128;
                if (str2 != null) {
                    i = i2;
                } else {
                    i = i2;
                }
                break;
            case 6:
                char cCharAt11 = str.charAt(1);
                if (cCharAt11 != 'a') {
                    if (cCharAt11 != 'e') {
                        if (cCharAt11 != 'h') {
                            if (cCharAt11 != 'm') {
                                if (cCharAt11 != 'o') {
                                    if (cCharAt11 != 't') {
                                        if (cCharAt11 == 'u') {
                                            str2 = "public";
                                        } else {
                                            switch (cCharAt11) {
                                                case 'w':
                                                    str2 = PsiKeyword.SWITCH;
                                                    i2 = 115;
                                                    break;
                                                case 'x':
                                                    str2 = "export";
                                                    break;
                                                case 'y':
                                                    str2 = "typeof";
                                                    i2 = 32;
                                                    break;
                                                default:
                                                    str2 = null;
                                                    i2 = 0;
                                                    break;
                                            }
                                        }
                                    } else {
                                        str2 = "static";
                                    }
                                } else {
                                    str2 = "double";
                                }
                            } else {
                                str2 = PsiKeyword.IMPORT;
                            }
                        } else {
                            str2 = PsiKeyword.THROWS;
                        }
                    } else {
                        char cCharAt12 = str.charAt(0);
                        if (cCharAt12 == 'd') {
                            str2 = "delete";
                            i2 = 31;
                        } else if (cCharAt12 == 'r') {
                            str2 = "return";
                            i2 = 4;
                        } else {
                            str2 = null;
                            i2 = 0;
                        }
                    }
                    if (str2 != null) {
                        i = i2;
                    } else {
                        i = i2;
                    }
                } else {
                    str2 = "native";
                }
                i2 = 128;
                if (str2 != null) {
                    i = i2;
                } else {
                    i = i2;
                }
                break;
            case 7:
                char cCharAt13 = str.charAt(1);
                if (cCharAt13 != 'a') {
                    if (cCharAt13 == 'e') {
                        str2 = "default";
                        i2 = 117;
                    } else if (cCharAt13 == 'i') {
                        str2 = PsiKeyword.FINALLY;
                        i2 = 126;
                    } else if (cCharAt13 == 'o') {
                        str2 = "boolean";
                    } else if (cCharAt13 == 'r') {
                        str2 = "private";
                    } else if (cCharAt13 != 'x') {
                        str2 = null;
                        i2 = 0;
                    } else {
                        str2 = PsiKeyword.EXTENDS;
                    }
                    if (str2 != null) {
                        i = i2;
                    } else {
                        i = i2;
                    }
                } else {
                    str2 = "package";
                }
                i2 = 128;
                if (str2 != null) {
                    i = i2;
                } else {
                    i = i2;
                }
                break;
            case 8:
                char cCharAt14 = str.charAt(0);
                if (cCharAt14 != 'a') {
                    if (cCharAt14 == 'f') {
                        str2 = "function";
                        i2 = 110;
                    } else if (cCharAt14 == 'v') {
                        str2 = "volatile";
                    } else if (cCharAt14 == 'c') {
                        str2 = PsiKeyword.CONTINUE;
                        i2 = 122;
                    } else if (cCharAt14 != 'd') {
                        str2 = null;
                        i2 = 0;
                    } else {
                        str2 = "debugger";
                        i2 = 161;
                    }
                    if (str2 != null) {
                        i = i2;
                    } else {
                        i = i2;
                    }
                } else {
                    str2 = "abstract";
                }
                i2 = 128;
                if (str2 != null) {
                    i = i2;
                } else {
                    i = i2;
                }
                break;
            case 9:
                char cCharAt15 = str.charAt(0);
                if (cCharAt15 == 'i') {
                    str2 = PsiKeyword.INTERFACE;
                } else if (cCharAt15 != 'p') {
                    if (cCharAt15 == 't') {
                        str2 = "transient";
                    } else {
                        str2 = null;
                        i2 = 0;
                    }
                    if (str2 != null) {
                        i = i2;
                    } else {
                        i = i2;
                    }
                } else {
                    str2 = "protected";
                }
                i2 = 128;
                if (str2 != null) {
                    i = i2;
                } else {
                    i = i2;
                }
                break;
            case 10:
                char cCharAt16 = str.charAt(1);
                if (cCharAt16 == 'm') {
                    str2 = PsiKeyword.IMPLEMENTS;
                    i2 = 128;
                } else if (cCharAt16 == 'n') {
                    str2 = PsiKeyword.INSTANCEOF;
                    i2 = 53;
                } else {
                    str2 = null;
                    i2 = 0;
                }
                if (str2 != null) {
                    i = i2;
                } else {
                    i = i2;
                }
                break;
            case 11:
            default:
                str2 = null;
                i2 = 0;
                if (str2 != null) {
                    i = i2;
                } else {
                    i = i2;
                }
                break;
            case 12:
                str2 = "synchronized";
                i2 = 128;
                if (str2 != null) {
                    i = i2;
                } else {
                    i = i2;
                }
                break;
        }
        if (i == 0) {
            return 0;
        }
        return i & 255;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:186:0x026a  */
    /* JADX WARN: Code duplicated, block: B:188:0x026e A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:192:0x0278 A[PHI: r2
      0x0278: PHI (r2v38 int) = (r2v2 int), (r2v39 int), (r2v39 int), (r2v39 int) binds: [B:184:0x0267, B:187:0x026c, B:188:0x026e, B:190:0x0274] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:40:0x0094  */
    /* JADX WARN: Code duplicated, block: B:52:0x00b8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:53:0x00ba  */
    /* JADX WARN: Code duplicated, block: B:54:0x00bd  */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x01a8, code lost:
    
        if (r17.charAt(1) == 'l') goto L193;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int stringToKeywordForES(java.lang.String r17, boolean r18) {
        /*
            Method dump skipped, instruction units count: 672
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.TokenStream.stringToKeywordForES(java.lang.String, boolean):int");
    }

    final String getSourceString() {
        return this.sourceString;
    }

    final int getLineno() {
        return this.lineno;
    }

    final String getString() {
        return this.string;
    }

    final char getQuoteChar() {
        return (char) this.quoteChar;
    }

    final double getNumber() {
        return this.number;
    }

    final boolean isNumberBinary() {
        return this.isBinary;
    }

    final boolean isNumberOldOctal() {
        return this.isOldOctal;
    }

    final boolean isNumberOctal() {
        return this.isOctal;
    }

    final boolean isNumberHex() {
        return this.isHex;
    }

    final boolean eof() {
        return this.hitEOF;
    }

    /* JADX WARN: Code duplicated, block: B:216:0x02a1  */
    /* JADX WARN: Code duplicated, block: B:219:0x02a6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:495:0x0287 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:496:0x02af A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:497:0x02aa A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:498:0x02a8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:501:0x028e A[SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:217:0x02a3 -> B:209:0x0287). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    final int getToken() throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 1504
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.TokenStream.getToken():int");
    }

    static boolean isJSSpace(int i) {
        if (i <= 127) {
            return i == 32 || i == 9 || i == 12 || i == 11;
        }
        return i == 160 || i == 65279 || Character.getType((char) i) == 12;
    }

    private static boolean isJSFormatChar(int i) {
        return i > 127 && Character.getType((char) i) == 16;
    }

    void readRegExp(int i) throws IOException {
        int i2;
        int i3 = this.tokenBeg;
        this.stringBufferTop = 0;
        if (i == 101) {
            addToString(61);
        } else {
            if (i != 24) {
                Kit.codeBug();
            }
            if (peekChar() == 42) {
                this.tokenEnd = this.cursor - 1;
                this.string = new String(this.stringBuffer, 0, this.stringBufferTop);
                this.parser.reportError("msg.unterminated.re.lit");
                return;
            }
        }
        boolean z = false;
        while (true) {
            i2 = getChar();
            if (i2 == 47 && !z) {
                int i4 = this.stringBufferTop;
                while (true) {
                    if (matchChar(103)) {
                        addToString(103);
                    } else if (matchChar(105)) {
                        addToString(105);
                    } else if (matchChar(109)) {
                        addToString(109);
                    } else if (!matchChar(121)) {
                        break;
                    } else {
                        addToString(121);
                    }
                }
                this.tokenEnd = i3 + this.stringBufferTop + 2;
                if (isAlpha(peekChar())) {
                    this.parser.reportError("msg.invalid.re.flag");
                }
                this.string = new String(this.stringBuffer, 0, i4);
                this.regExpFlags = new String(this.stringBuffer, i4, this.stringBufferTop - i4);
                return;
            }
            if (i2 == 10 || i2 == -1) {
                break;
            }
            if (i2 == 92) {
                addToString(i2);
                i2 = getChar();
                if (i2 == 10 || i2 == -1) {
                    ungetChar(i2);
                    this.tokenEnd = this.cursor - 1;
                    this.string = new String(this.stringBuffer, 0, this.stringBufferTop);
                    this.parser.reportError("msg.unterminated.re.lit");
                    return;
                }
            } else if (i2 == 91) {
                z = true;
            } else if (i2 == 93) {
                z = false;
            }
            addToString(i2);
        }
        ungetChar(i2);
        this.tokenEnd = this.cursor - 1;
        this.string = new String(this.stringBuffer, 0, this.stringBufferTop);
        this.parser.reportError("msg.unterminated.re.lit");
    }

    String readAndClearRegExpFlags() {
        String str = this.regExpFlags;
        this.regExpFlags = null;
        return str;
    }

    boolean isXMLAttribute() {
        return this.xmlIsAttribute;
    }

    int getFirstXMLToken() throws IOException {
        this.xmlOpenTagsCount = 0;
        this.xmlIsAttribute = false;
        this.xmlIsTagContent = false;
        if (!canUngetChar()) {
            return -1;
        }
        ungetChar(60);
        return getNextXMLToken();
    }

    int getNextXMLToken() throws IOException {
        this.tokenBeg = this.cursor;
        this.stringBufferTop = 0;
        while (true) {
            int i = getChar();
            if (i != -1) {
                if (this.xmlIsTagContent) {
                    if (i == 9 || i == 10 || i == 13 || i == 32) {
                        addToString(i);
                    } else if (i == 34 || i == 39) {
                        addToString(i);
                        if (!readQuotedString(i)) {
                            return -1;
                        }
                    } else if (i == 47) {
                        addToString(i);
                        if (peekChar() == 62) {
                            addToString(getChar());
                            this.xmlIsTagContent = false;
                            this.xmlOpenTagsCount--;
                        }
                    } else {
                        if (i == 123) {
                            ungetChar(i);
                            this.string = getStringFromBuffer();
                            return 146;
                        }
                        if (i == 61) {
                            addToString(i);
                            this.xmlIsAttribute = true;
                        } else if (i == 62) {
                            addToString(i);
                            this.xmlIsTagContent = false;
                            this.xmlIsAttribute = false;
                        } else {
                            addToString(i);
                            this.xmlIsAttribute = false;
                        }
                    }
                    if (!this.xmlIsTagContent && this.xmlOpenTagsCount == 0) {
                        this.string = getStringFromBuffer();
                        return 149;
                    }
                } else if (i == 60) {
                    addToString(i);
                    int iPeekChar = peekChar();
                    if (iPeekChar == 33) {
                        addToString(getChar());
                        int iPeekChar2 = peekChar();
                        if (iPeekChar2 == 45) {
                            addToString(getChar());
                            int i2 = getChar();
                            if (i2 == 45) {
                                addToString(i2);
                                if (!readXmlComment()) {
                                    return -1;
                                }
                            } else {
                                this.stringBufferTop = 0;
                                this.string = null;
                                this.parser.addError("msg.XML.bad.form");
                                return -1;
                            }
                        } else if (iPeekChar2 == 91) {
                            addToString(getChar());
                            if (getChar() == 67 && getChar() == 68 && getChar() == 65 && getChar() == 84 && getChar() == 65 && getChar() == 91) {
                                addToString(67);
                                addToString(68);
                                addToString(65);
                                addToString(84);
                                addToString(65);
                                addToString(91);
                                if (!readCDATA()) {
                                    return -1;
                                }
                            } else {
                                this.stringBufferTop = 0;
                                this.string = null;
                                this.parser.addError("msg.XML.bad.form");
                                return -1;
                            }
                        } else if (!readEntity()) {
                            return -1;
                        }
                    } else if (iPeekChar == 47) {
                        addToString(getChar());
                        int i3 = this.xmlOpenTagsCount;
                        if (i3 == 0) {
                            this.stringBufferTop = 0;
                            this.string = null;
                            this.parser.addError("msg.XML.bad.form");
                            return -1;
                        }
                        this.xmlIsTagContent = true;
                        this.xmlOpenTagsCount = i3 - 1;
                    } else if (iPeekChar == 63) {
                        addToString(getChar());
                        if (!readPI()) {
                            return -1;
                        }
                    } else {
                        this.xmlIsTagContent = true;
                        this.xmlOpenTagsCount++;
                    }
                } else {
                    if (i == 123) {
                        ungetChar(i);
                        this.string = getStringFromBuffer();
                        return 146;
                    }
                    addToString(i);
                }
            } else {
                this.tokenEnd = this.cursor;
                this.stringBufferTop = 0;
                this.string = null;
                this.parser.addError("msg.XML.bad.form");
                return -1;
            }
        }
    }

    private boolean readQuotedString(int i) throws IOException {
        int i2;
        do {
            i2 = getChar();
            if (i2 != -1) {
                addToString(i2);
            } else {
                this.stringBufferTop = 0;
                this.string = null;
                this.parser.addError("msg.XML.bad.form");
                return false;
            }
        } while (i2 != i);
        return true;
    }

    private boolean readXmlComment() throws IOException {
        int i = getChar();
        while (i != -1) {
            addToString(i);
            if (i == 45 && peekChar() == 45) {
                i = getChar();
                addToString(i);
                if (peekChar() == 62) {
                    addToString(getChar());
                    return true;
                }
            } else {
                i = getChar();
            }
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    private boolean readCDATA() throws IOException {
        int i = getChar();
        while (i != -1) {
            addToString(i);
            if (i == 93 && peekChar() == 93) {
                i = getChar();
                addToString(i);
                if (peekChar() == 62) {
                    addToString(getChar());
                    return true;
                }
            } else {
                i = getChar();
            }
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    private boolean readEntity() throws IOException {
        int i = getChar();
        int i2 = 1;
        while (i != -1) {
            addToString(i);
            if (i == 60) {
                i2++;
            } else if (i == 62 && (i2 = i2 - 1) == 0) {
                return true;
            }
            i = getChar();
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    private boolean readPI() throws IOException {
        while (true) {
            int i = getChar();
            if (i != -1) {
                addToString(i);
                if (i == 63 && peekChar() == 62) {
                    addToString(getChar());
                    return true;
                }
            } else {
                this.stringBufferTop = 0;
                this.string = null;
                this.parser.addError("msg.XML.bad.form");
                return false;
            }
        }
    }

    private String getStringFromBuffer() {
        this.tokenEnd = this.cursor;
        return new String(this.stringBuffer, 0, this.stringBufferTop);
    }

    private void addToString(int i) {
        int i2 = this.stringBufferTop;
        char[] cArr = this.stringBuffer;
        if (i2 == cArr.length) {
            char[] cArr2 = new char[cArr.length * 2];
            System.arraycopy(cArr, 0, cArr2, 0, i2);
            this.stringBuffer = cArr2;
        }
        this.stringBuffer[i2] = (char) i;
        this.stringBufferTop = i2 + 1;
    }

    private boolean canUngetChar() {
        int i = this.ungetCursor;
        return i == 0 || this.ungetBuffer[i - 1] != 10;
    }

    private void ungetChar(int i) {
        int i2 = this.ungetCursor;
        if (i2 != 0 && this.ungetBuffer[i2 - 1] == 10) {
            Kit.codeBug();
        }
        int[] iArr = this.ungetBuffer;
        int i3 = this.ungetCursor;
        this.ungetCursor = i3 + 1;
        iArr[i3] = i;
        this.cursor--;
    }

    private boolean matchChar(int i) throws IOException {
        int charIgnoreLineEnd = getCharIgnoreLineEnd();
        if (charIgnoreLineEnd == i) {
            this.tokenEnd = this.cursor;
            return true;
        }
        ungetCharIgnoreLineEnd(charIgnoreLineEnd);
        return false;
    }

    private int peekChar() throws IOException {
        int i = getChar();
        ungetChar(i);
        return i;
    }

    private int getChar() throws IOException {
        return getChar(true);
    }

    private int getChar(boolean z) throws IOException {
        char cCharAt;
        int i = this.ungetCursor;
        if (i != 0) {
            this.cursor++;
            int[] iArr = this.ungetBuffer;
            int i2 = i - 1;
            this.ungetCursor = i2;
            return iArr[i2];
        }
        while (true) {
            String str = this.sourceString;
            if (str != null) {
                int i3 = this.sourceCursor;
                if (i3 == this.sourceEnd) {
                    this.hitEOF = true;
                    return -1;
                }
                this.cursor++;
                this.sourceCursor = i3 + 1;
                cCharAt = str.charAt(i3);
            } else {
                if (this.sourceCursor == this.sourceEnd && !fillSourceBuffer()) {
                    this.hitEOF = true;
                    return -1;
                }
                this.cursor++;
                char[] cArr = this.sourceBuffer;
                int i4 = this.sourceCursor;
                this.sourceCursor = i4 + 1;
                cCharAt = cArr[i4];
            }
            int i5 = this.lineEndChar;
            if (i5 >= 0) {
                if (i5 == 13 && cCharAt == '\n') {
                    this.lineEndChar = 10;
                } else {
                    this.lineEndChar = -1;
                    this.lineStart = this.sourceCursor - 1;
                    this.lineno++;
                }
            }
            if (cCharAt <= 127) {
                if (cCharAt != '\n' && cCharAt != '\r') {
                    return cCharAt;
                }
                this.lineEndChar = cCharAt;
            } else {
                if (cCharAt == 65279) {
                    return cCharAt;
                }
                if (!z || !isJSFormatChar(cCharAt)) {
                    if (!ScriptRuntime.isJSLineTerminator(cCharAt)) {
                        return cCharAt;
                    }
                    this.lineEndChar = cCharAt;
                }
            }
            return 10;
        }
    }

    private int getCharIgnoreLineEnd() throws IOException {
        char cCharAt;
        int i = this.ungetCursor;
        if (i != 0) {
            this.cursor++;
            int[] iArr = this.ungetBuffer;
            int i2 = i - 1;
            this.ungetCursor = i2;
            return iArr[i2];
        }
        do {
            String str = this.sourceString;
            if (str != null) {
                int i3 = this.sourceCursor;
                if (i3 == this.sourceEnd) {
                    this.hitEOF = true;
                    return -1;
                }
                this.cursor++;
                this.sourceCursor = i3 + 1;
                cCharAt = str.charAt(i3);
            } else {
                if (this.sourceCursor == this.sourceEnd && !fillSourceBuffer()) {
                    this.hitEOF = true;
                    return -1;
                }
                this.cursor++;
                char[] cArr = this.sourceBuffer;
                int i4 = this.sourceCursor;
                this.sourceCursor = i4 + 1;
                cCharAt = cArr[i4];
            }
            if (cCharAt <= 127) {
                if (cCharAt != '\n' && cCharAt != '\r') {
                    return cCharAt;
                }
                this.lineEndChar = cCharAt;
            } else if (cCharAt == 65279) {
                return cCharAt;
            }
            return 10;
        } while (isJSFormatChar(cCharAt));
        if (!ScriptRuntime.isJSLineTerminator(cCharAt)) {
            return cCharAt;
        }
        this.lineEndChar = cCharAt;
        return 10;
    }

    private void ungetCharIgnoreLineEnd(int i) {
        int[] iArr = this.ungetBuffer;
        int i2 = this.ungetCursor;
        this.ungetCursor = i2 + 1;
        iArr[i2] = i;
        this.cursor--;
    }

    private void skipLine() throws IOException {
        int i;
        do {
            i = getChar();
            if (i == -1) {
                break;
            }
        } while (i != 10);
        ungetChar(i);
        this.tokenEnd = this.cursor;
    }

    final int getOffset() {
        int i = this.sourceCursor - this.lineStart;
        return this.lineEndChar >= 0 ? i - 1 : i;
    }

    private final int charAt(int i) {
        if (i < 0) {
            return -1;
        }
        String str = this.sourceString;
        if (str != null) {
            if (i >= this.sourceEnd) {
                return -1;
            }
            return str.charAt(i);
        }
        if (i >= this.sourceEnd) {
            int i2 = this.sourceCursor;
            try {
                if (!fillSourceBuffer()) {
                    return -1;
                }
                i -= i2 - this.sourceCursor;
            } catch (IOException unused) {
                return -1;
            }
        }
        return this.sourceBuffer[i];
    }

    private final String substring(int i, int i2) {
        String str = this.sourceString;
        if (str != null) {
            return str.substring(i, i2);
        }
        return new String(this.sourceBuffer, i, i2 - i);
    }

    final String getLine() {
        int i;
        int i2 = this.sourceCursor;
        int i3 = this.lineEndChar;
        if (i3 >= 0) {
            i = i2 - 1;
            if (i3 == 10 && charAt(i2 - 2) == 13) {
                i = i2 - 2;
            }
        } else {
            int i4 = i2 - this.lineStart;
            while (true) {
                int iCharAt = charAt(this.lineStart + i4);
                if (iCharAt == -1 || ScriptRuntime.isJSLineTerminator(iCharAt)) {
                    break;
                }
                i4++;
            }
            i = this.lineStart + i4;
        }
        return substring(this.lineStart, i);
    }

    final String getLine(int i, int[] iArr) {
        int i2 = (this.cursor + this.ungetCursor) - i;
        int i3 = this.sourceCursor;
        if (i2 > i3) {
            return null;
        }
        int i4 = 0;
        int i5 = 0;
        while (i2 > 0) {
            int iCharAt = charAt(i3 - 1);
            if (ScriptRuntime.isJSLineTerminator(iCharAt)) {
                if (iCharAt == 10 && charAt(i3 - 2) == 13) {
                    i2--;
                    i3--;
                }
                i4++;
                i5 = i3 - 1;
            }
            i2--;
            i3--;
        }
        int i6 = 0;
        while (true) {
            if (i3 <= 0) {
                i3 = 0;
                break;
            }
            if (ScriptRuntime.isJSLineTerminator(charAt(i3 - 1))) {
                break;
            }
            i3--;
            i6++;
        }
        iArr[0] = (this.lineno - i4) + (this.lineEndChar >= 0 ? 1 : 0);
        iArr[1] = i6;
        if (i4 == 0) {
            return getLine();
        }
        return substring(i3, i5);
    }

    private boolean fillSourceBuffer() throws IOException {
        if (this.sourceString != null) {
            Kit.codeBug();
        }
        if (this.sourceEnd == this.sourceBuffer.length) {
            if (this.lineStart != 0 && !isMarkingComment()) {
                char[] cArr = this.sourceBuffer;
                int i = this.lineStart;
                System.arraycopy(cArr, i, cArr, 0, this.sourceEnd - i);
                int i2 = this.sourceEnd;
                int i3 = this.lineStart;
                this.sourceEnd = i2 - i3;
                this.sourceCursor -= i3;
                this.lineStart = 0;
            } else {
                char[] cArr2 = this.sourceBuffer;
                char[] cArr3 = new char[cArr2.length * 2];
                System.arraycopy(cArr2, 0, cArr3, 0, this.sourceEnd);
                this.sourceBuffer = cArr3;
            }
        }
        Reader reader = this.sourceReader;
        char[] cArr4 = this.sourceBuffer;
        int i4 = this.sourceEnd;
        int i5 = reader.read(cArr4, i4, cArr4.length - i4);
        if (i5 < 0) {
            return false;
        }
        this.sourceEnd += i5;
        return true;
    }

    public int getCursor() {
        return this.cursor;
    }

    public int getTokenBeg() {
        return this.tokenBeg;
    }

    public int getTokenEnd() {
        return this.tokenEnd;
    }

    public int getTokenLength() {
        return this.tokenEnd - this.tokenBeg;
    }

    public Token.CommentType getCommentType() {
        return this.commentType;
    }

    private void markCommentStart() {
        markCommentStart("");
    }

    private void markCommentStart(String str) {
        if (!this.parser.compilerEnv.isRecordingComments() || this.sourceReader == null) {
            return;
        }
        this.commentPrefix = str;
        this.commentCursor = this.sourceCursor - 1;
    }

    private boolean isMarkingComment() {
        return this.commentCursor != -1;
    }

    final String getAndResetCurrentComment() {
        if (this.sourceString != null) {
            if (isMarkingComment()) {
                Kit.codeBug();
            }
            return this.sourceString.substring(this.tokenBeg, this.tokenEnd);
        }
        if (!isMarkingComment()) {
            Kit.codeBug();
        }
        StringBuilder sb = new StringBuilder(this.commentPrefix);
        sb.append(this.sourceBuffer, this.commentCursor, getTokenLength() - this.commentPrefix.length());
        this.commentCursor = -1;
        return sb.toString();
    }

    private static String convertLastCharToHex(String str) {
        int length = str.length() - 1;
        StringBuilder sb = new StringBuilder(str.substring(0, length));
        sb.append("\\u");
        String hexString = Integer.toHexString(str.charAt(length));
        for (int i = 0; i < 4 - hexString.length(); i++) {
            sb.append(ExternalAnnotationProvider.NULLABLE);
        }
        sb.append(hexString);
        return sb.toString();
    }
}
