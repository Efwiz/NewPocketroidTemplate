package antlr.actions.python;

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
import antlr.Tool;
import antlr.collections.impl.BitSet;
import io.netty.util.internal.StringUtil;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Hashtable;
import org.eclipse.jgit.transport.RefSpec;

/* JADX INFO: loaded from: classes.dex */
public class CodeLexer extends CharScanner implements CodeLexerTokenTypes, TokenStream {
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
    private Tool antlrTool;
    protected int lineOffset;

    public CodeLexer(String str, String str2, int i, Tool tool) {
        this(new StringReader(str));
        setLine(i);
        setFilename(str2);
        this.antlrTool = tool;
    }

    public void setLineOffset(int i) {
        setLine(i);
    }

    @Override // antlr.CharScanner
    public void reportError(RecognitionException recognitionException) {
        this.antlrTool.error(new StringBuffer("Syntax error in action: ").append(recognitionException).toString(), getFilename(), getLine(), getColumn());
    }

    @Override // antlr.CharScanner
    public void reportError(String str) {
        this.antlrTool.error(str, getFilename(), getLine(), getColumn());
    }

    @Override // antlr.CharScanner
    public void reportWarning(String str) {
        if (getFilename() == null) {
            this.antlrTool.warning(str);
        } else {
            this.antlrTool.warning(str, getFilename(), getLine(), getColumn());
        }
    }

    public CodeLexer(InputStream inputStream) {
        this(new ByteBuffer(inputStream));
    }

    public CodeLexer(Reader reader) {
        this(new CharBuffer(reader));
    }

    public CodeLexer(InputBuffer inputBuffer) {
        this(new LexerSharedInputState(inputBuffer));
    }

    public CodeLexer(LexerSharedInputState lexerSharedInputState) {
        super(lexerSharedInputState);
        this.lineOffset = 0;
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
                    mACTION(true);
                    Token token = this._returnToken;
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

    public final void mACTION(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        while (LA(1) >= 3 && LA(1) <= 255) {
            mSTUFF(false);
        }
        if (z) {
            tokenMakeToken = makeToken(4);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0055  */
    /* JADX WARN: Code duplicated, block: B:21:0x005b  */
    /* JADX WARN: Code duplicated, block: B:22:0x0062  */
    /* JADX WARN: Code duplicated, block: B:24:0x0068  */
    /* JADX WARN: Code duplicated, block: B:25:0x006f  */
    /* JADX WARN: Code duplicated, block: B:27:0x007b  */
    /* JADX WARN: Code duplicated, block: B:33:0x009f  */
    protected final void mSTUFF(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        BitSet bitSet;
        Token tokenMakeToken;
        int length = this.text.length();
        if (LA(1) == '/' && (LA(2) == '*' || LA(2) == '/')) {
            mCOMMENT(false);
        } else if (LA(1) == '\r' && LA(2) == '\n') {
            match("\r\n");
            newline();
        } else if (LA(1) == '/') {
            BitSet bitSet2 = _tokenSet_0;
            if (bitSet2.member(LA(2))) {
                match('/');
                match(bitSet2);
            } else if (LA(1) == '\r') {
                match('\r');
                newline();
            } else if (LA(1) == '\n') {
                match('\n');
                newline();
            } else {
                bitSet = _tokenSet_1;
                if (bitSet.member(LA(1))) {
                    match(bitSet);
                } else {
                    throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
                }
            }
        } else if (LA(1) == '\r') {
            match('\r');
            newline();
        } else if (LA(1) == '\n') {
            match('\n');
            newline();
        } else {
            bitSet = _tokenSet_1;
            if (bitSet.member(LA(1))) {
                match(bitSet);
            } else {
                throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
            }
        }
        if (z) {
            tokenMakeToken = makeToken(5);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mCOMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        if (LA(1) == '/' && LA(2) == '/') {
            mSL_COMMENT(false);
        } else if (LA(1) == '/' && LA(2) == '*') {
            mML_COMMENT(false);
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        if (z) {
            tokenMakeToken = makeToken(6);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mSL_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int length2 = this.text.length();
        match("//");
        this.text.setLength(length2);
        this.text.append("#");
        while (LA(1) != '\n' && LA(1) != '\r' && LA(1) >= 3 && LA(1) <= 255 && LA(2) >= 3 && LA(2) <= 255) {
            matchNot((char) 65535);
        }
        if (LA(1) == '\r' && LA(2) == '\n') {
            match("\r\n");
        } else if (LA(1) == '\n') {
            match('\n');
        } else if (LA(1) == '\r') {
            match('\r');
        } else {
            throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
        }
        newline();
        if (z) {
            tokenMakeToken = makeToken(7);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mML_COMMENT(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        int length2 = this.text.length();
        match(RefSpec.WILDCARD_SUFFIX);
        this.text.setLength(length2);
        this.text.append("#");
        while (true) {
            if (LA(1) == '*' && LA(2) == '/') {
                break;
            }
            if (LA(1) == '\r' && LA(2) == '\n') {
                match('\r');
                match('\n');
                int length3 = this.text.length();
                mIGNWS(false);
                this.text.setLength(length3);
                newline();
                this.text.append("# ");
            } else if (LA(1) == '\r' && LA(2) >= 3 && LA(2) <= 255) {
                match('\r');
                int length4 = this.text.length();
                mIGNWS(false);
                this.text.setLength(length4);
                newline();
                this.text.append("# ");
            } else if (LA(1) == '\n' && LA(2) >= 3 && LA(2) <= 255) {
                match('\n');
                int length5 = this.text.length();
                mIGNWS(false);
                this.text.setLength(length5);
                newline();
                this.text.append("# ");
            } else if (LA(1) < 3 || LA(1) > 255 || LA(2) < 3 || LA(2) > 255) {
                break;
            } else {
                matchNot((char) 65535);
            }
        }
        this.text.append("\n");
        int length6 = this.text.length();
        match("*/");
        this.text.setLength(length6);
        if (z) {
            tokenMakeToken = makeToken(9);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    protected final void mIGNWS(boolean z) throws RecognitionException, CharStreamException, TokenStreamException {
        Token tokenMakeToken;
        int length = this.text.length();
        while (true) {
            if (LA(1) == ' ' && LA(2) >= 3 && LA(2) <= 255) {
                match(StringUtil.SPACE);
            } else if (LA(1) != '\t' || LA(2) < 3 || LA(2) > 255) {
                break;
            } else {
                match('\t');
            }
        }
        if (z) {
            tokenMakeToken = makeToken(8);
            tokenMakeToken.setText(new String(this.text.getBuffer(), length, this.text.length() - length));
        } else {
            tokenMakeToken = null;
        }
        this._returnToken = tokenMakeToken;
    }

    private static final long[] mk_tokenSet_0() {
        long[] jArr = new long[8];
        jArr[0] = -145135534866440L;
        for (int i = 1; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }

    private static final long[] mk_tokenSet_1() {
        long[] jArr = new long[8];
        jArr[0] = -140737488364552L;
        for (int i = 1; i <= 3; i++) {
            jArr[i] = -1;
        }
        return jArr;
    }
}
