package org.jetbrains.kotlin.org.jline.reader;

import org.jetbrains.kotlin.org.jline.utils.AttributedString;

/* JADX INFO: loaded from: classes4.dex */
public interface Highlighter {
    AttributedString highlight(LineReader lineReader, String str);
}
