package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import org.jetbrains.kotlin.com.intellij.lang.Language;

/* JADX INFO: loaded from: classes3.dex */
public interface CodeStyleSettingsFacade {
    int getIndentSize();

    int getTabSize();

    boolean isSpaceAfterComma();

    boolean isSpaceAroundAssignmentOperators();

    boolean isSpaceBeforeComma();

    CodeStyleSettingsFacade withLanguage(Language language);
}
