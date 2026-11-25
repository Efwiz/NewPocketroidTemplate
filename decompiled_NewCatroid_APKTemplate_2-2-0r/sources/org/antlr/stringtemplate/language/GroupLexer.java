package org.antlr.stringtemplate.language;

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
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes12.dex */
public class GroupLexer extends CharScanner implements GroupParserTokenTypes, TokenStream {
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
    public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());

    public GroupLexer(InputStream inputStream) {
        this(new ByteBuffer(inputStream));
    }

    public GroupLexer(Reader reader) {
        this(new CharBuffer(reader));
    }

    public GroupLexer(InputBuffer inputBuffer) {
        this(new LexerSharedInputState(inputBuffer));
    }

    public GroupLexer(LexerSharedInputState lexerSharedInputState) {
        super(lexerSharedInputState);
        this.caseSensitiveLiterals = true;
        setCaseSensitive(true);
        this.literals = new Hashtable();
        this.literals.put(new ANTLRHashString("default", this), new Integer(21));
        this.literals.put(new ANTLRHashString("group", this), new Integer(4));
        this.literals.put(new ANTLRHashString(PsiKeyword.IMPLEMENTS, this), new Integer(7));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:60:0x00f8 A[Catch: CharStreamException -> 0x0129, RecognitionException -> 0x012b, TryCatch #0 {RecognitionException -> 0x012b, blocks: (B:3:0x0004, B:21:0x002c, B:22:0x002f, B:23:0x0032, B:24:0x0035, B:25:0x0038, B:27:0x0041, B:29:0x0047, B:65:0x0115, B:68:0x011b, B:30:0x004e, B:32:0x0056, B:34:0x005c, B:35:0x0063, B:37:0x0069, B:39:0x0071, B:40:0x0078, B:42:0x007e, B:43:0x0085, B:45:0x008e, B:46:0x0099, B:47:0x00ae, B:48:0x00af, B:49:0x00b6, B:50:0x00bc, B:51:0x00c2, B:52:0x00c8, B:53:0x00ce, B:54:0x00d4, B:55:0x00da, B:56:0x00e0, B:57:0x00e6, B:58:0x00ec, B:59:0x00f2, B:60:0x00f8, B:61:0x00fe, B:62:0x0104, B:63:0x010a, B:64:0x0110), top: B:81:0x0004, outer: #1 }] */
    @Override // antlr.TokenStream
    public Token nextToken() throws TokenStreamException {
        do {
            resetText();
            try {
                try {
                    char cLA = LA(1);
                    if (cLA != '\t' && cLA != '\n' && cLA != '\f' && cLA != '\r' && cLA != ' ') {
                        if (cLA != '\"') {
                            if (cLA != '.') {
                                if (cLA != ']') {
                                    if (cLA == '_') {
                                        mID(true);
                                        Token token = this._returnToken;
                                    } else {
                                        switch (cLA) {
                                            case '(':
                                                mLPAREN(true);
                                                Token token2 = this._returnToken;
                                                break;
                                            case ')':
                                                mRPAREN(true);
                                                Token token3 = this._returnToken;
                                                break;
                                            case '*':
                                                mSTAR(true);
                                                Token token4 = this._returnToken;
                                                break;
                                            case '+':
                                                mPLUS(true);
                                                Token token5 = this._returnToken;
                                                break;
                                            case ',':
                                                mCOMMA(true);
                                                Token token6 = this._returnToken;
                                                break;
                                            default:
                                                switch (cLA) {
                                                    case ';':
                                                        mSEMI(true);
                                                        Token token7 = this._returnToken;
                                                        break;
                                                    case '<':
                                                        mBIGSTRING(true);
                                                        Token token8 = this._returnToken;
                                                        break;
                                                    case '=':
                                                        mASSIGN(true);
                                                        Token token9 = this._returnToken;
                                                        break;
                                                    default:
                                                        switch (cLA) {
                                                            case '?':
                                                                mOPTIONAL(true);
                                                                Token token10 = this._returnToken;
                                                                break;
                                                            case '@':
                                                                mAT(true);
                                                                Token token11 = this._returnToken;
                                                                break;
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
                                                                mID(true);
                                                                Token token12 = this._returnToken;
                                                                break;
                                                            case '[':
                                                                mLBRACK(true);
                                                                Token token13 = this._returnToken;
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
                                                                        mID(true);
                                                                        Token token14 = this._returnToken;
                                                                        break;
                                                                    case '{':
                                                                        mANONYMOUS_TEMPLATE(true);
                                                                        Token token15 = this._returnToken;
                                                                        break;
                                                                    default:
                                                                        if (LA(1) == ':' && LA(2) == ':') {
                                                                            mDEFINED_TO_BE(true);
                                                                            Token token16 = this._returnToken;
                                                                        } else if (LA(1) == '/' && LA(2) == '/') {
                                                                            mSL_COMMENT(true);
                                                                            Token token17 = this._returnToken;
                                                                        } else if (LA(1) == '/' && LA(2) == '*') {
                                                                            mML_COMMENT(true);
                                                                            Token token18 = this._returnToken;
                                                                        } else if (LA(1) == ':') {
                                                                            mCOLON(true);
                                                                            Token token19 = this._returnToken;
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
                                    }
                                } else {
                                    mRBRACK(true);
                                    Token token20 = this._returnToken;
                                }
                            } else {
                                mDOT(true);
                                Token token21 = this._returnToken;
                            }
                        } else {
                            mSTRING(true);
                            Token token22 = this._returnToken;
                        }
                    } else {
                        mWS(true);
                        Token token23 = this._returnToken;
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

    public final void mID(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
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
            if (cLA == '-') {
                match('-');
            } else if (cLA != '_') {
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
                                        int iTestLiteralsTable = testLiteralsTable(5);
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
            } else {
                match('_');
            }
        }
    }

    public final void mSTRING(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int length2 = this.text.length();
        match('\"');
        this.text.setLength(length2);
        while (true) {
            if (LA(1) == '\\' && LA(2) == '\"') {
                int length3 = this.text.length();
                match('\\');
                this.text.setLength(length3);
                match('\"');
            } else if (LA(1) == '\\' && _tokenSet_0.member(LA(2))) {
                match('\\');
                matchNot('\"');
            } else if (!_tokenSet_1.member(LA(1))) {
                break;
            } else {
                matchNot('\"');
            }
        }
        int length4 = this.text.length();
        match('\"');
        this.text.setLength(length4);
        if (z) {
            tokenMakeToken = makeToken(15);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mBIGSTRING(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int length2 = this.text.length();
        match("<<");
        this.text.setLength(length2);
        if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 0 && LA(2) <= 65534) {
            char cLA = LA(1);
            if (cLA != '\n') {
                if (cLA == '\r') {
                    int length3 = this.text.length();
                    match('\r');
                    this.text.setLength(length3);
                } else {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
            }
            int length4 = this.text.length();
            match('\n');
            this.text.setLength(length4);
            newline();
        } else if (LA(1) < 0 || LA(1) > 65534 || LA(2) < 0 || LA(2) > 65534) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        while (true) {
            if (LA(1) == '>' && LA(2) == '>') {
                break;
            }
            if (LA(1) == '\r' && LA(2) == '\n' && LA(3) == '>' && LA(4) == '>') {
                int length5 = this.text.length();
                match('\r');
                this.text.setLength(length5);
                int length6 = this.text.length();
                match('\n');
                this.text.setLength(length6);
                newline();
            } else if (LA(1) == '\n' && LA(2) >= 0 && LA(2) <= 65534 && LA(2) == '>' && LA(3) == '>') {
                int length7 = this.text.length();
                match('\n');
                this.text.setLength(length7);
                newline();
            } else if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 0 && LA(2) <= 65534) {
                char cLA2 = LA(1);
                if (cLA2 != '\n') {
                    if (cLA2 == '\r') {
                        match('\r');
                    } else {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                }
                match('\n');
                newline();
            } else if (LA(1) == '\\' && LA(2) == '>') {
                int length8 = this.text.length();
                match('\\');
                this.text.setLength(length8);
                match('>');
            } else if (LA(1) < 0 || LA(1) > 65534 || LA(2) < 0 || LA(2) > 65534) {
                break;
            } else {
                matchNot((char) 65535);
            }
        }
        int length9 = this.text.length();
        match(">>");
        this.text.setLength(length9);
        if (z) {
            tokenMakeToken = makeToken(16);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mANONYMOUS_TEMPLATE(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int length2 = this.text.length();
        match('{');
        this.text.setLength(length2);
        while (LA(1) != '}') {
            if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 0 && LA(2) <= 65534) {
                char cLA = LA(1);
                if (cLA != '\n') {
                    if (cLA == '\r') {
                        match('\r');
                    } else {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                }
                match('\n');
                newline();
            } else if (LA(1) == '\\' && LA(2) == '}') {
                int length3 = this.text.length();
                match('\\');
                this.text.setLength(length3);
                match('}');
            } else if (LA(1) < 0 || LA(1) > 65534 || LA(2) < 0 || LA(2) > 65534) {
                break;
            } else {
                matchNot((char) 65535);
            }
        }
        int length4 = this.text.length();
        match('}');
        this.text.setLength(length4);
        if (z) {
            tokenMakeToken = makeToken(18);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mAT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('@');
        if (z) {
            tokenMakeToken = makeToken(10);
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
            tokenMakeToken = makeToken(12);
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
            tokenMakeToken = makeToken(13);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mLBRACK(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('[');
        if (z) {
            tokenMakeToken = makeToken(19);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mRBRACK(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(']');
        if (z) {
            tokenMakeToken = makeToken(20);
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
            tokenMakeToken = makeToken(8);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mDOT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('.');
        if (z) {
            tokenMakeToken = makeToken(11);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mDEFINED_TO_BE(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match("::=");
        if (z) {
            tokenMakeToken = makeToken(14);
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
            tokenMakeToken = makeToken(9);
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
            tokenMakeToken = makeToken(6);
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
            tokenMakeToken = makeToken(22);
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
            tokenMakeToken = makeToken(23);
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
            tokenMakeToken = makeToken(17);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mOPTIONAL(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match(MangleConstant.Q_MARK);
        if (z) {
            tokenMakeToken = makeToken(24);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mSL_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        this.text.length();
        match("//");
        while (true) {
            BitSet bitSet = _tokenSet_2;
            if (!bitSet.member(LA(1))) {
                break;
            } else {
                match(bitSet);
            }
        }
        if (LA(1) == '\n' || LA(1) == '\r') {
            char cLA = LA(1);
            if (cLA != '\n') {
                if (cLA == '\r') {
                    match('\r');
                } else {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
            }
            match('\n');
        }
        newline();
        this._returnToken = null;
    }

    public final void mML_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        this.text.length();
        match(RefSpec.WILDCARD_SUFFIX);
        while (true) {
            if (LA(1) == '*' && LA(2) == '/') {
                break;
            }
            if ((LA(1) == '\n' || LA(1) == '\r') && LA(2) >= 0 && LA(2) <= 65534) {
                char cLA = LA(1);
                if (cLA != '\n') {
                    if (cLA == '\r') {
                        match('\r');
                    } else {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                }
                match('\n');
                newline();
            } else if (LA(1) < 0 || LA(1) > 65534 || LA(2) < 0 || LA(2) > 65534) {
                break;
            } else {
                matchNot((char) 65535);
            }
        }
        match("*/");
        this._returnToken = null;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0043  */
    /* JADX WARN: Code duplicated, block: B:21:0x0049 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:22:0x004b  */
    /* JADX WARN: Code duplicated, block: B:29:0x004f A[SYNTHETIC] */
    public final void mWS(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        char cLA;
        this.text.length();
        int i = 0;
        while (true) {
            char cLA2 = LA(1);
            if (cLA2 == '\t') {
                match('\t');
            } else if (cLA2 == '\n') {
                cLA = LA(1);
                if (cLA != '\n') {
                    if (cLA == '\r') {
                        match('\r');
                    } else {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                }
                match('\n');
                newline();
            } else if (cLA2 == '\f') {
                match('\f');
            } else if (cLA2 == '\r') {
                cLA = LA(1);
                if (cLA != '\n') {
                    if (cLA == '\r') {
                        match('\r');
                    } else {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                }
                match('\n');
                newline();
            } else {
                if (cLA2 != ' ') {
                    if (i < 1) {
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                    }
                    this._returnToken = null;
                    return;
                }
                match(StringUtil.SPACE);
            }
            i++;
        }
    }

    private static final long[] mk_tokenSet_0() {
        long[] jArr = new long[2048];
        jArr[0] = -17179869185L;
        for (int i = 1; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_1() {
        long[] jArr = new long[2048];
        jArr[0] = -17179869185L;
        jArr[1] = -268435457;
        for (int i = 2; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }

    private static final long[] mk_tokenSet_2() {
        long[] jArr = new long[2048];
        jArr[0] = -9217;
        for (int i = 1; i <= 1022; i++) {
            jArr[i] = -1;
        }
        jArr[1023] = Long.MAX_VALUE;
        return jArr;
    }
}
