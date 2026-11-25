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
import org.antlr.stringtemplate.StringTemplate;

/* JADX INFO: loaded from: classes12.dex */
public class TemplateParser extends LLkParser implements TemplateParserTokenTypes {
    public static final String[] _tokenNames = {"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "LITERAL", "NEWLINE", "ACTION", "IF", "ELSEIF", "ELSE", "ENDIF", "REGION_REF", "REGION_DEF", "EXPR", "TEMPLATE", "IF_EXPR", "ESC_CHAR", "ESC", "HEX", "SUBTEMPLATE", "NESTED_PARENS", "INDENT", "COMMENT", "LINE_BREAK"};
    public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
    public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
    protected StringTemplate self;

    @Override // antlr.Parser
    public void reportError(RecognitionException recognitionException) {
        if (this.self.getGroup() == StringTemplate.defaultGroup) {
            this.self.error(new StringBuffer("template parse error; template context is ").append(this.self.getEnclosingInstanceStackString()).toString(), recognitionException);
        } else {
            this.self.error(new StringBuffer("template parse error in group ").append(this.self.getGroup().getName()).append(" line ").append(this.self.getGroupFileLine()).append("; template context is ").append(this.self.getEnclosingInstanceStackString()).toString(), recognitionException);
        }
    }

    protected TemplateParser(TokenBuffer tokenBuffer, int i) {
        super(tokenBuffer, i);
        this.tokenNames = _tokenNames;
    }

    public TemplateParser(TokenBuffer tokenBuffer) {
        this(tokenBuffer, 1);
    }

    protected TemplateParser(TokenStream tokenStream, int i) {
        super(tokenStream, i);
        this.tokenNames = _tokenNames;
    }

    public TemplateParser(TokenStream tokenStream) {
        this(tokenStream, 1);
    }

    public TemplateParser(ParserSharedInputState parserSharedInputState) {
        super(parserSharedInputState, 1);
        this.tokenNames = _tokenNames;
    }

    public final void template(StringTemplate stringTemplate) throws RecognitionException, TokenStreamException {
        this.self = stringTemplate;
        while (true) {
            try {
                int iLA = LA(1);
                if (iLA == 4) {
                    Token tokenLT = LT(1);
                    match(4);
                    stringTemplate.addChunk(new StringRef(stringTemplate, tokenLT.getText()));
                } else if (iLA == 5) {
                    Token tokenLT2 = LT(1);
                    match(5);
                    if (LA(1) != 9 && LA(1) != 10) {
                        stringTemplate.addChunk(new NewlineRef(stringTemplate, tokenLT2.getText()));
                    }
                } else if (iLA != 6 && iLA != 7 && iLA != 11 && iLA != 12) {
                    return;
                } else {
                    action(stringTemplate);
                }
            } catch (RecognitionException e) {
                reportError(e);
                recover(e, _tokenSet_0);
                return;
            }
        }
    }

    public final void action(StringTemplate stringTemplate) throws RecognitionException, TokenStreamException {
        boolean z = true;
        try {
            int iLA = LA(1);
            if (iLA == 6) {
                Token tokenLT = LT(1);
                match(6);
                String indentation = ((ChunkToken) tokenLT).getIndentation();
                ASTExpr action = stringTemplate.parseAction(tokenLT.getText());
                action.setIndentation(indentation);
                stringTemplate.addChunk(action);
                return;
            }
            String name2 = null;
            if (iLA == 7) {
                Token tokenLT2 = LT(1);
                match(7);
                ConditionalExpr conditionalExpr = (ConditionalExpr) stringTemplate.parseAction(tokenLT2.getText());
                StringTemplate stringTemplate2 = new StringTemplate(stringTemplate.getGroup(), (String) null);
                stringTemplate2.setEnclosingInstance(stringTemplate);
                stringTemplate2.setName(new StringBuffer().append(tokenLT2.getText()).append("_subtemplate").toString());
                stringTemplate.addChunk(conditionalExpr);
                template(stringTemplate2);
                if (conditionalExpr != null) {
                    conditionalExpr.setSubtemplate(stringTemplate2);
                }
                while (LA(1) == 8) {
                    Token tokenLT3 = LT(1);
                    match(8);
                    ASTExpr action2 = stringTemplate.parseAction(tokenLT3.getText());
                    StringTemplate stringTemplate3 = new StringTemplate(stringTemplate.getGroup(), (String) null);
                    stringTemplate3.setEnclosingInstance(stringTemplate);
                    stringTemplate3.setName(new StringBuffer().append(tokenLT3.getText()).append("_subtemplate").toString());
                    template(stringTemplate3);
                    if (conditionalExpr != null) {
                        conditionalExpr.addElseIfSubtemplate(action2, stringTemplate3);
                    }
                }
                int iLA2 = LA(1);
                if (iLA2 == 9) {
                    match(9);
                    StringTemplate stringTemplate4 = new StringTemplate(stringTemplate.getGroup(), (String) null);
                    stringTemplate4.setEnclosingInstance(stringTemplate);
                    stringTemplate4.setName("else_subtemplate");
                    template(stringTemplate4);
                    if (conditionalExpr != null) {
                        conditionalExpr.setElseSubtemplate(stringTemplate4);
                    }
                } else if (iLA2 != 10) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
                match(10);
                return;
            }
            boolean z2 = false;
            if (iLA != 11) {
                if (iLA != 12) {
                    throw new NoViableAltException(LT(1), getFilename());
                }
                Token tokenLT4 = LT(1);
                match(12);
                String text = tokenLT4.getText();
                int iIndexOf = text.indexOf("::=");
                if (iIndexOf < 1) {
                    stringTemplate.error("embedded region definition screwed up");
                    return;
                }
                StringTemplate stringTemplateDefineRegionTemplate = stringTemplate.getGroup().defineRegionTemplate(stringTemplate, text.substring(0, iIndexOf), text.substring(iIndexOf + 3, text.length()), 2);
                String indentation2 = ((ChunkToken) tokenLT4).getIndentation();
                ASTExpr action3 = stringTemplate.parseAction(new StringBuffer().append(stringTemplateDefineRegionTemplate.getName()).append("()").toString());
                action3.setIndentation(indentation2);
                stringTemplate.addChunk(action3);
                return;
            }
            Token tokenLT5 = LT(1);
            match(11);
            String text2 = tokenLT5.getText();
            if (text2.startsWith("super.")) {
                String strSubstring = text2.substring("super.".length(), text2.length());
                String unMangledTemplateName = stringTemplate.getGroup().getUnMangledTemplateName(stringTemplate.getName());
                StringTemplate stringTemplateLookupTemplate = stringTemplate.getGroup().lookupTemplate(unMangledTemplateName);
                if (stringTemplateLookupTemplate == null) {
                    stringTemplate.getGroup().error(new StringBuffer("reference to region within undefined template: ").append(unMangledTemplateName).toString());
                    z2 = true;
                }
                if (stringTemplateLookupTemplate.containsRegionName(strSubstring)) {
                    name2 = new StringBuffer("super.").append(stringTemplate.getGroup().getMangledRegionName(unMangledTemplateName, strSubstring)).toString();
                    z = z2;
                } else {
                    stringTemplate.getGroup().error(new StringBuffer("template ").append(unMangledTemplateName).append(" has no region called ").append(strSubstring).toString());
                }
                z2 = z;
            } else {
                name2 = stringTemplate.getGroup().defineImplicitRegionTemplate(stringTemplate, text2).getName();
            }
            if (z2) {
                return;
            }
            String indentation3 = ((ChunkToken) tokenLT5).getIndentation();
            ASTExpr action4 = stringTemplate.parseAction(new StringBuffer().append(name2).append("()").toString());
            action4.setIndentation(indentation3);
            stringTemplate.addChunk(action4);
        } catch (RecognitionException e) {
            reportError(e);
            recover(e, _tokenSet_1);
        }
    }

    private static final long[] mk_tokenSet_0() {
        return new long[]{1792, 0};
    }

    private static final long[] mk_tokenSet_1() {
        return new long[]{8176, 0};
    }
}
