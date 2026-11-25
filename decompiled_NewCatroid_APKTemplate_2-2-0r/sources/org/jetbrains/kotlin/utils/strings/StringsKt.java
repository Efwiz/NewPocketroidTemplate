package org.jetbrains.kotlin.utils.strings;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;

/* JADX INFO: compiled from: strings.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u001a\u0018\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\"\u0010\t\u001a\u00020\u0001*\u00020\u00062\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082D¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082D¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082D¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"BEGIN_MARKER", "", "CARET_MARKER", "END_MARKER", "position", "str", "", TypedValues.CycleType.S_WAVE_OFFSET, "", "substringWithContext", "beginIndex", "endIndex", "range", "util"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class StringsKt {
    private static final String BEGIN_MARKER = "<~BEGIN~>";
    private static final String CARET_MARKER = "<~!!~>";
    private static final String END_MARKER = "<~END~>";

    public static final String substringWithContext(CharSequence charSequence, int i, int i2, int i3) {
        String str;
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        int iMax = Math.max(0, i - i3);
        int iMin = Math.min(charSequence.length(), i3 + i2);
        boolean z = iMax != 0;
        boolean z2 = iMin != charSequence.length();
        int i4 = i - iMax;
        int i5 = i2 - iMax;
        String strSubstring = charSequence.toString().substring(iMax, iMin);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        StringBuilder sbInsert = new StringBuilder(strSubstring).insert(i5, i5 == i4 ? CARET_MARKER : END_MARKER);
        String str2 = "";
        StringBuilder sbInsert2 = sbInsert.insert(i4, i5 == i4 ? "" : BEGIN_MARKER);
        if (!z) {
            str = "";
        } else {
            str = "<~..." + position(charSequence, iMax) + "~>";
        }
        StringBuilder sbInsert3 = sbInsert2.insert(0, str);
        if (z2) {
            str2 = "<~" + position(charSequence, iMin) + "...~>";
        }
        String string = sbInsert3.append(str2).toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private static final String position(CharSequence charSequence, int i) {
        return "(line: " + (StringUtil.offsetToLineNumber(charSequence, i) + 1) + ')';
    }
}
