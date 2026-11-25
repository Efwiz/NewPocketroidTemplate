package org.antlr.stringtemplate.language;

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
import java.util.HashMap;
import java.util.Map;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes12.dex */
public class GroupParser extends LLkParser implements GroupParserTokenTypes {
    public static final String[] _tokenNames = {"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"group\"", SchemaSymbols.ATTVAL_ID, "COLON", "\"implements\"", "COMMA", "SEMI", "AT", "DOT", "LPAREN", "RPAREN", "DEFINED_TO_BE", "STRING", "BIGSTRING", "ASSIGN", "ANONYMOUS_TEMPLATE", "LBRACK", "RBRACK", "\"default\"", "STAR", "PLUS", "OPTIONAL", "SL_COMMENT", "ML_COMMENT", "WS"};
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
    public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
    public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
    public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
    public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
    protected StringTemplateGroup group;

    @Override // antlr.Parser
    public void reportError(RecognitionException recognitionException) {
        StringTemplateGroup stringTemplateGroup = this.group;
        if (stringTemplateGroup != null) {
            stringTemplateGroup.error("template group parse error", recognitionException);
        } else {
            System.err.println(new StringBuffer("template group parse error: ").append(recognitionException).toString());
            recognitionException.printStackTrace(System.err);
        }
    }

    protected GroupParser(TokenBuffer tokenBuffer, int i) {
        super(tokenBuffer, i);
        this.tokenNames = _tokenNames;
    }

    public GroupParser(TokenBuffer tokenBuffer) {
        this(tokenBuffer, 3);
    }

    protected GroupParser(TokenStream tokenStream, int i) {
        super(tokenStream, i);
        this.tokenNames = _tokenNames;
    }

    public GroupParser(TokenStream tokenStream) {
        this(tokenStream, 3);
    }

    public GroupParser(ParserSharedInputState parserSharedInputState) {
        super(parserSharedInputState, 3);
        this.tokenNames = _tokenNames;
    }

    public final void group(StringTemplateGroup stringTemplateGroup) throws RecognitionException, TokenStreamException {
        this.group = stringTemplateGroup;
        try {
            match(4);
            Token tokenLT = LT(1);
            match(5);
            stringTemplateGroup.setName(tokenLT.getText());
            int iLA = LA(1);
            if (iLA == 6) {
                match(6);
                Token tokenLT2 = LT(1);
                match(5);
                stringTemplateGroup.setSuperGroup(tokenLT2.getText());
            } else if (iLA != 7 && iLA != 9) {
                throw new NoViableAltException(LT(1), getFilename());
            }
            int iLA2 = LA(1);
            if (iLA2 == 7) {
                match(7);
                Token tokenLT3 = LT(1);
                match(5);
                stringTemplateGroup.implementInterface(tokenLT3.getText());
                while (LA(1) == 8) {
                    match(8);
                    Token tokenLT4 = LT(1);
                    match(5);
                    stringTemplateGroup.implementInterface(tokenLT4.getText());
                }
            } else if (iLA2 != 9) {
                throw new NoViableAltException(LT(1), getFilename());
            }
            match(9);
            int i = 0;
            while (true) {
                if ((LA(1) == 5 || LA(1) == 10) && ((LA(2) == 5 || LA(2) == 12 || LA(2) == 14) && (LA(3) == 5 || LA(3) == 11 || LA(3) == 13))) {
                    template(stringTemplateGroup);
                } else if (LA(1) != 5 || LA(2) != 14 || LA(3) != 19) {
                    break;
                } else {
                    mapdef(stringTemplateGroup);
                }
                i++;
            }
            if (i < 1) {
                throw new NoViableAltException(LT(1), getFilename());
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_0);
        }
    }

