package kotlin.script.experimental.jvm.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: identifiers.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001¨\u0006\u0002"}, d2 = {"toValidJvmIdentifier", "", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IdentifiersKt {
    public static final String toValidJvmIdentifier(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (StringsKt.isBlank(str)) {
            return StringsKt.repeat("_", str.length());
        }
        StringBuilder sb = new StringBuilder(str.length());
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '/') {
                sb.append("\\|");
            } else if (cCharAt == '.') {
                sb.append("\\,");
            } else if (cCharAt == ';') {
                sb.append("\\?");
            } else if (cCharAt == '$') {
                sb.append("\\%");
            } else if (cCharAt == '<') {
                sb.append("\\^");
            } else if (cCharAt == '>') {
                sb.append("\\_");
            } else if (cCharAt == '[') {
                sb.append("\\{");
            } else if (cCharAt == ']') {
                sb.append("\\}");
            } else if (cCharAt == ':') {
                sb.append("\\!");
            } else if (cCharAt == '\\') {
                sb.append("\\-");
            } else {
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
