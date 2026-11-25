package org.jetbrains.kotlin.com.intellij.lang;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface WhitespacesAndCommentsBinder {

    public interface RecursiveBinder extends WhitespacesAndCommentsBinder {
    }

    public interface TokenTextGetter {
        CharSequence get(int i);
    }

    int getEdgePosition(List<? extends IElementType> list, boolean z, TokenTextGetter tokenTextGetter);
}
