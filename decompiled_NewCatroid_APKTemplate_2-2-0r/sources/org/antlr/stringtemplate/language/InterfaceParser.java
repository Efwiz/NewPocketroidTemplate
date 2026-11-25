package org.antlr.stringtemplate.language;

import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;
import java.util.LinkedHashMap;
import org.antlr.stringtemplate.StringTemplateGroupInterface;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes12.dex */
public class InterfaceParser extends LLkParser implements InterfaceParserTokenTypes {
    public static final String[] _tokenNames = {"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"interface\"", SchemaSymbols.ATTVAL_ID, "SEMI", "\"optional\"", "LPAREN", "RPAREN", "COMMA", "COLON", "SL_COMMENT", "ML_COMMENT", "WS"};
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
    public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
    protected StringTemplateGroupInterface groupI;

    @Override // antlr.Parser
    public void reportError(RecognitionException recognitionException) {
        StringTemplateGroupInterface stringTemplateGroupInterface = this.groupI;
        if (stringTemplateGroupInterface != null) {
            stringTemplateGroupInterface.error("template group interface parse error", recognitionException);
        } else {
            System.err.println(new StringBuffer("template group interface parse error: ").append(recognitionException).toString());
            recognitionException.printStackTrace(System.err);
        }
    }

    protected InterfaceParser(TokenBuffer tokenBuffer, int i) {
        super(tokenBuffer, i);
        this.tokenNames = _tokenNames;
    }

    public InterfaceParser(TokenBuffer tokenBuffer) {
        this(tokenBuffer, 3);
    }

    protected InterfaceParser(TokenStream tokenStream, int i) {
        super(tokenStream, i);
        this.tokenNames = _tokenNames;
    }

    public InterfaceParser(TokenStream tokenStream) {
        this(tokenStream, 3);
    }

    public InterfaceParser(ParserSharedInputState parserSharedInputState) {
        super(parserSharedInputState, 3);
        this.tokenNames = _tokenNames;
    }

    public final void groupInterface(StringTemplateGroupInterface stringTemplateGroupInterface) throws RecognitionException, TokenStreamException {
        this.groupI = stringTemplateGroupInterface;
        try {
            match(4);
            Token tokenLT = LT(1);
            match(5);
            stringTemplateGroupInterface.setName(tokenLT.getText());
            match(6);
            int i = 0;
            while (true) {
                if (LA(1) != 5 && LA(1) != 7) {
                    break;
                }
                template(stringTemplateGroupInterface);
                i++;
            }
            if (i >= 1) {
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_0);
        }
    }

    public final void template(StringTemplateGroupInterface stringTemplateGroupInterface) throws RecognitionException, TokenStreamException {
        Token tokenLT;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        boolean z = true;
        try {
            int iLA = LA(1);
            if (iLA == 5) {
                tokenLT = null;
            } else if (iLA == 7) {
                tokenLT = LT(1);
                match(7);
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
            Token tokenLT2 = LT(1);
            match(5);
            match(8);
            int iLA2 = LA(1);
            if (iLA2 == 5) {
                linkedHashMap = args();
            } else if (iLA2 != 9) {
                throw new NoViableAltException(LT(1), getFilename());
            }
            match(9);
            match(6);
            String text = tokenLT2.getText();
            if (tokenLT == null) {
                z = false;
            }
            stringTemplateGroupInterface.defineTemplate(text, linkedHashMap, z);
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_1);
        }
    }

    public final LinkedHashMap args() throws RecognitionException, TokenStreamException {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            Token tokenLT = LT(1);
            match(5);
            linkedHashMap.put(tokenLT.getText(), new FormalArgument(tokenLT.getText()));
            while (LA(1) == 10) {
                match(10);
                Token tokenLT2 = LT(1);
                match(5);
                linkedHashMap.put(tokenLT2.getText(), new FormalArgument(tokenLT2.getText()));
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_2);
        }
        return linkedHashMap;
    }

    private static final long[] mk_tokenSet_0() {
        return new long[]{2, 0};
    }

    private static final long[] mk_tokenSet_1() {
        return new long[]{162, 0};
    }

    private static final long[] mk_tokenSet_2() {
        return new long[]{512, 0};
    }
}
