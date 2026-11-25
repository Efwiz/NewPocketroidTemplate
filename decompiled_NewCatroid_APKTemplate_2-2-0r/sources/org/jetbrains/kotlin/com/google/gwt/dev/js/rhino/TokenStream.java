package org.jetbrains.kotlin.com.google.gwt.dev.js.rhino;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes3.dex */
public class TokenStream {
    private static final Map<String, Integer> KEYWORDS;
    private Comment headComment;
    private LineBuffer in;
    private Comment lastComment;
    CodePosition lastPosition;
    CodePosition lastTokenPosition;
    private double number;
    private int op;
    String regExpFlags;
    CodePosition secondToLastPosition;
    private String sourceName;
    private int stringBufferTop;
    CodePosition tokenPosition;
    private int tokenno;
    public boolean treatKeywordAsIdentifier;
    private String string = "";
    private char[] stringBuffer = new char[128];
    private int pushbackToken = 0;
    int flags = 0;

    private static boolean isAlpha(int i) {
        return (i >= 97 && i <= 122) || (i >= 65 && i <= 90);
    }

    static boolean isDigit(int i) {
        return i >= 48 && i <= 57;
    }

    static int xDigitToInt(int i) {
        if (48 <= i && i <= 57) {
            return i - 48;
        }
        if (97 <= i && i <= 102) {
            return i - 87;
        }
        if (65 > i || i > 70) {
            return -1;
        }
        return i - 55;
    }

    static {
        HashMap map = new HashMap();
        KEYWORDS = map;
        map.put(PsiKeyword.BREAK, 121);
        map.put(PsiKeyword.CASE, 116);
        map.put(PsiKeyword.CONTINUE, 122);
        map.put("default", 117);
        map.put("delete", 31);
        map.put(PsiKeyword.DO, 119);
        map.put(PsiKeyword.ELSE, 114);
        map.put("export", 111);
        map.put("false", 13165);
        map.put(PsiKeyword.FOR, 120);
        map.put("function", 110);
        map.put(PsiKeyword.IF, 113);
        map.put("in", 16231);
        map.put(PsiKeyword.NEW, 30);
        map.put("null", 12653);
        map.put("return", 5);
        map.put(PsiKeyword.SWITCH, 115);
        map.put("this", 12909);
        map.put("true", 13421);
        map.put("typeof", 8297);
        map.put(PsiKeyword.VAR, 123);
        map.put(PsiKeyword.VOID, 33897);
        map.put(PsiKeyword.WHILE, 118);
        map.put("with", 124);
        map.put(PsiKeyword.CATCH, 125);
        map.put("debugger", 146);
        map.put(PsiKeyword.FINALLY, 126);
        map.put(PsiKeyword.IMPORT, 112);
        map.put(PsiKeyword.INSTANCEOF, 16487);
        map.put(PsiKeyword.THROW, 62);
        map.put(PsiKeyword.TRY, 75);
    }

