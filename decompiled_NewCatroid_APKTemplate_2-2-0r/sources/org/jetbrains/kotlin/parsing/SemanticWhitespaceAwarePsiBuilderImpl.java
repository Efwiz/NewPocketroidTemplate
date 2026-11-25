package org.jetbrains.kotlin.parsing;

import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderAdapter;
import org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderImpl;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: loaded from: classes4.dex */
public class SemanticWhitespaceAwarePsiBuilderImpl extends PsiBuilderAdapter implements SemanticWhitespaceAwarePsiBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final TokenSet complexTokens;
    private final PsiBuilderImpl delegateImpl;
    private final Stack<Boolean> joinComplexTokens;
    private final Stack<Boolean> newlinesEnabled;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "elementType", "org/jetbrains/kotlin/parsing/SemanticWhitespaceAwarePsiBuilderImpl", "isWhitespaceOrComment"));
    }

    public SemanticWhitespaceAwarePsiBuilderImpl(PsiBuilder psiBuilder) {
        super(psiBuilder);
        this.complexTokens = TokenSet.create(KtTokens.SAFE_ACCESS, KtTokens.ELVIS, KtTokens.EXCLEXCL);
        Stack<Boolean> stack = new Stack<>();
        this.joinComplexTokens = stack;
        Stack<Boolean> stack2 = new Stack<>();
        this.newlinesEnabled = stack2;
        stack2.push(true);
        stack.push(true);
        this.delegateImpl = findPsiBuilderImpl(psiBuilder);
    }

    private static PsiBuilderImpl findPsiBuilderImpl(PsiBuilder psiBuilder) {
        while (!(psiBuilder instanceof PsiBuilderImpl)) {
            if (!(psiBuilder instanceof PsiBuilderAdapter)) {
                return null;
            }
            psiBuilder = ((PsiBuilderAdapter) psiBuilder).getDelegate();
        }
        return (PsiBuilderImpl) psiBuilder;
    }

    @Override // org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilder
    public boolean isWhitespaceOrComment(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        return this.delegateImpl.whitespaceOrComment(iElementType);
    }

    @Override // org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilder
    public boolean newlineBeforeCurrentToken() {
        if (!this.newlinesEnabled.peek().booleanValue()) {
            return false;
        }
        if (eof()) {
            return true;
        }
        for (int i = 1; i <= getCurrentOffset(); i++) {
            int i2 = -i;
            IElementType iElementTypeRawLookup = rawLookup(i2);
            if (iElementTypeRawLookup != KtTokens.BLOCK_COMMENT && iElementTypeRawLookup != KtTokens.DOC_COMMENT && iElementTypeRawLookup != KtTokens.EOL_COMMENT && iElementTypeRawLookup != KtTokens.SHEBANG_COMMENT) {
                if (iElementTypeRawLookup != TokenType.WHITE_SPACE) {
                    break;
                }
                int iRawTokenTypeStart = rawTokenTypeStart(i2 + 1);
                for (int iRawTokenTypeStart2 = rawTokenTypeStart(i2); iRawTokenTypeStart2 < iRawTokenTypeStart; iRawTokenTypeStart2++) {
                    if (getOriginalText().charAt(iRawTokenTypeStart2) == '\n') {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilder
    public void disableNewlines() {
        this.newlinesEnabled.push(false);
    }

    @Override // org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilder
    public void enableNewlines() {
        this.newlinesEnabled.push(true);
    }

    @Override // org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilder
    public void restoreNewlinesState() {
        this.newlinesEnabled.pop();
    }

    private boolean joinComplexTokens() {
        return this.joinComplexTokens.peek().booleanValue();
    }

    @Override // org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilder
    public void restoreJoiningComplexTokensState() {
        this.joinComplexTokens.pop();
    }

    @Override // org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilder
    public void enableJoiningComplexTokens() {
        this.joinComplexTokens.push(true);
    }

    @Override // org.jetbrains.kotlin.parsing.SemanticWhitespaceAwarePsiBuilder
    public void disableJoiningComplexTokens() {
        this.joinComplexTokens.push(false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderAdapter, org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public IElementType getTokenType() {
        return !joinComplexTokens() ? super.getTokenType() : getJoinedTokenType(super.getTokenType(), 1);
    }

    private IElementType getJoinedTokenType(IElementType iElementType, int i) {
        if (iElementType != KtTokens.QUEST) {
            return (iElementType == KtTokens.EXCL && rawLookup(i) == KtTokens.EXCL) ? KtTokens.EXCLEXCL : iElementType;
        }
        IElementType iElementTypeRawLookup = rawLookup(i);
        if (iElementTypeRawLookup == KtTokens.DOT) {
            return KtTokens.SAFE_ACCESS;
        }
        return iElementTypeRawLookup == KtTokens.COLON ? KtTokens.ELVIS : iElementType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderAdapter, org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public void advanceLexer() {
        if (!joinComplexTokens()) {
            super.advanceLexer();
            return;
        }
        IElementType tokenType = getTokenType();
        if (this.complexTokens.contains(tokenType)) {
            PsiBuilder.Marker markerMark = m6463mark();
            super.advanceLexer();
            super.advanceLexer();
            markerMark.collapse(tokenType);
            return;
        }
        super.advanceLexer();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderAdapter, org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public String getTokenText() {
        if (!joinComplexTokens()) {
            return super.getTokenText();
        }
        IElementType tokenType = getTokenType();
        if (this.complexTokens.contains(tokenType)) {
            if (tokenType == KtTokens.ELVIS) {
                return "?:";
            }
            if (tokenType == KtTokens.SAFE_ACCESS) {
                return "?.";
            }
        }
        return super.getTokenText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderAdapter, org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public IElementType lookAhead(int i) {
        if (!joinComplexTokens()) {
            return super.lookAhead(i);
        }
        if (this.complexTokens.contains(getTokenType())) {
            return super.lookAhead(i + 1);
        }
        return getJoinedTokenType(super.lookAhead(i), 2);
    }
}
