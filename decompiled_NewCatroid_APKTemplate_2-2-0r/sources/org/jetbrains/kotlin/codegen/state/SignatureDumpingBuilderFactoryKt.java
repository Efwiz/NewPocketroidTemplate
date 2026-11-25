package org.jetbrains.kotlin.codegen.state;

import com.reandroid.dex.sections.Marker;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: compiled from: SignatureDumpingBuilderFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002\u001a&\u0010\u0003\u001a\u00060\u0004j\u0002`\u0005*\u00060\u0004j\u0002`\u00052\u0006\u0010\u0006\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001H\u0002\u001a\u001e\u0010\u0007\u001a\u00060\u0004j\u0002`\u0005*\u00060\u0004j\u0002`\u00052\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001H\u0002¨\u0006\b"}, d2 = {"jsonEscape", "", "value", "appendNameValue", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "name", "appendQuoted", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SignatureDumpingBuilderFactoryKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Appendable appendQuoted(Appendable appendable, String str) throws IOException {
        Appendable appendableAppend = str != null ? appendable.append('\"').append(jsonEscape(str)).append('\"') : null;
        if (appendableAppend != null) {
            return appendableAppend;
        }
        Appendable appendableAppend2 = appendable.append("null");
        Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
        return appendableAppend2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Appendable appendNameValue(Appendable appendable, String str, String str2) throws IOException {
        Appendable appendableAppend = appendQuoted(appendable, str).append(": ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        return appendQuoted(appendableAppend, str2);
    }

    private static final String jsonEscape(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\b') {
                sb.append("\\b");
            } else if (cCharAt == '\t') {
                sb.append("\\t");
            } else if (cCharAt == '\n') {
                sb.append("\\n");
            } else if (cCharAt == '\r') {
                sb.append("\\r");
            } else if (cCharAt == '\"') {
                sb.append("\\\"");
            } else if (cCharAt == '\\') {
                sb.append("\\\\");
            } else if (cCharAt < ' ') {
                StringBuilder sb2 = new StringBuilder("\\u");
                String hexString = Integer.toHexString(cCharAt);
                Intrinsics.checkNotNullExpressionValue(hexString, "toHexString(...)");
                sb.append(sb2.append(StringsKt.padStart(hexString, 4, ExternalAnnotationProvider.NULLABLE)).toString());
            } else {
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
