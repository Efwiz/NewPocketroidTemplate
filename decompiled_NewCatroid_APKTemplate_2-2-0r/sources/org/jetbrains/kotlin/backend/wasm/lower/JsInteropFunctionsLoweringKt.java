package org.jetbrains.kotlin.backend.wasm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JsInteropFunctionsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a,\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u0000¨\u0006\n"}, d2 = {"appendParameterList", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "size", "", "name", "", "isEnd", "", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsInteropFunctionsLoweringKt {
    public static /* synthetic */ void appendParameterList$default(StringBuilder sb, int i, String str, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str = "p";
        }
        if ((i2 & 4) != 0) {
            z = true;
        }
        appendParameterList(sb, i, str, z);
    }

    public static final void appendParameterList(StringBuilder sb, int i, String name2, boolean z) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(name2);
            sb.append(i2);
            if (!z || i2 + 1 < i) {
                sb.append(", ");
            }
        }
    }
}
