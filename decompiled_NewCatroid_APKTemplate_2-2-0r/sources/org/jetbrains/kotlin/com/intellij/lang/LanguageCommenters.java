package org.jetbrains.kotlin.com.intellij.lang;

/* JADX INFO: loaded from: classes3.dex */
public final class LanguageCommenters extends LanguageExtension<Commenter> {
    public static final LanguageCommenters INSTANCE = new LanguageCommenters();

    private LanguageCommenters() {
        super("org.jetbrains.kotlin.com.intellij.lang.commenter");
    }
}
