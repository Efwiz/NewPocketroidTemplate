package org.jetbrains.kotlin.com.intellij.openapi.fileTypes;

import org.catrobat.catroid.ui.SpriteActivity;
import org.jetbrains.kotlin.com.intellij.lang.Language;

/* JADX INFO: loaded from: classes3.dex */
public final class PlainTextLanguage extends Language {
    public static final PlainTextLanguage INSTANCE = new PlainTextLanguage();

    private PlainTextLanguage() {
        super(SpriteActivity.EXTRA_TEXT, "text/plain");
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.Language
    public String getDisplayName() {
        return "Plain text";
    }
}
