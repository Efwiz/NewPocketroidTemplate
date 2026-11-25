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

/* JADX INFO: loaded from: classes.dex */
public class ANTLRTokdefLexer extends CharScanner implements ANTLRTokdefParserTokenTypes, TokenStream {
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
    public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
    public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());

    public ANTLRTokdefLexer(InputStream inputStream) {
        this(new ByteBuffer(inputStream));
    }

    public ANTLRTokdefLexer(Reader reader) {
        this(new CharBuffer(reader));
    }

    public ANTLRTokdefLexer(InputBuffer inputBuffer) {
        this(new LexerSharedInputState(inputBuffer));
    }

    public ANTLRTokdefLexer(LexerSharedInputState lexerSharedInputState) {
        super(lexerSharedInputState);
        this.caseSensitiveLiterals = true;
        setCaseSensitive(true);
        this.literals = new Hashtable();
    }

    @Override // antlr.TokenStream
    public Token nextToken() throws TokenStreamException {
        do {
            resetText();
            try {
                try {
                    char cLA = LA(1);
                    if (cLA != '\t' && cLA != '\n' && cLA != '\r' && cLA != ' ') {
                        if (cLA != '\"') {
                            if (cLA != '=') {
                                if (cLA != '(') {
                                    if (cLA == ')') {
                                        mRPAREN(true);
                                        Token token = this._returnToken;
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
                                                mINT(true);
                                                Token token2 = this._returnToken;
                                                break;
                                            default:
                                                switch (cLA) {
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
                                                                break;
                                                            default:
                                                                if (LA(1) == '/' && LA(2) == '/') {
                                                                    mSL_COMMENT(true);
                                                                    Token token3 = this._returnToken;
                                                                } else if (LA(1) == '/' && LA(2) == '*') {
                                                                    mML_COMMENT(true);
                                                                    Token token4 = this._returnToken;
                                                                } else {
                                                                    if (LA(1) != 65535) {
                                                                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                                                                    }
                                                                    uponEOF();
                                                                    this._returnToken = makeToken(1);
                                                                }
                                                                break;
                                                        }
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
                                                        Token token5 = this._returnToken;
                                                        break;
                                                }
                                                break;
                                        }
                                    }
                                } else {
                                    mLPAREN(true);
                                    Token token6 = this._returnToken;
                                }
                            } else {
                                mASSIGN(true);
                                Token token7 = this._returnToken;
                            }
                        } else {
                            mSTRING(true);
                            Token token8 = this._returnToken;
                        }
                    } else {
                        mWS(true);
                        Token token9 = this._returnToken;
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
        } else if (cLA == '\r') {
            match('\r');
            if (LA(1) == '\n') {
                match('\n');
            }
            newline();
        } else if (cLA == ' ') {
            match(StringUtil.SPACE);
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        this._returnToken = null;
    }

    public final void mSL_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        this.text.length();
        match("//");
        while (true) {
            BitSet bitSet = _tokenSet_0;
            if (!bitSet.member(LA(1))) {
                break;
            } else {
                match(bitSet);
            }
        }
        char cLA = LA(1);
        if (cLA == '\n') {
            match('\n');
        } else if (cLA == '\r') {
            match('\r');
            if (LA(1) == '\n') {
                match('\n');
            }
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        newline();
        this._returnToken = null;
    }

    public final void mML_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        this.text.length();
        match(RefSpec.WILDCARD_SUFFIX);
        while (true) {
            if (LA(1) == '*' && _tokenSet_1.member(LA(2))) {
                match('*');
                matchNot('/');
            } else if (LA(1) == '\n') {
                match('\n');
                newline();
            } else if (_tokenSet_2.member(LA(1))) {
                matchNot('*');
            } else {
                match("*/");
                this._returnToken = null;
                return;
            }
        }
    }

    public final void mLPAREN(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('(');
        if (z) {
            tokenMakeToken = makeToken(7);
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
            tokenMakeToken = makeToken(8);
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
            tokenMakeToken = makeToken(6);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mSTRING(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        match('\"');
        while (true) {
            if (LA(1) == '\\') {
                mESC(false);
            } else if (!_tokenSet_3.member(LA(1))) {
                break;
            } else {
                matchNot('\"');
            }
        }
        match('\"');
        if (z) {
            tokenMakeToken = makeToken(5);
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
        } else if (cLA == 'b') {
            match('b');
        } else if (cLA == 'f') {
            match('f');
        } else if (cLA == 'n') {
            match('n');
        } else if (cLA == 'r') {
            match(Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL);
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
            tokenMakeToken = makeToken(13);
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
            tokenMakeToken = makeToken(14);
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
            tokenMakeToken = makeToken(15);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    public final void mID(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        char cLA = LA(1);
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
                        throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
                break;
        }
        while (true) {
            char cLA2 = LA(1);
            if (cLA2 == '_') {
                match('_');
            } else {
                switch (cLA2) {
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
                        switch (cLA2) {
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
                                switch (cLA2) {
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
                                        if (z) {
                                            tokenMakeToken = makeToken(4);
                                            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
                                        } else {
                                            tokenMakeToken = null;
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

    public final void mINT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int i = 0;
        while (LA(1) >= '0' && LA(1) <= '9') {
            mDIGIT(false);
            i++;
        }
        if (i < 1) {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(9);
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
        jArr[0] = -140737488355336L;
        for (int i = 1; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_2() {
        long[] jArr = new long[8];
        jArr[0] = -4398046512136L;
        for (int i = 1; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_3() {
        long[] jArr = new long[8];
        jArr[0] = -17179869192L;
        jArr[1] = -268435457;
        for (int i = 2; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }
}
