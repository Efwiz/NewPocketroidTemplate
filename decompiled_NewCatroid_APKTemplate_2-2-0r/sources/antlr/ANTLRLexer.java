package antlr;

import antlr.collections.impl.BitSet;
import io.netty.util.internal.StringUtil;
import java.io.InputStream;
import java.io.Reader;
import java.util.Hashtable;
import net.bytebuddy.asm.Advice;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.eclipse.jgit.transport.RefSpec;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes.dex */
public class ANTLRLexer extends CharScanner implements ANTLRTokenTypes, TokenStream {
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
    public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
    public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
    public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
    public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());

    public static int escapeCharValue(String str) {
        int iDigit;
        int iDigit2;
        char cCharAt;
        char cCharAt2;
        if (str.charAt(1) != '\\') {
            return 0;
        }
        char cCharAt3 = str.charAt(2);
        int i = 34;
        if (cCharAt3 != '\"') {
            i = 39;
            if (cCharAt3 != '\'') {
                if (cCharAt3 == '\\') {
                    return 92;
                }
                if (cCharAt3 == 'b') {
                    return 8;
                }
                if (cCharAt3 == 'f') {
                    return 12;
                }
                if (cCharAt3 == 'n') {
                    return 10;
                }
                if (cCharAt3 == 'r') {
                    return 13;
                }
                if (cCharAt3 == 't') {
                    return 9;
                }
                if (cCharAt3 == 'u') {
                    if (str.length() != 8) {
                        return 0;
                    }
                    iDigit = (Character.digit(str.charAt(3), 16) * 4096) + (Character.digit(str.charAt(4), 16) * 256) + (Character.digit(str.charAt(5), 16) * 16);
                    iDigit2 = Character.digit(str.charAt(6), 16);
                } else {
                    switch (cCharAt3) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                            if (str.length() > 5 && Character.isDigit(str.charAt(4))) {
                                iDigit = ((str.charAt(2) - '0') * 64) + ((str.charAt(3) - '0') * 8);
                                cCharAt2 = str.charAt(4);
                            } else if (str.length() > 4 && Character.isDigit(str.charAt(3))) {
                                iDigit = (str.charAt(2) - '0') * 8;
                                cCharAt2 = str.charAt(3);
                            } else {
                                cCharAt = str.charAt(2);
                                return cCharAt - '0';
                            }
                            iDigit2 = cCharAt2 - '0';
                            break;
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                            if (str.length() > 4 && Character.isDigit(str.charAt(3))) {
                                iDigit = (str.charAt(2) - '0') * 8;
                                cCharAt2 = str.charAt(3);
                                iDigit2 = cCharAt2 - '0';
                            } else {
                                cCharAt = str.charAt(2);
                                return cCharAt - '0';
                            }
                            break;
                        default:
                            return 0;
                    }
                }
                return iDigit + iDigit2;
            }
        }
        return i;
    }

    public static int tokenTypeForCharLiteral(String str) {
        if (str.length() > 3) {
            return escapeCharValue(str);
        }
        return str.charAt(1);
    }

    public ANTLRLexer(InputStream inputStream) {
        this(new ByteBuffer(inputStream));
    }

    public ANTLRLexer(Reader reader) {
        this(new CharBuffer(reader));
    }

    public ANTLRLexer(InputBuffer inputBuffer) {
        this(new LexerSharedInputState(inputBuffer));
    }

    public ANTLRLexer(LexerSharedInputState lexerSharedInputState) {
        super(lexerSharedInputState);
        this.caseSensitiveLiterals = true;
        setCaseSensitive(true);
        this.literals = new Hashtable();
        this.literals.put(new ANTLRHashString("public", this), new Integer(31));
        this.literals.put(new ANTLRHashString("class", this), new Integer(10));
        this.literals.put(new ANTLRHashString("header", this), new Integer(5));
        this.literals.put(new ANTLRHashString(PsiKeyword.THROWS, this), new Integer(37));
        this.literals.put(new ANTLRHashString("lexclass", this), new Integer(9));
        this.literals.put(new ANTLRHashString(PsiKeyword.CATCH, this), new Integer(40));
        this.literals.put(new ANTLRHashString("private", this), new Integer(32));
        this.literals.put(new ANTLRHashString("options", this), new Integer(51));
        this.literals.put(new ANTLRHashString(PsiKeyword.EXTENDS, this), new Integer(11));
        this.literals.put(new ANTLRHashString("protected", this), new Integer(30));
        this.literals.put(new ANTLRHashString("TreeParser", this), new Integer(13));
        this.literals.put(new ANTLRHashString("Parser", this), new Integer(29));
        this.literals.put(new ANTLRHashString("Lexer", this), new Integer(12));
        this.literals.put(new ANTLRHashString("returns", this), new Integer(35));
        this.literals.put(new ANTLRHashString("charVocabulary", this), new Integer(18));
        this.literals.put(new ANTLRHashString("tokens", this), new Integer(4));
        this.literals.put(new ANTLRHashString(K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION, this), new Integer(39));
    }

    /* JADX WARN: Code duplicated, block: B:65:0x0137 A[Catch: CharStreamException -> 0x0150, RecognitionException -> 0x0152, TryCatch #1 {RecognitionException -> 0x0152, blocks: (B:3:0x0004, B:15:0x0020, B:16:0x0023, B:17:0x0026, B:18:0x0029, B:19:0x002c, B:20:0x002f, B:22:0x0038, B:24:0x003e, B:66:0x013c, B:69:0x0142, B:25:0x0045, B:27:0x004d, B:29:0x0053, B:30:0x005a, B:32:0x0060, B:33:0x0067, B:35:0x006d, B:36:0x0074, B:38:0x007d, B:39:0x0088, B:40:0x009d, B:41:0x009e, B:42:0x00a5, B:43:0x00ac, B:44:0x00b3, B:45:0x00ba, B:46:0x00c1, B:47:0x00c8, B:48:0x00cf, B:49:0x00d6, B:50:0x00dd, B:51:0x00e3, B:52:0x00e9, B:53:0x00ef, B:54:0x00f5, B:55:0x00fb, B:56:0x0101, B:57:0x0107, B:58:0x010d, B:59:0x0113, B:60:0x0119, B:61:0x011f, B:62:0x0125, B:63:0x012b, B:64:0x0131, B:65:0x0137), top: B:82:0x0004, outer: #0 }] */
    @Override // antlr.TokenStream
    public Token nextToken() throws TokenStreamException {
        do {
            resetText();
            try {
                try {
                    char cLA = LA(1);
                    if (cLA == '\t' || cLA == '\n' || cLA == '\r') {
                        mWS(true);
                        Token token = this._returnToken;
                    } else if (cLA == '^') {
                        mCARET(true);
                        Token token2 = this._returnToken;
                    } else if (cLA == '>') {
                        mCLOSE_ELEMENT_OPTION(true);
                        Token token3 = this._returnToken;
                    } else if (cLA != '?') {
                        switch (cLA) {
                            case ' ':
                                mWS(true);
                                Token token4 = this._returnToken;
                                break;
                            case '!':
                                mBANG(true);
                                Token token5 = this._returnToken;
                                break;
                            case '\"':
                                mSTRING_LITERAL(true);
                                Token token6 = this._returnToken;
                                break;
                            case '#':
                                mTREE_BEGIN(true);
                                Token token7 = this._returnToken;
                                break;
                            default:
                                switch (cLA) {
                                    case '\'':
                                        mCHAR_LITERAL(true);
                                        Token token8 = this._returnToken;
                                        break;
                                    case '(':
                                        mLPAREN(true);
                                        Token token9 = this._returnToken;
                                        break;
                                    case ')':
                                        mRPAREN(true);
                                        Token token10 = this._returnToken;
                                        break;
                                    case '*':
                                        mSTAR(true);
                                        Token token11 = this._returnToken;
                                        break;
                                    case '+':
                                        mPLUS(true);
                                        Token token12 = this._returnToken;
                                        break;
                                    case ',':
                                        mCOMMA(true);
                                        Token token13 = this._returnToken;
                                        break;
                                    default:
                                        switch (cLA) {
                                            case '/':
                                                mCOMMENT(true);
                                                Token token14 = this._returnToken;
                                                break;
                                            case '0':
                                            case '1':
                                            case '2':
                                            case '3':
                                            case '4':
                                            case '5':
                                            case '6':
                                            case '7':
                                            case '8':
                                            case '9':
                                                mINT(true);
                                                Token token15 = this._returnToken;
                                                break;
                                            case ':':
                                                mCOLON(true);
                                                Token token16 = this._returnToken;
                                                break;
                                            case ';':
                                                mSEMI(true);
                                                Token token17 = this._returnToken;
                                                break;
                                            case '<':
                                                mOPEN_ELEMENT_OPTION(true);
                                                Token token18 = this._returnToken;
                                                break;
                                            default:
                                                switch (cLA) {
                                                    case 'A':
                                                    case 'B':
                                                    case 'C':
                                                    case 'D':
                                                    case 'E':
                                                    case 'F':
                                                    case 'G':
                                                    case 'H':
                                                    case 'I':
                                                    case 'J':
                                                    case 'K':
                                                    case 'L':
                                                    case 'M':
                                                    case 'N':
                                                    case 'O':
                                                    case 'P':
                                                    case 'Q':
                                                    case 'R':
                                                    case 'S':
                                                    case 'T':
                                                    case 'U':
                                                    case 'V':
                                                    case 'W':
                                                    case 'X':
                                                    case 'Y':
                                                    case 'Z':
                                                        mTOKEN_REF(true);
                                                        Token token19 = this._returnToken;
                                                        break;
                                                    case '[':
                                                        mARG_ACTION(true);
                                                        Token token20 = this._returnToken;
                                                        break;
                                                    default:
                                                        switch (cLA) {
                                                            case 'a':
                                                            case 'b':
                                                            case 'c':
                                                            case 'd':
                                                            case 'e':
                                                            case 'f':
                                                            case 'g':
                                                            case 'h':
                                                            case 'i':
                                                            case 'j':
                                                            case 'k':
                                                            case 'l':
                                                            case 'm':
                                                            case 'n':
                                                            case 'o':
                                                            case 'p':
                                                            case 'q':
                                                            case 'r':
                                                            case 's':
                                                            case 't':
                                                            case 'u':
                                                            case 'v':
                                                            case 'w':
                                                            case 'x':
                                                            case 'y':
                                                            case 'z':
                                                                mRULE_REF(true);
                                                                Token token21 = this._returnToken;
                                                                break;
                                                            case '{':
                                                                mACTION(true);
                                                                Token token22 = this._returnToken;
                                                                break;
                                                            case '|':
                                                                mOR(true);
                                                                Token token23 = this._returnToken;
                                                                break;
                                                            case '}':
                                                                mRCURLY(true);
                                                                Token token24 = this._returnToken;
                                                                break;
                                                            case '~':
                                                                mNOT_OP(true);
                                                                Token token25 = this._returnToken;
                                                                break;
                                                            default:
                                                                if (LA(1) == '=' && LA(2) == '>') {
                                                                    mIMPLIES(true);
                                                                    Token token26 = this._returnToken;
                                                                } else if (LA(1) == '.' && LA(2) == '.') {
                                                                    mRANGE(true);
                                                                    Token token27 = this._returnToken;
                                                                } else if (LA(1) == '=') {
                                                                    mASSIGN(true);
                                                                    Token token28 = this._returnToken;
                                                                } else if (LA(1) == '.') {
                                                                    mWILDCARD(true);
                                                                    Token token29 = this._returnToken;
                                                                } else {
                                                                    if (LA(1) != 65535) {
                                                                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                                                    }
                                                                    uponEOF();
                                                                    this._returnToken = makeToken(1);
                                                                }
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                    } else {
                        mQUESTION(true);
                        Token token30 = this._returnToken;
                    }
                } catch (RecognitionException e) {
                    throw new TokenStreamRecognitionException(e);
                }
            } catch (CharStreamException e2) {
                if (e2 instanceof CharStreamIOException) {
                    throw new TokenStreamIOException(((CharStreamIOException) e2).f54io);
                }
                throw new TokenStreamException(e2.getMessage());
            }
        } while (this._returnToken == null);
        this._returnToken.setType(this._returnToken.getType());
        return this._returnToken;
    }

    public final void mWS(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        this.text.length();
        char cLA = LA(1);
        if (cLA == '\t') {
            match('\t');
        } else if (cLA == '\n') {
            match('\n');
            newline();
        } else if (cLA == ' ') {
            match(StringUtil.SPACE);
        } else if (LA(1) == '\r' && LA(2) == '\n') {
            match('\r');
            match('\n');
            newline();
        } else if (LA(1) == '\r') {
            match('\r');
            newline();
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        this._returnToken = null;
    }

    public final void mCOMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        int type;
        Token tokenMakeToken;
        int length = this.text.length();
        if (LA(1) == '/' && LA(2) == '/') {
            mSL_COMMENT(false);
            type = 53;
        } else if (LA(1) == '/' && LA(2) == '*') {
            mML_COMMENT(true);
            type = this._returnToken.getType();
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (type != 8) {
            type = -1;
        }
        if (!z || type == -1) {
            tokenMakeToken = null;
        } else {
            tokenMakeToken = makeToken(type);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mSL_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match("//");
        while (true) {
            BitSet bitSet = _tokenSet_0;
            if (!bitSet.member(LA(1))) {
                break;
            } else {
                match(bitSet);
            }
        }
        if (LA(1) == '\r' && LA(2) == '\n') {
            match('\r');
            match('\n');
        } else if (LA(1) == '\r') {
            match('\r');
        } else if (LA(1) == '\n') {
            match('\n');
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        newline();
        if (z) {
            tokenMakeToken = makeToken(54);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mML_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        int i;
        Token tokenMakeToken;
        int length = this.text.length();
        match(RefSpec.WILDCARD_SUFFIX);
        if (LA(1) == '*' && LA(2) >= 3 && LA(2) <= 255 && LA(2) != '/') {
            match('*');
            i = 8;
        } else {
            if (LA(1) < 3 || LA(1) > 255 || LA(2) < 3 || LA(2) > 255) {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            i = 55;
        }
        while (true) {
            if (LA(1) == '*' && LA(2) == '/') {
                break;
            }
            if (LA(1) == '\r' && LA(2) == '\n') {
                match('\r');
                match('\n');
                newline();
            } else if (LA(1) == '\r' && LA(2) >= 3 && LA(2) <= 255) {
                match('\r');
                newline();
            } else {
                BitSet bitSet = _tokenSet_0;
                if (bitSet.member(LA(1)) && LA(2) >= 3 && LA(2) <= 255) {
                    match(bitSet);
                } else {
                    if (LA(1) != '\n') {
                        break;
                    }
                    match('\n');
                    newline();
                }
            }
        }
        match("*/");
        if (!z || i == -1) {
            tokenMakeToken = null;
        } else {
            tokenMakeToken = makeToken(i);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mOPEN_ELEMENT_OPTION(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('<');
        if (z) {
            tokenMakeToken = makeToken(25);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mCLOSE_ELEMENT_OPTION(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('>');
        if (z) {
            tokenMakeToken = makeToken(26);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mCOMMA(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(',');
        if (z) {
            tokenMakeToken = makeToken(38);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mQUESTION(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(MangleConstant.Q_MARK);
        if (z) {
            tokenMakeToken = makeToken(45);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mTREE_BEGIN(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match("#(");
        if (z) {
            tokenMakeToken = makeToken(44);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mLPAREN(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('(');
        if (z) {
            tokenMakeToken = makeToken(27);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mRPAREN(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(')');
        if (z) {
            tokenMakeToken = makeToken(28);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mCOLON(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(':');
        if (z) {
            tokenMakeToken = makeToken(36);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mSTAR(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('*');
        if (z) {
            tokenMakeToken = makeToken(46);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mPLUS(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('+');
        if (z) {
            tokenMakeToken = makeToken(47);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mASSIGN(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('=');
        if (z) {
            tokenMakeToken = makeToken(15);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mIMPLIES(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match("=>");
        if (z) {
            tokenMakeToken = makeToken(48);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mSEMI(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(';');
        if (z) {
            tokenMakeToken = makeToken(16);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mCARET(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(Util.C_EXCEPTION_START);
        if (z) {
            tokenMakeToken = makeToken(49);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mBANG(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('!');
        if (z) {
            tokenMakeToken = makeToken(33);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mOR(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('|');
        if (z) {
            tokenMakeToken = makeToken(21);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mWILDCARD(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('.');
        if (z) {
            tokenMakeToken = makeToken(50);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mRANGE(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match("..");
        if (z) {
            tokenMakeToken = makeToken(22);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mNOT_OP(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('~');
        if (z) {
            tokenMakeToken = makeToken(42);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mRCURLY(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('}');
        if (z) {
            tokenMakeToken = makeToken(17);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mCHAR_LITERAL(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('\'');
        if (LA(1) == '\\') {
            mESC(false);
        } else if (_tokenSet_1.member(LA(1))) {
            matchNot('\'');
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        match('\'');
        if (z) {
            tokenMakeToken = makeToken(19);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mESC(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('\\');
        char cLA = LA(1);
        if (cLA == '\"') {
            match('\"');
        } else if (cLA == '\'') {
            match('\'');
        } else if (cLA == '\\') {
            match('\\');
        } else if (cLA == 'f') {
            match('f');
        } else if (cLA == 'n') {
            match('n');
        } else if (cLA == 'r') {
            match(Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL);
        } else if (cLA == 'w') {
            match('w');
        } else if (cLA == 'a') {
            match('a');
        } else if (cLA == 'b') {
            match('b');
        } else if (cLA == 't') {
            match(Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL);
        } else if (cLA != 'u') {
            switch (cLA) {
                case '0':
                case '1':
                case '2':
                case '3':
                    matchRange(ExternalAnnotationProvider.NULLABLE, '3');
                    if (LA(1) >= '0' && LA(1) <= '7' && LA(2) >= 3 && LA(2) <= 255) {
                        matchRange(ExternalAnnotationProvider.NULLABLE, '7');
                        if (LA(1) >= '0' && LA(1) <= '7' && LA(2) >= 3 && LA(2) <= 255) {
                            matchRange(ExternalAnnotationProvider.NULLABLE, '7');
                        } else if (LA(1) < 3 || LA(1) > 255) {
                            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                        }
                    } else if (LA(1) < 3 || LA(1) > 255) {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                    break;
                case '4':
                case '5':
                case '6':
                case '7':
                    matchRange('4', '7');
                    if (LA(1) >= '0' && LA(1) <= '7' && LA(2) >= 3 && LA(2) <= 255) {
                        matchRange(ExternalAnnotationProvider.NULLABLE, '7');
                    } else if (LA(1) < 3 || LA(1) > 255) {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                    break;
                default:
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
        } else {
            match('u');
            mXDIGIT(false);
            mXDIGIT(false);
            mXDIGIT(false);
            mXDIGIT(false);
        }
        if (z) {
            tokenMakeToken = makeToken(56);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mSTRING_LITERAL(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('\"');
        while (true) {
            if (LA(1) == '\\') {
                mESC(false);
            } else if (!_tokenSet_2.member(LA(1))) {
                break;
            } else {
                matchNot('\"');
            }
        }
        match('\"');
        if (z) {
            tokenMakeToken = makeToken(6);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mXDIGIT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        char cLA = LA(1);
        switch (cLA) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                matchRange(ExternalAnnotationProvider.NULLABLE, '9');
                break;
            default:
                switch (cLA) {
                    case 'A':
                    case 'B':
                    case 'C':
                    case 'D':
                    case 'E':
                    case 'F':
                        matchRange('A', Util.C_FLOAT);
                        break;
                    default:
                        switch (cLA) {
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                matchRange('a', 'f');
                                break;
                            default:
                                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                        }
                        break;
                }
                break;
        }
        if (z) {
            tokenMakeToken = makeToken(58);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mDIGIT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        matchRange(ExternalAnnotationProvider.NULLABLE, '9');
        if (z) {
            tokenMakeToken = makeToken(57);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mINT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int i = 0;
        while (LA(1) >= '0' && LA(1) <= '9') {
            matchRange(ExternalAnnotationProvider.NULLABLE, '9');
            i++;
        }
        if (i < 1) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(20);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mARG_ACTION(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        mNESTED_ARG_ACTION(false);
        setText(StringUtils.stripFrontBack(getText(), "[", "]"));
        if (z) {
            tokenMakeToken = makeToken(34);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mNESTED_ARG_ACTION(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('[');
        while (true) {
            char cLA = LA(1);
            if (cLA == '\n') {
                match('\n');
                newline();
            } else if (cLA == '\"') {
                mSTRING_LITERAL(false);
            } else if (cLA == '\'') {
                mCHAR_LITERAL(false);
            } else if (cLA == '[') {
                mNESTED_ARG_ACTION(false);
            } else if (LA(1) == '\r' && LA(2) == '\n') {
                match('\r');
                match('\n');
                newline();
            } else if (LA(1) == '\r' && LA(2) >= 3 && LA(2) <= 255) {
                match('\r');
                newline();
            } else if (!_tokenSet_3.member(LA(1))) {
                break;
            } else {
                matchNot(']');
            }
        }
        match(']');
        if (z) {
            tokenMakeToken = makeToken(59);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mACTION(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        int i;
        int length = this.text.length();
        int line = getLine();
        int column = getColumn();
        mNESTED_ACTION(false);
        if (LA(1) == '?') {
            match(MangleConstant.Q_MARK);
            i = 43;
        } else {
            i = 7;
        }
        if (i == 7) {
            setText(StringUtils.stripFrontBack(getText(), "{", "}"));
        } else {
            setText(StringUtils.stripFrontBack(getText(), "{", "}?"));
        }
        CommonToken commonToken = new CommonToken(i, new String(this.text.getBuffer(), length, this.text.length() - length));
        commonToken.setLine(line);
        commonToken.setColumn(column);
        this._returnToken = commonToken;
    }

    protected final void mNESTED_ACTION(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('{');
        while (LA(1) != '}') {
            if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 3 && LA(2) <= 255) {
                if (LA(1) == '\r' && LA(2) == '\n') {
                    match('\r');
                    match('\n');
                    newline();
                } else if (LA(1) == '\r' && LA(2) >= 3 && LA(2) <= 255) {
                    match('\r');
                    newline();
                } else if (LA(1) == '\n') {
                    match('\n');
                    newline();
                } else {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
            } else if (LA(1) == '{' && LA(2) >= 3 && LA(2) <= 255) {
                mNESTED_ACTION(false);
            } else if (LA(1) == '\'' && _tokenSet_4.member(LA(2))) {
                mCHAR_LITERAL(false);
            } else if (LA(1) == '/' && (LA(2) == '*' || LA(2) == '/')) {
                mCOMMENT(false);
            } else if (LA(1) == '\"' && LA(2) >= 3 && LA(2) <= 255) {
                mSTRING_LITERAL(false);
            } else if (LA(1) < 3 || LA(1) > 255 || LA(2) < 3 || LA(2) > 255) {
                break;
            } else {
                matchNot((char) 65535);
            }
        }
        match('}');
        if (z) {
            tokenMakeToken = makeToken(60);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mTOKEN_REF(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        matchRange('A', 'Z');
        while (true) {
            char cLA = LA(1);
            if (cLA == '_') {
                match('_');
            } else {
                switch (cLA) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        matchRange(ExternalAnnotationProvider.NULLABLE, '9');
                        break;
                    default:
                        switch (cLA) {
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                            case 'G':
                            case 'H':
                            case 'I':
                            case 'J':
                            case 'K':
                            case 'L':
                            case 'M':
                            case 'N':
                            case 'O':
                            case 'P':
                            case 'Q':
                            case 'R':
                            case 'S':
                            case 'T':
                            case 'U':
                            case 'V':
                            case 'W':
                            case 'X':
                            case 'Y':
                            case 'Z':
                                matchRange('A', 'Z');
                                break;
                            default:
                                switch (cLA) {
                                    case 'a':
                                    case 'b':
                                    case 'c':
                                    case 'd':
                                    case 'e':
                                    case 'f':
                                    case 'g':
                                    case 'h':
                                    case 'i':
                                    case 'j':
                                    case 'k':
                                    case 'l':
                                    case 'm':
                                    case 'n':
                                    case 'o':
                                    case 'p':
                                    case 'q':
                                    case 'r':
                                    case 's':
                                    case 't':
                                    case 'u':
                                    case 'v':
                                    case 'w':
                                    case 'x':
                                    case 'y':
                                    case 'z':
                                        matchRange('a', 'z');
                                        break;
                                    default:
                                        int iTestLiteralsTable = testLiteralsTable(24);
                                        if (!z || iTestLiteralsTable == -1) {
                                            tokenMakeToken = null;
                                        } else {
                                            tokenMakeToken = makeToken(iTestLiteralsTable);
                                            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
                                        }
                                        this._returnToken = tokenMakeToken;
                                        return;
                                }
                                break;
                        }
                        break;
                }
            }
        }
    }

    public final void mRULE_REF(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int iMINTERNAL_RULE_REF = mINTERNAL_RULE_REF(false);
        if (iMINTERNAL_RULE_REF == 51) {
            mWS_LOOP(false);
            if (LA(1) == '{') {
                match('{');
                iMINTERNAL_RULE_REF = 14;
            }
        } else if (iMINTERNAL_RULE_REF == 4) {
            mWS_LOOP(false);
            if (LA(1) == '{') {
                match('{');
                iMINTERNAL_RULE_REF = 23;
            }
        }
        if (!z || iMINTERNAL_RULE_REF == -1) {
            tokenMakeToken = null;
        } else {
            tokenMakeToken = makeToken(iMINTERNAL_RULE_REF);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        }
        this._returnToken = tokenMakeToken;
    }

    protected final int mINTERNAL_RULE_REF(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        matchRange('a', 'z');
        while (true) {
            char cLA = LA(1);
            if (cLA == '_') {
                match('_');
            } else {
                switch (cLA) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        matchRange(ExternalAnnotationProvider.NULLABLE, '9');
                        break;
                    default:
                        switch (cLA) {
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                            case 'G':
                            case 'H':
                            case 'I':
                            case 'J':
                            case 'K':
                            case 'L':
                            case 'M':
                            case 'N':
                            case 'O':
                            case 'P':
                            case 'Q':
                            case 'R':
                            case 'S':
                            case 'T':
                            case 'U':
                            case 'V':
                            case 'W':
                            case 'X':
                            case 'Y':
                            case 'Z':
                                matchRange('A', 'Z');
                                break;
                            default:
                                switch (cLA) {
                                    case 'a':
                                    case 'b':
                                    case 'c':
                                    case 'd':
                                    case 'e':
                                    case 'f':
                                    case 'g':
                                    case 'h':
                                    case 'i':
                                    case 'j':
                                    case 'k':
                                    case 'l':
                                    case 'm':
                                    case 'n':
                                    case 'o':
                                    case 'p':
                                    case 'q':
                                    case 'r':
                                    case 's':
                                    case 't':
                                    case 'u':
                                    case 'v':
                                    case 'w':
                                    case 'x':
                                    case 'y':
                                    case 'z':
                                        matchRange('a', 'z');
                                        break;
                                    default:
                                        int iTestLiteralsTable = testLiteralsTable(41);
                                        if (z) {
                                            tokenMakeToken = makeToken(62);
                                            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
                                        } else {
                                            tokenMakeToken = null;
                                        }
                                        this._returnToken = tokenMakeToken;
                                        return iTestLiteralsTable;
                                }
                                break;
                        }
                        break;
                }
            }
        }
    }

    protected final void mWS_LOOP(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        while (true) {
            char cLA = LA(1);
            if (cLA == '\t' || cLA == '\n' || cLA == '\r' || cLA == ' ') {
                mWS(false);
            } else if (cLA != '/') {
                break;
            } else {
                mCOMMENT(false);
            }
        }
        if (z) {
            tokenMakeToken = makeToken(61);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mWS_OPT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        if (_tokenSet_5.member(LA(1))) {
            mWS(false);
        }
        if (z) {
            tokenMakeToken = makeToken(63);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    private static final long[] mk_tokenSet_0() {
        long[] jArr = new long[8];
        jArr[0] = -9224;
        for (int i = 1; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_1() {
        long[] jArr = new long[8];
        jArr[0] = -549755813896L;
        jArr[1] = -268435457;
        for (int i = 2; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_2() {
        long[] jArr = new long[8];
        jArr[0] = -17179869192L;
        jArr[1] = -268435457;
        for (int i = 2; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_3() {
        long[] jArr = new long[8];
        jArr[0] = -566935692296L;
        jArr[1] = -671088641;
        for (int i = 2; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_4() {
        long[] jArr = new long[8];
        jArr[0] = -549755813896L;
        for (int i = 1; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_5() {
        return new long[]{4294977024L, 0, 0, 0, 0};
    }
}
