package org.jetbrains.kotlin.com.intellij.lexer;

import org.jetbrains.kotlin.com.intellij.psi.JavaDocTokenType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaDocTokenTypes implements DocCommentTokenTypes {
    public static final DocCommentTokenTypes INSTANCE = new JavaDocTokenTypes();
    private final TokenSet mySpaceCommentsSet = TokenSet.create(JavaDocTokenType.DOC_SPACE, JavaDocTokenType.DOC_COMMENT_DATA);

    private JavaDocTokenTypes() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType commentStart() {
        return JavaDocTokenType.DOC_COMMENT_START;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType commentEnd() {
        return JavaDocTokenType.DOC_COMMENT_END;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType commentData() {
        return JavaDocTokenType.DOC_COMMENT_DATA;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public TokenSet spaceCommentsTokenSet() {
        return this.mySpaceCommentsSet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType space() {
        return JavaDocTokenType.DOC_SPACE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType tagValueToken() {
        return JavaDocTokenType.DOC_TAG_VALUE_TOKEN;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType tagValueLParen() {
        return JavaDocTokenType.DOC_TAG_VALUE_LPAREN;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType tagValueRParen() {
        return JavaDocTokenType.DOC_TAG_VALUE_RPAREN;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType tagValueSharp() {
        return JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType tagValueComma() {
        return JavaDocTokenType.DOC_TAG_VALUE_COMMA;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType tagName() {
        return JavaDocTokenType.DOC_TAG_NAME;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType tagValueLT() {
        return JavaDocTokenType.DOC_TAG_VALUE_LT;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType tagValueGT() {
        return JavaDocTokenType.DOC_TAG_VALUE_GT;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType inlineTagStart() {
        return JavaDocTokenType.DOC_INLINE_TAG_START;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType inlineTagEnd() {
        return JavaDocTokenType.DOC_INLINE_TAG_END;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType badCharacter() {
        return JavaDocTokenType.DOC_COMMENT_BAD_CHARACTER;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes
    public IElementType commentLeadingAsterisks() {
        return JavaDocTokenType.DOC_COMMENT_LEADING_ASTERISKS;
    }
}
