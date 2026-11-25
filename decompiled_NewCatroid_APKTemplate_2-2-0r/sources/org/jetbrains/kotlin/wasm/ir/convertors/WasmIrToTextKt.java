package org.jetbrains.kotlin.wasm.ir.convertors;

import java.util.ArrayList;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlin.text.UStringsKt;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: compiled from: WasmIrToText.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\u0010\u0012\n\u0000\u001a\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t\u001a\n\u0010\u000b\u001a\u00020\t*\u00020\f\u001a\n\u0010\u000b\u001a\u00020\t*\u00020\r\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"F32_CANON_NAN", "", "F64_CANON_NAN", "", "isValidWatIdentifierChar", "", "c", "", "sanitizeWatIdentifier", "", OutputKeys.INDENT, "toWatData", "", "", "wasm.ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class WasmIrToTextKt {
    public static final String toWatData(byte b) {
        return "\\" + StringsKt.padStart(UStringsKt.m4238toStringLxnNnR4(UByte.m2916constructorimpl(b), 16), 2, ExternalAnnotationProvider.NULLABLE);
    }

    public static final String toWatData(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        return "\"" + ArraysKt.joinToString$default(bArr, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) new Function1<Byte, CharSequence>() { // from class: org.jetbrains.kotlin.wasm.ir.convertors.WasmIrToTextKt.toWatData.1
            public final CharSequence invoke(byte b) {
                return WasmIrToTextKt.toWatData(b);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Byte b) {
                return invoke(b.byteValue());
            }
        }, 30, (Object) null) + '\"';
    }

    public static final String sanitizeWatIdentifier(String indent) {
        Intrinsics.checkNotNullParameter(indent, "indent");
        String str = indent;
        if (str.length() == 0) {
            return "_";
        }
        for (int i = 0; i < str.length(); i++) {
            if (!isValidWatIdentifierChar(str.charAt(i))) {
                ArrayList arrayList = new ArrayList(str.length());
                for (int i2 = 0; i2 < str.length(); i2++) {
                    char cCharAt = str.charAt(i2);
                    arrayList.add(isValidWatIdentifierChar(cCharAt) ? Character.valueOf(cCharAt) : "_");
                }
                return CollectionsKt.joinToString$default(arrayList, "", null, null, 0, null, null, 62, null);
            }
        }
        return indent;
    }

    public static final boolean isValidWatIdentifierChar(char c) {
        return ('0' <= c && c < ':') || ('A' <= c && c < '[') || (('a' <= c && c < '{') || StringsKt.contains$default((CharSequence) "!#$%&′*+-./:<=>?@\\^_`|~", c, false, 2, (Object) null) || StringsKt.contains$default((CharSequence) "$.@_", c, false, 2, (Object) null));
    }
}
