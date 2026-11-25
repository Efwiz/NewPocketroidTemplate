package org.jetbrains.kotlin.com.google.gwt.dev.js.rhino;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CodePosition.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"offsetOf", "", "", "position", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/CodePosition;", "js.parser"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CodePositionKt {
    public static final int offsetOf(String str, CodePosition position) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(position, "position");
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (i2 == position.getLine() && i3 == position.getOffset()) {
                return i;
            }
            i++;
            i3++;
            if (Utils.isEndOfLine(cCharAt)) {
                i2++;
                position.getLine();
                i3 = 0;
            }
        }
        return str.length();
    }
}
