package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.JsToStringGenerationVisitor;

/* JADX INFO: compiled from: JsHelpers.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\r\n\u0002\u0010\u000e\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"toJsStringLiteral", "", "", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsHelpersKt {
    public static final CharSequence toJsStringLiteral(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        CharSequence charSequenceJavaScriptString = JsToStringGenerationVisitor.javaScriptString(str);
        Intrinsics.checkNotNullExpressionValue(charSequenceJavaScriptString, "javaScriptString(...)");
        return charSequenceJavaScriptString;
    }
}
