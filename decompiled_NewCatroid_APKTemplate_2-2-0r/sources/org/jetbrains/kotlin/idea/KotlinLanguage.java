package org.jetbrains.kotlin.idea;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;

/* JADX INFO: loaded from: classes4.dex */
public class KotlinLanguage extends Language {
    public static final KotlinLanguage INSTANCE = new KotlinLanguage();
    public static final String NAME = "Kotlin";

    @Override // org.jetbrains.kotlin.com.intellij.lang.Language
    public boolean isCaseSensitive() {
        return true;
    }

    private KotlinLanguage() {
        super(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.Language
    public String getDisplayName() {
        return "Kotlin";
    }
}
