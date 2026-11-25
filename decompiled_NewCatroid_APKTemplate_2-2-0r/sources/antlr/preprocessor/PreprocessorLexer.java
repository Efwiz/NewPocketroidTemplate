package antlr.preprocessor;

import antlr.ANTLRHashString;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.CharScanner;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.InputBuffer;
import antlr.LexerSharedInputState;
import antlr.NoViableAltForCharException;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
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
public class PreprocessorLexer extends CharScanner implements PreprocessorTokenTypes, TokenStream {
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
    public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
    public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
    public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
    public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
    public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
    public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
    public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
    public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
    public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());

    public PreprocessorLexer(InputStream inputStream) {
        this(new ByteBuffer(inputStream));
    }

    public PreprocessorLexer(Reader reader) {
        this(new CharBuffer(reader));
    }

    public PreprocessorLexer(InputBuffer inputBuffer) {
        this(new LexerSharedInputState(inputBuffer));
    }

    public PreprocessorLexer(LexerSharedInputState lexerSharedInputState) {
        super(lexerSharedInputState);
        this.caseSensitiveLiterals = true;
        setCaseSensitive(true);
        this.literals = new Hashtable();
        this.literals.put(new ANTLRHashString("public", this), new Integer(18));
        this.literals.put(new ANTLRHashString("class", this), new Integer(8));
        this.literals.put(new ANTLRHashString(PsiKeyword.THROWS, this), new Integer(23));
        this.literals.put(new ANTLRHashString(PsiKeyword.CATCH, this), new Integer(26));
        this.literals.put(new ANTLRHashString("private", this), new Integer(17));
        this.literals.put(new ANTLRHashString(PsiKeyword.EXTENDS, this), new Integer(10));
        this.literals.put(new ANTLRHashString("protected", this), new Integer(16));
        this.literals.put(new ANTLRHashString("returns", this), new Integer(21));
        this.literals.put(new ANTLRHashString("tokens", this), new Integer(4));
        this.literals.put(new ANTLRHashString(K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION, this), new Integer(25));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:50:0x00be A[Catch: CharStreamException -> 0x00ff, RecognitionException -> 0x0101, TryCatch #1 {RecognitionException -> 0x0101, blocks: (B:3:0x0004, B:27:0x0038, B:28:0x003b, B:29:0x003e, B:30:0x0041, B:32:0x0049, B:34:0x0056, B:57:0x00e7, B:60:0x00ed, B:35:0x005d, B:37:0x0063, B:38:0x006a, B:40:0x0073, B:41:0x007e, B:42:0x0093, B:43:0x0094, B:44:0x009a, B:45:0x00a0, B:46:0x00a6, B:47:0x00ac, B:48:0x00b2, B:49:0x00b8, B:50:0x00be, B:51:0x00c4, B:52:0x00ca, B:53:0x00d0, B:54:0x00d6, B:55:0x00dc, B:56:0x00e2), top: B:73:0x0004, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:56:0x00e2 A[Catch: CharStreamException -> 0x00ff, RecognitionException -> 0x0101, TryCatch #1 {RecognitionException -> 0x0101, blocks: (B:3:0x0004, B:27:0x0038, B:28:0x003b, B:29:0x003e, B:30:0x0041, B:32:0x0049, B:34:0x0056, B:57:0x00e7, B:60:0x00ed, B:35:0x005d, B:37:0x0063, B:38:0x006a, B:40:0x0073, B:41:0x007e, B:42:0x0093, B:43:0x0094, B:44:0x009a, B:45:0x00a0, B:46:0x00a6, B:47:0x00ac, B:48:0x00b2, B:49:0x00b8, B:50:0x00be, B:51:0x00c4, B:52:0x00ca, B:53:0x00d0, B:54:0x00d6, B:55:0x00dc, B:56:0x00e2), top: B:73:0x0004, outer: #0 }] */
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
                    } else if (cLA == '\'') {
                        mCHAR_LITERAL(true);
                        Token token2 = this._returnToken;
                    } else if (cLA == ')') {
                        mRPAREN(true);
                        Token token3 = this._returnToken;
                    } else if (cLA == ',') {
                        mCOMMA(true);
                        Token token4 = this._returnToken;
                    } else if (cLA == '/') {
                        mCOMMENT(true);
                        Token token5 = this._returnToken;
                    } else if (cLA == '=') {
                        mASSIGN_RHS(true);
                        Token token6 = this._returnToken;
                    } else if (cLA == '_') {
                        mID_OR_KEYWORD(true);
                        Token token7 = this._returnToken;
                    } else if (cLA == '}') {
                        mRCURLY(true);
                        Token token8 = this._returnToken;
                    } else if (cLA == ':') {
                        mRULE_BLOCK(true);
                        Token token9 = this._returnToken;
                    } else if (cLA != ';') {
                        switch (cLA) {
                            case ' ':
                                mWS(true);
                                Token token10 = this._returnToken;
                                break;
                            case '!':
                                mBANG(true);
                                Token token11 = this._returnToken;
                                break;
                            case '\"':
                                mSTRING_LITERAL(true);
                                Token token12 = this._returnToken;
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
                                        mID_OR_KEYWORD(true);
                                        Token token13 = this._returnToken;
                                        break;
                                    case '[':
                                        mARG_ACTION(true);
                                        Token token14 = this._returnToken;
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
                                                mID_OR_KEYWORD(true);
                                                Token token15 = this._returnToken;
                                                break;
                                            case '{':
                                                mACTION(true);
                                                Token token16 = this._returnToken;
                                                break;
                                            default:
                                                if (LA(1) == '(' && _tokenSet_0.member(LA(2))) {
                                                    mSUBRULE_BLOCK(true);
                                                    Token token17 = this._returnToken;
                                                } else if (LA(1) == '(') {
                                                    mLPAREN(true);
                                                    Token token18 = this._returnToken;
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
                    } else {
                        mSEMI(true);
                        Token token19 = this._returnToken;
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
        this._returnToken.setType(testLiteralsTable(this._returnToken.getType()));
        return this._returnToken;
    }

    public final void mRULE_BLOCK(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(':');
        if (_tokenSet_1.member(LA(1)) && _tokenSet_2.member(LA(2))) {
            int length2 = this.text.length();
            mWS(false);
            this.text.setLength(length2);
        } else if (!_tokenSet_2.member(LA(1))) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        mALT(false);
        char cLA = LA(1);
        if (cLA == '\t' || cLA == '\n' || cLA == '\r' || cLA == ' ') {
            int length3 = this.text.length();
            mWS(false);
            this.text.setLength(length3);
        } else if (cLA != ';' && cLA != '|') {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        while (LA(1) == '|') {
            match('|');
            if (_tokenSet_1.member(LA(1)) && _tokenSet_2.member(LA(2))) {
                int length4 = this.text.length();
                mWS(false);
                this.text.setLength(length4);
            } else if (!_tokenSet_2.member(LA(1))) {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            mALT(false);
            char cLA2 = LA(1);
            if (cLA2 == '\t' || cLA2 == '\n' || cLA2 == '\r' || cLA2 == ' ') {
                int length5 = this.text.length();
                mWS(false);
                this.text.setLength(length5);
            } else if (cLA2 != ';' && cLA2 != '|') {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
        }
        match(';');
        if (z) {
            tokenMakeToken = makeToken(22);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mWS(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        this.text.length();
        int i = 0;
        while (true) {
            if (LA(1) == ' ') {
                match(StringUtil.SPACE);
            } else if (LA(1) == '\t') {
                match('\t');
            } else if (LA(1) != '\n' && LA(1) != '\r') {
                break;
            } else {
                mNEWLINE(false);
            }
            i++;
        }
        if (i < 1) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        this._returnToken = null;
    }

    protected final void mALT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        while (_tokenSet_3.member(LA(1)) && LA(2) >= 3 && LA(2) <= 255) {
            mELEMENT(false);
        }
        if (z) {
            tokenMakeToken = makeToken(27);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mSUBRULE_BLOCK(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('(');
        if (_tokenSet_1.member(LA(1)) && _tokenSet_0.member(LA(2))) {
            mWS(false);
        } else if (!_tokenSet_0.member(LA(1))) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        mALT(false);
        while (_tokenSet_4.member(LA(1))) {
            BitSet bitSet = _tokenSet_0;
            if (!bitSet.member(LA(2))) {
                break;
            }
            char cLA = LA(1);
            if (cLA == '\t' || cLA == '\n' || cLA == '\r' || cLA == ' ') {
                mWS(false);
            } else if (cLA != '|') {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            match('|');
            if (_tokenSet_1.member(LA(1)) && bitSet.member(LA(2))) {
                mWS(false);
            } else if (!bitSet.member(LA(1))) {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            mALT(false);
        }
        char cLA2 = LA(1);
        if (cLA2 == '\t' || cLA2 == '\n' || cLA2 == '\r' || cLA2 == ' ') {
            mWS(false);
        } else if (cLA2 != ')') {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        match(')');
        if (LA(1) == '=' && LA(2) == '>') {
            match("=>");
        } else if (LA(1) == '*') {
            match('*');
        } else if (LA(1) == '+') {
            match('+');
        } else if (LA(1) == '?') {
            match(MangleConstant.Q_MARK);
        }
        if (z) {
            tokenMakeToken = makeToken(6);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mELEMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        char cLA = LA(1);
        if (cLA == '\n' || cLA == '\r') {
            mNEWLINE(false);
        } else if (cLA == '\"') {
            mSTRING_LITERAL(false);
        } else if (cLA == '/') {
            mCOMMENT(false);
        } else if (cLA == '{') {
            mACTION(false);
        } else if (cLA == '\'') {
            mCHAR_LITERAL(false);
        } else if (cLA == '(') {
            mSUBRULE_BLOCK(false);
        } else {
            BitSet bitSet = _tokenSet_5;
            if (bitSet.member(LA(1))) {
                match(bitSet);
            } else {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
        }
        if (z) {
            tokenMakeToken = makeToken(28);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mCOMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        this.text.length();
        if (LA(1) == '/' && LA(2) == '/') {
            mSL_COMMENT(false);
        } else if (LA(1) == '/' && LA(2) == '*') {
            mML_COMMENT(false);
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        this._returnToken = null;
    }

    public final void mACTION(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('{');
        while (LA(1) != '}') {
            if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 3 && LA(2) <= 255) {
                mNEWLINE(false);
            } else if (LA(1) == '{' && LA(2) >= 3 && LA(2) <= 255) {
                mACTION(false);
            } else if (LA(1) == '\'' && _tokenSet_6.member(LA(2))) {
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
            tokenMakeToken = makeToken(7);
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
            } else if (!_tokenSet_7.member(LA(1))) {
                break;
            } else {
                matchNot('\"');
            }
        }
        match('\"');
        if (z) {
            tokenMakeToken = makeToken(39);
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
        } else if (_tokenSet_8.member(LA(1))) {
            matchNot('\'');
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        match('\'');
        if (z) {
            tokenMakeToken = makeToken(38);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mNEWLINE(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        if (LA(1) == '\r' && LA(2) == '\n') {
            match('\r');
            match('\n');
            newline();
        } else if (LA(1) == '\r') {
            match('\r');
            newline();
        } else if (LA(1) == '\n') {
            match('\n');
            newline();
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(34);
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
            tokenMakeToken = makeToken(19);
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
            tokenMakeToken = makeToken(11);
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
            tokenMakeToken = makeToken(24);
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
            tokenMakeToken = makeToken(15);
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
            tokenMakeToken = makeToken(29);
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
            tokenMakeToken = makeToken(30);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mID_OR_KEYWORD(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        mID(true);
        Token token = this._returnToken;
        int type = token.getType();
        BitSet bitSet = _tokenSet_9;
        if (bitSet.member(LA(1)) && LA(2) >= 3 && LA(2) <= 255 && token.getText().equals("header")) {
            if (_tokenSet_1.member(LA(1)) && bitSet.member(LA(2))) {
                mWS(false);
            } else if (!bitSet.member(LA(1)) || LA(2) < 3 || LA(2) > 255) {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
            char cLA = LA(1);
            if (cLA != '\t' && cLA != '\n' && cLA != '\r' && cLA != ' ') {
                if (cLA == '\"') {
                    mSTRING_LITERAL(false);
                } else if (cLA != '/' && cLA != '{') {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
            }
            while (true) {
                char cLA2 = LA(1);
                if (cLA2 == '\t' || cLA2 == '\n' || cLA2 == '\r' || cLA2 == ' ') {
                    mWS(false);
                } else if (cLA2 != '/') {
                    break;
                } else {
                    mCOMMENT(false);
                }
            }
            mACTION(false);
            type = 5;
        } else {
            BitSet bitSet2 = _tokenSet_10;
            if (bitSet2.member(LA(1)) && LA(2) >= 3 && LA(2) <= 255 && token.getText().equals("tokens")) {
                while (true) {
                    char cLA3 = LA(1);
                    if (cLA3 == '\t' || cLA3 == '\n' || cLA3 == '\r' || cLA3 == ' ') {
                        mWS(false);
                    } else if (cLA3 != '/') {
                        break;
                    } else {
                        mCOMMENT(false);
                    }
                }
                mCURLY_BLOCK_SCARF(false);
                type = 12;
            } else if (bitSet2.member(LA(1)) && token.getText().equals("options")) {
                while (true) {
                    char cLA4 = LA(1);
                    if (cLA4 == '\t' || cLA4 == '\n' || cLA4 == '\r' || cLA4 == ' ') {
                        mWS(false);
                    } else if (cLA4 != '/') {
                        break;
                    } else {
                        mCOMMENT(false);
                    }
                }
                match('{');
                type = 13;
            }
        }
        if (!z || type == -1) {
            tokenMakeToken = null;
        } else {
            tokenMakeToken = makeToken(type);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mID(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        switch (LA(1)) {
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
            case '[':
            case '\\':
            case ']':
            case '^':
            case '`':
            default:
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            case '_':
                match('_');
                break;
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
        }
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
                                        int iTestLiteralsTable = testLiteralsTable(new String(this.text.getBuffer(), length, this.text.length() - length), 9);
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

    protected final void mCURLY_BLOCK_SCARF(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('{');
        while (LA(1) != '}') {
            if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 3 && LA(2) <= 255) {
                mNEWLINE(false);
            } else if (LA(1) == '\"' && LA(2) >= 3 && LA(2) <= 255) {
                mSTRING_LITERAL(false);
            } else if (LA(1) == '\'' && _tokenSet_6.member(LA(2))) {
                mCHAR_LITERAL(false);
            } else if (LA(1) == '/' && (LA(2) == '*' || LA(2) == '/')) {
                mCOMMENT(false);
            } else if (LA(1) < 3 || LA(1) > 255 || LA(2) < 3 || LA(2) > 255) {
                break;
            } else {
                matchNot((char) 65535);
            }
        }
        match('}');
        if (z) {
            tokenMakeToken = makeToken(32);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mASSIGN_RHS(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int length2 = this.text.length();
        match('=');
        this.text.setLength(length2);
        while (LA(1) != ';') {
            if (LA(1) == '\"' && LA(2) >= 3 && LA(2) <= 255) {
                mSTRING_LITERAL(false);
            } else if (LA(1) == '\'' && _tokenSet_6.member(LA(2))) {
                mCHAR_LITERAL(false);
            } else if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 3 && LA(2) <= 255) {
                mNEWLINE(false);
            } else if (LA(1) < 3 || LA(1) > 255 || LA(2) < 3 || LA(2) > 255) {
                break;
            } else {
                matchNot((char) 65535);
            }
        }
        match(';');
        if (z) {
            tokenMakeToken = makeToken(14);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mSL_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match("//");
        while (LA(1) != '\n' && LA(1) != '\r' && LA(1) >= 3 && LA(1) <= 255 && LA(2) >= 3 && LA(2) <= 255) {
            matchNot((char) 65535);
        }
        mNEWLINE(false);
        if (z) {
            tokenMakeToken = makeToken(36);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mML_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(RefSpec.WILDCARD_SUFFIX);
        while (true) {
            if (LA(1) == '*' && LA(2) == '/') {
                break;
            }
            if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 3 && LA(2) <= 255) {
                mNEWLINE(false);
            } else if (LA(1) < 3 || LA(1) > 255 || LA(2) < 3 || LA(2) > 255) {
                break;
            } else {
                matchNot((char) 65535);
            }
        }
        match("*/");
        if (z) {
            tokenMakeToken = makeToken(37);
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
                    if (LA(1) >= '0' && LA(1) <= '9' && LA(2) >= 3 && LA(2) <= 255) {
                        mDIGIT(false);
                        if (LA(1) >= '0' && LA(1) <= '9' && LA(2) >= 3 && LA(2) <= 255) {
                            mDIGIT(false);
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
                    if (LA(1) >= '0' && LA(1) <= '9' && LA(2) >= 3 && LA(2) <= 255) {
                        mDIGIT(false);
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
            tokenMakeToken = makeToken(40);
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
            tokenMakeToken = makeToken(41);
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
            tokenMakeToken = makeToken(42);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mARG_ACTION(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('[');
        while (LA(1) != ']') {
            if (LA(1) == '[' && LA(2) >= 3 && LA(2) <= 255) {
                mARG_ACTION(false);
            } else if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 3 && LA(2) <= 255) {
                mNEWLINE(false);
            } else if (LA(1) == '\'' && _tokenSet_6.member(LA(2))) {
                mCHAR_LITERAL(false);
            } else if (LA(1) == '\"' && LA(2) >= 3 && LA(2) <= 255) {
                mSTRING_LITERAL(false);
            } else if (LA(1) < 3 || LA(1) > 255 || LA(2) < 3 || LA(2) > 255) {
                break;
            } else {
                matchNot((char) 65535);
            }
        }
        match(']');
        if (z) {
            tokenMakeToken = makeToken(20);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    private static final long[] mk_tokenSet_0() {
        long[] jArr = new long[8];
        jArr[0] = -576460752303423496L;
        for (int i = 1; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_1() {
        return new long[]{4294977024L, 0, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_2() {
        long[] jArr = new long[8];
        jArr[0] = -2199023255560L;
        for (int i = 1; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_3() {
        long[] jArr = new long[8];
        jArr[0] = -576462951326679048L;
        for (int i = 1; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_4() {
        return new long[]{4294977024L, 1152921504606846976L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_5() {
        long[] jArr = new long[8];
        jArr[0] = -576605355262354440L;
        jArr[1] = -576460752303423489L;
        for (int i = 2; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_6() {
        long[] jArr = new long[8];
        jArr[0] = -549755813896L;
        for (int i = 1; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_7() {
        long[] jArr = new long[8];
        jArr[0] = -17179869192L;
        jArr[1] = -268435457;
        for (int i = 2; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_8() {
        long[] jArr = new long[8];
        jArr[0] = -549755813896L;
        jArr[1] = -268435457;
        for (int i = 2; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_9() {
        return new long[]{140758963201536L, 576460752303423488L, 0, 0, 0};
    }

    private static final long[] mk_tokenSet_10() {
        return new long[]{140741783332352L, 576460752303423488L, 0, 0, 0};
    }
}
