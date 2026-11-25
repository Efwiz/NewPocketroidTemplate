package org.jetbrains.kotlin.com.intellij.lang;

/* JADX INFO: loaded from: classes3.dex */
public final class LanguageASTFactory extends LanguageExtension<ASTFactory> {
    public static final LanguageASTFactory INSTANCE = new LanguageASTFactory();

    private LanguageASTFactory() {
        super("org.jetbrains.kotlin.com.intellij.lang.ast.factory", ASTFactory.DefaultFactoryHolder.DEFAULT);
    }
}
