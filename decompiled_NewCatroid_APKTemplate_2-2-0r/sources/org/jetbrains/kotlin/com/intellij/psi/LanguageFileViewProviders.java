package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.LanguageExtension;

/* JADX INFO: loaded from: classes3.dex */
public final class LanguageFileViewProviders extends LanguageExtension<FileViewProviderFactory> {
    public static final LanguageFileViewProviders INSTANCE = new LanguageFileViewProviders();

    private LanguageFileViewProviders() {
        super("org.jetbrains.kotlin.com.intellij.lang.fileViewProviderFactory");
    }
}
