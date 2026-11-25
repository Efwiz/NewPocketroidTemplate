package org.jetbrains.kotlin.parsing;

/* JADX INFO: loaded from: classes4.dex */
public interface TokenStreamPredicate {
    boolean matching(boolean z);

    TokenStreamPredicate or(TokenStreamPredicate tokenStreamPredicate);
}
