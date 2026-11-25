package org.jetbrains.kotlin.com.intellij.openapi.util.text;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface CharFilter {
    public static final CharFilter WHITESPACE_FILTER = new CharFilter() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.text.CharFilter$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.text.CharFilter
        public final boolean accept(char c) {
            return Character.isWhitespace(c);
        }
    };
    public static final CharFilter NOT_WHITESPACE_FILTER = new CharFilter() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.text.CharFilter$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.text.CharFilter
        public final boolean accept(char c) {
            return CharFilter.lambda$static$1(c);
        }
    };

    boolean accept(char c);

    static /* synthetic */ boolean lambda$static$1(char c) {
        return !Character.isWhitespace(c);
    }
}