    public final void template(StringTemplateGroup stringTemplateGroup) throws Throwable {
        StringTemplate stringTemplateDefineTemplate;
        boolean z;
        int line = LT(1).getLine();
        try {
            if ((LA(1) != 5 && LA(1) != 10) || (LA(2) != 5 && LA(2) != 12)) {
                if (LA(1) == 5 && LA(2) == 14) {
                    Token tokenLT = LT(1);
                    match(5);
                    match(14);
                    Token tokenLT2 = LT(1);
                    match(5);
                    stringTemplateGroup.defineTemplateAlias(tokenLT.getText(), tokenLT2.getText());
                    return;
                }
                throw new NoViableAltException(LT(1), getFilename());
            }
            int iLA = LA(1);
            if (iLA == 5) {
                Token tokenLT3 = LT(1);
                match(5);
                String text = tokenLT3.getText();
                if (stringTemplateGroup.isDefinedInThisGroup(text)) {
                    stringTemplateGroup.error(new StringBuffer("redefinition of template: ").append(text).toString());
                    stringTemplateDefineTemplate = new StringTemplate();
                } else {
                    stringTemplateDefineTemplate = stringTemplateGroup.defineTemplate(text, null);
                }
            } else if (iLA == 10) {
                match(10);
                Token tokenLT4 = LT(1);
                match(5);
                match(11);
                Token tokenLT5 = LT(1);
                match(5);
                if (stringTemplateGroup.isDefinedInThisGroup(stringTemplateGroup.getMangledRegionName(tokenLT4.getText(), tokenLT5.getText()))) {
                    stringTemplateGroup.error(new StringBuffer("group ").append(stringTemplateGroup.getName()).append(" line ").append(line).append(": redefinition of template region: @").append(tokenLT4.getText()).append(".").append(tokenLT5.getText()).toString());
                    stringTemplateDefineTemplate = new StringTemplate();
                } else {
                    StringTemplate stringTemplateLookupTemplate = stringTemplateGroup.lookupTemplate(tokenLT4.getText());
                    if (stringTemplateLookupTemplate == null) {
                        stringTemplateGroup.error(new StringBuffer("group ").append(stringTemplateGroup.getName()).append(" line ").append(line).append(": reference to region within undefined template: ").append(tokenLT4.getText()).toString());
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!stringTemplateLookupTemplate.containsRegionName(tokenLT5.getText())) {
                        stringTemplateGroup.error(new StringBuffer("group ").append(stringTemplateGroup.getName()).append(" line ").append(line).append(": template ").append(tokenLT4.getText()).append(" has no region called ").append(tokenLT5.getText()).toString());
                        z = true;
                    }
                    if (z) {
                        stringTemplateDefineTemplate = new StringTemplate();
                    } else {
                        stringTemplateDefineTemplate = stringTemplateGroup.defineRegionTemplate(tokenLT4.getText(), tokenLT5.getText(), (String) null, 3);
                    }
                }
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
            if (stringTemplateDefineTemplate != null) {
                stringTemplateDefineTemplate.setGroupFileLine(line);
            }
            match(12);
            int iLA2 = LA(1);
            if (iLA2 == 5) {
                args(stringTemplateDefineTemplate);
            } else if (iLA2 == 13) {
                stringTemplateDefineTemplate.defineEmptyFormalArgumentList();
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
            match(13);
            match(14);
            int iLA3 = LA(1);
            if (iLA3 == 15) {
                Token tokenLT6 = LT(1);
                match(15);
                stringTemplateDefineTemplate.setTemplate(tokenLT6.getText());
            } else {
                if (iLA3 == 16) {
                    Token tokenLT7 = LT(1);
                    match(16);
                    stringTemplateDefineTemplate.setTemplate(tokenLT7.getText());
                    return;
                }
                throw new NoViableAltException(LT(1), getFilename());
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_1);
        }
    }

    public final void mapdef(StringTemplateGroup stringTemplateGroup) throws RecognitionException, TokenStreamException {
        try {
            Token tokenLT = LT(1);
            match(5);
            match(14);
            Map map = map();
            if (stringTemplateGroup.getMap(tokenLT.getText()) != null) {
                stringTemplateGroup.error(new StringBuffer("redefinition of map: ").append(tokenLT.getText()).toString());
            } else if (stringTemplateGroup.isDefinedInThisGroup(tokenLT.getText())) {
                stringTemplateGroup.error(new StringBuffer("redefinition of template as map: ").append(tokenLT.getText()).toString());
            } else {
                stringTemplateGroup.defineMap(tokenLT.getText(), map);
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_1);
        }
    }

    public final void args(StringTemplate stringTemplate) throws RecognitionException, TokenStreamException {
        try {
            arg(stringTemplate);
            while (LA(1) == 8) {
                match(8);
                arg(stringTemplate);
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_2);
        }
    }

    public final void arg(StringTemplate stringTemplate) throws RecognitionException, TokenStreamException {
        StringTemplate stringTemplate2;
        try {
            Token tokenLT = LT(1);
            match(5);
            if (LA(1) == 17 && LA(2) == 15) {
                match(17);
                Token tokenLT2 = LT(1);
                match(15);
                stringTemplate2 = new StringTemplate("$_val_$");
                stringTemplate2.setAttribute("_val_", tokenLT2.getText());
                stringTemplate2.defineFormalArgument("_val_");
                stringTemplate2.setName(new StringBuffer("<").append(stringTemplate.getName()).append("'s arg ").append(tokenLT.getText()).append(" default value subtemplate>").toString());
            } else if (LA(1) == 17 && LA(2) == 18) {
                match(17);
                Token tokenLT3 = LT(1);
                match(18);
                stringTemplate2 = new StringTemplate(stringTemplate.getGroup(), tokenLT3.getText());
                stringTemplate2.setName(new StringBuffer("<").append(stringTemplate.getName()).append("'s arg ").append(tokenLT.getText()).append(" default value subtemplate>").toString());
            } else {
                if (LA(1) != 8 && LA(1) != 13) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
                stringTemplate2 = null;
            }
            stringTemplate.defineFormalArgument(tokenLT.getText(), stringTemplate2);
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_3);
        }
    }

    public final Map map() throws RecognitionException, TokenStreamException {
        HashMap map = new HashMap();
        try {
            match(19);
            mapPairs(map);
            match(20);
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_1);
        }
        return map;
    }

