package org.jetbrains.kotlin.parsing;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.WhitespacesAndCommentsBinder;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: KotlinWhitespaceAndCommentsBinders.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/parsing/PrecedingDocCommentsBinder;", "Lorg/jetbrains/kotlin/com/intellij/lang/WhitespacesAndCommentsBinder;", "()V", "getEdgePosition", "", "tokens", "", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "atStreamEdge", "", "getter", "Lorg/jetbrains/kotlin/com/intellij/lang/WhitespacesAndCommentsBinder$TokenTextGetter;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrecedingDocCommentsBinder implements WhitespacesAndCommentsBinder {
    public static final PrecedingDocCommentsBinder INSTANCE = new PrecedingDocCommentsBinder();

    private PrecedingDocCommentsBinder() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.WhitespacesAndCommentsBinder
    public int getEdgePosition(List<? extends IElementType> tokens, boolean atStreamEdge, WhitespacesAndCommentsBinder.TokenTextGetter getter) {
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        Intrinsics.checkNotNullParameter(getter, "getter");
        if (tokens.isEmpty()) {
            return 0;
        }
        int size = tokens.size() - 1;
        if (size >= 0) {
            while (true) {
                int i = size - 1;
                if (Intrinsics.areEqual(tokens.get(size), KtTokens.DOC_COMMENT)) {
                    return size;
                }
                if (i >= 0) {
                    size = i;
                }
            }
        }
        return tokens.size();
    }
}
