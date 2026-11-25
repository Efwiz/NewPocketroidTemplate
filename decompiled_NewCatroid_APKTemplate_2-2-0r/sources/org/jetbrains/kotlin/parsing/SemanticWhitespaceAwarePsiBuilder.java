package org.jetbrains.kotlin.parsing;

import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes4.dex */
public interface SemanticWhitespaceAwarePsiBuilder extends PsiBuilder {
    void disableJoiningComplexTokens();

    void disableNewlines();

    void enableJoiningComplexTokens();

    void enableNewlines();

    boolean isWhitespaceOrComment(IElementType iElementType);

    boolean newlineBeforeCurrentToken();

    void restoreJoiningComplexTokensState();

    void restoreNewlinesState();
}
