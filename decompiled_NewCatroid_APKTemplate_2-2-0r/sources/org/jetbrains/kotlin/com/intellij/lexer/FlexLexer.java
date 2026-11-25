package org.jetbrains.kotlin.com.intellij.lexer;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface FlexLexer {
    IElementType advance() throws IOException;

    int getTokenEnd();

    void reset(CharSequence charSequence, int i, int i2, int i3);

    int yystate();
}
