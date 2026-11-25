package org.jetbrains.kotlin.parsing;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.WhitespacesAndCommentsBinder;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: compiled from: KotlinWhitespaceAndCommentsBinders.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J&\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/parsing/BindAll;", "Lorg/jetbrains/kotlin/com/intellij/lang/WhitespacesAndCommentsBinder;", "isTrailing", "", "(Z)V", "()Z", "getEdgePosition", "", "tokens", "", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "atStreamEdge", "getter", "Lorg/jetbrains/kotlin/com/intellij/lang/WhitespacesAndCommentsBinder$TokenTextGetter;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BindAll implements WhitespacesAndCommentsBinder {
    private final boolean isTrailing;

    public BindAll(boolean z) {
        this.isTrailing = z;
    }

    /* JADX INFO: renamed from: isTrailing, reason: from getter */
    public final boolean getIsTrailing() {
        return this.isTrailing;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.WhitespacesAndCommentsBinder
    public int getEdgePosition(List<? extends IElementType> tokens, boolean atStreamEdge, WhitespacesAndCommentsBinder.TokenTextGetter getter) {
        Intrinsics.checkNotNullParameter(tokens, "tokens");
        Intrinsics.checkNotNullParameter(getter, "getter");
        if (this.isTrailing) {
            return tokens.size();
        }
        return 0;
    }
}