    public final void mapPairs(Map map) throws RecognitionException, TokenStreamException {
        try {
            int iLA = LA(1);
            if (iLA != 15) {
                if (iLA == 21) {
                    defaultValuePair(map);
                    return;
                }
                throw new NoViableAltException(LT(1), getFilename());
            }
            keyValuePair(map);
            while (LA(1) == 8 && LA(2) == 15) {
                match(8);
                keyValuePair(map);
            }
            int iLA2 = LA(1);
            if (iLA2 != 8) {
                if (iLA2 != 20) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
            } else {
                match(8);
                defaultValuePair(map);
            }
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_4);
        }
    }

    public final void keyValuePair(Map map) throws RecognitionException, TokenStreamException {
        try {
            Token tokenLT = LT(1);
            match(15);
            match(6);
            map.put(tokenLT.getText(), keyValue());
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_5);
        }
    }

    public final void defaultValuePair(Map map) throws RecognitionException, TokenStreamException {
        try {
            match(21);
            match(6);
            map.put(ASTExpr.DEFAULT_MAP_VALUE_NAME, keyValue());
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_4);
        }
    }

    public final StringTemplate keyValue() throws RecognitionException, TokenStreamException {
        StringTemplate stringTemplate;
        try {
            int iLA = LA(1);
            if (iLA == 5) {
                Token tokenLT = LT(1);
                match(5);
                if (!tokenLT.getText().equals(ASTExpr.DEFAULT_MAP_KEY_NAME)) {
                    throw new SemanticException("k.getText().equals(\"key\")");
                }
                return ASTExpr.MAP_KEY_VALUE;
            }
            if (iLA == 8 || iLA == 20) {
                return null;
            }
            if (iLA == 15) {
                Token tokenLT2 = LT(1);
                match(15);
                stringTemplate = new StringTemplate(this.group, tokenLT2.getText());
            } else if (iLA == 16) {
                Token tokenLT3 = LT(1);
                match(16);
                stringTemplate = new StringTemplate(this.group, tokenLT3.getText());
            } else {
                throw new NoViableAltException(LT(1), getFilename());
            }
            return stringTemplate;
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_5);
            return null;
        }
    }

    private static final long[] mk_tokenSet_0() {
        return new long[]{2, 0};
    }

    private static final long[] mk_tokenSet_1() {
        return new long[]{1058, 0};
    }

    private static final long[] mk_tokenSet_2() {
        return new long[]{8192, 0};
    }

    private static final long[] mk_tokenSet_3() {
        return new long[]{8448, 0};
    }

    private static final long[] mk_tokenSet_4() {
        return new long[]{1048576, 0};
    }

    private static final long[] mk_tokenSet_5() {
        return new long[]{1048832, 0};
    }
}
