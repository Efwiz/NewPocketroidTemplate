package org.catrobat.paintroid.tools;

import kotlin.Metadata;
import org.catrobat.paintroid.R;

/* JADX INFO: compiled from: FontType.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\t\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lorg/catrobat/paintroid/tools/FontType;", "", "nameResource", "", "(Ljava/lang/String;II)V", "getNameResource", "()I", "SANS_SERIF", "SERIF", "MONOSPACE", "STC", "DUBAI", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public enum FontType {
    SANS_SERIF(R.string.text_tool_dialog_font_sans_serif),
    SERIF(R.string.text_tool_dialog_font_serif),
    MONOSPACE(R.string.text_tool_dialog_font_monospace),
    STC(R.string.text_tool_dialog_font_arabic_stc),
    DUBAI(R.string.text_tool_dialog_font_dubai);

    private final int nameResource;

    FontType(int i) {
        this.nameResource = i;
    }

    public final int getNameResource() {
        return this.nameResource;
    }
}
