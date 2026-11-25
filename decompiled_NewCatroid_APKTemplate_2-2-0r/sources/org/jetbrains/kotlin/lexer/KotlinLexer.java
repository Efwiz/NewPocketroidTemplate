package org.jetbrains.kotlin.lexer;

import org.jetbrains.kotlin.com.intellij.lexer.FlexAdapter;

/* JADX INFO: loaded from: classes5.dex */
public class KotlinLexer extends FlexAdapter {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinLexer() {
        super(new _JetLexer(null));
    }
}
