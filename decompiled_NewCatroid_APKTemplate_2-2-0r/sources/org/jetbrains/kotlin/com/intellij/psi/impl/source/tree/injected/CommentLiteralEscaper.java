package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.injected;

import org.jetbrains.kotlin.com.intellij.lang.CodeDocumentationAwareCommenter;
import org.jetbrains.kotlin.com.intellij.lang.Commenter;
import org.jetbrains.kotlin.com.intellij.lang.LanguageCommenters;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.PsiCommentImpl;

/* JADX INFO: loaded from: classes3.dex */
public class CommentLiteralEscaper extends LiteralTextEscaper<PsiCommentImpl> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "rangeInsideHost";
        } else {
            objArr[0] = "outChars";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/injected/CommentLiteralEscaper";
        if (i != 2) {
            objArr[2] = "decode";
        } else {
            objArr[2] = "getOffsetInHost";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public CommentLiteralEscaper(PsiCommentImpl psiCommentImpl) {
        super(psiCommentImpl);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper
    public boolean decode(TextRange textRange, StringBuilder sb) {
        if (textRange == null) {
            $$$reportNull$$$0(0);
        }
        if (sb == null) {
            $$$reportNull$$$0(1);
        }
        sb.append((CharSequence) ((PsiCommentImpl) this.myHost).getText(), textRange.getStartOffset(), textRange.getEndOffset());
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper
    public int getOffsetInHost(int i, TextRange textRange) {
        if (textRange == null) {
            $$$reportNull$$$0(2);
        }
        int startOffset = i + textRange.getStartOffset();
        if (startOffset < textRange.getStartOffset()) {
            startOffset = textRange.getStartOffset();
        }
        return startOffset > textRange.getEndOffset() ? textRange.getEndOffset() : startOffset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper
    public boolean isOneLine() {
        Commenter commenterForLanguage = LanguageCommenters.INSTANCE.forLanguage(((PsiCommentImpl) this.myHost).getLanguage());
        return (commenterForLanguage instanceof CodeDocumentationAwareCommenter) && ((PsiCommentImpl) this.myHost).getTokenType() == ((CodeDocumentationAwareCommenter) commenterForLanguage).getLineCommentTokenType();
    }
}