    public static String tokenToName(int i) {
        switch (i) {
            case -1:
                return "error";
            case 0:
                return "eof";
            case 1:
                return ConfigConstants.CONFIG_KEY_EOL;
            case 2:
                return "popv";
            case 3:
                return "enterwith";
            case 4:
                return "leavewith";
            case 5:
                return "return";
            case 6:
                return PsiKeyword.GOTO;
            case 7:
                return "ifeq";
            case 8:
                return "ifne";
            case 9:
                return "dup";
            case 10:
                return "setname";
            case 11:
                return "bitor";
            case 12:
                return "bitxor";
            case 13:
                return "bitand";
            case 14:
                return "eq";
            case 15:
                return "ne";
            case 16:
                return "lt";
            case 17:
                return "le";
            case 18:
                return "gt";
            case 19:
                return "ge";
            case 20:
                return "lsh";
            case 21:
                return "rsh";
            case 22:
                return "ursh";
            case 23:
                return ProductAction.ACTION_ADD;
            case 24:
                return "sub";
            case 25:
                return "mul";
            case 26:
                return "div";
            case 27:
                return "mod";
            case 28:
                return "bitnot";
            case 29:
                return "neg";
            case 30:
                return PsiKeyword.NEW;
            case 31:
                return "delprop";
            case 32:
                return "typeof";
            case 33:
                return "nameinc";
            case 34:
                return "propinc";
            case 35:
                return "eleminc";
            case 36:
                return "namedec";
            case 37:
                return "propdec";
            case 38:
                return "elemdec";
            case 39:
                return "getprop";
            case 40:
                return "setprop";
            case 41:
                return "getelem";
            case 42:
                return "setelem";
            case 43:
                return "call";
            case 44:
                return "name";
            case 45:
                return "double";
            case 46:
                return "string";
            case 47:
                return "zero";
            case 48:
                return "one";
            case 49:
                return "null";
            case 50:
                return "this";
            case 51:
                return "false";
            case 52:
                return "true";
            case 53:
                return "sheq";
            case 54:
                return "shne";
            case 55:
                return "closure";
            case 56:
                return "object";
            case 57:
                return "pop";
            case 58:
                return "pos";
            case 59:
                return "varinc";
            case 60:
                return "vardec";
            case 61:
                return "bindname";
            case 62:
                return PsiKeyword.THROW;
            case 63:
                return "in";
            case 64:
                return PsiKeyword.INSTANCEOF;
            case 65:
                return "gosub";
            case 66:
                return "retsub";
            case 67:
                return "callspecial";
            case 68:
                return "getthis";
            case 69:
                return "newtemp";
            case 70:
                return "usetemp";
            case 71:
                return "getbase";
            case 72:
                return "getvar";
            case 73:
                return "setvar";
            case 74:
                return "undefined";
            case 75:
                return PsiKeyword.TRY;
            case 76:
                return "endtry";
            case 77:
                return "newscope";
            case 78:
                return "typeofname";
            case 79:
                return "enuminit";
            case 80:
                return "enumnext";
            case 81:
                return "getproto";
            case 82:
                return "getparent";
            case 83:
                return "setproto";
            case 84:
                return "setparent";
            case 85:
                return "scope";
            case 86:
                return "getscopeparent";
            case 87:
                return "thisfn";
            case 88:
                return "jthrow";
            case 89:
                return "semi";
            case 90:
                return "lb";
            case 91:
                return "rb";
            case 92:
                return "lc";
            case 93:
                return "rc";
            case 94:
                return "lp";
            case 95:
                return "gwt";
            case 96:
                return "comma";
            case 97:
                return "assign";
            case 98:
                return "hook";
            case 99:
                return "colon";
            case 100:
                return "or";
            case 101:
                return "and";
            case 102:
                return "eqop";
            case 103:
                return "relop";
            case 104:
                return "shop";
            case 105:
                return "unaryop";
            case 106:
                return "inc";
            case 107:
                return "dec";
            case 108:
                return "dot";
            case 109:
                return "primary";
            case 110:
                return "function";
            case 111:
                return "export";
            case 112:
                return PsiKeyword.IMPORT;
            case 113:
                return PsiKeyword.IF;
            case 114:
                return PsiKeyword.ELSE;
            case 115:
                return PsiKeyword.SWITCH;
            case 116:
                return PsiKeyword.CASE;
            case 117:
                return "default";
            case 118:
                return PsiKeyword.WHILE;
            case 119:
                return PsiKeyword.DO;
            case 120:
                return PsiKeyword.FOR;
            case 121:
                return PsiKeyword.BREAK;
            case 122:
                return PsiKeyword.CONTINUE;
            case 123:
                return PsiKeyword.VAR;
            case 124:
                return "with";
            case 125:
                return PsiKeyword.CATCH;
            case 126:
                return PsiKeyword.FINALLY;
            case 127:
            case 146:
            default:
                return "<unknown=" + i + ">";
            case 128:
                return "nop";
            case 129:
                return "not";
            case 130:
                return "pre";
            case 131:
                return "post";
            case 132:
                return PsiKeyword.VOID;
            case 133:
                return "block";
            case 134:
                return "arraylit";
            case 135:
                return "objlit";
            case 136:
                return CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME;
            case 137:
                return "target";
            case 138:
                return "loop";
            case 139:
                return "enumdone";
            case 140:
                return "exprstmt";
            case 141:
                return "parent";
            case 142:
                return "convert";
            case 143:
                return "jsr";
            case 144:
                return "newlocal";
            case 145:
                return "uselocal";
            case 147:
                return "script";
            case 148:
                return "integer";
        }
    }

