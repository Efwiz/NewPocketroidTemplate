package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import org.jetbrains.kotlin.com.intellij.formatting.FormattingMode;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;

/* JADX INFO: loaded from: classes3.dex */
public interface FormattingModeAwareIndentAdjuster {
    int adjustLineIndent(Document document, int i, FormattingMode formattingMode);

    FormattingMode getCurrentFormattingMode();
}
