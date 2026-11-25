package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

/* JADX INFO: loaded from: classes3.dex */
public interface DocCommentSettings {
    public static final DocCommentSettings DEFAULTS = new Defaults();

    public static final class Defaults implements DocCommentSettings {
        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.DocCommentSettings
        public boolean isDocFormattingEnabled() {
            return true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.DocCommentSettings
        public boolean isLeadingAsteriskEnabled() {
            return true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.DocCommentSettings
        public boolean isRemoveEmptyTags() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.DocCommentSettings
        public void setDocFormattingEnabled(boolean z) {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.DocCommentSettings
        public void setRemoveEmptyTags(boolean z) {
        }
    }

    boolean isDocFormattingEnabled();

    boolean isLeadingAsteriskEnabled();

    boolean isRemoveEmptyTags();

    void setDocFormattingEnabled(boolean z);

    void setRemoveEmptyTags(boolean z);
}
