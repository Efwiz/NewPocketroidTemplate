package com.android.tools.r8.position;

import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class TextRange implements Position {
    private final TextPosition a;
    private final TextPosition b;

    public TextRange(TextPosition textPosition, TextPosition textPosition2) {
        this.a = textPosition;
        this.b = textPosition2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        TextRange textRange = (TextRange) obj;
        return this.a.equals(textRange.getStart()) && this.b.equals(textRange.getEnd());
    }

    @Override // com.android.tools.r8.position.Position
    public String getDescription() {
        return this.a.getDescription();
    }

    public TextPosition getEnd() {
        return this.b;
    }

    public TextPosition getStart() {
        return this.a;
    }

    public int hashCode() {
        return this.a.hashCode() ^ this.b.hashCode();
    }

    public String toString() {
        return "Text range from: '" + getStart() + "', to: '" + getEnd() + FormatHelper.QUOTE;
    }
}
