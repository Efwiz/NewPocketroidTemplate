package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.tree.java.IJavaDocElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface JavaDocTokenType {
    public static final TokenSet ALL_JAVADOC_TOKENS;
    public static final IElementType DOC_COMMENT_BAD_CHARACTER;
    public static final IElementType DOC_COMMENT_DATA;
    public static final IElementType DOC_COMMENT_END;
    public static final IElementType DOC_COMMENT_LEADING_ASTERISKS;
    public static final IElementType DOC_COMMENT_START;
    public static final IElementType DOC_INLINE_TAG_END;
    public static final IElementType DOC_INLINE_TAG_START;
    public static final IElementType DOC_SPACE;
    public static final IElementType DOC_TAG_NAME;
    public static final IElementType DOC_TAG_VALUE_COMMA;
    public static final IElementType DOC_TAG_VALUE_DOT;
    public static final IElementType DOC_TAG_VALUE_GT;
    public static final IElementType DOC_TAG_VALUE_LPAREN;
    public static final IElementType DOC_TAG_VALUE_LT;
    public static final IElementType DOC_TAG_VALUE_RPAREN;
    public static final IElementType DOC_TAG_VALUE_SHARP_TOKEN;
    public static final IElementType DOC_TAG_VALUE_TOKEN;

    static {
        IJavaDocElementType iJavaDocElementType = new IJavaDocElementType("DOC_COMMENT_START");
        DOC_COMMENT_START = iJavaDocElementType;
        IJavaDocElementType iJavaDocElementType2 = new IJavaDocElementType("DOC_COMMENT_END");
        DOC_COMMENT_END = iJavaDocElementType2;
        IJavaDocElementType iJavaDocElementType3 = new IJavaDocElementType("DOC_COMMENT_DATA");
        DOC_COMMENT_DATA = iJavaDocElementType3;
        IJavaDocElementType iJavaDocElementType4 = new IJavaDocElementType("DOC_SPACE");
        DOC_SPACE = iJavaDocElementType4;
        IJavaDocElementType iJavaDocElementType5 = new IJavaDocElementType("DOC_COMMENT_LEADING_ASTERISKS");
        DOC_COMMENT_LEADING_ASTERISKS = iJavaDocElementType5;
        IJavaDocElementType iJavaDocElementType6 = new IJavaDocElementType("DOC_TAG_NAME");
        DOC_TAG_NAME = iJavaDocElementType6;
        IJavaDocElementType iJavaDocElementType7 = new IJavaDocElementType("DOC_INLINE_TAG_START");
        DOC_INLINE_TAG_START = iJavaDocElementType7;
        IJavaDocElementType iJavaDocElementType8 = new IJavaDocElementType("DOC_INLINE_TAG_END");
        DOC_INLINE_TAG_END = iJavaDocElementType8;
        IJavaDocElementType iJavaDocElementType9 = new IJavaDocElementType("DOC_TAG_VALUE_TOKEN");
        DOC_TAG_VALUE_TOKEN = iJavaDocElementType9;
        IJavaDocElementType iJavaDocElementType10 = new IJavaDocElementType("DOC_TAG_VALUE_DOT");
        DOC_TAG_VALUE_DOT = iJavaDocElementType10;
        IJavaDocElementType iJavaDocElementType11 = new IJavaDocElementType("DOC_TAG_VALUE_COMMA");
        DOC_TAG_VALUE_COMMA = iJavaDocElementType11;
        IJavaDocElementType iJavaDocElementType12 = new IJavaDocElementType("DOC_TAG_VALUE_LPAREN");
        DOC_TAG_VALUE_LPAREN = iJavaDocElementType12;
        IJavaDocElementType iJavaDocElementType13 = new IJavaDocElementType("DOC_TAG_VALUE_RPAREN");
        DOC_TAG_VALUE_RPAREN = iJavaDocElementType13;
        DOC_TAG_VALUE_LT = new IJavaDocElementType("DOC_TAG_VALUE_LT");
        DOC_TAG_VALUE_GT = new IJavaDocElementType("DOC_TAG_VALUE_GT");
        IJavaDocElementType iJavaDocElementType14 = new IJavaDocElementType("DOC_TAG_VALUE_SHARP_TOKEN");
        DOC_TAG_VALUE_SHARP_TOKEN = iJavaDocElementType14;
        DOC_COMMENT_BAD_CHARACTER = new IJavaDocElementType("DOC_COMMENT_BAD_CHARACTER");
        ALL_JAVADOC_TOKENS = TokenSet.create(iJavaDocElementType, iJavaDocElementType2, iJavaDocElementType3, iJavaDocElementType4, iJavaDocElementType5, iJavaDocElementType6, iJavaDocElementType7, iJavaDocElementType8, iJavaDocElementType9, iJavaDocElementType10, iJavaDocElementType11, iJavaDocElementType12, iJavaDocElementType13, iJavaDocElementType14);
    }
}
