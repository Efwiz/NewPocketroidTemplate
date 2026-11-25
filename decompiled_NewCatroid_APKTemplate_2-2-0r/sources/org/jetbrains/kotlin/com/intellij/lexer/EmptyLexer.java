package org.jetbrains.kotlin.com.intellij.lexer;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class EmptyLexer extends DummyLexer {
    private static final IElementType EMPTY_TOKEN_TYPE = new IElementType("empty token", Language.ANY);

    public EmptyLexer() {
        super(EMPTY_TOKEN_TYPE);
    }
}