    public String tokenToString(int i) {
        String str = tokenToName(i);
        if (i != 56) {
            if (i != 97 && i != 109) {
                if (i == 148) {
                    return "NUMBER_INT " + ((int) this.number);
                }
                switch (i) {
                    case 44:
                    case 46:
                        break;
                    case 45:
                        return "NUMBER " + this.number;
                    default:
                        switch (i) {
                            case 102:
                            case 103:
                            case 104:
                            case 105:
                                break;
                            default:
                                return str;
                        }
                        break;
                }
            }
            return str + " " + tokenToName(this.op);
        }
        return str + " `" + this.string + FormatHelper.QUOTE;
    }

    private int stringToKeyword(String str) {
        Integer num = KEYWORDS.get(str);
        if (num == null) {
            return 0;
        }
        this.op = num.intValue() >> 8;
        return num.intValue() & 255;
    }

    public TokenStream(Reader reader, String str, CodePosition codePosition) {
        this.in = new LineBuffer(reader, codePosition);
        this.sourceName = str;
        this.secondToLastPosition = codePosition;
        this.lastPosition = codePosition;
        this.tokenPosition = codePosition;
        this.lastTokenPosition = codePosition;
    }

    public boolean matchToken(int i) throws IOException {
        int token = getToken();
        if (token == i) {
            return true;
        }
        ungetToken(token);
        return false;
    }

    public void ungetToken(int i) {
        if (this.pushbackToken != 0 && i != -1) {
            throw new RuntimeException(Context.getMessage2("msg.token.replaces.pushback", tokenToString(i), tokenToString(this.pushbackToken)));
        }
        this.pushbackToken = i;
        this.lastPosition = this.secondToLastPosition;
        this.lastTokenPosition = this.tokenPosition;
        this.tokenno--;
    }

    public int peekToken() throws IOException {
        return peekTokenHelper(getToken());
    }

    private int peekTokenHelper(int i) throws IOException {
        this.pushbackToken = i;
        this.lastPosition = this.secondToLastPosition;
        this.lastTokenPosition = this.tokenPosition;
        this.tokenno--;
        return i;
    }

    public int peekTokenSameLine() throws IOException {
        this.flags |= 1;
        int iPeekToken = peekToken();
        this.flags &= -2;
        if (this.pushbackToken == 1) {
            this.pushbackToken = 0;
        }
        return iPeekToken;
    }

    public static boolean isJSSpace(int i) {
        return i == 32 || i == 9 || i == 12 || i == 11 || i == 160 || Character.getType((char) i) == 12;
    }

    private void skipLine() throws IOException {
        int i;
        do {
            i = this.in.read();
            if (i == -1) {
                break;
            }
        } while (i != 10);
        this.in.unread();
    }

    public void collectCommentsAfter() throws IOException {
        ungetToken(getToken());
    }

