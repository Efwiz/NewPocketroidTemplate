package org.jetbrains.kotlin.parsing;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes4.dex */
public class TruncatedSemanticWhitespaceAwarePsiBuilder extends SemanticWhitespaceAwarePsiBuilderAdapter {
    private final int myEOFPosition;

    public TruncatedSemanticWhitespaceAwarePsiBuilder(SemanticWhitespaceAwarePsiBuilder semanticWhitespaceAwarePsiBuilder, int i) {
        super(semanticWhitespaceAwarePsiBuilder);
        this.myEOFPosition = i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderAdapter, org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public boolean eof() {
        return super.eof() || isOffsetBeyondEof(getCurrentOffset());
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderAdapter, org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public String getTokenText() {
        if (eof()) {
            return null;
        }
        return super.getTokenText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderAdapter, org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public IElementType getTokenType() {
        if (eof()) {
            return null;
        }
        return super.getTokenType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderAdapter, org.jetbrains.kotlin.com.intellij.lang.SyntaxTreeBuilder
    public IElementType lookAhead(int i) {
        if (eof()) {
            return null;
        }
        int iRawLookAhead = rawLookAhead(i);
        if (isOffsetBeyondEof(rawTokenTypeStart(iRawLookAhead))) {
            return null;
        }
        return super.rawLookup(iRawLookAhead);
    }

    private int rawLookAhead(int i) {
        int i2 = 0;
        while (i > 0) {
            i2++;
            IElementType iElementTypeRawLookup = rawLookup(i2);
            while (iElementTypeRawLookup != null && isWhitespaceOrComment(iElementTypeRawLookup)) {
                i2++;
                iElementTypeRawLookup = rawLookup(i2);
            }
            i--;
        }
        return i2;
    }

    private boolean isOffsetBeyondEof(int i) {
        int i2 = this.myEOFPosition;
        return i2 >= 0 && i >= i2;
    }
}
