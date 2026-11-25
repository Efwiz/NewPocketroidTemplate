package org.jetbrains.kotlin.parsing;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.lexer.KtKeywordToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.utils.strings.StringsKt;

/* JADX INFO: loaded from: classes4.dex */
abstract class AbstractKotlinParsing {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Map<String, KtKeywordToken> SOFT_KEYWORD_TEXTS = new HashMap();
    protected final boolean isLazy;
    protected final SemanticWhitespaceAwarePsiBuilder myBuilder;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "marker";
        } else {
            objArr[0] = "elementType";
        }
        objArr[1] = "org/jetbrains/kotlin/parsing/AbstractKotlinParsing";
        objArr[2] = "closeDeclarationWithCommentBinders";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected abstract KotlinParsing create(SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder);

    static {
        for (IElementType iElementType : KtTokens.SOFT_KEYWORDS.getTypes()) {
            KtKeywordToken ktKeywordToken = (KtKeywordToken) iElementType;
            SOFT_KEYWORD_TEXTS.put(ktKeywordToken.getValue(), ktKeywordToken);
        }
        for (IElementType iElementType2 : KtTokens.KEYWORDS.getTypes()) {
        }
    }

    public AbstractKotlinParsing(SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder) {
        this(semanticWhitespaceAwarePsiBuilder, true);
    }

    public AbstractKotlinParsing(SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder, boolean z) {
        this.myBuilder = semanticWhitespaceAwarePsiBuilder;
        this.isLazy = z;
    }

    protected IElementType getLastToken() {
        int currentOffset = this.myBuilder.getCurrentOffset();
        int i = 1;
        while (i <= currentOffset && KtTokens.WHITE_SPACE_OR_COMMENT_BIT_SET.contains(this.myBuilder.rawLookup(-i))) {
            i++;
        }
        return this.myBuilder.rawLookup(-i);
    }

    protected boolean expect(KtToken ktToken, String str) {
        return expect(ktToken, str, null);
    }

    protected PsiBuilder.Marker mark() {
        return this.myBuilder.m6463mark();
    }

    protected void error(String str) {
        this.myBuilder.error(str);
    }

    protected boolean expect(KtToken ktToken, String str, TokenSet tokenSet) {
        if (expect(ktToken)) {
            return true;
        }
        errorWithRecovery(str, tokenSet);
        return false;
    }

    protected boolean expect(KtToken ktToken) {
        if (at(ktToken)) {
            advance();
            return true;
        }
        if (ktToken != KtTokens.IDENTIFIER || !"`".equals(this.myBuilder.getTokenText())) {
            return false;
        }
        advance();
        return false;
    }

    protected void expectNoAdvance(KtToken ktToken, String str) {
        if (at(ktToken)) {
            advance();
        } else {
            error(str);
        }
    }

    protected void errorWithRecovery(String str, TokenSet tokenSet) {
        IElementType iElementTypeTt = tt();
        if (tokenSet == null || tokenSet.contains(iElementTypeTt) || iElementTypeTt == KtTokens.LBRACE || iElementTypeTt == KtTokens.RBRACE || (tokenSet.contains(KtTokens.EOL_OR_SEMICOLON) && (eof() || iElementTypeTt == KtTokens.SEMICOLON || this.myBuilder.newlineBeforeCurrentToken()))) {
            error(str);
        } else {
            errorAndAdvance(str);
        }
    }

    protected void errorAndAdvance(String str) {
        errorAndAdvance(str, 1);
    }

    protected void errorAndAdvance(String str, int i) {
        PsiBuilder.Marker markerMark = mark();
        advance(i);
        markerMark.error(str);
    }

    protected boolean eof() {
        return this.myBuilder.eof();
    }

    protected void advance() {
        this.myBuilder.advanceLexer();
    }

    protected void advance(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            advance();
        }
    }

    protected void advanceAt(IElementType iElementType) {
        this.myBuilder.advanceLexer();
    }

    protected int getTokenId() {
        IElementType iElementTypeTt = tt();
        if (iElementTypeTt instanceof KtToken) {
            return ((KtToken) iElementTypeTt).tokenId;
        }
        return 0;
    }

    protected IElementType tt() {
        return this.myBuilder.getTokenType();
    }

    protected boolean _at(IElementType iElementType) {
        return tokenMatches(tt(), iElementType);
    }

    private boolean tokenMatches(IElementType iElementType, IElementType iElementType2) {
        if (iElementType == iElementType2) {
            return true;
        }
        if (iElementType2 == KtTokens.EOL_OR_SEMICOLON) {
            return eof() || iElementType == KtTokens.SEMICOLON || this.myBuilder.newlineBeforeCurrentToken();
        }
        return false;
    }

    protected boolean at(IElementType iElementType) {
        if (_at(iElementType)) {
            return true;
        }
        IElementType iElementTypeTt = tt();
        if (iElementTypeTt == KtTokens.IDENTIFIER && (iElementType instanceof KtKeywordToken)) {
            KtKeywordToken ktKeywordToken = (KtKeywordToken) iElementType;
            if (ktKeywordToken.isSoft() && ktKeywordToken.getValue().equals(this.myBuilder.getTokenText())) {
                this.myBuilder.remapCurrentToken(iElementType);
                return true;
            }
        }
        if (iElementType != KtTokens.IDENTIFIER || !(iElementTypeTt instanceof KtKeywordToken) || !((KtKeywordToken) iElementTypeTt).isSoft()) {
            return false;
        }
        this.myBuilder.remapCurrentToken(KtTokens.IDENTIFIER);
        return true;
    }

    protected boolean _atSet(TokenSet tokenSet) {
        IElementType iElementTypeTt = tt();
        if (tokenSet.contains(iElementTypeTt)) {
            return true;
        }
        if (tokenSet.contains(KtTokens.EOL_OR_SEMICOLON)) {
            return eof() || iElementTypeTt == KtTokens.SEMICOLON || this.myBuilder.newlineBeforeCurrentToken();
        }
        return false;
    }

    protected boolean atSet(TokenSet tokenSet) {
        if (_atSet(tokenSet)) {
            return true;
        }
        IElementType iElementTypeTt = tt();
        if (iElementTypeTt == KtTokens.IDENTIFIER) {
            KtKeywordToken ktKeywordToken = SOFT_KEYWORD_TEXTS.get(this.myBuilder.getTokenText());
            if (ktKeywordToken == null || !tokenSet.contains(ktKeywordToken)) {
                return false;
            }
            this.myBuilder.remapCurrentToken(ktKeywordToken);
            return true;
        }
        if (!tokenSet.contains(KtTokens.IDENTIFIER) || !(iElementTypeTt instanceof KtKeywordToken) || !((KtKeywordToken) iElementTypeTt).isSoft()) {
            return false;
        }
        this.myBuilder.remapCurrentToken(KtTokens.IDENTIFIER);
        return true;
    }

    protected IElementType lookahead(int i) {
        return this.myBuilder.lookAhead(i);
    }

    protected boolean consumeIf(KtToken ktToken) {
        if (!at(ktToken)) {
            return false;
        }
        advance();
        return true;
    }

    protected void skipUntil(TokenSet tokenSet) {
        boolean zContains = tokenSet.contains(KtTokens.EOL_OR_SEMICOLON);
        while (!eof() && !tokenSet.contains(tt())) {
            if (zContains && at(KtTokens.EOL_OR_SEMICOLON)) {
                return;
            } else {
                advance();
            }
        }
    }

    protected void errorUntil(String str, TokenSet tokenSet) {
        PsiBuilder.Marker markerMark = mark();
        skipUntil(tokenSet);
        markerMark.error(str);
    }

    protected static void errorIf(PsiBuilder.Marker marker, boolean z, String str) {
        if (z) {
            marker.error(str);
        } else {
            marker.drop();
        }
    }

    protected class OptionalMarker {
        private final PsiBuilder.Marker marker;
        private final int offset;

        public OptionalMarker(boolean z) {
            this.marker = z ? AbstractKotlinParsing.this.mark() : null;
            this.offset = AbstractKotlinParsing.this.myBuilder.getCurrentOffset();
        }

        public void done(IElementType iElementType) {
            PsiBuilder.Marker marker = this.marker;
            if (marker == null) {
                return;
            }
            marker.done(iElementType);
        }

        public void error(String str) {
            if (this.marker == null) {
                return;
            }
            if (this.offset == AbstractKotlinParsing.this.myBuilder.getCurrentOffset()) {
                this.marker.drop();
            } else {
                this.marker.error(str);
            }
        }

        public void drop() {
            PsiBuilder.Marker marker = this.marker;
            if (marker == null) {
                return;
            }
            marker.drop();
        }
    }

    protected int matchTokenStreamPredicate(TokenStreamPattern tokenStreamPattern) {
        PsiBuilder.Marker markerMark = mark();
        Stack stack = new Stack();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (!eof() && !tokenStreamPattern.processToken(this.myBuilder.getCurrentOffset(), tokenStreamPattern.isTopLevel(i, i2, i3, i4))) {
            int tokenId = getTokenId();
            if (tokenId == 63) {
                i++;
                stack.push(KtTokens.LT);
            } else if (tokenId != 64) {
                switch (tokenId) {
                    case 48:
                        i2++;
                        stack.push(KtTokens.LBRACKET);
                        break;
                    case 49:
                        i2--;
                        break;
                    case 50:
                        i3++;
                        stack.push(KtTokens.LBRACE);
                        break;
                    case 51:
                        i3--;
                        break;
                    case 52:
                        i4++;
                        stack.push(KtTokens.LPAR);
                        break;
                    case 53:
                        i4--;
                        if (stack.isEmpty() || stack.pop() != KtTokens.LPAR) {
                            tokenStreamPattern.handleUnmatchedClosing(KtTokens.RPAR);
                        }
                        break;
                }
            } else {
                i--;
            }
            advance();
        }
        markerMark.rollbackTo();
        return tokenStreamPattern.result();
    }

    protected boolean eol() {
        return this.myBuilder.newlineBeforeCurrentToken() || eof();
    }

    protected static void closeDeclarationWithCommentBinders(PsiBuilder.Marker marker, IElementType iElementType, boolean z) {
        if (marker == null) {
            $$$reportNull$$$0(0);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(1);
        }
        marker.done(iElementType);
        marker.setCustomEdgeTokenBinders(z ? PrecedingCommentsBinder.INSTANCE : PrecedingDocCommentsBinder.INSTANCE, TrailingCommentsBinder.INSTANCE);
    }

    protected KotlinParsing createTruncatedBuilder(int i) {
        return create(new TruncatedSemanticWhitespaceAwarePsiBuilder(this.myBuilder, i));
    }

    protected class At extends AbstractTokenStreamPredicate {
        private final IElementType lookFor;
        private final boolean topLevelOnly;

        public At(IElementType iElementType, boolean z) {
            this.lookFor = iElementType;
            this.topLevelOnly = z;
        }

        public At(AbstractKotlinParsing abstractKotlinParsing, IElementType iElementType) {
            this(iElementType, true);
        }

        @Override // org.jetbrains.kotlin.parsing.TokenStreamPredicate
        public boolean matching(boolean z) {
            return (z || !this.topLevelOnly) && AbstractKotlinParsing.this.at(this.lookFor);
        }
    }

    protected class AtSet extends AbstractTokenStreamPredicate {
        private final TokenSet lookFor;
        private final TokenSet topLevelOnly;

        public AtSet(TokenSet tokenSet, TokenSet tokenSet2) {
            this.lookFor = tokenSet;
            this.topLevelOnly = tokenSet2;
        }

        public AtSet(AbstractKotlinParsing abstractKotlinParsing, TokenSet tokenSet) {
            this(tokenSet, tokenSet);
        }

        @Override // org.jetbrains.kotlin.parsing.TokenStreamPredicate
        public boolean matching(boolean z) {
            return (z || !AbstractKotlinParsing.this.atSet(this.topLevelOnly)) && AbstractKotlinParsing.this.atSet(this.lookFor);
        }
    }

    public String currentContext() {
        return StringsKt.substringWithContext(this.myBuilder.getOriginalText(), this.myBuilder.getCurrentOffset(), this.myBuilder.getCurrentOffset(), 20);
    }
}