    public int getToken() throws IOException {
        int tokenHelper;
        this.lastTokenPosition = this.tokenPosition;
        do {
            tokenHelper = getTokenHelper();
        } while (tokenHelper == 65535);
        updatePosition();
        return tokenHelper;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0024  */
    /* JADX WARN: Failed to find 'out' block for switch in B:326:0x043f. Please report as an issue. */
    private int getTokenHelper() throws IOException {
        int i;
        boolean zIsJavaIdentifierStart;
        boolean z;
        int i2;
        double dStringToNumber;
        int i3;
        int iXDigitToInt;
        int i4;
        int i5;
        int iStringToKeyword;
        boolean z2 = true;
        this.tokenno++;
        int i6 = this.pushbackToken;
        if (i6 != 0) {
            this.pushbackToken = 0;
            return i6;
        }
        while (true) {
            i = this.in.read();
            if (i == 10) {
                int i7 = this.flags;
                this.flags = i7 & (-33);
                if ((i7 & 1) != 0) {
                    break;
                }
                if (isJSSpace(i) && i != 10) {
                    break;
                }
            } else if (isJSSpace(i)) {
            }
        }
        this.tokenPosition = new CodePosition(this.in.getLineno(), this.in.getColumnno() - 1);
        if (i == -1) {
            return 0;
        }
        if (i != 45 && i != 10) {
            this.flags |= 32;
        }
        int i8 = 117;
        if (i == 92) {
            i = this.in.read();
            if (i == 117) {
                this.stringBufferTop = 0;
                zIsJavaIdentifierStart = true;
                z = true;
            } else {
                this.in.unread();
                zIsJavaIdentifierStart = false;
                z = false;
                i = 92;
            }
        } else {
            zIsJavaIdentifierStart = Character.isJavaIdentifierStart((char) i);
            if (zIsJavaIdentifierStart) {
                this.stringBufferTop = 0;
                addToString(i);
            }
            if (i == 64) {
                this.stringBufferTop = 0;
                addToString(i);
                return jsniMatchReference();
            }
            z = false;
        }
        if (zIsJavaIdentifierStart) {
            boolean z3 = z;
            while (true) {
                if (z) {
                    int iXDigitToInt2 = 0;
                    for (int i9 = 0; i9 != 4; i9++) {
                        iXDigitToInt2 = (iXDigitToInt2 << 4) | xDigitToInt(this.in.read());
                        if (iXDigitToInt2 < 0) {
                            break;
                        }
                    }
                    if (iXDigitToInt2 < 0) {
                        reportTokenError("msg.invalid.escape", null);
                        return -1;
                    }
                    addToString(iXDigitToInt2);
                    z = false;
                } else {
                    int i10 = this.in.read();
                    if (i10 == 92) {
                        if (this.in.read() != 117) {
                            reportTokenError("msg.illegal.character", null);
                            return -1;
                        }
                        z3 = true;
                        z = true;
                    } else if (Character.isJavaIdentifierPart((char) i10)) {
                        addToString(i10);
                    } else {
                        this.in.unread();
                        String stringFromBuffer = getStringFromBuffer();
                        if (!z3 && !this.treatKeywordAsIdentifier && (iStringToKeyword = stringToKeyword(stringFromBuffer)) != 0) {
                            return iStringToKeyword;
                        }
                        this.string = stringFromBuffer;
                        return 44;
                    }
                }
            }
        } else {
            if (isDigit(i) || (i == 46 && isDigit(this.in.peek()))) {
                this.stringBufferTop = 0;
                if (i != 48) {
                    i2 = 10;
                } else {
                    i = this.in.read();
                    if (i == 120 || i == 88) {
                        i = this.in.read();
                        i2 = 16;
                    } else if (isDigit(i)) {
                        i2 = 8;
                    } else {
                        addToString(48);
                        i2 = 10;
                    }
                }
                if (i2 == 16) {
                    while (xDigitToInt(i) >= 0) {
                        addToString(i);
                        i = this.in.read();
                    }
                } else {
                    while (48 <= i && i <= 57) {
                        if (i2 == 8 && i >= 56) {
                            reportTokenWarning("msg.bad.octal.literal", new Object[]{i == 56 ? "8" : "9"});
                            i2 = 10;
                        }
                        addToString(i);
                        i = this.in.read();
                    }
                }
                if (i2 == 10 && (i == 46 || i == 101 || i == 69)) {
                    if (i == 46) {
                        do {
                            addToString(i);
                            i = this.in.read();
                        } while (isDigit(i));
                    }
                    if (i == 101 || i == 69) {
                        addToString(i);
                        int i11 = this.in.read();
                        if (i11 == 43 || i11 == 45) {
                            addToString(i11);
                            i11 = this.in.read();
                        }
                        if (!isDigit(i11)) {
                            reportTokenError("msg.missing.exponent", null);
                            return -1;
                        }
                        do {
                            addToString(i11);
                            i11 = this.in.read();
                        } while (isDigit(i11));
                    }
                    z2 = false;
                }
                this.in.unread();
                String stringFromBuffer2 = getStringFromBuffer();
                if (i2 == 10 && !z2) {
                    try {
                        dStringToNumber = Double.valueOf(stringFromBuffer2).doubleValue();
                    } catch (NumberFormatException e) {
                        reportTokenError("msg.caught.nfe", new Object[]{e.getMessage()});
                        return -1;
                    }
                } else {
                    dStringToNumber = ScriptRuntime.stringToNumber(stringFromBuffer2, 0, i2);
                }
                this.number = dStringToNumber;
                return z2 ? 148 : 45;
            }
            if (i == 34 || i == 39) {
                this.stringBufferTop = 0;
                int i12 = this.in.read();
                while (i12 != i) {
                    if (i12 == 10 || i12 == -1) {
                        this.in.unread();
                        reportTokenError("msg.unterminated.string.lit", null);
                        return -1;
                    }
                    if (i12 == 92) {
                        i12 = this.in.read();
                        if (i12 == 10) {
                            i12 = this.in.read();
                        } else {
                            if (i12 == 98) {
                                i12 = 8;
                            } else if (i12 == 102) {
                                i12 = 12;
                            } else if (i12 == 110) {
                                i12 = 10;
                            } else if (i12 == 114) {
                                i12 = 13;
                            } else if (i12 != 120) {
                                switch (i12) {
                                    case 116:
                                        i12 = 9;
                                        break;
                                    case 117:
                                        int i13 = this.stringBufferTop;
                                        addToString(i8);
                                        iXDigitToInt = 0;
                                        int i14 = 0;
                                        while (true) {
                                            if (i14 != 4) {
                                                i3 = this.in.read();
                                                iXDigitToInt = (iXDigitToInt << 4) | xDigitToInt(i3);
                                                if (iXDigitToInt < 0) {
                                                    i12 = i3;
                                                } else {
                                                    addToString(i3);
                                                    i14++;
                                                }
                                            } else {
                                                this.stringBufferTop = i13;
                                                i12 = iXDigitToInt;
                                            }
                                            break;
                                        }
                                        break;
                                    case 118:
                                        i12 = 11;
                                        break;
                                    default:
                                        if (48 <= i12 && i12 < 56) {
                                            i12 -= 48;
                                            int i15 = this.in.read();
                                            if (48 <= i15 && i15 < 56) {
                                                i12 = ((i12 * 8) + i15) - 48;
                                                int i16 = this.in.read();
                                                if (48 <= i16 && i16 < 56 && i12 <= 31) {
                                                    i12 = ((i12 * 8) + i16) - 48;
                                                    this.in.read();
                                                }
                                            }
                                            this.in.unread();
                                        }
                                        break;
                                }
                            } else {
                                i12 = this.in.read();
                                int iXDigitToInt3 = xDigitToInt(i12);
                                if (iXDigitToInt3 < 0) {
                                    addToString(120);
                                } else {
                                    i3 = this.in.read();
                                    iXDigitToInt = (iXDigitToInt3 << 4) | xDigitToInt(i3);
                                    if (iXDigitToInt < 0) {
                                        addToString(120);
                                        addToString(i12);
                                        i12 = i3;
                                    }
                                    i12 = iXDigitToInt;
                                }
                            }
                            addToString(i12);
                            i12 = this.in.read();
                        }
                    } else {
                        addToString(i12);
                        i12 = this.in.read();
                    }
                    i8 = 117;
                }
                this.string = getStringFromBuffer();
                return 46;
            }
            if (i == 10) {
                return 1;
            }
            if (i == 33) {
                if (this.in.match(61)) {
                    if (this.in.match(61)) {
                        this.op = 54;
                        return 102;
                    }
                    this.op = 15;
                    return 102;
                }
                this.op = 129;
                return 105;
            }
            if (i == 91) {
                return 90;
            }
            if (i == 37) {
                this.op = 27;
                return this.in.match(61) ? 97 : 27;
            }
            if (i == 38) {
                if (this.in.match(38)) {
                    return 101;
                }
                if (!this.in.match(61)) {
                    return 13;
                }
                this.op = 13;
                return 97;
            }
            if (i == 93) {
                return 91;
            }
            if (i == 94) {
                if (!this.in.match(61)) {
                    return 12;
                }
                this.op = 12;
                return 97;
            }
            switch (i) {
                case 40:
                    return 94;
                case 41:
                    return 95;
                case 42:
                    if (!this.in.match(61)) {
                        return 25;
                    }
                    this.op = 25;
                    return 97;
                case 43:
                    if (!this.in.match(61)) {
                        return this.in.match(43) ? 106 : 23;
                    }
                    this.op = 23;
                    return 97;
                case 44:
                    return 96;
                case 45:
                    if (this.in.match(61)) {
                        this.op = 24;
                        i4 = 97;
                    } else if (!this.in.match(45)) {
                        i4 = 24;
                    } else {
                        if ((this.flags & 32) == 0 && this.in.match(62)) {
                            skipLine();
                            return 65535;
                        }
                        i4 = 107;
                    }
                    this.flags |= 32;
                    return i4;
                case 46:
                    return 108;
                case 47:
                    if (this.in.match(47)) {
                        this.stringBufferTop = 0;
                        while (true) {
                            int i17 = this.in.read();
                            if (i17 != -1 && i17 != 10) {
                                addToString(i17);
                            }
                        }
                        addCommentToQueue(new Comment(getStringFromBuffer(), false));
                        return 65535;
                    }
                    if (this.in.match(42)) {
                        this.stringBufferTop = 0;
                        while (true) {
                            i5 = this.in.read();
                            if (i5 != -1 && (i5 != 42 || !this.in.match(47))) {
                                addToString(i5);
                            }
                        }
                        if (i5 == -1) {
                            reportTokenError("msg.unterminated.comment", null);
                            return -1;
                        }
                        addCommentToQueue(new Comment(getStringFromBuffer(), true));
                        return 65535;
                    }
                    if ((this.flags & 16) != 0) {
                        this.stringBufferTop = 0;
                        while (true) {
                            int i18 = this.in.read();
                            if (i18 != 47) {
                                if (i18 == 10 || i18 == -1) {
                                    this.in.unread();
                                    reportTokenError("msg.unterminated.re.lit", null);
                                    return -1;
                                }
                                if (i18 == 92) {
                                    addToString(i18);
                                    i18 = this.in.read();
                                }
                                addToString(i18);
                            } else {
                                int i19 = this.stringBufferTop;
                                while (true) {
                                    if (this.in.match(103)) {
                                        addToString(103);
                                    } else if (this.in.match(105)) {
                                        addToString(105);
                                    } else if (this.in.match(109)) {
                                        addToString(109);
                                    } else {
                                        if (isAlpha(this.in.peek())) {
                                            reportTokenError("msg.invalid.re.flag", null);
                                            return -1;
                                        }
                                        this.string = new String(this.stringBuffer, 0, i19);
                                        this.regExpFlags = new String(this.stringBuffer, i19, this.stringBufferTop - i19);
                                        return 56;
                                    }
                                }
                            }
                        }
                    } else {
                        if (!this.in.match(61)) {
                            return 26;
                        }
                        this.op = 26;
                        return 97;
                    }
                    break;
                default:
                    switch (i) {
                        case 58:
                            return 99;
                        case 59:
                            return 89;
                        case 60:
                            if (this.in.match(33)) {
                                if (this.in.match(45)) {
                                    if (this.in.match(45)) {
                                        skipLine();
                                        return 65535;
                                    }
                                    this.in.unread();
                                }
                                this.in.unread();
                            }
                            if (this.in.match(60)) {
                                if (this.in.match(61)) {
                                    this.op = 20;
                                    return 97;
                                }
                                this.op = 20;
                                return 104;
                            }
                            if (this.in.match(61)) {
                                this.op = 17;
                                return 103;
                            }
                            this.op = 16;
                            return 103;
                        case 61:
                            if (this.in.match(61)) {
                                if (this.in.match(61)) {
                                    this.op = 53;
                                    return 102;
                                }
                                this.op = 14;
                                return 102;
                            }
                            this.op = 128;
                            return 97;
                        case 62:
                            if (this.in.match(62)) {
                                if (this.in.match(62)) {
                                    if (this.in.match(61)) {
                                        this.op = 22;
                                        return 97;
                                    }
                                    this.op = 22;
                                    return 104;
                                }
                                if (this.in.match(61)) {
                                    this.op = 21;
                                    return 97;
                                }
                                this.op = 21;
                                return 104;
                            }
                            if (this.in.match(61)) {
                                this.op = 19;
                                return 103;
                            }
                            this.op = 18;
                            return 103;
                        case 63:
                            return 98;
                        default:
                            switch (i) {
                                case 123:
                                    return 92;
                                case 124:
                                    if (this.in.match(124)) {
                                        return 100;
                                    }
                                    if (!this.in.match(61)) {
                                        return 11;
                                    }
                                    this.op = 11;
                                    return 97;
                                case 125:
                                    return 93;
                                case 126:
                                    this.op = 28;
                                    return 105;
                                default:
                                    reportTokenError("msg.illegal.character", null);
                                    return -1;
                            }
                    }
            }
        }
    }

    private void skipWhitespace() throws IOException {
        while (true) {
            int i = this.in.read();
            if (!isJSSpace(i) && i != 10) {
                this.in.unread();
                return;
            }
        }
    }

    private int jsniMatchReference() throws IOException {
        if (!jsniMatchQualifiedTypeName('.', ':')) {
            return -1;
        }
        int i = this.in.read();
        if (i != 58) {
            this.in.unread();
            reportTokenError("msg.jsni.expected.char", new String[]{":"});
            return -1;
        }
        addToString(i);
        skipWhitespace();
        if (!jsniMatchMethodSignatureOrFieldName()) {
            return -1;
        }
        this.string = new String(this.stringBuffer, 0, this.stringBufferTop);
        return 44;
    }

    private boolean jsniMatchParamListSignature() throws IOException {
        skipWhitespace();
        if (this.in.peek() == 42) {
            addToString(this.in.read());
            if (this.in.peek() != 41) {
                reportTokenError("msg.jsni.expected.char", new String[]{Constant.AFTER_QUTO});
            }
            addToString(this.in.read());
            return true;
        }
        do {
            skipWhitespace();
            int i = this.in.read();
            if (i == 41) {
                addToString(i);
                return true;
            }
            this.in.unread();
        } while (jsniMatchParamTypeSignature());
        return false;
    }

    private boolean jsniMatchParamTypeSignature() throws IOException {
        int i = this.in.read();
        if (i != 70) {
            if (i == 76) {
                addToString(i);
                return jsniMatchQualifiedTypeName('/', ';');
            }
            if (i != 83 && i != 73 && i != 74 && i != 90) {
                if (i == 91) {
                    addToString(i);
                    return jsniMatchParamArrayTypeSignature();
                }
                switch (i) {
                    case 66:
                    case 67:
                    case 68:
                        break;
                    default:
                        this.in.unread();
                        reportTokenError("msg.jsni.expected.param.type", null);
                        return false;
                }
            }
        }
        addToString(i);
        return true;
    }

    private boolean jsniMatchParamArrayTypeSignature() throws IOException {
        return jsniMatchParamTypeSignature();
    }

    private boolean jsniMatchMethodSignatureOrFieldName() throws IOException {
        int i;
        int i2 = this.in.read();
        if (!Character.isJavaIdentifierStart((char) i2)) {
            this.in.unread();
            reportTokenError("msg.jsni.expected.identifier", null);
            return false;
        }
        addToString(i2);
        while (true) {
            i = this.in.read();
            if (!Character.isJavaIdentifierPart((char) i)) {
                break;
            }
            addToString(i);
        }
        if (i == 40) {
            addToString(i);
            return jsniMatchParamListSignature();
        }
        this.in.unread();
        return true;
    }

    private boolean jsniMatchQualifiedTypeName(char c, char c2) throws IOException {
        int i;
        int i2 = this.in.read();
        if (!Character.isJavaIdentifierStart((char) i2)) {
            this.in.unread();
            reportTokenError("msg.jsni.expected.identifier", null);
            return false;
        }
        addToString(i2);
        while (true) {
            i = this.in.read();
            if (!Character.isJavaIdentifierPart((char) i)) {
                break;
            }
            addToString(i);
        }
        while (i == 91 && 93 == this.in.peek()) {
            addToString(91);
            addToString(this.in.read());
            i = this.in.read();
        }
        if (i == c) {
            addToString(i);
            return jsniMatchQualifiedTypeName(c, c2);
        }
        if (i == c2) {
            addToString(i);
            return true;
        }
        this.in.unread();
        return true;
    }

    private String getStringFromBuffer() {
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
        char[] cArr3 = this.stringBuffer;
        int i3 = this.stringBufferTop;
        this.stringBufferTop = i3 + 1;
        cArr3[i3] = (char) i;
    }

    public void reportSyntaxError(String str, Object[] objArr) {
        Context.reportError(Context.getMessage(str, objArr), this.secondToLastPosition, this.lastPosition);
    }

    private void reportTokenError(String str, Object[] objArr) {
        Context.reportError(Context.getMessage(str, objArr), this.lastPosition, new CodePosition(getLineno(), getOffset()));
    }

    private void reportTokenWarning(String str, Object[] objArr) {
        Context.reportWarning(Context.getMessage(str, objArr), this.lastPosition, new CodePosition(getLineno(), getOffset()));
    }

    private void updatePosition() {
        CodePosition codePosition = new CodePosition(getLineno(), getOffset());
        if (codePosition.compareTo(this.lastPosition) > 0) {
            this.secondToLastPosition = this.lastPosition;
            this.lastPosition = codePosition;
        }
    }

    private void addCommentToQueue(Comment comment) {
        if (this.headComment == null) {
            this.headComment = comment;
            this.lastComment = comment;
        } else {
            this.lastComment.setNext(comment);
            this.lastComment = comment;
        }
    }

    public int getLineno() {
        return this.in.getLineno();
    }

    public int getOp() {
        return this.op;
    }

    public String getString() {
        return this.string;
    }

    public double getNumber() {
        return this.number;
    }

    public int getOffset() {
        return this.in.getOffset();
    }

    public int getTokenno() {
        return this.tokenno;
    }

    public Comment getHeadComment() {
        return this.headComment;
    }

    public void releaseComments() {
        this.headComment = null;
        this.lastComment = null;
    }
}
