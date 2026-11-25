package org.jetbrains.kotlin.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;

/* JADX INFO: compiled from: numbers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"extractRadix", "Lorg/jetbrains/kotlin/utils/NumberWithRadix;", "value", "", "util.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NumbersKt {
    public static final NumberWithRadix extractRadix(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (kotlin.text.StringsKt.startsWith$default(value, PsiLiteralUtil.HEX_PREFIX, false, 2, (Object) null) || kotlin.text.StringsKt.startsWith$default(value, "0X", false, 2, (Object) null)) {
            String strSubstring = value.substring(2);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return new NumberWithRadix(strSubstring, 16);
        }
        if (kotlin.text.StringsKt.startsWith$default(value, PsiLiteralUtil.BIN_PREFIX, false, 2, (Object) null) || kotlin.text.StringsKt.startsWith$default(value, "0B", false, 2, (Object) null)) {
            String strSubstring2 = value.substring(2);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
            return new NumberWithRadix(strSubstring2, 2);
        }
        return new NumberWithRadix(value, 10);
    }
}
