package antlr.preprocessor;

import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.SemanticException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;
import antlr.collections.impl.IndexedVector;
import org.apache.commons.lang3.SystemProperties;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes.dex */
public class Preprocessor extends LLkParser implements PreprocessorTokenTypes {
    public static final String[] _tokenNames = {"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"tokens\"", "HEADER_ACTION", "SUBRULE_BLOCK", "ACTION", "\"class\"", SchemaSymbols.ATTVAL_ID, "\"extends\"", "SEMI", "TOKENS_SPEC", "OPTIONS_START", "ASSIGN_RHS", "RCURLY", "\"protected\"", "\"private\"", "\"public\"", "BANG", "ARG_ACTION", "\"returns\"", "RULE_BLOCK", "\"throws\"", "COMMA", "\"exception\"", "\"catch\"", "ALT", "ELEMENT", "LPAREN", "RPAREN", "ID_OR_KEYWORD", "CURLY_BLOCK_SCARF", "WS", "NEWLINE", "COMMENT", "SL_COMMENT", "ML_COMMENT", "CHAR_LITERAL", "STRING_LITERAL", "ESC", "DIGIT", "XDIGIT"};
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
    public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
    public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
    public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
    public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
    public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
    public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
    public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
    private antlr.Tool antlrTool;

    public void setTool(antlr.Tool tool) {
        if (this.antlrTool == null) {
            this.antlrTool = tool;
            return;
        }
        throw new IllegalStateException("antlr.Tool already registered");
    }

    protected antlr.Tool getTool() {
        return this.antlrTool;
    }

    @Override // antlr.Parser
    public void reportError(String str) {
        if (getTool() != null) {
            getTool().error(str, getFilename(), -1, -1);
        } else {
            super.reportError(str);
        }
    }

    @Override // antlr.Parser
    public void reportError(RecognitionException recognitionException) {
        if (getTool() != null) {
            getTool().error(recognitionException.getErrorMessage(), recognitionException.getFilename(), recognitionException.getLine(), recognitionException.getColumn());
        } else {
            super.reportError(recognitionException);
        }
    }

    @Override // antlr.Parser
    public void reportWarning(String str) {
        if (getTool() != null) {
            getTool().warning(str, getFilename(), -1, -1);
        } else {
            super.reportWarning(str);
        }
    }

    protected Preprocessor(TokenBuffer tokenBuffer, int i) {
        super(tokenBuffer, i);
        this.tokenNames = _tokenNames;
    }

    public Preprocessor(TokenBuffer tokenBuffer) {
        this(tokenBuffer, 1);
    }

    protected Preprocessor(TokenStream tokenStream, int i) {
        super(tokenStream, i);
        this.tokenNames = _tokenNames;
    }

    public Preprocessor(TokenStream tokenStream) {
        this(tokenStream, 1);
    }

    public Preprocessor(ParserSharedInputState parserSharedInputState) {
        super(parserSharedInputState, 1);
        this.tokenNames = _tokenNames;
    }

    public final void grammarFile(Hierarchy hierarchy, String str) throws RecognitionException, TokenStreamException {
        while (LA(1) == 5) {
            try {
                Token tokenLT = LT(1);
                match(5);
                hierarchy.getFile(str).addHeaderAction(tokenLT.getText());
            } catch (RecognitionException e) {
                reportError(e);
                consume();
                consumeUntil(_tokenSet_0);
                return;
            }
        }
        int iLA = LA(1);
        IndexedVector indexedVectorOptionSpec = null;
        if (iLA != 1) {
            if (iLA == 13) {
                indexedVectorOptionSpec = optionSpec(null);
            } else if (iLA != 7 && iLA != 8) {
                throw new NoViableAltException(LT(1), getFilename());
            }
        }
        while (true) {
            if (LA(1) != 7 && LA(1) != 8) {
                match(1);
                return;
            }
            Grammar grammarClass_def = class_def(str, hierarchy);
            if (grammarClass_def != null && indexedVectorOptionSpec != null) {
                hierarchy.getFile(str).setOptions(indexedVectorOptionSpec);
            }
            if (grammarClass_def != null) {
                grammarClass_def.setFileName(str);
                hierarchy.addGrammar(grammarClass_def);
            }
        }
    }

