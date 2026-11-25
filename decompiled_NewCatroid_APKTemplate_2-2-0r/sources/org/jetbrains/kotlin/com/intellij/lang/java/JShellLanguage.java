package org.jetbrains.kotlin.com.intellij.lang.java;

import org.jetbrains.kotlin.com.intellij.lang.Language;

/* JADX INFO: loaded from: classes3.dex */
public final class JShellLanguage extends Language {
    public static final JShellLanguage INSTANCE = new JShellLanguage();

    private JShellLanguage() {
        super(JavaLanguage.INSTANCE, "JShellLanguage", new String[0]);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.Language
    public String getDisplayName() {
        return "JShell Snippet";
    }
}
