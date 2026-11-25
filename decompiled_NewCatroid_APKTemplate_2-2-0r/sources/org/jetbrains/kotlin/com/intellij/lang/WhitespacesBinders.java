package org.jetbrains.kotlin.com.intellij.lang;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public final class WhitespacesBinders {
    public static final WhitespacesAndCommentsBinder DEFAULT_LEFT_BINDER;
    public static final WhitespacesAndCommentsBinder DEFAULT_RIGHT_BINDER;
    public static final WhitespacesAndCommentsBinder GREEDY_LEFT_BINDER;
    public static final WhitespacesAndCommentsBinder GREEDY_RIGHT_BINDER;

    static {
        WhitespacesAndCommentsBinder whitespacesAndCommentsBinder = new WhitespacesAndCommentsBinder() { // from class: org.jetbrains.kotlin.com.intellij.lang.WhitespacesBinders.1
            @Override // org.jetbrains.kotlin.com.intellij.lang.WhitespacesAndCommentsBinder
            public int getEdgePosition(List<? extends IElementType> list, boolean z, WhitespacesAndCommentsBinder.TokenTextGetter tokenTextGetter) {
                return list.size();
            }
        };
        DEFAULT_LEFT_BINDER = whitespacesAndCommentsBinder;
        WhitespacesAndCommentsBinder whitespacesAndCommentsBinder2 = new WhitespacesAndCommentsBinder() { // from class: org.jetbrains.kotlin.com.intellij.lang.WhitespacesBinders.2
            @Override // org.jetbrains.kotlin.com.intellij.lang.WhitespacesAndCommentsBinder
            public int getEdgePosition(List<? extends IElementType> list, boolean z, WhitespacesAndCommentsBinder.TokenTextGetter tokenTextGetter) {
                return 0;
            }
        };
        DEFAULT_RIGHT_BINDER = whitespacesAndCommentsBinder2;
        GREEDY_LEFT_BINDER = whitespacesAndCommentsBinder2;
        GREEDY_RIGHT_BINDER = whitespacesAndCommentsBinder;
    }
}
