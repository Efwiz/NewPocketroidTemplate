package io.github.rosemoe.sora.langs.java;

import io.github.rosemoe.sora.util.MyCharacter;
import io.github.rosemoe.sora.util.TrieTree;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes11.dex */
public class JavaTextTokenizer {
    private static TrieTree<Tokens> keywords;
    protected static String[] sKeywords;
    protected int bufferLen;
    private int column;
    private Tokens currToken;
    private int index;
    private boolean lcCal;
    protected int length;
    private int line;
    protected int offset;
    private CharSequence source;

    protected static boolean isDigit(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

    protected static boolean isPrimeDigit(char c) {
        return c >= '0' && c <= '9';
    }

    protected static boolean isWhitespace(char c) {
        return c == '\t' || c == ' ' || c == '\f' || c == '\n' || c == '\r';
    }

    protected Tokens scanOperatorTwo(Tokens tokens) {
        return tokens;
    }

    static {
        doStaticInit();
    }

    public static TrieTree<Tokens> getTree() {
        return keywords;
    }

    public JavaTextTokenizer(CharSequence charSequence) {
        if (charSequence == null) {
            throw new IllegalArgumentException("src can not be null");
        }
        this.source = charSequence;
        init();
    }

    private void init() {
        this.line = 0;
        this.column = 0;
        this.length = 0;
        this.index = 0;
        this.currToken = Tokens.WHITESPACE;
        this.lcCal = false;
        this.bufferLen = this.source.length();
    }

    public void setCalculateLineColumn(boolean z) {
        this.lcCal = z;
    }

    public void pushBack(int i) {
        if (i > getTokenLength()) {
            throw new IllegalArgumentException("pushBack length too large");
        }
        this.length -= i;
    }

    private boolean isIdentifierPart(char c) {
        return MyCharacter.isJavaIdentifierPart(c);
    }

    private boolean isIdentifierStart(char c) {
        return MyCharacter.isJavaIdentifierStart(c);
    }

    public CharSequence getTokenText() {
        CharSequence charSequence = this.source;
        int i = this.offset;
        return charSequence.subSequence(i, this.length + i);
    }

    public int getTokenLength() {
        return this.length;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public int getIndex() {
        return this.index;
    }

    public Tokens getToken() {
        return this.currToken;
    }

    private char charAt(int i) {
        return this.source.charAt(i);
    }

    private char charAt() {
        return this.source.charAt(this.offset + this.length);
    }

    public Tokens nextToken() {
        Tokens tokensNextTokenInternal = nextTokenInternal();
        this.currToken = tokensNextTokenInternal;
        return tokensNextTokenInternal;
    }

    private Tokens nextTokenInternal() {
        if (this.lcCal) {
            boolean z = false;
            for (int i = this.offset; i < this.offset + this.length; i++) {
                char cCharAt = charAt(i);
                if (cCharAt == '\r') {
                    this.line++;
                    this.column = 0;
                    z = true;
                } else {
                    if (cCharAt != '\n') {
                        this.column++;
                    } else if (!z) {
                        this.line++;
                        this.column = 0;
                    }
                    z = false;
                }
            }
        }
        int i2 = this.index;
        int i3 = this.length;
        this.index = i2 + i3;
        int i4 = this.offset + i3;
        this.offset = i4;
        if (i4 >= this.bufferLen) {
            return Tokens.EOF;
        }
        char cCharAt2 = this.source.charAt(i4);
        this.length = 1;
        if (cCharAt2 == '\n') {
            return Tokens.NEWLINE;
        }
        if (cCharAt2 == '\r') {
            scanNewline();
            return Tokens.NEWLINE;
        }
        if (isWhitespace(cCharAt2)) {
            while (true) {
                int i5 = this.offset;
                int i6 = this.length;
                if (i5 + i6 >= this.bufferLen) {
                    break;
                }
                char cCharAt3 = charAt(i5 + i6);
                if (!isWhitespace(cCharAt3) || cCharAt3 == '\r' || cCharAt3 == '\n') {
                    break;
                }
                this.length++;
            }
            return Tokens.WHITESPACE;
        }
        if (isIdentifierStart(cCharAt2)) {
            return scanIdentifier(cCharAt2);
        }
        if (isPrimeDigit(cCharAt2)) {
            return scanNumber();
        }
        if (cCharAt2 == ';') {
            return Tokens.SEMICOLON;
        }
        if (cCharAt2 == '(') {
            return Tokens.LPAREN;
        }
        if (cCharAt2 == ')') {
            return Tokens.RPAREN;
        }
        if (cCharAt2 == ':') {
            return Tokens.COLON;
        }
        if (cCharAt2 == '<') {
            return scanLT();
        }
        if (cCharAt2 == '>') {
            return scanGT();
        }
        if (cCharAt2 == '!') {
            return Tokens.NOT;
        }
        if (cCharAt2 == '\"') {
            scanStringLiteral();
            return Tokens.STRING;
        }
        if (cCharAt2 == '=') {
            return scanOperatorTwo(Tokens.EQ);
        }
        if (cCharAt2 == '[') {
            return Tokens.LBRACK;
        }
        if (cCharAt2 == '?') {
            return Tokens.QUESTION;
        }
        if (cCharAt2 == '@') {
            return Tokens.AT;
        }
        if (cCharAt2 == ']') {
            return Tokens.RBRACK;
        }
        if (cCharAt2 == '^') {
            return scanOperatorTwo(Tokens.XOR);
        }
        switch (cCharAt2) {
            case '%':
                return scanOperatorTwo(Tokens.MOD);
            case '&':
                return scanOperatorTwo(Tokens.AND);
            case '\'':
                scanCharLiteral();
                return Tokens.CHARACTER_LITERAL;
            default:
                switch (cCharAt2) {
                    case '*':
                        return scanOperatorTwo(Tokens.MULT);
                    case '+':
                        return scanOperatorTwo(Tokens.PLUS);
                    case ',':
                        return Tokens.COMMA;
                    case '-':
                        return scanOperatorTwo(Tokens.MINUS);
                    case '.':
                        return Tokens.DOT;
                    case '/':
                        return scanDIV();
                    default:
                        switch (cCharAt2) {
                            case '{':
                                return Tokens.LBRACE;
                            case '|':
                                return scanOperatorTwo(Tokens.OR);
                            case '}':
                                return Tokens.RBRACE;
                            case '~':
                                return Tokens.COMP;
                            default:
                                return Tokens.UNKNOWN;
                        }
                }
        }
    }

    protected final void throwIfNeeded() {
        if (this.offset + this.length == this.bufferLen) {
            throw new RuntimeException("Token too long");
        }
    }

    protected void scanNewline() {
        int i = this.offset;
        int i2 = this.length;
        if (i + i2 >= this.bufferLen || charAt(i + i2) != '\n') {
            return;
        }
        this.length++;
    }

    protected Tokens scanIdentifier(char c) {
        TrieTree.Node<Tokens> node = keywords.root.map.get(c);
        while (true) {
            int i = this.offset;
            int i2 = this.length;
            if (i + i2 >= this.bufferLen) {
                break;
            }
            char cCharAt = charAt(i + i2);
            if (!isIdentifierPart(cCharAt)) {
                break;
            }
            this.length++;
            node = node == null ? null : node.map.get(cCharAt);
        }
        return (node == null || node.token == null) ? Tokens.IDENTIFIER : node.token;
    }

    protected void scanTrans() {
        throwIfNeeded();
        char cCharAt = charAt();
        if (cCharAt == '\\' || cCharAt == 't' || cCharAt == 'f' || cCharAt == 'n' || cCharAt == 'r' || cCharAt == '0' || cCharAt == '\"' || cCharAt == '\'' || cCharAt == 'b') {
            this.length++;
            return;
        }
        if (cCharAt == 'u') {
            this.length++;
            for (int i = 0; i < 4; i++) {
                throwIfNeeded();
                if (!isDigit(charAt(this.offset + this.length))) {
                    return;
                }
                this.length++;
            }
        }
    }

    protected void scanStringLiteral() {
        char cCharAt;
        throwIfNeeded();
        while (true) {
            int i = this.offset;
            int i2 = this.length;
            if (i + i2 >= this.bufferLen || (cCharAt = charAt(i + i2)) == '\"') {
                break;
            }
            if (cCharAt == '\\') {
                this.length++;
                scanTrans();
            } else if (cCharAt == '\n') {
                return;
            } else {
                this.length++;
            }
        }
        int i3 = this.offset;
        int i4 = this.length;
        if (i3 + i4 < this.bufferLen) {
            this.length = i4 + 1;
        }
    }

    protected void scanCharLiteral() {
        char cCharAt;
        throwIfNeeded();
        while (true) {
            int i = this.offset;
            int i2 = this.length;
            if (i + i2 >= this.bufferLen || (cCharAt = charAt(i + i2)) == '\'') {
                break;
            }
            if (cCharAt == '\\') {
                this.length++;
                scanTrans();
            } else {
                if (cCharAt == '\n') {
                    return;
                }
                this.length++;
                throwIfNeeded();
            }
        }
        int i3 = this.offset;
        int i4 = this.length;
        if (i3 + i4 != this.bufferLen) {
            this.length = i4 + 1;
        }
    }

    protected Tokens scanNumber() {
        boolean z;
        int i = this.offset;
        if (this.length + i == this.bufferLen) {
            return Tokens.INTEGER_LITERAL;
        }
        if (charAt(i) == '0') {
            if (charAt() == 'x') {
                this.length++;
            }
            z = true;
        } else {
            z = false;
        }
        while (this.offset + this.length < this.bufferLen && isDigit(charAt())) {
            this.length++;
        }
        if (this.offset + this.length == this.bufferLen) {
            return Tokens.INTEGER_LITERAL;
        }
        char cCharAt = charAt();
        if (cCharAt != '.') {
            if (cCharAt == 'l' || cCharAt == 'L') {
                this.length++;
                return Tokens.INTEGER_LITERAL;
            }
            if (cCharAt == 'F' || cCharAt == 'f' || cCharAt == 'D' || cCharAt == 'd') {
                this.length++;
                return Tokens.FLOATING_POINT_LITERAL;
            }
            return Tokens.INTEGER_LITERAL;
        }
        if (z) {
            return Tokens.INTEGER_LITERAL;
        }
        int i2 = this.offset;
        int i3 = this.length;
        if (i2 + i3 + 1 == this.bufferLen) {
            return Tokens.INTEGER_LITERAL;
        }
        this.length = i3 + 1;
        throwIfNeeded();
        while (this.offset + this.length < this.bufferLen && isDigit(charAt())) {
            this.length++;
        }
        if (this.offset + this.length == this.bufferLen) {
            return Tokens.FLOATING_POINT_LITERAL;
        }
        char cCharAt2 = charAt();
        if (cCharAt2 == 'e' || cCharAt2 == 'E') {
            this.length++;
            throwIfNeeded();
            if (charAt() == '-' || charAt() == '+') {
                this.length++;
                throwIfNeeded();
            }
            while (this.offset + this.length < this.bufferLen && isPrimeDigit(charAt())) {
                this.length++;
            }
            if (this.offset + this.length == this.bufferLen) {
                return Tokens.FLOATING_POINT_LITERAL;
            }
            cCharAt2 = charAt();
        }
        if (cCharAt2 == 'f' || cCharAt2 == 'F' || cCharAt2 == 'D' || cCharAt2 == 'd') {
            this.length++;
        }
        return Tokens.FLOATING_POINT_LITERAL;
    }

    protected Tokens scanDIV() {
        if (this.offset + 1 == this.bufferLen) {
            return Tokens.DIV;
        }
        char cCharAt = charAt();
        if (cCharAt == '/') {
            this.length++;
            while (this.offset + this.length < this.bufferLen && charAt() != '\n') {
                this.length++;
            }
            return Tokens.LINE_COMMENT;
        }
        if (cCharAt == '*') {
            this.length++;
            char c = MangleConstant.Q_MARK;
            while (this.offset + this.length < this.bufferLen) {
                char cCharAt2 = charAt();
                if (cCharAt2 == '/' && c == '*') {
                    this.length++;
                    return Tokens.LONG_COMMENT_COMPLETE;
                }
                this.length++;
                c = cCharAt2;
            }
            return Tokens.LONG_COMMENT_INCOMPLETE;
        }
        return Tokens.DIV;
    }

    protected Tokens scanLT() {
        return Tokens.LT;
    }

    protected Tokens scanGT() {
        return Tokens.GT;
    }

    public void reset(CharSequence charSequence) {
        if (charSequence == null) {
            throw new IllegalArgumentException();
        }
        this.source = charSequence;
        this.line = 0;
        this.column = 0;
        this.length = 0;
        this.index = 0;
        this.offset = 0;
        this.currToken = Tokens.WHITESPACE;
        this.bufferLen = charSequence.length();
    }

    protected static void doStaticInit() {
        int i = 0;
        sKeywords = new String[]{"abstract", PsiKeyword.ASSERT, "boolean", "byte", PsiKeyword.CHAR, "class", PsiKeyword.DO, "double", "final", "float", PsiKeyword.FOR, PsiKeyword.IF, "int", "long", PsiKeyword.NEW, "public", "private", "protected", "package", "return", "static", "short", PsiKeyword.SUPER, PsiKeyword.SWITCH, PsiKeyword.ELSE, "volatile", "synchronized", "strictfp", PsiKeyword.GOTO, PsiKeyword.CONTINUE, PsiKeyword.BREAK, "transient", PsiKeyword.VOID, PsiKeyword.TRY, PsiKeyword.CATCH, PsiKeyword.FINALLY, PsiKeyword.WHILE, PsiKeyword.CASE, "default", PsiKeyword.CONST, PsiKeyword.ENUM, PsiKeyword.EXTENDS, PsiKeyword.IMPLEMENTS, PsiKeyword.IMPORT, PsiKeyword.INSTANCEOF, PsiKeyword.INTERFACE, "native", "this", PsiKeyword.THROW, PsiKeyword.THROWS, "true", "false", "null", PsiKeyword.VAR, "sealed", PsiKeyword.PERMITS};
        Tokens[] tokensArr = {Tokens.ABSTRACT, Tokens.ASSERT, Tokens.BOOLEAN, Tokens.BYTE, Tokens.CHAR, Tokens.CLASS, Tokens.DO, Tokens.DOUBLE, Tokens.FINAL, Tokens.FLOAT, Tokens.FOR, Tokens.IF, Tokens.INT, Tokens.LONG, Tokens.NEW, Tokens.PUBLIC, Tokens.PRIVATE, Tokens.PROTECTED, Tokens.PACKAGE, Tokens.RETURN, Tokens.STATIC, Tokens.SHORT, Tokens.SUPER, Tokens.SWITCH, Tokens.ELSE, Tokens.VOLATILE, Tokens.SYNCHRONIZED, Tokens.STRICTFP, Tokens.GOTO, Tokens.CONTINUE, Tokens.BREAK, Tokens.TRANSIENT, Tokens.VOID, Tokens.TRY, Tokens.CATCH, Tokens.FINALLY, Tokens.WHILE, Tokens.CASE, Tokens.DEFAULT, Tokens.CONST, Tokens.ENUM, Tokens.EXTENDS, Tokens.IMPLEMENTS, Tokens.IMPORT, Tokens.INSTANCEOF, Tokens.INTERFACE, Tokens.NATIVE, Tokens.THIS, Tokens.THROW, Tokens.THROWS, Tokens.TRUE, Tokens.FALSE, Tokens.NULL, Tokens.VAR, Tokens.SEALED, Tokens.PERMITS};
        keywords = new TrieTree<>();
        while (true) {
            String[] strArr = sKeywords;
            if (i >= strArr.length) {
                return;
            }
            keywords.put(strArr[i], tokensArr[i]);
            i++;
        }
    }
}
