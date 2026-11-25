package org.jetbrains.kotlin.js.resolve.diagnostics;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.Utils;

/* JADX INFO: compiled from: jsRenderers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003\u001a\u001a\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003¨\u0006\u0006"}, d2 = {"underlineAsHtml", "", TypedValues.TransitionType.S_FROM, "", "to", "underlineAsText", "js.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsRenderersKt {
    public static final String underlineAsText(String str, int i, int i2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        int length = str.length();
        int i3 = 0;
        boolean z = false;
        while (i3 < length) {
            char cCharAt = str.charAt(i3);
            char c = (i > i3 || i3 > i2) ? ' ' : Util.C_EXCEPTION_START;
            sb.append(cCharAt);
            sb2.append(c);
            z = z || c != ' ';
            if (Utils.isEndOfLine(cCharAt)) {
                if (z) {
                    String string = sb2.toString();
                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                    StringBuilder sbAppend = sb.append(StringsKt.trimEnd((CharSequence) string).toString());
                    Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
                    Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
                    z = false;
                }
                sb2 = new StringBuilder();
            }
            i3++;
        }
        if (z) {
            Intrinsics.checkNotNullExpressionValue(sb.append('\n'), "append(...)");
            sb.append(sb2.toString());
        }
        String string2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        return string2;
    }

    public static final String underlineAsHtml(String str, int i, int i2) {
        String str2;
        Intrinsics.checkNotNullParameter(str, "<this>");
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        boolean z = false;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if (i3 == i) {
                z = true;
                str2 = "<u>";
            } else if (i3 == i2) {
                z = false;
                str2 = "</u>";
            } else {
                str2 = "";
            }
            sb.append(str2);
            if (Utils.isEndOfLine(cCharAt) && z) {
                sb.append("</u>" + cCharAt + "<u>");
            } else {
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