    public final IndexedVector optionSpec(Grammar grammar) throws RecognitionException, TokenStreamException {
        IndexedVector indexedVector = new IndexedVector();
        try {
            match(13);
            while (LA(1) == 9) {
                Token tokenLT = LT(1);
                match(9);
                Token tokenLT2 = LT(1);
                match(14);
                Option option = new Option(tokenLT.getText(), tokenLT2.getText(), grammar);
                indexedVector.appendElement(option.getName(), option);
                if (grammar != null && tokenLT.getText().equals("importVocab")) {
                    grammar.specifiedVocabulary = true;
                    grammar.importVocab = tokenLT2.getText();
                } else if (grammar != null && tokenLT.getText().equals("exportVocab")) {
                    grammar.exportVocab = tokenLT2.getText().substring(0, tokenLT2.getText().length() - 1);
                    grammar.exportVocab = grammar.exportVocab.trim();
                }
            }
            match(15);
        } catch (RecognitionException e) {
            reportError(e);
            consume();
            consumeUntil(_tokenSet_1);
        }
        return indexedVector;
    }

    public final Grammar class_def(String str, Hierarchy hierarchy) throws RecognitionException, TokenStreamException {
        Token tokenLT;
        String strSuperClass;
        IndexedVector indexedVector = new IndexedVector(100);
        Grammar grammar = null;
        indexedVectorOptionSpec = null;
        indexedVectorOptionSpec = null;
        indexedVectorOptionSpec = null;
        IndexedVector indexedVectorOptionSpec = null;
        try {
            int iLA = LA(1);
            if (iLA == 7) {
                tokenLT = LT(1);
                match(7);
            } else {
                if (iLA != 8) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
                tokenLT = null;
            }
            match(8);
            Token tokenLT2 = LT(1);
            match(9);
            match(10);
            Token tokenLT3 = LT(1);
            match(9);
            int iLA2 = LA(1);
            if (iLA2 == 6) {
                strSuperClass = superClass();
            } else {
                if (iLA2 != 11) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
                strSuperClass = null;
            }
            match(11);
            Grammar grammar2 = hierarchy.getGrammar(tokenLT2.getText());
            if (grammar2 != null) {
                throw new SemanticException(new StringBuffer("redefinition of grammar ").append(tokenLT2.getText()).toString(), str, tokenLT2.getLine(), tokenLT2.getColumn());
            }
            try {
                Grammar grammar3 = new Grammar(hierarchy.getTool(), tokenLT2.getText(), tokenLT3.getText(), indexedVector);
                try {
                    grammar3.superClass = strSuperClass;
                    if (tokenLT != null) {
                        grammar3.setPreambleAction(tokenLT.getText());
                    }
                    int iLA3 = LA(1);
                    if (iLA3 != 7 && iLA3 != 9 && iLA3 != 12) {
                        if (iLA3 == 13) {
                            indexedVectorOptionSpec = optionSpec(grammar3);
                        } else {
                            switch (iLA3) {
                                case 16:
                                case 17:
                                case 18:
                                    break;
                                default:
                                    throw new NoViableAltException(LT(1), getFilename());
                            }
                        }
                    }
                    grammar3.setOptions(indexedVectorOptionSpec);
                    int iLA4 = LA(1);
                    if (iLA4 != 7 && iLA4 != 9) {
                        if (iLA4 == 12) {
                            Token tokenLT4 = LT(1);
                            match(12);
                            grammar3.setTokenSection(tokenLT4.getText());
                        } else {
                            switch (iLA4) {
                                case 16:
                                case 17:
                                case 18:
                                    break;
                                default:
                                    throw new NoViableAltException(LT(1), getFilename());
                            }
                        }
                    }
                    int iLA5 = LA(1);
                    if (iLA5 == 7) {
                        Token tokenLT5 = LT(1);
                        match(7);
                        grammar3.setMemberAction(tokenLT5.getText());
                    } else if (iLA5 != 9) {
                        switch (iLA5) {
                            case 16:
                            case 17:
                            case 18:
                                break;
                            default:
                                throw new NoViableAltException(LT(1), getFilename());
                        }
                    }
                    int i = 0;
                    while (_tokenSet_2.member(LA(1))) {
                        rule(grammar3);
                        i++;
                    }
                    if (i >= 1) {
                        return grammar3;
                    }
                    throw new NoViableAltException(LT(1), getFilename());
                } catch (RecognitionException e) {
                    grammar = grammar3;
                    e = e;
                    reportError(e);
                    consume();
                    consumeUntil(_tokenSet_3);
                    return grammar;
                }
            } catch (RecognitionException e2) {
                e = e2;
                grammar = grammar2;
            }
        } catch (RecognitionException e3) {
            e = e3;
        }
    }

