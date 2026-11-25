package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface CustomHighlighterTokenType {
    public static final int KEYWORD_TYPE_COUNT = 4;
    public static final IElementType KEYWORD_1 = new CustomElementType("KEYWORD_1");
    public static final IElementType KEYWORD_2 = new CustomElementType("KEYWORD_2");
    public static final IElementType KEYWORD_3 = new CustomElementType("KEYWORD_3");
    public static final IElementType KEYWORD_4 = new CustomElementType("KEYWORD_4");
    public static final IElementType STRING = new CustomElementType("STRING");
    public static final IElementType SINGLE_QUOTED_STRING = new CustomElementType("SINGLE_QUOTED_STRING");
    public static final IElementType NUMBER = new CustomElementType("NUMBER");
    public static final IElementType IDENTIFIER = new CustomElementType("IDENTIFIER");
    public static final IElementType LINE_COMMENT = new CustomElementType("LINE_COMMENT");
    public static final IElementType MULTI_LINE_COMMENT = new CustomElementType("MULTI_LINE_COMMENT");
    public static final IElementType WHITESPACE = new CustomElementType("WHITESPACE");
    public static final IElementType CHARACTER = new CustomElementType("CHARACTER");
    public static final IElementType PUNCTUATION = new CustomElementType("PUNCTUATION");
    public static final IElementType L_BRACE = new CustomElementType("L_BRACE");
    public static final IElementType R_BRACE = new CustomElementType("R_BRACE");
    public static final IElementType L_ANGLE = new CustomElementType("L_BROCKET");
    public static final IElementType R_ANGLE = new CustomElementType("R_BROCKET");
    public static final IElementType L_BRACKET = new CustomElementType("L_BRACKET");
    public static final IElementType R_BRACKET = new CustomElementType("R_BRACKET");
    public static final IElementType L_PARENTH = new CustomElementType("L_PARENTH");
    public static final IElementType R_PARENTH = new CustomElementType("R_PARENTH");
    public static final IElementType CUSTOM_CONTENT = new CustomElementType("CUSTOM_CONTENT");

    public static class CustomElementType extends IElementType {
        public CustomElementType(String str) {
            super(str, null);
        }
    }
}
