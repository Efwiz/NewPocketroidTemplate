package org.jetbrains.kotlin.kdoc.lexer;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.kdoc.parser.KDocLinkParser;
import org.jetbrains.kotlin.kdoc.parser.KDocParser;
import org.jetbrains.kotlin.kdoc.psi.impl.KDocImpl;

/* JADX INFO: loaded from: classes5.dex */
public interface KDocTokens {
    public static final KDocToken CODE_BLOCK_TEXT;
    public static final int CODE_BLOCK_TEXT_Id = 4;
    public static final TokenSet CONTENT_TOKENS;
    public static final KDocToken END;
    public static final int END_Id = 1;
    public static final ILazyParseableElementType KDOC = new ILazyParseableElementType("KDoc", KotlinLanguage.INSTANCE) { // from class: org.jetbrains.kotlin.kdoc.lexer.KDocTokens.1
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public ASTNode parseContents(ASTNode aSTNode) {
            return new KDocParser().parse(this, PsiBuilderFactory.getInstance().createBuilder(aSTNode.getTreeParent().getPsi().getProject(), aSTNode, new KDocLexer(), getLanguage(), aSTNode.getText())).getFirstChildNode();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType
        public ASTNode createNode(CharSequence charSequence) {
            return new KDocImpl(charSequence);
        }
    };
    public static final TokenSet KDOC_HIGHLIGHT_TOKENS;
    public static final KDocToken LEADING_ASTERISK;
    public static final int LEADING_ASTERISK_Id = 2;
    public static final KDocToken MARKDOWN_ESCAPED_CHAR;
    public static final int MARKDOWN_ESCAPED_CHAR_Id = 6;
    public static final KDocToken MARKDOWN_INLINE_LINK;
    public static final int MARKDOWN_INLINE_LINK_Id = 7;
    public static final ILazyParseableElementType MARKDOWN_LINK;
    public static final KDocToken START;
    public static final int START_Id = 0;
    public static final KDocToken TAG_NAME;
    public static final int TAG_NAME_Id = 5;
    public static final KDocToken TEXT;
    public static final int TEXT_Id = 3;

    static {
        KDocToken kDocToken = new KDocToken("KDOC_START", 0);
        START = kDocToken;
        KDocToken kDocToken2 = new KDocToken("KDOC_END", 1);
        END = kDocToken2;
        KDocToken kDocToken3 = new KDocToken("KDOC_LEADING_ASTERISK", 2);
        LEADING_ASTERISK = kDocToken3;
        KDocToken kDocToken4 = new KDocToken("KDOC_TEXT", 3);
        TEXT = kDocToken4;
        KDocToken kDocToken5 = new KDocToken("KDOC_CODE_BLOCK_TEXT", 4);
        CODE_BLOCK_TEXT = kDocToken5;
        KDocToken kDocToken6 = new KDocToken("KDOC_TAG_NAME", 5);
        TAG_NAME = kDocToken6;
        ILazyParseableElementType iLazyParseableElementType = new ILazyParseableElementType("KDOC_MARKDOWN_LINK", KotlinLanguage.INSTANCE) { // from class: org.jetbrains.kotlin.kdoc.lexer.KDocTokens.2
            @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
            public ASTNode parseContents(ASTNode aSTNode) {
                return KDocLinkParser.parseMarkdownLink(this, aSTNode);
            }
        };
        MARKDOWN_LINK = iLazyParseableElementType;
        KDocToken kDocToken7 = new KDocToken("KDOC_MARKDOWN_ESCAPED_CHAR", 6);
        MARKDOWN_ESCAPED_CHAR = kDocToken7;
        KDocToken kDocToken8 = new KDocToken("KDOC_MARKDOWN_INLINE_LINK", 7);
        MARKDOWN_INLINE_LINK = kDocToken8;
        KDOC_HIGHLIGHT_TOKENS = TokenSet.create(kDocToken, kDocToken2, kDocToken3, kDocToken4, kDocToken5, iLazyParseableElementType, kDocToken7, kDocToken8);
        CONTENT_TOKENS = TokenSet.create(kDocToken4, kDocToken5, kDocToken6, iLazyParseableElementType, kDocToken7, kDocToken8);
    }
}
