package org.eclipse.jgit.errors;

import java.util.Locale;

/* JADX INFO: loaded from: classes13.dex */
public class TranslationBundleLoadingException extends TranslationBundleException {
    private static final long serialVersionUID = 1;

    public TranslationBundleLoadingException(Class cls, Locale locale, Exception exc) {
        super("Loading of translation bundle failed for [" + cls.getName() + ", " + locale.toString() + "]", cls, locale, exc);
    }
}
