package org.jetbrains.kotlin.com.intellij.lang.java;

import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.com.intellij.lang.Language;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaLanguage extends Language {
    public static final JavaLanguage INSTANCE = new JavaLanguage();

    @Override // org.jetbrains.kotlin.com.intellij.lang.Language
    public boolean isCaseSensitive() {
        return true;
    }

    private JavaLanguage() {
        super(SuffixConstants.EXTENSION_JAVA, "text/x-java-source", "text/java", "application/x-java", "text/x-java");
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.Language
    public String getDisplayName() {
        return "Java";
    }
}