    public final String superClass() throws RecognitionException, TokenStreamException {
        String text = LT(1).getText();
        try {
            match(6);
        } catch (RecognitionException e) {
            reportError(e);
            consume();
            consumeUntil(_tokenSet_4);
        }
        return text;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0061  */
    public final void rule(Grammar grammar) throws RecognitionException, TokenStreamException {
        String str;
        boolean z;
        Token tokenLT;
        Token tokenLT2;
        String strThrowsSpec;
        IndexedVector indexedVectorOptionSpec;
        try {
            int iLA = LA(1);
            Token tokenLT3 = null;
            if (iLA != 9) {
                switch (iLA) {
                    case 16:
                        match(16);
                        str = "protected";
                        break;
                    case 17:
                        match(17);
                        str = "private";
                        break;
                    case 18:
                        match(18);
                        str = "public";
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            } else {
                str = null;
            }
            Token tokenLT4 = LT(1);
            match(9);
            int iLA2 = LA(1);
            if (iLA2 == 7 || iLA2 == 13) {
                z = false;
            } else {
                switch (iLA2) {
                    case 19:
                        match(19);
                        z = true;
                        break;
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                        z = false;
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
            int iLA3 = LA(1);
            if (iLA3 == 7 || iLA3 == 13) {
                tokenLT = null;
            } else {
                switch (iLA3) {
                    case 20:
                        tokenLT = LT(1);
                        match(20);
                        break;
                    case 21:
                    case 22:
                    case 23:
                        tokenLT = null;
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
            int iLA4 = LA(1);
            if (iLA4 == 7 || iLA4 == 13) {
                tokenLT2 = null;
            } else {
                switch (iLA4) {
                    case 21:
                        match(21);
                        tokenLT2 = LT(1);
                        match(20);
                        break;
                    case 22:
                    case 23:
                        tokenLT2 = null;
                        break;
                    default:
                        throw new NoViableAltException(LT(1), getFilename());
                }
            }
            int iLA5 = LA(1);
            if (iLA5 == 7 || iLA5 == 13 || iLA5 == 22) {
                strThrowsSpec = "";
            } else if (iLA5 == 23) {
                strThrowsSpec = throwsSpec();
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
            int iLA6 = LA(1);
            if (iLA6 == 7) {
                indexedVectorOptionSpec = null;
            } else if (iLA6 == 13) {
                indexedVectorOptionSpec = optionSpec(null);
            } else {
                if (iLA6 != 22) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
                indexedVectorOptionSpec = null;
            }
            int iLA7 = LA(1);
            if (iLA7 == 7) {
                tokenLT3 = LT(1);
                match(7);
            } else if (iLA7 != 22) {
                throw new NoViableAltException(LT(1), getFilename());
            }
            Token tokenLT5 = LT(1);
            match(22);
            Rule rule = new Rule(tokenLT4.getText(), new StringBuffer().append(tokenLT5.getText()).append(exceptionGroup()).toString(), indexedVectorOptionSpec, grammar);
            rule.setThrowsSpec(strThrowsSpec);
            if (tokenLT != null) {
                rule.setArgs(tokenLT.getText());
            }
            if (tokenLT2 != null) {
                rule.setReturnValue(tokenLT2.getText());
            }
            if (tokenLT3 != null) {
                rule.setInitAction(tokenLT3.getText());
            }
            if (z) {
                rule.setBang();
            }
            rule.setVisibility(str);
            if (grammar != null) {
                grammar.addRule(rule);
            }
        } catch (RecognitionException e) {
            reportError(e);
            consume();
            consumeUntil(_tokenSet_5);
        }
    }

    public final String throwsSpec() throws RecognitionException, TokenStreamException {
        String string = "throws ";
        try {
            match(23);
            Token tokenLT = LT(1);
            match(9);
            string = new StringBuffer("throws ").append(tokenLT.getText()).toString();
            while (LA(1) == 24) {
                match(24);
                Token tokenLT2 = LT(1);
                match(9);
                string = new StringBuffer().append(string).append(",").append(tokenLT2.getText()).toString();
            }
        } catch (RecognitionException e) {
            reportError(e);
            consume();
            consumeUntil(_tokenSet_6);
        }
        return string;
    }

    public final String exceptionGroup() throws RecognitionException, TokenStreamException {
        String string = "";
        while (LA(1) == 25) {
            try {
                string = new StringBuffer().append(string).append(exceptionSpec()).toString();
            } catch (RecognitionException e) {
                reportError(e);
                consume();
                consumeUntil(_tokenSet_5);
            }
        }
        return string;
    }

    public final String exceptionSpec() throws RecognitionException, TokenStreamException {
        String string = new StringBuffer().append(System.getProperty(SystemProperties.LINE_SEPARATOR)).append("exception ").toString();
        try {
            match(25);
            int iLA = LA(1);
            if (iLA != 1) {
                if (iLA == 20) {
                    Token tokenLT = LT(1);
                    match(20);
                    string = new StringBuffer().append(string).append(tokenLT.getText()).toString();
                } else if (iLA != 7 && iLA != 8 && iLA != 9 && iLA != 25 && iLA != 26) {
                    switch (iLA) {
                        case 16:
                        case 17:
                        case 18:
                            break;
                        default:
                            throw new NoViableAltException(LT(1), getFilename());
                    }
                }
            }
            while (LA(1) == 26) {
                string = new StringBuffer().append(string).append(exceptionHandler()).toString();
            }
        } catch (RecognitionException e) {
            reportError(e);
            consume();
            consumeUntil(_tokenSet_7);
        }
        return string;
    }

    public final String exceptionHandler() throws RecognitionException, TokenStreamException {
        try {
            match(26);
            Token tokenLT = LT(1);
            match(20);
            Token tokenLT2 = LT(1);
            match(7);
            return new StringBuffer().append(System.getProperty(SystemProperties.LINE_SEPARATOR)).append("catch ").append(tokenLT.getText()).append(" ").append(tokenLT2.getText()).toString();
        } catch (RecognitionException e) {
            reportError(e);
            consume();
            consumeUntil(_tokenSet_8);
            return null;
        }
    }

    private static final long[] mk_tokenSet_0() {
        return new long[]{2, 0};
    }

    private static final long[] mk_tokenSet_1() {
        return new long[]{4658050, 0};
    }

    private static final long[] mk_tokenSet_2() {
        return new long[]{459264, 0};
    }

    private static final long[] mk_tokenSet_3() {
        return new long[]{386, 0};
    }

    private static final long[] mk_tokenSet_4() {
        return new long[]{2048, 0};
    }

    private static final long[] mk_tokenSet_5() {
        return new long[]{459650, 0};
    }

    private static final long[] mk_tokenSet_6() {
        return new long[]{4202624, 0};
    }

    private static final long[] mk_tokenSet_7() {
        return new long[]{34014082, 0};
    }

    private static final long[] mk_tokenSet_8() {
        return new long[]{101122946, 0};
    }
}
