package org.eclipse.jgit.errors;

import java.util.Locale;

/* JADX INFO: loaded from: classes13.dex */
public class TranslationStringMissingException extends TranslationBundleException {
    private static final long serialVersionUID = 1;
    private final String key;

    public TranslationStringMissingException(Class cls, Locale locale, String str, Exception exc) {
        super("Translation missing for [" + cls.getName() + ", " + locale.toString() + ", " + str + "]", cls, locale, exc);
        this.key = str;
    }

    public String getKey() {
        return this.key;
    }
}
